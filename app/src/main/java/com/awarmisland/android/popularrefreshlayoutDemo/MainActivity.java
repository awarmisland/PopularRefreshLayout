package com.awarmisland.android.popularrefreshlayoutDemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.awarmisland.android.popularrefreshlayout.RefreshLayout;
import com.awarmisland.android.popularrefreshlayout.RefreshListener;
import com.awarmisland.android.popularrefreshlayout.material.MaterialHeaderView;
import com.awarmisland.android.popularrefreshlayoutDemo.good.GoodActivity;
/**
 * Created by awarmisland on 2016/12/28.
 */
public class MainActivity extends AppCompatActivity {
    private  RefreshLayout refresh;
    private Button btn_default;
    private Button btn_material;
    private Button btn_default_listview;
    private Button btn_material_listview;
    private Button btn_default_webView;
    private Button btn_material_webView;
    private Button btn_default_goodview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }
    private void initView(){
        refresh  = (RefreshLayout) findViewById(R.id.refresh);
        MaterialHeaderView headerView = new MaterialHeaderView(this);
        refresh.setHeaderView(headerView);
        refresh.setIsOverLay(true);
        btn_default = (Button) findViewById(R.id.btn_default);
        btn_material =  (Button) findViewById(R.id.btn_material);
        btn_default_listview = (Button) findViewById(R.id.btn_default_listview);
        btn_material_listview = (Button) findViewById(R.id.btn_material_listview);
        btn_default_webView = (Button) findViewById(R.id.btn_default_webView);
        btn_material_webView = (Button) findViewById(R.id.btn_material_webView);
        btn_default_goodview = (Button) findViewById(R.id.btn_default_goodview);
    }

    private void initEvent(){
        refresh.setRefreshListener(refreshListener);
        btn_default.setOnClickListener(btn_default_onClick);
        btn_material.setOnClickListener(btn_material_onClick);
        btn_default_listview.setOnClickListener(btn_default_listview_onClick);
        btn_material_listview.setOnClickListener(btn_material_listview_onClick);
        btn_default_webView.setOnClickListener(btn_default_webView_onClick);
        btn_material_webView.setOnClickListener(btn_material_webView_onClick);
        btn_default_goodview.setOnClickListener(btn_default_goodview_onClick);
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
    //default content
    private View.OnClickListener btn_default_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,DefaultRefreshContentActivity.class));
        }
    };
    //Material content
    private View.OnClickListener btn_material_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,MaterialRefreshContentActivity.class));
        }
    };
    //default listView
    private View.OnClickListener btn_default_listview_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,DefaultRefreshListViewActivity.class));
        }
    };
    //Material listView
    private View.OnClickListener btn_material_listview_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,MaterialRefreshListViewActivity.class));
        }
    };
    //default webView
    private View.OnClickListener btn_default_webView_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,DefaultRefreshWebViewActivity.class));
        }
    };
    //Material webView
    private View.OnClickListener btn_material_webView_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,MaterialRefreshWebViewActivity.class));
        }
    };
    //btn_default_goodview
    private View.OnClickListener btn_default_goodview_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,GoodActivity.class));
        }
    };
}
