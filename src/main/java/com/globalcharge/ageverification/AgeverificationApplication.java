package com.globalcharge.ageverification;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AgeverificationApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AgeverificationApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(AgeverificationApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() throws Exception {

		// server

		// char[] password = "tomcatpass".toCharArray();
		// String filePath="/opt/apache-tomcat-8.5.5/conf/globalchargeKeyStore.jks";

		// local

		// char[] password = "tomcatpass".toCharArray();
		// String filePath="/opt/apache-tomcat-8.5.5/conf/cacerts.jks";

		// local server copy
		char[] password = "tomcatpass".toCharArray();
		String filePath = "/opt/apache-tomcat-8.5.5/conf/globalchargeKeyStore.jks";

		SSLContext sslContext = SSLContextBuilder.create().loadKeyMaterial(keyStore(filePath, password), password)
				.loadTrustMaterial(new File(filePath), password).build();

		CloseableHttpClient client = HttpClients.custom().setSslcontext(sslContext).build();
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(client));
		return restTemplate;

	}

	private KeyStore keyStore(String file, char[] password) throws Exception {
		KeyStore keyStore = KeyStore.getInstance("JKS");
		File key = ResourceUtils.getFile(file);
		try (InputStream in = new FileInputStream(key)) {
			keyStore.load(in, password);
		}
		return keyStore;
	}

}
