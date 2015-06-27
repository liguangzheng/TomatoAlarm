package com.tomatoalarm.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;

import com.tomatoalarm.common.Constant;

public class BaseTickView extends View {
    private StringBuilder remainTime = new StringBuilder();
    protected int mTotalTime = Constant.DEFAULT_ALARM_TIME;// 单位s
    protected int mRemainTime;// 单位s

    public BaseTickView(Context context) {
        super(context);
        init(context);
    }

    public BaseTickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        // 获取当前番茄钟时长
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                Constant.SHARED_PREFERENCE, Activity.MODE_PRIVATE);
        mTotalTime = sharedPreferences.getInt(
                Constant.SHARED_PREFERENCE_ALARM_TIME,
                Constant.DEFAULT_ALARM_TIME);
        mRemainTime = mTotalTime;
    }

    /**
     * 获取当前进度值
     * 
     * @return
     */
    public float getProgress() {
        return 1.0f - (float) mRemainTime / (float) mTotalTime;
    }

    /**
     * 获取番茄钟总时长
     * 
     * @return
     */
    public float getTotalTime() {
        return mTotalTime;
    }

    /**
     * 更新剩余时长
     * 
     * @param remaintime
     */
    public void updateRemainTime(int remaintime) {
        mRemainTime = remaintime;
    }

    /**
     * 获取剩余时长HH:MM:SS格式实现
     * 
     * @return
     */
    public String getRemainTime() {
        remainTime.delete(0, remainTime.length());
        int second = mRemainTime % 60;
        int minute = mRemainTime / 60;
        int hour = minute / 60;
        minute = minute % 60;
        if (hour < 10) {
            remainTime.append("0");
        }
        remainTime.append(hour);
        remainTime.append(":");
        if (minute < 10) {
            remainTime.append("0");
        }
        remainTime.append(minute);
        remainTime.append(":");
        if (second < 10) {
            remainTime.append("0");
        }
        remainTime.append(second);
        return remainTime.toString();
    }

    /**
     * 启动番茄钟
     */
    public void start() {
        // 启动服务
        Intent intent = new Intent(Constant.ACTION_TICK_SERVICE);
        intent.putExtra(Constant.SHARED_PREFERENCE_ALARM_TIME, getTotalTime());
        getContext().startService(intent);
    }

    /**
     * 关闭番茄钟
     */
    public void stop() {
        getContext().stopService(new Intent(Constant.ACTION_TICK_SERVICE));
    }
}
