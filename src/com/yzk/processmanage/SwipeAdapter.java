package com.yzk.processmanage;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class SwipeAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<ProcessInfo> mData = new ArrayList<ProcessInfo>();
    private int mRightWidth = 0;
    private IOnItemRightClickListener mListener = null;

    public interface IOnItemRightClickListener {
        void onRightClick(View v, int position);
    }
	
	public SwipeAdapter(Context context, int rightWidth, IOnItemRightClickListener l) {
		mContext = context;
        mRightWidth = rightWidth;
        mListener = l;
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

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
        final int thisPosition = position;
		if (convertView == null) {
			holder=new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.process_item, parent, false);
            holder.item_left = convertView.findViewById(R.id.item_left);
			holder.icon = (ImageView) convertView.findViewById(R.id.processicon);
			holder.name = (TextView) convertView.findViewById(R.id.processname);
			holder.info = (TextView) convertView.findViewById(R.id.processinfo);
            holder.item_right = convertView.findViewById(R.id.item_right);
            holder.item_right_image = (ImageView) convertView.findViewById(R.id.item_right_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
        LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        holder.item_left.setLayoutParams(lp1);
        LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
        holder.item_right.setLayoutParams(lp2);
        holder.item_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightClick(v, thisPosition);
                }
            }
        });
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
        public View item_left;
		public ImageView icon;
		public TextView name;
		public TextView info;

        public View item_right;
        public ImageView item_right_image;
	}
}

