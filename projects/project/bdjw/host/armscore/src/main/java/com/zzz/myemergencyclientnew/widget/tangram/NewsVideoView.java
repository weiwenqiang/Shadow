/*
 * MIT License
 *
 * Copyright (c) 2018 Alibaba Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.zzz.myemergencyclientnew.widget.tangram;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;
import com.zzz.myemergencyclientnew.R;

/**
 * 一个图片的展示
 */
public class NewsVideoView extends FrameLayout implements ITangramViewLifeCycle {
    private Context context;
    private ImageView img_head_portrait;
    private TextView txt_user_name;
    private SuperButton stv_news_type;
    private TextView txt_date;
    private TextView txt_views;
    private TextView txt_comm;
    private TextView txt_content;

    private StandardGSYVideoPlayer video_player;

    public NewsVideoView(Context context) {
        this(context, null);
    }

    public NewsVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NewsVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initUI(context);
    }

    private void initUI(Context context){
        inflate(context, R.layout.v_news_video, this);
        img_head_portrait= findViewById(R.id.img_head_portrait);
        txt_user_name= findViewById(R.id.txt_user_name);
        stv_news_type= findViewById(R.id.stv_news_type);
        txt_date= findViewById(R.id.txt_date);
        txt_views= findViewById(R.id.txt_views);
        txt_comm= findViewById(R.id.txt_comm);
        txt_content= findViewById(R.id.txt_content);

        video_player= findViewById(R.id.video_player);
    }

    @Override
    public void cellInited(BaseCell cell) {
        setOnClickListener(cell);
    }

    @Override
    public void postBindView(BaseCell cell) {
        txt_user_name.setText(cell.optStringParam("title"));
        txt_content.setText(cell.optStringParam("content"));
        txt_views.setText(cell.optIntParam("views")+"浏览");
        txt_comm.setText(cell.optIntParam("comm")+"评论");
        txt_date.setText(cell.optStringParam("createTime"));
        stv_news_type.setText(cell.optStringParam("newsType"));
        Glide.with(context.getApplicationContext())
                .load(cell.optStringParam("icon"))
                .dontAnimate()
                .placeholder(R.mipmap.default_head_photo)
                .into(img_head_portrait);

        String videoUrl = cell.optStringParam("video");

//        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        video_player.setUp(videoUrl, true, "视频");
        //增加title
        video_player.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        video_player.getBackButton().setVisibility(View.GONE);
        //是否可以滑动调整
        video_player.setIsTouchWiget(false);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        video_player.getFullscreenButton().setVisibility(View.GONE);
//        mViewBinding.videoPlayer.startPlayLogic();
    }

    @Override
    public void postUnBindView(BaseCell cell) {

    }
}
