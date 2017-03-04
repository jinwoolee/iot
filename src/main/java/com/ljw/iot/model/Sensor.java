package com.ljw.iot.model;

import java.sql.Date;

public class Sensor {
	private	int		sensor_id;		//센서ID
	private	String	sensor_type;		//센서구분(DUST : 먼지센서)
	private	String	sensor_name;		//센서이름	
	private	String	explan;			//센서설명
	private	Date		reg_dt;			//등록일시
	
	public Sensor(){
	}
	
	public int getSensor_id() {
		return sensor_id;
	}

	public void setSensor_id(int sensor_id) {
		this.sensor_id = sensor_id;
	}

	public String getSensor_type() {
		return sensor_type;
	}

	public void setSensor_type(String sensor_type) {
		this.sensor_type = sensor_type;
	}

	public String getSensor_name() {
		return sensor_name;
	}

	public void setSensor_name(String sensor_name) {
		this.sensor_name = sensor_name;
	}

	public String getExplan() {
		return explan;
	}

	public void setExplan(String explan) {
		this.explan = explan;
	}

	public Date getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(Date reg_dt) {
		this.reg_dt = reg_dt;
	}

	@Override
	public String toString() {
		return "Sensor [sensor_id=" + sensor_id + ", sensor_type=" + sensor_type + ", sensor_name=" + sensor_name
				+ ", explan=" + explan + ", reg_dt=" + reg_dt + "]";
	}

}