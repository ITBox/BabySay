package cc.itbox.babysay.image;

import cc.itbox.babysay.exception.SDCardNotFoundException;

public interface ImageChooser {

	public void openGallery() throws SDCardNotFoundException;

	public void openCamera() throws SDCardNotFoundException;

}
