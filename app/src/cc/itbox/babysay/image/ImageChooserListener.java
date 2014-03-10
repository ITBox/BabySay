package cc.itbox.babysay.image;

import java.io.File;

import android.graphics.Bitmap;

public interface ImageChooserListener {

	public void onResult(Bitmap image, File savedImages);
	public void onError(String message);
	
}
