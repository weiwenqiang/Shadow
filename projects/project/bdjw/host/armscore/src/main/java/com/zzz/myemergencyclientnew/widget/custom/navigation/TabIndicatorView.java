package com.zzz.myemergencyclientnew.widget.custom.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzz.myemergencyclientnew.R;

/**
 * Created by 魏文强 on 2016/11/28.
 */
public class TabIndicatorView extends RelativeLayout {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";

    private ImageView ivTabIcon;
    private TextView tvTabHint;
    private TextView tvTabUnRead;
    private RelativeLayout lytTabIndicator;

    private int focusId = -1, normalId = -1;

    private int unreadCount = 0;

    private boolean isClip;

    private int type;
    //按钮点击监听
    private OnClickListener btnOnClick;

    public TabIndicatorView(Context context, boolean isClip) {
        this(context, null);
        this.isClip = isClip;
    }

    public TabIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.tabIndicatorView);
        //提示文本
        type = typedArray.getInt(R.styleable.tabIndicatorView_type, -1);
        if(isClip){
            View.inflate(context, R.layout.tab_indicator_clip, this);
        }else{
            View.inflate(context, R.layout.tab_indicator, this);
        }
        ivTabIcon = (ImageView) findViewById(R.id.tab_indicator_icon);
        tvTabHint = (TextView) findViewById(R.id.tab_indicator_hint);
        tvTabUnRead = (TextView) findViewById(R.id.tab_indicator_unread);
        lytTabIndicator = (RelativeLayout) findViewById(R.id.lyt_tab_indicator);

        setUnread(0);

        initTabIndicatorView();
        typedArray.recycle();
    }

    private void initTabIndicatorView(){
        switch (type){
            case 1:
                setTabIcon(R.drawable.tabbar_icon_index_normal,
                        R.drawable.tabbar_icon_index_down_pink);
                setTabHint(R.string.home_tab_index);
                break;
            case 2:
                setTabIcon(R.drawable.tabbar_icon_adde_normal,
                        R.drawable.tabbar_icon_adde_down_pink);
                setTabHint(R.string.home_tab_adde);
                break;
            case 3:
                setTabIcon(R.drawable.tabbar_icon_cargo_normal,
                        R.drawable.tabbar_icon_cargo_normal);
                setTabHint(R.string.home_tab_cargo);
                break;
            case 4:
                setTabIcon(R.drawable.tabbar_icon_money_normal,
                        R.drawable.tabbar_icon_money_down_pink);
                setTabHint(R.string.home_tab_money);
                break;
            case 5:
                setTabIcon(R.drawable.tabbar_icon_personal_normal,
                        R.drawable.tabbar_icon_personal_down_pink);
                setTabHint(R.string.home_tab_personal);
                break;
        }
        setCurrentFocus(false);
    }

    public void setTabIcon(int normalId, int focusId) {
        this.normalId = normalId;
        this.focusId = focusId;
    }

    public void setTabHint(int hintId) {
        tvTabHint.setText(hintId);
    }

    public void setUnread(int unreadCount) {
        this.unreadCount = unreadCount;

        if (unreadCount <= 0) {
            tvTabUnRead.setVisibility(View.GONE);
        } else {
            if (unreadCount >= 100) {
                tvTabUnRead.setText("99+");
            } else {
                tvTabUnRead.setText("" + unreadCount);
            }
            tvTabUnRead.setVisibility(View.VISIBLE);
        }
//        if(isClip){
//            LayoutParams params = new LayoutParams(100, 100);
//            lytTabIndicator.setLayoutParams(params);
//            ivTabIcon.setLayoutParams(new LayoutParams(60, 60));
//        }
    }

    public int getUnreadCount() {
        return this.unreadCount;
    }

    public void setCurrentFocus(boolean current) {
        if (current) {
            if (focusId != -1) {
                ivTabIcon.setImageResource(focusId);
                tvTabHint.setTextColor(getResources().getColor(R.color.main_color));
            }
        } else {
            if (normalId != -1) {
                ivTabIcon.setImageResource(normalId);
                tvTabHint.setTextColor(getResources().getColor(R.color.gray2));
            }
        }
    }

    /**
     * 是否修剪，如果需要图标突出显示，设置此方法
     */
    public void setIsClip(int width, int height){
        LayoutParams params = new LayoutParams(width, height);
        lytTabIndicator.setLayoutParams(params);

    }
}

