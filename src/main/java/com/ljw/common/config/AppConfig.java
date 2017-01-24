package com.ljw.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@ComponentScan(basePackages = {"com.ljw.common"}, useDefaultFilters = false, 
includeFilters = {@Filter(Repository.class),
				  @Filter(Service.class)})
@Configuration
public class AppConfig  {
}