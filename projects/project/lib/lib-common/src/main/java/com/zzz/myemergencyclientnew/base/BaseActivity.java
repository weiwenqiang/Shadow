package com.zzz.myemergencyclientnew.base;

import androidx.databinding.ViewDataBinding;

import java.lang.reflect.ParameterizedType;

public abstract class BaseActivity<P extends BasePresenter, B extends ViewDataBinding> extends DataBindingActivity<B> {
    public P mPresenter;

    @Override
    protected void initPresenter() {
        if (this instanceof BaseView &&
                this.getClass().getGenericSuperclass() instanceof ParameterizedType &&
                ((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments().length > 0) {
            Class mPresenterClass = (Class) ((ParameterizedType) (this.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[0];
            mPresenter = InstanceFactory.getInstance(mPresenterClass);
            if (mPresenter != null) mPresenter.setView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDetached();
    }
}
