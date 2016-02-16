$(document).ready(function() {
    
    $( "#labelCompanyAutocomplete" ).autocomplete({
    	 source: function( request, response ) {
    		 $.ajax({
    			 url: $("#contexto").val() + "admin/track/findLabelCompanyByName",
    			 dataType: "json",
    			 data: { labelCompanyName: request.term },
    			 success: function( data ) {
	    			 response( $.map( data, function( item ) {
	    				 return {
	    					 label: item.name,
	    					 id: item.id
	    				 };
	    			 }));
    		 	}
    		 });
    	},
    	minLength: 2,
    	select: function( event, ui ) {
    		if (ui.item) {
    			$("#labelCompanyId").val(ui.item.id);
    		}
    	}
    });
} );

function cleanLabelCompanySelection() {
	if ( $.trim( $("#labelCompanyAutocomplete").val() ) == '') {
		$("#labelCompanyId").val("");
	}
}