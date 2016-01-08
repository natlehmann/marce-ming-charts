package com.bmat.digitalcharts.admin.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
	
	
	@Transient
	public static String getOrderingField(int indiceColumna) {
		
		switch(indiceColumna) {
		
		case 0:
			return "id";
		case 1:
			return "year";
		case 2:
			return "weekFrom";
		case 3:
			return "country.name";
		case 4:
			return "right.name";
		case 5:
			return "filteredBySource.name";
		default:
			return null;
		}
	}
	
	
	@Override
	public List<String> getCamposAsList() {
		
		List<String> resultado = new LinkedList<>();
		resultado.add(String.valueOf(this.getId()));
		resultado.add(String.valueOf(this.getYear()));
		resultado.add(String.valueOf(this.getWeekFrom()));
		resultado.add(this.getCountry().getName());
		resultado.add(this.getRight().getName());
		resultado.add(this.getFilteredBySource() != null ? this.getFilteredBySource().getName() : "");
		resultado.add(super.getLinkEliminar());
		
		return resultado;
	}

}
