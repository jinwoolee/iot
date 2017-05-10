package com.ljw.iot.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SensorVo {
	private	int		sensor_id;			
	private	String	st_dt;
	private	String	ed_dt;
	private	String	st_tm;
	private	String	ed_tm;
	
	private	int		page;
	private	int		pageSize;
	
	private	String	method;
	
	public SensorVo(){
	}
	public SensorVo(int sensor_id){
		this.sensor_id	=	sensor_id;
	}
	public int getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}
	public String getSt_dt() {
		
		if(st_dt == null){
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, 1);
			System.out.println(c.toString());
			return (new SimpleDateFormat("yyyyMMdd").format(c.getTime()));
		}
		else
			return st_dt;
	}
	public void setSt_dt(String st_dt) {
		this.st_dt = st_dt;
	}
	public String getEd_dt() {
		if(ed_dt == null){
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, c.getMaximum(Calendar.DAY_OF_MONTH));
			return (new SimpleDateFormat("yyyyMMdd").format(c.getTime()));
		}
		else
			return ed_dt;
	}
	public void setEd_dt(String ed_dt) {
		this.ed_dt = ed_dt;
	}
	public String getSt_tm() {
		return st_tm;
	}
	public void setSt_tm(String st_tm) {
		this.st_tm = st_tm;
	}
	public String getEd_tm() {
		return ed_tm;
	}
	public void setEd_tm(String ed_tm) {
		this.ed_tm = ed_tm;
	}
	public String getSt_dtm(){
		return getSt_dt() + getSt_tm();
	}
	public String getEd_dtm(){
		if(getEd_dt() == null)
			setEd_dt(getSt_dt());
		if(getEd_tm() == null)
			setEd_tm("2400");
		return getEd_dt() + getEd_tm();
	}
	
	public int getPage() {
		if(page == 0)
			page = 1;
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		if(pageSize == 0)
			pageSize = 10;
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageStart(){
		return (getPage()-1)*getPageSize();
	}
	public String getMethod() {
		return method == null ? "monthly" : method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	@Override
	public String toString() {
		return "SensorVo [sensor_id=" + sensor_id + ", st_dt=" + st_dt + ", ed_dt=" + ed_dt + ", st_tm=" + st_tm
				+ ", ed_tm=" + ed_tm + ", page=" + page + ", pageSize=" + pageSize + "]";
	}
}
