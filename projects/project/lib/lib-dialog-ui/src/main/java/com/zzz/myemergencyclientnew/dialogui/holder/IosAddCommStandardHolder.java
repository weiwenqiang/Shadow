package com.zzz.myemergencyclientnew.dialogui.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zzz.myemergencyclientnew.dialogui.DialogUIUtils;
import com.zzz.myemergencyclientnew.dialogui.R;
import com.zzz.myemergencyclientnew.dialogui.bean.BuildBean;
import com.zzz.myemergencyclientnew.dialogui.utils.ToolUtils;

import java.util.Map;

/**
 * Created by 魏文强 on 2017/5/20.
 */

public class IosAddCommStandardHolder extends SuperHolder {
    protected TextView tvTitle;
    protected Button btn1;
    protected Button btn2;
    protected Button btn3;
    protected LinearLayout lyt_add_editview;
    protected ScrollView sv;

    public IosAddCommStandardHolder(Context context) {
        super(context);
    }

    @Override
    protected void findViews() {
        tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        btn1 = (Button) rootView.findViewById(R.id.btn_1);
        btn2 = (Button) rootView.findViewById(R.id.btn_2);
        btn3 = (Button) rootView.findViewById(R.id.btn_3);
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

        /**
         * 编辑商品时初始化属性列表
         */

        if(!TextUtils.isEmpty(bean.strArray)){
            for(String str : bean.strArray.toString().trim().split(",")){
                LinearLayout layout = initLinearLayout(context, bean);
//                editText.setText(str);
                lyt_add_editview.addView(layout);
            }
        }
        if(bean.map != null){
            for (Map.Entry<String, String> entry : bean.map.entrySet()) {
                LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.widget_view_standard, null);
                EditText editName = (EditText) layout.findViewById(R.id.edt_name);
                editName.setText(entry.getKey());
                EditText editMoney = (EditText) layout.findViewById(R.id.edt_money);
                editMoney.setText(entry.getValue());
                lyt_add_editview.addView(layout);
            }
        }else {
            lyt_add_editview.addView(initLinearLayout(context, bean));
        }

        //事件
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lyt_add_editview.getChildCount() < bean.addNumber){
                    lyt_add_editview.addView(initLinearLayout(context, bean));
                }else {
                    Toast.makeText(context, "无法添加更多", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lyt_add_editview.getChildCount() < 2){
                    DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
                }else{
                    lyt_add_editview.removeViewAt(lyt_add_editview.getChildCount()-1);
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer sb = new StringBuffer("");
                for(int i=0; i< lyt_add_editview.getChildCount(); i++){
                    LinearLayout layout = (LinearLayout) lyt_add_editview.getChildAt(i);
                    EditText editName = (EditText) layout.findViewById(R.id.edt_name);
                    String name = editName.getText().toString().trim();

                    EditText editMoney = (EditText) layout.findViewById(R.id.edt_money);
                    String money = editMoney.getText().toString().trim();

                    if(TextUtils.isEmpty(name) || TextUtils.isEmpty(money)){
                        Toast.makeText(context, "请完整填写属性", Toast.LENGTH_SHORT).show();
                        return;
                    } else if(name.indexOf(",")!= -1 || money.indexOf(",")!= -1){
                        Toast.makeText(context, "输入包含非法字符", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        sb.append(name+":"+money+",");
                    }
                }
                String text = TextUtils.isEmpty(sb.toString().trim()) ? "" : sb.substring(0, sb.length()-1);
                bean.addListener.onAddAttribute(text);
                Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
                DialogUIUtils.dismiss(bean.dialog, bean.alertDialog);
            }
        });

    }

    private LinearLayout initLinearLayout(Context context, final BuildBean bean){
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.widget_view_standard, null);
        EditText editName = (EditText) layout.findViewById(R.id.edt_name);
        editName.setHint(bean.hint1);

        EditText editMoney = (EditText) layout.findViewById(R.id.edt_money);
        editMoney.setHint(bean.hint2);

        return layout;
    }
}
