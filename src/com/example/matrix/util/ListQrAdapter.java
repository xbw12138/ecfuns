package com.example.matrix.util;

import java.util.List;

import com.example.frag3.QQTalkActivity;
import com.example.frag3.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListQrAdapter extends BaseAdapter {
	private List<ListQrModel> mDate;
	private Context mContext;
	public CreateQR qr;

	public ListQrAdapter(Context mContext, List<ListQrModel> mDate) {
		this.mContext = mContext;
		this.mDate = mDate;
		qr = new CreateQR(mContext.getApplicationContext());
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

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根

		View view = View.inflate(mContext, R.layout.list_qr_adapter, null);

		final ListQrModel model = mDate.get(position);
		final TextView txt_name = (TextView) view.findViewById(R.id.text_name);
		ImageView head = (ImageView) view.findViewById(R.id.image_send);
		ImageView image = (ImageView) view.findViewById(R.id.imageView);
		RelativeLayout btn1 = (RelativeLayout) view.findViewById(R.id.rrr);
		image.setVisibility(View.GONE);
		txt_name.setText(model.getid());
		qr.getQR(model.geturl(), head);
		if (model.getfinish().equals("Y")) {
			image.setVisibility(View.VISIBLE);
		}
		btn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				// txt_name.setText("点击事件响应1");
				if (model.getfinish().equals("N")) {
					Intent mIntent = new Intent();
					mIntent.putExtra("url",model.geturl());
					mIntent.setClass(mContext, QQTalkActivity.class);
					mContext.startActivity(mIntent);
				} else {
					Toast.makeText(mContext, "悄悄话未启用", 8000).show();
				}
			}
		});
		/*btn1.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				// TODO 自动生成的方法存根
				
				return false;
			}
			
		});*/
		return view;
	}
}
