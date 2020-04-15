package com.zzz.myemergencyclientnew.widget.tangram;///*
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
//import android.util.AttributeSet;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.zzz.myemergencyclientnew.R;
//import com.bumptech.glide.Glide;
//import com.tmall.wireless.tangram.structure.BaseCell;
//import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;
//
//public class NewsTitleView extends FrameLayout implements ITangramViewLifeCycle {
//    private Context context;
//    private TextView txt_title;
//    private TextView txt_author;
//    private TextView txt_msg;
//    private ImageView img_cover;
//
//    public NewsTitleView(Context context) {
//        this(context, null);
//    }
//
//    public NewsTitleView(Context context, AttributeSet attrs) {
//        this(context, attrs, -1);
//    }
//
//    public NewsTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.context = context;
//        initUI(context);
//    }
//
//    private void initUI(Context context){
//        inflate(context, R.layout.v_news_title, this);
//        txt_title= findViewById(R.id.txt_title);
//        txt_author= findViewById(R.id.txt_author);
//        txt_msg= findViewById(R.id.txt_msg);
//        img_cover= findViewById(R.id.img_cover);
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
//        txt_title.setText(cell.optStringParam("title"));
//        txt_author.setText(cell.optStringParam("author"));
//
//        txt_msg.setText(cell.optStringParam("createTime")+""+cell.optStringParam("views")+"浏览"+cell.optStringParam("comm")+"评论");
//
//        Glide.with(context.getApplicationContext())
//                .load("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg")
////                .crossFade()
//                .into(img_cover);
////        txt_msg.setText(cell.optStringParam("date")+""+cell.optStringParam("views")+"浏览"+cell.optStringParam("comm")+"评论");
//    }
//
//    @Override
//    public void postUnBindView(BaseCell cell) {
//
//    }
//
//}
