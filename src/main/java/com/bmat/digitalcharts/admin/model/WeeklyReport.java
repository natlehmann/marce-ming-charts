package com.bmat.digitalcharts.admin.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class WeeklyReport extends SummaryReport {

	private static final long serialVersionUID = 1042642783830129799L;
	
	@OneToMany(mappedBy="weeklyReport", cascade=CascadeType.ALL)
	private List<WeeklyReportItem> items;
	
	public List<WeeklyReportItem> getItems() {
		return items;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setItems(List items) {
		this.items = items;
	}


	@Override
	public void addItem(SummaryReportItem item) {
		if (this.items == null) {
			this.items = new LinkedList<>();
		}
		
		this.items.add((WeeklyReportItem) item);
	}

}
