
package org.holoeverywhere.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;

import org.holoeverywhere.widget.Switch;

public class SwitchPreference extends TwoStatePreference {
    private final Listener mListener = new Listener();
    private CharSequence mSwitchOff, mSwitchOn;

    public SwitchPreference(Context context) {
        this(context, null);
    }

    public SwitchPreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.switchPreferenceStyle);
    }

    public SwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        context = getContext();
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SwitchPreference, defStyle,
                R.style.Holo_PreferenceSwitch);
        mSwitchOn = a.getString(R.styleable.SwitchPreference_android_switchTextOn);
        mSwitchOff = a.getString(R.styleable.SwitchPreference_android_switchTextOff);
        a.recycle();
    }

    public CharSequence getSwitchTextOff() {
        return mSwitchOff;
    }

    public void setSwitchTextOff(CharSequence offText) {
        mSwitchOff = offText;
        notifyChanged();
    }

    public void setSwitchTextOff(int resId) {
        setSwitchTextOff(getContext().getText(resId));
    }

    public CharSequence getSwitchTextOn() {
        return mSwitchOn;
    }

    public void setSwitchTextOn(CharSequence onText) {
        mSwitchOn = onText;
        notifyChanged();
    }

    public void setSwitchTextOn(int resId) {
        setSwitchTextOn(getContext().getText(resId));
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        View checkableView = view.findViewById(R.id.switchWidget);
        if (checkableView != null && checkableView instanceof Checkable) {
            ((Checkable) checkableView).setChecked(mChecked);
            sendAccessibilityEvent(checkableView);
            if (checkableView instanceof Switch) {
                final Switch switchView = (Switch) checkableView;
                switchView.setTextOn(mSwitchOn);
                switchView.setTextOff(mSwitchOff);
                switchView.setOnCheckedChangeListener(mListener);
            }
        }
        syncSummaryView(view);
    }

    private class Listener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!callChangeListener(isChecked)) {
                buttonView.setChecked(!isChecked);
                return;
            }
            setChecked(isChecked);
        }
    }
}
