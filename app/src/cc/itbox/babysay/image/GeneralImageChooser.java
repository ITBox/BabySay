package cc.itbox.babysay.image;

import android.content.Intent;
import android.support.v4.app.Fragment;
import cc.itbox.babysay.exception.SDCardNotFoundException;
import cc.itbox.babysay.util.FileUtil;

public class GeneralImageChooser implements ImageChooser {

	protected Fragment fragment;
	protected int requestCode;

	public GeneralImageChooser(Fragment fragment, int requestCode) {
		this.fragment = fragment;
		this.requestCode = requestCode;
	}

	@Override
	public void openCamera() throws SDCardNotFoundException {

		if (FileUtil.isSDCardAvailable() == false) {
			throw new SDCardNotFoundException("SDCard is not mounted.");
		}
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		fragment.startActivityForResult(cameraIntent, requestCode);
	}

	@Override
	public void openGallery() throws SDCardNotFoundException {
		if (FileUtil.isSDCardAvailable() == false) {
			throw new SDCardNotFoundException("SDCard is not mounted.");
		}
		Intent cameraIntent = null;
		cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
		cameraIntent.setType("image/*");
		fragment.startActivityForResult(cameraIntent, requestCode);
	}
}
