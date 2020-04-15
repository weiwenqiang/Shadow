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

import com.alibaba.android.arouter.launcher.ARouter;
import com.zzz.myemergencyclientnew.R;
import com.zzz.myemergencyclientnew.media.GlideImageLoader;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BannerView extends FrameLayout implements ITangramViewLifeCycle {
    private Banner banner;
    private Context context;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initUI(context);
    }

    private void initUI(Context context){
        inflate(context, R.layout.v_banner, this);
        banner= findViewById(R.id.banner);
    }


    @Override
    public void cellInited(BaseCell cell) {
        setOnClickListener(cell);
    }

    @Override
    public void postBindView(BaseCell cell) {
//        List<Integer> list = new ArrayList<>();
//        list.add(R.mipmap.b1);
//        list.add(R.mipmap.b2);
//        list.add(R.mipmap.b3);
//        list.add(R.mipmap.b4);

        List<String> urlList = new ArrayList<>();
        JSONArray imgList = cell.optJsonArrayParam("items");
        for(int i = 0; i<imgList.length(); i++){
            try {
                JSONObject obj = imgList.getJSONObject(i);
                urlList.add(obj.getString("imgUrl"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        banner.setImages(urlList)
                .setImageLoader(new GlideImageLoader())
                .setDelayTime(4000)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        try {
//                            T.success(imgList.getJSONObject(position).getString("oid"));
                            String route = imgList.getJSONObject(position).getString("route");
                            String oid = imgList.getJSONObject(position).getString("oid");
                            ARouter.getInstance().build(route).withString("oid", oid).navigation();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .start();
    }

    @Override
    public void postUnBindView(BaseCell cell) {

    }
}
