package com.example.frag3;

import java.util.ArrayList;

import com.baoyz.widget.PullRefreshLayout;
import com.example.imgload.ImageLoaderHead;
import com.example.matrix.util.ChangeDialog;
import com.example.matrix.util.CustomFAB;
import com.example.matrix.util.ListPlAdapter;
import com.example.matrix.util.ListTalkAdapter;
import com.example.matrix.util.ListTalkModel;
import com.example.matrix.util.ListViewLoadMore;
import com.example.matrix.util.PlDialog;
import com.example.matrix.util.SharedPreference;
import com.example.matrix.util.ZhuanfaDialog;
import com.example.matrix.util.ListViewLoadMore.IsLoadingListener;
import com.example.mysql.AsyncTask_Search_Pl;
import com.example.mysql.AsyncTask_Change_Zan.MysqlListenersss;
import com.example.mysql.AsyncTask_Insert_Talk.MysqlListeners;
import com.example.mysql.AsyncTask_Search_Pl.MysqlListener;
import com.example.mysql.AsyncTask_Change_Zan;
import com.example.mysql.AsyncTask_Insert_Talk;
import com.example.mysql.AsyncTask_Search_Talk;
import com.example.mysql.Singleton;
import com.example.ninepic.NoScrollGridAdapter;
import com.example.ninepic.NoScrollGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class PlActivity extends Activity {
	private ListViewLoadMore lis1;
	private ListPlAdapter mAdapter;
	PullRefreshLayout layout;
	CustomFAB btn_fab;
	private int page = 1;
	private int pl_num;
	String ID;
	public ImageLoaderHead imageLoaders;
	ListTalkModel models;
	private MyHoveringScrollView view_hover;
	TextView txt_name;
	TextView txt_time;
	TextView txt_content;
	TextView textview;
	TextView txty;
	ImageView heads;
	Button btn1;
	Button btn2;
	Button btn3;
	private int index = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);// 沉浸式状态栏
		getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);// 沉浸式状态栏
		setContentView(R.layout.b);
		initui();
		// //////////////////////////////////////////////////////////////////////
		initView();
		layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				init();
				page = 1;
			}
		});
		// //////////////////////////////////////////////////////////////////////
		button();
	}
	//listView 高度计算，解决scrollview与listview的冲突
	public void setListViewHeight(ListViewLoadMore listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public void initui() {
		view_hover = (MyHoveringScrollView) findViewById(R.id.view_hover);
		txt_name = (TextView) findViewById(R.id.text_name);
		txt_time = (TextView) findViewById(R.id.text_time);
		txt_content = (TextView) findViewById(R.id.text_content);
		txty = (TextView) findViewById(R.id.textView2);
		heads = (ImageView) findViewById(R.id.user_logo);
		btn1 = (Button) findViewById(R.id.btn_zhuanfa);
		btn2 = (Button) findViewById(R.id.btn_pinglun);
		btn3 = (Button) findViewById(R.id.btn_zan);
		textview = (TextView) findViewById(R.id.title_name);
		view_hover.setTopView(R.id.top);
		imageLoaders = new ImageLoaderHead(this);
		ArrayList<String> urls = getIntent().getStringArrayListExtra("picurl");
		String time = getIntent().getStringExtra("time");
		String name = getIntent().getStringExtra("name");
		String content = getIntent().getStringExtra("content");
		String head = getIntent().getStringExtra("head");
		String pl = getIntent().getStringExtra("pl");
		String zan = getIntent().getStringExtra("zan");
		String ty = getIntent().getStringExtra("ty");
		ID = getIntent().getStringExtra("idd");
		models = new ListTalkModel();
		models.setContent(content);
		models.setName(name);
		models.setUrls(urls);
		txt_name.setText(name);
		txt_time.setText(time);
		txt_content.setText(content);
		imageLoaders.DisplayImage(head, heads);
		txty.setText("来自 "+ty);
		btn2.setText("评论(" + pl + ")");
		btn3.setText("赞(" + zan + ")");
		textview.setText("评论");
		loadpic(urls);
	}

	public void button() {
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				ZhuanfaDialog in = new ZhuanfaDialog(PlActivity.this);
				in.showDialog(models, "pl");
				// showDialog(models);
			}
		});
		btn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				PlDialog in = new PlDialog(PlActivity.this);
				in.showChangeDialog("评论一下不会怀孕", ID);
			}
		});
		btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				// Toast.makeText(mContext, "点赞暂未开放", 8000).show();
				AsyncTask_Change_Zan in = new AsyncTask_Change_Zan(
						PlActivity.this, "talk");
				in.setMysqlListenersss(new MysqlListenersss() {
					@Override
					public void Success() {
						// TODO 自动生成的方法存根
					}

					@Override
					public void Fail() {
						// TODO 自动生成的方法存根
					}
				});
				in.execute(ID);
			}
		});
	}

	// 初始化设置listview数据适配器
	public void init() {
		AsyncTask_Search_Pl search = new AsyncTask_Search_Pl(this);
		search.setMysqlListener(new MysqlListener() {
			@Override
			public void Success() {
				// TODO 自动生成的方法存根
				mAdapter = new ListPlAdapter(PlActivity.this, Singleton
						.getInstance().getlistPl());
				lis1.setAdapter(mAdapter);
				setListViewHeight(lis1);
				layout.setRefreshing(false);
				page++;
			}

			@Override
			public void Fail() {
				// TODO 自动生成的方法存根
				layout.setRefreshing(false);
				Toast.makeText(PlActivity.this, "无数据", 8000).show();
			}

			@Override
			public void Full() {
				// TODO 自动生成的方法存根

			}
		});
		search.execute(ID, 1 + "");

	}

	// 下拉追加listview数据适配器
	public void initpull(int p) {
		AsyncTask_Search_Pl search = new AsyncTask_Search_Pl(this);
		search.setMysqlListener(new MysqlListener() {
			@Override
			public void Success() {
				// TODO 自动生成的方法存根
				page++;
				mAdapter.notifyDataSetChanged();
				//setListViewHeight(lis1);
				lis1.complateLoad();
				setListViewHeight(lis1);
			}

			@Override
			public void Fail() {
				// TODO 自动生成的方法存根
				layout.setRefreshing(false);
				Toast.makeText(PlActivity.this, "无数据", 8000).show();
			}

			@Override
			public void Full() {
				// TODO 自动生成的方法存根
				mAdapter.notifyDataSetChanged();
				lis1.complateLoad();
				setListViewHeight(lis1);
				// Toast.makeText(getActivity(), "数据全部加载", 8000).show();
			}
		});
		search.execute(ID, p + "");
	}

	protected void initView() {
		lis1 = (ListViewLoadMore) findViewById(R.id.listview_order);
		btn_fab = (CustomFAB) findViewById(R.id.fab_btn);
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
				return false;
			}
		});
		btn_fab.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				PlDialog in = new PlDialog(PlActivity.this);
				in.showChangeDialog("评论一下不会怀孕", ID);
			}

		});
	}

	public void loadpic(ArrayList<String> urls) {
		NoScrollGridView gridview = (NoScrollGridView) findViewById(R.id.gridview);
		// 使用ImageLoader加载网络图片
		DisplayImageOptions options = new DisplayImageOptions.Builder()//
				.showImageOnLoading(R.drawable.ic_launcher) // 加载中显示的默认图片
				.showImageOnFail(R.drawable.ic_launcher) // 设置加载失败的默认图片
				.cacheInMemory(true) // 内存缓存
				.cacheOnDisk(true) // sdcard缓存
				.bitmapConfig(Config.RGB_565)// 设置最低配置
				.build();//
		final ArrayList<String> imageUrls = urls;
		if (imageUrls == null || imageUrls.size() == 0) { // 没有图片资源就隐藏GridView
			gridview.setVisibility(View.GONE);
		} else {
			gridview.setAdapter(new NoScrollGridAdapter(this, imageUrls));
		}
		// 点击回帖九宫格，查看大图
		gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				imageBrower(position, imageUrls);
			}
		});
	}

	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(this, ImagePagerActivity.class);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		this.startActivity(intent);
	}

	public void back(View view) {
		finish();
	}
}
