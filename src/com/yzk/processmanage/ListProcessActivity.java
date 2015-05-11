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
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ListProcessActivity extends Activity {
	
	private SwipeListView mListProcessView;
	private SwipeAdapter mSwipeAdapter;
	private ArrayList<ProcessInfo> mProcessList = new ArrayList<ProcessInfo>();
	private ActivityManager mActivityManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_process);
		mActivityManager=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
		
		mListProcessView = (SwipeListView) findViewById(R.id.ListProcess);
        mSwipeAdapter = new SwipeAdapter(this, mListProcessView.getRightViewWidth(),
                new SwipeAdapter.IOnItemRightClickListener() {
                    @Override
                    public void onRightClick(View v, int position) {
                        Toast.makeText(ListProcessActivity.this, "right onclick " + position, Toast.LENGTH_SHORT).show();
                        if (position >=0 && !mProcessList.isEmpty() && position < mProcessList.size()) {
                            ProcessInfo process = mProcessList.get(position);
                            Log.d("ListProcessActivity", "kill process: " + process.getName());
                            Process.sendSignal(process.getPid(), Process.SIGNAL_KILL);
                            //mActivityManager.killBackgroundProcesses(process.getName());
                            mProcessList.remove(position);
                            mSwipeAdapter.setData(mProcessList);
                        }
                    }
        });
		mListProcessView.setAdapter(mSwipeAdapter);
		Thread thread = new Thread(new Runnable() {
			public void run() {
				// TODO Auto-generated method stub
				setProcessList();
			}
		});
		thread.start();
		
		mListProcessView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
                Toast.makeText(ListProcessActivity.this, "item onclick " + position, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void setProcessList() {
		mProcessList.clear();
		PackageManager pm = getApplicationContext().getPackageManager();
		List<RunningAppProcessInfo> apps = mActivityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo app : apps) {
			ProcessInfo procInfo = new ProcessInfo();
            procInfo.setPid(app.pid);
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
                mSwipeAdapter.setData(mProcessList);
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