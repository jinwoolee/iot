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
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldPath;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
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
		
		DocumentReference hDocRef = yyyyMMddDocRef.collection(sdf_H.format(dt)).document(sdf_m.format(dt));
		
		// asynchronously write data
		ApiFuture<WriteResult> result = hDocRef.set(measure);
		
		logger.trace("docRef.getId() : " + yyyyMMddDocRef.getId());					//문서 아이디
		logger.trace("Update time : {}", result.get().getUpdateTime());		//서버 반영시간
		
		return result.isDone();
	}
	
	/**
	 * Method : getMeasureDaily
	 * 작성자 : SEM
	 * 변경이력 :
	 * @param day
	 * @return
	 * Method 설명 : 일일 센싱 데이터 조회(진행중)
	 */
	public List<Measure> getMeasureDaily(String day) {
		
		//dustMeaure collection에서 파라미터에 해당하는 document를 가져온다
		//일자 document는 시간단위로 collection을 관리하고
		//시간 컬렉션에는 분단위로 document를 저장하고 있다
		
		DocumentReference doc = db.collection("dustMeasure").document(day);
			
		//결과값
		List<Measure> measureList = new ArrayList<Measure>();
		
		//일자 document에 속한 시간단위 collection
		for(CollectionReference colRef : doc.getCollections()){
			ApiFuture<QuerySnapshot> query = colRef.get();
			try {
				QuerySnapshot snapshot = query.get();
				List<QueryDocumentSnapshot> documents = snapshot.getDocuments();
				
				//시간 collection에 속한 분단위 document
				for(QueryDocumentSnapshot queryDocumentSnapShot : documents){
					logger.debug(" dt-{} h-{} : {}", queryDocumentSnapShot.getId(), queryDocumentSnapShot.getData());
					measureList.add(queryDocumentSnapShot.toObject(Measure.class));
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return measureList;
	}

	@Override
	public List<Measure> getMeasureMonthly(String yyyyMM) {
		
		List<Measure> resultList = new ArrayList<Measure>();
		try {
			CollectionReference colRef = db.collection("dustMeasure");
			
			logger.debug("FieldPath.documentId() : {} " ,FieldPath.documentId());
			//Query query = colRef.whereLessThanOrEqualTo(FieldPath.documentId(), yyyyMM+"31");
			String[] days = new String[]{yyyyMM + "03", yyyyMM + "07"};
			Query query = colRef.whereArrayContains(FieldPath.documentId(), days);
			
			ApiFuture<QuerySnapshot> future= query.get();
			QuerySnapshot snap = future.get();
			List<QueryDocumentSnapshot> list = snap.getDocuments();
			logger.debug("list.size() : {}", list.size());
			
			for(QueryDocumentSnapshot querySnap : list){
				logger.debug("querySnap.getId() : {} ", querySnap.getId());
				resultList.add(querySnap.toObject(Measure.class));
			}
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		return resultList;
	}
}