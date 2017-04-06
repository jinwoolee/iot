package com.ljw.iot.service.impl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ljw.common.config.AppConfigTest;
import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;
import com.ljw.iot.model.SensorMeasure;
import com.ljw.iot.model.SensorVo;
import com.ljw.iot.service.SensorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfigTest.class})
public class SensorServiceImplTest {
	Logger logger = LoggerFactory.getLogger(SensorServiceImplTest.class);
	
	@Autowired
	private SensorService sensorService;
	
	//센서 목록 조회 테스트
	@Test
	public void testGetSensor() {
		//given
		
		//when
		List<Sensor> sensorList = sensorService.getSensorList();
		
		//then
		for(Sensor sensor : sensorList)
			logger.debug(sensor.toString());
		
		assertEquals(1, sensorList.size());
	}
	
	//측정치 조회 테스트
	@Test
	public void testGetMesureList(){
		//given
		
		//when
		List<Measure> mesureList = sensorService.getMesureList();
		
		//then
		for(Measure mesure : mesureList)
			logger.debug(mesure.toString());
	}
	
	//센서별 측정치 조회 테스트
	@Test
	public void testGetSensorMeasure(){
		//given
		SensorVo vo = new SensorVo(1);
		vo.setSt_dt("20170307");
		vo.setSt_tm("0000");
		
		//when
		List<SensorMeasure> sensorMeasureList = sensorService.getSensorMeasure(vo);
		logger.info("sensorMeasureList {}: ", sensorMeasureList.size());
		for(SensorMeasure sensorMeasure : sensorMeasureList)
			logger.info("{}", sensorMeasure.toString());
		
		//then
	}
	
	//측정치 입력 테스트
	@Test
	public void testInsertMeasure(){
		//given
		Measure measure = new Measure("1", 1262.83d);
		
		//when
		int insertCnt = sensorService.insertMeasure(measure);
		
		//then
		assertEquals(1, insertCnt);
	}
}