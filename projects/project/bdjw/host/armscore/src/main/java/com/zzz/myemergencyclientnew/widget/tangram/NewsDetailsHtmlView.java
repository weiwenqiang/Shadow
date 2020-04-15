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
//import com.tmall.wireless.tangram.structure.BaseCell;
//import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;
//import com.zzhoujay.richtext.RichText;
//import com.zzhoujay.richtext.callback.OnUrlClickListener;
//import com.zzz.myemergencyclientnew.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 一个图片的展示
// */
//public class NewsDetailsHtmlView extends FrameLayout implements ITangramViewLifeCycle {
//    private Context context;
//    private TextView txt_html;
//
//    private List<ImageView> imageViewList = new ArrayList<>();
//
//    public NewsDetailsHtmlView(Context context) {
//        this(context, null);
//    }
//
//    public NewsDetailsHtmlView(Context context, AttributeSet attrs) {
//        this(context, attrs, -1);
//    }
//
//    public NewsDetailsHtmlView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.context = context;
//        initUI(context);
//    }
//
//    private void initUI(Context context) {
//        inflate(context, R.layout.v_news_details_html, this);
//        txt_html = findViewById(R.id.txt_html);
//    }
//
//    @Override
//    public void cellInited(BaseCell cell) {
//        setOnClickListener(cell);
//    }
//
//    @Override
//    public void postBindView(BaseCell cell) {
//        String html = cell.optStringParam("content");
//        RichText.from("<html><body>" + html + "</body></html>")
//                .urlClick(new OnUrlClickListener() {
//                    @Override
//                    public boolean urlClicked(String url) {
//                        if (url.startsWith("code://")) {
//                            return true;
//                        }
//                        return false;
//                    }
//                })
//                .into(txt_html);
//    }
//
//    @Override
//    public void postUnBindView(BaseCell cell) {
//
//    }
//}
