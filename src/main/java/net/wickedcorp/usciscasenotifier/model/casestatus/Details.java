package net.wickedcorp.usciscasenotifier.model.casestatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Details {

	@JsonProperty("formNum")
	private String formNum;

	@JsonProperty("formTitle")
	private String formTitle;

	@JsonProperty("actionCodeText")
	private String actionCodeText;

	@JsonProperty("actionCodeDesc")
	private String actionCodeDesc;

	@JsonProperty("empty")
	private boolean empty;




}
