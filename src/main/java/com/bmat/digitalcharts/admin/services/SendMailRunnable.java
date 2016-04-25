package com.bmat.digitalcharts.admin.services;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.bmat.digitalcharts.admin.model.EmailAddress;
import com.bmat.digitalcharts.admin.model.SummaryReport;
import com.bmat.digitalcharts.admin.view.ChartSummaryExcelView;
import com.bmat.digitalcharts.admin.view.ReportViewUtils;
import com.bmat.digitalcharts.admin.view.ReportViewUtils.Extension;

@Component
public class SendMailRunnable implements Runnable {
	
	private static Log log = LogFactory.getLog(SendMailRunnable.class);
	
	@Autowired
	protected JavaMailSender javaMailSender;
	
	@Autowired @Qualifier(value="fromAddress")
	private InternetAddress fromAddress;
	
	@Autowired
	protected MimeMessage mimeMessage;
	
	@Autowired
	private SummaryReportService summaryReportService;
	
	@Value("${mail.to.dcps.content.message}")
	private String CONTENT_MESSAGE;
	
	private ChartSummaryExcelView excelView = new ChartSummaryExcelView();
	
	private SummaryReport report;
	
	private List<EmailAddress> recipients;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	private boolean running;
	
	
	
	@Override
	public void run() {
		
		if (this.report == null) {
			throw new IllegalArgumentException("No se ha seteado un reporte para su envio.");
		}
		
		
		try {
			running = true;
			configureMimeMessage();
			
			
			int contador = 0;
			for (EmailAddress recipient : recipients) {
				
				log.info("Enviando reporte a DCP " + recipient.getRestSource().getName());
				
				try {
					log.info("Inicia busqueda y armado del reporte");
					SummaryReport filteredReport = summaryReportService.getSummaryReport(
							report.getCountry().getId(), report.getYear(), report.getWeekFrom(), 
							report.getWeekTo(), report.getMonth(), report.getRight().getId(), 
							recipient.getRestSource().getId());
					
					String reportName = ReportViewUtils.getReportName(filteredReport, Extension.XLS);
					
					log.info("Inicia armado del Excel");
					
					HSSFWorkbook workbook = new HSSFWorkbook();
					excelView.buildExcelDocument(workbook, filteredReport);
					
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					workbook.write(os);
					os.close();
					
					log.info("Configuracion del mail");					
					armarMailParaUsuario(mimeMessage, recipient.getEmail(), os.toByteArray(), reportName);
					
					log.info("Enviando mail");
					javaMailSender.send(mimeMessage);
					
					log.info("Guardando reporte - anulado");
//					summaryReportService.saveReport(filteredReport);
					
					contador++;
					
					log.info("Fin del proceso para " + recipient.getRestSource().getName());
					
				} catch (Exception e) {
					log.error("Error al enviar mail a " + recipient.getEmail(), e);
				}
			}
			
			log.info("Reporte enviado a " + contador + " DCPs.");
			
			
		} catch (MessagingException e) {
			log.error("Error en la configuración del envio del reporte", e);
		
		} finally {
			running = false;
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
			String email, byte[] byteArray, String reportName) 
			throws MessagingException {
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);					
		
		helper.setText(CONTENT_MESSAGE);

		helper.setTo(new InternetAddress(email));
		
		helper.addAttachment(reportName, new ByteArrayResource(byteArray), "application/vnd.ms-excel");
		
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
	
	public boolean isRunning() {
		return running;
	}

}
