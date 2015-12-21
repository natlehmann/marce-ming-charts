package com.bmat.digitalcharts.admin.model;

import java.text.NumberFormat;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class SummaryReportItem extends AbstractEntity {

	private static final long serialVersionUID = 2765679262811873494L;
	
	public static final String EQUAL = "(=)";
	public static final String OPEN_COMPARISON = "(";
	public static final String CLOSE_COMPARISON = ")";
	
	@Column(nullable=false)
	private Integer currentPosition;
	
	private Integer previousPosition;
	
	private Integer positionBeforePrevious;
	
	@Column(nullable=false)
	private Long songId;
	
	private String songName;
	
	@Column(nullable=false)
	private Long performerId;
	
	private String performerName;
	
	@Column(nullable=false)
	private Long currentAmount;
	
	private Long previousAmount;
	
	private Long labelCompanyId;
	
	private String labelCompanyName;
	
	public SummaryReportItem(){}
	
	

	public SummaryReportItem(Long songId, String songName, Long performerId,
			String performerName, Long currentAmount, Long labelCompanyId,
			String labelCompanyName) {
		super();
		this.songId = songId;
		this.songName = songName;
		this.performerId = performerId;
		this.performerName = performerName;
		this.currentAmount = currentAmount;
		this.labelCompanyId = labelCompanyId;
		this.labelCompanyName = labelCompanyName;
	}

	public Integer getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Integer currentPosition) {
		this.currentPosition = currentPosition;
	}

	public Integer getPreviousPosition() {
		return previousPosition;
	}

	public void setPreviousPosition(Integer previousPosition) {
		this.previousPosition = previousPosition;
	}

	public Integer getPositionBeforePrevious() {
		return positionBeforePrevious;
	}

	public void setPositionBeforePrevious(Integer positionBeforePrevious) {
		this.positionBeforePrevious = positionBeforePrevious;
	}

	public Long getSongId() {
		return songId;
	}

	public void setSongId(Long songId) {
		this.songId = songId;
	}

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public Long getPerformerId() {
		return performerId;
	}

	public void setPerformerId(Long performerId) {
		this.performerId = performerId;
	}

	public String getPerformerName() {
		return performerName;
	}

	public void setPerformerName(String performerName) {
		this.performerName = performerName;
	}

	public Long getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(Long currentAmount) {
		this.currentAmount = currentAmount;
	}

	public Long getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(Long previousAmount) {
		this.previousAmount = previousAmount;
	}

	public Long getLabelCompanyId() {
		return labelCompanyId;
	}

	public void setLabelCompanyId(Long labelCompanyId) {
		this.labelCompanyId = labelCompanyId;
	}

	public String getLabelCompanyName() {
		return labelCompanyName;
	}

	public void setLabelCompanyName(String labelCompanyName) {
		this.labelCompanyName = labelCompanyName;
	}

	@Transient
	public String getComparison() {
		
		if (this.currentPosition != null && this.previousPosition != null) {
			if (this.currentPosition.equals(this.previousPosition)) {
				return EQUAL;
			}
			
			if (this.currentPosition < this.previousPosition) {
				return OPEN_COMPARISON + "+" + (this.previousPosition - this.currentPosition) 
						+ CLOSE_COMPARISON;
			}
			
			if (this.currentPosition > this.previousPosition) {
				return OPEN_COMPARISON + "-" + (this.currentPosition - this.previousPosition) 
						+ CLOSE_COMPARISON;
			}
		}
		
		return "";
	}

	@Transient
	public String getComparativePercentage() {
		
		if (this.currentAmount != null && this.previousAmount != null) {
			
			long difference = this.currentAmount - this.previousAmount;
			double percentage = Math.abs(difference) / (double)this.previousAmount;
			
			String sign = "";
			if (difference < 0) {
				sign = "-";
			}
			
			NumberFormat format = NumberFormat.getPercentInstance(new Locale("es"));
			return sign + format.format(percentage);
		}
		
		return "";
	}



	public abstract void setReport(SummaryReport report);
	
	

}
