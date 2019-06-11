package com.globalcharge.ageverification.client;

import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.globalcharge.ageverification.util.RequestResponseLoggingInterceptor;
import com.globalcharge.ageverification.util.Utility;

@Service
public class HttpClient {

	private static final Logger logger = LogManager.getLogger(HttpClient.class);

	RestTemplate restTemplate;

	private final String url;

	@Autowired
	public HttpClient(RestTemplate restTemplate, @Value("${destination-url}") String url) {
		this.restTemplate = restTemplate;
		this.url = url;
	}

	public void sendPostHttps(String queryString) {

		try {

			restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
			ResponseEntity<ResponseEntity> response = restTemplate.postForEntity(url, queryString,
					ResponseEntity.class);

		} catch (org.springframework.web.client.ResourceAccessException e) {
		}

		catch (org.springframework.web.client.RestClientException e) {
			logger.error(Utility.convertStackTraceToString(e));

		}
	}

}
