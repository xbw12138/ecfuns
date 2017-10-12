package com.example.matrix.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.frag3.PlActivity;
import com.example.frag3.R;
import com.example.mysql.AsyncTask_Insert_Talk;
import com.example.mysql.Singleton;
import com.example.mysql.AsyncTask_Insert_Talk.MysqlListeners;

public class ZhuanfaDialog {
	Context context;
	public ZhuanfaDialog(Context context) {
		this.context = context;
	}

	public void showDialog(final ListTalkModel model,final String ty) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(
				R.layout.zhuanfa_dialog, null);
		final Dialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.getWindow().setContentView(layout);
		dialog.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		dialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
		final TextView tx = (TextView) layout.findViewById(R.id.textView1);
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AsyncTask_Insert_Talk in = new AsyncTask_Insert_Talk(
						context);
				in.setMysqlListeners(new MysqlListeners() {
					@Override
					public void Success() {
						// TODO 自动生成的方法存根
						// 上传图片到服务器
						// new Thread(uploadImageRunnable).start();
						dialog.dismiss();
					}

					@Override
					public void Fail() {
						// TODO 自动生成的方法存根
						tx.setText("转发失败--重新发送");
					}
				});
				String neirong;
				if (!model.getContent().equals("")) {
					if (model.getContent().subSequence(0, 1).equals("@")) {
						neirong = "@" + model.getName() + " \r\n"
								+ model.getContent();
					} else {
						neirong = "@" + model.getName() + " \r\n"
								+ model.getContent() + "\r\n"
								+ "----------------------------------转发";
					}
				} else {
					// Toast.makeText(mContext, "空消息", 8000);
					neirong = "@" + model.getName() + " \r\n"
							+ model.getContent() + "\r\n"
							+ "----------------------------------转发";
				}
				if(ty.equals("talk")){
					in.execute(Singleton.getInstance().getPhone(), neirong,
							model.getUrl(),android.os.Build.MODEL);
				}else if(ty.equals("pl")){
					String url = "";
					for (String s : model.getUrls())
						url += s + "#";
					in.execute(Singleton.getInstance().getPhone(), neirong, url,android.os.Build.MODEL);
				}
				
			}
		});
		ImageButton btnClose = (ImageButton) layout
				.findViewById(R.id.dialog_close);
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
}
