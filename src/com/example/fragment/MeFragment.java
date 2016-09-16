package com.example.fragment;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frag3.LoginActivity;
import com.example.frag3.R;
import com.example.imgload.ImageLoaderHead;
import com.example.matrix.util.ChangeDialog;
import com.example.matrix.util.PullScrollView;
import com.example.matrix.util.SelectPicPopupWindow;
import com.example.matrix.util.SharedPreference;
import com.example.mysql.Config_mysql;
import com.example.mysql.Singleton;
import com.example.upimg.FileUtil;
import com.example.upimg.NetUtil;

public class MeFragment extends Fragment implements
		PullScrollView.OnTurnListener {
	private PullScrollView mScrollView;
	private ImageView mHeadImg;
	private ImageView mHead;
	public ImageLoaderHead imageLoader;
	private SelectPicPopupWindow menuWindow; // 自定义的头像编辑弹出框
	private static final int REQUESTCODE_PICK = 0; // 相册选图标记
	private static final int REQUESTCODE_TAKE = 1; // 相机拍照标记
	private static final int REQUESTCODE_CUTTING = 2; // 图片裁切标记
	private static final String IMAGE_FILE_NAME = "avatarImage.jpg";// 头像文件名称
	private String urlpath; // 图片本地路径
	private String resultStr = ""; // 服务端返回结果集
	private String imgUrl = Config_mysql.mysql_url_up_head;
	private String uurl = Config_mysql.urlimghead;
	private String pathurl = "404.jpg";
	private TextView nickname;
	private TextView shownickname;
	private EditText edit;
	private TextView charnum_tx;
	private int charnum = 0;
	private Button zhuxiao;
	
	private RelativeLayout r1;
	private RelativeLayout r2;
	private RelativeLayout r3;
	private RelativeLayout r4;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_ms, container, false);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		imageLoader = new ImageLoaderHead(getActivity());
		nickname = (TextView) getActivity().findViewById(R.id.attention_user);
		shownickname=(TextView)getActivity().findViewById(R.id.user_name);
		zhuxiao=(Button)getActivity().findViewById(R.id.btn_zhuxiao);
		r1=(RelativeLayout)getActivity().findViewById(R.id.RelativeLayout01);
		r2=(RelativeLayout)getActivity().findViewById(R.id.RelativeLayout00);
		r3=(RelativeLayout)getActivity().findViewById(R.id.RelativeLayout02);
		r4=(RelativeLayout)getActivity().findViewById(R.id.RelativeLayout03);
		initView();
		shownickname.setText(Singleton.getInstance().getName());
		nickname.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				ChangeDialog in=new ChangeDialog(getActivity());
				in.showChangeDialog(shownickname.getText().toString(),shownickname);
			}

		});
		zhuxiao.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				SharedPreference sharedpreference=new SharedPreference(getActivity());
				sharedpreference.DisconnectLogin();
				Intent mIntent = new Intent(); 
	            mIntent.setClass(getActivity(), LoginActivity.class); 
	            getActivity().startActivity(mIntent); 
				getActivity().finish();
			}
			
		});
		r1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(getActivity(), "暂未开放", 8000).show();
			}

		});
		r2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(getActivity(), "暂未开放", 8000).show();
				Log.i("########33","^^^^^^^^^");
			}

		});
		r3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(getActivity(), "暂未开放", 8000).show();
			}

		});
		r4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(getActivity(), "暂未开放", 8000).show();
			}

		});
	}

	protected void initView() {
		mScrollView = (PullScrollView) getActivity().findViewById(
				R.id.scroll_view);
		mHeadImg = (ImageView) getActivity().findViewById(R.id.background_img);
		mHead = (ImageView) getActivity().findViewById(R.id.user_avatar);
		imageLoader.DisplayImage(Singleton.getInstance().getHead(), mHead);
		mHead.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				menuWindow = new SelectPicPopupWindow(getActivity(),
						itemsOnClick);
				menuWindow.showAtLocation(
						getActivity().findViewById(R.id.melayout),
						Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
			}

		});
		mScrollView.setHeader(mHeadImg);
		mScrollView.setOnTurnListener(this);
	}

	@Override
	public void onTurn() {
		// TODO 自动生成的方法存根

	}

	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			// 拍照
			case R.id.takePhotoBtn:
				Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 下面这句指定调用相机拍照后的照片存储的路径
				takeIntent
						.putExtra(MediaStore.EXTRA_OUTPUT, Uri
								.fromFile(new File(Environment
										.getExternalStorageDirectory(),
										IMAGE_FILE_NAME)));
				startActivityForResult(takeIntent, REQUESTCODE_TAKE);
				break;
			// 相册选择图片
			case R.id.pickPhotoBtn:
				Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
				// 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
				pickIntent
						.setDataAndType(
								MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
								"image/*");
				startActivityForResult(pickIntent, REQUESTCODE_PICK);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUESTCODE_PICK:// 直接从相册获取
			try {
				startPhotoZoom(data.getData());
			} catch (NullPointerException e) {
				e.printStackTrace();// 用户点击取消操作
			}
			break;
		case REQUESTCODE_TAKE:// 调用相机拍照
			File temp = new File(Environment.getExternalStorageDirectory()
					+ "/" + IMAGE_FILE_NAME);
			startPhotoZoom(Uri.fromFile(temp));
			break;
		case REQUESTCODE_CUTTING:// 取得裁剪后的图片
			if (data != null) {
				setPicToView(data);
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, REQUESTCODE_CUTTING);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	@SuppressLint("SimpleDateFormat")
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		if (extras != null) {
			// 取得SDCard图片路径做显示
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(null, photo);

			SharedPreference sharedpreference = new SharedPreference(
					getActivity());
			urlpath = FileUtil.saveFile(getActivity(),
					"_" + sharedpreference.getID() + "_.jpg", photo);
			pathurl = "_" + sharedpreference.getID() + "_.jpg";
			mHead.setImageDrawable(drawable);
			// 上传图片到服务器
			new Thread(uploadImageRunnable).start();
			// new Thread(uploadImageRunnable).start();
			// Log.i("##########","@@@@@@@@@@@");
			// finish();
			// 上传图片到服务器第二种方法
			// new Task().execute();
		}
	}

	/**
	 * 使用HttpUrlConnection模拟post表单进行文件 上传平时很少使用，比较麻烦 原理是：
	 * 分析文件上传的数据格式，然后根据格式构造相应的发送给服务器的字符串。
	 */
	Runnable uploadImageRunnable = new Runnable() {
		@Override
		public void run() {
			if (TextUtils.isEmpty(imgUrl)) {
				Toast.makeText(getActivity(), "还没有设置上传服务器的路径！",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Map<String, String> textParams = new HashMap<String, String>();
			Map<String, File> fileparams = new HashMap<String, File>();
			try {
				// 创建一个URL对象
				URL url = new URL(imgUrl);
				textParams = new HashMap<String, String>();
				fileparams = new HashMap<String, File>();
				// 要上传的图片文件
				File file = new File(urlpath);
				fileparams.put("image", file);
				// 利用HttpURLConnection对象从网络中获取网页数据
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				// 设置连接超时（记得设置连接超时,如果网络不好,Android系统在超过默认时间会收回资源中断操作）
				conn.setConnectTimeout(5000);
				// 设置允许输出（发送POST请求必须设置允许输出）
				conn.setDoOutput(true);
				// 设置使用POST的方式发送
				conn.setRequestMethod("POST");
				// 设置不使用缓存（容易出现问题）
				conn.setUseCaches(false);
				conn.setRequestProperty("Charset", "UTF-8");// 设置编码
				// 在开始用HttpURLConnection对象的setRequestProperty()设置,就是生成HTML文件头
				conn.setRequestProperty("ser-Agent", "Fiddler");
				// 设置contentType
				conn.setRequestProperty("Content-Type",
						"multipart/form-data; boundary=" + NetUtil.BOUNDARY);
				OutputStream os = conn.getOutputStream();
				DataOutputStream ds = new DataOutputStream(os);
				NetUtil.writeStringParams(textParams, ds);
				NetUtil.writeFileParams(fileparams, ds);
				NetUtil.paramsEnd(ds);
				// 对文件流操作完,要记得及时关闭
				os.close();
				// 服务器返回的响应吗
				int code = conn.getResponseCode(); // 从Internet获取网页,发送请求,将网页以流的形式读回来
				// 对响应码进行判断
				if (code == 200) {// 返回的响应码200,是成功
					// 得到网络返回的输入流
					handler.sendEmptyMessage(200);
					// Toast.makeText(getActivity(), "上传头像成功",
					// Toast.LENGTH_SHORT).show();
					InputStream is = conn.getInputStream();
					resultStr = NetUtil.readString(is);
				} else {

					handler.sendEmptyMessage(300);
				}
			} catch (Exception e) {
				e.printStackTrace();
				handler.sendEmptyMessage(400);
			}
			// 执行耗时的方法之后发送消给handler
		}
	};

	Handler handler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 200:
				// dialogs.dismiss();
				Toast.makeText(getActivity(), "上传图片成功", Toast.LENGTH_SHORT)
						.show();
				// finish();
				try {
					// 返回数据示例，根据需求和后台数据灵活处理
					// {"status":"1","statusMessage":"上传成功","imageUrl":"http://120.24.219.49/726287_temphead.jpg"}
					JSONObject jsonObject = new JSONObject(resultStr);

					// 服务端以字符串“1”作为操作成功标记
					if (jsonObject.optString("status").equals("1")) {
						BitmapFactory.Options option = new BitmapFactory.Options();
						// 压缩图片:表示缩略图大小为原始图片大小的几分之一，1为原图，3为三分之一
						option.inSampleSize = 1;
						// 服务端返回的JsonObject对象中提取到图片的网络URL路径
						// String imageUrl = jsonObject.optString("imageUrl");

					} else {
						Toast.makeText(getActivity(),
								jsonObject.optString("statusMessage"),
								Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

				break;
			case 300:
				Toast.makeText(getActivity(), "请求URL失败！", Toast.LENGTH_SHORT)
						.show();
				break;
			case 400:
				Toast.makeText(getActivity(), "未知异常", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
			return false;
		}
	});
}
