package net.wickedcorp.usciscasenotifier.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {


	@Value("${environment.uscis.host}")
	private String uscisHost;

	@Bean
	public WebClient webClient() {
		return WebClient.builder()
				.baseUrl(uscisHost)
				.build();
	}
}
