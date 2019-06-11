package com.globalcharge.ageverification.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.globalcharge.ageverification.client.HttpClient;

@RestController
public class Controller {

	private static final Logger logger = LogManager.getLogger(Controller.class);

	HttpClient httpClient;

	@Autowired
	public Controller(HttpClient client) {
		this.httpClient = client;
	}
	@GetMapping("/forwardresponse")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> forwardResponse(HttpServletRequest request) {

		StringBuilder newQueryString = new StringBuilder();
		String queryString = null;
		if (request.getQueryString() != null) {

			 queryString = new String(request.getQueryString());
			String[] tempArray = null;

			logger.info("REQUEST RECIEVED " + queryString);

			// remove extra params and add new

			if (queryString.contains("&")) {

				tempArray = queryString.split("&");
				for (int i = 0; i < tempArray.length; i++) {

					if (tempArray[i].contains("gc_age_verified") || tempArray[i].contains("gc_mt_type"))
						continue;

					if (tempArray[i].contains("gc_aggregator")) {
						tempArray[i] = "gc_aggregator=2";
					}

					newQueryString.append(tempArray[i] + "&");
				}
			}

			newQueryString.append("action=mpush_ir_message");
		}
		else {
			logger.info("REQUEST RECIEVED " + queryString);
		}

		httpClient.sendPostHttps(newQueryString.toString());

		return new ResponseEntity<>(HttpStatus.OK);

	}

}
