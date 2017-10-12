package com.example.frag3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.example.mysql.AsyncTask_Change_Content;
import com.example.mysql.AsyncTask_Change_Content.MysqlListenerss;

public class QQTalkActivity extends Activity {
	public static WebView webView;
	public ProgressBar pb;
	PullRefreshLayout layout;
	private EditText et; 
	private Button btn;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//沉浸式状态栏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//沉浸式状态栏
		setContentView(R.layout.activity_qqtalk);
		TextView textview = (TextView) findViewById(R.id.title_name);
		textview.setText("悄悄话");
		et=(EditText)findViewById(R.id.editText1);
		btn=(Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				AsyncTask_Change_Content change=new AsyncTask_Change_Content(QQTalkActivity.this);
				change.setMysqlListenerss(new MysqlListenerss(){

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
				change.execute(getIntent().getStringExtra("url"),et.getText().toString());
			}
			
		});
		init();
		layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				init();
			}
		});
	}

	public void init() {
		// 进度条显示
		pb = (ProgressBar) findViewById(R.id.pb);
		pb.setMax(100);
		String url = getIntent().getStringExtra("url");
		webView = (WebView) findViewById(R.id.webView1);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setSupportMultipleWindows(true);
		// 覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		webView.setWebViewClient(new webViewClient());
		webView.setWebChromeClient(new webChromeClient());
		webView.loadUrl(url);
	}

	private class webChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			pb.setProgress(newProgress);
			if (newProgress == 100) {
				// 加载完成刷新图标消失
				layout.setRefreshing(false);
				pb.setVisibility(View.GONE);
			}
			super.onProgressChanged(view, newProgress);
		}

		@SuppressWarnings("unused")
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			// 如果不需要其他对点击链接事件的处理返回true，否则返回false
			return true;
		}
	}

	private class webViewClient extends WebViewClient {
		@SuppressWarnings("unused")
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			pb.setVisibility(View.VISIBLE);
			// 如果不需要其他对点击链接事件的处理返回true，否则返回false
			return true;
		}

	}

	public void back(View view) {
		finish();
	}

}
