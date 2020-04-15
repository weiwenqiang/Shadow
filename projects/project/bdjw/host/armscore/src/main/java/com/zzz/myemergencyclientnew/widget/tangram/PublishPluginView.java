///*
// * MIT License
// *
// * Copyright (c) 2018 Alibaba Group
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in all
// * copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// * SOFTWARE.
// */
//
//package com.zzz.myemergencyclientnew.widget.tangram;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.util.AttributeSet;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.zzz.myemergencyclientnew.R;
//import com.zzz.myemergencyclientnew.utils.FileUtils;
//import com.bumptech.glide.Glide;
//import com.downloader.Error;
//import com.downloader.OnDownloadListener;
//import com.downloader.OnProgressListener;
//import com.downloader.PRDownloader;
//import com.downloader.Progress;
//import com.tmall.wireless.tangram.structure.BaseCell;
//import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;
//import com.toast.T;
//
//import java.io.File;
//
//public class PublishPluginView extends FrameLayout implements ITangramViewLifeCycle {
//    private Context context;
//    private ImageView imgIcon;
//    private TextView txtTitle;
//    private ProgressBar progressBar;
//    private TextView txtState;
//
//    public PublishPluginView(Context context) {
//        this(context, null);
//    }
//
//    public PublishPluginView(Context context, AttributeSet attrs) {
//        this(context, attrs, -1);
//    }
//
//    public PublishPluginView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.context = context;
//        initUI(context);
//    }
//
//    private void initUI(Context context){
//        inflate(context, R.layout.v_publish_plugin, this);
//        imgIcon= findViewById(R.id.img_icon);
//        txtTitle= findViewById(R.id.txt_title);
//        progressBar= findViewById(R.id.progressBar);
//        txtState= findViewById(R.id.txt_state);
//    }
//
//
//    @Override
//    public void cellInited(BaseCell cell) {
//        setOnClickListener(cell);
//    }
//
//    @Override
//    public void postBindView(BaseCell cell) {
//        txtTitle.setText(cell.optStringParam("title"));
//        Glide.with(context.getApplicationContext())
//                .load(cell.optStringParam("iconNormal"))
//                .dontAnimate()
//                .placeholder(R.drawable.ic_placeholder)
//                .into(imgIcon);
//
//
//        int funType =  cell.optIntParam("funType");
//        if(funType==1){//是否是插件
//            String downloadLink = cell.optStringParam("downloadLink");
//            String apkFileName = cell.optStringParam("apkFileName");
//            int apkVersionCode = cell.optIntParam("apkVersionCode");
//            String enter = cell.optStringParam("enter");
//            String plugCode = cell.optStringParam("plugCode");
//
//            if(){
//
//            }
//        }
//        String llsApkFilePath = FileUtils.getRootDirPath(context) + File.separator + apkFileName;
//        File file = new File(llsApkFilePath);
//        if (file.exists()) {
//            boolean isAnZ = file.delete();
//        }
//
//        progressBar.setIndeterminate(false);
//        progressBar.getIndeterminateDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
//
//        PRDownloader.download(downloadLink, FileUtils.getRootDirPath(context), apkFileName)
//                .build()
//                .setOnProgressListener(new OnProgressListener() {
//                    @Override
//                    public void onProgress(Progress progress) {
//                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
//                        progressBar.setProgress((int) progressPercent);
//                        txtState.setText(FileUtils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
//                    }
//                })
//                .start(new OnDownloadListener() {
//                    @Override
//                    public void onDownloadComplete() {
//                        txtState.setText("准备就绪");
//                    }
//
//                    @Override
//                    public void onError(Error error) {
//                        txtState.setText("下载异常");
//                        progressBar.setProgress(0);
//                        progressBar.setIndeterminate(false);
//                    }
//                });
//    }
//
//    @Override
//    public void postUnBindView(BaseCell cell) {
//
//    }
//}
