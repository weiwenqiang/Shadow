package com.zzz.myemergencyclientnew.widget.combine;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzz.myemergencyclientnew.common.R;

/**
 * Created by wwq on 2017/7/2.
 */

public class ToolbarView extends RelativeLayout {
    public ImageView imgToolbarBack;
    TextView txtToolbarTitle;
    RelativeLayout lytToolbarMenu;
    RelativeLayout lytToolbar;
//    RelativeLayout lytToolbarMenuEnd;

    View view;

    //返回按钮点击监听
    private OnClickListener backOnClick;
    //菜单按钮点击监听
    private OnClickListener menuOnClick;
    //菜单按钮点击监听
//    private OnClickListener menuEndOnClick;

    public void setTxtTitle(String txtTitle) {
        if(TextUtils.isEmpty(txtTitle)){
            lytToolbar.setVisibility(View.GONE);
        }else{
            lytToolbar.setVisibility(View.VISIBLE);
            txtToolbarTitle.setText(txtTitle);
        }
    }

    public void setBackOnClick(OnClickListener backOnClick) {
        this.backOnClick = backOnClick;
    }

    public void setMenuViewClick(View view, OnClickListener menuOnClick) {
        lytToolbarMenu.removeAllViews();
        lytToolbarMenu.addView(view);
        this.menuOnClick = menuOnClick;
    }
//    public void setMenuEndViewClick(View view, OnClickListener menuOnClick) {
//        lytToolbarMenuEnd.removeAllViews();
//        lytToolbarMenuEnd.addView(view);
//        this.menuEndOnClick = menuOnClick;
//    }

    public ToolbarView(Context context) {
        this(context, null);
    }

    public ToolbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initToolbarView(context);
    }

    public ToolbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initToolbarView(Context context) {
        view= View.inflate(context, R.layout.widget_toolbar_view, this);
        imgToolbarBack = view.findViewById(R.id.img_toolbar_back);
        txtToolbarTitle = view.findViewById(R.id.txt_toolbar_title);
        lytToolbarMenu = view.findViewById(R.id.lyt_toolbar_menu);
//        lytToolbarMenuEnd = view.findViewById(R.id.lyt_toolbar_menu_end);
        lytToolbar = view.findViewById(R.id.lyt_toolbar);
        lytToolbarMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuOnClick!=null){
                    menuOnClick.onClick(view);
                }
            }
        });
//        lytToolbarMenuEnd.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(menuEndOnClick!=null){
//                    menuEndOnClick.onClick(view);
//                }
//            }
//        });
        imgToolbarBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(backOnClick!=null){
                    backOnClick.onClick(view);
                }
            }
        });
    }

    public RelativeLayout getLytToolbarMenu() {
        return lytToolbarMenu;
    }
}
