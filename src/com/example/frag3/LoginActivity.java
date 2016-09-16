package com.example.frag3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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

import com.example.matrix.util.Get_Set;
import com.example.matrix.util.ProgressDialogs;
import com.example.matrix.util.SharedPreference;
import com.example.mysql.AsyncTask_Login_verify;
import com.example.mysql.AsyncTask_Login_verify.MysqlListeners;
import com.example.mysql.AsyncTask_Search_User;

public class LoginActivity extends Activity{
	private EditText mUsernameET;
	private EditText mPasswordET;
	private Button mSigninBtn;
	private Button mForget;
	private Button mSignupTV;
	private CheckBox mPasswordCB;
	Get_Set userisexist;
	Dialog dialog;
	 @Override 
	    protected void onCreate(Bundle savedInstanceState) { 
	        super.onCreate(savedInstanceState); 
	        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//沉浸式状态栏
		    //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//沉浸式状态栏
	        setContentView(R.layout.activity_login); 
	        //TextView textview=(TextView)findViewById(R.id.title_name);
		    //textview.setText(getString(R.string.login_sign_in));
		    SharedPreference sharedpreference=new SharedPreference(this);
			boolean islogin=sharedpreference.isLogin(this.getClass().getName());
			if(islogin){
				Intent mIntent = new Intent(); 
                mIntent.setClass(this, MainActivity.class); 
                this.startActivity(mIntent); 
                this.finish(); 
			}
	        initView();
	 }
	 private void initView()
	 {
		mUsernameET = (EditText) findViewById(R.id.chat_login_username);
		mPasswordET = (EditText) findViewById(R.id.chat_login_password);
		mSigninBtn = (Button) findViewById(R.id.chat_login_signin_btn);
		mSignupTV = (Button) findViewById(R.id.chat_login_signup);
		mPasswordCB = (CheckBox) findViewById(R.id.chat_login_password_checkbox);
		mForget=(Button) findViewById(R.id.chat_forgot_password);
		mPasswordCB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (arg1) {
					mPasswordCB.setChecked(true);
					//动态设置密码是否可见
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
		mSigninBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String userName = mUsernameET.getText().toString().trim();
				final String password = mPasswordET.getText().toString().trim();
				if (TextUtils.isEmpty(userName)) {
					Toast.makeText(getApplicationContext(), "请输入用户名",
							Toast.LENGTH_SHORT).show();
				} else if (TextUtils.isEmpty(password)) {
					Toast.makeText(getApplicationContext(), "请输入密码",
							Toast.LENGTH_SHORT).show();
				} else {
					AsyncTask_Login_verify in= new AsyncTask_Login_verify(LoginActivity.this);
					in.setMysqlListeners(new MysqlListeners(){

						@Override
						public void Success() {
							// TODO 自动生成的方法存根
							SharedPreference sharedpreference=new SharedPreference(LoginActivity.this);
							sharedpreference.KeepLogin(mUsernameET.getText().toString());//写入保持登陆状态
							//new AsyncTask_Search_User(LoginActivity.this).execute(mUsernameET.getText().toString());
							Intent mIntent = new Intent(); 
			                mIntent.setClass(LoginActivity.this, MainActivity.class); 
			                LoginActivity.this.startActivity(mIntent); 
			                LoginActivity.this.finish(); 
						}

						@Override
						public void Fail() {
							// TODO 自动生成的方法存根
							
						}

						@Override
						public void Exist() {
							// TODO 自动生成的方法存根
							
						}
						
					});
					in.execute(mUsernameET.getText().toString(),mPasswordET.getText().toString());
			        
							
				}
			}
		});
		mForget.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this,
						ForgetpwdActivity.class));
			}
		});
		mSignupTV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(LoginActivity.this,
						RegisterActivity.class));
			}
		});
		
	 }
	 public void back(View view) {
			finish();
	 }


}
