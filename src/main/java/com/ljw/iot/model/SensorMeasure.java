package com.ljw.iot.model;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class SensorMeasure {
	private	Sensor sensor;
	private	List<Measure> measureList;
	
	public SensorMeasure(){
	}

	@Override
	public String toString() {
		return "SensorMeasure [sensor=" + sensor + ", measureList=" + measureList + "]";
	}
	
	/*private	int		sensor_id;		//센서ID
	private	String	sensor_type;		//센서구분(DUST : 먼지센서)
	private	String	sensor_name;		//센서이름	
	private	String	desc;			//센서설명
	
	private	String	measure_id;		//측정id
	private	String	measure;			//측정값
	private	String	reg_dt;			//측정값 등록일
	
	public SensorMeasure(){
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMeasure_id() {
		return measure_id;
	}

	public void setMeasure_id(String measure_id) {
		this.measure_id = measure_id;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}

	@Override
	public String toString() {
		return "SensorMeasure [sensor_id=" + sensor_id + ", sensor_type=" + sensor_type + ", sensor_name=" + sensor_name
				+ ", desc=" + desc + ", measure_id=" + measure_id + ", measure=" + measure + ", reg_dt=" + reg_dt + "]";
	}*/
}