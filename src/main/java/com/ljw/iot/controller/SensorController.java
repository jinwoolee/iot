package com.ljw.iot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljw.common.dao.CodeDao;
import com.ljw.iot.model.Sensor;
import com.ljw.iot.service.SensorService;

@Controller
@RequestMapping("/sensor")
public class SensorController {
	Logger logger = LoggerFactory.getLogger(SensorController.class);
	
	@Autowired
	private SensorService sensorService;
	
	/**
	  * @FileName : SensorController.java
	  * @Project : iot
	  * @Date : 2017. 1. 17.
	  * @작성자 : jw
	  * @변경이력 :
	  * @return
	  * @프로그램 설명 : 센서 리스트 조회
	  */
	@RequestMapping(value = "/sensors", method = RequestMethod.GET)
	@ResponseBody
	public List<Sensor> sensors(){
		return sensorService.getSensorList(); 
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public String sensors_(){
		return "servlet context root"; 
	}
}