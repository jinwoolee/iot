package com.ljw.iot.dao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;


public interface ISensorDao {
	
	public List<Sensor> getSensorList();

	/** 
	 * Method   : insertMeasure
	 * 작성자 : jw
	 * 변경이력 : 
	 * @param measure
	 * @return 
	 * Method 설명 : 먼지 센싱 데이터 firebase 입력 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public boolean insertMeasure(Measure measure) throws InterruptedException, ExecutionException;
}