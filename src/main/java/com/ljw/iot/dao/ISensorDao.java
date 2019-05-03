package com.ljw.iot.dao;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;


/**
 * ISensorDao.java
 *
 * @author SEM
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 * 수정자 수정내용
 * ------ ------------------------
 * SEM 최초 생성
 *
 * </pre>
 */
public interface ISensorDao {
	
	List<Sensor> getSensorList();

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
	boolean insertMeasure(Measure measure) throws InterruptedException, ExecutionException;
	
	List<Measure> getMeasureDaily(String day);
}