package com.bmat.digitalcharts.admin.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class MonthlyReport extends SummaryReport {

	private static final long serialVersionUID = -2099764491946077098L;

	private Integer month;
	
	@OneToMany(mappedBy="monthlyReport", cascade=CascadeType.ALL)
	private List<MonthlyReportItem> items;
	
	public List<MonthlyReportItem> getItems() {
		return items;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setItems(List items) {
		this.items = items;
	}
	
	public Integer getMonth() {
		return month;
	}
	
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	@Override
	public boolean isMonthly() {
		return true;
	}
	
	@Override
	public void addItem(SummaryReportItem item) {
		if (this.items == null) {
			this.items = new LinkedList<>();
		}
		
		this.items.add((MonthlyReportItem) item);
	}
	
	
	@Override
	public String getLinkEliminar() {
		return "<a onclick='confirmDeleteMonthlyReport(" + this.getId() 
				+ ")' class='eliminar-link' title='Eliminar'></a>";
	}
}
