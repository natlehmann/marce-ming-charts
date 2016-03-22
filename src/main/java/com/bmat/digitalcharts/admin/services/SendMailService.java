package com.bmat.digitalcharts.admin.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.bmat.digitalcharts.admin.model.EmailAddress;
import com.bmat.digitalcharts.admin.model.SummaryReport;


@Service
public class SendMailService {
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private SendMailRunnable sendMailRunnable;
	
	
	public void sendReportsToDCPs(SummaryReport report, List<EmailAddress> recipients) {
		
		if ( !sendMailRunnable.isRunning() ) {
			
			sendMailRunnable.setReport(report);
			sendMailRunnable.setRecipients(recipients);
			
			taskExecutor.execute(sendMailRunnable);
			
		} else {
			throw new IllegalStateException(
					"No es posible ejecutar el envío de mails en este momento porque ya está en ejecución. "
					+ "Por favor intente nuevamente más tarde.");
		}
		
	}


}
