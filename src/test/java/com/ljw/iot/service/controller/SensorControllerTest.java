package com.ljw.iot.service.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ModelAndView;

import com.ljw.common.config.AppConfig;
import com.ljw.iot.config.IotConfigTest;
import com.ljw.iot.controller.SensorController;
import com.ljw.iot.model.Measure;
import com.ljw.iot.model.SensorVo;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfig.class, IotConfigTest.class})
public class SensorControllerTest {
	Logger logger = LoggerFactory.getLogger(SensorControllerTest.class);

	private MockMvc mockMvc;
	
	@Autowired
	private SensorController sensorController;
	
	@Before
	public void init() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		mockMvc = MockMvcBuilders.standaloneSetup(sensorController).addFilter(filter).build();
	}
	
	//센서 리스트 조회 테스트(json)
	@Test
	public void testSensors() throws Exception {
		MockHttpServletRequestBuilder requestBuilder 
			= MockMvcRequestBuilders
				.get("/sensor/sensors")
				.accept(MediaType.APPLICATION_JSON);
				//.accept(MediaType.valueOf("text/plain;charset=UTF-8"));
		this.mockMvc.perform(requestBuilder).andDo(print())
				.andExpect(status().isOk());
	}
	
	//측정치 입력 테스트
	@Test
	public void testInsertMeasure() throws Exception{
		//given
		//Measure measure = new Measure("1", 1262.83d);
		
		//when
		MockHttpServletRequestBuilder requestBuilder 
						= MockMvcRequestBuilders
							.post("/sensor/measure")
							.accept(MediaType.APPLICATION_JSON)
							.param("sensor_id", "1")
							.param("measure", "1234");
		//then
		this.mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk());
	}
	
	//측정치 조회화면   
	@Test
	public void testGetMeasureView(){
		//given
		SensorVo sensorVo = new SensorVo(1);
		sensorVo.setSt_dt("20170401");
		sensorVo.setEd_dt("20170430");
		
		//when
		ModelAndView mav = sensorController.getMeasureView(sensorVo);
		List<Measure> measureList = (List<Measure>)mav.getModelMap().get("measureList");
		for(Measure measure : measureList)
			logger.info(measure.toString());
		
		//then
		assertEquals("sensor/measureView", mav.getViewName());
		assertTrue(measureList.size() > 0);
	}
	
	//월별 측정치 조회
	@Test
	public void testGetMonthlyMeasure() throws Exception{
		//given
		
		//when
		MockHttpServletRequestBuilder requestBuilder 
				= MockMvcRequestBuilders
					.get("/sensor/getMeasure")
					.accept(MediaType.APPLICATION_JSON)
					.param("sensor_id", "1")
					.param("st_dt", "20170401")
					.param("ed_dt", "20170430")
					.param("method", "monthly");
		//then
		this.mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk());
	}
	
	//일별 측정치 조회
	@Test
	public void testGetDailyMeasure() throws Exception{
		//given
		
		//when
		MockHttpServletRequestBuilder requestBuilder 
				= MockMvcRequestBuilders
					.get("/sensor/getMeasure")
					.accept(MediaType.APPLICATION_JSON)
					.param("sensor_id", "1")
					.param("st_dt", "20170401")
					.param("ed_dt", "20170430")
					.param("method", "daily");
		//then
		this.mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk());
	}
	
	//시간별 측정치 조회
	@Test
	public void testGetTimelyMeasure() throws Exception{
		//given
		
		//when
		MockHttpServletRequestBuilder requestBuilder 
				= MockMvcRequestBuilders
					.get("/sensor/getMeasure")
					.accept(MediaType.APPLICATION_JSON)
					.param("sensor_id", "1")
					.param("st_dt", "20170401")
					.param("ed_dt", "20170430")
					.param("method", "timely");
		//then
		this.mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk());
	}
	
	//5분단위 측정치 조회
	@Test 
	public void testGet5MinMeasure() throws Exception{
		//given
		
		//when
		MockHttpServletRequestBuilder requestBuilder 
				= MockMvcRequestBuilders
					.get("/sensor/getMeasure")
					.accept(MediaType.APPLICATION_JSON)
					.param("sensor_id", "1")
					.param("st_dt", "20170402")
					.param("ed_dt", "20170430")
					.param("method", "5min");
		//then
		this.mockMvc.perform(requestBuilder).andDo(print())
		.andExpect(status().isOk());
	}
}