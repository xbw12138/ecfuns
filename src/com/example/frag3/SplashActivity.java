package com.example.frag3;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity; 
import android.app.Dialog;
import android.content.Context; 
import android.content.Intent; 
import android.os.AsyncTask;
import android.os.Bundle; 
import android.os.Handler; 
import android.os.Message; 
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
  

public class SplashActivity extends Activity { 
  
    @Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_splash); 
        boolean mFirst = isFirstEnter(SplashActivity.this,SplashActivity.this.getClass().getName()); 
        if(mFirst) {
        	
            mHandler.sendEmptyMessageDelayed(SWITCH_GUIDACTIVITY,1000); 
        }
        else {
            mHandler.sendEmptyMessageDelayed(SWITCH_MAINACTIVITY,1000); 
        }
    }    
    private static final String SHAREDPREFERENCES_NAME = "my_pref"; 
    private static final String KEY_GUIDE_ACTIVITY = "guide_activity"; 
    private boolean isFirstEnter(Context context,String className){ 
        if(context==null || className==null||"".equalsIgnoreCase(className))return false; 
        String mResultStr = context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_WORLD_READABLE) 
                 .getString(KEY_GUIDE_ACTIVITY, "");
        if(mResultStr.equalsIgnoreCase("false")) 
            return false; 
        else 
            return true; 
    } 
    private final static int SWITCH_MAINACTIVITY = 1000; 
    private final static int SWITCH_GUIDACTIVITY = 1001; 
    public Handler mHandler = new Handler(){ 
        public void handleMessage(Message msg) { 
            switch(msg.what){ 
            case SWITCH_MAINACTIVITY: 
                Intent mIntent = new Intent(); 
                mIntent.setClass(SplashActivity.this, LoginActivity.class); 
                SplashActivity.this.startActivity(mIntent); 
                SplashActivity.this.finish(); 
                break; 
            case SWITCH_GUIDACTIVITY: 
                mIntent = new Intent(); 
                mIntent.setClass(SplashActivity.this, GuideActivity.class); 
                SplashActivity.this.startActivity(mIntent); 
                SplashActivity.this.finish(); 
                break; 
            } 
            super.handleMessage(msg); 
        } 
    }; 
} 


