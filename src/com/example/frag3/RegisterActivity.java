package com.example.frag3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.example.matrix.util.Config;
import com.example.matrix.util.Get_Set;
import com.example.matrix.util.ProgressDialogs;
import com.example.matrix.util.SmsContent;
import com.example.matrix.util.TimeCountUtil;
import com.example.mysql.AsyncTask_Insert_Register;
import com.example.mysql.AsyncTask_Insert_Register.MysqlListeners;
import com.example.mysql.AsyncTask_Is_User_Exist;
import com.example.mysql.AsyncTask_Is_User_Exist.MysqlListenerss;

public class RegisterActivity extends Activity{
	
	private EditText mUsernameET;
	private EditText mPasswordET;
	private EditText mCodeET;
	private EditText mPasswordET2;
	private Button mSendmsgBtn;
	private Button mSignupBtn;
	private CheckBox mPasswordCB;
	private CheckBox mPasswordCB2;
	private String phone_number="";
	private boolean success=false;
	Get_Set userisexist;
	Dialog dialog;
	SmsContent content;
	//private SmsReceiver smsReceiver;
	 @Override 
	    protected void onCreate(Bundle savedInstanceState) { 
	        super.onCreate(savedInstanceState); 
	        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//沉浸式状态栏
		    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//沉浸式状态栏
	        setContentView(R.layout.activity_register); 
	        //SmsReceiver.autoFillListener = this;
	        TextView textview=(TextView)findViewById(R.id.title_name);
		    textview.setText(getString(R.string.register_sign_up));
	        initView();
	        SMSSDK.initSDK(this,Config.mob_APPKEY,Config.mob_APPSECRET);
	        EventHandler eh=new EventHandler(){
				@Override
				public void afterEvent(int event, int result, Object data) {
					Message msg = new Message();
					msg.arg1 = event;
					msg.arg2 = result;
					msg.obj = data;
					handlersms.sendMessage(msg);
				}
			};
			SMSSDK.registerEventHandler(eh);
	 }
	 @SuppressLint("NewApi") private void initView()
	 {
		 mUsernameET = (EditText) findViewById(R.id.chat_register_username);
		 mPasswordET = (EditText) findViewById(R.id.chat_register_password);
		 mPasswordET2 = (EditText) findViewById(R.id.chat_register_password2);
		 mCodeET = (EditText) findViewById(R.id.chat_register_code);
		 mSendmsgBtn = (Button) findViewById(R.id.chat_register_sendmsg_btn);
		 mSignupBtn = (Button) findViewById(R.id.chat_register_signup_btn);
		 mPasswordCB = (CheckBox) findViewById(R.id.chat_register_password_checkbox);
		 mPasswordCB2 = (CheckBox) findViewById(R.id.chat_register_password_checkbox2);
		 mUsernameET.addTextChangedListener(myWatchers);
		 mCodeET.addTextChangedListener(myWatcher);  
		 mPasswordCB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					if (arg1) {
						mPasswordCB.setChecked(true);
						mPasswordET
								.setTransformationMethod(HideReturnsTransformationMethod
										.getInstance());
					} else {
						mPasswordCB.setChecked(false);
						mPasswordET
								.setTransformationMethod(PasswordTransformationMethod
										.getInstance());
					}
				}
			});
			mPasswordCB2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					if (arg1) {
						mPasswordCB2.setChecked(true);
						mPasswordET2
								.setTransformationMethod(HideReturnsTransformationMethod
										.getInstance());
					} else {
						mPasswordCB2.setChecked(false);
						mPasswordET2
								.setTransformationMethod(PasswordTransformationMethod
										.getInstance());
					}
				}
			});
			mSendmsgBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
					if(TextUtils.isEmpty(mUsernameET.getText().toString()))
					{
						Toast.makeText(getApplicationContext(), "手机号码不能为空", 1).show();
					}else if(!judgephone(mUsernameET.getText().toString())){
						
						Toast.makeText(getApplicationContext(), "请输入正确的手机号码", 1).show();
					}else{
						dialog=ProgressDialogs.createLoadingDialog(RegisterActivity.this,"数据加载中");
						dialog.show();
						new Thread() {
							public void run() {
								try {
									SMSSDK.getVerificationCode("86",mUsernameET.getText().toString());
									success=true;
								 	} catch (Exception e) {
									 e.printStackTrace();
									} finally { 
									 }
									}
								}.start();
////////////////////////////////////////////////////////////////////////////////////
					}
				}
			});
			mSignupBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					final String userName = mUsernameET.getText().toString().trim();
					final String password = mPasswordET.getText().toString().trim();
					final String password2 = mPasswordET2.getText().toString().trim();
					final String code = mCodeET.getText().toString().trim();
					
					if (TextUtils.isEmpty(userName)) {
						Toast.makeText(getApplicationContext(), "请输入手机号",
								Toast.LENGTH_SHORT).show();
					}else if(!judgephone(userName)){
						Toast.makeText(getApplicationContext(), "请输入正确的手机号",
								Toast.LENGTH_SHORT).show();
					}else if (TextUtils.isEmpty(password)) {
						Toast.makeText(getApplicationContext(), "请输入密码",
								Toast.LENGTH_SHORT).show();
					}else if(password.length()<6){
						Toast.makeText(getApplicationContext(), "密码长度不小于6位",
								Toast.LENGTH_SHORT).show();
					}else if (TextUtils.isEmpty(password2)) {
						Toast.makeText(getApplicationContext(), "请确认输入密码",
								Toast.LENGTH_SHORT).show(); 
					}else if (!password.equals(password2)) {
						Toast.makeText(getApplicationContext(), "两次密码输入不一致",
								Toast.LENGTH_SHORT).show();
						
					} else{
						AsyncTask_Insert_Register in= new AsyncTask_Insert_Register(RegisterActivity.this);
						in.setMysqlListeners(new MysqlListeners(){

							@Override
							public void Success() {
								// TODO 自动生成的方法存根
								finish();
							}

							@Override
							public void Fail() {
								// TODO 自动生成的方法存根
								
							}
							
						});
						in.execute(mUsernameET.getText().toString(),mPasswordET.getText().toString());
						
////////////////////////////////////////////////////////////////////////////////////
					}
				}
			});
			mSignupBtn.setClickable(false); 
			mSignupBtn.setBackground(RegisterActivity.this.getResources().getDrawable(R.color.sign_up_btn_normal));
			mSendmsgBtn.setText(getString(R.string.register_send_verification_code));
			mSendmsgBtn.setClickable(false);
			mSendmsgBtn.setBackground(this.getResources().getDrawable(R.drawable.btn_long_white));

	 }
	 Handler handlersms=new Handler(){

			@SuppressLint("NewApi") @SuppressWarnings("deprecation")
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				Log.e("event", "event="+event);
				if (result == SMSSDK.RESULT_COMPLETE) {
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
						if (result==SMSSDK.RESULT_COMPLETE) {
							Toast.makeText(getApplicationContext(), "短信验证成功", Toast.LENGTH_SHORT).show();
							mSignupBtn.setBackground(RegisterActivity.this.getResources().getDrawable(R.color.sign_up_btn_press));
							mSignupBtn.setClickable(true); 
						}
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
						Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
						TimeCountUtil timeCountUtil= new TimeCountUtil(RegisterActivity.this, 60000, 1000, mSendmsgBtn);
						timeCountUtil.start();
						//smsReceiver = new SmsReceiver();
						//registerBroadcastReceiver();
						content = new SmsContent(RegisterActivity.this, new Handler(), mCodeET);
						   // 注册短信变化监听
						getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, content);
						//dialog.dismiss();
					}else if(event==SMSSDK.RESULT_ERROR){
						Toast .makeText(getApplicationContext(),"------" ,1000).show();
					}
				} else {
					((Throwable) data).printStackTrace();
					Toast.makeText(getApplicationContext(), "错误"+data, 10000).show();
				}	
				dialog.dismiss();
			}
			
		};
	 TextWatcher myWatcher = new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
				if(mCodeET.getText().length() == 4&&judgephone(mUsernameET.getText().toString())&&success){  
					dialog=ProgressDialogs.createLoadingDialog(RegisterActivity.this,"数据加载中");
					dialog.show();
					new Thread() {
					public void run() {
						try {
							SMSSDK.submitVerificationCode("86", mUsernameET.getText().toString(), mCodeET.getText().toString());
						 	} catch (Exception e) {
							 e.printStackTrace();
							} finally { 
							   
							 }
							}
						}.start();
					
				}
////////////////////////////////////////////////////////////////////////////////////
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO 自动生成的方法存根
				
			}
			};
			TextWatcher myWatchers = new TextWatcher(){
				@SuppressLint("NewApi") @Override
				public void afterTextChanged(Editable s) {
					if(mUsernameET.getText().length() == 11){
						if(judgephone(mUsernameET.getText().toString())){ 
						    AsyncTask_Is_User_Exist in= new AsyncTask_Is_User_Exist(RegisterActivity.this);
					        in.setMysqlListenerss(new MysqlListenerss(){

								@Override
								public void Success() {
									// TODO 自动生成的方法存根
									mSendmsgBtn.setText(getString(R.string.register_send_verification_code));
									mSendmsgBtn.setClickable(true);
									mSendmsgBtn.setBackground(RegisterActivity.this.getResources().getDrawable(R.drawable.btn_long_red));
								}

								@Override
								public void Fail() {
									// TODO 自动生成的方法存根
									mSendmsgBtn.setText(getString(R.string.register_send_verification_code));
									mSendmsgBtn.setClickable(false);
									mSendmsgBtn.setBackground(RegisterActivity.this.getResources().getDrawable(R.drawable.btn_long_white));
								}
					        	
					        });
					        in.execute(mUsernameET.getText().toString());
						}
						}else{
							success=false;
							mSignupBtn.setClickable(false); 
							mSignupBtn.setBackground(RegisterActivity.this.getResources().getDrawable(R.color.sign_up_btn_normal));
					}
	////////////////////////////////////////////////////////////////////////////////////
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO 自动生成的方法存根
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start, int before,
						int count) {
					// TODO 自动生成的方法存根
					
				}
				};
	public boolean judgephone(String phone){
			String str="";
			str=phone;
			String pattern = "(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(str);
			return m.matches();
	 }
	 public void back(View view) {
			finish();
	 }
	 @Override
		protected void onDestroy() {
		 super.onDestroy();
			SMSSDK.unregisterAllEventHandler();
			if(content!=null)
			this.getContentResolver().unregisterContentObserver(content);
			// stopBroadcastReceiver();
		}
	 /*@Override
	public void autofill(String code) {
		// TODO 自动生成的方法存根
		if(mCodeET!=null){
			mCodeET.setText(code);
		}
	}
	private void stopBroadcastReceiver() {
		this.unregisterReceiver(smsReceiver);
	}

	private void registerBroadcastReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.setPriority(800);
		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		this.registerReceiver(smsReceiver, filter);
	}*/
}
