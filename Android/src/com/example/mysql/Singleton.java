package com.example.mysql;

import java.util.ArrayList;
import java.util.List;

import com.example.matrix.util.ListPlModel;
import com.example.matrix.util.ListQrModel;
import com.example.matrix.util.ListTalkModel;

public class Singleton {

	private Singleton() {
	}
	private static final Singleton single = new Singleton();
	private List ListDataLight = new ArrayList();
	private List ListDataBrand = new ArrayList();
	private List ListDataCarlevel = new ArrayList();
	private List ListDataColometer = new ArrayList();
	private List ListDataEnginenum = new ArrayList();
	private List ListDataEnginestate = new ArrayList();
	private List ListDataLogo = new ArrayList();
	private List ListDataOilcount = new ArrayList();
	private List ListDataPlatenum = new ArrayList();
	private List ListDataShiftstate = new ArrayList();
	private List ListDataKeepID = new ArrayList();
	private List ListDataKeepSendTime = new ArrayList();
	private List ListDataGasNum = new ArrayList();
	private List ListDataGasType = new ArrayList();
	private List ListDataGasStation = new ArrayList();
	private List ListDataOrderTime = new ArrayList();
	private List ListDataFinish = new ArrayList();
	private List ListDataOrderID = new ArrayList();
	private List ListDataOrderSendTime = new ArrayList();
	
	private List<ListTalkModel>listTalk=new ArrayList<ListTalkModel>(); 
	private List<ListQrModel>listQr=new ArrayList<ListQrModel>(); 
	private List<ListPlModel>listPl=new ArrayList<ListPlModel>(); 
	//user
	private String Name=null;
	private String Age=null;
	private String Sex=null;
	private String Add=null;
	private String Sign=null;
	private String Time=null;
	private String Head=null;
	private String BG=null;
	private String MSG=null;
	private String Phone=null;
	//order
	private String GasNum=null;
	private String GasType=null;
	private String GasStation=null;
	private String OrderTime=null;
	private String Finish=null;
	private String OrderID=null;
	private String OrderSendTime=null;
	//keep
	private String Brand=null;
	private String Carlevel=null;
	private String Colometer=null;
	private String Enginenum=null;
	private String Enginestate=null;
	private String Light=null;
	private String Logo=null;
	private String Oilcount=null;
	private String Platenum=null;
	private String Shiftstate=null;
	private String KeepID=null;
	private String KeepSendTime=null;
	// ��̬��������
	public static Singleton getInstance() {
		return single;
	}
	public void setName(String ID){
		this.Name=ID;
	}
	public String getName(){
		return Name;
	}
	public void setAge(String ID){
		this.Age=ID;
	}
	public String getAge(){
		return Age;
	}
	public void setSex(String ID){
		this.Sex=ID;
	}
	public String getSex(){
		return Sex;
	}
	public void setAdd(String ID){
		this.Add=ID;
	}
	public String getAdd(){
		return Add;
	}
	public void setSign(String ID){
		this.Sign=ID;
	}
	public String getSign(){
		return Sign;
	}
	public void setTime(String ID){
		this.Time=ID;
	}
	public String getTime(){
		return Time;
	}
	public void setHead(String ID){
		this.Head=ID;
	}
	public String getHead(){
		return Head;
	}
	public void setBG(String ID){
		this.BG=ID;
	}
	public String getBG(){
		return BG;
	}
	public void setMSG(String ID){
		this.MSG=ID;
	}
	public String getMSG(){
		return MSG;
	}
	public void setGasNum(String ID){
		this.GasNum=ID;
	}
	public String getGasNum(){
		return GasNum;
	}
	public void setGasType(String ID){
		this.GasType=ID;
	}
	public String getGasType(){
		return GasType;
	}
	public void setGasStation(String ID){
		this.GasStation=ID;
	}
	public String getGasStation(){
		return GasStation;
	}
	public void setOrderTime(String ID){
		this.OrderTime=ID;
	}
	public String getOrderTime(){
		return OrderTime;
	}
	public void setFinish(String ID){
		this.Finish=ID;
	}
	public String getFinish(){
		return Finish;
	}
	public void setOrderID(String ID){
		this.OrderID=ID;
	}
	public String getOrderID(){
		return OrderID;
	}
	public void setKeepID(String ID){
		this.KeepID=ID;
	}
	public String getKeepID(){
		return KeepID;
	}
	public void setBrand(String ID){
		this.Brand=ID;
	}
	public String getBrand(){
		return Brand;
	}
	public void setCarlevel(String ID){
		this.Carlevel=ID;
	}
	public String getCarlevel(){
		return Carlevel;
	}
	public void setColometer(String ID){
		this.Colometer=ID;
	}
	public String getColometer(){
		return Colometer;
	}
	public void setEnginenum(String ID){
		this.Enginenum=ID;
	}
	public String getEnginenum(){
		return Enginenum;
	}
	public void setEnginestate(String ID){
		this.Enginestate=ID;
	}
	public String getEnginestate(){
		return Enginestate;
	}
	public void setLight(String ID){
		this.Light=ID;
	}
	public String getLight(){
		return Light;
	}
	public void setLogo(String ID){
		this.Logo=ID;
	}
	public String getLogo(){
		return Logo;
	}
	public void setPhone(String ID){
		this.Phone=ID;
	}
	public String getPhone(){
		return Phone;
	}
	public void setOilcount(String ID){
		this.Oilcount=ID;
	}
	public String getOilcount(){
		return Oilcount;
	}
	public void setPlatenum(String ID){
		this.Platenum=ID;
	}
	public String getPlatenum(){
		return Platenum;
	}
	public void setShiftstate(String ID){
		this.Shiftstate=ID;
	}
	public String getShiftstate(){
		return Shiftstate;
	}
	public void setOrderSendTime(String ID){
		this.OrderSendTime=ID;
	}
	public String getOrderSendTime(){
		return OrderSendTime;
	}
	public void setKeepSendTime(String ID){
		this.KeepSendTime=ID;
	}
	public String getKeepSendTime(){
		return KeepSendTime;
	}
	
	public void setListDataLight(List ID){
		this.ListDataLight=ID;
	}
	public List getListDataLight(){
		return ListDataLight;
	}
	public void setListDataBrand(List ID){
		this.ListDataBrand=ID;
	}
	public List getListDataBrand(){
		return ListDataBrand;
	}
	public void setListDataCarlevel(List ID){
		this.ListDataCarlevel=ID;
	}
	public List getListDataCarlevel(){
		return ListDataCarlevel;
	}
	public void setListDataColometer(List ID){
		this.ListDataColometer=ID;
	}
	public List getListDataColometer(){
		return ListDataColometer;
	}
	public void setListDataEnginenum(List ID){
		this.ListDataEnginenum=ID;
	}
	public List getListDataEnginenum(){
		return ListDataEnginenum;
	}
	public void setListDataEnginestate(List ID){
		this.ListDataEnginestate=ID;
	}
	public List getListDataEnginestate(){
		return ListDataEnginestate;
	}
	public void setListDataLogo(List ID){
		this.ListDataLogo=ID;
	}
	public List getListDataLogo(){
		return ListDataLogo;
	}
	public void setListDataOilcount(List ID){
		this.ListDataOilcount=ID;
	}
	public List getListDataOilcount(){
		return ListDataOilcount;
	}
	public void setListDataPlatenum(List ID){
		this.ListDataPlatenum=ID;
	}
	public List getListDataPlatenum(){
		return ListDataPlatenum;
	}
	public void setListDataShiftstate(List ID){
		this.ListDataShiftstate=ID;
	}
	public List getListDataShiftstate(){
		return ListDataShiftstate;
	}
	
	public void setListDataKeepID(List ID){
		this.ListDataKeepID=ID;
	}
	public List getListDataKeepID(){
		return ListDataKeepID;
	}public void setListDataKeepSendTime(List ID){
		this.ListDataKeepSendTime=ID;
	}
	public List getListDataKeepSendTime(){
		return ListDataKeepSendTime;
	}
	
	public void setListDataGasNum(List ID){
		this.ListDataGasNum=ID;
	}
	public List getListDataGasNum(){
		return ListDataGasNum;
	}
	public void setListDataGasType(List ID){
		this.ListDataGasType=ID;
	}
	public List getListDataGasType(){
		return ListDataGasType;
	}
	public void setListDataGasStation(List ID){
		this.ListDataGasStation=ID;
	}
	public List getListDataGasStation(){
		return ListDataGasStation;
	}
	public void setListDataOrderTime(List ID){
		this.ListDataOrderTime=ID;
	}
	public List getListDataOrderTime(){
		return ListDataOrderTime;
	}
	public void setListDataFinish(List ID){
		this.ListDataFinish=ID;
	}
	public List getListDataFinish(){
		return ListDataFinish;
	}
	public void setListDataOrderID(List ID){
		this.ListDataOrderID=ID;
	}
	public List getListDataOrderID(){
		return ListDataOrderID;
	}
	public void setListDataOrderSendTime(List ID){
		this.ListDataOrderSendTime=ID;
	}
	public List getListDataOrderSendTime(){
		return ListDataOrderSendTime;
	}
	
	public void setlistTalk(List<ListTalkModel> ID){
		this.listTalk=ID;
	}
	public List<ListTalkModel> getlistTalk(){
		return listTalk;
	}
	public void setlistQr(List<ListQrModel> ID){
		this.listQr=ID;
	}
	public List<ListQrModel> getlistQr(){
		return listQr;
	}
	public void setlistPl(List<ListPlModel> ID){
		this.listPl=ID;
	}
	public List<ListPlModel> getlistPl(){
		return listPl;
	}
	/*private static Singleton instance = null;
	private static Singleton singleton = null;

	public static Singleton getInstance() {
		if (instance == null) {
			synchronized (Singleton.class) {
				if (singleton == null) {
					singleton = new Singleton();
				}
			}
		}
		return instance;
	}
	*/

}
