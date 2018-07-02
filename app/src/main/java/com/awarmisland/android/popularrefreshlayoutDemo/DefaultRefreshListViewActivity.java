package com.awarmisland.android.popularrefreshlayoutDemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.awarmisland.android.popularrefreshlayout.RefreshLayout;
import com.awarmisland.android.popularrefreshlayout.RefreshListener;

/**
 * 默认 listView刷新样式
 * Created by awarmisland on 2017/2/6.
 */

public class DefaultRefreshListViewActivity extends Activity {
    private RefreshLayout refresh;
    private ListView lvw_list;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_refresh);
        initView();
        initEvent();
        initData();
    }
    private void initView(){
        refresh  = (RefreshLayout) findViewById(R.id.refresh);
        refresh.setAllowLoadMore(true);
        lvw_list = (ListView) findViewById(R.id.lvw_list);
    }
    private void initEvent(){
        refresh.setRefreshListener(refreshListener);
    }
    private void initData(){
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1);
        for(int i=0;i<10;i++) {
            adapter.add("line " + (i + 1));
        }
        lvw_list.setAdapter(adapter);
    }
    private RefreshListener refreshListener = new RefreshListener() {
        @Override
        public void onRefresh(final RefreshLayout refreshLayout) {
            refreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.finishRefresh();
                    adapter.clear();
                    for(int i=0;i<10;i++) {
                        adapter.add("line " + (i + 1));
                    }
                    adapter.notifyDataSetChanged();
                }
            }, 3000);
        }

        @Override
        public void onRefreshLoadMore(final RefreshLayout refreshLayout) {
            refreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.finishRefreshLoadMore();
                    int size = adapter.getCount();
                    if(size<20) {
                        for (int i = 10; i < 20; i++) {
                            adapter.add("line " + (i + 1));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }, 3000);
        }
    };
}
