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

import com.bumptech.glide.Glide;
import com.zzz.myemergencyclientnew.R;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个图片的展示
 */
public class IndexImageThreeView extends FrameLayout implements ITangramViewLifeCycle {
    private Context context;
    private TextView txt_title;
    private TextView txt_content;
    private TextView txt_author;
    private TextView txt_views;
    private TextView txt_comm;
    private TextView txt_date;

    private ImageView img_one;
    private ImageView img_two;
    private ImageView img_three;
    private ImageView img_four;
    private ImageView img_five;
    private ImageView img_six;
    private ImageView img_seven;
    private ImageView img_eight;
    private ImageView img_nine;

    private List<ImageView> imageViewList = new ArrayList<>();

    public IndexImageThreeView(Context context) {
        this(context, null);
    }

    public IndexImageThreeView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public IndexImageThreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initUI(context);
    }

    private void initUI(Context context){
        inflate(context, R.layout.v_index_image_three, this);
        txt_title= findViewById(R.id.txt_title);
        txt_content= findViewById(R.id.txt_content);
        txt_author= findViewById(R.id.txt_author);
        txt_date= findViewById(R.id.txt_date);
        txt_views= findViewById(R.id.txt_views);
        txt_comm= findViewById(R.id.txt_comm);

        img_one= findViewById(R.id.img_one);
        img_two= findViewById(R.id.img_two);
        img_three= findViewById(R.id.img_three);
        img_four= findViewById(R.id.img_four);
        img_five= findViewById(R.id.img_five);
        img_six= findViewById(R.id.img_six);
        img_seven= findViewById(R.id.img_seven);
        img_eight= findViewById(R.id.img_eight);
        img_nine= findViewById(R.id.img_nine);

        imageViewList.add(img_one);
        imageViewList.add(img_two);
        imageViewList.add(img_three);
        imageViewList.add(img_four);
        imageViewList.add(img_five);
        imageViewList.add(img_six);
        imageViewList.add(img_seven);
        imageViewList.add(img_eight);
        imageViewList.add(img_nine);
    }

    @Override
    public void cellInited(BaseCell cell) {
        setOnClickListener(cell);
    }

    @Override
    public void postBindView(BaseCell cell) {
        txt_title.setText(cell.optStringParam("title"));
        txt_author.setText(cell.optStringParam("author"));
        txt_views.setText(cell.optIntParam("views")+"浏览");
        txt_comm.setText(cell.optIntParam("comm")+"评论");
        txt_date.setText(cell.optStringParam("createTime"));
        txt_content.setText(cell.optStringParam("content"));

        JSONArray imgList = cell.optJsonArrayParam("imgList");
        for(int i = 0; i<imgList.length(); i++){
            try {
                String img = imgList.getString(i);
                Glide.with(context.getApplicationContext())
                        .load(img)
                        .dontAnimate()
                        .fitCenter()
                        .placeholder(R.drawable.ic_placeholder)
                        .into(imageViewList.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void postUnBindView(BaseCell cell) {

    }
}
