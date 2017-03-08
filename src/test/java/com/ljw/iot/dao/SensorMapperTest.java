package com.ljw.iot.dao;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfigTest.class})
public class SensorMapperTest {
	Logger logger = LoggerFactory.getLogger(SensorMapperTest.class);
	//DbMaintainer dbMaintainer; 
	
	@Autowired
	SensorMapper sensorMapper;
	
	@Before
	public void setup(){
//		 try {  
//	        // 프로퍼티 객체 생성
//	        Properties props = new Properties();
//	         
//	        // 프로퍼티 파일 스트림에 담기
//	        FileInputStream fis = new FileInputStream("src/com/ljw/iot/config/unitils.properties");
//	         
//	        // 프로퍼티 파일 로딩
//	        props.load(new java.io.BufferedInputStream(fis));
//			MainFactory mainFactory = new MainFactory(props);
//			dbMaintainer = mainFactory.createDbMaintainer();
//			dbMaintainer.updateDatabase(false);
//		 }catch(Exception e){e.printStackTrace();}
	}
	
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
		SensorVo vo = new SensorVo(1);
		vo.setSt_dt("20170307");
		vo.setSt_tm("0000");
		
		//when
		List<SensorMeasure> sensorMeasureList = sensorMapper.getSensorMeasure(vo);
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