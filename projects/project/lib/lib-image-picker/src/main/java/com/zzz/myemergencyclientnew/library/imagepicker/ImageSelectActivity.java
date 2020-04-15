package com.zzz.myemergencyclientnew.library.imagepicker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zzz.myemergencyclientnew.base.BaseActivity;
import com.zzz.myemergencyclientnew.constant.pref.C;
import com.zzz.myemergencyclientnew.eventbus.EvnImagePath;
import com.zzz.myemergencyclientnew.imagepicker.R;
import com.zzz.myemergencyclientnew.imagepicker.databinding.ActivityImageSelectBinding;
import com.zzz.myemergencyclientnew.library.imagepicker.adapter.ImageFolderAdapter;
import com.zzz.myemergencyclientnew.library.imagepicker.adapter.ImageRecyclerAdapter;
import com.zzz.myemergencyclientnew.library.imagepicker.bean.ImageFolder;
import com.zzz.myemergencyclientnew.library.imagepicker.bean.ImageItem;
import com.zzz.myemergencyclientnew.library.imagepicker.ui.ImageGridActivity;
import com.zzz.myemergencyclientnew.library.imagepicker.util.Utils;
import com.zzz.myemergencyclientnew.library.imagepicker.view.FolderPopUpWindow;
import com.zzz.myemergencyclientnew.library.imagepicker.view.GridSpacingItemDecoration;

//import com.zzz.myemergencyclientnew.utils.T;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

@Route(path = C.ImageSelect)
public class ImageSelectActivity extends BaseActivity<ImageSelectPresenter, ActivityImageSelectBinding> implements ImageSelectContract.View, ImageDataSource.OnImagesLoadedListener, ImagePicker.OnImageSelectedListener, ImageRecyclerAdapter.OnImageItemClickListener {
    public static final int REQUEST_PERMISSION_STORAGE = 0x01;
    public static final int REQUEST_PERMISSION_CAMERA = 0x02;

    private ImagePicker imagePicker;

    private ImageFolderAdapter mImageFolderAdapter;    //图片文件夹的适配器
    private FolderPopUpWindow mFolderPopupWindow;  //ImageSet的PopupWindow
    private List<ImageFolder> mImageFolders;   //所有的图片文件夹
    private ImageRecyclerAdapter mRecyclerAdapter;

    public static final String EXTRAS_TAKE_PICKERS = "TAKE";//解决相机调用两次得问题

    @Autowired
    public boolean directPhoto=false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_select;
    }

    public void initView() {
        mViewBinding.setMView(this);
//        ARouter.getInstance().inject(this);

        imagePicker = ImagePicker.getInstance();
        imagePicker.clear();
        imagePicker.addOnImageSelectedListener(this);

//        Intent data = getIntent();
//        // 新增可直接拍照
//        if (data != null && data.getExtras() != null) {
//            directPhoto = data.getBooleanExtra(EXTRAS_TAKE_PICKERS, false); // 默认不是直接打开相机
            if (directPhoto) {
                if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, ImageGridActivity.REQUEST_PERMISSION_CAMERA);
                } else {
                    imagePicker.takePicture(this, ImagePicker.REQUEST_CODE_TAKE);
                }
                ArrayList<ImageItem> images = new ArrayList<ImageItem>();
                imagePicker.setSelectedImages(images);
            }
//        }


        if (imagePicker.isMultiMode()) {
            mViewBinding.btnAffirm.setVisibility(View.VISIBLE);
        } else {
            mViewBinding.btnAffirm.setVisibility(View.GONE);
        }

        mImageFolderAdapter = new ImageFolderAdapter(this, null);
        mRecyclerAdapter = new ImageRecyclerAdapter(this, null);
        mRecyclerAdapter.setOnImageItemClickListener(this);

        onImageSelected(0, null, false);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                new ImageDataSource(this, null, this);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
            }
        } else {
            new ImageDataSource(this, null, this);
        }
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        directPhoto = savedInstanceState.getBoolean(EXTRAS_TAKE_PICKERS, false);
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putBoolean(EXTRAS_TAKE_PICKERS, directPhoto);
//    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                new ImageDataSource(this, null, this);
            } else {
//                T.error("权限被禁止，无法选择本地图片");
            }
        } else if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imagePicker.takePicture(this, ImagePicker.REQUEST_CODE_TAKE);
            } else {
//                T.error("权限被禁止，无法打开相机");
            }
        }
    }

    @Override
    protected void onDestroy() {
        imagePicker.removeOnImageSelectedListener(this);
        super.onDestroy();
    }

    /**
     * 创建弹出的ListView
     */
    private void createPopupFolderList() {
        mFolderPopupWindow = new FolderPopUpWindow(this, mImageFolderAdapter);
        mFolderPopupWindow.setOnItemClickListener(new FolderPopUpWindow.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mImageFolderAdapter.setSelectIndex(position);
                imagePicker.setCurrentImageFolderPosition(position);
                mFolderPopupWindow.dismiss();
                ImageFolder imageFolder = (ImageFolder) adapterView.getAdapter().getItem(position);
                if (null != imageFolder) {
                    mRecyclerAdapter.refreshData(imageFolder.images);
                    mViewBinding.txtDir.setText(imageFolder.name);
                }
            }
        });
        mFolderPopupWindow.setMargin(mViewBinding.lytBottom.getHeight());
    }

    @Override
    public void onImagesLoaded(List<ImageFolder> imageFolders) {
        this.mImageFolders = imageFolders;
        imagePicker.setImageFolders(imageFolders);
        if (imageFolders.size() == 0) {
            mRecyclerAdapter.refreshData(null);
        } else {
            mRecyclerAdapter.refreshData(imageFolders.get(0).images);
        }
        mViewBinding.rv.setLayoutManager(new GridLayoutManager(this, 3));
        mViewBinding.rv.addItemDecoration(new GridSpacingItemDecoration(3, Utils.dp2px(this, 2), false));
        mViewBinding.rv.setAdapter(mRecyclerAdapter);
        mImageFolderAdapter.refreshData(imageFolders);
    }


    @SuppressLint("StringFormatMatches")
    @Override
    public void onImageSelected(int position, ImageItem item, boolean isAdd) {
        if (imagePicker.getSelectImageCount() > 0) {
            mViewBinding.btnAffirm.setText(getString(R.string.ip_select_complete, imagePicker.getSelectImageCount(), imagePicker.getSelectLimit()));
            mViewBinding.btnAffirm.setEnabled(true);
            mViewBinding.btnAffirm.setTextColor(ContextCompat.getColor(this, R.color.ip_text_primary_inverted));
        } else {
            mViewBinding.btnAffirm.setText(getString(R.string.ip_complete));
            mViewBinding.btnAffirm.setEnabled(false);
            mViewBinding.btnAffirm.setTextColor(ContextCompat.getColor(this, R.color.ip_text_secondary_inverted));
        }
        for (int i = imagePicker.isShowCamera() ? 1 : 0; i < mRecyclerAdapter.getItemCount(); i++) {
            if (mRecyclerAdapter.getItem(i).path != null && mRecyclerAdapter.getItem(i).path.equals(item.path)) {
                mRecyclerAdapter.notifyItemChanged(i);
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && data.getExtras() != null) {
            //从拍照界面返回
            //点击 X , 没有选择照片
            if (data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) == null) {
                //什么都不做 直接调起相机
            } else {
                //说明是从裁剪页面过来的数据，直接返回就可以
                setResult(ImagePicker.RESULT_CODE_ITEMS, data);
            }
            finish();
        } else {
//            如果是裁剪，因为裁剪指定了存储的Uri，所以返回的data一定为null
            if (resultCode == RESULT_OK && requestCode == ImagePicker.REQUEST_CODE_TAKE) {
                //发送广播通知图片增加了
                ImagePicker.galleryAddPic(this, imagePicker.getTakeImageFile());

                /**
                 * 2017-03-21 对机型做旋转处理
                 */
                String path = imagePicker.getTakeImageFile().getAbsolutePath();
//                int degree = BitmapUtil.getBitmapDegree(path);
//                if (degree != 0){
//                    Bitmap bitmap = BitmapUtil.rotateBitmapByDegree(path,degree);
//                    if (bitmap != null){
//                        File file = new File(path);
//                        try {
//                            FileOutputStream bos = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                            bos.flush();
//                            bos.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }

                ImageItem imageItem = new ImageItem();
                imageItem.path = path;
                imagePicker.clearSelectedImages();
                imagePicker.addSelectedImageItem(0, imageItem, true);
//                if (imagePicker.isCrop()) {
//                    Intent intent = new Intent(ImageGridActivity.this, ImageCropActivity.class);
//                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP);  //单选需要裁剪，进入裁剪界面
//                } else {
//                    Intent intent = new Intent();
//                    intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, imagePicker.getSelectedImages());
//                    setResult(ImagePicker.RESULT_CODE_ITEMS, intent);   //单选不需要裁剪，返回数据
//                    finish();
                EventBus.getDefault().post(new EvnImagePath(imagePicker.getSelectedImages()));
                finish();
//                }
            }
        }
    }

    public void goBack(View view) {
        finish();
    }

    public void affirm(View view) {
        EventBus.getDefault().post(new EvnImagePath(imagePicker.getSelectedImages()));
        finish();
    }

    public void dirSelect(View view) {
        if (mImageFolders == null) {
            Log.i("ImageSelect1Activity", "您的手机没有图片");
            return;
        }
        //点击文件夹按钮
        createPopupFolderList();
        mImageFolderAdapter.refreshData(mImageFolders);  //刷新数据
        if (mFolderPopupWindow.isShowing()) {
            mFolderPopupWindow.dismiss();
        } else {
            mFolderPopupWindow.showAtLocation(mViewBinding.lytBottom, Gravity.NO_GRAVITY, 0, 0);
            //默认选择当前选择的上一个，当目录很多时，直接定位到已选中的条目
            int index = mImageFolderAdapter.getSelectIndex();
            index = index == 0 ? index : index - 1;
            mFolderPopupWindow.setSelection(index);
        }
    }

    @Override
    public void onImageItemClick(View view, ImageItem imageItem, int position) {
//根据是否有相机按钮确定位置
        position = imagePicker.isShowCamera() ? position - 1 : position;
        if (imagePicker.isMultiMode()) {
//            Intent intent = new Intent(ImageGridActivity.this, ImagePreviewActivity.class);
//            intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
//
//            /**
//             * 2017-03-20
//             *
//             * 依然采用弱引用进行解决，采用单例加锁方式处理
//             */
//
//            // 据说这样会导致大量图片的时候崩溃
////            intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imagePicker.getCurrentImageFolderItems());
//
//            // 但采用弱引用会导致预览弱引用直接返回空指针
//            DataHolder.getInstance().save(DataHolder.DH_CURRENT_IMAGE_FOLDER_ITEMS, imagePicker.getCurrentImageFolderItems());
//            intent.putExtra(ImagePreviewActivity.ISORIGIN, isOrigin);
//            startActivityForResult(intent, ImagePicker.REQUEST_CODE_PREVIEW);  //如果是多选，点击图片进入预览界面
        } else {
            imagePicker.clearSelectedImages();
            imagePicker.addSelectedImageItem(position, imagePicker.getCurrentImageFolderItems().get(position), true);
            if (imagePicker.isCrop()) {
                ARouter.getInstance().build(C.ImageCrop).navigation();
                finish();
            } else {
                EventBus.getDefault().post(new EvnImagePath(imagePicker.getSelectedImages()));
                finish();
            }
        }
    }
}