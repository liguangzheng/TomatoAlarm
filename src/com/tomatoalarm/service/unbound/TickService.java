package com.tomatoalarm.service.unbound;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import com.tomatoalarm.MainActivity;
import com.tomatoalarm.R;
import com.tomatoalarm.common.Constant;

/**
 * 绑定服务。一但用户解除绑定或停止绑定Activity（应用程序），服务会自动停止。即，它是随绑定activity的生命周期运行的。
 * 
 * @author liguangzheng
 */
public class TickService extends Service {

    public static final String TICK_BROADCAST_ACTION = "com.tomatoalarm.broadcast.tickservice";// 广播action
    public static final String TICK_BROADCAST_VALUE = "value";// 参数key

    private NotificationManager mNM;
    private MyNotification mNotification;
    private static final int NOTIFICATION_ID = R.string.app_name;// 通知栏id

    private static final int MSG_REPORT_DRIP_TIME = 1;// 消息id
    private static final int MSG_LOOP_TIME = 1 * 1000;// 1秒钟

    private int mRemainTime;// 服务需要执行剩余时长

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_REPORT_DRIP_TIME:
                sendBroadcast(mRemainTime);
                if (null != mNotification) {
                    mNotification.contentView.setTextViewText(
                            R.id.tv_notification_value,
                            String.valueOf(mRemainTime));
                    mNM.notify(mNotification.getId(), mNotification);
                }
                mRemainTime--;
                // 每隔1秒钟，进行消息更新
                if (mRemainTime >= 0) {
                    sendMessageDelayed(obtainMessage(MSG_REPORT_DRIP_TIME),
                            MSG_LOOP_TIME);
                } else {
                    // 服务结束
                    stopSelf();
                }
                break;
            default:
                super.handleMessage(msg);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mHandler.sendEmptyMessage(MSG_REPORT_DRIP_TIME);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mRemainTime = intent.getIntExtra(Constant.SHARED_PREFERENCE_ALARM_TIME,
                Constant.DEFAULT_ALARM_TIME);
        showNotification();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mNotification) {
            mNM.cancel(mNotification.getId());
        }
        mHandler.removeMessages(MSG_REPORT_DRIP_TIME);
    }

    /**
     * 显示通知信息
     * 
     * @param notificationid
     * @param title
     * @param content
     */
    private void showNotification() {
        mNotification = new MyNotification(R.drawable.app_icon, getResources()
                .getString(R.string.tomato_alarm_start),
                System.currentTimeMillis());
        mNotification.flags = Notification.FLAG_NO_CLEAR;// 不能够自动清除
        RemoteViews rv = new RemoteViews(this.getPackageName(),
                R.layout.notification);
        rv.setTextViewText(R.id.tv_notification_value,
                String.valueOf(mRemainTime));
        mNotification.contentView = rv;
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, 0);
        mNotification.contentIntent = contentIntent;
        mNotification.setId(NOTIFICATION_ID);
        mNM.notify(mNotification.getId(), mNotification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 发送广播，更新value
     * 
     * @param value
     */
    private void sendBroadcast(int value) {
        Intent i = new Intent(TICK_BROADCAST_ACTION);
        i.putExtra(TICK_BROADCAST_VALUE, value);
        sendBroadcast(i);
    }

    class MyNotification extends Notification {
        private int id;

        public MyNotification(int icon, CharSequence text, long time) {
            super(icon, text, time);
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}