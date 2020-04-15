package com.zzz.myemergencyclientnew.activity.plugin;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zzz.myemergencyclientnew.R;
import com.zzz.myemergencyclientnew.activity.plugin.PluginListPresenter;
import com.zzz.myemergencyclientnew.adapter.recycler.PluginListAdapter;
import com.zzz.myemergencyclientnew.base.BaseActivity;
import com.zzz.myemergencyclientnew.databinding.ActivityPluginListBinding;
import com.zzz.myemergencyclientnew.entity.PluginListEntity;

import java.util.List;

public class PluginListActivity extends BaseActivity<PluginListPresenter, ActivityPluginListBinding> implements PluginListContract.View {
    private PluginListAdapter mAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_plugin_list;
    }

    public void initView() {
        mViewBinding.rv.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PluginListAdapter();
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);

        mViewBinding.rv.setAdapter(mAdapter);

        mPresenter.netInitData(mContext);
    }

    @Override
    public void netInitData(List<PluginListEntity> list) {
        mAdapter.setNewData(list);
    }
}
