
package com.tomatoalarm.service.bound;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.tomatoalarm.MainActivity;
import com.tomatoalarm.R;

/**
 * 绑定服务。一但用户解除绑定或停止绑定Activity（应用程序），服务会自动停止。即，它是随绑定activity的生命周期运行的。
 * 
 * @author liguangzheng
 */
public class TickService extends Service {

    public static final int MAX_DRIP_TIME = 3 * 60 * 60;// 3小时
    public static final int MIN_DRIP_TIME = 1 * 60;// 15分钟

    private static final int NOTIFICATION_ID = R.string.app_name;// 通知栏id
    private static final int MSG_REPORT_DRIP_TIME = 1;// 消息id
    private static final int MSG_LOOP_TIME = 1 * 1000;// 1秒钟

    private int mRemainderTime = MIN_DRIP_TIME;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case MSG_REPORT_DRIP_TIME: {
                    // 通知所有注册用户，信息更新
                    final int N = mCallbacks.beginBroadcast();
                    for (int i = 0; i < N; i++) {
                        try {
                            mCallbacks.getBroadcastItem(i).valueChanged(mRemainderTime);
                            showNotification(NOTIFICATION_ID, "番茄钟", "倒计时：" + mRemainderTime);
                            mRemainderTime--;
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    mCallbacks.finishBroadcast();

                    // 每隔1秒钟，进行消息更新
                    if (mRemainderTime >= 0) {
                        sendMessageDelayed(obtainMessage(MSG_REPORT_DRIP_TIME), MSG_LOOP_TIME);
                    } else {
                        // 服务结束
                    }
                }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    private NotificationManager mNM;
    private final RemoteCallbackList<IDripServiceCallback> mCallbacks = new RemoteCallbackList<IDripServiceCallback>();

    private final IDripService.Stub mBinder = new IDripService.Stub() {
        public void registerCallback(IDripServiceCallback cb) {
            if (cb != null)
                mCallbacks.register(cb);
        }

        public void unregisterCallback(IDripServiceCallback cb) {
            if (cb != null)
                mCallbacks.unregister(cb);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        showNotification(NOTIFICATION_ID, "番茄钟", "已启动");
        mHandler.sendEmptyMessage(MSG_REPORT_DRIP_TIME);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNM.cancel(NOTIFICATION_ID);
        mHandler.removeMessages(MSG_REPORT_DRIP_TIME);
        mCallbacks.kill();
    }

    /**
     * 显示通知信息
     * 
     * @param notificationid
     * @param title
     * @param content
     */
    private void showNotification(int notificationid, String title, String content) {
        Notification n = new Notification(R.drawable.ic_launcher, content, System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        n.setLatestEventInfo(this, title, content, contentIntent);
        mNM.notify(notificationid, n);
    }

}
