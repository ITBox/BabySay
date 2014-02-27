/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.holoeverywhere.widget.datetimepicker.date;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.holoeverywhere.FontLoader;
import org.holoeverywhere.R;
import org.holoeverywhere.widget.datetimepicker.DateTimePickerUtils;
import org.holoeverywhere.widget.datetimepicker.TouchExplorationHelper;
import org.holoeverywhere.widget.datetimepicker.date.SimpleMonthAdapter.CalendarDay;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A calendar-like view displaying a specified month and the appropriate selectable day numbers
 * within the specified month.
 */
public class SimpleMonthView extends View {
    /**
     * This sets the height of this week in pixels
     */
    public static final String VIEW_PARAMS_HEIGHT = "height";

    /**
     * These params can be passed into the view to control how it appears.
     * {@link #VIEW_PARAMS_WEEK} is the only required field, though the default
     * values are unlikely to fit most layouts correctly.
     */
    /**
     * This specifies the position (or weeks since the epoch) of this week,
     * calculated using {@link org.holoeverywhere.widget.datetimepicker.DateTimePickerUtils#getWeeksSinceEpochFromJulianDay}
     */
    public static final String VIEW_PARAMS_MONTH = "month";
    /**
     * This specifies the position (or weeks since the epoch) of this week,
     * calculated using {@link org.holoeverywhere.widget.datetimepicker.DateTimePickerUtils#getWeeksSinceEpochFromJulianDay}
     */
    public static final String VIEW_PARAMS_YEAR = "year";
    /**
     * This sets one of the days in this view as selected {@link Time#SUNDAY}
     * through {@link Time#SATURDAY}.
     */
    public static final String VIEW_PARAMS_SELECTED_DAY = "selected_day";
    /**
     * Which day the week should start on. {@link Time#SUNDAY} through
     * {@link Time#SATURDAY}.
     */
    public static final String VIEW_PARAMS_WEEK_START = "week_start";
    protected static final int DEFAULT_SELECTED_DAY = -1;
    protected static final int DEFAULT_WEEK_START = Calendar.SUNDAY;
    protected static final int DEFAULT_NUM_DAYS = 7;
    protected static final int DEFAULT_NUM_ROWS = 6;
    protected static final int MAX_NUM_ROWS = 6;
    private static final int SELECTED_CIRCLE_ALPHA = 60;
    protected static int DEFAULT_HEIGHT = 32;
    protected static int MIN_HEIGHT = 10;
    protected static int DAY_SEPARATOR_WIDTH = 1;
    protected static int MINI_DAY_NUMBER_TEXT_SIZE;
    protected static int MONTH_LABEL_TEXT_SIZE;
    protected static int MONTH_DAY_LABEL_TEXT_SIZE;
    protected static int MONTH_HEADER_SIZE;
    protected static int DAY_SELECTED_CIRCLE_SIZE;
    private final Formatter mFormatter;
    private final StringBuilder mStringBuilder;
    private final Calendar mCalendar;
    private final Calendar mDayLabelCalendar;
    private final MonthViewNodeProvider mNodeProvider;
    // affects the padding on the sides of this view
    protected int mPadding = 0;
    protected Paint mMonthNumPaint;
    protected Paint mMonthTitlePaint;
    protected Paint mSelectedCirclePaint;
    protected Paint mMonthDayLabelPaint;
    protected int mMonth;
    protected int mYear;
    // Quick reference to the width of this view, matches parent
    protected int mWidth;
    // The height this view should draw at in pixels, set by height param
    protected int mRowHeight = DEFAULT_HEIGHT;
    // If this view contains the today
    protected boolean mHasToday = false;
    // Which day is selected [0-6] or -1 if no day is selected
    protected int mSelectedDay = -1;
    // Which day is today [0-6] or -1 if no day is today
    protected int mToday = DEFAULT_SELECTED_DAY;
    // Which day of the week to start on [0-6]
    protected int mWeekStart = DEFAULT_WEEK_START;
    // How many days to display
    protected int mNumDays = DEFAULT_NUM_DAYS;
    // The number of days + a spot for week number if it is displayed
    protected int mNumCells = mNumDays;
    protected int mDayTextColor;
    protected int mTodayNumberColor;
    protected int mMonthTitleColor;
    protected int mSelectedCircleColor;
    private int mNumRows = DEFAULT_NUM_ROWS;
    // Optional listener for handling day click actions
    private OnDayClickListener mOnDayClickListener;
    // Whether to prevent setting the accessibility delegate
    private boolean mLockAccessibilityDelegate;
    private int mDayOfWeekStart = 0;
    private SimpleDateFormat mMonthAndYearFormatter;

    public SimpleMonthView(Context context) {
        this(context, null);
    }

    public SimpleMonthView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dateTimePickerStyle);
    }

    public SimpleMonthView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);


        Resources res = context.getResources();

        mDayLabelCalendar = Calendar.getInstance();
        mCalendar = Calendar.getInstance();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DateTimePicker, defStyle, R.style.Holo_DateTimePicker);
        mDayTextColor = a.getColor(R.styleable.DateTimePicker_dateDayTextColor, 0);
        mTodayNumberColor = a.getColor(R.styleable.DateTimePicker_dateTodayColor, 0);
        mMonthTitleColor = a.getColor(R.styleable.DateTimePicker_dateMonthTitleColor, 0);
        mSelectedCircleColor = a.getColor(R.styleable.DateTimePicker_dateCircleColor, 0);
        a.recycle();

        mStringBuilder = new StringBuilder(50);
        mFormatter = new Formatter(mStringBuilder, Locale.getDefault());

        MINI_DAY_NUMBER_TEXT_SIZE = res.getDimensionPixelSize(R.dimen.date_day_number_size);
        MONTH_LABEL_TEXT_SIZE = res.getDimensionPixelSize(R.dimen.date_month_label_size);
        MONTH_DAY_LABEL_TEXT_SIZE = res.getDimensionPixelSize(R.dimen.date_month_day_label_text_size);
        MONTH_HEADER_SIZE = res.getDimensionPixelOffset(R.dimen.date_month_list_item_header_height);
        DAY_SELECTED_CIRCLE_SIZE = res
                .getDimensionPixelSize(R.dimen.date_day_number_select_circle_radius);

        mRowHeight = (res.getDimensionPixelOffset(R.dimen.date_time_picker_size)
                - MONTH_HEADER_SIZE) / MAX_NUM_ROWS;

        // Set up accessibility components.
        mNodeProvider = new MonthViewNodeProvider(context, this);
        ViewCompat.setAccessibilityDelegate(this, mNodeProvider.getAccessibilityDelegate());
        ViewCompat.setImportantForAccessibility(this, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);
        mLockAccessibilityDelegate = true;

        // Sets up any standard paints that will be used
        initView();
    }

    @Override
    public void setAccessibilityDelegate(AccessibilityDelegate delegate) {
        // Workaround for a JB MR1 issue where accessibility delegates on
        // top-level ListView items are overwritten.
        if (!mLockAccessibilityDelegate) {
            super.setAccessibilityDelegate(delegate);
        }
    }

    public void setOnDayClickListener(OnDayClickListener listener) {
        mOnDayClickListener = listener;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public boolean onHoverEvent(MotionEvent event) {
        // First right-of-refusal goes the touch exploration helper.
        if (mNodeProvider.getOnHoverListener().onHover(this, event)) {
            return true;
        }
        return super.onHoverEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                final CalendarDay day = getDayFromLocation(event.getX(), event.getY());
                if (day != null) {
                    onDayClick(day);
                }
                break;
        }
        return true;
    }

    /**
     * Sets up the text and style properties for painting. Override this if you
     * want to use a different paint.
     */
    protected void initView() {

        mMonthTitlePaint = new Paint();
        mMonthTitlePaint.setFakeBoldText(true);
        mMonthTitlePaint.setAntiAlias(true);
        mMonthTitlePaint.setTextSize(MONTH_LABEL_TEXT_SIZE);
        mMonthTitlePaint.setTypeface(FontLoader.ROBOTO_BOLD.getTypeface(getContext()));
        mMonthTitlePaint.setColor(mMonthTitleColor);
        mMonthTitlePaint.setTextAlign(Align.CENTER);
        mMonthTitlePaint.setStyle(Style.FILL);

        mSelectedCirclePaint = new Paint();
        mSelectedCirclePaint.setFakeBoldText(true);
        mSelectedCirclePaint.setAntiAlias(true);
        mSelectedCirclePaint.setColor(mSelectedCircleColor);
        mSelectedCirclePaint.setTextAlign(Align.CENTER);
        mSelectedCirclePaint.setStyle(Style.FILL);
        mSelectedCirclePaint.setAlpha(SELECTED_CIRCLE_ALPHA);

        mMonthDayLabelPaint = new Paint();
        mMonthDayLabelPaint.setAntiAlias(true);
        mMonthDayLabelPaint.setTextSize(MONTH_DAY_LABEL_TEXT_SIZE);
        mMonthDayLabelPaint.setColor(mDayTextColor);
        mMonthDayLabelPaint.setTypeface(FontLoader.ROBOTO_REGULAR.getTypeface(getContext()));
        mMonthDayLabelPaint.setStyle(Style.FILL);
        mMonthDayLabelPaint.setTextAlign(Align.CENTER);
        mMonthDayLabelPaint.setFakeBoldText(true);

        mMonthNumPaint = new Paint();
        mMonthNumPaint.setAntiAlias(true);
        mMonthNumPaint.setTextSize(MINI_DAY_NUMBER_TEXT_SIZE);
        mMonthNumPaint.setStyle(Style.FILL);
        mMonthNumPaint.setTextAlign(Align.CENTER);
        mMonthNumPaint.setFakeBoldText(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawMonthTitle(canvas);
        drawMonthDayLabels(canvas);
        drawMonthNums(canvas);
    }

    /**
     * Sets all the parameters for displaying this week. The only required
     * parameter is the week number. Other parameters have a default value and
     * will only update if a new value is included, except for focus month,
     * which will always default to no focus month if no value is passed in. See
     * {@link #VIEW_PARAMS_HEIGHT} for more info on parameters.
     *
     * @param params A map of the new parameters, see
     *               {@link #VIEW_PARAMS_HEIGHT}
     */
    public void setMonthParams(HashMap<String, Integer> params) {
        if (!params.containsKey(VIEW_PARAMS_MONTH) && !params.containsKey(VIEW_PARAMS_YEAR)) {
            throw new InvalidParameterException("You must specify the month and year for this view");
        }
        setTag(params);
        // We keep the current value for any params not present
        if (params.containsKey(VIEW_PARAMS_HEIGHT)) {
            mRowHeight = params.get(VIEW_PARAMS_HEIGHT);
            if (mRowHeight < MIN_HEIGHT) {
                mRowHeight = MIN_HEIGHT;
            }
        }
        if (params.containsKey(VIEW_PARAMS_SELECTED_DAY)) {
            mSelectedDay = params.get(VIEW_PARAMS_SELECTED_DAY);
        }

        // Allocate space for caching the day numbers and focus values
        mMonth = params.get(VIEW_PARAMS_MONTH);
        mYear = params.get(VIEW_PARAMS_YEAR);

        // Figure out what day today is
        final Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        mHasToday = false;
        mToday = -1;

        mCalendar.set(Calendar.MONTH, mMonth);
        mCalendar.set(Calendar.YEAR, mYear);
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        mDayOfWeekStart = mCalendar.get(Calendar.DAY_OF_WEEK);

        if (params.containsKey(VIEW_PARAMS_WEEK_START)) {
            mWeekStart = params.get(VIEW_PARAMS_WEEK_START);
        } else {
            mWeekStart = mCalendar.getFirstDayOfWeek();
        }

        mNumCells = DateTimePickerUtils.getDaysInMonth(mMonth, mYear);
        for (int i = 0; i < mNumCells; i++) {
            final int day = i + 1;
            if (sameDay(day, today)) {
                mHasToday = true;
                mToday = day;
            }
        }
        mNumRows = calculateNumRows();

        // Invalidate cached accessibility information.
        mNodeProvider.invalidateParent();
    }

    public void reuse() {
        mNumRows = DEFAULT_NUM_ROWS;
        requestLayout();
    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        int dividend = (offset + mNumCells) / mNumDays;
        int remainder = (offset + mNumCells) % mNumDays;
        return (dividend + (remainder > 0 ? 1 : 0));
    }

    private boolean sameDay(int day, Time today) {
        return mYear == today.year &&
                mMonth == today.month &&
                day == today.monthDay;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mRowHeight * mNumRows
                + MONTH_HEADER_SIZE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;

        // Invalidate cached accessibility information.
        mNodeProvider.invalidateParent();
    }

    private String getMonthAndYearString() {
        int flags = DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                | DateUtils.FORMAT_NO_MONTH_DAY;
        mStringBuilder.setLength(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            long millis = mCalendar.getTimeInMillis();
            return DateUtils.formatDateRange(getContext(), mFormatter, millis, millis, flags,
                    Time.getCurrentTimezone()).toString();
        } else {
            if (mMonthAndYearFormatter == null) {
                mMonthAndYearFormatter = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
                mMonthAndYearFormatter.setTimeZone(TimeZone.getDefault());
            }
            return mMonthAndYearFormatter.format(mCalendar.getTime());
        }
    }

    private void drawMonthTitle(Canvas canvas) {
        int x = (mWidth + 2 * mPadding) / 2;
        int y = (MONTH_HEADER_SIZE - MONTH_DAY_LABEL_TEXT_SIZE) / 2 + (MONTH_LABEL_TEXT_SIZE / 3);
        canvas.drawText(getMonthAndYearString(), x, y, mMonthTitlePaint);
    }

    private void drawMonthDayLabels(Canvas canvas) {
        int y = MONTH_HEADER_SIZE - (MONTH_DAY_LABEL_TEXT_SIZE / 2);
        int dayWidthHalf = (mWidth - mPadding * 2) / (mNumDays * 2);

        for (int i = 0; i < mNumDays; i++) {
            int calendarDay = (i + mWeekStart) % mNumDays;
            int x = (2 * i + 1) * dayWidthHalf + mPadding;
            mDayLabelCalendar.set(Calendar.DAY_OF_WEEK, calendarDay);
            canvas.drawText(DatePickerDialog.getDisplayName(mDayLabelCalendar, Calendar.DAY_OF_WEEK, Calendar.SHORT,
                    Locale.getDefault()).toUpperCase(Locale.getDefault()), x, y,
                    mMonthDayLabelPaint);
        }
    }

    /**
     * Draws the week and month day numbers for this week. Override this method
     * if you need different placement.
     *
     * @param canvas The canvas to draw on
     */
    protected void drawMonthNums(Canvas canvas) {
        int y = (((mRowHeight + MINI_DAY_NUMBER_TEXT_SIZE) / 2) - DAY_SEPARATOR_WIDTH)
                + MONTH_HEADER_SIZE;
        int dayWidthHalf = (mWidth - mPadding * 2) / (mNumDays * 2);
        int j = findDayOffset();
        for (int dayNumber = 1; dayNumber <= mNumCells; dayNumber++) {
            int x = (2 * j + 1) * dayWidthHalf + mPadding;
            if (mSelectedDay == dayNumber) {
                canvas.drawCircle(x, y - (MINI_DAY_NUMBER_TEXT_SIZE / 3), DAY_SELECTED_CIRCLE_SIZE,
                        mSelectedCirclePaint);
            }

            mMonthNumPaint.setColor(mHasToday && mToday == dayNumber ? mTodayNumberColor : mDayTextColor);
            canvas.drawText(String.format("%d", dayNumber), x, y, mMonthNumPaint);
            j++;
            if (j == mNumDays) {
                j = 0;
                y += mRowHeight;
            }
        }
    }

    private int findDayOffset() {
        return (mDayOfWeekStart < mWeekStart ? (mDayOfWeekStart + mNumDays) : mDayOfWeekStart)
                - mWeekStart;
    }

    /**
     * Calculates the day that the given x position is in, accounting for week
     * number. Returns a Time referencing that day or null if
     *
     * @param x The x position of the touch event
     * @return A time object for the tapped day or null if the position wasn't
     * in a day
     */
    public CalendarDay getDayFromLocation(float x, float y) {
        int dayStart = mPadding;
        if (x < dayStart || x > mWidth - mPadding) {
            return null;
        }
        // Selection is (x - start) / (pixels/day) == (x -s) * day / pixels
        int row = (int) (y - MONTH_HEADER_SIZE) / mRowHeight;
        int column = (int) ((x - dayStart) * mNumDays / (mWidth - dayStart - mPadding));

        int day = column - findDayOffset() + 1;
        day += row * mNumDays;
        if (day < 1 || day > mNumCells) {
            return null;
        }
        return new CalendarDay(mYear, mMonth, day);
    }

    /**
     * Called when the user clicks on a day. Handles callbacks to the
     * {@link OnDayClickListener} if one is set.
     *
     * @param day A time object representing the day that was clicked
     */
    private void onDayClick(CalendarDay day) {
        if (mOnDayClickListener != null) {
            mOnDayClickListener.onDayClick(this, day);
        }

        // This is a no-op if accessibility is turned off.
        mNodeProvider.sendEventForItem(day, AccessibilityEvent.TYPE_VIEW_CLICKED);
    }

    /**
     * @return The date that has accessibility focus, or {@code null} if no date
     * has focus
     */
    public CalendarDay getAccessibilityFocus() {
        return mNodeProvider.getFocusedItem();
    }

    /**
     * Clears accessibility focus within the view. No-op if the view does not
     * contain accessibility focus.
     */
    public void clearAccessibilityFocus() {
        mNodeProvider.clearFocusedItem();
    }

    /**
     * Attempts to restore accessibility focus to the specified date.
     *
     * @param day The date which should receive focus
     * @return {@code false} if the date is not valid for this month view, or
     * {@code true} if the date received focus
     */
    public boolean restoreAccessibilityFocus(CalendarDay day) {
        if ((day.year != mYear) || (day.month != mMonth) || (day.day > mNumCells)) {
            return false;
        }

        mNodeProvider.setFocusedItem(day);
        return true;
    }

    /**
     * Handles callbacks when the user clicks on a time object.
     */
    public interface OnDayClickListener {
        public void onDayClick(SimpleMonthView view, CalendarDay day);
    }

    /**
     * Provides a virtual view hierarchy for interfacing with an accessibility
     * service.
     */
    private class MonthViewNodeProvider extends TouchExplorationHelper<CalendarDay> {
        private final SparseArray<CalendarDay> mCachedItems = new SparseArray<CalendarDay>();
        private final Rect mTempRect = new Rect();
        Calendar recycle;

        public MonthViewNodeProvider(Context context, View parent) {
            super(context, parent);
        }

        @Override
        public void invalidateItem(CalendarDay item) {
            super.invalidateItem(item);
            mCachedItems.delete(getIdForItem(item));
        }

        @Override
        public void invalidateParent() {
            super.invalidateParent();
            mCachedItems.clear();
        }

        @Override
        protected boolean performActionForItem(CalendarDay item, int action, Bundle arguments) {
            switch (action) {
                case AccessibilityNodeInfo.ACTION_CLICK:
                    onDayClick(item);
                    return true;
            }

            return false;
        }

        @Override
        protected void populateEventForItem(CalendarDay item, AccessibilityEvent event) {
            event.setContentDescription(getItemDescription(item));
        }

        @Override
        protected void populateNodeForItem(CalendarDay item, AccessibilityNodeInfoCompat node) {
            getItemBounds(item, mTempRect);

            node.setContentDescription(getItemDescription(item));
            node.setBoundsInParent(mTempRect);
            node.addAction(AccessibilityNodeInfo.ACTION_CLICK);

            if (item.day == mSelectedDay) {
                node.setSelected(true);
            }
        }

        @Override
        protected void getVisibleItems(List<CalendarDay> items) {
            // TODO: Optimize, only return items visible within parent bounds.
            for (int day = 1; day <= mNumCells; day++) {
                items.add(getItemForId(day));
            }
        }

        @Override
        protected CalendarDay getItemAt(float x, float y) {
            return getDayFromLocation(x, y);
        }

        @Override
        protected int getIdForItem(CalendarDay item) {
            return item.day;
        }

        @Override
        protected CalendarDay getItemForId(int id) {
            if ((id < 1) || (id > mNumCells)) {
                return null;
            }

            final CalendarDay item;
            if (mCachedItems.indexOfKey(id) >= 0) {
                item = mCachedItems.get(id);
            } else {
                item = new CalendarDay(mYear, mMonth, id);
                mCachedItems.put(id, item);
            }

            return item;
        }

        /**
         * Calculates the bounding rectangle of a given time object.
         *
         * @param item The time object to calculate bounds for
         * @param rect The rectangle in which to store the bounds
         */
        private void getItemBounds(CalendarDay item, Rect rect) {
            final int offsetX = mPadding;
            final int offsetY = MONTH_HEADER_SIZE;
            final int cellHeight = mRowHeight;
            final int cellWidth = ((mWidth - (2 * mPadding)) / mNumDays);
            final int index = ((item.day - 1) + findDayOffset());
            final int row = (index / mNumDays);
            final int column = (index % mNumDays);
            final int x = (offsetX + (column * cellWidth));
            final int y = (offsetY + (row * cellHeight));

            rect.set(x, y, (x + cellWidth), (y + cellHeight));
        }

        /**
         * Generates a description for a given time object. Since this
         * description will be spoken, the components are ordered by descending
         * specificity as DAY MONTH YEAR.
         *
         * @param item The time object to generate a description for
         * @return A description of the time object
         */
        private CharSequence getItemDescription(CalendarDay item) {
            if (recycle == null) {
                recycle = Calendar.getInstance();
            }
            recycle.set(item.year, item.month, item.day);
            CharSequence date = DateFormat.format("dd MMMM yyyy", recycle.getTimeInMillis());

            if (item.day == mSelectedDay) {
                return getContext().getString(R.string.item_is_selected, date);
            }

            return date;
        }
    }
}
