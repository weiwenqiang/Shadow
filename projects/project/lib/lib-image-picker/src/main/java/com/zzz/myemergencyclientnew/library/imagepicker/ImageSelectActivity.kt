//package com.zzz.myemergencyclientnew.activity.image
//
//import android.app.Activity
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.os.Build
//import android.util.Log
//import android.view.Gravity
//import android.view.View
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.GridLayoutManager
//
//import com.alibaba.android.arouter.facade.annotation.Autowired
//import com.alibaba.android.arouter.facade.annotation.Route
//import com.alibaba.android.arouter.launcher.ARouter
//import com.zzz.myemergencyclientnew.R
//import com.zzz.myemergencyclientnew.base.BaseActivity
//import com.zzz.myemergencyclientnew.databinding.ActivityImageSelectBinding
//import com.zzz.myemergencyclientnew.eventbus.EvnImagePath
//import com.zzz.myemergencyclientnew.pref.C
//
//import com.zzz.myemergencyclientnew.utils.T
//import com.lzy.imagepicker.ImagePicker
//import com.lzy.imagepicker.adapter.ImageFolderAdapter
//import com.lzy.imagepicker.bean.ImageFolder
//import com.lzy.imagepicker.bean.ImageItem
//import com.lzy.imagepicker.ui.ImageCrop1Activity
//import com.lzy.imagepicker.util.Utils
//import com.lzy.imagepicker.view.FolderPopUpWindow
//import com.lzy.imagepicker.view.GridSpacingItemDecoration
//import com.zzz.myemergencyclientnew.base.BasePresenter
//import com.zzz.myemergencyclientnew.base.BaseView
//
//import org.greenrobot.eventbus.EventBus
//
//
//@Route(path = C.ImageSelect)
//class ImageSelectActivity : BaseActivity<ImageSelectPresenter, ActivityImageSelectBinding>(), ImageSelectContract.View,
//    ImageDataSource.OnImagesLoadedListener, ImagePicker.OnImageSelectedListener {
//
//    private var imagePicker: ImagePicker? = null
//
//    private var mImageFolderAdapter: ImageFolderAdapter? = null    //图片文件夹的适配器
//    private var mFolderPopupWindow: FolderPopUpWindow? = null  //ImageSet的PopupWindow
//    private var mImageFolders: List<ImageFolder>? = null   //所有的图片文件夹
//    private var mRecyclerAdapter: ImageRecyclerAdapter? = null
//
////    @Autowired
////    var oid: String? = null
//
//    override fun getLayoutId(): Int {
//        return R.layout.activity_image_select
//    }
//
//    override fun initView() {
//        mViewBinding.mView = this
////        ARouter.getInstance().inject(this)
//
//        imagePicker = ImagePicker.getInstance()
//        imagePicker!!.clear()
//        imagePicker!!.addOnImageSelectedListener(this)
//
//        if (imagePicker!!.isMultiMode) {
//            mViewBinding.btnAffirm.visibility = View.VISIBLE
//        } else {
//            mViewBinding.btnAffirm.visibility = View.GONE
//        }
//
//        mImageFolderAdapter = ImageFolderAdapter(this, null)
//        mRecyclerAdapter = ImageRecyclerAdapter(this, null)
//
//        onImageSelected(0, null, false)
//
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                ImageDataSource(this, null, this)
//            } else {
//                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION_STORAGE)
//            }
//        } else {
//            ImageDataSource(this, null, this)
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_PERMISSION_STORAGE) {
//            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                ImageDataSource(this, null, this)
//            } else {
//                T.error("权限被禁止，无法选择本地图片")
//            }
//        } else if (requestCode == REQUEST_PERMISSION_CAMERA) {
//            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                imagePicker!!.takePicture(this, ImagePicker.REQUEST_CODE_TAKE)
//            } else {
//                T.error("权限被禁止，无法打开相机")
//            }
//        }
//    }
//
//    override fun onDestroy() {
//        imagePicker!!.removeOnImageSelectedListener(this)
//        super.onDestroy()
//    }
//
//    /**
//     * 创建弹出的ListView
//     */
//    private fun createPopupFolderList() {
//        mFolderPopupWindow = FolderPopUpWindow(this, mImageFolderAdapter)
//        mFolderPopupWindow!!.setOnItemClickListener { adapterView, view, position, l ->
//            mImageFolderAdapter!!.selectIndex = position
//            imagePicker!!.currentImageFolderPosition = position
//            mFolderPopupWindow!!.dismiss()
//            val imageFolder = adapterView.adapter.getItem(position) as ImageFolder
//            if (null != imageFolder) {
//                mRecyclerAdapter!!.refreshData(imageFolder.images)
//                mViewBinding.txtDir.text = imageFolder.name
//            }
//        }
//        mFolderPopupWindow!!.setMargin(mViewBinding.lytBottom.height)
//    }
//
//    override fun onImagesLoaded(imageFolders: List<ImageFolder>) {
//        this.mImageFolders = imageFolders
//        imagePicker!!.imageFolders = imageFolders
//        if (imageFolders.size == 0) {
//            mRecyclerAdapter!!.refreshData(null)
//        } else {
//            mRecyclerAdapter!!.refreshData(imageFolders[0].images)
//        }
//        mViewBinding.rv.layoutManager = GridLayoutManager(mContext, 3)
//        mViewBinding.rv.addItemDecoration(GridSpacingItemDecoration(3, Utils.dp2px(this, 2f), false))
//        mViewBinding.rv.adapter = mRecyclerAdapter
//        mImageFolderAdapter!!.refreshData(imageFolders)
//    }
//
//
//    @SuppressLint("StringFormatMatches")
//    override fun onImageSelected(position: Int, item: ImageItem?, isAdd: Boolean) {
//        if (imagePicker!!.selectImageCount > 0) {
//            mViewBinding.btnAffirm.text = getString(R.string.ip_select_complete, imagePicker!!.selectImageCount, imagePicker!!.selectLimit)
//            mViewBinding.btnAffirm.isEnabled = true
//            mViewBinding.btnAffirm.setTextColor(ContextCompat.getColor(this, R.color.ip_text_primary_inverted))
//        } else {
//            mViewBinding.btnAffirm.text = getString(R.string.ip_complete)
//            mViewBinding.btnAffirm.isEnabled = false
//            mViewBinding.btnAffirm.setTextColor(ContextCompat.getColor(this, R.color.ip_text_secondary_inverted))
//        }
//        for (i in (if (imagePicker!!.isShowCamera) 1 else 0) until mRecyclerAdapter!!.itemCount) {
//            if (mRecyclerAdapter!!.getItem(i)!!.path != null && mRecyclerAdapter!!.getItem(i)!!.path == item!!.path) {
//                mRecyclerAdapter!!.notifyItemChanged(i)
//                return
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (data != null && data.extras != null) {
//            //            if (resultCode == ImagePicker.RESULT_CODE_BACK) {
//            ////                isOrigin = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
//            //            } else {
//            //从拍照界面返回
//            //点击 X , 没有选择照片
//            if (data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) == null) {
//                //什么都不做 直接调起相机
//            } else {
//                //说明是从裁剪页面过来的数据，直接返回就可以
//                setResult(ImagePicker.RESULT_CODE_ITEMS, data)
//            }
//            finish()
//            //            }
//        } else {
//            //如果是裁剪，因为裁剪指定了存储的Uri，所以返回的data一定为null
//            if (resultCode == Activity.RESULT_OK && requestCode == ImagePicker.REQUEST_CODE_TAKE) {
//                //发送广播通知图片增加了
//                ImagePicker.galleryAddPic(this, imagePicker!!.takeImageFile)
//
//                /**
//                 * 2017-03-21 对机型做旋转处理
//                 */
//                val path = imagePicker!!.takeImageFile.absolutePath
//
//                val imageItem = ImageItem()
//                imageItem.path = path
//                imagePicker!!.clearSelectedImages()
//                imagePicker!!.addSelectedImageItem(0, imageItem, true)
//                if (imagePicker!!.isCrop) {
//                    val intent = Intent(this@ImageSelectActivity, ImageCrop1Activity::class.java)
//                    startActivityForResult(intent, ImagePicker.REQUEST_CODE_CROP)  //单选需要裁剪，进入裁剪界面
//                } else {
//                    val intent = Intent()
//                    intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, imagePicker!!.selectedImages)
//                    setResult(ImagePicker.RESULT_CODE_ITEMS, intent)   //单选不需要裁剪，返回数据
//                    finish()
//                }
//            }
//        }
//    }
//
//    fun goBack(view: View) {
//        finish()
//    }
//
//    fun affirm(view: View) {
//        EventBus.getDefault().post(EvnImagePath(imagePicker!!.selectedImages))
//        finish()
//    }
//
//    fun dirSelect(view: View) {
//        if (mImageFolders == null) {
//            Log.i("ImageSelect1Activity", "您的手机没有图片")
//            return
//        }
//        //点击文件夹按钮
//        createPopupFolderList()
//        mImageFolderAdapter!!.refreshData(mImageFolders)  //刷新数据
//        if (mFolderPopupWindow!!.isShowing) {
//            mFolderPopupWindow!!.dismiss()
//        } else {
//            mFolderPopupWindow!!.showAtLocation(mViewBinding.lytBottom, Gravity.NO_GRAVITY, 0, 0)
//            //默认选择当前选择的上一个，当目录很多时，直接定位到已选中的条目
//            var index = mImageFolderAdapter!!.selectIndex
//            index = if (index == 0) index else index - 1
//            mFolderPopupWindow!!.setSelection(index)
//        }
//    }
//
//    companion object {
//        const val REQUEST_PERMISSION_STORAGE = 0x01
//        const val REQUEST_PERMISSION_CAMERA = 0x02
//    }
//}
//
//interface ImageSelectContract {
//    interface View : BaseView
//
//    abstract class Presenter : BasePresenter<View>()
//}
//
//class ImageSelectPresenter : ImageSelectContract.Presenter() {
//    override fun onAttached() {
//
//    }
//}
