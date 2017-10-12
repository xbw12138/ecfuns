package com.example.upimg;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import android.widget.Toast;

public class Upload_image {

	public static class UpFile {
		public static int post(String actionUrl, File file) throws IOException {
			// 产生随机分隔内容
			String BOUNDARY = UUID.randomUUID().toString();
			String PREFIX = "--";
			String LINEND = "\r\n";
			String MULTIPART_FROM_DATA = "multipart/form-data";
			String CHARSET = "UTF-8";
			URL url = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置超时时间单位是毫秒
			conn.setReadTimeout(5 * 1000);
			// 设置允许输入
			conn.setDoInput(true);
			// 设置允许输出
			conn.setDoOutput(true);
			// 不允许使用缓存
			conn.setUseCaches(false);
			// 设置请求的方法为Post
			conn.setRequestMethod("POST");
			// 设置维持长连接
			conn.setRequestProperty("Connection", "keep-alive");
			// 设置字符集为UTF-8
			conn.setRequestProperty("Charset", CHARSET);
			// 设置文件的类型
			conn.setRequestProperty("Content-type", MULTIPART_FROM_DATA
					+ "; boundary=" + BOUNDARY);
			DataOutputStream outStream = new DataOutputStream(
					conn.getOutputStream());
			// 发送文件数据
			if (file != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINEND);
				// uploadedfile与服务器端内容匹配
				sb.append("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\""
						+ file.getName() + "\"" + LINEND);
				//
				sb.append("Content-Type: image/png" + LINEND);
				sb.append(LINEND);
				// 写入输出流中
				outStream.write(sb.toString().getBytes());
				// 将文件读入输入流中
				InputStream is = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = -1;
				// 写入输出流中
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				is.close();
				// 添加换行标识
				outStream.write(LINEND.getBytes());
			}

			byte[] endData = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(endData);
			// 发送数据
			outStream.flush();
			// 获取响应码 上传成功返回的是200
			int res = conn.getResponseCode();
			return res;
		}
	}

}
