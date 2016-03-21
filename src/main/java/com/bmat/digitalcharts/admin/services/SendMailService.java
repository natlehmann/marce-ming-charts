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
		
		sendMailRunnable.setReport(report);
		sendMailRunnable.setRecipients(recipients);
		
		taskExecutor.execute(sendMailRunnable);
		
	}


}
