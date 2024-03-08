package net.wickedcorp.usciscasenotifier.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ConfigurationProperties(prefix = "notification")
@Configuration("notification")
public class NotificationConfig {

	private Map<String, Map<String, List<String>>> subscriptions;

}
