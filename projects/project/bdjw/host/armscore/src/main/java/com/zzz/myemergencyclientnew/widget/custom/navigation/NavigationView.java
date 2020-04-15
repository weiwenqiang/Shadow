package com.zzz.myemergencyclientnew.widget.custom.navigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.zzz.myemergencyclientnew.R;

public class NavigationView extends LinearLayout {
    public TabIndicatorView tabOne;
    TabIndicatorView tabTwo;
    TabIndicatorView tabThree;
    public TabIndicatorView tabFour;
    public TabIndicatorView tabFive;
    LinearLayout lytNavigation;

    private View view;

    private NavigationViewListener listener;

    public NavigationView(Context context) {
        super(context);
        initNavigationView(context);
    }

    public NavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initNavigationView(context);
    }

    public NavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initNavigationView(context);
    }

    public void initNavigationView(Context context) {
        view = View.inflate(context, R.layout.widget_navigation, this);
        tabOne = view.findViewById(R.id.tab_one);
        tabTwo = view.findViewById(R.id.tab_two);
        tabThree = view.findViewById(R.id.tab_three);
        tabFour = view.findViewById(R.id.tab_four);
        tabFive = view.findViewById(R.id.tab_five);
        lytNavigation = view.findViewById(R.id.lyt_navigation);

        tabOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFocus();
                tabOne.setCurrentFocus(true);
                listener.getNavigaIntex(0);
            }
        });
        tabTwo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFocus();
                tabTwo.setCurrentFocus(true);
                listener.getNavigaIntex(1);
            }
        });
        tabThree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFocus();
                tabThree.setCurrentFocus(true);
                listener.getNavigaIntex(2);
            }
        });
        tabFour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFocus();
                tabFour.setCurrentFocus(true);
                listener.getNavigaIntex(3);
            }
        });
        tabFive.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentFocus();
                tabFive.setCurrentFocus(true);
                listener.getNavigaIntex(4);
            }
        });
    }

    public void setListener(NavigationViewListener listener) {
        this.listener = listener;
    }

    public void setCurrentFocus(){
        tabOne.setCurrentFocus(false);
        tabTwo.setCurrentFocus(false);
        tabThree.setCurrentFocus(false);
        tabFour.setCurrentFocus(false);
        tabFive.setCurrentFocus(false);
    }
}
