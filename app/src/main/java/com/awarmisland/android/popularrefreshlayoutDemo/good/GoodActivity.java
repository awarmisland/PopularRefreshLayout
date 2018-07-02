package com.awarmisland.android.popularrefreshlayoutDemo.good;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.awarmisland.android.popularrefreshlayout.RefreshLayout;
import com.awarmisland.android.popularrefreshlayout.RefreshListener;
import com.awarmisland.android.popularrefreshlayout.utils.DisplayUtil;
import com.awarmisland.android.popularrefreshlayoutDemo.R;

import java.lang.reflect.Field;

/**
 * Created by awarmisland on 2017/3/18.
 */

public class GoodActivity extends Activity {
    private RefreshLayout refresh;
    private RefreshLayout refresh_detail;
    private Context context;

    private WebView wvw_web;
    private int height;
    private LinearLayout llt_detail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);
        context = this;
        initView();
        initEvent();
        initData();
    }
    private void initView(){
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        refresh_detail = (RefreshLayout) findViewById(R.id.refresh_detail);
        llt_detail = (LinearLayout) findViewById(R.id.llt_detail);
        GoodFooterView goodFooterView = new GoodFooterView(this);
        GoodHeaderView goodHeaderView = new GoodHeaderView(this);
        refresh.setAllowLoadMore(true);
        refresh.setFooderView(goodFooterView);
        refresh_detail.setHeaderView(goodHeaderView);
    }
    private void initEvent(){
        refresh.setRefreshListener(refreshListener);
        refresh_detail.setRefreshListener(detailrefreshListener);
    }
    private void initData(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        height = metric.heightPixels;   // 屏幕高度（像素）
        height = height -getStatusBarHeight();

        refresh.setLayoutParams(new FrameLayout.LayoutParams(width,height));
        refresh.setAllowRefresh(false);

        llt_detail.setLayoutParams(new FrameLayout.LayoutParams(width,height));
        llt_detail.setVisibility(View.GONE);

        wvw_web = (WebView) findViewById(R.id.wvw_web);
        WebSettings settings = wvw_web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        wvw_web.setWebViewClient(webViewClient);
        wvw_web.loadUrl("http://www.awarmisland.com");
    }


    //设置webview
    private WebViewClient webViewClient = new WebViewClient(){
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };
    //头部
    private RefreshListener refreshListener = new RefreshListener() {
        @Override
        public void onRefresh(RefreshLayout refreshLayout) {

        }

        @Override
        public void onRefreshLoadMore(RefreshLayout refreshLayout) {
            super.onRefreshLoadMore(refreshLayout);
            llt_detail.setVisibility(View.VISIBLE);
            head_up_ani();
            detail_up_ani();
            refresh.completeLoadMore();
        }
    };
    private void head_up_ani(){
        TranslateAnimation animation = new TranslateAnimation(0,0,0,-height);
        animation.setDuration(800);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setAnimationListener(head_listener);
        animation.setFillAfter(true);
        refresh.startAnimation(animation);
    }
    private void detail_up_ani(){
        TranslateAnimation animation = new TranslateAnimation(0,0,height,0);
        animation.setDuration(800);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setFillAfter(true);
        llt_detail.startAnimation(animation);
    }
    private void head_down_ani(){
        TranslateAnimation animation = new TranslateAnimation(0,0,-height,0);
        animation.setDuration(800);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setFillAfter(true);
        refresh.startAnimation(animation);
    }
    private void detail_down_ani(){
        TranslateAnimation animation = new TranslateAnimation(0,0,0,height);
        animation.setDuration(800);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setAnimationListener(detail_listener);
        animation.setFillAfter(true);
        llt_detail.startAnimation(animation);
    }
    private RefreshListener detailrefreshListener = new RefreshListener() {
        @Override
        public void onRefresh(RefreshLayout refreshLayout) {
            refresh.setVisibility(View.VISIBLE);
            detail_down_ani();
            head_down_ani();
            refresh_detail.completeRefresh();
        }
    };
    private Animation.AnimationListener head_listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            refresh.clearAnimation();
            refresh.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
    private Animation.AnimationListener detail_listener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            llt_detail.clearAnimation();
            llt_detail.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    private int getStatusBarHeight() {
        Class<?> c = null;

        Object obj = null;

        Field field = null;

        int x = 0, sbar = 0;

        try {

            c = Class.forName("com.android.internal.R$dimen");

            obj = c.newInstance();

            field = c.getField("status_bar_height");

            x = Integer.parseInt(field.get(obj).toString());

            sbar =  getResources().getDimensionPixelSize(x);

        } catch (Exception e1) {

            e1.printStackTrace();

        }

        return sbar;
    }

}
