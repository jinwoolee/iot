package com.ljw.iot.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ljw.common.dao.CodeDao;
import com.ljw.iot.dao.SensorMapper;
import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;
import com.ljw.iot.model.SensorMeasure;
import com.ljw.iot.model.SensorVo;
import com.ljw.iot.service.SensorService;

@Service
public class SensorServiceImpl implements SensorService{
	Logger logger = LoggerFactory.getLogger(SensorServiceImpl.class);
	
	//@Autowired
	private SensorMapper sensorMapper;
	
	@Autowired
	private CodeDao codeDao;
	
	/**
	  * @FileName : SensorServiceImpl.java
	  * @Project : iot
	  * @Date : 2017. 1. 16.
	  * @작성자 : jw
	  * @변경이력 :
	  * @return
	  * @프로그램 설명 : 센서 목록 조회 
	  */
	@Override
	public List<Sensor> getSensorList() {
		logger.debug(codeDao.getCode().toString());
		return new ArrayList<Sensor>();
		//return sensorMapper.getSensorList();
	}

	/**
	  * @FileName : SensorServiceImpl.java
	  * @Project : iot
	  * @Date : 2017. 1. 16.
	  * @작성자 : jw
	  * @변경이력 :
	  * @return
	  * @프로그램 설명 : 측정치 조회 
	  */
	@Override
	public List<Measure> getMesureList() {
		//return sensorMapper.getMesureList();
		return new ArrayList<Measure>();
	}

	/**
	  * @FileName : SensorServiceImpl.java
	  * @Project : iot
	  * @Date : 2017. 1. 16.
	  * @작성자 : jw
	  * @변경이력 :
	  * @return
	  * @프로그램 설명 : 센서별 측정치 조회
	  */
	@Override
	public List<SensorMeasure> getSensorMeasure(SensorVo sensorVo) {
		//return sensorMapper.getSensorMeasure(sensorVo);
		return new ArrayList<SensorMeasure>();
	}

	/**
	  * @FileName : SensorServiceImpl.java
	  * @Project : iot
	  * @Date : 2017. 1. 16.
	  * @작성자 : jw
	  * @변경이력 :
	  * @param measure
	  * @return
	  * @프로그램 설명 : 측정치 입력 
	  */
	@Override
	@Transactional
	public int insertMeasure(Measure measure) {
		//return sensorMapper.insertMeasure(measure);
		return 0;
	}

	/**
	  * @FileName : SensorServiceImpl.java
	  * @Project : iot
	  * @Date : 2017. 5. 10.
	  * @작성자 : jw
	  * @변경이력 :
	  * @param sensorVo
	  * @return
	  * @프로그램 설명 : 측정치 조회 
	  */
	@Override
	public Map<String, List<Measure>> getMeasure(SensorVo sensorVo) {
//		Map<String, List<Measure>> resultMap = new HashMap<String, List<Measure>>();
//		if("monthly".equals(sensorVo.getMethod()))
//			resultMap.put("monthly", sensorMapper.getMonthlyMeasure(sensorVo));
//		else if("daily".equals(sensorVo.getMethod()))
//			resultMap.put("daily", sensorMapper.getDailyMeasure(sensorVo));
//		else if("timely".equals(sensorVo.getMethod()))
//			resultMap.put("timely", sensorMapper.getTimelyMeasure(sensorVo));
//		else if("5min".equals(sensorVo.getMethod()))
//			resultMap.put("5min", sensorMapper.get5MinMeasure(sensorVo));
//		else if("dashBoard".equals(sensorVo.getMethod())){
//			resultMap.put("timely", sensorMapper.getTimelyMeasure(sensorVo));
//			
//			sensorVo.setSt_dt(sensorVo.getSt_dt().substring(0, 6) + "01");
//			sensorVo.setEd_dt(sensorVo.getSt_dt().substring(0, 6) + "31");
//			resultMap.put("daily", sensorMapper.getDailyMeasure(sensorVo));
//			
//			sensorVo.setSt_dt(sensorVo.getSt_dt().substring(0, 4) + "0101");
//			sensorVo.setEd_dt(sensorVo.getSt_dt().substring(0, 4) + "1231");
//			resultMap.put("monthly", sensorMapper.getMonthlyMeasure(sensorVo));
//		}
//		
//		return resultMap;
		return new HashMap<String, List<Measure>>();
	}
}