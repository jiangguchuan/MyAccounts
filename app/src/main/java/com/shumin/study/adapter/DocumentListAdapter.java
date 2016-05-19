package com.shumin.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shumin.study.R;
import com.shumin.study.bean.Documents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guchuan on 2016/5/17.
 */
public class DocumentListAdapter extends BaseAdapter {

    private final int[] ICON_BG_LIST = {
            R.drawable.service_icon_bg_1,
            R.drawable.service_icon_bg_2,
            R.drawable.service_icon_bg_3,
            R.drawable.service_icon_bg_4
    };

    private Context mContext;
    private List<Documents> mData = new ArrayList<>();

    public DocumentListAdapter(Context context, List<Documents> data) {
        mContext = context;
        mData = data;
    }

    public void updateData(List<Documents> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.documents_list_item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.item_documents_icon);
            holder.name = (TextView) convertView.findViewById(R.id.item_name);
            holder.content = (TextView) convertView.findViewById(R.id.item_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Documents info = mData.get(position);

        holder.icon.setBackgroundResource(ICON_BG_LIST[position % ICON_BG_LIST.length]);
        holder.icon.setImageResource(R.drawable.icon_punchcard);
        holder.name.setText(info.getName());
        holder.content.setText("共有" + info.getImages().size() + "张图片");
        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView name;
        TextView content;
    }
}
