package com.ljw.iot.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.ljw.iot.httpcleint.HttpClientTest;

@Configuration
public class FirebaseConfig {
    
    @Bean
    public Firestore firestore() {
	InputStream serviceAccount = HttpClientTest.class.getClassLoader()
		.getResourceAsStream("com/ljw/iot/config/firebase/iot-dust-sensor-account.json");
	GoogleCredentials credentials = null;
	try {
	    credentials = GoogleCredentials.fromStream(serviceAccount);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	// getDate메소드를 호출하면 convertToDateIfNecessary(),
	// areTimestampsInSnapshotsEnabled() 설정에 의해,
	// date로 자동 형변환됨.
	// timestamp 타입으로 받고 싶으면 setTimestampsInSnapshotsEnabled(true) 설정을 적용 해야함
	FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials)
		.setFirestoreOptions(FirestoreOptions.newBuilder().setTimestampsInSnapshotsEnabled(true).build())
		.build();

	FirebaseApp.initializeApp(options);
	
	return FirestoreClient.getFirestore();
    }
}
