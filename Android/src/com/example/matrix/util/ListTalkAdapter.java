package com.example.matrix.util;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.frag3.ImagePagerActivity;
import com.example.frag3.PlActivity;
import com.example.frag3.R;
import com.example.frag3.SingleTalkActivity;
import com.example.ninepic.NoScrollGridView;
import com.example.ninepic.NoScrollGridAdapter;
import com.example.imgload.ImageLoader;
import com.example.imgload.ImageLoaderHead;
import com.example.mysql.AsyncTask_Change_Zan;
import com.example.mysql.AsyncTask_Change_Zan.MysqlListenersss;
import com.example.mysql.AsyncTask_Insert_Talk;
import com.example.mysql.AsyncTask_Insert_Talk.MysqlListeners;
import com.example.mysql.Singleton;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class ListTalkAdapter extends BaseAdapter {

	private List<ListTalkModel> mDate;
	private Context mContext;
	public ImageLoader imageLoader;
	public ImageLoaderHead imageLoaders;

	public ListTalkAdapter(Context mContext, List<ListTalkModel> mDate) {
		this.mContext = mContext;
		this.mDate = mDate;
		imageLoader = new ImageLoader(mContext.getApplicationContext());
		imageLoaders = new ImageLoaderHead(mContext.getApplicationContext());
	}

	@Override
	public int getCount() {
		return mDate.size();
	}

	@Override
	public Object getItem(int position) {
		return mDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view = View.inflate(mContext, R.layout.list_talk_adapter, null);

		final ListTalkModel model = mDate.get(position);
		ViewHolder.btn4 = (RelativeLayout) view.findViewById(R.id.rrr);
		ViewHolder.tv = (TextView) view.findViewById(R.id.textView1);
		ViewHolder.tv2 = (TextView) view.findViewById(R.id.textView2);
		ViewHolder.tv2.setText("来自 " + model.getPhonety());
		ViewHolder.txt_name = (TextView) view.findViewById(R.id.text_name);
		ViewHolder.txt_time = (TextView) view.findViewById(R.id.text_time);
		ViewHolder.txt_content = (TextView) view
				.findViewById(R.id.text_content);
		ViewHolder.head = (ImageView) view.findViewById(R.id.user_logo);
		// ImageView image = (ImageView) view.findViewById(R.id.image_send);
		ViewHolder.gridview = (NoScrollGridView) view
				.findViewById(R.id.gridview);
		ViewHolder.btn1 = (Button) view.findViewById(R.id.btn_zhuanfa);
		ViewHolder.btn2 = (Button) view.findViewById(R.id.btn_pinglun);
		ViewHolder.btn2.setText("评论(" + model.getPnum() + ")");
		ViewHolder.btn3 = (Button) view.findViewById(R.id.btn_zan);
		ViewHolder.btn3.setText("赞(" + model.getZan() + ")");
		ViewHolder.head.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent();
				mIntent.putExtra("phone", model.getPhone());
				mIntent.putExtra("name", model.getName());
				mIntent.setClass(mContext, SingleTalkActivity.class);
				mContext.startActivity(mIntent);
			}
			
		});
		ViewHolder.btn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent();
				mIntent.putExtra("idd", model.getID());
				mIntent.putExtra("time", model.getTime());
				mIntent.putExtra("name", model.getName());
				mIntent.putExtra("content", model.getContent());
				mIntent.putExtra("head", model.getHead());
				mIntent.putExtra("picurl", model.getUrls());
				mIntent.putExtra("pl", model.getPnum());
				mIntent.putExtra("zan", model.getZan());
				mIntent.putExtra("ty", model.getPhonety());
				mIntent.setClass(mContext, PlActivity.class);
				mContext.startActivity(mIntent);
			}
		});
		ViewHolder.btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				// txt_name.setText("点击事件响应1");
				ZhuanfaDialog in = new ZhuanfaDialog(mContext);
				in.showDialog(model, "talk");
				// showDialog(model);
			}

		});
		ViewHolder.btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				// Toast.makeText(mContext, "评论暂未开放", 8000).show();
				Intent mIntent = new Intent();
				mIntent.putExtra("idd", model.getID());
				mIntent.putExtra("time", model.getTime());
				mIntent.putExtra("name", model.getName());
				mIntent.putExtra("content", model.getContent());
				mIntent.putExtra("head", model.getHead());
				mIntent.putExtra("picurl", model.getUrls());
				mIntent.putExtra("pl", model.getPnum());
				mIntent.putExtra("zan", model.getZan());
				mIntent.putExtra("ty", model.getPhonety());
				mIntent.setClass(mContext, PlActivity.class);
				mContext.startActivity(mIntent);
			}
		});
		ViewHolder.btn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				// Toast.makeText(mContext, "点赞暂未开放", 8000).show();
				AsyncTask_Change_Zan in = new AsyncTask_Change_Zan(mContext,
						"talk");
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
				in.execute(model.getID());
			}
		});
		ViewHolder.txt_name.setText(model.getName());
		// 控制首页内容字数
		if (model.getContent().length() >= 120) {
			ViewHolder.txt_content.setText(model.getContent().subSequence(0,
					119)+"…………");
			ViewHolder.tv.setText("全文");
			ViewHolder.tv.setVisibility(View.VISIBLE);
		} else {
			ViewHolder.txt_content.setText(model.getContent());
			ViewHolder.tv.setVisibility(View.GONE);

		}
		ViewHolder.txt_time.setText(model.getTime());
		// 使用ImageLoader加载网络图片
		DisplayImageOptions options = new DisplayImageOptions.Builder()//
				.showImageOnLoading(R.drawable.ic_launcher) // 加载中显示的默认图片
				.showImageOnFail(R.drawable.ic_launcher) // 设置加载失败的默认图片
				.cacheInMemory(true) // 内存缓存
				.cacheOnDisk(true) // sdcard缓存
				.bitmapConfig(Config.RGB_565)// 设置最低配置
				.build();//
		imageLoaders.DisplayImage(model.getHead(), ViewHolder.head);
		final ArrayList<String> imageUrls = model.getUrls();
		if (imageUrls == null || imageUrls.size() == 0) { // 没有图片资源就隐藏GridView
			ViewHolder.gridview.setVisibility(View.GONE);
		} else {
			ViewHolder.gridview.setAdapter(new NoScrollGridAdapter(mContext,
					imageUrls));
		}
		// 点击回帖九宫格，查看大图
		ViewHolder.gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				imageBrower(position, imageUrls);
			}
		});
		return view;
	}

	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(mContext, ImagePagerActivity.class);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		mContext.startActivity(intent);
	}

	static class ViewHolder {
		private static TextView txt_name;
		private static TextView txt_time;
		private static TextView txt_content;
		private static ImageView head;
		private static NoScrollGridView gridview;
		private static Button btn1;
		private static Button btn2;
		private static Button btn3;
		private static RelativeLayout btn4;
		private static TextView tv;
		private static TextView tv2;
	}
}
