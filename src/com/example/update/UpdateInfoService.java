package com.example.update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




import com.example.matrix.util.Config;

import android.content.Context;

public class UpdateInfoService {
	public UpdateInfoService(Context context) {
	}

	public UpdateInfo getUpDateInfo() throws Exception {
		String path = Config.mysql_url_update;
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader reader = null;
		UpdateInfo updateInfo = new UpdateInfo();
		try {
			// 创建�?个url对象
			URL url = new URL(path);
			// 通過url对象，创建一个HttpURLConnection对象（连接）
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			// 通过HttpURLConnection对象，得到InputStream
			reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			// 使用io流读取文�?
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		String info = sb.toString();
		updateInfo.setVersion(info.split("&")[1]);
		updateInfo.setDescription(info.split("&")[2]);
		updateInfo.setUrl(info.split("&")[3]);
		return updateInfo;
	}

}
