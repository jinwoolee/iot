package com.ljw.iot.model;

public class Measure {
	private	String	measure_id;		//측정id
	private	String	sensor_id;		//sensor id
	private	Double	measure;			//측정값
	private	String	reg_dt;			//측정값 등록일
	
	public Measure(String sensor_id, Double measure){
		this.sensor_id	=	sensor_id;
		this.measure		=	measure;
	}
	public Measure(){		
	}
	public String getMeasure_id() {
		return measure_id;
	}
	public void setMeasure_id(String measure_id) {
		this.measure_id = measure_id;
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
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}
	@Override
	public String toString() {
		return "Measure [measure_id=" + measure_id + ", sensor_id=" + sensor_id + ", measure=" + measure + ", reg_dt="
				+ reg_dt + "]";
	}
}