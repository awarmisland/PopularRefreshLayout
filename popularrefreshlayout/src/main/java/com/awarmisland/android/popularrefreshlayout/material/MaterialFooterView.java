package com.awarmisland.android.popularrefreshlayout.material;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.awarmisland.android.popularrefreshlayout.R;
import com.awarmisland.android.popularrefreshlayout.RefreshLayout;
import com.awarmisland.android.popularrefreshlayout.base.BaseFooterView;
import com.awarmisland.android.popularrefreshlayout.utils.DisplayUtil;


public class MaterialFooterView extends BaseFooterView {
    private MaterialWaveView materialWaveView;
    private CircleProgressBar circleProgressBar;
    private int waveColor;
    private int progressTextColor;
    private int[] progress_colors;
    private int progressStokeWidth;
    private boolean isShowArrow, isShowProgressBg;
    private int progressValue, progressValueMax;
    private int textType;
    private int progressBg;
    private int progressSize;



    public MaterialFooterView(Context context) {
        this(context, null);
    }

    public MaterialFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs, defStyle);
    }


    protected void init(Context context,AttributeSet attrs, int defStyle) {
        if (isInEditMode()) return;
        setClipToPadding(false);
        setWillNotDraw(false);
        /**attrs for circleprogressbar*/
        int colorsId = R.array.material_colors;
        progress_colors = context.getResources().getIntArray(colorsId);
        progressBg =  CircleProgressBar.DEFAULT_CIRCLE_BG_LIGHT;
        progressTextColor = Color.BLACK;
        setProgressSize(50);//设置大小
        setProgressStokeWidth(3);//设置进度条宽度
        setTextType(1);//是否显示文字
        setIsProgressBg(true);//显示旋转背景
        showProgressArrow(true);//显示箭头
        setProgressTextColor(progressTextColor);//字体颜色
        setProgressValue(0);
        setProgressValueMax(100);
    }

    public int getWaveColor() {
        return waveColor;
    }

    public void setWaveColor(int waveColor) {
        this.waveColor = waveColor;
        if (null != materialWaveView) {
            materialWaveView.setColor(this.waveColor);
        }
    }

    public void setProgressSize(int progressSize) {
        this.progressSize = progressSize;
    }

    public void setProgressBg(int progressBg) {
        this.progressBg = progressBg;
        if(circleProgressBar!=null)
        circleProgressBar.setProgressBackGroundColor(progressBg);
    }

    public void setIsProgressBg(boolean isShowProgressBg) {
        this.isShowProgressBg = isShowProgressBg;
        if(circleProgressBar!=null)
        circleProgressBar.setCircleBackgroundEnabled(isShowProgressBg);
    }

    public void setProgressTextColor(int textColor) {
        this.progressTextColor = textColor;
    }

    public void setProgressColors(int[] colors) {
        this.progress_colors = colors;
        if(circleProgressBar!=null)
        circleProgressBar.setColorSchemeColors(progress_colors);
    }

    public void setTextType(int textType) {
        this.textType = textType;
    }

    public void setProgressValue(int value) {
        this.progressValue = value;
        this.post(new Runnable() {
            @Override
            public void run() {
                if (circleProgressBar != null) {
                    circleProgressBar.setProgress(progressValue);
                }
            }
        });

    }

    public void setProgressValueMax(int value) {
        this.progressValueMax = value;
    }

    public void setProgressStokeWidth(int w) {
        this.progressStokeWidth = w;
        if(circleProgressBar!=null)
        circleProgressBar.setProgressStokeWidth(progressStokeWidth);
    }

    public void showProgressArrow(boolean isShowArrow) {
        this.isShowArrow = isShowArrow;
        if(circleProgressBar!=null)
        circleProgressBar.setShowArrow(isShowArrow);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        final float density = getContext().getResources().getDisplayMetrics().density;

        materialWaveView = new MaterialWaveView(getContext());
        materialWaveView.setColor(waveColor);
        addView(materialWaveView);

        circleProgressBar = new CircleProgressBar(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((int) density * progressSize, (int) density * progressSize);
        layoutParams.gravity = Gravity.CENTER;
        circleProgressBar.setLayoutParams(layoutParams);
        circleProgressBar.setColorSchemeColors(progress_colors);
        circleProgressBar.setProgressStokeWidth(progressStokeWidth);
        circleProgressBar.setShowArrow(isShowArrow);
        circleProgressBar.setShowProgressText(textType == 0);
        circleProgressBar.setTextColor(progressTextColor);
        circleProgressBar.setProgress(progressValue);
        circleProgressBar.setMax(progressValueMax);
        circleProgressBar.setCircleBackgroundEnabled(isShowProgressBg);
        circleProgressBar.setProgressBackGroundColor(progressBg);
        addView(circleProgressBar);
    }

    @Override
    public void onComlete(RefreshLayout materialRefreshLayout) {
        if (materialWaveView != null) {
            materialWaveView.onComlete(materialRefreshLayout);
        }
        if (circleProgressBar != null) {
            circleProgressBar.onComlete(materialRefreshLayout);
            ViewCompat.setTranslationY(circleProgressBar, 0);
            ViewCompat.setScaleX(circleProgressBar, 0);
            ViewCompat.setScaleY(circleProgressBar, 0);
        }


    }

    @Override
    public void onBegin(RefreshLayout materialRefreshLayout) {
        if (materialWaveView != null) {
            materialWaveView.onBegin(materialRefreshLayout);
        }
        if (circleProgressBar != null) {
            circleProgressBar.onBegin(materialRefreshLayout);
            ViewCompat.setScaleX(circleProgressBar, 1);
            ViewCompat.setScaleY(circleProgressBar, 1);
        }
    }

    @Override
    public void onPull(RefreshLayout materialRefreshLayout, float fraction) {
        if (materialWaveView != null) {
            materialWaveView.onPull(materialRefreshLayout, fraction);
        }
        if (circleProgressBar != null) {
            circleProgressBar.onPull(materialRefreshLayout, fraction);
            float a = DisplayUtil.limitValue(1, fraction);
            ViewCompat.setScaleX(circleProgressBar, 1);
            ViewCompat.setScaleY(circleProgressBar, 1);
            ViewCompat.setAlpha(circleProgressBar, a);
        }
    }

    @Override
    public void onRelease(RefreshLayout materialRefreshLayout, float fraction) {

    }

    @Override
    public void onRefreshLoadMore(RefreshLayout materialRefreshLayout) {
        if (materialWaveView != null) {
            materialWaveView.onRefreshing(materialRefreshLayout);
        }
        if (circleProgressBar != null) {
            circleProgressBar.onRefreshing(materialRefreshLayout);
        }
    }

}


