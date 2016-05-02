package com.shumin.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shumin.study.R;
import com.shumin.study.bean.Questions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/2 0002.
 */
public class QuestionsGridAdapter extends BaseAdapter {

    private static final int[] ITEM_BACKGROUND = {
            R.drawable.questions_item_bg_1,
            R.drawable.questions_item_bg_2,
            R.drawable.questions_item_bg_3
    };

    private Context mContext;
    private List<Questions> mData = new ArrayList<>();

    public QuestionsGridAdapter(Context context, List<Questions> data) {
        mContext = context;
        mData = data;
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
            convertView = View.inflate(mContext, R.layout.questions_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.questions_name);
            holder.description = (TextView) convertView.findViewById(R.id.questions_description);
            holder.createTime = (TextView) convertView.findViewById(R.id.questions_create_time);
            holder.examCount = (TextView) convertView.findViewById(R.id.questions_exam_count);
            holder.questionCount = (TextView) convertView.findViewById(R.id.questions_question_count);
            holder.level = (TextView) convertView.findViewById(R.id.questions_level);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Questions info = mData.get(i);
        holder.name.setText(info.getName());
        holder.description.setText(info.getDescription());
        holder.createTime.setText(info.getCreateTime());
        holder.examCount.setText(mContext.getString(R.string.exam_count, info.getExamCount()));
        holder.questionCount.setText(mContext.getString(R.string.question_count, info.getQuestionCount()));

        String star = "";
        for(int j = 0; j < info.getLevel(); j++) {
            star += "☆";
        }
        holder.level.setText(mContext.getString(R.string.level, star));

        convertView.setBackgroundResource(ITEM_BACKGROUND[i % 3]);

        return convertView;
    }

    class ViewHolder {
        private TextView name;
        private TextView description;
        private TextView createTime;
        private TextView examCount;
        private TextView questionCount;
        private TextView level;
    }
}
