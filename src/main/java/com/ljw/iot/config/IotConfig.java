package com.ljw.iot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@ComponentScan(basePackages = {"com.ljw.iot"}, useDefaultFilters = false, 
								includeFilters = {@Filter(Controller.class),
										  		  @Filter(ControllerAdvice.class),
										  		  @Filter(Repository.class),
												  @Filter(Service.class)})
@Configuration
@EnableWebMvc
public class IotConfig {
//test 	  
}