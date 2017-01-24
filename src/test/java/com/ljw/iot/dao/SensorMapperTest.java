package com.ljw.iot.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ljw.iot.config.AppConfigTest;
import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;
import com.ljw.iot.model.SensorMeasure;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfigTest.class})
public class SensorMapperTest {
	Logger logger = LoggerFactory.getLogger(SensorMapperTest.class);
	
	@Autowired
	SensorMapper sensorMapper;
	
	@Test
	public void testGetSensor() {
		//given
		
		//when
		List<Sensor> sensorList = sensorMapper.getSensorList();
		
		//then
		for(Sensor sensor : sensorList)
			logger.debug(sensor.toString());
	}
	
	@Test
	public void testGetMesureList(){
		//given
		
		//when
		List<Measure> mesureList = sensorMapper.getMesureList();
		
		//then
		for(Measure mesure : mesureList)
			logger.debug(mesure.toString());
	}
	
	@Test
	public void testGetSensorMeasure(){
		//given
		
		//when
		List<SensorMeasure> sensorMeasureList = sensorMapper.getSensorMeasure();
		logger.debug("sensorMeasureList : " + sensorMeasureList.size());
		for(SensorMeasure sensorMeasure : sensorMeasureList)
			logger.debug(sensorMeasure.toString());
		
		//then
	}
	
	@Test
	public void testInsertMeasure(){
		//given
		Measure measure = new Measure("1", 1262.83d);
		
		//when
		int insertCnt = sensorMapper.insertMeasure(measure);
		
		//then
		assertEquals(1, insertCnt);
	}
}