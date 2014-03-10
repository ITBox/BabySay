package cc.itbox.babysay.image;

import android.content.Context;
import android.content.Intent;

public interface ImageChooser {

	public void openGallery();

	public void openCamera() throws ImageChooserException;

	public void onActivityResult(Context activity, Intent data,
			ImageChooserListener listener);

}
