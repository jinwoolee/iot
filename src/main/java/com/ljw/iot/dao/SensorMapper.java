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

	/**
	  * @FileName : SensorMapper.java
	  * @Project : iot
	  * @Date : 2017. 4. 26.
	  * @작성자 : jw
	  * @변경이력 :
	  * @param sensorVo
	  * @return
	  * @프로그램 설명 : 센서,월별 측정량
	  */
	List<Measure> getMonthlyMeasure(SensorVo sensorVo);

	/**
	  * @FileName : SensorMapper.java
	  * @Project : iot
	  * @Date : 2017. 4. 27.
	  * @작성자 : jw
	  * @변경이력 :
	  * @param sensorVo
	  * @return
	  * @프로그램 설명 : 센서,일별 측정량
	  */
	List<Measure> getDailyMeasure(SensorVo sensorVo);

	/**
	  * @FileName : SensorMapper.java
	  * @Project : iot
	  * @Date : 2017. 4. 27.
	  * @작성자 : jw
	  * @변경이력 :
	  * @param sensorVo
	  * @return
	  * @프로그램 설명 : 센서,시간별 측정량
	  */
	List<Measure> getTimelyMeasure(SensorVo sensorVo);

	/**
	  * @FileName : SensorMapper.java
	  * @Project : iot
	  * @Date : 2017. 5. 2.
	  * @작성자 : jw
	  * @변경이력 :
	  * @param sensorVo
	  * @return
	  * @프로그램 설명 : 센서, 5분단위별 측정량
	  */
	List<Measure> get5MinMeasure(SensorVo sensorVo);
	
}