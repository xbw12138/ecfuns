package com.example.mysql;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.matrix.util.ListQrModel;
import com.example.matrix.util.ListTalkModel;
import com.example.mysql.AsyncTask_Search_Talk.MysqlListener;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class AsyncTask_Search_Qr extends AsyncTask<String, String, String> {
	JSONParsers jsonParser = new JSONParsers();
	private static String url_up = Config_mysql.mysql_url_search_qr;
	Context context;
	ProgressDialog dialog;
	List<ListQrModel> list = new ArrayList<ListQrModel>();
	public AsyncTask_Search_Qr(Context context) {
		this.context = context;
	}
/////////////////////////////////////////////////////////////////////////////////////
	public interface MysqlListener {                                       		  ///
		public void Success();                                                    ///
		public void Fail();                                                       ///
	} 																			  ///
	private MysqlListener mysqlListener=null;                                     ///
	public void setMysqlListener(MysqlListener mysqlListener) {                   ///
		this.mysqlListener = mysqlListener;                                       /// 
	}                                                                             ///
																				  ///
/////////////////////////////////////////////////////////////////////////////////////
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = Progress_Dialog.CreateProgressDialog(context);
		dialog.show();
	}
	@Override
	protected String doInBackground(String... p) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String message = null;
		params.add(new BasicNameValuePair("user_phone", p[0]));
		try {
			JSONArray jsons = jsonParser
					.makeHttpRequest(url_up, "POST", params);
			int iSize = jsons.length();
			for (int i = 0; i <iSize; i++) {
				JSONObject json = (JSONObject) jsons.opt(i);
				ListQrModel model = new ListQrModel();
				//model.setTitle(json.getString("talk_title"));
				model.setid(json.getString("qr_id"));
				model.seturl(json.getString("qr_url"));
				model.setfinish(json.getString("is_finish"));
				list.add(model);
				message = json.getString("message");
			}
			Singleton.getInstance().setlistQr(list);
		} catch (Exception e) {
			e.printStackTrace();
			message = "NONET";
		}
		return message;
	}
	@SuppressLint("ShowToast")
	protected void onPostExecute(String message) {
		dialog.dismiss();
		if(mysqlListener!=null){
			if (message.equals("YES")) {
				mysqlListener.Success();
			} else {
				mysqlListener.Fail();
			}
		}
		if (message.equals("NONET")) {
			Toast.makeText(context, "网络连接失败", 8000).show();
		} else if (message.equals("YES")) {
			Toast.makeText(context, "获取数据成功", 8000).show();
		} else {
			Toast.makeText(context, "获取数据失败", 8000).show();
		}
	}
}
