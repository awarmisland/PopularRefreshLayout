package com.awarmisland.android.popularrefreshlayout.base;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.awarmisland.android.popularrefreshlayout.R;
import com.awarmisland.android.popularrefreshlayout.RefreshLayout;
import com.awarmisland.android.popularrefreshlayout.utils.DisplayUtil;


/**
 * Created by awarmisland on 2016/12/28.
 */

public class DefaultFooterView extends BaseFooterView {
    private Context mContext;
    private final static int DEFAULT_FOOTER_HEIGHT = 30;
    public DefaultFooterView(Context context) {
        super(context);
        init(context);
    }

    public DefaultFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        this.mContext = context;
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(DEFAULT_FOOTER_HEIGHT)));
        View view = LayoutInflater.from(mContext).inflate(R.layout.refresh_layout_footer_view,null);
        addView(view);
    }
    @Override
    public void onComlete(RefreshLayout materialRefreshLayout) {

    }

    @Override
    public void onBegin(RefreshLayout materialRefreshLayout) {

    }

    @Override
    public void onPull(RefreshLayout materialRefreshLayout, float fraction) {

    }

    @Override
    public void onRelease(RefreshLayout materialRefreshLayout, float fraction) {

    }

    @Override
    public void onRefreshLoadMore(RefreshLayout materialRefreshLayout) {

    }
}
