package com.example.matrix.util;

import java.util.List;

import com.example.frag3.R;
import com.example.imgload.ImageLoader;
import com.example.imgload.ImageLoaderHead;
import com.example.mysql.AsyncTask_Change_Zan;
import com.example.mysql.AsyncTask_Change_Zan.MysqlListenersss;
import com.example.ninepic.NoScrollGridView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListPlAdapter extends BaseAdapter {
	private List<ListPlModel> mDate;
	private Context mContext;
	public ImageLoaderHead imageLoaders;

	public ListPlAdapter(Context mContext, List<ListPlModel> mDate) {
		this.mContext = mContext;
		this.mDate = mDate;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(mContext, R.layout.list_pl_adapter, null);
		final ListPlModel model = mDate.get(position);
		ViewHolder.txt_name = (TextView) view.findViewById(R.id.text_name);
		ViewHolder.txt_time = (TextView) view.findViewById(R.id.text_time);
		ViewHolder.txt_content = (TextView) view.findViewById(R.id.text_content);
		ViewHolder.head = (ImageView) view.findViewById(R.id.user_logo);
		ViewHolder.btn3 = (Button) view.findViewById(R.id.btn_zan);
		ViewHolder.btn3.setText("赞(" + model.getZan() + ")");
		ViewHolder.btn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				// Toast.makeText(mContext, "点赞暂未开放", 8000).show();
				AsyncTask_Change_Zan in = new AsyncTask_Change_Zan(mContext,"pl");
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
		ViewHolder.txt_content.setText(model.getContent());
		ViewHolder.txt_time.setText(model.getTime());
		imageLoaders.DisplayImage(model.getHead(), ViewHolder.head);
		return view;
	}
	static class ViewHolder
	{
		private static TextView txt_name;
		private static TextView txt_time;
		private static TextView txt_content;
		private static ImageView head;
		private static Button btn3;
	}
}
