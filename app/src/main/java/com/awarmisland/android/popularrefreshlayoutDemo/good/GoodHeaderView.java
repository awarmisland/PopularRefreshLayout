package com.awarmisland.android.popularrefreshlayoutDemo.good;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awarmisland.android.popularrefreshlayout.RefreshLayout;
import com.awarmisland.android.popularrefreshlayout.base.BaseFooterView;
import com.awarmisland.android.popularrefreshlayout.base.BaseHeadView;
import com.awarmisland.android.popularrefreshlayout.utils.DisplayUtil;

import org.w3c.dom.Text;


/**
 * Created by awarmisland on 2016/12/28.
 */

public class GoodHeaderView extends BaseHeadView {
    private Context mContext;
    private final static int DEFAULT_FOOTER_HEIGHT = 30;
    private TextView tvw_content;
    public GoodHeaderView(Context context) {
        super(context);
        init(context);
    }

    public GoodHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        this.mContext = context;
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(DEFAULT_FOOTER_HEIGHT)));
        tvw_content = new TextView(mContext);
        tvw_content.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        tvw_content.setGravity(Gravity.CENTER);
        tvw_content.setText("下拉回到商品详情");
        //View view = LayoutInflater.from(mContext).inflate(R.layout.refresh_layout_footer_view,null);
        addView(tvw_content);
    }
    @Override
    public void onComlete(RefreshLayout materialRefreshLayout) {

    }

    @Override
    public void onBegin(RefreshLayout materialRefreshLayout) {

    }

    @Override
    public void onPull(RefreshLayout materialRefreshLayout, float fraction) {
        if(fraction>1) {
            tvw_content.setText("释放回到商品详情");
        }else{
            tvw_content.setText("下拉回到商品详情");
        }
    }

    @Override
    public void onRelease(RefreshLayout materialRefreshLayout, float fraction) {

    }

    @Override
    public void onRefreshing(RefreshLayout materialRefreshLayout) {

    }


}
