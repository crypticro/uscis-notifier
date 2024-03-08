package net.wickedcorp.usciscasenotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import net.wickedcorp.usciscasenotifier.model.auth.Jwt;

@Getter
public class Auth {

	@JsonProperty("JwtResponse")
	private Jwt jwtResponse;
}
