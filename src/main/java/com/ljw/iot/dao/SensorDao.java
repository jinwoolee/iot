package com.ljw.iot.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
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
		
		SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf_H = new SimpleDateFormat("H");
		SimpleDateFormat sdf_m = new SimpleDateFormat("m");
		
		Date dt = new Date();
		
		Map<String, Map<String, Measure>> map = new HashMap<String, Map<String, Measure>>();
		Map<String, Measure> data = new HashMap<String, Measure>();
		
		DocumentReference yyyyMMddDocRef = db.collection("dustMeasure").document(sdf_yyyyMMdd.format(dt));	//신규 문서 생성
		
		//DocumentReference hDocRef = yyyyMMddDocRef.collection("hour").document(sdf_H.format(dt));
		//DocumentReference mDocRef = hDocRef.collection("min").document(sdf_m.format(dt));
		
		data.put(sdf_m.format(dt), measure);
		map.put(sdf_H.format(dt), data);
		
		
		// asynchronously write data
		ApiFuture<WriteResult> result = yyyyMMddDocRef.set(measure);
		
		logger.trace("docRef.getId() : " + yyyyMMddDocRef.getId());					//문서 아이디
		logger.trace("Update time : {}", result.get().getUpdateTime());		//서버 반영시간
		
		return result.isDone();
	}
//	public boolean insertMeasure(Measure measure) throws InterruptedException, ExecutionException {
//		
//		SimpleDateFormat sdf_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
//		SimpleDateFormat sdf_H = new SimpleDateFormat("H");
//		SimpleDateFormat sdf_m = new SimpleDateFormat("m");
//		
//		Date dt = new Date();
//		
//		DocumentReference yyyyMMddDocRef = db.collection("dustMeasure").document(sdf_yyyyMMdd.format(dt));	//신규 문서 생성
//		DocumentReference hDocRef = yyyyMMddDocRef.collection("hour").document(sdf_H.format(dt));
//		DocumentReference mDocRef = hDocRef.collection("min").document(sdf_m.format(dt));
//		
//		
//		// asynchronously write data
//		ApiFuture<WriteResult> result = mDocRef.set(measure);
//		
//		logger.trace("docRef.getId() : " + yyyyMMddDocRef.getId());					//문서 아이디
//		logger.trace("Update time : {}", result.get().getUpdateTime());		//서버 반영시간
//		
//		return result.isDone();
//	}

	
	/**
	 * Method : getMeasureDaily
	 * 작성자 : SEM
	 * 변경이력 :
	 * @param day
	 * @return
	 * Method 설명 : 일일 센싱 데이터 조회(진행중)
	 */
	public List<Measure> getMeasureDaily(String day) {
		
		//dustMeasure - 
		ApiFuture<QuerySnapshot> query = db.collection("dustMeasure").get();
		try {
			QuerySnapshot querySnapshot = query.get();
			
			//querySnapshot.get
			for(QueryDocumentSnapshot queryDocumentSnapshot : querySnapshot.getDocuments())
				logger.debug("data : " + queryDocumentSnapshot.getData());//logger.debug("); queryDocumentSnapshot
			
		} catch (InterruptedException | ExecutionException e) {
		
			e.printStackTrace();
		}
		return null;
	}
}