package com.zzz.myemergencyclientnew.widget.custom.dynamicnavigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.zzz.myemergencyclientnew.R;

import java.util.ArrayList;
import java.util.List;

public class NavigationBottomView extends LinearLayout {
    private View view;
    private Context context;

    private LinearLayout lyt_navigation;

    private List<TabUnitView> list = new ArrayList<>();

    public NavigationBottomView(Context context) {
        this(context, null);
    }

    public NavigationBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        this.context = context;
        view = View.inflate(context, R.layout.widget_navigation_bottom, this);

        lyt_navigation = view.findViewById(R.id.lyt_navigation);
    }

//    public void addTabUnitView(TabUnitView view){
//        list.add(view);
//    }

    public void initTabUnitList(List<TabUnitView> tabUnitViews){
        lyt_navigation.removeAllViews();
        list = tabUnitViews;
        for(int i=0; i<list.size(); i++){
            lyt_navigation.addView(list.get(i));
        }
    }

    public void setChecked(int index){
        for(int i=0; i<list.size(); i++){
            TabUnitView tab = list.get(i);
            tab.setNormal();
        }
        list.get(index).setChecked();
    }
}
