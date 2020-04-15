package com.zzz.myemergencyclientnew.widget.combine;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zzz.myemergencyclientnew.common.R;
import com.zzz.myemergencyclientnew.listener.RegexCorrectListener;
import com.zzz.myemergencyclientnew.utils.RegexUtil;


/**
 * Created by wwq on 2017/7/2.
 */

public class ExtendEditView extends LinearLayout {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";

    LinearLayout lytRootExtend;
    ImageView imgExtendLeft;
    EditText edtEdit;
    ImageView imgExtendRight;
    ImageView imgExtendRequired;
    Button btnButton;

    private int lyt_root_bg;
    private int img_extend_left_normal;
    private int img_extend_left_pressed;
    private int img_extend_right_normal;
    private int img_extend_right_pressed;
    private String edt_hint;
    private String edt_inputType;
    private int edt_maxLength;
    private boolean edt_enabled;
    private String btn_text;
    private boolean btn_visibility;
    private boolean btn_enabled;
    private boolean img_required;

    //反转显示密码
    private boolean passShow = false;
    //监听正则判断是否成功
    private RegexCorrectListener listener;
    //监听正则判断是否成功
    private boolean isCorrect= false;
    //按钮点击监听
    private OnClickListener btnOnClick;

    public ExtendEditView(Context context) {
        this(context, null);
        initExtendEditView(context);
    }

    public ExtendEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.extendEditView);
        //提示文本
        edt_hint = typedArray.getString(R.styleable.extendEditView_edt_hint);
        //键盘类型
        edt_inputType = typedArray.getString(R.styleable.extendEditView_edt_inputType);
        //最大长度
        edt_maxLength = typedArray.getInt(R.styleable.extendEditView_edt_maxLength, 50);
        //是否可以编辑
        edt_enabled = typedArray.getBoolean(R.styleable.extendEditView_edt_enabled, true);
        //布局背景
        lyt_root_bg = typedArray.getInt(R.styleable.extendEditView_lyt_root_bg, R.drawable.line_extend_edit);
        //左图片
        img_extend_left_normal = typedArray.getResourceId(R.styleable.extendEditView_img_extend_left_normal, R.mipmap.ic_login_phone_normal);
        //左图片
        img_extend_left_pressed = typedArray.getResourceId(R.styleable.extendEditView_img_extend_left_pressed, R.mipmap.ic_login_phone_pressed);
        //右图片
        img_extend_right_normal = typedArray.getResourceId(R.styleable.extendEditView_img_extend_right_normal, R.mipmap.ic_login_correct_normal);
        //右图片
        img_extend_right_pressed = typedArray.getResourceId(R.styleable.extendEditView_img_extend_right_pressed, R.mipmap.ic_login_correct_pressed);

        //按钮文本
        btn_text = typedArray.getString(R.styleable.extendEditView_btn_text);
        //按钮默认不可见
        btn_visibility = typedArray.getBoolean(R.styleable.extendEditView_btn_visibility, false);
        //按钮默认可以点击
        btn_enabled = typedArray.getBoolean(R.styleable.extendEditView_btn_enabled, true);
        //是否必填
        img_required = typedArray.getBoolean(R.styleable.extendEditView_img_extend_required, false);

        initExtendEditView(context);
        typedArray.recycle();
    }

    public ExtendEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void initExtendEditView(Context context) {
        View view = View.inflate(context, R.layout.widget_editview_extend, this);

        lytRootExtend = view.findViewById(R.id.lyt_root_extend);
        imgExtendLeft = view.findViewById(R.id.img_extend_left);
        edtEdit = view.findViewById(R.id.edt_edit);
        imgExtendRight = view.findViewById(R.id.img_extend_right);
        imgExtendRequired = view.findViewById(R.id.img_extend_required);
        btnButton = view.findViewById(R.id.btn_button);

        //背景
        if(lyt_root_bg==0){
            lytRootExtend.setBackground(null);
        }else{
            lytRootExtend.setBackgroundResource(lyt_root_bg);
        }

        imgExtendLeft.setImageResource(img_extend_left_normal);
        imgExtendRight.setImageResource(img_extend_right_normal);

        btnButton.setOnClickListener(null);

        //提示文本
        edtEdit.setHint(edt_hint);
        //最大长度
        edtEdit.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(edt_maxLength)});
        //默认可以编辑
        edtEdit.setEnabled(edt_enabled);
        imgExtendRight.setVisibility(edt_enabled ? View.VISIBLE : View.GONE);

        if(img_required){
            imgExtendRequired.setVisibility(View.VISIBLE);
        }

        initEditListener();
        initBottom();
    }

    private void initEditListener() {
        switch (edt_inputType) {
            case "phone":
                edtEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "password":
                edtEdit.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imgExtendRight.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        passShow = !passShow;
                        if (passShow) {
                            edtEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//如果选中，显示密码
                            imgExtendRight.setImageResource(img_extend_right_pressed);
                        } else {
                            edtEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());//否则隐藏密码
                            imgExtendRight.setImageResource(img_extend_right_normal);
                        }
                        Selection.setSelection(edtEdit.getText(), edtEdit.getText().length());
                    }
                });
                break;
            case "code":
                edtEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "default":
                edtEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "carnumber":
                edtEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case "number":
                edtEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case "numberDecimal":
                ////InputType.TYPE_NUMBER_FLAG_DECIMAL 的代码是8192，而我们需要的是8194就是android:inputType="numberDecimal"，
                //            //但是没有这个常量，所以我们需要手动的输入数字
                edtEdit.setInputType(8194);
                break;
        }

        edtEdit.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imgExtendLeft.setImageResource(img_extend_left_pressed);
                } else {
                    imgExtendLeft.setImageResource(img_extend_left_normal);
                }
            }
        });

        edtEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(listener ==null){
                    listener = new RegexCorrectListener() {
                        @Override
                        public void isRegexCorrect(View view, boolean isRegexCorrect) {
                            isCorrect = isRegexCorrect;
                        }
                    };
                }
                switch (edt_inputType) {
                    case "phone":
                        if (RegexUtil.isMobileNO(s.toString())) {
                            imgExtendRight.setImageResource(img_extend_right_pressed);
                        } else {
                            imgExtendRight.setImageResource(img_extend_right_normal);
                        }
                        listener.isRegexCorrect(ExtendEditView.this, RegexUtil.isMobileNO(s.toString()));
                        break;
                    case "password":
                        listener.isRegexCorrect(ExtendEditView.this, RegexUtil.isSixNumber(s.toString()));
                        break;
                    case "code":
                        if (RegexUtil.isSixNumber(s.toString())) {
                            imgExtendRight.setImageResource(img_extend_right_pressed);
                        } else {
                            imgExtendRight.setImageResource(img_extend_right_normal);
                        }
                        listener.isRegexCorrect(ExtendEditView.this, RegexUtil.isSixNumber(s.toString()));
                        break;
                    case "default":
                        if (RegexUtil.isDefault(s.toString())) {
                            imgExtendRight.setImageResource(img_extend_right_pressed);
                        } else {
                            imgExtendRight.setImageResource(img_extend_right_normal);
                        }
                        listener.isRegexCorrect(ExtendEditView.this, RegexUtil.isDefault(s.toString()));
                        break;
                    case "number":
                        if (s.toString().length()>0) {
                            imgExtendRight.setImageResource(img_extend_right_pressed);
                        } else {
                            imgExtendRight.setImageResource(img_extend_right_normal);
                        }
                        listener.isRegexCorrect(ExtendEditView.this, s.toString().length()>0);
                        break;
                    case "numberDecimal":
                        if (s.toString().length()>0) {
                            imgExtendRight.setImageResource(img_extend_right_pressed);
                        } else {
                            imgExtendRight.setImageResource(img_extend_right_normal);
                        }
                        listener.isRegexCorrect(ExtendEditView.this,s.toString().length()>0);
                        break;
                    case "carnumber":
                        if (RegexUtil.isPlateNumber(s.toString())) {
                            imgExtendRight.setImageResource(img_extend_right_pressed);
                        } else {
                            imgExtendRight.setImageResource(img_extend_right_normal);
                        }
                        listener.isRegexCorrect(ExtendEditView.this, RegexUtil.isSixNumber(s.toString()));
                        break;
                }

                if(s.length()>0){
                    imgExtendRequired.setVisibility(View.GONE);
                } else if (img_required) {
                    imgExtendRequired.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initBottom(){
        btnButton.setText(btn_text);
        btnButton.setVisibility(btn_visibility ? View.VISIBLE : View.GONE);
        btnButton.setEnabled(btn_enabled);
        btnButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnOnClick!=null){
                    btnOnClick.onClick(view);
                }
            }
        });
    }

    public String getText(){
        return edtEdit.getText().toString().trim();
    }

    public void setRegexCorrectListener(RegexCorrectListener listener){
        this.listener = listener;
    }

    public void setBtnOnClick(OnClickListener btnOnClick) {
        this.btnOnClick = btnOnClick;
    }

    public Button getBtnButton() {
        return btnButton;
    }

    public EditText getEdtEdit() {
        return edtEdit;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
}
