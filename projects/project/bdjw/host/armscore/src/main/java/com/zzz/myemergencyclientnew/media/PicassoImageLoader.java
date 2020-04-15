package com.zzz.myemergencyclientnew.media;//package com.zzz.myemergencyclientnew.media;
//
///**
// * wwq 图片加载工具
// */
//
//import android.app.Activity;
//import android.net.Uri;
//import android.widget.ImageView;
//
//import com.squareup.picasso.MemoryPolicy;
//import com.squareup.picasso.Picasso;
//
//import java.io.File;
//
//public class PicassoImageLoader implements ImageLoader {
//
//    @Override
//    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
//        Picasso.with(activity)//
//                .load(Uri.fromFile(new File(path)))//
//                .placeholder(R.drawable.ic_default_image)//
//                .error(R.drawable.ic_default_image)//
//                .resize(width, height)//
//                .centerInside()//
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
//                .into(imageView);
//    }
//
//    @Override
//    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
//        Picasso.with(activity)//
//                .load(Uri.fromFile(new File(path)))//
//                .resize(width, height)//
//                .centerInside()//
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)//
//                .into(imageView);
//    }
//
//    @Override
//    public void clearMemoryCache() {
//    }
//}
