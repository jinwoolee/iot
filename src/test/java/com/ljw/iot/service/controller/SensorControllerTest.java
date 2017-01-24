package com.ljw.iot.service.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.ljw.iot.config.AppConfigTest;
import com.ljw.iot.controller.SensorController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppConfigTest.class})
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
}