package com.tomatoalarm.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.core.fragment.BaseFragment;
import com.tomatoalarm.R;
import com.tomatoalarm.common.Constant;
import com.tomatoalarm.service.unbound.TickService;
import com.tomatoalarm.view.CellTickView;

public class AlarmFragment extends BaseFragment {

    private CellTickView mCellTickView;
    private TextView mTextViewTickValue;

    private BroadcastReceiver mDripServiceBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            int remainSecond = intent.getIntExtra(
                    TickService.TICK_BROADCAST_VALUE, 0);
            mCellTickView.updateRemainTime(remainSecond);
            mCellTickView.invalidate();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_alarm_show, null);
        mTextViewTickValue = (TextView) layout
                .findViewById(R.id.textview_alarm_show_value);
        mCellTickView = (CellTickView) layout
                .findViewById(R.id.celltickview_alarm);
        return layout;
    }

    @Override
    public void onPause() {
        super.onPause();
        // 注销广播接收器
        getActivity().unregisterReceiver(mDripServiceBroadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 动态注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TickService.TICK_BROADCAST_ACTION);
        getActivity().registerReceiver(mDripServiceBroadcastReceiver,
                intentFilter);

    }

}
