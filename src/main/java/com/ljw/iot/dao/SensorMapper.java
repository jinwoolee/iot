package com.ljw.iot.dao;

 import java.util.List;

import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;
import com.ljw.iot.model.SensorMeasure;

public interface SensorMapper {

	List<Sensor> getSensorList();
	
	List<Measure> getMesureList();

	List<SensorMeasure> getSensorMeasure();

	int insertMeasure(Measure measure);
	
}