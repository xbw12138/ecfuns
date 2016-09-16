package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.SMSSDK;

import com.baoyz.widget.PullRefreshLayout;
import com.example.frag3.DetailActivity;
import com.example.frag3.R;
import com.example.frag3.RegisterActivity;
import com.example.frag3.SendActivity;
import com.example.matrix.util.CustomFAB;
import com.example.matrix.util.ListTalkAdapter;
import com.example.matrix.util.ListTalkModel;
import com.example.matrix.util.ListViewLoadMore;
import com.example.matrix.util.ListViewLoadMore.IsLoadingListener;
import com.example.matrix.util.SharedPreference;
import com.example.matrix.util.SmsContent;
import com.example.matrix.util.TimeCountUtil;
import com.example.mysql.AsyncTask_Search_Talk;
import com.example.mysql.AsyncTask_Search_Talk.MysqlListener;
import com.example.mysql.Singleton;
import com.king.photo.activity.SendActivitys;

public class ContentFragment extends Fragment {
	private ListViewLoadMore lis1;
	private ListTalkAdapter mAdapter;
	CustomFAB btn_fab;
	SharedPreference sharedpreference;
	PullRefreshLayout layout;
	private int page=1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_content, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		layout = (PullRefreshLayout) getActivity().findViewById(
				R.id.swipeRefreshLayout);
		layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				init();
				page=1;
			}
		});
		
	}
	//初始化设置listview数据适配器
	public void init() {
		sharedpreference = new SharedPreference(getActivity());
		AsyncTask_Search_Talk search = new AsyncTask_Search_Talk(getActivity());
		search.setMysqlListener(new MysqlListener() {
			@Override
			public void Success() {
				// TODO 自动生成的方法存根
				mAdapter = new ListTalkAdapter(getActivity(), Singleton
						.getInstance().getlistTalk());
				lis1.setAdapter(mAdapter);
				layout.setRefreshing(false);
				page++;
			}
			@Override
			public void Fail() {
				// TODO 自动生成的方法存根
				layout.setRefreshing(false);
				Toast.makeText(getActivity(), "无数据", 8000).show();
			}
			@Override
			public void Full() {
				// TODO 自动生成的方法存根
				
			}
		});
		search.execute(sharedpreference.getID(),1+"");
		
	}
	//下拉追加listview数据适配器
	public void initpull(int p) {
		sharedpreference = new SharedPreference(getActivity());
		AsyncTask_Search_Talk search = new AsyncTask_Search_Talk(getActivity());
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
				Toast.makeText(getActivity(), "无数据", 8000).show();
			}
			@Override
			public void Full() {
				// TODO 自动生成的方法存根
				mAdapter.notifyDataSetChanged();
				lis1.complateLoad();
				//Toast.makeText(getActivity(), "数据全部加载", 8000).show();
			}
		});
		search.execute(sharedpreference.getID(),p+"");
		
	}
	protected void initView() {
		lis1 = (ListViewLoadMore) getActivity().findViewById(
				R.id.listview_order);
		btn_fab = (CustomFAB) getActivity().findViewById(R.id.fab_btn);
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
		btn_fab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent();
				intent.setClass(getActivity(), SendActivitys.class);
				intent.putExtra("title_name", "发布");
				startActivity(intent);
			}

		});
	}
}
