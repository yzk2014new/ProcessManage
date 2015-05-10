package com.yzk.processmanage;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListProcessAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<ProcessInfo> mData = new ArrayList<ProcessInfo>();
	
	public ListProcessAdapter(Context context) {
		mContext = context;
	}
	
	public void setData(ArrayList<ProcessInfo> data) {
		mData.clear();
		mData.addAll(data);
		notifyDataSetChanged();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder=new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.process_item, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.processicon);
			holder.name = (TextView) convertView.findViewById(R.id.processname);
			holder.info = (TextView) convertView.findViewById(R.id.processinfo);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		Drawable icon = mData.get(position).getIcon();
		if (icon != null) {
			holder.icon.setImageDrawable(icon);
		} else {
			holder.icon.setImageResource(R.drawable.ic_launcher);
		}
		holder.name.setText(mData.get(position).getName());
		holder.info.setText(mData.get(position).getInfo());
		
		return convertView;
	}
	
	public final class ViewHolder {
		public ImageView icon;
		public TextView name;
		public TextView info;
	}
}

