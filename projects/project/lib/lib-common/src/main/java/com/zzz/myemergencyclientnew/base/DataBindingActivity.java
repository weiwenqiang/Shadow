package com.zzz.myemergencyclientnew.base;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class DataBindingActivity<B extends ViewDataBinding> extends AppCompatActivity {
    public AppCompatActivity mContext;
    public B mViewBinding;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //保持屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        View rootView = getLayoutInflater().inflate(this.getLayoutId(), null, false);
        mViewBinding = DataBindingUtil.bind(rootView);
        setContentView(rootView);
        mContext = this;
        initPresenter();
        initView();
    }

    protected abstract void initPresenter();

    public abstract int getLayoutId();

    public abstract void initView();
}
