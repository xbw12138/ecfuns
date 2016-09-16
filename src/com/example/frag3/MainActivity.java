package com.example.frag3;


import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fragment.ContentFragment;
import com.example.fragment.FindFragment;
import com.example.fragment.MeFragment;
import com.example.matrix.util.ChangeDialog;
import com.example.matrix.util.SharedPreference;
import com.example.mysql.AsyncTask_Change_Token;
import com.example.mysql.AsyncTask_Change_Token.MysqlListenerss;
import com.example.mysql.AsyncTask_Search_User;
import com.example.mysql.AsyncTask_Search_User.MysqlListeners;

import com.example.mysql.Singleton;
import com.example.update.IsNeedUpdate;
import com.example.update.ShowUpdateDialog;
import com.nineoldandroids.view.ViewHelper;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.tauth.Tencent;
import com.umeng.analytics.MobclickAgent;

public class MainActivity extends FragmentActivity {

	private Button[] mTabs;
	private ContentFragment contactFragment;
	private FindFragment findFragment;
	private MeFragment meFragment;
	private Fragment[] fragments;
	private int index;
	// 当前fragment的index
	private int currentTabIndex;
	// 再按一下退出计时
	private long mExitTime = 0;
	private boolean isneedupdate=false;
	private String describe="";
	private String url="";
	SharedPreference sharedpreference;
	TextView tv_title;
	
	
	//复写该函数使得fragment避免多次重复加载
	@Override
	public void onSaveInstanceState(Bundle outState) {  
	    //super.onSaveInstanceState(outState);   //将这一行注释掉，阻止activity保存fragment的状态
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sharedpreference = new SharedPreference(this);
		//Context context = getApplicationContext();
		//XGPushManager.registerPush(context); 
		
		XGPushManager.registerPush(this, new XGIOperateCallback() {
			@Override
			public void onSuccess(Object data, int flag) {
				AsyncTask_Change_Token changes = new AsyncTask_Change_Token(
						MainActivity.this);
				changes.setMysqlListenerss(new MysqlListenerss() {
					@Override
					public void Success() {
				
					}

					@Override
					public void Fail() {
					
					}
				});
				changes.execute(sharedpreference.getID(), data + "");
			}

			@Override
			public void onFail(Object data, int errCode, String msg) {
				
			}
		});
		
		Singleton.getInstance().setPhone(sharedpreference.getID());
		MobclickAgent.onProfileSignIn(sharedpreference.getID());
		download();
		initView();
		//savedInstanceState是记录onpause时刻的状态，
		//如果这状态非空，就不要新建fragment了
		//if(savedInstanceState==null){
		initFragment();
		//}
		user();
	}
	private void user(){
		AsyncTask_Search_User in =new AsyncTask_Search_User(this);
		in.setMysqlListeners(new MysqlListeners(){
			@Override
			public void Success() {}
			@Override
			public void Fail() {}
		});
		in.execute(sharedpreference.getID());
	}
	private void download(){
		new Thread() {
			public void run() {
				try {
					IsNeedUpdate isNeedUpdate=new IsNeedUpdate(MainActivity.this);
					isneedupdate=isNeedUpdate.isNeedUpdate(MainActivity.this);
					describe=isNeedUpdate.getDescribe();
					url=isNeedUpdate.getUrl();
					handler1.sendEmptyMessage(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start(); 
	}
	@SuppressLint("HandlerLeak")
	private Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			// 如果有更新就提示
			if (isneedupdate) {
				ShowUpdateDialog show=new ShowUpdateDialog();
					show.showUpdateDialog(MainActivity.this, describe,url);
			}
		};
	};
	private void initFragment() {
		contactFragment = new ContentFragment();
		findFragment = new FindFragment();
		meFragment = new MeFragment();
		fragments = new Fragment[] { contactFragment, findFragment, meFragment };
		// 添加显示第一个fragment
		getSupportFragmentManager().beginTransaction()
				.add(R.id.id_content, contactFragment)
				.add(R.id.id_content, findFragment).hide(findFragment)
				.show(contactFragment).commit();
	}

	private void initView() {
		mTabs = new Button[3];
		mTabs[0] = (Button) findViewById(R.id.btn_contact);
		mTabs[1] = (Button) findViewById(R.id.btn_find);
		mTabs[2] = (Button) findViewById(R.id.btn_me);
		// 把第一个tab设为选中状态
		mTabs[0].setSelected(true);
		tv_title=(TextView)findViewById(R.id.title);
		tv_title.setText("首页");
	}

	public void onTabClicked(View view) {
		switch (view.getId()) {
		case R.id.btn_contact:
			index = 0;
			tv_title.setText("首页");
			break;
		case R.id.btn_find:
			index = 1;
			tv_title.setText("悄悄话");
			break;
		case R.id.btn_me:
			index = 2;
			tv_title.setText("我的");
			break;
		}
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.id_content, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);
		// 把当前tab设为选中状态
		mTabs[index].setSelected(true);
		currentTabIndex = index;
	}
	// 按两次返回退出
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出",
						Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
