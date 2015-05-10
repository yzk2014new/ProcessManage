package com.yzk.processmanage;

import android.graphics.drawable.Drawable;

public class ProcessInfo {
	
	private Drawable mIcon;
	private String mName;
	private String mInfo;
	
	public ProcessInfo() {
	}
	
	public ProcessInfo(Drawable icon, String name, String info) {
		mIcon = icon;
		mName = name;
		mInfo = info;
	}
	
	public Drawable getIcon() {
		return mIcon;
	}
	
	public void setIcon(Drawable icon) {
		mIcon = icon;
	}
	
	public String getName() {
		return mName;
	}
	
	public void setName(String name) {
		mName = name;
	}
	
	public String getInfo() {
		return mInfo;
	}
	
	public void setInfo(String info) {
		mInfo = info;
	}
}