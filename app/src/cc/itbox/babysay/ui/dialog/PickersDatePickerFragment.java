//package cc.itbox.babysay.ui.dialog;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//import org.holoeverywhere.widget.datetimepicker.date.DatePickerDialog;
//
//import android.os.Bundle;
//
///**
// * 日期选择器
// * 
// * @author malinkang 2014-3-7
// * 
// */
//
//public class PickersDatePickerFragment extends DatePickerDialog implements
//		DatePickerDialog.OnDateSetListener {
//	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("y-M-d");
//
//	private OnSetDateListener mOnSetDateListener;
//
//	public void setOnSetDateListener(OnSetDateListener onSetDateListener) {
//		this.mOnSetDateListener = onSetDateListener;
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		initialize(this, 1996, Calendar.APRIL, 14);
//	}
//
//	@Override
//	public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear,
//			int dayOfMonth) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.YEAR, year);
//		calendar.set(Calendar.MONTH, monthOfYear);
//		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//		mOnSetDateListener.setDate(DATE_FORMAT.format(calendar.getTime()));
//
//	}
// }