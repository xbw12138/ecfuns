package com.example.update;

import android.content.Context;

public class IsNeedUpdate {

	private UpdateInfo info;
	public IsNeedUpdate(Context context){
		UpdateInfoService updateInfoService = new UpdateInfoService(
				context);
		try {
			info = updateInfoService.getUpDateInfo();
		} catch (Exception e) {
			// TODO 自动生成�? catch �?
			e.printStackTrace();
		}
	}
	public boolean isNeedUpdate(Context context) {
		 GetVersion version=new GetVersion();
		 //版本更新检测使用浮点型存在小版本问题，浮点型不能识别v 1.2.1这种小版本
		 //double webVersion=Double.parseDouble(info.getVersion());
		// double localVersion=Double.parseDouble(version.getVersion(context));
		 //采用比较字典序大小检测版本更新
		if (info.getVersion().compareTo(version.getVersion(context))>0) {
			return true;
		} else {
			return false;
		}
	}
	public String getDescribe(){
		
		return info.getDescription();
	}
	public String getUrl(){
		
		return info.getUrl();
	}
}
