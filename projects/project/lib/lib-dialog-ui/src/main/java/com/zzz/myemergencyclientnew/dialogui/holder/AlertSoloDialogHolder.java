package com.zzz.myemergencyclientnew.dialogui.holder;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzz.myemergencyclientnew.dialogui.DialogUIUtils;
import com.zzz.myemergencyclientnew.dialogui.R;
import com.zzz.myemergencyclientnew.dialogui.bean.BuildBean;
import com.zzz.myemergencyclientnew.dialogui.utils.ToolUtils;

/**
 * ========================================
 * <p>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p>
 * 作 者：陈冠明
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2016/11/22 23:05
 * <p>
 * 描 述：复用的一个holder，alert弹出框
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class AlertSoloDialogHolder extends SuperHolder {
    protected TextView tvTitle;
    public TextView tvMsg;
    public EditText et1;
    protected View line;
    protected Button btn1;
    protected View lineBtn2;
    protected Button btn2;

    public AlertSoloDialogHolder(Context context) {
        super(context);
    }

    @Override
    protected void findViews() {
        tvTitle = (TextView) rootView.findViewById(R.id.dialogui_tv_title);
        tvMsg = (TextView) rootView.findViewById(R.id.dialogui_tv_msg);
        et1 = (EditText) rootView.findViewById(R.id.et_1);
        line = (View) rootView.findViewById(R.id.line);
        btn1 = (Button) rootView.findViewById(R.id.btn_1);
        lineBtn2 = (View) rootView.findViewById(R.id.line_btn2);
        btn2 = (Button) rootView.findViewById(R.id.btn_2);
    }

    @Override
    protected int setLayoutRes() {
        return R.layout.dialogui_holder_alert_solo;
    }

    @Override
    public void assingDatasAndEvents(Context context, final BuildBean bean) {

        //style
        tvMsg.setTextColor(ToolUtils.getColor(tvMsg.getContext(), bean.msgTxtColor));
        tvMsg.setTextSize(bean.msgTxtSize);

        tvTitle.setTextColor(ToolUtils.getColor(tvTitle.getContext(), bean.titleTxtColor));
        tvTitle.setTextSize(bean.titleTxtSize);

        btn2.setTextSize(bean.btnTxtSize);
        btn1.setTextSize(bean.btnTxtSize);

        btn1.setTextColor(ToolUtils.getColor(btn1.getContext(), bean.btn1Color));
        btn2.setTextColor(ToolUtils.getColor(btn1.getContext(), bean.btn2Color));

        if (TextUtils.isEmpty(bean.title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(bean.title);
        }

        if (TextUtils.isEmpty(bean.msg)) {
            tvMsg.setVisibility(View.GONE);
        } else {
            tvMsg.setVisibility(View.VISIBLE);
            tvMsg.setText(bean.msg);

            tvMsg.setTextColor(ToolUtils.getColor(tvMsg.getContext(), bean.msgTxtColor));
            tvMsg.setTextSize(bean.msgTxtSize);
        }

        if (TextUtils.isEmpty(bean.hint1)) {
            et1.setVisibility(View.GONE);
        } else {
            et1.setVisibility(View.VISIBLE);
            et1.setHint(bean.hint1);

            et1.setTextColor(ToolUtils.getColor(et1.getContext(), bean.inputTxtColor));
            et1.setTextSize(bean.inputTxtSize);
        }

        //按钮数量

        if (TextUtils.isEmpty(bean.text3)) {
            btn2.setBackgroundResource(R.drawable.dialogui_selector_right_bottom);
        }

        if (TextUtils.isEmpty(bean.text2)) {
            btn2.setVisibility(View.GONE);
            lineBtn2.setVisibility(View.GONE);
            btn1.setBackgroundResource(R.drawable.dialogui_selector_right_bottom);
        } else {
            btn2.setVisibility(View.VISIBLE);
            lineBtn2.setVisibility(View.VISIBLE);
            btn2.setText(bean.text2);
        }
        if (TextUtils.isEmpty(bean.text1)) {
            line.setVisibility(View.GONE);
        } else {
            btn1.setText(bean.text1);
        }
        //事件
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
                bean.soloListener.onGetInput(et1.getText().toString());
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
                bean.soloListener.onCancle();
            }
        });
    }
}
