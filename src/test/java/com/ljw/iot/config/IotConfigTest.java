package com.ljw.iot.config;

import java.io.IOException;
import java.io.InputStream;
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

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;


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
	
	@Bean
	public Firestore db() {
		InputStream serviceAccount = IotConfig.class.getClassLoader().getResourceAsStream("com/ljw/iot/config/firebase/iot-dust-sensor-account.json");
		GoogleCredentials credentials = null;
		try {
			credentials = GoogleCredentials.fromStream(serviceAccount);
			// getDate메소드를 호출하면 convertToDateIfNecessary(),
			// areTimestampsInSnapshotsEnabled() 설정에 의해,
			// date로 자동 형변환됨.
			// timestamp 타입으로 받고 싶으면 setTimestampsInSnapshotsEnabled(true) 설정을 적용 해야함
			FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials)
					.setFirestoreOptions(FirestoreOptions.newBuilder().setTimestampsInSnapshotsEnabled(true).build()).build();
			
			FirebaseApp.initializeApp(options);
			
			return FirestoreClient.getFirestore();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}