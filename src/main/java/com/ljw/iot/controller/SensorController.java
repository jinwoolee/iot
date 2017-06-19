package com.ljw.iot.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ljw.common.dao.CodeDao;
import com.ljw.iot.model.Measure;
import com.ljw.iot.model.Sensor;
import com.ljw.iot.model.SensorVo;
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
	
	@RequestMapping(value = "/measure", method = RequestMethod.POST)
	@ResponseBody
	public String insertMeasure(Measure measure){
		String msg;
		
		int insertCnt = sensorService.insertMeasure(measure);
		msg = insertCnt == 1 ? "OK" : "ERROR"; 
		
		return msg;
	}
	
	@RequestMapping(value="/measureView", method = RequestMethod.GET)
	public ModelAndView getMeasureView(SensorVo sensorVo) {
		ModelAndView mav = new ModelAndView("/sensor/measureView");
		//mav.addObject("measureList", sensorService.getMeasure(sensorVo));
		return mav;
	}
	
	@RequestMapping(value = "/getMeasure", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<Measure>> getMeasure(SensorVo sensorVo){
		return sensorService.getMeasure(sensorVo);
	}
}