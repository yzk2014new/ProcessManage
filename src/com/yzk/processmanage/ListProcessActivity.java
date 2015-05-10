package com.yzk.processmanage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Debug;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListProcessActivity extends Activity {
	
	private ListView mListProcessView;
	private ListProcessAdapter mListProcessAdapter;
	private ArrayList<ProcessInfo> mProcessList = new ArrayList<ProcessInfo>();
	private ActivityManager mActivityManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_process);
		mActivityManager=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
		
		mListProcessView = (ListView) findViewById(R.id.ListProcess);
		mListProcessAdapter = new ListProcessAdapter(this);
		mListProcessView.setAdapter(mListProcessAdapter);
		Thread thread = new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				setProcessList();
			}
		});
		thread.start();
		
		mListProcessView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	private void setProcessList() {
		mProcessList.clear();
		PackageManager pm = getApplicationContext().getPackageManager();
		List<RunningAppProcessInfo> apps = mActivityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo app : apps) {
			ProcessInfo procInfo = new ProcessInfo();
			procInfo.setName(app.processName);
			
			StringBuilder info = new StringBuilder("PID: ");
			info.append(app.pid).append("   MemoryInfo: ");
			int[] mempid = new int[] { app.pid };
			Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(mempid);
			int memSize = memoryInfo[0].dalvikPrivateDirty;
			info.append(memSize).append("KB");
			procInfo.setInfo(info.toString());
			
			ApplicationInfo appInfo = getApplicationInfo(app.processName);
			if (appInfo != null) {
				procInfo.setIcon(appInfo.loadIcon(pm));
			}
			mProcessList.add(procInfo);
		}
		runOnUiThread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				mListProcessAdapter.setData(mProcessList);
			}
		});
	}
	
	private ApplicationInfo getApplicationInfo(String appName) {
		if (TextUtils.isEmpty(appName)) {
			return null;
		}
		PackageManager pm = getApplicationContext().getPackageManager();
		List<ApplicationInfo> allAppList = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		pm.getInstalledPackages(0);
		
		for (ApplicationInfo appinfo : allAppList) {
			if (appName.equals(appinfo.processName)) {
				return appinfo;
			}
		}
		return null;
	}
}