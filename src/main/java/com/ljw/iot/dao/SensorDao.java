package com.ljw.iot.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;

@Repository
public class SensorDao implements ISensorDao{
	
	private static final Logger logger = LoggerFactory.getLogger(SensorDao.class);
	
	@Autowired
	private Firestore db;
	
	public List<Sensor> getSensorList() {
		return new ArrayList<Sensor>();
	}

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
	public boolean insertMeasure(Measure measure) throws InterruptedException, ExecutionException {
		
		DocumentReference docRef = db.collection("dustMeasure").document();	//신규 문서 생성
		
		// asynchronously write data
		ApiFuture<WriteResult> result = docRef.set(measure);
		
		logger.trace("docRef.getId() : " + docRef.getId());					//문서 아이디
		logger.trace("Update time : {}", result.get().getUpdateTime());		//서버 반영시간
		
		return result.isDone();
	}
}