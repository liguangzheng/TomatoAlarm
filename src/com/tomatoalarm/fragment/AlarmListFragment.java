package com.tomatoalarm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.core.fragment.BaseFragment;
import com.tomatoalarm.AddAlarmActivity;
import com.tomatoalarm.R;
import com.tomatoalarm.adapter.AlarmListAdapter;
import com.tomatoalarm.beans.AlarmObject;

public class AlarmListFragment extends BaseFragment {

    private ListView mMainListView;
    private AlarmListAdapter mAlarmListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_list, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 首页内容
        getView().findViewById(R.id.ibtn_title_left).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // showMenu();
                    }
                });
        getView().findViewById(R.id.ibtn_title_right).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(),
                                AddAlarmActivity.class);
                        startActivity(i);
                    }
                });

        mMainListView = (ListView) getView().findViewById(R.id.lv_alarm_list);
        mAlarmListAdapter = new AlarmListAdapter(getActivity());
        mMainListView.setAdapter(mAlarmListAdapter);

        for (int i = 0; i < 10; i++) {
            mAlarmListAdapter.add(new AlarmObject());
        }
        mAlarmListAdapter.notifyDataSetChanged();
    }
}
