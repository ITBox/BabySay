package cc.itbox.babysay.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import cc.itbox.babysay.ui.FloatLabel.LabelAnimator;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class FloatLabelAnimator implements LabelAnimator {

	/* package */static final float SCALE_X_SHOWN = 1f;
	/* package */static final float SCALE_X_HIDDEN = 2f;
	/* package */static final float SCALE_Y_SHOWN = 1f;
	/* package */static final float SCALE_Y_HIDDEN = 0f;

	@Override
	public void onDisplayLabel(View label) {
		final float shift = label.getWidth() / 2;
		label.setScaleX(SCALE_X_HIDDEN);
		label.setScaleY(SCALE_Y_HIDDEN);
		label.setX(shift);
		label.animate().alpha(1).scaleX(SCALE_X_SHOWN).scaleY(SCALE_Y_SHOWN).x(0f);
	}

	@Override
	public void onHideLabel(View label) {
		final float shift = label.getWidth() / 2;
		label.setScaleX(SCALE_X_SHOWN);
		label.setScaleY(SCALE_Y_SHOWN);
		label.setX(0f);
		label.animate().alpha(0).scaleX(SCALE_X_HIDDEN).scaleY(SCALE_Y_HIDDEN).x(shift);
	}

}
