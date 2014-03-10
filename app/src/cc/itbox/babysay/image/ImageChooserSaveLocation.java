package cc.itbox.babysay.image;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import cc.itbox.babysay.exception.ImageExtension;
import cc.itbox.babysay.util.FileUtil.StorageOption;

public class ImageChooserSaveLocation {

	private final StorageOption storageOption;
	private final String path;

	public ImageChooserSaveLocation(StorageOption storageOption,
			String directory, String imageName, ImageExtension extension) {
		this.storageOption = storageOption;
		this.path = directory + "/" + imageName + "."
				+ extension.toString().toLowerCase();
	}

	public ImageChooserSaveLocation(StorageOption saveLocation,
			String directory, String imageName) {
		this(saveLocation, directory, imageName, ImageExtension.JPG);
	}

	public File getFile(Context context) {
		if (storageOption == StorageOption.SD_CARD_ROOT) {
			return new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/" + path);
		} else if (storageOption == StorageOption.SD_CARD_APP_ROOT) {
			return new File(context.getExternalFilesDir(null).getAbsolutePath()
					+ "/" + path);
		} else {
			return new File(context.getFilesDir().getAbsolutePath() + "/"
					+ path);
		}
	}

}
