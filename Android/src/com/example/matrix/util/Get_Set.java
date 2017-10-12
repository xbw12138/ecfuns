package com.example.matrix.util;

public class Get_Set {
	public boolean httpjsonsuccess=false;
	public boolean isNetWork=true;
	public String LoginMSG="";
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
	public void Set_LoginMSG(String LoginMSG)
	{
		this.LoginMSG=LoginMSG;
	}
	public String Get_LoginMSG()
	{
		return LoginMSG;
	}
}
