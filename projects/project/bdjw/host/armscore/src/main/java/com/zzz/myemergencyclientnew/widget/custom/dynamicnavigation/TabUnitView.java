package com.zzz.myemergencyclientnew.widget.custom.dynamicnavigation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zzz.myemergencyclientnew.constant.pref.Api;
import com.bumptech.glide.Glide;
import com.zzz.myemergencyclientnew.R;
import com.zzz.myemergencyclientnew.entity.ApkFunctionEntity;
import com.zzz.myemergencyclientnew.entity.HomeBottomNavigationEntity;
import com.zzz.myemergencyclientnew.entity.NavigationEntity;

public class TabUnitView extends LinearLayout {
    private View view;
    private Context context;

    private ImageView img_icon;
    private TextView txt_hint;

//    private ApkFunctionEntity tabEntity;
    private NavigationEntity tabEntity;

    public TabUnitView(Context context) {
        this(context, null);
    }

    public TabUnitView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        this.context = context;
        view = View.inflate(context, R.layout.widget_tab_unit, this);

        img_icon = view.findViewById(R.id.img_icon);
        txt_hint = view.findViewById(R.id.txt_hint);
    }

    public void setDate(HomeBottomNavigationEntity entity){
        Glide.with(context)
                .load(entity.icon_normal)
                .placeholder(R.mipmap.default_head_photo)
                .into(img_icon);
        txt_hint.setText(entity.title);
    }

    public void setDate(ApkFunctionEntity entity){
//        tabEntity = entity;
        Glide.with(context)
                .load(tabEntity.iconNormal)
                .placeholder(R.mipmap.default_head_photo)
                .into(img_icon);
        txt_hint.setText(tabEntity.title);
    }
    public void setDate(NavigationEntity entity){
        tabEntity = entity;
        Glide.with(context)
                .load(tabEntity.iconNormal)
                .placeholder(R.mipmap.default_head_photo)
                .into(img_icon);
        txt_hint.setText(tabEntity.title);
    }

    public void setNormal(){
        txt_hint.setTextColor(context.getResources().getColor(R.color.text_black));
        Glide.with(context)
                .load(tabEntity.iconNormal)
                .placeholder(R.mipmap.default_head_photo)
                .into(img_icon);
    }

    public void setChecked(){
        txt_hint.setTextColor(context.getResources().getColor(R.color.main_color));
        Glide.with(context)
                .load(tabEntity.iconChecked)
                .placeholder(R.mipmap.default_head_photo)
                .into(img_icon);
    }
}
