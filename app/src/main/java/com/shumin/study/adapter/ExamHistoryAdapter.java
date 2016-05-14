package com.shumin.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shumin.study.R;
import com.shumin.study.bean.ExamHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/14 0014.
 */
public class ExamHistoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<ExamHistory> mData = new ArrayList<>();

    public ExamHistoryAdapter(Context context, List<ExamHistory> data) {
        mData = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.history_item, null);
            holder = new ViewHolder();
            holder.headIcon = (ImageView) convertView.findViewById(R.id.user_head_icon);
            holder.questionsName = (TextView) convertView.findViewById(R.id.questions_name);
            holder.result = (TextView) convertView.findViewById(R.id.item_result);
            holder.editIcon = (ImageView) convertView.findViewById(R.id.item_edit_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ExamHistory info = mData.get(i);
        holder.questionsName.setText(info.getQuestionsName());
        holder.result.setText("共回答" + info.getCompletedCount() + "题  " + "正确" + info.getCorrectCount());

        return convertView;
    }

    class ViewHolder {
        private ImageView headIcon;
        private TextView questionsName;
        private TextView result;
        private ImageView editIcon;
    }
}
