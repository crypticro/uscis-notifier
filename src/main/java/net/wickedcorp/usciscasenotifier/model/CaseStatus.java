package net.wickedcorp.usciscasenotifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import net.wickedcorp.usciscasenotifier.model.casestatus.CaseStatusResponse;
@Getter
public class CaseStatus {

	@JsonProperty("CaseStatusResponse")
	private CaseStatusResponse caseStatusResponse;
}
