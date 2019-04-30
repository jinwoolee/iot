package com.ljw.iot.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;
import com.ljw.iot.model.SensorMeasure;
import com.ljw.iot.model.SensorVo;

public interface SensorService {

	List<Sensor> getSensorList();

	List<Measure> getMesureList();

	List<SensorMeasure> getSensorMeasure(SensorVo sensorVo);

	/** 
	 * Method   : insertMeasure
	 * 작성자 : jw
	 * 변경이력 : 
	 * @param measure
	 * @return 
	 * Method 설명 : 먼지 센싱 데이터 firebase 입력 
	 */
	boolean insertMeasure(Measure measure) throws InterruptedException, ExecutionException;

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
	Map<String, List<Measure>> getMeasure(SensorVo sensorVo);

}