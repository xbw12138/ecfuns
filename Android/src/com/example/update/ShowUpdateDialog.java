package com.example.update;

import com.example.frag3.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShowUpdateDialog{
	private UpdateInfo info;
	//更新窗口
	public Dialog showUpdateDialog(final Context context,String msg,final String url) {
		LayoutInflater layoutInflater =LayoutInflater.from(context);
		RelativeLayout layout = (RelativeLayout)layoutInflater.inflate(R.layout.dialog, null );
		final Dialog dialog = new AlertDialog.Builder(context).create();
	    dialog.show();
	    dialog.getWindow().setContentView(layout);
	    TextView tex=(TextView)layout.findViewById(R.id.dialog_text);
	    TextView tex1=(TextView)layout.findViewById(R.id.textView_title);
	    tex.setMovementMethod(ScrollingMovementMethod.getInstance()); 
	    tex.setText(msg);
	    tex1.setText("更新提示");
         //确定按钮
         Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
         btnOK.setOnClickListener(new OnClickListener() {         
           @Override 
           public void onClick(View v) { 
        	   if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
        		   		//services.service();
        		   Intent intent = new Intent(context,UpdateServices.class);
        		   intent.putExtra("Key_App_Name",context.getString(R.string.app_name));
        		   intent.putExtra("Key_Down_Url",url);		
        		   context.startService(intent);
				} else {
					Toast.makeText(context, "SD卡不可用，请插入SD卡",
							Toast.LENGTH_SHORT).show();
				}
        	   dialog.dismiss();    
           }
         });	 
         //关闭按钮
         ImageButton btnClose = (ImageButton) layout.findViewById(R.id.dialog_close);
         btnClose.setOnClickListener(new OnClickListener() {	          
           @Override
           public void onClick(View v) {
              dialog.dismiss();          
           }
         });
         return dialog;
     }
	//不更新窗口
	public Dialog showDialog(final Context context) {
		LayoutInflater layoutInflater =LayoutInflater.from(context);
		RelativeLayout layout = (RelativeLayout)layoutInflater.inflate(R.layout.dialog, null );
		final Dialog dialog = new AlertDialog.Builder(context).create();
	    dialog.show();
	    dialog.getWindow().setContentView(layout);
	    TextView tex=(TextView)layout.findViewById(R.id.dialog_text);
	    TextView tex1=(TextView)layout.findViewById(R.id.textView_title);
	    GetVersion version=new GetVersion();
	    tex.setText("您使用的是最新版:"+version.getVersion(context)+"版本");
	    tex1.setText("更新提示");
	    Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
	    btnOK.setVisibility(View.INVISIBLE);
	  //关闭按钮
         ImageButton btnClose = (ImageButton) layout.findViewById(R.id.dialog_close);
         btnClose.setOnClickListener(new OnClickListener() {	          
           @Override
           public void onClick(View v) {
              dialog.dismiss();          
           }
         });
         return dialog;
	}

}
