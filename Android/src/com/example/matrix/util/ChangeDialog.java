package com.example.matrix.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.frag3.R;
import com.example.mysql.AsyncTask_Change_Name;
import com.example.mysql.AsyncTask_Change_Name.MysqlListenerss;

public class ChangeDialog {
	private EditText edit;
	private TextView charnum_tx;
	private int charnum = 15;
	
	Context context;
	public ChangeDialog(Context context) {
		this.context=context;
	}
	
	public void showChangeDialog(String contexts,final TextView nickname) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		RelativeLayout layout = (RelativeLayout) layoutInflater.inflate(
				R.layout.change_dialog, null);
		final Dialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		dialog.getWindow().setContentView(layout);
		dialog.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		dialog.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
						| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		TextView tex1 = (TextView) layout.findViewById(R.id.textView_title);
		tex1.setText("修改昵称");
		edit = (EditText) layout.findViewById(R.id.dialog_text);
		edit.setText(contexts);
		edit.addTextChangedListener(myWatcher);
		charnum_tx = (TextView) layout.findViewById(R.id.charnum_tx);
		Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
		btnOK.setText("更新");
		btnOK.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (edit.getText().length() > 15) {
					
				} else {
					SharedPreference sharedpreference=new SharedPreference(context);
					AsyncTask_Change_Name in=new AsyncTask_Change_Name(context);
					in.setMysqlListenerss(new MysqlListenerss(){

						@Override
						public void Success() {
							// TODO 自动生成的方法存根
							nickname.setText(edit.getText());
							dialog.dismiss();
						}

						@Override
						public void Fail() {
							// TODO 自动生成的方法存根
							
						}
						
					});
					in.execute(sharedpreference.getID(),edit.getText().toString().trim());
					
				}
			}
		});
		ImageButton btnClose = (ImageButton) layout
				.findViewById(R.id.dialog_close);
		btnClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	TextWatcher myWatcher = new TextWatcher() {
		@Override
		public void afterTextChanged(Editable s) {
			int len = charnum - edit.getText().length();
			if (len >= 0)
				charnum_tx.setText("您还可以输入" + len + " \\ " + charnum);
			else
				charnum_tx.setText("字数超限");
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}
	};
}
