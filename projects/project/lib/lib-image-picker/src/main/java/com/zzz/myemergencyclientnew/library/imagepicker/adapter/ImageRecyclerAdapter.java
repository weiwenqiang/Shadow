package com.zzz.myemergencyclientnew.library.imagepicker.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.zzz.myemergencyclientnew.common.R;
import com.zzz.myemergencyclientnew.library.imagepicker.ImagePicker;
import com.zzz.myemergencyclientnew.library.imagepicker.ImageSelectActivity;
import com.zzz.myemergencyclientnew.library.imagepicker.bean.ImageItem;
import com.zzz.myemergencyclientnew.library.imagepicker.util.ProviderUtil;
import com.zzz.myemergencyclientnew.library.imagepicker.util.Utils;
import com.zzz.myemergencyclientnew.library.imagepicker.view.SuperCheckBox;
import com.zzz.myemergencyclientnew.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 加载相册图片的RecyclerView适配器
 * <p>
 * 用于替换原项目的GridView，使用局部刷新解决选中照片出现闪动问题
 * <p>
 * 替换为RecyclerView后只是不再会导致全局刷新，
 * <p>
 * 但还是会出现明显的重新加载图片，可能是picasso图片加载框架的问题
 * <p>
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-04-05  10:04
 */

public class ImageRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_TYPE_CAMERA = 0;  //第一个条目是相机
    private static final int ITEM_TYPE_NORMAL = 1;  //第一个条目不是相机
    private ImagePicker imagePicker;
    private Activity mActivity;
    private ArrayList<ImageItem> images;       //当前需要显示的所有的图片数据
    private ArrayList<ImageItem> mSelectedImages; //全局保存的已经选中的图片数据
    private boolean isShowCamera;         //是否显示拍照按钮
    private int mImageSize;               //每个条目的大小
    private LayoutInflater mInflater;
    private OnImageItemClickListener listener;   //图片被点击的监听

    public void setOnImageItemClickListener(OnImageItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnImageItemClickListener {
        void onImageItemClick(View view, ImageItem imageItem, int position);
    }

    public void refreshData(ArrayList<ImageItem> images) {
        if (images == null || images.size() == 0) this.images = new ArrayList<>();
        else this.images = images;
        notifyDataSetChanged();
    }

    /**
     * 构造方法
     */
    public ImageRecyclerAdapter(Activity activity, ArrayList<ImageItem> images) {
        this.mActivity = activity;
        if (images == null || images.size() == 0) this.images = new ArrayList<>();
        else this.images = images;

        mImageSize = Utils.getImageItemWidth(mActivity);
        imagePicker = ImagePicker.getInstance();
        isShowCamera = imagePicker.isShowCamera();
        mSelectedImages = imagePicker.getSelectedImages();
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_CAMERA) {
            return new CameraViewHolder(mInflater.inflate(R.layout.adapter_camera_item, parent, false));
        }
        return new ImageViewHolder(mInflater.inflate(R.layout.adapter_image_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CameraViewHolder) {
            ((CameraViewHolder) holder).bindCamera();
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bind(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowCamera) return position == 0 ? ITEM_TYPE_CAMERA : ITEM_TYPE_NORMAL;
        return ITEM_TYPE_NORMAL;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return isShowCamera ? images.size() + 1 : images.size();
    }

    public ImageItem getItem(int position) {
        if (isShowCamera) {
            if (position == 0) return null;
            return images.get(position - 1);
        } else {
            return images.get(position);
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        ImageView ivThumb;
        View mask;
        View checkView;
        SuperCheckBox cbCheck;


        ImageViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            ivThumb = (ImageView) itemView.findViewById(R.id.iv_thumb);
            mask = itemView.findViewById(R.id.mask);
            checkView = itemView.findViewById(R.id.checkView);
            cbCheck = (SuperCheckBox) itemView.findViewById(R.id.cb_check);
            itemView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mImageSize)); //让图片是个正方形
        }

        void bind(final int position) {
            final ImageItem imageItem = getItem(position);
            ivThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    cbCheck.setChecked(!cbCheck.isChecked());
//                    int selectLimit = imagePicker.getSelectLimit();
//                    if (cbCheck.isChecked() && mSelectedImages.size() >= selectLimit) {
//                        Toast.makeText(mActivity.getApplicationContext(), mActivity.getString(R.string.ip_select_limit, selectLimit), Toast.LENGTH_SHORT).show();
//                        cbCheck.setChecked(false);
//                        mask.setVisibility(View.GONE);
//                    } else {
//                        imagePicker.addSelectedImageItem(position, imageItem, cbCheck.isChecked());
//                        mask.setVisibility(View.VISIBLE);
//                    }
                    if (listener != null) listener.onImageItemClick(rootView, imageItem, position);
                }
            });
            checkView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cbCheck.setChecked(!cbCheck.isChecked());
                    int selectLimit = imagePicker.getSelectLimit();
                    if (cbCheck.isChecked() && mSelectedImages.size() >= selectLimit) {
                        Toast.makeText(mActivity.getApplicationContext(), mActivity.getString(R.string.ip_select_limit, selectLimit), Toast.LENGTH_SHORT).show();
                        cbCheck.setChecked(false);
                        mask.setVisibility(View.GONE);
                    } else {
                        imagePicker.addSelectedImageItem(position, imageItem, cbCheck.isChecked());
                        mask.setVisibility(View.VISIBLE);
                    }
                }
            });
            //根据是否多选，显示或隐藏checkbox
            if (imagePicker.isMultiMode()) {
                cbCheck.setVisibility(View.VISIBLE);
                boolean checked = mSelectedImages.contains(imageItem);
                if (checked) {
                    mask.setVisibility(View.VISIBLE);
                    cbCheck.setChecked(true);
                } else {
                    mask.setVisibility(View.GONE);
                    cbCheck.setChecked(false);
                }
            } else {
                cbCheck.setVisibility(View.GONE);
            }
            Log.e("imagePicker",imagePicker.toString());
            Log.e("mActivity",mActivity.toString());
            Log.e("imageItem.path",imageItem.path.toString());
            Log.e("ivThumb",ivThumb.toString());
            Log.e("mImageSize",""+mImageSize);
            imagePicker.getImageLoader().displayImage(mActivity, imageItem.path, ivThumb, mImageSize, mImageSize); //显示图片
        }

    }

    private class CameraViewHolder extends RecyclerView.ViewHolder {

        View mItemView;

        CameraViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
        }

        void bindCamera() {
            mItemView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mImageSize)); //让图片是个正方形
            mItemView.setTag(null);
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!(ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, ImageSelectActivity.REQUEST_PERMISSION_CAMERA);
                    } else {
                        imagePicker.takePicture(mActivity, ImagePicker.REQUEST_CODE_TAKE);//原代码
                        File takeImageFile = null;
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePictureIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {
                            if (Utils.existSDCard()){
                                takeImageFile = new File(Environment.getExternalStorageDirectory(), "/DCIM/camera/");
                            } else{
                                takeImageFile = Environment.getDataDirectory();
                            }
                            takeImageFile = FileUtils.createFile(takeImageFile, "IMG_", ".jpg");
                            if (takeImageFile != null) {
                                // 默认情况下，即不需要指定intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                                // 照相机有自己默认的存储路径，拍摄的照片将返回一个缩略图。如果想访问原始图片，
                                // 可以通过dat extra能够得到原始图片位置。即，如果指定了目标uri，data就没有数据，
                                // 如果没有指定uri，则data就返回有数据！

                                Uri uri;
                                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                                    uri = Uri.fromFile(takeImageFile);
                                } else {

                                    /**
                                     * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
                                     * 并且这样可以解决MIUI系统上拍照返回size为0的情况
                                     */
                                    uri = FileProvider.getUriForFile(mActivity, ProviderUtil.getFileProviderName(mActivity), takeImageFile);
                                    //加入uri权限 要不三星手机不能拍照
                                    List<ResolveInfo> resInfoList = mActivity.getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                                    for (ResolveInfo resolveInfo : resInfoList) {
                                        String packageName = resolveInfo.activityInfo.packageName;
                                        mActivity.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    }
                                }

//                                Log.e("nanchen", ProviderUtil.getFileProviderName(mActivity));
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            }
                        }
                        mActivity.startActivityForResult(takePictureIntent, ImagePicker.REQUEST_CODE_TAKE);
//                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                        Uri contentUri = Uri.fromFile(takeImageFile);
//                        mediaScanIntent.setData(contentUri);
//                        mActivity.sendBroadcast(mediaScanIntent);
                        mActivity.finish();
                    }
                }
            });
        }
    }
}
