package com.example.mysql;

import android.app.ProgressDialog;
import android.content.Context;

public class Progress_Dialog {
	
	@SuppressWarnings("deprecation")
	public static ProgressDialog CreateProgressDialog(Context context)
	{
		ProgressDialog dialog = new ProgressDialog(context); 
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); 
		dialog.setMessage("数据加载中……"); 
		dialog.setCancelable(false); 
		return dialog;
	}

}
