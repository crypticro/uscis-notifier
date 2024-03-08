package net.wickedcorp.usciscasenotifier.service;

import lombok.extern.slf4j.Slf4j;
import net.wickedcorp.usciscasenotifier.config.WebclientConfig;
import net.wickedcorp.usciscasenotifier.model.Auth;
import net.wickedcorp.usciscasenotifier.model.mapper.CaseStatusMapper;
import net.wickedcorp.usciscasenotifier.model.CaseStatus;
import net.wickedcorp.usciscasenotifier.model.CaseStatusTransformed;
import org.mapstruct.factory.Mappers;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.Duration;

@Service
@Slf4j
public class CaseStatusService {

	private final CaseStatusMapper caseStatusMapper;
	private final WebclientConfig webClient;

	public CaseStatusService(WebclientConfig webClient) {
		this.caseStatusMapper = Mappers.getMapper(CaseStatusMapper.class);
		this.webClient = webClient;
	}

	public CaseStatusTransformed getUscisCaseStatus(String receiptNumber) {
		Mono<CaseStatusTransformed> response = this.webClient.webClient()
				.get().uri("/csol-api/case-statuses/"+ receiptNumber)
				.header("Authorization","Bearer "+this.getAccessToken())
				.retrieve().bodyToMono(CaseStatus.class)
				.map(this.caseStatusMapper::toCaseStatusTransformed)
				.retry(3);
		return response.block();
	}

	public String getAccessToken() {

		Mono<Auth> response = this.webClient.webClient()
				.get()
				.uri("/csol-api/ui-auth")
				.headers(headers -> headers.setContentType(MediaType.APPLICATION_JSON))
				.retrieve()
				.bodyToMono(Auth.class)
				.doOnError(error -> {
					log.error("Error occurred during request:", error);
				})
				.cache(Duration.ofHours(24))
				.retry(3);

		return response.block().getJwtResponse().getAccessToken();
	}
}
