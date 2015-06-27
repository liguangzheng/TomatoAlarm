package com.tomatoalarm.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.core.fragment.BaseFragment;
import com.core.utils.ToastUtil;
import com.tomatoalarm.R;

public class RightMenuFragment extends BaseFragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu_right, null);
        v.findViewById(R.id.tv_menu_about).setOnClickListener(this);
        v.findViewById(R.id.tv_menu_set).setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.tv_menu_about:
            ToastUtil.show(getActivity(), (ViewGroup) (getView().getParent()),
                    getResources().getString(R.string.about), 1000);
            break;
        case R.id.tv_menu_set:
            ToastUtil.show(getActivity(), (ViewGroup) (getView().getParent()),
                    getResources().getString(R.string.set), 1000);
            break;
        }
    }

}
