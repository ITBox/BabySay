package cc.itbox.babysay.ui.dialog;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.DialogFragment;
import org.holoeverywhere.widget.ArrayAdapter;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import cc.itbox.babysay.R;
import cc.itbox.babysay.exception.SDCardNotFoundException;
import cc.itbox.babysay.image.ImageChooser;
import cc.itbox.babysay.image.ImageChooserException;
import cc.itbox.babysay.util.UIUtil;

public class GetPictureDialogListFragment extends DialogFragment {

	private ImageChooser mImageChooser;

	public static GetPictureDialogListFragment newInstance(
			ImageChooser imageChooser) {
		GetPictureDialogListFragment fragment = new GetPictureDialogListFragment();
		fragment.mImageChooser = imageChooser;
		return fragment;
	}

	@Override
	public AlertDialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				getSupportActivity());
		builder.setTitle("获取照片");
		builder.setAdapter(ArrayAdapter.createFromResource(
				getSupportActivity(), R.array.get_picture,
				R.layout.simple_list_item_1), LIST_CLICK_LISTENER);
		return builder.create();
	}

	private final OnClickListener LIST_CLICK_LISTENER = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == 1) {

				mImageChooser.openGallery();
			} else {
				try {
					mImageChooser.openCamera();

				} catch (SDCardNotFoundException e) {
					e.printStackTrace();
					UIUtil.showShortToast(
							getActivity(),
							getSupportActionBarContext().getString(
									R.string.please_insert_sdcard));
				} catch (ImageChooserException e) {
					e.printStackTrace();
				}
			}
		}
	};

}