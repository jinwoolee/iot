package com.ljw.iot.service;

import java.util.List;

import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;
import com.ljw.iot.model.SensorMeasure;
import com.ljw.iot.model.SensorVo;

public interface SensorService {

	List<Sensor> getSensorList();

	List<Measure> getMesureList();

	List<SensorMeasure> getSensorMeasure(SensorVo sensorVo);

	int insertMeasure(Measure measure);

	/**
	  * @FileName : SensorService.java
	  * @Project : iot
	  * @Date : 2017. 5. 10.
	  * @작성자 : jw
	  * @변경이력 :
	  * @param sensorVo
	  * @return
	  * @프로그램 설명 : 측정치 조회
	  */
	List<Measure> getMeasure(SensorVo sensorVo);

}