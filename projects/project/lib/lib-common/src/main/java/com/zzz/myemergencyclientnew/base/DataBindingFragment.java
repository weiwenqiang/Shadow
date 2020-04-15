package com.zzz.myemergencyclientnew.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class DataBindingFragment<B extends ViewDataBinding> extends Fragment {
    public AppCompatActivity mActivity;
    public B mViewBinding;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected View mRootView;
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mViewBinding = DataBindingUtil.bind(view);
        mActivity= (AppCompatActivity) getActivity();
        mRootView = mViewBinding.getRoot();
        initPresenter();
        initView();
        return mRootView;
    }

    protected abstract void initPresenter();

    public abstract int getLayoutId();

    public abstract void initView();
}
