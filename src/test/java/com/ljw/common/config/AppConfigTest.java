package com.ljw.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ComponentScan(basePackages = {"com.ljw.common"}, useDefaultFilters = false, 
								includeFilters = {@Filter(Repository.class),
												  @Filter(Service.class),
												  @Filter(Controller.class),
												  @Filter(ControllerAdvice.class)})
@Configuration
public class AppConfigTest {

}