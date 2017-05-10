package com.ljw.iot.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ljw.common.config.AppConfigTest;
import com.ljw.iot.config.IotConfigTest;
import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;
import com.ljw.iot.model.SensorMeasure;
import com.ljw.iot.model.SensorVo;
import com.ljw.iot.service.SensorService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfigTest.class, IotConfigTest.class})
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
	
	//월별 측정치 조회
	@Test
	public void testGetMonthlyMeasure(){
		//given
		SensorVo sensorVo = new SensorVo(1);
		sensorVo.setSt_dt("20170401");
		sensorVo.setEd_dt("20170430");
		
		//when
		List<Measure> measureList = sensorService.getMeasure(sensorVo);
		for(Measure measure : measureList)
			logger.debug(measure.toString());
		
		//then
		assertTrue(measureList.size() > 0);
	}
	
	//일별 측정치 조회
	@Test
	public void testGetDailyMeasure(){
		//given
		SensorVo sensorVo = new SensorVo(1);
		sensorVo.setMethod("daily");
		
		//when
		List<Measure> measureList = sensorService.getMeasure(sensorVo);
		for(Measure measure : measureList)
			logger.debug(measure.toString());
		
		//then
		assertTrue(measureList.size() > 0);
	}
	
	//시간별 측정치 조회
	@Test
	public void testGetTimelyMeasure(){
		//given
		SensorVo sensorVo = new SensorVo(1);
		sensorVo.setMethod("timely");
		
		//when
		List<Measure> measureList = sensorService.getMeasure(sensorVo);
		for(Measure measure : measureList)
			logger.debug(measure.toString());
		
		//then
		assertTrue(measureList.size() > 0);
	}
	
	//5분단위 측정치 조회
	@Test 
	public void testGet5MinMeasure(){
		//given
		SensorVo sensorVo = new SensorVo(1);
		sensorVo.setMethod("5min");
		
		//when
		List<Measure> measureList = sensorService.getMeasure(sensorVo);
		for(Measure measure : measureList)
			logger.debug(measure.toString());
		
		//then
		assertTrue(measureList.size() > 0);
	}
}