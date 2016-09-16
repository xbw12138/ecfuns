package com.example.mysql;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.mysql.AsyncTask_Is_User_Exist.MysqlListenerss;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class AsyncTask_Change_Zan extends AsyncTask<String, String, String> {

	JSONParser jsonParser = new JSONParser();
	private String url_up;// = Config_mysql.mysql_change_talkzan;
	Context context;
	private String ty;
	ProgressDialog dialog;

	public AsyncTask_Change_Zan(Context context,String type) {
		this.context = context;
		this.ty=type;
		if(type.equals("talk")){
			this.url_up=Config_mysql.mysql_change_talkzan;
		}else if(type.equals("pl")){
			this.url_up=Config_mysql.mysql_change_plzan;
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////
	public interface MysqlListenersss { // /
		public void Success(); // /

		public void Fail(); // /
	} // /

	private MysqlListenersss mysqlListener = null; // /

	public void setMysqlListenersss(MysqlListenersss mysqlListener) { // /
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
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String message = null;
		if(ty.equals("talk")){
			params.add(new BasicNameValuePair("talk_id", p[0]));
		}else if(ty.equals("pl")){
			params.add(new BasicNameValuePair("pl_id", p[0]));
		}
		//params.add(new BasicNameValuePair("talk_id", p[0]));
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
			} else {
				mysqlListener.Fail();
			}
		}
		if (message.equals("NONET")) {
			Toast.makeText(context, "网络连接失败", 8000).show();
		} else if (message.equals("YES")) {
			Toast.makeText(context, "赞+1", 8000).show();
		} else {
			Toast.makeText(context, "没点到", 8000).show();
		}
	}

}
