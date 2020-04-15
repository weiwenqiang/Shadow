package com.zzz.myemergencyclientnew.dialogui.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzz.myemergencyclientnew.dialogui.R;
import com.zzz.myemergencyclientnew.dialogui.adapter.StrericWheelAdapter;
import com.zzz.myemergencyclientnew.dialogui.listener.OnWheelChangedListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作 者：魏文强
 * 创建日期：2017/7/10 10:39
 */
public class DateStageSelectorWheelView extends RelativeLayout implements
        OnWheelChangedListener {
    private final String flag = this.getClass().getSimpleName();

    public static final int TYPE_HHMM = 4;

    private LinearLayout rlTitle;
    private View lineL;
    private LinearLayout llWheelViews;

    private TextView tvSubTitleStart;
    private TextView tvSubTitleEnd;

    private TextView tvHourStart;
    private TextView tvMinuteStart;
    private TextView tvHourEnd;
    private TextView tvMinuteEnd;

    private WheelView wvHourStart;
    private WheelView wvMinuteStart;
    private WheelView wvHourEnd;
    private WheelView wvMinuteEnd;
    /**
     * 设置显示的日期
     */
    private long mDate;
    /**
     * 显示时数
     */
    private String[] hours = new String[24];
    /**
     * 显示分数
     */
    private String[] minutes = new String[60];

    private StrericWheelAdapter hoursAdapter;
    private StrericWheelAdapter minutesAdapter;

    private int currentDateType;
//    private int todayHour;
//    private int todayMinute;

    public DateStageSelectorWheelView(Context context, AttributeSet attrs,
                                      int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    public DateStageSelectorWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public DateStageSelectorWheelView(Context context) {
        super(context);
        initLayout(context);
    }

    private void initLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.dialogui_datepick_date_stage_selector_layout, this,
                true);
        rlTitle = (LinearLayout) findViewById(R.id.rl_date_time_title);
        lineL = findViewById(R.id.line_1);
        llWheelViews = (LinearLayout) findViewById(R.id.ll_wheel_views);

        tvSubTitleStart = (TextView) findViewById(R.id.tv_date_time_subtitle_start);
        tvSubTitleEnd = (TextView) findViewById(R.id.tv_date_time_subtitle_end);

        tvHourStart = (TextView) findViewById(R.id.tv_date_time_hour_start);
        tvMinuteStart = (TextView) findViewById(R.id.tv_date_time_minute_start);
        tvHourEnd = (TextView) findViewById(R.id.tv_date_time_hour_end);
        tvMinuteEnd = (TextView) findViewById(R.id.tv_date_time_minute_end);

        wvHourStart = (WheelView) findViewById(R.id.wv_date_of_hour_start);
        wvMinuteStart = (WheelView) findViewById(R.id.wv_date_of_minute_start);
        wvHourEnd = (WheelView) findViewById(R.id.wv_date_of_hour_end);
        wvMinuteEnd = (WheelView) findViewById(R.id.wv_date_of_minute_end);

        wvHourStart.addChangingListener(this);
        wvMinuteStart.addChangingListener(this);
        wvHourEnd.addChangingListener(this);
        wvMinuteEnd.addChangingListener(this);

        setData();
        setShowDate(0);
        setShowDateType(TYPE_HHMM);
    }

    private void setData() {
        /** 时初始化 */
        for (int i = 0; i < hours.length; i++) {
            if (i <= 9) {
                hours[i] = "0" + i + " 时";
            } else {
                hours[i] = i + " 时";
            }
        }
        /** 分初始化 */
        for (int i = 0; i < minutes.length; i++) {
            if (i <= 9) {
                minutes[i] = "0" + i + " 分";
            } else {
                minutes[i] = i + " 分";
            }
        }

        hoursAdapter = new StrericWheelAdapter(hours);
        minutesAdapter = new StrericWheelAdapter(minutes);


        wvHourStart.setAdapter(hoursAdapter);
        wvHourStart.setCyclic(true);
        wvMinuteStart.setAdapter(minutesAdapter);
        wvMinuteStart.setCyclic(true);

        wvHourEnd.setAdapter(hoursAdapter);
        wvHourEnd.setCyclic(true);
        wvMinuteEnd.setAdapter(minutesAdapter);
        wvMinuteEnd.setCyclic(true);
    }


    public void setShowDate(long date) {
        mDate = date;

        wvHourStart.setCurrentItem(getTodayHourStart());
        wvMinuteStart.setCurrentItem(getTodayMinuteStart());

        wvHourEnd.setCurrentItem(getTodayHourEnd());
        wvMinuteEnd.setCurrentItem(getTodayMinuteEnd());
    }

    /**
     * 设置显示的样式
     */
    public void setShowDateType(int type) {
        currentDateType = type;
        switch (type) {
            case TYPE_HHMM:
                wvHourStart.setVisibility(View.VISIBLE);
                wvMinuteStart.setVisibility(View.VISIBLE);
                wvHourStart.setStyle(18);
                wvMinuteStart.setStyle(18);

                wvHourEnd.setVisibility(View.VISIBLE);
                wvMinuteEnd.setVisibility(View.VISIBLE);
                wvHourEnd.setStyle(18);
                wvMinuteEnd.setStyle(18);
                break;
        }
    }


    /**
     * 设置标题的点击事件
     *
     * @param onClickListener
     */
    public void setTitleClick(OnClickListener onClickListener) {
        rlTitle.setOnClickListener(onClickListener);
    }

    /**
     * 设置日期选择器的日期转轮是否可见
     *
     * @param visibility
     */
    public void setDateSelectorVisiblility(int visibility) {
        lineL.setVisibility(visibility);
        llWheelViews.setVisibility(visibility);
    }

    public int getDateSelectorVisibility() {
        return llWheelViews.getVisibility();
    }

    public int getTitleId() {
        if (rlTitle != null) {
            return rlTitle.getId();
        }
        return 0;
    }

    public int getTodayHourStart() {
        int position = 0;
        String today = getToday();
        String hour = today.substring(12, 14);
        if (tvHourStart != null) {
            tvHourStart.setText(hour);
        }
        hour = hour + " 时";
        for (int i = 0; i < hours.length; i++) {
            if (hour.equals(hours[i])) {
                position = i;
                break;
            }
        }
        return position;
    }

    public int getTodayMinuteStart() {
        int position = 0;
        String today = getToday();
        String minute = today.substring(15, 17);
        if (tvMinuteStart != null) {
            tvMinuteStart.setText(minute);
        }
        minute = minute + " 分";
        for (int i = 0; i < minutes.length; i++) {
            if (minute.equals(minutes[i])) {
                position = i;
                break;
            }
        }
        return position;
    }

    public int getTodayHourEnd() {
        int position = 0;
        String today = getToday();
        String hour = today.substring(12, 14);
        if (tvHourEnd != null) {
            tvHourEnd.setText(hour);
        }
        hour = hour + " 时";
        for (int i = 0; i < hours.length; i++) {
            if (hour.equals(hours[i])) {
                position = i;
                break;
            }
        }
        return position;
    }

    public int getTodayMinuteEnd() {
        int position = 0;
        String today = getToday();
        String minute = today.substring(15, 17);
        if (tvMinuteEnd != null) {
            tvMinuteEnd.setText(minute);
        }
        minute = minute + " 分";
        for (int i = 0; i < minutes.length; i++) {
            if (minute.equals(minutes[i])) {
                position = i;
                break;
            }
        }
        return position;
    }

    /**
     * 获取今天的日期
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    private String getToday() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate;
        if (mDate > 0) {
            curDate = new Date(mDate);
        } else {
            curDate = new Date(System.currentTimeMillis());// 获取当前时间
        }
        String str = formatter.format(curDate);
        return str;
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        String trim = null;
        if (wheel.getId() == wvHourStart.getId()) {
            tvHourStart.setText((wvHourStart.getCurrentItemValue())
                    .trim().split(" ")[0]);
        } else if (wheel.getId() == wvMinuteStart.getId()) {
            tvMinuteStart.setText((wvMinuteStart.getCurrentItemValue())
                    .trim().split(" ")[0]);
        } else if (wheel.getId() == wvHourEnd.getId()) {
            tvHourEnd.setText((wvHourEnd.getCurrentItemValue())
                    .trim().split(" ")[0]);
        } else if (wheel.getId() == wvMinuteEnd.getId()) {
            tvMinuteEnd.setText((wvMinuteEnd.getCurrentItemValue())
                    .trim().split(" ")[0]);
        }
    }

    /**
     * 获取选择的日期的值
     *
     * @return
     */
    public String getSelectedDate() {
        switch (currentDateType) {
            case TYPE_HHMM:
                return tvHourStart.getText().toString().trim() + ":" + tvMinuteStart.getText().toString().trim()
                        + " - " + tvHourStart.getText().toString().trim() + ":" + tvMinuteStart.getText().toString().trim();
        }
        return tvHourStart.getText().toString().trim() + ":" + tvMinuteStart.getText().toString().trim()
                + " - " + tvHourEnd.getText().toString().trim() + ":" + tvMinuteEnd.getText().toString().trim();
    }
}
