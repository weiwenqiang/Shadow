package com.zzz.myemergencyclientnew.widget.plug;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by 魏文强 on 2017/10/23 0023.
 */

public class ControlViewPager extends ViewPager {
    private boolean isScroll = false;

    public ControlViewPager(Context context) {
        super(context);
    }

    public ControlViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * @param isScroll 是否滑动（true 滑动，false 禁止）
     */
    public void setScroll(boolean isScroll) {
        this.isScroll = isScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {
            return false;
        }
    }

    /**
     * 去除滑动对话
     */
    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }
}
