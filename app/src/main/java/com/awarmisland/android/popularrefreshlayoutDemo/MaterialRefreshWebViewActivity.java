package com.awarmisland.android.popularrefreshlayoutDemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.awarmisland.android.popularrefreshlayout.RefreshLayout;
import com.awarmisland.android.popularrefreshlayout.RefreshListener;
import com.awarmisland.android.popularrefreshlayout.material.MaterialHeaderView;

/**
 * Material webView刷新样式
 * Created by awarmisland on 2017/2/6.
 */

public class MaterialRefreshWebViewActivity extends Activity {
    private  RefreshLayout refresh;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_refresh);
        initView();
        initEvent();
    }
    private void initView(){
        refresh  = (RefreshLayout) findViewById(R.id.refresh);
        MaterialHeaderView headerView = new MaterialHeaderView(this);
        refresh.setHeaderView(headerView);
        refresh.setIsOverLay(true);
        webView = (WebView) findViewById(R.id.wvw_web);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(webViewClient);
        webView.loadUrl("http://blog.csdn.net/ljzdyh");
    }
    private void initEvent(){
        refresh.setRefreshListener(refreshListener);
    }
    //设置webview
    private WebViewClient webViewClient = new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            refresh.autoRefresh();
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
