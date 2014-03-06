package cc.itbox.babysay.ui.dialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.holoeverywhere.widget.Toast;
import org.holoeverywhere.widget.datetimepicker.date.DatePickerDialog;

import android.os.Bundle;

public class PickersDatePickerFragment extends DatePickerDialog implements
		DatePickerDialog.OnDateSetListener {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("y-M-d");

	private OnSetDateListener mOnSetDateListener;

	public void setOnSetDateListener(OnSetDateListener onSetDateListener) {
		this.mOnSetDateListener = onSetDateListener;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize(this, 1996, Calendar.APRIL, 14);
	}

	@Override
	public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, monthOfYear);
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		Toast.makeText(getSupportActivity(),
				"Set date: " + DATE_FORMAT.format(calendar.getTime()),
				Toast.LENGTH_SHORT).show();
		mOnSetDateListener.setDate(DATE_FORMAT.format(calendar.getTime()));

	}
}