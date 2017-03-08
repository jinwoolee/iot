package com.ljw.iot.dao;

 import java.util.List;

import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;
import com.ljw.iot.model.SensorMeasure;
import com.ljw.iot.model.SensorVo;

public interface SensorMapper {

	List<Sensor> getSensorList();
	
	List<Measure> getMesureList();

	List<SensorMeasure> getSensorMeasure(SensorVo sensorVo);

	int insertMeasure(Measure measure);
	
}