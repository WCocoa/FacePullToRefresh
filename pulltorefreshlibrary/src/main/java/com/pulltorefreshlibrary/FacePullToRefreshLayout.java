package com.pulltorefreshlibrary;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * @Package com.pulltorefreshlibrary
 * @作 用:笑脸下拉刷新，基于android-Ultra-Pull-To-Refresh开源库实现的下拉刷新
 * @创 建 人: linguoding
 * @日 期: 2016/3/10
 *
 */
public class FacePullToRefreshLayout extends PtrFrameLayout {
    private FacePullToRefreshHeader aiJiaPullToRefreshHeader;
    private RefreshListener refreshListener;

    public FacePullToRefreshLayout(Context context) {
        super(context);
        initViews();
    }

    public FacePullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public FacePullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        aiJiaPullToRefreshHeader = new FacePullToRefreshHeader(getContext());
        setHeaderView(aiJiaPullToRefreshHeader);
        addPtrUIHandler(aiJiaPullToRefreshHeader);
        setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (refreshListener != null) {
                    refreshListener.onRefresh(frame);
                }
            }
        });
    }

    public FacePullToRefreshHeader getHeader() {
        return aiJiaPullToRefreshHeader;
    }

    public void setLastUpdateTimeKey(String key) {
        if (aiJiaPullToRefreshHeader != null) {
            aiJiaPullToRefreshHeader.setLastUpdateTimeKey(key);
        }
    }

    /**
     * Using an object to specify the last update time.
     * 指定使用对象最后更新时间。
     *
     * @param object
     */
    public void setLastUpdateTimeRelateObject(Object object) {
        if (aiJiaPullToRefreshHeader != null) {
            aiJiaPullToRefreshHeader.setLastUpdateTimeRelateObject(object);
        }
    }

    /**
     * 刷新的监听器
     */
    public interface RefreshListener {
        void onRefresh(PtrFrameLayout frame);
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }
}
