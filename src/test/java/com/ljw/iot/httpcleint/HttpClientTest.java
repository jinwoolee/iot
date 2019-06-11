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
}
