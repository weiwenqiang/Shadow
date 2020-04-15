package com.zzz.myemergencyclientnew.adapter.gridview;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.zzz.myemergencyclientnew.library.imagepicker.bean.ImageItem;
import com.bumptech.glide.Glide;

import java.util.List;

public class ExpandImageAdapter extends BaseAdapter {
    private Activity context;
    private GridView gridView;
    private List<ImageItem> items;

    public ExpandImageAdapter(Activity context, GridView gridView, List<ImageItem> items) {
        this.context = context;
        this.gridView = gridView;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ImageItem getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        int size = gridView.getWidth() / gridView.getNumColumns();
        if (convertView == null) {
            imageView = new ImageView(context);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundColor(Color.parseColor("#88888888"));
        } else {
            imageView = (ImageView) convertView;
        }
        Glide.with(context).load(getItem(position).path).into(imageView);
        return imageView;
    }
}
