package com.example.update;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.frag3.MainActivity;
import com.example.frag3.R;

public class UpdateServices extends Service{
	public static final String Install_Apk = "Install_Apk";
	private static final int down_step_custom = 3;
	private static final int TIMEOUT = 10 * 1000;
	private static String down_url;
	private static final int DOWN_OK = 1;
	private static final int DOWN_ERROR = 0;
	private String app_name;
	private NotificationManager notificationManager;
	private Notification notification;
	private Intent updateIntent;
	private PendingIntent pendingIntent;
	private RemoteViews contentView;
	public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
	public final static String INTENT_BUTTONID_TAG = "ButtonId";
	public final static int BUTTON_PREV_ID = 1;
	public final static int BUTTON_PALY_ID = 2;
	public final static int BUTTON_NEXT_ID = 3;
	public ButtonBroadcastReceiver bReceiver;
	public boolean jud=false;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存�?
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		app_name = intent.getStringExtra("Key_App_Name");
		down_url = intent.getStringExtra("Key_Down_Url");
		FileUtil.createFile(app_name);
		if(FileUtil.isCreateFileSucess == true){	
			jud=true;
			createNotification();
			createThread();
		}else{
			Toast.makeText(this, "无内存卡", Toast.LENGTH_SHORT).show();
			stopSelf();
			
		}
		return super.onStartCommand(intent, flags, startId);
	} 
	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_OK:
				Uri uri = Uri.fromFile(FileUtil.updateFile);
				notification.flags = Notification.FLAG_AUTO_CANCEL; 				 
				notification.setLatestEventInfo(UpdateServices.this,app_name, "下载成功", pendingIntent);		
				notificationManager.notify(R.layout.notification_item, notification);	
				installApk();	
				stopService(updateIntent);
				stopSelf();
				break;
			case DOWN_ERROR:
				notification.flags = Notification.FLAG_AUTO_CANCEL; 
				notification.setLatestEventInfo(UpdateServices.this,app_name, "下载失败", null);
				onDestroy();
				stopSelf();
				break;
			default:
				stopService(updateIntent);
				stopSelf();
				break;
			}
		}
	};
	private void installApk() {
		// TODO Auto-generated method stub
		Uri uri = Uri.fromFile(FileUtil.updateFile);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		intent.setDataAndType(uri,"application/vnd.android.package-archive");			        
        UpdateServices.this.startActivity(intent);	       
	}
	public void createThread() {
		new DownLoadThread().start();
	}
	private class DownLoadThread extends Thread{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			try {								
				long downloadSize = downloadUpdateFile(down_url,FileUtil.updateFile.toString());
				if (downloadSize > 0) {					
					// down success										
					message.what = DOWN_OK;
					handler.sendMessage(message);																		
				}
			} catch (Exception e) {
				e.printStackTrace();
				message.what = DOWN_ERROR;
				handler.sendMessage(message);
			}						
		}		
	}
	
	@SuppressWarnings("deprecation")
	public void createNotification() {
		notification = new Notification(
				R.drawable.ic_launcher,
				app_name + "正在下载",
				System.currentTimeMillis());
		notification.flags = Notification.FLAG_ONGOING_EVENT; 
		//notification.flags = Notification.FLAG_AUTO_CANCEL;	 
		contentView = new RemoteViews(getPackageName(),R.layout.notification_item);
		initButtonReceiver();//启动广播监听    忘记启动会�?�成 按钮无响�?
		contentView.setTextViewText(R.id.notificationTitle, app_name + "正在下载");
		contentView.setTextViewText(R.id.notificationPercent, "0%");
		contentView.setProgressBar(R.id.notificationProgress, 100, 0, false);
		// 点击的事件处�?
		Intent buttonIntent = new Intent(ACTION_BUTTON);
		//按钮1
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PREV_ID);
		// 这里加了广播，所及INTENT的必须用getBroadcast方法
		PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1,
				buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		contentView.setOnClickPendingIntent(R.id.btn_close, intent_prev);
		//按钮2
		buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
		PendingIntent intent_paly = PendingIntent.getBroadcast(this, 2,
				buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		contentView.setOnClickPendingIntent(R.id.relativelayout_btn, intent_paly);		
		notification.contentView = contentView;
		updateIntent = new Intent(this, MainActivity.class);
		updateIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		//updateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		pendingIntent = PendingIntent.getActivity(this, 0, updateIntent, 0);
		notification.contentIntent = pendingIntent;
		
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(R.layout.notification_item, notification);
	}
	/** 带按钮的通知栏点击广播接�? */
	public void initButtonReceiver() {
		bReceiver = new ButtonBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(ACTION_BUTTON);
		getApplication().registerReceiver(bReceiver, intentFilter);//加上getApplication()不易丢失
	}

	/**
	 * 广播监听按钮点击时间
	 */
	public class ButtonBroadcastReceiver extends BroadcastReceiver {
	
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (action.equals(ACTION_BUTTON)) {
				// 通过传�?�过来的ID判断按钮点击属�?�或者�?�过getResultCode()获得相应点击事件
				int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
				switch (buttonId) {
				case BUTTON_PREV_ID:
					notificationManager.cancel(R.layout.notification_item);//删除�?个特定的通知ID对应的�?�知
					jud=false;
					stopService(updateIntent);
					stopSelf();
					//onDestroy();
					//context.unregisterReceiver(this);
					break;
				case BUTTON_PALY_ID:
					Toast.makeText(UpdateServices.this, "正在更新…",
							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
		}
	}
	public PendingIntent getDefalutIntent(int flags){
		PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);
		return pendingIntent;
	}
	public long downloadUpdateFile(String down_url, String file)throws Exception {
		
		int down_step = down_step_custom;
		int totalSize;
		int downloadCount = 0;
		int updateCount = 0;
		InputStream inputStream;
		OutputStream outputStream;
		URL url = new URL(down_url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setConnectTimeout(TIMEOUT);
		httpURLConnection.setReadTimeout(TIMEOUT);
		totalSize = httpURLConnection.getContentLength();
		if (httpURLConnection.getResponseCode() == 404) {
			throw new Exception("fail!");					
		}
		
		inputStream = httpURLConnection.getInputStream();
		outputStream = new FileOutputStream(file, false);// �ļ������򸲸ǵ�
		byte buffer[] = new byte[1024];
		int readsize = 0;
		
		while ((readsize = inputStream.read(buffer)) != -1) {
			if (httpURLConnection.getResponseCode() == 404) {
				notificationManager.cancel(R.layout.notification_item);
				throw new Exception("fail!");					
			}					
			outputStream.write(buffer, 0, readsize);
			downloadCount += readsize;
			if (updateCount == 0 || (downloadCount * 100 / totalSize - down_step) >= updateCount) {
				if(jud)
				{
					updateCount += down_step;
					contentView.setTextViewText(R.id.notificationPercent,updateCount + "%");
					contentView.setProgressBar(R.id.notificationProgress, 100,updateCount, false);			
					notification.contentView = contentView;
					notificationManager.notify(R.layout.notification_item, notification);	
				}
				else
				{
					notificationManager.cancel(R.layout.notification_item);
					inputStream.close();
					outputStream.close();
				}
			}
		}
		if (httpURLConnection != null) {
			httpURLConnection.disconnect();
		}
		inputStream.close();
		outputStream.close();
		return downloadCount;
	}
	@Override 
	 public void onDestroy() 
	 { 
		//getApplicationContext().unregisterReceiver(bReceiver);
		super.onDestroy(); 
	 } 

}
