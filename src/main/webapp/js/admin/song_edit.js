$(document).ready(function() {
    
    $( "#performerAutocomplete" ).autocomplete({
    	 source: function( request, response ) {
    		 $.ajax({
    			 url: $("#contexto").val() + "admin/performer/findPerformerByName",
    			 dataType: "json",
    			 data: { performerName: request.term },
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
    			$("#performerId").val(ui.item.id);
    		}
    	}
    });
} );

function cleanPerformerSelection() {
	if ( $.trim( $("#performerAutocomplete").val() ) == '') {
		$("#performerId").val("");
	}
}