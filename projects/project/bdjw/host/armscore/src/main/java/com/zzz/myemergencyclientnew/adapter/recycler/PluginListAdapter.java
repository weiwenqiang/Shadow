package com.zzz.myemergencyclientnew.adapter.recycler;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zzz.myemergencyclientnew.R;
import com.zzz.myemergencyclientnew.entity.PluginListEntity;

public class PluginListAdapter extends BaseQuickAdapter<PluginListEntity, BaseViewHolder> {

    public PluginListAdapter() {
        super(R.layout.item_plugin_list, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, PluginListEntity item) {
        helper.setText(R.id.txt_name, item.pluginName);
    }
}
