package com.bmat.digitalcharts.admin.model;

import java.util.List;

import com.bmat.digitalcharts.admin.controllers.Utils;

public class FromPageDataStrategy implements DataStrategy {
	
	private String from;
	
	private String regularListFrom;

	
	public FromPageDataStrategy(String from, String regularListFrom) {
		super();
		this.from = from;
		this.regularListFrom = regularListFrom;
	}



	@Override
	public List<String> getData(Listable listable) {
		
		if (from.equalsIgnoreCase(regularListFrom)) {
			return listable.getCamposAsList();
			
		} else {
			
			if (from.equalsIgnoreCase(Utils.TRACK_LIST)) {
				return listable.getFieldsForTrackEdition();
				
			} else {
				return listable.getFieldsForUniqueSelection();
			}
		}
	}

}
