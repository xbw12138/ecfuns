package com.example.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.example.frag3.R;
import com.example.imgload.FileCache;
import com.example.matrix.util.ListQrAdapter;
import com.example.matrix.util.SharedPreference;
import com.example.mysql.AsyncTask_Change_Finish;
import com.example.mysql.AsyncTask_Change_Finish.MysqlListenerss;
import com.example.mysql.AsyncTask_Search_Qr;
import com.example.mysql.AsyncTask_Search_Qr.MysqlListener;
import com.example.mysql.Singleton;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class FindFragment extends Fragment implements IUiListener {
	private ListView lis1;
	private ListQrAdapter mAdapter;
	SharedPreference sharedpreference;
	PullRefreshLayout layout;
	Tencent mTencent;
	FileCache fileCache;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_find, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mTencent = Tencent.createInstance("1105401014", getActivity());
		fileCache = new FileCache(getActivity());
		initView();
		layout = (PullRefreshLayout) getActivity().findViewById(
				R.id.swipeRefreshLayouts);
		layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				init();
			}
		});
	}

	public void init() {
		sharedpreference = new SharedPreference(getActivity());
		AsyncTask_Search_Qr search = new AsyncTask_Search_Qr(getActivity());
		search.setMysqlListener(new MysqlListener() {
			@Override
			public void Success() {
				// TODO 自动生成的方法存根
				mAdapter = new ListQrAdapter(getActivity(), Singleton
						.getInstance().getlistQr());
				lis1.setAdapter(mAdapter);
				layout.setRefreshing(false);
			}

			@SuppressLint("ShowToast")
			@Override
			public void Fail() {
				// TODO 自动生成的方法存根
				layout.setRefreshing(false);
				Toast.makeText(getActivity(), "无数据", 8000).show();
			}
		});
		search.execute(sharedpreference.getID());
	}

	protected void initView() {
		lis1 = (ListView) getActivity().findViewById(R.id.listview_qr);
		init();
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
				TextView txt_id = (TextView) view.findViewById(R.id.text_name);
				ImageView img = (ImageView) view.findViewById(R.id.image_send);
				ImageView image = (ImageView) view.findViewById(R.id.imageView);
				showChangeDialog(txt_id.getText().toString(), img, position);
				return false;
			}
		});
	}

	public void showChangeDialog(final String id, final ImageView img,
			final int position) {
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(
				R.layout.delete_dialog, null);
		final Dialog dialog = new AlertDialog.Builder(getActivity()).create();
		dialog.show();
		dialog.getWindow().setContentView(layout);
		dialog.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		dialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		// 确定按钮
		Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
		Button btnfinish = (Button) layout.findViewById(R.id.btn_finish);
		Button btnnofinish = (Button) layout.findViewById(R.id.btn_nofinish);
		Button btnshare = (Button) layout.findViewById(R.id.Button01);
		// 从imageview中获取bitmap
		Bitmap bb = ((BitmapDrawable) img.getDrawable()).getBitmap();
		final String path = saveBitmap(bb, id);
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btnshare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if (Singleton.getInstance().getlistQr().get(position)
						.getfinish().equals("N")) {
					onClickShare(path);
				}else{
					Toast.makeText(getActivity(), "未开启使用不能分享", 8000).show();
				}
				
			}
		});
		btnfinish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AsyncTask_Change_Finish changes = new AsyncTask_Change_Finish(
						getActivity());
				changes.setMysqlListenerss(new MysqlListenerss() {
					@Override
					public void Success() {
						// TODO 自动生成的方法存根
						init();
						dialog.dismiss();
					}

					@Override
					public void Fail() {
						// TODO 自动生成的方法存根
					}
				});
				changes.execute(id, "N", Singleton.getInstance().getPhone());
			}
		});
		btnnofinish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AsyncTask_Change_Finish changes = new AsyncTask_Change_Finish(
						getActivity());
				changes.setMysqlListenerss(new MysqlListenerss() {
					@Override
					public void Success() {
						// TODO 自动生成的方法存根
						init();
						dialog.dismiss();
					}

					@Override
					public void Fail() {
						// TODO 自动生成的方法存根
					}
				});
				changes.execute(id, "Y", Singleton.getInstance().getPhone());
			}
		});
	}

	@Override
	public void onCancel() {
		// TODO 自动生成的方法存根

	}

	@Override
	public void onComplete(Object arg0) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void onError(UiError arg0) {
		// TODO 自动生成的方法存根

	}

	private void onClickShare(String path) {
		Bundle params = new Bundle();
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
		params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "ECFUN");
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
				QQShare.SHARE_TO_QQ_TYPE_IMAGE);
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://ecfun.cc");
		// params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,
		// QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
		mTencent.shareToQQ(getActivity(), params, new FindFragment());
	}

	/*
	 * private void onClickShare() { final Bundle params = new Bundle();
	 * params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,
	 * QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
	 * params.putString(QQShare.SHARE_TO_QQ_TITLE, "ECFUN悄悄话");
	 * params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "有你的悄悄话");
	 * params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,
	 * "http://www.qq.com/news/1.html");
	 * params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,
	 * "http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");
	 * params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "ECFUN");
	 * mTencent.shareToQQ(getActivity(), params, new FindFragment()); }
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (null != mTencent)
			mTencent.onActivityResult(requestCode, resultCode, data);
	}

	public String saveBitmap(Bitmap bm, String id) {
		File f = fileCache.getFile(id);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f.getPath();
	}

}
