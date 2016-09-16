package com.king.photo.activity;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frag3.R;
import com.example.frag3.SendActivity;
import com.example.matrix.util.ListTalkModel;
import com.example.matrix.util.SharedPreference;
import com.example.mysql.AsyncTask_Insert_Talk;
import com.example.mysql.Config_mysql;
import com.example.mysql.AsyncTask_Insert_Talk.MysqlListeners;
import com.example.upimg.FileUtil;
import com.example.upimg.NetUtil;
import com.king.photo.util.Bimp;
import com.king.photo.util.FileUtils;
import com.king.photo.util.ImageItem;
import com.king.photo.util.PublicWay;
import com.king.photo.util.Res;


/**
 * 首页面activity
 *
 * @author king
 * @QQ:595163260
 * @version 2014年10月18日  下午11:48:34
 */
public class SendActivitys extends Activity {

	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap ;
	private String urlpath; // 图片本地路径
	private String resultStr = ""; // 服务端返回结果集
	private String imgUrl = Config_mysql.mysql_url_up_img;
	private String uurl = Config_mysql.urlimg;
	private String pathurl = "";
	SharedPreference sharedpreference;
	private TextView tv_x;
	private EditText ed;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Res.init(this);
		bimap = BitmapFactory.decodeResource(
				getResources(),
				R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		parentView = getLayoutInflater().inflate(R.layout.activity_selectimg, null);
		setContentView(parentView);
		sharedpreference = new SharedPreference(this);
		tv_x = (TextView) findViewById(R.id.text_time);
		tv_x.setText("您还可以输入" + 140 + " \\ " + 140);
		TextView tx=(TextView)findViewById(R.id.activity_selectimg_send);
		ed=(EditText)findViewById(R.id.sendcontent);
		ed.addTextChangedListener(myWatcher2);

		
		tx.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Toast.makeText(SendActivitys.this, "hahahah", 8000).show();
				if(Bimp.tempSelectBitmap.size()==0){
					Toast.makeText(SendActivitys.this, "请插入一些图片", 8000).show();
				}
				for(int i=0;i<Bimp.tempSelectBitmap.size();i++){
					//Bitmap photo = Bimp.tempSelectBitmap.get(i).getBitmap();
					//Drawable drawable = new BitmapDrawable(null, photo);
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
					String user_signtime = df.format(new Date());
					urlpath = FileUtil.saveFile(SendActivitys.this, "_" + sharedpreference.getID()
							+ user_signtime +"-"+ i+"_.jpg", Bimp.tempSelectBitmap.get(i).getBitmap());//将图片另存为重命名；
					//urlpath="_" +sharedpreference.getID()
					//				+ user_signtime +"-"+Bimp.tempSelectBitmap.get(i).getImagePath()+ "-"+i;
					Log.i("iiii",urlpath);
					pathurl+= uurl+"_" + sharedpreference.getID() + user_signtime +"-"+ i+ "_.jpg";
					pathurl+="#";
					//urlpath=Bimp.tempSelectBitmap.get(i).getImagePath();
					//Log.i("iiii",j);
					new Thread(uploadImageRunnable).start();
					
					if(i==Bimp.tempSelectBitmap.size()-1){
						AsyncTask_Insert_Talk in = new AsyncTask_Insert_Talk(
								SendActivitys.this);
						in.setMysqlListeners(new MysqlListeners() {
							@Override
							public void Success() {
								// TODO 自动生成的方法存根
								// 上传图片到服务器
								// new Thread(uploadImageRunnable).start();
								
								//finish();
								for(int i=0;i<PublicWay.activityList.size();i++){
									if (null != PublicWay.activityList.get(i)) {
										PublicWay.activityList.get(i).finish();
									}
								}
								//Bimp.tempSelectBitmap.clear();
							}
							@Override
							public void Fail() {
								// TODO 自动生成的方法存根
								Toast.makeText(SendActivitys.this, "发送失败", 8000).show();
							}
						});
						String content = ed.getText().toString();
						if (content.equals("")) {
							content = "我这个人很懒,一个字都不发";
						}
						in.execute(sharedpreference.getID(), content, pathurl,android.os.Build.MODEL);
					}
				}
			}
		});
		Init();
	}
	TextWatcher myWatcher2 = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO 自动生成的方法存根
			int len = 140 - ed.getText().length();
			if (len >= 0)
				tv_x.setText("您还可以输入" + len + " \\ " + 140);
			else {
				tv_x.setText("输入超限");
				Toast.makeText(SendActivitys.this, "字数超限", 8000).show();
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO 自动生成的方法存根
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO 自动生成的方法存根
		}
	};
	/**
	 * 使用HttpUrlConnection模拟post表单进行文件 上传平时很少使用，比较麻烦 原理是：
	 * 分析文件上传的数据格式，然后根据格式构造相应的发送给服务器的字符串。
	 */
	Runnable uploadImageRunnable = new Runnable() {
		@Override
		public void run() {
			if (TextUtils.isEmpty(imgUrl)) {
				Toast.makeText(SendActivitys.this, "还没有设置上传服务器的路径！",
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
				//Toast.makeText(SendActivitys.this, "上传图片成功", Toast.LENGTH_SHORT)
				//		.show();
				//opensend = false;
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
						Toast.makeText(SendActivitys.this,
								jsonObject.optString("statusMessage"),
								Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

				break;
			case 300:
				Toast.makeText(SendActivitys.this, "请求URL失败！",
						Toast.LENGTH_SHORT).show();
				break;
			case 400:
				Toast.makeText(SendActivitys.this, "未知异常", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
			return false;
		}
	});

	public void Init() {
		
		pop = new PopupWindow(SendActivitys.this);
		
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
		
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view
				.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view
				.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view
				.findViewById(R.id.item_popupwindows_cancel);
		parent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				photo();
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SendActivitys.this,
						AlbumActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);	
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(AnimationUtils.loadAnimation(SendActivitys.this,R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(SendActivitys.this,
							GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if(Bimp.tempSelectBitmap.size() == 9){
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position ==Bimp.tempSelectBitmap.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	private static final int TAKE_PICTURE = 0x000001;

	public void photo() {
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				
				String fileName = String.valueOf(System.currentTimeMillis());
				Bitmap bm = (Bitmap) data.getExtras().get("data");
				FileUtils.saveBitmap(bm, fileName);
				
				ImageItem takePhoto = new ImageItem();
				takePhoto.setBitmap(bm);
				Bimp.tempSelectBitmap.add(takePhoto);
			}
			break;
		}
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			for(int i=0;i<PublicWay.activityList.size();i++){
				if (null != PublicWay.activityList.get(i)) {
					PublicWay.activityList.get(i).finish();
				}
			}
			//System.exit(0);
		}
		return true;
	}

}

