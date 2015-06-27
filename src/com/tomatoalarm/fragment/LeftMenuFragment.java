package com.tomatoalarm.fragment;

import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.fragment.BaseFragment;
import com.core.utils.ToastUtil;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;
import com.tomatoalarm.R;

public class LeftMenuFragment extends BaseFragment implements View.OnClickListener {

    private CalendarPickerView calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu_left, null);
        v.findViewById(R.id.tv_menu_total_active_list).setOnClickListener(this);
        v.findViewById(R.id.tv_menu_today_active_list).setOnClickListener(this);
        v.findViewById(R.id.tv_menu_urgent_list).setOnClickListener(this);
        v.findViewById(R.id.tv_menu_progress_list).setOnClickListener(this);
        v.findViewById(R.id.tv_menu_active_record).setOnClickListener(this);
        v.findViewById(R.id.tv_menu_active_analysis).setOnClickListener(this);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);
        calendar = (CalendarPickerView) v.findViewById(R.id.calendar_view);
        // List<CalendarCellDecorator> decorators = new
        // ArrayList<CalendarCellDecorator>();
        // decorators.add(new SampleDecorator());
        // calendar.setDecorators(decorators);
        calendar.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(SelectionMode.SINGLE) //
                .withSelectedDate(new Date());
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.tv_menu_total_active_list:
            // 活动清单
            ToastUtil.show(getActivity(), (ViewGroup) (getView().getParent()),
                    getResources().getString(R.string.total_active_list), 1000);
            break;
        case R.id.tv_menu_today_active_list:
            // 今日代办
            ToastUtil.show(getActivity(), (ViewGroup) (getView().getParent()),
                    getResources().getString(R.string.today_active_list), 1000);
            break;
        case R.id.tv_menu_urgent_list:
            // 计划外紧急
            ToastUtil
                    .show(getActivity(),
                            (ViewGroup) (getView().getParent()),
                            getResources().getString(
                                    R.string.urgent_active_list), 1000);
            break;
        case R.id.tv_menu_progress_list:
            // 大活动进度
            ToastUtil.show(getActivity(), (ViewGroup) (getView().getParent()),
                    getResources().getString(R.string.progress_list), 1000);
            break;
        case R.id.tv_menu_active_record:
            // 活动记录
            ToastUtil.show(getActivity(), (ViewGroup) (getView().getParent()),
                    getResources().getString(R.string.active_record), 1000);
            break;
        case R.id.tv_menu_active_analysis:
            // 活动分析
            ToastUtil.show(getActivity(), (ViewGroup) (getView().getParent()),
                    getResources().getString(R.string.active_analysis), 1000);
            break;
        }
    }

}
