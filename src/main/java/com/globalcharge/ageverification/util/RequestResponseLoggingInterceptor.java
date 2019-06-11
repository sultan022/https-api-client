package com.globalcharge.ageverification.util;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
 
public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {
 
	private static final Logger logger = LogManager.getLogger(RequestResponseLoggingInterceptor.class);
	
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }
 
    private void logRequest(HttpRequest request, byte[] body) throws IOException {
            logger.info("===========================request begin================================================");
            logger.info("URI         : "+request.getURI());
            logger.info("Method      : "+ request.getMethod());
            logger.info("Request body: "+ new String(body, "UTF-8"));
            logger.info("==========================request end================================================");
    }
 
    private void logResponse(ClientHttpResponse response) throws IOException {
            logger.info("============================response begin==========================================");
            logger.info("Status code  : "+ response.getStatusCode());
            logger.info("Status text  : "+ response.getStatusText());
            logger.info("Response body: "+ StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            logger.info("=======================response end=================================================");
    }
}