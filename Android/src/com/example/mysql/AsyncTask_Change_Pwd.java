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

public class AsyncTask_Change_Pwd extends AsyncTask<String, String, String> {

	JSONParser jsonParser = new JSONParser();
	private static String url_up = Config_mysql.mysql_url_change_password;
	Context context;
	ProgressDialog dialog;
	public AsyncTask_Change_Pwd(Context context) {
		this.context=context;
	}
/////////////////////////////////////////////////////////////////////////////////////
public interface MysqlListenerss {                                       		  ///
public void Success();                                                    ///
public void Fail();                                                       ///
} 																			  ///
private MysqlListenerss mysqlListener=null;                                     ///
public void setMysqlListenerss(MysqlListenerss mysqlListener) {                   ///
this.mysqlListener = mysqlListener;                                       /// 
}                                                                             ///
///
/////////////////////////////////////////////////////////////////////////////////////
protected void onPreExecute() {
super.onPreExecute();
dialog = Progress_Dialog.CreateProgressDialog(context);
dialog.show();
}
	@SuppressWarnings("deprecation")
	@Override
	protected String doInBackground(String... p) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		String message=null;
	    params.add(new BasicNameValuePair("user_phone", p[0]));
	    params.add(new BasicNameValuePair("user_pwd", p[1]));
	       try{
	        JSONObject json = jsonParser.makeHttpRequest(url_up,
	                "POST", params);
	        message=json.getString("message");
	       }catch(Exception e){
	           e.printStackTrace();    
	           message="NONET";
	       }
		return message;
	}
	@SuppressLint("ShowToast") protected void onPostExecute(String message) {    
		dialog.dismiss();
		if(mysqlListener!=null){
			if (message.equals("YES")) {
				mysqlListener.Success();
			} else {
				mysqlListener.Fail();
			}
		}
		if(message.equals("NONET")){
			Toast.makeText(context, "网络连接失败", 8000).show(); 
		}else if(message.equals("YES")){
			Toast.makeText(context, "修改成功", 8000).show(); 
		}else{
			Toast.makeText(context, "修改失败", 8000).show(); 
		}
    }

}
