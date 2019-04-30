package com.ljw.iot.model;

import java.util.Date;

public class Measure {
	private String sensor_id;	// sensor id
	private Double measure; 	// 측정값
	private Double aqi;			// aqi 지수
	private Date reg_dt;		// 측정값 등록일
	private String dt;			// 조회일정

	public Measure(String sensor_id, Double measure) {
		this.sensor_id = sensor_id;
		this.measure = measure;
	}

	public Measure() {
	}

	public String getSensor_id() {
		return sensor_id;
	}

	public void setSensor_id(String sensor_id) {
		this.sensor_id = sensor_id;
	}

	public Double getMeasure() {
		return measure;
	}

	public void setMeasure(Double measure) {
		this.measure = measure;
	}

	public Date getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(Date reg_dt) {
		this.reg_dt = reg_dt;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public Double getAqi() {
		return aqi;
	}

	public void setAqi(Double aqi) {
		this.aqi = aqi;
	}

	@Override
	public String toString() {
		return "Measure [sensor_id=" + sensor_id + ", measure=" + measure + ", aqi=" + aqi + ", reg_dt=" + reg_dt + ", dt=" + dt + "]";
	}
}