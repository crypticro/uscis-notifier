package net.wickedcorp.usciscasenotifier.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class CaseStatusTransformed {

	@Id
	private String receiptNumber;

	private String formNum;

	private String formTitle;

	private String actionCodeText;

	private String actionCodeDesc;

	private Date date;

	private String uspsTrackingCode;
}
