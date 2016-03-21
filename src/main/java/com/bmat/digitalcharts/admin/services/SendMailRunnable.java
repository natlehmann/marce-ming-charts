package com.bmat.digitalcharts.admin.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.bmat.digitalcharts.admin.model.EmailAddress;
import com.bmat.digitalcharts.admin.model.SummaryReport;

@Component
public class SendMailRunnable implements Runnable {
	
	private static Log log = LogFactory.getLog(SendMailRunnable.class);
	
	@Autowired
	protected JavaMailSender javaMailSender;
	
	@Autowired @Qualifier(value="fromAddress")
	private InternetAddress fromAddress;
	
	@Autowired
	protected MimeMessage mimeMessage;
	
	private SummaryReport report;
	
	private List<EmailAddress> recipients;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	
	
	@Override
	public void run() {
		
		if (this.report == null) {
			throw new IllegalArgumentException("No se ha seteado un reporte para su envio.");
		}
		
		
		try {
			configureMimeMessage();
			
			int contador = 0;
			for (EmailAddress recipient : recipients) {
				
				log.info("Enviando reporte a DCP " + recipient.getRestSource().getName());
				
				try {
					
					armarMailParaUsuario(mimeMessage, recipient.getEmail());
					
					javaMailSender.send(mimeMessage);
					
					contador++;
					
				} catch (Exception e) {
					log.error("Error al enviar mail a " + recipient.getEmail(), e);
				}
			}
			
			log.info("Reporte enviado a " + contador + " DCPs.");
			
			
		} catch (MessagingException e) {
			log.error("Error en la configuración del envio del reporte", e);
		}
		
	}


	protected void configureMimeMessage() throws MessagingException {
		
		mimeMessage.setHeader("Content-Type", "text/html");
		mimeMessage.setHeader("Content-Transfer-Encoding", "base64");
		
		mimeMessage.setSubject("Reporte " + report.getCountry().getName() + " del " 
				+ dateFormat.format(report.getDateFrom()) 
				+ " al " + dateFormat.format(report.getDateTo()), "UTF-8");					
		mimeMessage.setSentDate(new Date());					
		mimeMessage.setFrom(fromAddress);
	}


	public MimeMessageHelper armarMailParaUsuario(MimeMessage mimeMessage,
			String email) 
			throws MessagingException {
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);					
		
		helper.setText("hola");

		helper.setTo(new InternetAddress(email));
		
		return helper;
	}


	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;		
	}

	public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;		
	}
	
	public void setRecipients(List<EmailAddress> recipients) {
		this.recipients = recipients;
	}
	
	public void setReport(SummaryReport report) {
		this.report = report;
	}

}
