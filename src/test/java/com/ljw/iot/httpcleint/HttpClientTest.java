package com.ljw.iot.httpcleint;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class HttpClientTest {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientTest.class);
	
	private Firestore db;
	
	@Before
	public void init() {
		db = FirestoreClient.getFirestore();
	}

	@BeforeClass
	public static void setup() throws IOException {
		InputStream serviceAccount = HttpClientTest.class.getClassLoader().getResourceAsStream("com/ljw/iot/config/firebase/iot-dust-sensor-account.json");
		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

		// getDate메소드를 호출하면 convertToDateIfNecessary(),
		// areTimestampsInSnapshotsEnabled() 설정에 의해,
		// date로 자동 형변환됨.
		// timestamp 타입으로 받고 싶으면 setTimestampsInSnapshotsEnabled(true) 설정을 적용 해야함
		FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials)
				.setFirestoreOptions(FirestoreOptions.newBuilder().setTimestampsInSnapshotsEnabled(true).build()).build();

		FirebaseApp.initializeApp(options);
	}

	// get test
	// @Test
	public void httpClientRequestTest() throws ClientProtocolException, IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://github.com/jinwoolee");

		ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

			@Override
			public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			}
		};

		String responseString = httpClient.execute(httpGet, responseHandler);
		logger.debug("responseString : {}", responseString);
		assertNotNull(responseString);
	}

	// read firebase test
	@Test
	public void readDataFirebaseTest() throws IOException, InterruptedException, ExecutionException {

		ApiFuture<QuerySnapshot> query = db.collection("dustMeasure").get();
		QuerySnapshot querySnapshot = query.get();

		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {

			logger.debug("document.getId() : {} ", document.getId());
			logger.debug("aqi : {} ", document.getLong("aqi"));
			logger.debug("measure : {} ", document.getLong("measure"));
			logger.debug("measure_id : {} ", document.getLong("measure_id"));
			logger.debug("measure_id : {} ", document.getDate("reg_dt"));

			// timestamp 추후 적
			// logger.debug("reg_dt : {} ", document.getTimestamp("reg_dt"));
			// logger.debug("reg_dt : {} ", document.getDate("reg_dt"));
		}
	}
}
