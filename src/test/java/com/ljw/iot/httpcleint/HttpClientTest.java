package com.ljw.iot.httpcleint;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientTest {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientTest.class);

	@Test
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
