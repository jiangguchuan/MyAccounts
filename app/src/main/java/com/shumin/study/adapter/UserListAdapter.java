package com.shumin.study.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shumin.study.R;
import com.shumin.study.bean.UserInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/5/2 0002.
 */
public class UserListAdapter extends BaseAdapter {

    private List<UserInfo> mData;
    private Context mContext;

    public UserListAdapter(Context context, List<UserInfo> userInfo) {
        mContext = context;
        mData = userInfo;
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
            convertView = View.inflate(mContext, R.layout.user_list_item, null);
            holder = new ViewHolder();
            holder.headIcon = (ImageView) convertView.findViewById(R.id.user_head_icon);
            holder.userName = (TextView) convertView.findViewById(R.id.item_username);
            holder.password = (TextView) convertView.findViewById(R.id.item_password);
            holder.editIcon = (ImageView) convertView.findViewById(R.id.item_edit_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UserInfo info = mData.get(i);
        holder.headIcon.setImageResource(info.isManager() ? R.drawable.head_icon_manager : R.drawable.head_icon_user);
        holder.userName.setText(info.getUserName());
        holder.password.setText(mContext.getString(R.string.password, info.getPassword()));
        holder.editIcon.setVisibility(info.isManager() ? View.VISIBLE : View.INVISIBLE);
        convertView.setBackgroundResource(info.isManager() ? R.drawable.user_item_manager_bg : R.drawable.user_item_user_bg);

        return convertView;
    }

    class ViewHolder {
        private ImageView headIcon;
        private TextView userName;
        private TextView password;
        private ImageView editIcon;
    }
}
