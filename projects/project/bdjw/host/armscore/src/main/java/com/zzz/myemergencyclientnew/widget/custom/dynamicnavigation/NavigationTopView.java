package com.zzz.myemergencyclientnew.widget.custom.dynamicnavigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zzz.myemergencyclientnew.R;

import java.util.ArrayList;
import java.util.List;

public class NavigationTopView extends LinearLayout {
    private View view;
    private Context context;

//    private LinearLayout lyt_navigation;
    private TextView txt_title;
    private LinearLayout lyt_function;

    private List<TabUnitView> list = new ArrayList<>();

    public NavigationTopView(Context context) {
        this(context, null);
    }

    public NavigationTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        this.context = context;
        view = View.inflate(context, R.layout.widget_navigation_top, this);

//        lyt_navigation = view.findViewById(R.id.lyt_navigation);
        txt_title = view.findViewById(R.id.txt_title);
        lyt_function = view.findViewById(R.id.lyt_function);
    }

    public void addTabUnitView(TabUnitView view){
        list.add(view);
    }

    public void initTabUnitList(String title, List<TabUnitView> tabUnitViews){
        txt_title.setText(title);
        lyt_function.removeAllViews();
        list = tabUnitViews;
        for(int i=0; i<tabUnitViews.size(); i++){
            lyt_function.addView(tabUnitViews.get(i));
        }
    }
}
