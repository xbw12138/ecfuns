package com.example.update;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class GetVersion {

	// 获取当前版本的版本号
		public String getVersion(Context context) {
			try {
				PackageManager packageManager = context.getPackageManager();
				PackageInfo packageInfo = packageManager.getPackageInfo(
						context.getPackageName(), 0);
				return packageInfo.versionName;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
				return "版本号未知";
			}
		}
}
