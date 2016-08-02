package com.facepulltorefresh.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facepulltorefresh.R;


/**
 * @Package com.daoda.aijiacommunity
 * @作 用:TitleBar的帮助类，先创建一个 ViewGroup 来作为视图的父 View，把用户定义的 View，和 toolBar 依次 Add 到 ViewGroup 中
 * @创 建 人: linguoding
 * @日 期: 2016/1/9
 */
public class TitleBarHelper {
    /*上下文，创建view的时候需要用到*/
    private Context mContext;

    /*base view*/
    private CoordinatorLayout mContentView;

    /*用户定义的view*/
    private View mUserView;

    /*titleHeaderBar*/
    private TitleBar titleHeaderBar;

    /*视图构造器*/
    private LayoutInflater mInflater;

    FrameLayout.LayoutParams params;
    int toolBarSize;


    /*
    * 两个属性
    * 1、titlebar是否悬浮在窗口之上
    * 2、titlebar的高度获取
    * */
    private static int[] ATTRS = {R.attr.windowActionBarOverlay, R.attr.actionBarSize};

    public TitleBarHelper(Context context, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        /*初始化整个内容*/
        initContentView();
        /*初始化用户定义的布局*/
        initUserView(layoutId);
        /*初始化toolbar*/
        initTitleHeaderBar();
    }

    public TitleBarHelper(Context context, View view) {
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        /*初始化整个内容*/
        initContentView();
        /*初始化用户定义的布局*/
        initUserView(view);
        /*初始化toolbar*/
        initTitleHeaderBar();
    }

    private void initContentView() {
        /*直接创建一个增强帧布局，作为视图容器的父容器*/
        mContentView = new CoordinatorLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);

    }

    private void initUserView(int id) {
        initUserView(mInflater.inflate(id, null));
    }

    private void initTitleHeaderBar() {
        /*通过inflater获取toolbar的布局文件*/
        View view = mInflater.inflate(R.layout.common_title_bar, mContentView, true);
        titleHeaderBar = (TitleBar) view.findViewById(R.id.common_title_bar);
        View rootView = view.findViewById(R.id.ll_common_title_bar);
        rootView.setPadding(0, getStatusBarHeight(mContext), 0, 0);

    }


    private void initUserView(View view) {
        mUserView = view;
        params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);
        /*获取主题中定义的悬浮标志*/
        boolean overly = typedArray.getBoolean(0, false);
        /*获取主题中定义的toolbar的高度*/
        toolBarSize = (int) typedArray.getDimension(1, (int) mContext.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
        typedArray.recycle();
        /*如果是悬浮状态，则不需要设置间距*/
        params.topMargin = overly ? 0 : toolBarSize;
        mContentView.addView(mUserView, params);

    }


    /**
     * 设置悬浮
     */
    public void setOverlay() {
        CoordinatorLayout.LayoutParams coorLayoutParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        coorLayoutParams.setMargins(0, 0, 0, 0);
        mUserView.setLayoutParams(coorLayoutParams);
    }

    /**
     * 设置不悬浮
     */
    public void setUnOverlay() {
        CoordinatorLayout.LayoutParams coorLayoutParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        coorLayoutParams.setMargins(0, toolBarSize, 0, 0);
        mUserView.setLayoutParams(coorLayoutParams);
    }

    public CoordinatorLayout getContentView() {
        return mContentView;
    }

    public TitleBar getTitleHeaderBar() {
        return titleHeaderBar;
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

}
