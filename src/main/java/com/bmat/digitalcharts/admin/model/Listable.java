package com.bmat.digitalcharts.admin.model;

import java.util.List;

public interface Listable {
	
	List<String> getCamposAsList();

	List<String> getFieldsForUniqueSelection();

	List<String> getFieldsForTrackEdition();

}
