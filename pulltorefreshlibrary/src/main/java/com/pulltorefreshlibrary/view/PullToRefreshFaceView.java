package com.pulltorefreshlibrary.view;

import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.pulltorefreshlibrary.R;

/**
 * @Package com.pulltorefreshlibrary.view
 * @作 用:下拉刷新的笑脸
 * @创 建 人: linguoding
 * @日 期: 2016/3/9
 */
public class PullToRefreshFaceView extends View {
    private Paint paint;//画笔
    private Paint eyePaint;
    private int backgroupColor;
    private int width;
    private int height;
    private int centerWidth;
    private int centerHeight;
    private float degrees;
    private float radius = 0;
    private float sweepRadius = 180;

    private int radiusCircle;
    private int eyeRadius;
    private int eyeBallRadius;
    private boolean isDrawFace = false;

    AnimatorSet set = new AnimatorSet();


    public PullToRefreshFaceView(Context context) {
        super(context);
        initView();
    }

    public PullToRefreshFaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        backgroupColor = array.getColor(R.styleable.LoadingView_backgroupColor, Color.BLACK);
        array.recycle();
        initView();

    }

    public void setDegrees(float degrees) {
        this.degrees = degrees;
        invalidate();
    }

    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();
    }

    public void setSweepRadius(float sweepRadius) {
        this.sweepRadius = sweepRadius;
        invalidate();
    }

    public void setRadiusCircle(int radiusCircle) {
        this.radiusCircle = radiusCircle;
    }

    public void setEyeRadius(int eyeRadius) {
        this.eyeRadius = eyeRadius;

    }

    public void setEyeBallRadius(int eyeBallRadius) {
        this.eyeBallRadius = eyeBallRadius;

    }

    public void setDrawFace(boolean isDrawFace) {
        this.isDrawFace = isDrawFace;
    }

    public int getRadiusCircle() {
        return radiusCircle;
    }

    public int getEyeRadius() {
        return eyeRadius;
    }

    public int getEyeBallRadius() {
        return eyeBallRadius;
    }

    public boolean isDrawFace() {
        return isDrawFace;
    }

    public void setBackgroupColor(int backgroupColor) {
        this.backgroupColor = backgroupColor;
    }

    /**
     * 下拉过程渐变的效果
     *
     * @param sunRadius
     * @param per
     */
    public void setPerView(int sunRadius, float per) {
        if (per >= 0.5) {
            isDrawFace = true;
        } else {
            isDrawFace = false;
        }
        per = Math.min(per, 1);
        float tempRadius = sunRadius * per;
        this.radiusCircle = (int) tempRadius;
        invalidate();
    }





    private void initView() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(backgroupColor);
        //眼眶画笔
        eyePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        eyePaint.setColor(Color.WHITE);
        eyePaint.setStyle(Paint.Style.FILL);
        createAnimatorSet();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /*super.onMeasure(widthMeasureSpec, heightMeasureSpec);*/
        width = measureResult(widthMeasureSpec);
        height = measureResult(heightMeasureSpec);
        centerWidth = width >> 1;
        centerHeight = height >> 1;
        setMeasuredDimension(width, height);

    }

    private int measureResult(int widthMeasureSpec) {
        int result = 0;
        int sizeSpec = MeasureSpec.getSize(widthMeasureSpec);
        int modeSpec = MeasureSpec.getMode(widthMeasureSpec);
        if (modeSpec == MeasureSpec.EXACTLY) {
            result = sizeSpec;
        } else {
            result = 400;
            if (modeSpec == MeasureSpec.AT_MOST) {
                result = Math.min(result, sizeSpec);
            }
        }
        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerWidth = width / 2;
        centerHeight = height / 2;
        canvas.translate(centerWidth, centerHeight);
        radiusCircle = Math.min(centerWidth, centerHeight);
        //画圆
        canvas.drawCircle(0, 0, radiusCircle, paint);
        if (isDrawFace) {
            //画两个眼框
            eyeRadius = Math.min(centerWidth / 3, centerHeight / 3);
            canvas.drawCircle(-centerWidth / 2, -centerHeight >> 3, eyeRadius, eyePaint);
            canvas.drawCircle(centerWidth / 2, -centerHeight >> 3, eyeRadius, eyePaint);
            //画嘴巴
            canvas.drawArc(new RectF(-eyeRadius, 0, eyeRadius, eyeRadius * 2), 0, 180, true, eyePaint);

            canvas.save();
            //画两个眼睛
            eyeBallRadius = Math.min(centerWidth >> 3, centerHeight >> 3);
            canvas.translate(-centerWidth / 2, -centerHeight >> 3);
            canvas.rotate(-degrees);
            canvas.drawCircle(0, (eyeRadius >> 1), eyeBallRadius, paint);
            canvas.restore();

            canvas.save();

            canvas.translate(centerWidth / 2, -centerHeight >> 3);
            canvas.rotate(-degrees);
            canvas.drawCircle(0, (eyeRadius >> 1), eyeBallRadius, paint);
            canvas.restore();

            //画两个眼皮
            canvas.save();
            canvas.translate(-centerWidth / 2, -centerHeight >> 3);
            canvas.drawArc(new RectF(-eyeRadius, -eyeRadius, eyeRadius, eyeRadius), -this.radius, -this.sweepRadius, false, paint);
            canvas.restore();

            canvas.save();
            canvas.translate(centerWidth / 2, -centerHeight >> 3);
            canvas.drawArc(new RectF(-eyeRadius, -eyeRadius, eyeRadius, eyeRadius), -this.radius, -this.sweepRadius, false, paint);
            canvas.restore();
        }


    }

    private void createAnimatorSet() {
        ValueAnimator rotateAnimator = ValueAnimator.ofFloat(0, 360).setDuration(3000);
        rotateAnimator.setInterpolator(new LinearInterpolator());
        rotateAnimator.setRepeatCount(-1);
        rotateAnimator.setEvaluator(new FloatEvaluator());
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degrees = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator translationAnimator = ValueAnimator.ofFloat(0, 90, 0).setDuration(3000);
        translationAnimator.setInterpolator(new LinearInterpolator());
        translationAnimator.setRepeatCount(-1);
        translationAnimator.setEvaluator(new FloatEvaluator());
        translationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radius = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator sweepAnimator = ValueAnimator.ofFloat(180, 0, 180).setDuration(3000);
        sweepAnimator.setInterpolator(new LinearInterpolator());
        sweepAnimator.setRepeatCount(-1);
        sweepAnimator.setEvaluator(new FloatEvaluator());
        sweepAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepRadius = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        set.playTogether(rotateAnimator, translationAnimator, sweepAnimator);
    }

    public void startAnimators() {
        set.start();
    }

    public void stopAnimators() {
        set.cancel();
    }
}
