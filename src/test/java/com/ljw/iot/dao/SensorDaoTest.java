package com.ljw.iot.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={AppConfigTest.class, IotConfigTest.class})
public class SensorDaoTest {
	Logger logger = LoggerFactory.getLogger(SensorDaoTest.class);
	
	@Autowired
	SensorDao sensorDao;
	
	@Test
	public void testGetSensor() {
		assertEquals(1, 1);
		//given
		
		//when
		List<Sensor> sensorList = sensorDao.getSensorList();

		//then
		for(Sensor sensor : sensorList)
			logger.debug(sensor.toString());
	}
	
	@Test
	public void insertMeasureTest() throws InterruptedException, ExecutionException {
		/***given***/
		
		Measure measure = new Measure();
		measure.setAqi(13.4);
		measure.setMeasure(2459.7);
		measure.setSensor_id("1");
		measure.setReg_dt(new Date());

		/***when***/
		boolean isDone = sensorDao.insertMeasure(measure);
		
		/***then***/
		assertTrue(isDone);		//정상 완료 여부	
	}
}