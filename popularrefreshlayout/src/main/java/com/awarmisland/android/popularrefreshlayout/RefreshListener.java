package com.awarmisland.android.popularrefreshlayout;

/**
 * 下拉上拉事件
 */
public abstract class RefreshListener {
    public abstract void onRefresh(RefreshLayout refreshLayout);
    public  void onRefreshLoadMore(RefreshLayout  refreshLayout){};
}
