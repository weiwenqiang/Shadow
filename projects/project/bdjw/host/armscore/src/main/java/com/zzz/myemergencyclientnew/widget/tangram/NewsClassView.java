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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzz.myemergencyclientnew.constant.pref.Api;
import com.bumptech.glide.Glide;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;
import com.zzz.myemergencyclientnew.R;

public class NewsClassView extends FrameLayout implements ITangramViewLifeCycle {
    private Context context;
    private ImageView imgIcon;
    private TextView txtTitle;

    public NewsClassView(Context context) {
        this(context, null);
    }

    public NewsClassView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public NewsClassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initUI(context);
    }

    private void initUI(Context context){
        inflate(context, R.layout.v_news_class, this);
        imgIcon= findViewById(R.id.img_icon);
        txtTitle= findViewById(R.id.txt_title);
    }


    @Override
    public void cellInited(BaseCell cell) {
        setOnClickListener(cell);
    }

    @Override
    public void postBindView(BaseCell cell) {
        txtTitle.setText(cell.optStringParam("title"));
        Glide.with(context.getApplicationContext())
                .load(cell.optStringParam("icon"))
                .dontAnimate()
                .placeholder(R.drawable.ic_placeholder)
                .into(imgIcon);
    }

    @Override
    public void postUnBindView(BaseCell cell) {

    }
}
