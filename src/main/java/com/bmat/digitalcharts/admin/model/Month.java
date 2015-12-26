package com.bmat.digitalcharts.admin.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Month implements Serializable {
	
	private static final long serialVersionUID = 4225587378315258461L;
	
	private static List<Month> months;

	private Integer id;
	
	private String name;
	
	public Month(){}

	public Month(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Month other = (Month) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public static List<Month> getMonths() {
		
		if (months == null) {
		
			months = new LinkedList<>();
			
			months.add(new Month(1, "Enero"));
			months.add(new Month(2, "Febrero"));
			months.add(new Month(3, "Marzo"));
			months.add(new Month(4, "Abril"));
			months.add(new Month(5, "Mayo"));
			months.add(new Month(6, "Junio"));
			months.add(new Month(7, "Julio"));
			months.add(new Month(8, "Agosto"));
			months.add(new Month(9, "Septiembre"));
			months.add(new Month(10, "Octubre"));
			months.add(new Month(11, "Noviembre"));
			months.add(new Month(12, "Diciembre"));
		}
		
		return months;
	}
	
	public static Month getMonth(Integer id) {
		
		for (Month month : getMonths()) {
			if (month.getId().equals(id)) {
				return month;
			}
		}
		
		return null;
	}

}
