package com.example.frag3;

import com.baoyz.widget.PullRefreshLayout;
import com.example.matrix.util.CustomFAB;
import com.example.matrix.util.ListTalkAdapter;
import com.example.matrix.util.ListViewLoadMore;
import com.example.matrix.util.SharedPreference;
import com.example.matrix.util.ListViewLoadMore.IsLoadingListener;
import com.example.mysql.AsyncTask_Search_Single_Talk;
import com.example.mysql.AsyncTask_Search_Single_Talk.MysqlListener;
import com.example.mysql.Singleton;
import com.king.photo.activity.SendActivitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class SingleTalkActivity extends Activity {
	private ListViewLoadMore lis1;
	private ListTalkAdapter mAdapter;
	PullRefreshLayout layout;
	private int page = 1;
	private String phone;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 沉浸式状态栏
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);// 沉浸式状态栏
		setContentView(R.layout.activity_singletalk);
		TextView title = (TextView) findViewById(R.id.title_name);
		title.setText(getIntent().getStringExtra("name"));
		phone = getIntent().getStringExtra("phone");
		initView();
		layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				init();
				page = 1;
			}
		});
	}

	// 初始化设置listview数据适配器
	public void init() {
		AsyncTask_Search_Single_Talk search = new AsyncTask_Search_Single_Talk(
				this);
		search.setMysqlListener(new MysqlListener() {
			@Override
			public void Success() {
				// TODO 自动生成的方法存根
				mAdapter = new ListTalkAdapter(SingleTalkActivity.this,
						Singleton.getInstance().getlistTalk());
				lis1.setAdapter(mAdapter);
				layout.setRefreshing(false);
				page++;
			}

			@Override
			public void Fail() {
				// TODO 自动生成的方法存根
				layout.setRefreshing(false);
				Toast.makeText(SingleTalkActivity.this, "无数据", 8000).show();
			}

			@Override
			public void Full() {
				// TODO 自动生成的方法存根

			}
		});
		search.execute(phone, 1 + "");

	}

	// 下拉追加listview数据适配器
	public void initpull(int p) {
		AsyncTask_Search_Single_Talk search = new AsyncTask_Search_Single_Talk(
				this);
		search.setMysqlListener(new MysqlListener() {
			@Override
			public void Success() {
				// TODO 自动生成的方法存根
				page++;
				mAdapter.notifyDataSetChanged();
				lis1.complateLoad();
			}

			@Override
			public void Fail() {
				// TODO 自动生成的方法存根
				layout.setRefreshing(false);
				Toast.makeText(SingleTalkActivity.this, "无数据", 8000).show();
			}

			@Override
			public void Full() {
				// TODO 自动生成的方法存根
				mAdapter.notifyDataSetChanged();
				lis1.complateLoad();
				// Toast.makeText(getActivity(), "数据全部加载", 8000).show();
			}
		});
		search.execute(phone, p + "");

	}

	protected void initView() {
		lis1 = (ListViewLoadMore) findViewById(R.id.listview_order);

		init();
		lis1.setOnLoadingListener(new IsLoadingListener() {
			@Override
			public void onLoad() {
				// TODO 自动生成的方法存根
				initpull(page);
			}
		});
		lis1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}
		});
		lis1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				// TextView txt_id = (TextView)
				// view.findViewById(R.id.order_id);
				// showChangeDialog(txt_id.getText().toString());

				return false;
			}
		});

	}

	public void back(View view) {
		finish();
	}
}
