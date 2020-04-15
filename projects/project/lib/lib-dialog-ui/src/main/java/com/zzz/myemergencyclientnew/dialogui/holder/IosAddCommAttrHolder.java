package com.zzz.myemergencyclientnew.dialogui.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzz.myemergencyclientnew.dialogui.DialogUIUtils;
import com.zzz.myemergencyclientnew.dialogui.R;
import com.zzz.myemergencyclientnew.dialogui.bean.BuildBean;
import com.zzz.myemergencyclientnew.dialogui.config.DialogConfig;
import com.zzz.myemergencyclientnew.dialogui.utils.ToolUtils;


/**
 * Created by 魏文强 on 2017/5/20.
 */

public class IosAddCommAttrHolder extends SuperHolder {
    protected TextView tvTitle;
    protected Button btn1;
    protected Button btn2;
    protected Button btn3;
    protected Button btn4;
    protected View v2;
    protected View v3;
    protected View v4;
    protected LinearLayout lyt_add_editview;
    protected ScrollView sv;

    public IosAddCommAttrHolder(Context context) {
        super(context);
    }

    @Override
    protected void findViews() {
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        btn1 = (Button) rootView.findViewById(R.id.btn_1);
        btn2 = (Button) rootView.findViewById(R.id.btn_2);
        btn3 = (Button) rootView.findViewById(R.id.btn_3);
        btn4 = (Button) rootView.findViewById(R.id.btn_4);
        v2 = rootView.findViewById(R.id.line_btn2);
        v3 = rootView.findViewById(R.id.line_btn3);
        v4 = rootView.findViewById(R.id.line_btn4);
        lyt_add_editview = (LinearLayout) rootView.findViewById(R.id.lyt_add_editview);
        sv = (ScrollView) rootView.findViewById(R.id.sv);

    }

    @Override
    protected int setLayoutRes() {
        return R.layout.dialog_ios_add_attr;
    }

    @Override
    public void assingDatasAndEvents(final Context context, final BuildBean bean) {
        btn3.setTextSize(bean.btnTxtSize);
        btn2.setTextSize(bean.btnTxtSize);
        btn1.setTextSize(bean.btnTxtSize);

        btn1.setTextColor(ToolUtils.getColor(btn1.getContext(), bean.btn1Color));
        btn2.setTextColor(ToolUtils.getColor(btn1.getContext(), bean.btn2Color));
        btn3.setTextColor(ToolUtils.getColor(btn1.getContext(), bean.btn3Color));

        if (TextUtils.isEmpty(bean.title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(bean.title);
            tvTitle.setTextColor(ToolUtils.getColor(tvTitle.getContext(), bean.titleTxtColor));
            tvTitle.setTextSize(bean.titleTxtSize);
        }

        if(TextUtils.isEmpty(bean.text3)){
            btn3.setVisibility(View.GONE);
            v4.setVisibility(View.GONE);
        }else{
            btn3.setVisibility(View.VISIBLE);
            v4.setVisibility(View.VISIBLE);
            btn3.setText(bean.text3);
        }

        if(bean.type == DialogConfig.TYPE_IOS_ATTRIBUTE_SINGLE){
            btn1.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            v3.setVisibility(View.GONE);
        }else{
            btn1.setVisibility(View.VISIBLE);
            v2.setVisibility(View.VISIBLE);
            btn1.setText(bean.text1);
            btn2.setVisibility(View.VISIBLE);
            v3.setVisibility(View.VISIBLE);
            btn2.setText(bean.text2);
        }

        /**
         * 编辑商品时初始化属性列表
         */
        if (!TextUtils.isEmpty(bean.strArray)) {
            for (String str : bean.strArray.toString().trim().split(",")) {
                EditText editText = initEditText(context, bean);
                editText.setText(str);
                lyt_add_editview.addView(editText);
            }
        } else {
            lyt_add_editview.addView(initEditText(context, bean));
        }

        //事件
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lyt_add_editview.getChildCount() < bean.addNumber) {
                    lyt_add_editview.addView(initEditText(context, bean));
                } else {
                    Toast.makeText(context, "无法添加更多", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lyt_add_editview.getChildCount() < 2) {
                    DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
                } else {
                    lyt_add_editview.removeViewAt(lyt_add_editview.getChildCount() - 1);
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer sb = new StringBuffer("");
                for (int i = 0; i < lyt_add_editview.getChildCount(); i++) {
                    EditText edt = (EditText) lyt_add_editview.getChildAt(i);
                    String edtStr = edt.getText().toString().trim();
                    if (TextUtils.isEmpty(edtStr)) {
                        Toast.makeText(context, "请完整填写属性", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (edtStr.indexOf(",") != -1) {
                        Toast.makeText(context, "输入包含非法字符", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        sb.append(edt.getText().toString().trim() + ",");
                    }
                }
                String text = TextUtils.isEmpty(sb.toString().trim()) ? "" : sb.substring(0, sb.length() - 1);
                bean.addListener.onAddAttribute(text);
                DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
            }
        });

    }

    private EditText initEditText(Context context, final BuildBean bean) {
        EditText editText = (EditText) LayoutInflater.from(context).inflate(R.layout.widget_view_edittext, null);
        final float scale = context.getResources().getDisplayMetrics().density;
        editText.setHint(bean.hint1);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) (35 * scale + 0.5f));
        params.setMargins(20, 5, 20, 15);
        editText.setPadding(5, 0, 5, 0);
        editText.setLayoutParams(params);
        return editText;
    }
}
