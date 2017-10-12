package com.example.mysql;

import com.example.matrix.util.Config;

public class Config_mysql {
	public static String mysql_url_isuserexist=Config.ip+"/ecfun/mysql/isuserexist.php";
	public static String mysql_url_loginverify=Config.ip+"/ecfun/mysql/loginverify.php";
	public static String mysql_url_insert_user=Config.ip+"/ecfun/mysql/insert_login.php";
	public static String mysql_url_insert_talk=Config.ip+"/ecfun/mysql/talk/insert_talk.php";
	public static String mysql_url_change_password=Config.ip+"/ecfun/mysql/user/change_pwd.php";
	public static String mysql_url_change_name=Config.ip+"/ecfun/mysql/user/change_name.php";
	public static String mysql_url_search_user=Config.ip+"/ecfun/mysql/user/search_user.php";
	public static String mysql_url_search_talk=Config.ip+"/ecfun/mysql/talk/search_talk.php";
	public static String mysql_url_up_img=Config.ip+"/ecfun/image_bg_upload/receive_file.php";
	public static String urlimg=Config.ip+"/ecfun/image_bg_upload/upload/";
	public static String mysql_url_up_head=Config.ip+"/ecfun/image_head/receive_file.php";
	public static String urlimghead=Config.ip+"/ecfun/image_head/upload/receive_file.php";
	public static String mysql_url_search_qr=Config.ip+"/ecfun/qr/searchqrurl.php";
	public static String mysql_change_finish=Config.ip+"/ecfun/qr/change_finish.php";
	public static String mysql_change_content=Config.ip+"/ecfun/qr/change_content.php";
	public static String mysql_insert_pl=Config.ip+"/ecfun/mysql/pinglun/insert_pl.php";
	public static String mysql_search_pl=Config.ip+"/ecfun/mysql/pinglun/search_pl.php";
	public static String mysql_change_talkzan=Config.ip+"/ecfun/mysql/talk/change_zan.php";
	public static String mysql_change_plzan=Config.ip+"/ecfun/mysql/pinglun/change_zan.php";
	public static String URLPATH=Config.main_url;
	public static String mysql_url_search_single_talk=Config.ip+"/ecfun/mysql/talk/search_single_talk.php";
	public static String mysql_url_change_token=Config.ip+"/ecfun/mysql/user/change_token.php";
	public boolean httpjsonsuccess=false;
	public boolean isNetWork=true;
	public void Set_httpjsonsuccess(boolean httpjsonsuccess)
	{
		this.httpjsonsuccess=httpjsonsuccess;
	}
	public boolean Get_httpjsonsuccess()
	{
		return httpjsonsuccess;
	}
	public void Set_isNetWork(boolean isNetWork)
	{
		this.isNetWork=isNetWork;
	}
	public boolean Get_isNetWork()
	{
		return isNetWork;
	}
	public void Set_URLPATH(String URLPATH)
	{
		this.URLPATH=URLPATH;
	}
	public static String Get_URLPATH()
	{
		return URLPATH;
	}
	

}
