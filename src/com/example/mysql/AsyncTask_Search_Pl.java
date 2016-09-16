package com.example.mysql;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.matrix.util.ListPlModel;
import com.example.mysql.AsyncTask_Search_Talk.MysqlListener;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class AsyncTask_Search_Pl extends AsyncTask<String, String, String> {
	JSONParsers jsonParser = new JSONParsers();
	private static String url_up = Config_mysql.mysql_search_pl;
	Context context;
	ProgressDialog dialog;
	List<ListPlModel> list = new ArrayList<ListPlModel>();

	public AsyncTask_Search_Pl(Context context) {
		this.context = context;
	}

	// ///////////////////////////////////////////////////////////////////////////////////
	public interface MysqlListener { // /
		public void Success(); // /

		public void Fail();

		public void Full();// /
	} // /

	private MysqlListener mysqlListener = null; // /

	public void setMysqlListener(MysqlListener mysqlListener) { // /
		this.mysqlListener = mysqlListener; // /
	} // /
		// /
		// ///////////////////////////////////////////////////////////////////////////////////

	protected void onPreExecute() {
		super.onPreExecute();
		dialog = Progress_Dialog.CreateProgressDialog(context);
		dialog.show();
	}

	@Override
	protected String doInBackground(String... p) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String message = null;
		params.add(new BasicNameValuePair("pl_num", p[0]));
		params.add(new BasicNameValuePair("page", p[1]));
		// Log.i("P1的值是多少  ", p[1]);
		try {
			JSONArray jsons = jsonParser
					.makeHttpRequest(url_up, "POST", params);
			int iSize = jsons.length();
			// Log.i("数据条数", "$$$$$$   " + iSize);

			// ListTalkModel model = null;
			for (int i = iSize - 1; i >= 0; i--) {
				JSONObject json = (JSONObject) jsons.opt(i);
				ListPlModel model = new ListPlModel();
				model.setContent(json.getString("pl_content"));
				model.setName(json.getString("user_name"));
				model.setTime(json.getString("pl_time"));
				model.setHead(json.getString("user_head"));
				model.setZan(json.getString("pl_zan"));
				model.setID(json.getString("pl_id"));
				list.add(model);
				if (p[1].equals("1")) {
					Singleton.getInstance().setlistPl(list);
				} else {
					if (!json.getString("message").equals("FULL")) {
						Singleton.getInstance().getlistPl().add(model);
						// Log.i("追加单例", "$$$$$$   ");
					}
				}
				message = json.getString("message");
				// Log.i("麦色哥", "$$$$$$   " + message);
			}
			// if(p[1].equals("1")){
			// Singleton.getInstance().setlistTalk(list);
			// }else{
			// Singleton.getInstance().getlistTalk().add(model);
			// }
			// Singleton.getInstance().setlistTalk(list);
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
			} else if (message.equals("FULL")) {
				mysqlListener.Full();
			} else {
				mysqlListener.Fail();
			}
		}
		if (message.equals("NONET")) {
			Toast.makeText(context, "无数据", 8000).show();
		} else if (message.equals("YES")) {
			Toast.makeText(context, "获取数据成功", 8000).show();
		} else if (message.equals("FULL")) {
			Toast.makeText(context, "已加载全部数据", 8000).show();
		} else {
			Toast.makeText(context, "获取数据失败", 8000).show();
		}
	}
}
