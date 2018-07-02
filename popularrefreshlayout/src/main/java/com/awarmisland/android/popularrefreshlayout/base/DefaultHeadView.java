package com.awarmisland.android.popularrefreshlayout.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.awarmisland.android.popularrefreshlayout.R;
import com.awarmisland.android.popularrefreshlayout.RefreshLayout;
import com.awarmisland.android.popularrefreshlayout.utils.DateUtil;
import com.awarmisland.android.popularrefreshlayout.utils.DisplayUtil;
import com.awarmisland.android.popularrefreshlayout.utils.SharedPreferUtil;
import com.awarmisland.android.popularrefreshlayout.utils.StringUtil;

import java.util.Date;


/**
 * Created by awarmisland on 2016/12/28.
 */

public class DefaultHeadView extends BaseHeadView {
    private Context mContext;
    private final static int HIGHER_HEAD_HEIGHT = 100;
    private TextView tvw_tips;
    private TextView tvw_time;
    private ImageView ivw_refresh;
    private ProgressBar pbr_loading;
    private boolean is_arrow_start_refresh_anim;
    private boolean is_arrow_start_normal_anim;
    private boolean is_fresh = false;
    public DefaultHeadView(Context context) {
        super(context);
        init(context);
    }

    public DefaultHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        this.mContext = context;
        is_arrow_start_refresh_anim =false;
        is_arrow_start_normal_anim = true;
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dp2px(HIGHER_HEAD_HEIGHT)));
        View view = LayoutInflater.from(mContext).inflate(R.layout.refresh_layout_header_view,null);
        tvw_tips = (TextView) view.findViewById(R.id.tvw_tips);
        tvw_time = (TextView) view.findViewById(R.id.tvw_time);
        ivw_refresh = (ImageView) view.findViewById(R.id.ivw_refresh);
        pbr_loading = (ProgressBar) view.findViewById(R.id.pbr_loading);
        resetTvw_time();
        addView(view);
    }

    @Override
    public void onComlete(RefreshLayout materialRefreshLayout) {
        //记录刷新时间
        SharedPreferUtil.setString("material_refresh","refresh_time", DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm"));
        tvw_tips.setText("下拉可以刷新");
        pbr_loading.setVisibility(View.GONE);
        ivw_refresh.setVisibility(View.VISIBLE);
        is_arrow_start_refresh_anim = false;
        is_arrow_start_normal_anim = true;
        is_fresh = true;
    }

    @Override
    public void onBegin(RefreshLayout materialRefreshLayout) {

    }

    @Override
    public void onPull(RefreshLayout materialRefreshLayout, float fraction) {
        if(fraction>1){
            tvw_tips.setText("松开立即刷新");
            rotate_refresh();
        }else{
            tvw_tips.setText("下拉可以刷新");
            rotate_normal();
        }
    }

    @Override
    public void onRelease(RefreshLayout materialRefreshLayout, float fraction) {

    }

    @Override
    public void onRefreshing(RefreshLayout materialRefreshLayout) {
        resetIvw_arrow();
        pbr_loading.setVisibility(View.VISIBLE);
        resetTvw_time();
    }
    private void rotate_refresh(){
        if(!is_arrow_start_refresh_anim) {
            RotateAnimation animation = new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.setDuration(300);
            animation.setFillAfter(true);
            ivw_refresh.startAnimation(animation);
            is_arrow_start_refresh_anim = true;
            is_arrow_start_normal_anim = false;
        }
    }
    private void rotate_normal(){
        if(!is_arrow_start_normal_anim) {
            RotateAnimation animation = new RotateAnimation(-180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.setDuration(300);
            animation.setFillAfter(true);
            ivw_refresh.startAnimation(animation);
            is_arrow_start_normal_anim = true;
            is_arrow_start_refresh_anim = false;
        }
    }
    private void resetIvw_arrow(){
        RotateAnimation animation = new RotateAnimation(-180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        ivw_refresh.startAnimation(animation);
        ivw_refresh.setVisibility(View.GONE);
        ivw_refresh.clearAnimation();
    }

    private void resetTvw_time(){
        try {
            String last_time = SharedPreferUtil.getString("material_refresh","refresh_time");
            String tmp = DateUtil.formatDate(last_time,"yyyy-MM-dd HH:mm","yyyy-MM-dd");
            if(!StringUtil.isEmpty(last_time)&&DateUtil.isToday(tmp)){
                String time = DateUtil.formatDate(new Date(),"HH:mm");
                tvw_time.setText("最后更新：今天 "+time);
            }else{
                String date = DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm");
                tvw_time.setText("最后更新："+date);
            }
        }catch (Exception e){

        }

    }
}
