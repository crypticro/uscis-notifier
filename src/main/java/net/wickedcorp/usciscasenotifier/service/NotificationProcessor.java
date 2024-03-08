package net.wickedcorp.usciscasenotifier.service;

import jakarta.mail.MessagingException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.wickedcorp.usciscasenotifier.config.NotificationConfig;
import net.wickedcorp.usciscasenotifier.model.CaseStatusTransformed;
import net.wickedcorp.usciscasenotifier.repository.CaseStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class NotificationProcessor {

	@Autowired
	private EmailService emailService;
	private final CaseStatusRepository caseStatusRepository;

	private final NotificationConfig notificationConfig;

	private final CaseStatusService caseStatusService;

	public NotificationProcessor(CaseStatusRepository caseStatusRepository, @Qualifier("notification") NotificationConfig notificationConfig, CaseStatusService caseStatusService) {
		this.caseStatusRepository = caseStatusRepository;
		this.notificationConfig = notificationConfig;
		this.caseStatusService = caseStatusService;
	}

	@Scheduled(fixedRate = 3600000)
	public void processSubscriptions() {
		for (Map.Entry<String, Map<String, List<String>>> entry : this.notificationConfig.getSubscriptions().entrySet()) {
			List<String> recipients = entry.getValue().get("recipients");
			List<String> cases = entry.getValue().get("cases");
			System.out.println("Recipients: " + recipients);
			System.out.println("Cases: " + cases);

			for (String item : cases) {
				processCaseUpdates(item, recipients);
			}
		}
	}

	@SneakyThrows
	protected void processCaseUpdates(String receiptNumber, List<String> recipients) {
		CaseStatusTransformed currentStatus = this.caseStatusService.getUscisCaseStatus(receiptNumber);
		boolean caseAlreadyTracked = caseStatusRepository.existsById(receiptNumber);

		if (caseAlreadyTracked) {
			Optional<CaseStatusTransformed> trackedCase = caseStatusRepository.findById(receiptNumber);
			log.info("Case: " + receiptNumber + " | Type: " + currentStatus.getFormNum() + " is already tracked");
			if (trackedCase.get().getDate().before(currentStatus.getDate())
					|| (trackedCase.get().getDate().equals(currentStatus.getDate()) &&
					!Objects.equals(trackedCase.get().getActionCodeText(), currentStatus.getActionCodeText()))
			) {
				log.info("Case: " + receiptNumber + " | Type: " + currentStatus.getFormNum() + " | Update detected: ", currentStatus);
				try {
					notifyRecipients(currentStatus, recipients);
				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
				caseStatusRepository.save(currentStatus);
			}else {
				log.info("Case: " + receiptNumber + " | Type: " + currentStatus.getFormNum() + " | No Update available");
			}

		} else {
			log.info("Case: " + receiptNumber + " | Type: " + currentStatus.getFormNum() + " | Not tracked yet. Creating record: ", currentStatus);
			notifyRecipients(currentStatus, recipients);
			caseStatusRepository.save(currentStatus);
		}

	}

	protected void notifyRecipients(CaseStatusTransformed currentStatus, List<String> recipients) throws MessagingException {
		for (String item : recipients) {
			log.info("Email to: " + item + " | Content ", currentStatus);
			Context context = new Context();
			context.setVariable("receiptNumber", currentStatus.getReceiptNumber());
			context.setVariable("status", currentStatus.getActionCodeText());
			context.setVariable("actionDescription", currentStatus.getActionCodeDesc());
			String subject = "USCIS Update for "+ currentStatus.getFormNum() +" - "+ currentStatus.getFormTitle();
			emailService.sendEmail(item,subject, "notification", context);
		}

	}


}
