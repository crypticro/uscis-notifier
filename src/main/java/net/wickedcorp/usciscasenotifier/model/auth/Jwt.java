package net.wickedcorp.usciscasenotifier.model.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
@Getter
public class Jwt {

	@JsonProperty("id")
	private float id;

	@JsonProperty("username")
	private String username;

	@JsonProperty("email")
	private String email;

	@JsonProperty("roles")
	private List<String> roles;

	@JsonProperty("accessToken")
	private String accessToken;
	@JsonProperty("tokenType")
	private String tokenType;
}
