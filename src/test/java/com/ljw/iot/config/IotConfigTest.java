package com.ljw.iot.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@ComponentScan(basePackages = {"com.ljw.iot"}, useDefaultFilters = false, 
								includeFilters = {@Filter(Controller.class),
										  		  @Filter(ControllerAdvice.class),
										  		  @Filter(Repository.class),
												  @Filter(Service.class)})
@Configuration
@EnableWebMvc
public class IotConfigTest extends WebMvcConfigurerAdapter{

//	@Override
//	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//		configurer
//			.useJaf(true)
//			.favorPathExtension(true)
//			.favorParameter(false)
//			.ignoreAcceptHeader(false)
//			.defaultContentType(MediaType.APPLICATION_JSON)
//			.mediaType("json", MediaType.APPLICATION_JSON)
//			.mediaType("xml", MediaType.APPLICATION_XML);
//	}

	@Override  
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
		configurer.enable();
	}
	 
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(mappingJacksonHttpMessageConverter());
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setPrettyPrint(true);
		return converter;
	}
}