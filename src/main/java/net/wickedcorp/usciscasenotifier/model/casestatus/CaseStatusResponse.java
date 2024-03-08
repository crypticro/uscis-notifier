package net.wickedcorp.usciscasenotifier.model.casestatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CaseStatusResponse {

	@JsonProperty("receiptNumber")
	private String receiptNumber;

	@JsonProperty("isValid")
	private boolean isValid;

	@JsonProperty("detailsEng")
	private Details detailsEng;

	@JsonProperty("detailsEs")
	private Details detailsEs;
}
