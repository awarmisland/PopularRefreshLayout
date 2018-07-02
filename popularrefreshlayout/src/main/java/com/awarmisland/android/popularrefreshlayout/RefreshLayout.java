package com.awarmisland.android.popularrefreshlayout;


import android.content.Context;
import android.graphics.PointF;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import com.awarmisland.android.popularrefreshlayout.base.BaseFooterView;
import com.awarmisland.android.popularrefreshlayout.base.BaseHeadView;
import com.awarmisland.android.popularrefreshlayout.base.DefaultFooterView;
import com.awarmisland.android.popularrefreshlayout.base.DefaultHeadView;
import com.awarmisland.android.popularrefreshlayout.utils.DisplayUtil;

/**
 * 下拉上拉layout
 */
public class RefreshLayout extends FrameLayout {

    public static final String Tag = RefreshLayout.class.getSimpleName();

    private final static int DEFAULT_HEAD_HEIGHT = 70;
    private final static int DEFAULT_FOOTER_HEIGHT = 70;

    private final static int COMPLETE_WAIT_TIME = 1000;//保持刷新 加载 时间
    private final static float ANGLE_TAN_VALUE = 1.0f; //允许下拉下载角度tan 45
    private BaseHeadView mHeaderView;
    private BaseFooterView mFooterView;

    private boolean isOverlay;
    protected float mHeadHeight;
    protected float mFooterHeight;
    private View mChildView;
    protected boolean isRefreshing;
    private float mTouchY;
    private float mCurrentY;
    private DecelerateInterpolator decelerateInterpolator;


    private RefreshListener refreshListener;

    private boolean isLoadMoreing;
    private boolean isAllowLoadMore;
    private boolean isAllowRefresh;

    private PointF pointF = new PointF();

    public RefreshLayout(Context context) {
        this(context, null, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defstyleAttr) {
        Config.mContext = context;
        if (isInEditMode()) {
            return;
        }

        if (getChildCount() > 1) {
            throw new RuntimeException("can only have one child widget");
        }

        decelerateInterpolator = new DecelerateInterpolator(10);

        mHeadHeight = DisplayUtil.dp2px(DEFAULT_HEAD_HEIGHT);
        mFooterHeight = DisplayUtil.dp2px(DEFAULT_FOOTER_HEIGHT);
        isOverlay = false;
        isAllowLoadMore = false;
        isAllowRefresh=true;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //Log.i(Tag, "onAttachedToWindow");

        Context context = getContext();

        mChildView = getChildAt(0);

        if (mChildView == null) {
            return;
        }

        //设置顶部
        if (mHeaderView==null) {
            setHeaderView(new DefaultHeadView(context));
        }
        LayoutParams headLayoutParams = (LayoutParams) mHeaderView.getLayoutParams();
        headLayoutParams.gravity= Gravity.TOP;
        headLayoutParams.height =(int)mHeadHeight;
        mHeaderView.setVisibility(View.GONE);
        //设置默认底部
        if(mFooterView ==null){
            setFooderView(new DefaultFooterView(context));
        }
        LayoutParams footerLayoutParams = (LayoutParams) mFooterView.getLayoutParams();
        footerLayoutParams.height =(int)mFooterHeight;
        footerLayoutParams.gravity= Gravity.BOTTOM;
        mFooterView.setVisibility(View.GONE);


    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //Log.i("kk","Parent dispatch down");
                break;
            case MotionEvent.ACTION_CANCEL:

            case MotionEvent.ACTION_MOVE:
                //Log.i("kk","Parent dispatch move");
                break;
            case MotionEvent.ACTION_UP:
                //Log.i("kk","Parent dispatch up");
                //出现旋转
//                float dy = ev.getY() - mTouchY;
//                dy = ev.getY() - pointF.y;
//                if(isOverlay){
//                    if (dy < 0 && !canChildScrollDown() && isAllowLoadMore) {
//                        if (mFooterView != null && !isLoadMoreing) {
//                            updateLoadMoreListener();
//                            //Log.i("kk","Parent dispatch up true");
//                            return true;
//                        }
//                    }
//                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isRefreshing) return true;
        float dy = 0,min=0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = ev.getY();
                mCurrentY = mTouchY;
                pointF.set(ev.getX(),mTouchY);
                //Log.i("kk","Parent intercept down x "+pointF.x +" y "+pointF.y );
                break;
            case MotionEvent.ACTION_MOVE:
                dy = ev.getY() - mTouchY;
                min = Math.abs(pointF.y-ev.getY())/ Math.abs(pointF.x-ev.getX());
//                Log.i("kk","dy  "+ (pointF.y-ev.getY()));
//                Log.i("kk","dx  "+ (pointF.x-ev.getX()));
//                Log.i("kk","Parent intercept move "+min);
                if (min>ANGLE_TAN_VALUE && dy > 0 && !canChildScrollUp()&&isAllowRefresh) {
                    if (mHeaderView != null) {
                        mHeaderView.onBegin(this);
                    }
                    //Log.i("kk","Parent intercept move true");
                    return true;
                }else if(min>ANGLE_TAN_VALUE && dy < 0 && !canChildScrollDown()&& isAllowLoadMore){
                    //Log.i("kk","scroll bottom");
                    if (mFooterView != null) {
                        mFooterView.onBegin(this);
                    }
                    return true;
                }
                //Log.i("kk","Parent intercept move");
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                //Log.i("kk","Parent intercept up");
                break;

        }
        return super.onInterceptTouchEvent(ev);
    }



    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (isRefreshing||isLoadMoreing) {
            return super.onTouchEvent(e);
        }
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Log.i("kk","Parent touch down");
                break;
            case MotionEvent.ACTION_MOVE:
                //Log.i("kk","Parent touch move");
            	 mCurrentY = e.getY();
                 float dy = mCurrentY - mTouchY;
                 float offsetY = 0f;
                 if (mChildView != null) {
                     if (mHeaderView != null&&mCurrentY>mTouchY&&isAllowRefresh) {
                     	float dvalue= mHeadHeight+DisplayUtil.dp2px(10);
                     	dy = Math.min(dvalue * 2, Math.abs(dy));
                         dy = Math.max(0, dy);
                         offsetY = decelerateInterpolator.getInterpolation(dy / dvalue / 2) * dy / 2;
                         float fraction = offsetY / mHeadHeight;
                         mHeaderView.setVisibility(View.VISIBLE);
                         mHeaderView.getLayoutParams().height = (int) offsetY;
                         mHeaderView.requestLayout();
                         mHeaderView.onPull(this, fraction);
                     }
                     if(mFooterView !=null&&mCurrentY<mTouchY&&isAllowLoadMore){
                         if(mHeaderView!=null&&mHeaderView.getVisibility()==View.VISIBLE){
                             return true;
                         }
                     	float dvalue= mFooterHeight+DisplayUtil.dp2px(10);
                     	dy = Math.min(dvalue * 2, Math.abs(dy));
                         dy = Math.max(0, dy);
                         offsetY = decelerateInterpolator.getInterpolation(dy / dvalue / 2) * dy / 2;
                         float fraction = offsetY / mFooterHeight;
                         mFooterView.setVisibility(View.VISIBLE);
                         mFooterView.getLayoutParams().height = (int) offsetY;
                         mFooterView.requestLayout();
                         mFooterView.onPull(this, fraction);
                         offsetY = -offsetY;
                     }
                     if (!isOverlay) {
                         ViewCompat.setTranslationY(mChildView, offsetY);
                     }

                 }
                return true;
            case MotionEvent.ACTION_UP:
                //Log.i("kk","Parent touch up");
                if (mChildView != null) {
                    if (isOverlay) {
                        if (mHeaderView.getLayoutParams().height > mHeadHeight) {
                            updateHeaderListener();
                            mHeaderView.getLayoutParams().height = (int) mHeadHeight;
                            mHeaderView.requestLayout();
                        } else {
                            mHeaderView.getLayoutParams().height = 0;
                            mHeaderView.requestLayout();
                        }
                        if (mFooterView.getLayoutParams().height > mFooterHeight) {
                            //Log.d("kk","height "+ mFooterView.getLayoutParams().height);
                            updateLoadMoreListener();
                            mFooterView.getLayoutParams().height = (int) mFooterHeight;
                            mFooterView.requestLayout();
                        } else {
                            mFooterView.getLayoutParams().height = 0;
                            mFooterView.requestLayout();
                        }
                    } else {
                        float tmp_y = ViewCompat.getTranslationY(mChildView);
                        if ( tmp_y>= mHeadHeight) {
                            createAnimatorTranslationY(mChildView, mHeadHeight, mHeaderView);
                            updateHeaderListener();
                        }else if(tmp_y <= -mFooterHeight){
                            createAnimatorTranslationY(mChildView, -mFooterHeight, mFooterView);
                            updateLoadMoreListener();
                        }else {
                            createAnimatorTranslationY(mChildView, 0, mHeaderView);//拉动距离不够，还�?
                            createAnimatorTranslationY(mChildView, 0, mFooterView);//拉动距离不够，还�?
                            mHeaderView.setVisibility(View.GONE);
                            mFooterView.setVisibility(View.GONE);
                        }
                    }
                }
                return true;
        }

        return super.onTouchEvent(e);
    }


    public void autoRefresh() {
        this.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isRefreshing) {
                    if (mHeaderView != null) {
                        mHeaderView.setVisibility(View.VISIBLE);

                        if (isOverlay) {
                            mHeaderView.getLayoutParams().height = (int) mHeadHeight;
                            mHeaderView.requestLayout();
                        } else {
                            createAnimatorTranslationY(mChildView, mHeadHeight, mHeaderView);
                        }
                    }

                    updateHeaderListener();


                }
            }
        }, 50);


    }

    public void autoRefreshLoadMore() {
        this.post(new Runnable() {
            @Override
            public void run() {
                if (isAllowLoadMore) {
                    updateLoadMoreListener();
                } else {
                    throw new RuntimeException("you must setAllowLoadMore ture");
                }
            }
        });
    }

    public void updateHeaderListener() {
        isRefreshing = true;

        if (mHeaderView != null) {
            mHeaderView.onRefreshing(RefreshLayout.this);
        }

        if (refreshListener != null) {
            refreshListener.onRefresh(RefreshLayout.this);
        }

    }

    public void updateLoadMoreListener() {
        isLoadMoreing = true;

        if (mFooterView != null) {
            mFooterView.onRefreshLoadMore(RefreshLayout.this);
        }

        if (refreshListener != null) {
            refreshListener.onRefreshLoadMore(RefreshLayout.this);
        }

    }

    public void createAnimatorTranslationY(final View v, final float h, final FrameLayout fl) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate(v);
        viewPropertyAnimatorCompat.setDuration(250);
        viewPropertyAnimatorCompat.setInterpolator(new DecelerateInterpolator());
        viewPropertyAnimatorCompat.translationY(h);
        viewPropertyAnimatorCompat.start();
        viewPropertyAnimatorCompat.setUpdateListener(new ViewPropertyAnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(View view) {
                float height = Math.abs(ViewCompat.getTranslationY(v));
                fl.getLayoutParams().height = (int) height;
                fl.requestLayout();
            }
        });
    }

    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */
    public boolean canChildScrollUp() {
        if (mChildView == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mChildView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mChildView;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mChildView, -1) || mChildView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mChildView, -1);
        }
    }

    public boolean canChildScrollDown() {
        if (mChildView == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 14) {
            if (mChildView instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mChildView;
                if (absListView.getChildCount() > 0) {
                    int lastChildBottom = absListView.getChildAt(absListView.getChildCount() - 1).getBottom();
                    return absListView.getLastVisiblePosition() == absListView.getAdapter().getCount() - 1 && lastChildBottom <= absListView.getMeasuredHeight();
                } else {
                    return false;
                }

            } else {
                return ViewCompat.canScrollVertically(mChildView, 1) || mChildView.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mChildView, 1);
        }
    }
    //默认结束时间
   public void completeRefresh(){
       if(isRefreshing) {
           postDelayed(new Runnable() {
               @Override
               public void run() {
                   finishRefresh();
               }
           }, COMPLETE_WAIT_TIME);
       }
   }
    public void completeLoadMore(){
        if(isLoadMoreing) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    finishRefreshLoadMore();
                }
            }, COMPLETE_WAIT_TIME);
        }
    }
    public void finishRefresh() {
        if (mChildView != null) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate(mChildView);
            viewPropertyAnimatorCompat.setDuration(200);
            viewPropertyAnimatorCompat.y(ViewCompat.getTranslationY(mChildView));
            viewPropertyAnimatorCompat.translationY(0);
            viewPropertyAnimatorCompat.setInterpolator(new DecelerateInterpolator());
            viewPropertyAnimatorCompat.start();

            if (mHeaderView != null) {
                mHeaderView.onComlete(RefreshLayout.this);
                mHeaderView.setVisibility(View.GONE);
            }
        }
        isRefreshing = false;
    }

    public void finishRefreshLoadMore() {
        if (mChildView != null) {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = ViewCompat.animate(mChildView);
            viewPropertyAnimatorCompat.setDuration(200);
            viewPropertyAnimatorCompat.y(ViewCompat.getTranslationY(mChildView));
            viewPropertyAnimatorCompat.translationY(0);
            viewPropertyAnimatorCompat.setInterpolator(new DecelerateInterpolator());
            viewPropertyAnimatorCompat.start();

            if (mFooterView != null) {
                mFooterView.onComlete(RefreshLayout.this);
                mFooterView.setVisibility(View.GONE);
            }

        }
        isLoadMoreing =false;
    }

    public void setAllowLoadMore(boolean isAllowLoadMore) {
        this.isAllowLoadMore = isAllowLoadMore;
    }
    public void setAllowRefresh(boolean isAllowRefresh){
        this.isAllowRefresh = isAllowRefresh;
    }

    public void setIsOverLay(boolean isOverLay) {
        this.isOverlay = isOverLay;
    }

    public void setHeaderView(BaseHeadView headerView) {
        mHeaderView = headerView;
        addView(mHeaderView);
    }

    public void setFooderView(final BaseFooterView fooderView) {
        mFooterView = fooderView;
        this.addView(fooderView);
    }


    public void setHeaderHeight(float headHeight) {
        this.mHeadHeight = headHeight;
    }

    public void setFooterHeight(float footerHeight){this.mFooterHeight = footerHeight;}

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

}
