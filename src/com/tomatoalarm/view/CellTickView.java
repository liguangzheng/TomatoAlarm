package com.tomatoalarm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.tomatoalarm.R;

public class CellTickView extends BaseTickView {

    private Paint mPaint, mFramePaint;
    private FontMetrics mFontMetrics;
    private int mRimColor;
    private int mInsideStartColor;
    private int mInsideEndColor;

    private int mCenterX;
    private int mCenterY;
    private int mRadius;
    private int mRimWidth;
    private float mTouchX;
    private float mTouchY;

    public CellTickView(Context context) {
        super(context);
        init();
        mRimWidth = getResources().getDimensionPixelSize(
                R.dimen.width_celltickview_rim);
        mRimColor = getResources().getColor(R.color.celltickview_rim);
        mInsideStartColor = getResources().getColor(
                R.color.celltickview_start_inside);
        mInsideEndColor = getResources().getColor(
                R.color.celltickview_end_inside);
    }

    public CellTickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        // 获取属性
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CellTickView);
        mRimWidth = a.getDimensionPixelSize(
                R.styleable.CellTickView_rimwidth,
                getResources().getDimensionPixelSize(
                        R.dimen.width_celltickview_rim));
        mRimColor = a.getColor(R.styleable.CellTickView_rimcolor,
                getResources().getColor(R.color.celltickview_rim));
        mInsideStartColor = a.getColor(
                R.styleable.CellTickView_insidestartcolor, getResources()
                        .getColor(R.color.celltickview_start_inside));
        mInsideEndColor = a.getColor(R.styleable.CellTickView_insideendcolor,
                getResources().getColor(R.color.celltickview_end_inside));
        a.recycle();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(getResources()
                .getDimension(R.dimen.textsize_xxlarge));
        mFontMetrics = mPaint.getFontMetrics();

        mFramePaint = new Paint();
        mFramePaint.setAntiAlias(true);
        mFramePaint.setStyle(Paint.Style.STROKE);
        mFramePaint.setStrokeWidth(0);

        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((mTouchX - mCenterX) * (mTouchX - mCenterX)
                        + (mTouchY - mCenterY) * (mTouchY - mCenterY) < (mRadius - mRimWidth)
                        * (mRadius - mRimWidth)) {
                    // 点击中心区域才能出发点击事件
                    float progress = getProgress();
                    if (progress > 0.0f && progress < 1.0f) {
                        stop();
                        updateRemainTime(mTotalTime);
                        invalidate();
                    } else {
                        start();
                    }
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchX = event.getX();
        mTouchY = event.getY();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;
        mRadius = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(255, 255, 255, 255);
        // 外框
        mPaint.setColor(mRimColor);
        RectF rim = new RectF(mCenterX - mRadius, mCenterY - mRadius, mCenterX
                + mRadius, mCenterY + mRadius);
        float progress = getProgress();
        canvas.drawArc(rim, 360 * progress - 90, 360 * (1 - progress), true,
                mPaint);
        // 内环
        if (progress == 1.0f) {
            canvas.drawArc(rim, 270, 360, true, mPaint);
            mPaint.setColor(mInsideEndColor);
        } else {
            mPaint.setColor(mInsideStartColor);
        }
        canvas.drawCircle(mCenterX, mCenterY, mRadius - mRimWidth, mPaint);

        mPaint.setColor(getResources().getColor(R.color.white));
        String text = getRemainTime();
        float textWidth = mPaint.measureText(text);
        canvas.drawText(text, mCenterX - textWidth / 2, mCenterY
                - (mFontMetrics.descent - mFontMetrics.ascent) / 2
                + (mFontMetrics.leading - mFontMetrics.ascent), mPaint);
    }

}
