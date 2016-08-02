package com.facepulltorefresh.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facepulltorefresh.R;

/**
 * @Package com.daoda.aijiacommunity.common.widget
 * @作 用:自定义组合标题的控件---IOS风格
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2016/6/2
 */
public class TitleBar extends RelativeLayout {

    private TextView mCenterTitleTextView;
    private ImageView mLeftReturnImageView;
    private RelativeLayout mLeftViewContainer;
    private RelativeLayout mRightViewContainer;
    private RelativeLayout mCenterViewContainer;

    private String mTitle;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(getHeaderViewLayoutId(), this);
        mLeftViewContainer = (RelativeLayout) findViewById(R.id.ly_title_bar_left);
        mCenterViewContainer = (RelativeLayout) findViewById(R.id.ly_title_bar_center);
        mRightViewContainer = (RelativeLayout) findViewById(R.id.ly_title_bar_right);
        mLeftReturnImageView = (ImageView) findViewById(R.id.iv_title_bar_left);
        mCenterTitleTextView = (TextView) findViewById(R.id.tv_title_bar_title);
    }

    protected int getHeaderViewLayoutId() {
        return R.layout.title_bar;
    }

    public ImageView getLeftImageView() {
        return mLeftReturnImageView;
    }

    public TextView getTitleTextView() {
        return mCenterTitleTextView;
    }

    public void setTitle(String title) {
        mTitle = title;
        mCenterTitleTextView.setText(title);
    }

    public String getTitle() {
        return mTitle;
    }

    private LinearLayout.LayoutParams makeLayoutParams(View view) {
        ViewGroup.LayoutParams lpOld = view.getLayoutParams();
        LinearLayout.LayoutParams lp = null;
        if (lpOld == null) {
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            lp = new LinearLayout.LayoutParams(lpOld.width, lpOld.height);
        }
        return lp;
    }

    /**
     * 设置左边的view
     *
     * @param view
     */
    public void setCustomizedLeftView(View view) {
        mLeftReturnImageView.setVisibility(GONE);
        LinearLayout.LayoutParams lp = makeLayoutParams(view);
        lp.gravity = Gravity.CENTER;
        getLeftViewContainer().addView(view, lp);
    }

    /**
     * 设置左边的布局
     *
     * @param layoutId
     */
    public void setCustomizedLeftView(int layoutId) {
        View view = inflate(getContext(), layoutId, null);
        setCustomizedLeftView(view);
    }

    /**
     * 设置中间的view
     *
     * @param view
     */
    public void setCustomizedCenterView(View view) {
        mCenterTitleTextView.setVisibility(GONE);
        LinearLayout.LayoutParams lp = makeLayoutParams(view);
        lp.gravity = Gravity.CENTER;
        getCenterViewContainer().addView(view, lp);
    }

    /**
     * 设置中间布局
     *
     * @param layoutId
     */
    public void setCustomizedCenterView(int layoutId) {
        View view = inflate(getContext(), layoutId, null);
        setCustomizedCenterView(view);
    }

    /**
     * 设置右边的View
     *
     * @param view View添加到右边
     */
    public void setCustomizedRightView(View view) {
        LinearLayout.LayoutParams lp = makeLayoutParams(view);
        lp.gravity = Gravity.CENTER;
        getRightViewContainer().addView(view, lp);
    }

    public void setCustomizedRightView(int layoutId) {
        View view = inflate(getContext(), layoutId, null);
        setCustomizedRightView(view);
    }

    public RelativeLayout getLeftViewContainer() {
        return mLeftViewContainer;
    }

    public RelativeLayout getCenterViewContainer() {
        return mCenterViewContainer;
    }

    public RelativeLayout getRightViewContainer() {
        return mRightViewContainer;
    }

    public void setLeftOnClickListener(OnClickListener l) {
        mLeftViewContainer.setOnClickListener(l);
    }

    public void setCenterOnClickListener(OnClickListener l) {
        mCenterViewContainer.setOnClickListener(l);
    }

    public void setRightOnClickListener(OnClickListener l) {
        mRightViewContainer.setOnClickListener(l);
    }
}
