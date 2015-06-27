package com.tomatoalarm.common;

public class Constant {

    private Constant() {
    }

    // 默认番茄钟时长
    public static final int DEFAULT_ALARM_TIME = 10;//25 * 60;

    // SharedPreferences默认番茄钟时长设置参数
    public static final String SHARED_PREFERENCE = "tomatoalarm";

    // SharedPreferences默认番茄钟时长设置参数
    public static final String SHARED_PREFERENCE_ALARM_TIME = "alarm_time";

    // 滴答服务action
    public static final String ACTION_TICK_SERVICE = "com.tomatoalarm.service.action.TICK";
}
