package com.ljw.iot.config;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

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
public class IotConfig {
	
	private Logger logger = LoggerFactory.getLogger(IotConfig.class);
	
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
			
			logger.debug("FirebaseApp.getApps().size() : {}", FirebaseApp.getApps().size());
			if(FirebaseApp.getApps().size() ==0)
				FirebaseApp.initializeApp(options);
			
			return FirestoreClient.getFirestore();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}