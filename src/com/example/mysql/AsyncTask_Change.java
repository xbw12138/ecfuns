package com.example.mysql;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.mysql.Info_Type.INFOTYPE;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class AsyncTask_Change extends AsyncTask<String, String, String> {
	// mysql
	JSONParser jsonParser = new JSONParser();
	private static String url_up = Config_mysql.Get_URLPATH();
	private static String url_s = "";
	private static final String TAG_MESSAGE = "message";
	Config_mysql USERYETEXIST = new Config_mysql();
	Context context;
	ProgressDialog dialog;
	boolean result = false;

	public AsyncTask_Change(Context context) {
		this.context = context;
	}

	@SuppressWarnings("deprecation")
	public Config_mysql Mysql_Change(String ID, String INFO, INFOTYPE TYPE) {
		String id = ID;
		String info = INFO;
		INFOTYPE type = TYPE;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user_phone", id));
		switch (type) {
		case brand:
			params.add(new BasicNameValuePair("brand", info));
			url_s = INFOTYPE.brand.getUrl();
			break;
		case logo:
			url_s = INFOTYPE.logo.getUrl();
			params.add(new BasicNameValuePair("logo", info));
			break;
		case platenum:
			url_s = INFOTYPE.platenum.getUrl();
			params.add(new BasicNameValuePair("platenum", info));
			break;
		case enginenum:
			url_s = INFOTYPE.enginenum.getUrl();
			params.add(new BasicNameValuePair("enginenum", info));
			break;
		case carlevel:
			url_s = INFOTYPE.carlevel.getUrl();
			params.add(new BasicNameValuePair("carlevel", info));
			break;
		case colometer:
			url_s = INFOTYPE.colometer.getUrl();
			params.add(new BasicNameValuePair("colometer", info));
			break;
		case enginestate:
			url_s = INFOTYPE.enginestate.getUrl();
			params.add(new BasicNameValuePair("enginestate", info));
			break;
		case shiftstate:
			url_s = INFOTYPE.shiftstate.getUrl();
			params.add(new BasicNameValuePair("shiftstate", info));
			break;
		case light:
			url_s = INFOTYPE.light.getUrl();
			params.add(new BasicNameValuePair("light", info));
			break;
		case oilcount:
			url_s = INFOTYPE.oilcount.getUrl();
			params.add(new BasicNameValuePair("oilcount", info));
			break;
		case order_time:
			url_s = INFOTYPE.order_time.getUrl();
			params.add(new BasicNameValuePair("order_time", info));
			break;
		case gas_station:
			url_s = INFOTYPE.gas_station.getUrl();
			params.add(new BasicNameValuePair("gas_station", info));
			break;
		case gas_type:
			url_s = INFOTYPE.gas_type.getUrl();
			params.add(new BasicNameValuePair("gas_type", info));
			break;
		case gas_num:
			url_s = INFOTYPE.gas_num.getUrl();
			params.add(new BasicNameValuePair("gas_num", info));
			break;
		case user_name:
			url_s = INFOTYPE.user_name.getUrl();
			params.add(new BasicNameValuePair("user_name", info));
			break;
		case user_age:
			url_s = INFOTYPE.user_age.getUrl();
			params.add(new BasicNameValuePair("user_age", info));
			break;
		case user_image_head:
			url_s = INFOTYPE.user_image_head.getUrl();
			params.add(new BasicNameValuePair("user_image_head", info));
			break;
		case user_sex:
			url_s = INFOTYPE.user_sex.getUrl();
			params.add(new BasicNameValuePair("user_sex", info));
			break;
		case user_schoolname:
			url_s = INFOTYPE.user_schoolname.getUrl();
			params.add(new BasicNameValuePair("user_schoolname", info));
			break;
		case user_password:
			url_s = INFOTYPE.user_password.getUrl();
			params.add(new BasicNameValuePair("user_pwd", info));
			break;
		case user_bg:
			url_s = INFOTYPE.user_bg.getUrl();
			params.add(new BasicNameValuePair("user_bg", info));
			break;
		case user_sign:
			url_s = INFOTYPE.user_sign.getUrl();
			params.add(new BasicNameValuePair("user_sign", info));
			break;
		case user_signtime:
			url_s = INFOTYPE.user_signtime.getUrl();
			params.add(new BasicNameValuePair("user_signtime", info));
			break;
		}
		try {
			JSONObject json = jsonParser.makeHttpRequest(url_up + url_s,
					"POST", params);
			String message = json.getString(TAG_MESSAGE);
			if (message.equals("NONET")) {
				USERYETEXIST.Set_isNetWork(false);
			} else {
				USERYETEXIST.Set_httpjsonsuccess(message.equals("YES"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return USERYETEXIST;
	}
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = Progress_Dialog.CreateProgressDialog(context);
		dialog.show();
	}
	@Override
	protected String doInBackground(String... params) {
		// TODO �Զ����ɵķ������
		String ID = params[0];
		String INFO = params[1];
		String TYPE = params[2];
		Config_mysql LOG;
		LOG = Mysql_Change(ID, INFO, STRING_INFOTYPE(TYPE));
		String message;
		if (!LOG.Get_isNetWork()) {
			message = "1";
		} else if (LOG.Get_httpjsonsuccess()) {
			message = "2";
		} else {
			message = "3";
		}
		return message;
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
	@SuppressLint("ShowToast")
	protected void onPostExecute(String message) {
		dialog.dismiss();
		if(mysqlListener!=null){
			if (message.equals("2")) {
				mysqlListener.Success();
			} else {
				mysqlListener.Fail();
			}
		}
		if (message.equals("1")) {
			Toast.makeText(context, "网络连接失败", 8000).show();
		} else if (message.equals("2")) {
			Toast.makeText(context, "修改成功", 8000).show();
		} else {
			Toast.makeText(context, "修改失败", 8000).show();
		}
	}

	public INFOTYPE STRING_INFOTYPE(String TYPE) {
		INFOTYPE type = null;
		switch (TYPE) {
		case "brand":
			type = INFOTYPE.brand;
			break;
		case "logo":
			type = INFOTYPE.logo;
			break;
		case "platenum":
			type = INFOTYPE.platenum;
			break;
		case "enginenum":
			type = INFOTYPE.enginenum;
			break;
		case "carlevel":
			type = INFOTYPE.carlevel;
			break;
		case "colometer":
			type = INFOTYPE.colometer;
			break;
		case "enginestate":
			type = INFOTYPE.enginestate;
			break;
		case "shiftstate":
			type = INFOTYPE.shiftstate;
			break;
		case "light":
			type = INFOTYPE.light;
			break;
		case "oilcount":
			type = INFOTYPE.oilcount;
			break;
		case "order_time":
			type = INFOTYPE.order_time;
			break;
		case "gas_station":
			type = INFOTYPE.gas_station;
			break;
		case "gas_type":
			type = INFOTYPE.gas_type;
			break;
		case "gas_num":
			type = INFOTYPE.gas_num;
			break;
		case "user_name":
			type = INFOTYPE.user_name;
			break;
		case "user_image_head":
			type = INFOTYPE.user_image_head;
			break;
		case "user_age":
			type = INFOTYPE.user_age;
			break;
		case "user_schoolname":
			type = INFOTYPE.user_schoolname;
			break;
		case "user_sex":
			type = INFOTYPE.user_sex;
			break;
		case "user_password":
			type = INFOTYPE.user_password;
			break;
		case "user_bg":
			type = INFOTYPE.user_bg;
			break;
		case "user_sign":
			type = INFOTYPE.user_sign;
			break;
		case "user_signtime":
			type = INFOTYPE.user_signtime;
			break;
		}
		return type;
	}

	public static String INFOTYPE_STRING(INFOTYPE TYPE) {
		String type = null;
		switch (TYPE) {
		case brand:
			type = "brand";
			break;
		case logo:
			type = "logo";
			break;
		case platenum:
			type = "platenum";
			break;
		case enginenum:
			type = "enginenum";
			break;
		case carlevel:
			type = "carlevel";
			break;
		case colometer:
			type = "colometer";
			break;
		case enginestate:
			type = "enginestate";
			break;
		case shiftstate:
			type = "shiftstate";
			break;
		case light:
			type = "light";
			break;
		case oilcount:
			type = "oilcount";
			break;
		case order_time:
			type = "order_time";
			break;
		case gas_station:
			type = "gas_station";
			break;
		case gas_type:
			type = "gas_type";
			break;
		case gas_num:
			type = "gas_num";
			break;
		case user_name:
			type = "user_name";
			break;
		case user_age:
			type = "user_age";
			break;
		case user_schoolname:
			type = "user_schoolname";
			break;
		case user_sex:
			type = "user_sex";
			break;
		case user_image_head:
			type = "user_image_head";
			break;
		case user_password:
			type = "user_password";
			break;
		case user_bg:
			type = "user_bg";
			break;
		case user_sign:
			type = "user_sign";
			break;
		case user_signtime:
			type = "user_signtime";
			break;
		}
		return type;
	}
}
