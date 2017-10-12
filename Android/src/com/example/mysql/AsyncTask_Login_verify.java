package com.example.mysql;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.mysql.AsyncTask_Insert_Register.MysqlListeners;

public class AsyncTask_Login_verify extends AsyncTask<String, String, String> {
	JSONParser jsonParser = new JSONParser();
	private static String url_up = Config_mysql.mysql_url_loginverify;
	Context context;
	ProgressDialog dialog;

	public AsyncTask_Login_verify(Context context) {
		this.context = context;
	}

	// ///////////////////////////////////////////////////////////////////////////////////
	public interface MysqlListeners { // /
		public void Success(); // /

		public void Exist();

		public void Fail(); // /
	} // /

	private MysqlListeners mysqlListener = null; // /

	public void setMysqlListeners(MysqlListeners mysqlListener) { // /
		this.mysqlListener = mysqlListener; // /
	} // /
		// /

	// ///////////////////////////////////////////////////////////////////////////////////

	protected void onPreExecute() {
		super.onPreExecute();
		dialog = Progress_Dialog.CreateProgressDialog(context);
		dialog.show();
	}

	@SuppressWarnings("deprecation")
	@Override
	protected String doInBackground(String... p) {
		String message = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String user_signtime = df.format(new Date());
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user_phone", p[0]));
		params.add(new BasicNameValuePair("user_pwd", p[1]));
		try {
			JSONObject json = jsonParser
					.makeHttpRequest(url_up, "POST", params);
			message = json.getString("message");
		} catch (Exception e) {
			e.printStackTrace();
			message = "NONET";
		}
		return message;
	}

	@SuppressLint("ShowToast")
	protected void onPostExecute(String message) {
		dialog.dismiss();
		if (mysqlListener != null) {
			if (message.equals("YES")) {
				mysqlListener.Success();
			} else if (message.equals("USERNO")) {
				mysqlListener.Exist();
			} else if (message.equals("PWDNO")) {
				mysqlListener.Fail();
			}
		}
		if (message.equals("NONET")) {
			Toast.makeText(context, "网络连接失败", 8000).show();
		} else if (message.equals("YES")) {
			Toast.makeText(context, "登录成功", 8000).show();
		} else if (message.equals("USERNO")) {
			Toast.makeText(context, "用户不存在", 8000).show();
		} else {
			Toast.makeText(context, "密码错误", 8000).show();
		}
	}

}
