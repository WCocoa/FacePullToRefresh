package com.pulltorefreshlibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pulltorefreshlibrary.view.PullToRefreshFaceView;

import java.text.SimpleDateFormat;
import java.util.Date;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class FacePullToRefreshHeader extends FrameLayout implements PtrUIHandler {
    private final static String KEY_SharedPreferences = "face_ptr_classic_last_update";
    private TextView mTitleTextView;
    private PullToRefreshFaceView mLoadView;
    private long mLastUpdateTime = -1;
    private TextView mLastUpdateTextView;
    private String mLastUpdateTimeKey;
    private static SimpleDateFormat sDataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private boolean mShouldShowLastUpdate;

    private LastUpdateTimeUpdater mLastUpdateTimeUpdater = new LastUpdateTimeUpdater();


    public FacePullToRefreshHeader(Context context) {
        super(context);
        initViews(null);
    }

    public FacePullToRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public FacePullToRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(attrs);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mLastUpdateTimeUpdater != null) {
            mLastUpdateTimeUpdater.stop();
        }
    }

    private void initViews(AttributeSet attrs) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.face_pull_to_refresh_header, this);
        mTitleTextView = (TextView) header.findViewById(R.id.ptr_face_header_title);
        mLastUpdateTextView = (TextView) header.findViewById(R.id.ptr_face_header_last_update);
        mLoadView = (PullToRefreshFaceView) header.findViewById(R.id.ptr_ai_jia_load_view);

    }


    private void tryUpdateLastUpdateTime() {
        if (TextUtils.isEmpty(mLastUpdateTimeKey) || !mShouldShowLastUpdate) {
            mLastUpdateTextView.setVisibility(GONE);
        } else {
            String time = getLastUpdateTime();
            if (TextUtils.isEmpty(time)) {
                mLastUpdateTextView.setVisibility(GONE);
            } else {
                mLastUpdateTextView.setVisibility(VISIBLE);
                mLastUpdateTextView.setText(time);
            }
        }
    }


    public void setLastUpdateTimeKey(String key) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        mLastUpdateTimeKey = key;
    }

    public void setLastUpdateTimeRelateObject(Object object) {
        setLastUpdateTimeKey(object.getClass().getName());
    }

    private String getLastUpdateTime() {

        if (mLastUpdateTime == -1 && !TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = getContext().getSharedPreferences(KEY_SharedPreferences, 0).getLong(mLastUpdateTimeKey, -1);
        }
        if (mLastUpdateTime == -1) {
            return null;
        }
        long diffTime = new Date().getTime() - mLastUpdateTime;
        int seconds = (int) (diffTime / 1000);
        if (diffTime < 0) {
            return null;
        }
        if (seconds <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(getContext().getString(R.string.face_ptr_last_update));

        if (seconds < 60) {
            sb.append(seconds + getContext().getString(R.string.face_ptr_seconds_ago));
        } else {
            int minutes = (seconds / 60);
            if (minutes > 60) {
                int hours = minutes / 60;
                if (hours > 24) {
                    Date date = new Date(mLastUpdateTime);
                    sb.append(sDataFormat.format(date));
                } else {
                    sb.append(hours + getContext().getString(R.string.face_ptr_hours_ago));
                }

            } else {
                sb.append(minutes + getContext().getString(R.string.face_ptr_minutes_ago));
            }
        }
        return sb.toString();
    }

    private void resetView() {
        mLoadView.stopAnimators();
        mLoadView.setVisibility(INVISIBLE);

    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
        mShouldShowLastUpdate = true;
        tryUpdateLastUpdateTime();
    }


    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        mShouldShowLastUpdate = true;
        tryUpdateLastUpdateTime();
        mLoadView.setVisibility(VISIBLE);
        mTitleTextView.setVisibility(VISIBLE);
        mTitleTextView.setText(getResources().getString(R.string.face_ptr_pull_down_to_refresh));

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mShouldShowLastUpdate = false;


        mLoadView.startAnimators();
        mTitleTextView.setVisibility(VISIBLE);
        mTitleTextView.setText(R.string.face_ptr_refreshing);
        tryUpdateLastUpdateTime();
        mLastUpdateTimeUpdater.stop();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        mLoadView.stopAnimators();
        mTitleTextView.setVisibility(VISIBLE);
        mTitleTextView.setText(getResources().getString(R.string.face_ptr_refresh_complete));

        // update last update time
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(KEY_SharedPreferences, 0);
        if (!TextUtils.isEmpty(mLastUpdateTimeKey)) {
            mLastUpdateTime = new Date().getTime();
            sharedPreferences.edit().putLong(mLastUpdateTimeKey, mLastUpdateTime).commit();
        }
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        float percent = Math.min(1f, ptrIndicator.getCurrentPercent());
        if (status == PtrFrameLayout.PTR_STATUS_PREPARE) {
            mLoadView.setDegrees(percent * 360 * 4);
            ViewCompat.setScaleX(mLoadView, percent);
            ViewCompat.setScaleY(mLoadView, percent);
            mLoadView.setEyeRadius((int) (mLoadView.getEyeRadius()*percent));
            mLoadView.setEyeBallRadius((int) (mLoadView.getEyeBallRadius()*percent));
            mLoadView.setPerView(mLoadView.getRadiusCircle(), percent);
            if (percent < 0.5) {
                mLoadView.setRadius((percent * 180));
                mLoadView.setSweepRadius((180 - percent * 360));
            } else {
                percent = (float) (percent - 0.5);
                mLoadView.setRadius((90 - percent * 180));
                mLoadView.setSweepRadius((percent * 360));
            }

        }
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();//当前位置
        final int lastPos = ptrIndicator.getLastPosY();//上一个位置
        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {

                crossRotateLineFromBottomUnderTouch(frame);

            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {

                crossRotateLineFromTopUnderTouch(frame);

            }
        }
    }



    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            mTitleTextView.setVisibility(VISIBLE);
            mTitleTextView.setText(R.string.face_ptr_release_to_refresh);
        }
    }


    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        mTitleTextView.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            mTitleTextView.setText(getResources().getString(R.string.face_ptr_pull_down_to_refresh));
        } else {
            mTitleTextView.setText(getResources().getString(R.string.face_ptr_pull_down_to_refresh));
        }
    }


    private class LastUpdateTimeUpdater implements Runnable {
        private boolean mRunning = false;

        private void start() {
            if (TextUtils.isEmpty(mLastUpdateTimeKey)) {
                return;
            }
            mRunning = true;
            run();
        }

        private void stop() {
            mRunning = false;
            removeCallbacks(this);
        }

        @Override
        public void run() {
            tryUpdateLastUpdateTime();
            if (mRunning) {
                postDelayed(this, 1000);
            }
        }
    }
}
