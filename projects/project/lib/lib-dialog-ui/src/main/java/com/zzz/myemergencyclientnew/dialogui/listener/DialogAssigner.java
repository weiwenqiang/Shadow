package com.zzz.myemergencyclientnew.dialogui.listener;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.zzz.myemergencyclientnew.dialogui.bean.BuildBean;
import com.zzz.myemergencyclientnew.dialogui.bean.TieBean;
import com.zzz.myemergencyclientnew.dialogui.config.DialogConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/9 0009.
 */
public class DialogAssigner implements Assignable {

    private static DialogAssigner instance;

    private DialogAssigner() {

    }

    public static DialogAssigner getInstance() {
        if (instance == null) {
            instance = new DialogAssigner();
        }
        return instance;
    }


    @Override
    public BuildBean assignDatePick(Context context,int gravity, String dateTitle, long date, int dateType, int tag, DialogUIDateTimeSaveListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.dateTitle = dateTitle;
        bean.gravity = gravity;
        bean.cancelable = true;
        bean.outsideTouchable = true;
        bean.type = DialogConfig.TYPE_DATEPICK;
        bean.dateTimeListener = listener;
        bean.dateType = dateType;
        bean.date = date;
        bean.tag = tag;
        return bean;
    }

    @Override
    public BuildBean assignDatePickStage(Context context,int gravity, String dateTitle, long date, int dateType, int tag, DialogUIDateTimeSaveListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.dateTitle = dateTitle;
        bean.gravity = gravity;
        bean.cancelable = true;
        bean.outsideTouchable = true;
        bean.type = DialogConfig.TYPE_DATEPICK_STAGE;
        bean.dateTimeListener = listener;
        bean.dateType = dateType;
        bean.date = date;
        bean.tag = tag;
        return bean;
    }

    @Override
    public BuildBean assignLoading(Context context, CharSequence msg,boolean isVertical, boolean cancleable, boolean outsideTouchable, boolean isWhiteBg) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.msg = msg;
        bean.isVertical = isVertical;
        bean.isWhiteBg = isWhiteBg;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_LOADING;
        return bean;
    }

    @Override
    public BuildBean assignMdLoading(Context context, CharSequence msg,boolean isVertical, boolean cancleable, boolean outsideTouchable, boolean isWhiteBg) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.msg = msg;
        bean.isVertical = isVertical;
        bean.isWhiteBg = isWhiteBg;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_MD_LOADING;
        return bean;
    }

    @Override
    public BuildBean assignMdAlert(Activity activity, CharSequence title, CharSequence msg, boolean cancleable, boolean outsideTouchable, DialogUIListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = activity;
        bean.msg = msg;
        bean.title = title;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.listener = listener;
        bean.type = DialogConfig.TYPE_MD_ALERT;
        bean.btn1Color = DialogConfig.mdBtnColor;
        bean.btn2Color = DialogConfig.mdBtnColor;
        bean.btn3Color = DialogConfig.mdBtnColor;
        return bean;
    }

    @Override
    public BuildBean assignSingleChoose(Activity context, CharSequence title, int defaultChosen, CharSequence[] words, boolean cancleable, boolean outsideTouchable, DialogUIItemListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.title = title;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.itemListener = listener;
        bean.wordsMd = words;
        bean.type = DialogConfig.TYPE_SINGLE_CHOOSE;
        bean.defaultChosen = defaultChosen;
        bean.btn1Color = DialogConfig.mdBtnColor;
        bean.btn2Color = DialogConfig.mdBtnColor;
        bean.btn3Color = DialogConfig.mdBtnColor;
        return bean;
    }

    @Override
    public BuildBean assignMdMultiChoose(Activity context, CharSequence title, CharSequence[] words, boolean[] checkedItems, boolean cancleable, boolean outsideTouchable, DialogUIListener btnListener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.msg = title;
        bean.title = title;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.listener = btnListener;
        bean.wordsMd = words;
        bean.checkedItems = checkedItems;
        bean.type = DialogConfig.TYPE_MD_MULTI_CHOOSE;
        bean.btn1Color = DialogConfig.mdBtnColor;
        bean.btn2Color = DialogConfig.mdBtnColor;
        bean.btn3Color = DialogConfig.mdBtnColor;
        return bean;
    }

    @Override
    public BuildBean assignMdMultiChooseAdd(Activity context, CharSequence title, CharSequence btn3, CharSequence[] words, boolean[] checkedItems, boolean cancleable, boolean outsideTouchable, DialogUIListener btnListener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.msg = title;
        bean.title = title;
        bean.text3 = btn3;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.listener = btnListener;
        bean.wordsMd = words;
        bean.checkedItems = checkedItems;
        bean.type = DialogConfig.TYPE_MD_MULTI_CHOOSE_ADD;
        bean.btn1Color = DialogConfig.mdBtnColor;
        bean.btn2Color = DialogConfig.mdBtnColor;
        bean.btn3Color = DialogConfig.mdBtnColor;
        return bean;
    }

    @Override
    public BuildBean assignMdMultiChooseWorkday(Activity context, CharSequence title, CharSequence[] words, boolean[] checkedItems, boolean cancleable, boolean outsideTouchable, DialogUIListener btnListener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.msg = title;
        bean.title = title;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.listener = btnListener;
        bean.wordsMd = words;
        bean.checkedItems = checkedItems;
        bean.type = DialogConfig.TYPE_MD_MULTI_CHOOSE_WORKDAY;
        bean.btn1Color = DialogConfig.mdBtnColor;
        bean.btn2Color = DialogConfig.mdBtnColor;
        bean.btn3Color = DialogConfig.mdBtnColor;
        return bean;
    }

    @Override
    public BuildBean assignAlert(Context activity, CharSequence title, CharSequence msg, CharSequence hint1, CharSequence hint2,
                                 CharSequence firstTxt, CharSequence secondTxt, boolean isVertical, boolean cancleable, boolean outsideTouchable, DialogUIListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = activity;
        bean.msg = msg;
        bean.title = title;
        bean.hint1 = hint1;
        bean.hint2 = hint2;
        bean.text1 = firstTxt;
        bean.text2 = secondTxt;
        bean.isVertical = isVertical;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.listener = listener;
        bean.type = DialogConfig.TYPE_ALERT;
        return bean;
    }

    @Override
    public BuildBean assignSoloAlert(Context activity, CharSequence title, CharSequence msg, CharSequence hint1,
                                 CharSequence firstTxt, CharSequence secondTxt,  boolean cancleable, boolean outsideTouchable, DialogUISoloListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = activity;
        bean.msg = msg;
        bean.title = title;
        bean.hint1 = hint1;
        bean.text1 = firstTxt;
        bean.text2 = secondTxt;
        bean.gravity = Gravity.CENTER;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.soloListener = listener;
        bean.type = DialogConfig.TYPE_ALERT_SOLO;
        return bean;
    }

    @Override
    public BuildBean assignCustomAlert(Context context, View contentView, int gravity, boolean cancleable, boolean outsideTouchable) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.customView = contentView;
        bean.gravity = gravity;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_CUSTOM_ALERT;
        return bean;
    }

    @Override
    public BuildBean assignCustomBottomAlert(Context context, View contentView, boolean cancleable, boolean outsideTouchable) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.customView = contentView;
        bean.gravity = Gravity.BOTTOM;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_CUSTOM_BOTTOM_ALERT;
        return bean;
    }




    @Override
    public BuildBean assignSheet(Context context, List<TieBean> datas,CharSequence bottomTxt,int gravity, boolean cancleable, boolean outsideTouchable, DialogUIItemListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.itemListener = listener;
        bean.mLists = datas;
        bean.bottomTxt = bottomTxt;
        bean.gravity = gravity;
        bean.isVertical = true;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.type = DialogConfig.TYPE_SHEET;

        return bean;
    }

    @Override
    public BuildBean assignMdBottomSheet(Context context,boolean isVertical, CharSequence title, List<TieBean> datas, int columnsNum, boolean cancleable, boolean outsideTouchable, DialogUIItemListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.title = title;
        bean.isVertical = isVertical;
        bean.mLists = datas;
        bean.bottomTxt = "";
        bean.gravity = Gravity.BOTTOM;
        bean.cancelable = cancleable;
        bean.outsideTouchable = outsideTouchable;
        bean.itemListener = listener;
        bean.gridColumns = columnsNum;
        bean.type = DialogConfig.TYPE_BOTTOM_SHEET;
        return bean;
    }

    @Override
    public BuildBean addCommodityAttribute(Context context, CharSequence title, CharSequence hint1, int addNumber, CharSequence strArray, CharSequence btn1, CharSequence btn2, CharSequence btn3, DialogUIAddListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.addListener = listener;
        bean.title = title;
        bean.hint1 = hint1;
        bean.text1 = btn1;
        bean.text2 = btn2;
        bean.text3 = btn3;
        bean.addNumber = addNumber;
        bean.strArray = strArray;
        bean.type = DialogConfig.TYPE_IOS_ATTRIBUTE;
        return bean;
    }

    @Override
    public BuildBean addCommodityStandard(Context context, CharSequence title, CharSequence hint1, CharSequence hint2, int addNumber, Map<String, String> map, CharSequence btn1, CharSequence btn2, CharSequence btn3, DialogUIAddListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.addListener = listener;
        bean.title = title;
        bean.hint1 = hint1;
        bean.hint2 = hint2;
        bean.text1 = btn1;
        bean.text2 = btn2;
        bean.text3 = btn3;
        bean.addNumber = addNumber;
        bean.map = map;
        bean.type = DialogConfig.TYPE_IOS_STANDARD;
        return bean;
    }

    @Override
    public BuildBean addCommodityAttributeSingle(Context context, CharSequence title, CharSequence hint1,CharSequence btn3, CharSequence btn4, DialogUIAddListener listener) {
        BuildBean bean = new BuildBean();
        bean.mContext = context;
        bean.addListener = listener;
        bean.title = title;
        bean.hint1 = hint1;
        bean.text3 = btn3;
        bean.text4 = btn4;
        bean.type = DialogConfig.TYPE_IOS_ATTRIBUTE_SINGLE;
        return bean;
    }
}
