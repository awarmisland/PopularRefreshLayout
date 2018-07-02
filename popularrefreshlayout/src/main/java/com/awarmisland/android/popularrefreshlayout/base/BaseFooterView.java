package com.awarmisland.android.popularrefreshlayout.base;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.awarmisland.android.popularrefreshlayout.RefreshLayout;


/**
 * Created by awarmisland on 2016/12/29.
 */

public abstract class BaseFooterView extends FrameLayout {
    public BaseFooterView(Context context) {
        super(context);
    }

    public BaseFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public abstract void onComlete(RefreshLayout materialRefreshLayout);
    public abstract void onBegin(RefreshLayout materialRefreshLayout);
    public abstract void onPull(RefreshLayout materialRefreshLayout, float fraction);
    public abstract void onRelease(RefreshLayout materialRefreshLayout, float fraction);
    public abstract void onRefreshLoadMore(RefreshLayout materialRefreshLayout);
}
