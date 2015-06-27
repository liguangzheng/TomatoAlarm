package com.tomatoalarm.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.tomatoalarm.R;

public class LineTickView extends BaseTickView {

    private Paint mPaint;
    private int mLineWidth;
    private int mLineColor;

    public LineTickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 获取属性
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LineTickView);
        mLineWidth = a
                .getDimensionPixelSize(
                        R.styleable.LineTickView_linewidth,
                        getResources().getDimensionPixelSize(
                                R.dimen.width_linetickview_line));
        mLineColor = a.getColor(R.styleable.LineTickView_linecolor,
                getResources().getColor(R.color.black));
        a.recycle();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制竖线
        mPaint.setColor(mLineColor);
        canvas.drawLine(getWidth(), 0, getWidth(), getHeight(), mPaint);
    }
}
