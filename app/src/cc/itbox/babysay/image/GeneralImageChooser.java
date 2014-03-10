package cc.itbox.babysay.image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.FragmentActivity;
import cc.itbox.babysay.exception.SDCardNotFoundException;
import cc.itbox.babysay.util.FileUtil;
import cc.itbox.babysay.util.FileUtil.StorageOption;

public class GeneralImageChooser implements ImageChooser {

	protected FragmentActivity activity;
	protected int requestCode;

	private ImageChooserSaveLocation tmpCameraSaveLocation;
	private ImageChooserSaveLocation saveLocation;

	private ImageSource source;

	public GeneralImageChooser(FragmentActivity activity, int requestCode) {
		this.activity = activity;
		this.requestCode = requestCode;
	}

	@Override
	public void onActivityResult(final Context context, final Intent data,
			final ImageChooserListener listener) {
		new AsyncTask<Integer, Integer, Bitmap>() {

			private String errorMessage;

			@Override
			protected Bitmap doInBackground(Integer... params) {
				Bitmap photo = null;

				if (data != null && data.getData() != null) {
					try {
						photo = Media.getBitmap(context.getContentResolver(),
								data.getData());
						writePhotoToSaveLocations(photo);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						errorMessage = "ImageSource: " + source
								+ " - could not find a file at "
								+ data.toString() + ".";
						return null;
					} catch (IOException e1) {
						e1.printStackTrace();
						errorMessage = "ImageSource: " + source
								+ " - IOException at " + data.toString() + ".";
						return null;
					}
				} else {
					File tmpFile = new ImageChooserSaveLocation(
							StorageOption.SD_CARD_APP_ROOT, "tmp",
							"my_temporary_image").getFile(context);
					photo = BitmapFactory.decodeFile(tmpFile.getAbsolutePath());
					if (saveLocation != null) {
						try {
							writePhotoToSaveLocations(photo);
						} catch (IOException e) {
							e.printStackTrace();
							errorMessage = "ImageSource: " + source
									+ " - IOException.";
							return null;
						}
					}

					tmpFile.delete();
				}

				return photo;
			}

			private void writePhotoToSaveLocations(Bitmap photo)
					throws IOException {
				File file = saveLocation.getFile(context);
				new File(file.getParent()).mkdirs();

				writeToFile(photo, file);
			}

			private void writeToFile(Bitmap photo, File file)
					throws IOException {
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				photo.compress(CompressFormat.JPEG, 100, bytes);
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(bytes.toByteArray());
				fos.close();
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				super.onPostExecute(result);

				if (result == null) {
					listener.onError(errorMessage);
				} else {
					File file = null;
					if (saveLocation != null) {
						file = saveLocation.getFile(context);
					}

					listener.onResult(result, file);
				}
			}

		}.execute();
	}

	@Override
	public void openCamera() throws SDCardNotFoundException {
		source = ImageSource.CAMERA;
		if (FileUtil.isSDCardAvailable() == false) {
			throw new SDCardNotFoundException("SDCard is not mounted.");
		}
		tmpCameraSaveLocation = new ImageChooserSaveLocation(
				StorageOption.SD_CARD_APP_ROOT, "BabySay", "my_temporary_image");
		File saveTo = tmpCameraSaveLocation.getFile(activity);
		new File(saveTo.getParent()).mkdirs();
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveTo));
		activity.startActivityForResult(cameraIntent, requestCode);
	}

	@Override
	public void openGallery() {
		source = ImageSource.GALLERY;
		Intent cameraIntent = null;
		cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
		cameraIntent.setType("image/*");
		activity.startActivityForResult(cameraIntent, requestCode);
	}
}
