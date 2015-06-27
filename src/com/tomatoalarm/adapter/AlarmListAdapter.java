package com.tomatoalarm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.core.utils.BaseCoreAdapter;
import com.core.utils.LogUtil;
import com.tomatoalarm.R;
import com.tomatoalarm.beans.AlarmObject;
import com.tomatoalarm.view.LineTickView;

public class AlarmListAdapter extends BaseCoreAdapter<AlarmObject> {

    public AlarmListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getConvertView(int position, View convertView,
            LayoutInflater layoutInflater, ViewGroup parent) {
        ViewHolder holder = null;
        if (null == convertView) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_alarm_layout,
                    null);
            holder.rlInfo = (RelativeLayout) convertView
                    .findViewById(R.id.rl_alarm_item_content);
            holder.tvName = (TextView) convertView
                    .findViewById(R.id.tv_item_alarm_title_name);
            holder.tvContent = (TextView) convertView
                    .findViewById(R.id.tv_item_alarm_content);
            holder.timeDripView = (LineTickView) convertView
                    .findViewById(R.id.tdv_alarm_item);
            holder.tvName.measure(0, 0);
            holder.tvContent.measure(0, 0);
            LogUtil.e(
                    "AlarmListAdapter",
                    "relativelayout_height = "
                            + (holder.tvName.getMeasuredHeight() + holder.tvContent
                                    .getMeasuredHeight()));
            RelativeLayout.LayoutParams params = (LayoutParams) holder.timeDripView
                    .getLayoutParams();
            params.height = (holder.tvName.getMeasuredHeight() + holder.tvContent
                    .getMeasuredHeight());
            holder.timeDripView.setLayoutParams(params);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        RelativeLayout rlInfo;
        TextView tvName;
        TextView tvContent;
        LineTickView timeDripView;

    }
}
