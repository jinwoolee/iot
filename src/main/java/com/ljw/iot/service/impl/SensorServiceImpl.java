package com.ljw.iot.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ljw.common.dao.CodeDao;
import com.ljw.iot.dao.SensorMapper;
import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;
import com.ljw.iot.model.SensorMeasure;
import com.ljw.iot.service.SensorService;

@Service
public class SensorServiceImpl implements SensorService{
	Logger logger = LoggerFactory.getLogger(SensorServiceImpl.class);
	
	@Autowired
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
		return sensorMapper.getSensorList();
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
		return sensorMapper.getMesureList();
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
	public List<SensorMeasure> getSensorMeasure() {
		return sensorMapper.getSensorMeasure(null);
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
	public int insertMeasure(Measure measure) {
		return sensorMapper.insertMeasure(measure);
	}
}