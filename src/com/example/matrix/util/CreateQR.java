package com.example.matrix.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.WriterException;

public class CreateQR {
	private Context context;
	public CreateQR(Context context) {
		this.context = context;
	}
	public void getQR(String s,ImageView img) {
		try {
			String contentString = s;
			if (!contentString.equals("")) {
				Bitmap qrCodeBitmap = EncodingHandler.createQRCode(
						contentString, 600);
				img.setImageBitmap(qrCodeBitmap);
			}
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
}
