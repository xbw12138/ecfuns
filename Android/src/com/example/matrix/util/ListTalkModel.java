package com.example.matrix.util;

import java.util.ArrayList;

public class ListTalkModel {
		private String ID=null;
		private String Content=null;
		private String Time=null;
		private String Url=null;
		private ArrayList<String> Urls=null;
		private String Name=null;
		private String Phone=null;
		private String Head=null;
		private String Pnum=null;
		private String Zan=null;
		private String Phonety=null;
		public void setContent(String ID){
			this.Content=ID;
		}
		public String getContent(){
			return Content;
		}
		public void setPnum(String ID){
			this.Pnum=ID;
		}
		public String getPnum(){
			return Pnum;
		}
		public void setZan(String ID){
			this.Zan=ID;
		}
		public String getZan(){
			return Zan;
		}
		public void setID(String ID){
			this.ID=ID;
		}
		public String getID(){
			return ID;
		}
		public void setTime(String ID){
			this.Time=ID;
		}
		public String getTime(){
			return Time;
		}
		public void setUrl(String ID){
			this.Url=ID;
		}
		public String getUrl(){
			return Url;
		}
		public void setUrls(ArrayList<String> ID){
			this.Urls=ID;
		}
		public ArrayList<String> getUrls(){
			return Urls;
		}
		public void setName(String ID){
			this.Name=ID;
		}
		public String getName(){
			return Name;
		}
		public void setPhone(String ID){
			this.Phone=ID;
		}
		public String getPhone(){
			return Phone;
		}
		public void setHead(String ID){
			this.Head=ID;
		}
		public String getHead(){
			return Head;
		}
		public void setPhonety(String ID){
			this.Phonety=ID;
		}
		public String getPhonety(){
			return Phonety;
		}
}
