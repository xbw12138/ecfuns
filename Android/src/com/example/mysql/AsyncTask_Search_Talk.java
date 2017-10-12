package com.example.mysql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.matrix.util.ListTalkModel;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AsyncTask_Search_Talk extends AsyncTask<String, String, String> {
	JSONParsers jsonParser = new JSONParsers();
	private static String url_up = Config_mysql.mysql_url_search_talk;
	Context context;
	ProgressDialog dialog;
	List<ListTalkModel> list = new ArrayList<ListTalkModel>();

	public AsyncTask_Search_Talk(Context context) {
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

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	protected String doInBackground(String... p) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String message = null;
		params.add(new BasicNameValuePair("user_phone", p[0]));
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
				ListTalkModel model = new ListTalkModel();
				model.setContent(json.getString("talk_content"));
				model.setUrl(json.getString("talk_url"));
				model.setName(json.getString("user_name"));
				model.setTime(json.getString("talk_time"));
				model.setHead(json.getString("user_head"));
				model.setPhone(json.getString("user_phone"));
				model.setID(json.getString("talk_id"));
				model.setZan(json.getString("talk_zan"));
				model.setPhonety(json.getString("talk_ty"));
				model.setPnum(json.getString("pl_nums"));
				String a = model.getUrl();//分割图片url
				String[] splits = a.split("#"); // split
				ArrayList<String> urls_3 = new ArrayList<String>();
				List<String> listsplit = new ArrayList(Arrays.asList(splits));
				for (String s : listsplit)
					//System.out.print(s + " ");
					urls_3.add(s);
				model.setUrls(urls_3);
				list.add(model);
				if (p[1].equals("1")) {
					Singleton.getInstance().setlistTalk(list);
				} else {
					if (!json.getString("message").equals("FULL")) {
						Singleton.getInstance().getlistTalk().add(model);
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
			Toast.makeText(context, "网络连接失败", 8000).show();
		} else if (message.equals("YES")) {
			Toast.makeText(context, "获取数据成功", 8000).show();
		} else if (message.equals("FULL")) {
			Toast.makeText(context, "已加载全部数据", 8000).show();
		} else {
			Toast.makeText(context, "获取数据失败", 8000).show();
		}
	}
}
