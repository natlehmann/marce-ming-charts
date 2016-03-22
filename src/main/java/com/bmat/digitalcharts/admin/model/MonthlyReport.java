package com.bmat.digitalcharts.admin.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
		return "<a onclick='confirmSendMail(" + this.getId() 
				+ ")' class='eliminar-link' title='Eliminar'></a>";
	}
	
	
	@Transient
	public static String getOrderingField(int indiceColumna) {
		
		switch(indiceColumna) {
		
		case 0:
			return "id";
		case 1:
			return "year";
		case 2:
			return "month";
		case 3:
			return "weekFrom";
		case 4:
			return "weekTo";
		case 5:
			return "country.name";

		default:
			return null;
		}
	}
	
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(String.valueOf(this.getId()));
		resultado.add(String.valueOf(this.getYear()));
		resultado.add(String.valueOf(this.getMonth()));
		resultado.add(String.valueOf(this.getWeekFrom()));
		resultado.add(String.valueOf(this.getWeekTo()));
		resultado.add(this.getCountry().getName());
		resultado.add(this.getRight().getName());
		resultado.add(this.getFilteredBySource() != null ? this.getFilteredBySource().getName() : "");
		
		if (this.getFilteredBySource() == null) {
			resultado.add(getLinkSendMail() + getLinkEliminar());
		
		} else {
			resultado.add(getLinkEliminar());
		}
		
		return resultado;
	}
	
	@Transient
	public String getLinkSendMail() {
		return "<a onclick='confirmSendMailMonthlyReport(" + this.getId() 
				+ ")' class='mail-link' title='Enviar mails a DCPs'>Mail</a>";
	}
}
