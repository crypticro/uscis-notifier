package net.wickedcorp.usciscasenotifier.model.mapper;

import net.wickedcorp.usciscasenotifier.model.CaseStatus;
import net.wickedcorp.usciscasenotifier.model.CaseStatusTransformed;
import org.mapstruct.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE,
		builder = @Builder(disableBuilder = true))
public interface CaseStatusMapper {
	@Mapping(source = "caseStatusResponse.receiptNumber", target = "receiptNumber")
	@Mapping(source = "caseStatusResponse.detailsEng.formNum", target = "formNum")
	@Mapping(source = "caseStatusResponse.detailsEng.formTitle", target = "formTitle")
	@Mapping(source = "caseStatusResponse.detailsEng.actionCodeText", target = "actionCodeText")
	@Mapping(source = "caseStatusResponse.detailsEng.actionCodeDesc", target = "actionCodeDesc")
	@Mapping(source = ".", target = "date", qualifiedByName = "extractDate")
	@Mapping(source = ".", target = "uspsTrackingCode", qualifiedByName = "extractUspsTrackingCode")
	CaseStatusTransformed toCaseStatusTransformed(CaseStatus caseStatus);

	@Named("extractDate")
	static LocalDate extractDate(CaseStatus casestatus){
		String regex = "^.*(January|February|March|April|May|June|July|August|September|October|November|December) (\\d{1,2}), (\\d{4}).*$";
		Matcher matcher = Pattern.compile(regex).matcher(casestatus.getCaseStatusResponse().getDetailsEng().getActionCodeDesc());
		if (matcher.find()) {
			String month = matcher.group(1);
			int day = Integer.parseInt(matcher.group(2));
			int year = Integer.parseInt(matcher.group(3));
			return LocalDate.of(year, Month.valueOf(month.toUpperCase()), day);
		} else {
			return LocalDate.now();
		}
	}

	@Named("extractUspsTrackingCode")
	static String extractUspsTrackingCode(CaseStatus casestatus){
		String regex = "^.*https://tools\\.usps\\.com/go/TrackConfirmAction_input\\?origTrackNum=(\\d+).*$";
		Matcher matcher = Pattern.compile(regex).matcher(casestatus.getCaseStatusResponse().getDetailsEng().getActionCodeDesc());
		return matcher.find() ? matcher.group(1) : null;
	}

}
