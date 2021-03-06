package com.awarmisland.android.popularrefreshlayoutDemo;

import android.app.Activity;
import android.os.Bundle;

import com.awarmisland.android.popularrefreshlayout.RefreshLayout;
import com.awarmisland.android.popularrefreshlayout.RefreshListener;

/**
 * 默认刷新样式
 * Created by awarmisland on 2017/2/6.
 */

public class DefaultRefreshContentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_refresh);
        RefreshLayout refresh  = (RefreshLayout) findViewById(R.id.refresh);
        refresh.setRefreshListener(refreshListener);
    }
    private RefreshListener refreshListener = new RefreshListener() {
        @Override
        public void onRefresh(final RefreshLayout refreshLayout) {
            refreshLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.finishRefresh();
                }
            }, 3000);
        }
    };
}
