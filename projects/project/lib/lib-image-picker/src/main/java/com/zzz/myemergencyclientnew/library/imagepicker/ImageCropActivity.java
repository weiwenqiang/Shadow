package com.zzz.myemergencyclientnew.library.imagepicker;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.View;

//import com.alibaba.android.arouter.facade.annotation.Route;
//import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzz.myemergencyclientnew.base.BaseActivity;
import com.zzz.myemergencyclientnew.constant.pref.C;
import com.zzz.myemergencyclientnew.eventbus.EvnImagePath;
import com.zzz.myemergencyclientnew.imagepicker.R;
import com.zzz.myemergencyclientnew.imagepicker.databinding.ActivityImageCropBinding;
import com.zzz.myemergencyclientnew.library.imagepicker.bean.ImageItem;
import com.zzz.myemergencyclientnew.library.imagepicker.util.BitmapUtil;
import com.zzz.myemergencyclientnew.library.imagepicker.view.CropImageView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

@Route(path = C.ImageCrop)
public class ImageCropActivity extends BaseActivity<ImageCropPresenter, ActivityImageCropBinding> implements ImageCropContract.View, CropImageView.OnBitmapSaveCompleteListener {
//    private CropImageView mCropImageView;
    private Bitmap mBitmap;
    private boolean mIsSaveRectangle;
    private int mOutputX;
    private int mOutputY;
    private ArrayList<ImageItem> mImageItems;
    private ImagePicker imagePicker;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_crop;
    }

    public void initView() {
        mViewBinding.setMView(this);
//        ARouter.getInstance().inject(this);

        imagePicker = ImagePicker.getInstance();
        //初始化View
        mViewBinding.cropImage.setOnBitmapSaveCompleteListener(this);
        //获取需要的参数
        mOutputX = imagePicker.getOutPutX();
        mOutputY = imagePicker.getOutPutY();
        mIsSaveRectangle = imagePicker.isSaveRectangle();
        mImageItems = imagePicker.getSelectedImages();
        String imagePath = mImageItems.get(0).path;

        mViewBinding.cropImage.setFocusStyle(imagePicker.getStyle());
        mViewBinding.cropImage.setFocusWidth(imagePicker.getFocusWidth());
        mViewBinding.cropImage.setFocusHeight(imagePicker.getFocusHeight());
        //缩放图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        options.inSampleSize = calculateInSampleSize(options, displayMetrics.widthPixels, displayMetrics.heightPixels);
        options.inJustDecodeBounds = false;
        mBitmap = BitmapFactory.decodeFile(imagePath, options);
//        mCropImageView.setImageBitmap(mBitmap);
        //设置默认旋转角度
        mViewBinding.cropImage.setImageBitmap(mViewBinding.cropImage.rotate(mBitmap, BitmapUtil.getBitmapDegree(imagePath)));

//        mCropImageView.setImageURI(Uri.fromFile(new File(imagePath)));
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = width / reqWidth;
            } else {
                inSampleSize = height / reqHeight;
            }
        }
        return inSampleSize;
    }

    public void goBack(View view){
        finish();
    }

    public void affirm(View view){
        mViewBinding.cropImage.saveBitmapToFile(imagePicker.getCropCacheFolder(this), mOutputX, mOutputY, mIsSaveRectangle);
    }

    @Override
    public void onBitmapSaveSuccess(File file) {
        //裁剪后替换掉返回数据的内容，但是不要改变全局中的选中数据
        mImageItems.remove(0);
        ImageItem imageItem = new ImageItem();
        imageItem.path = file.getAbsolutePath();
        mImageItems.add(imageItem);

//        Intent intent = new Intent();
//        intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, mImageItems);
//        setResult(ImagePicker.RESULT_CODE_ITEMS, intent);   //单选不需要裁剪，返回数据
        EventBus.getDefault().post(new EvnImagePath(mImageItems));
        finish();
    }

    @Override
    public void onBitmapSaveError(File file) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewBinding.cropImage.setOnBitmapSaveCompleteListener(null);
        if (null != mBitmap && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }
}