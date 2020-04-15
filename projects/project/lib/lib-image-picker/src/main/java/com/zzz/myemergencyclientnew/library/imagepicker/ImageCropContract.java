package com.zzz.myemergencyclientnew.library.imagepicker;


import com.zzz.myemergencyclientnew.base.BasePresenter;
import com.zzz.myemergencyclientnew.base.BaseView;

public interface ImageCropContract {
    interface View extends BaseView {
    }

    abstract class Presenter extends BasePresenter<View> {
    }
}