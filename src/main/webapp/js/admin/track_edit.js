var searchCallback = function( oSettings ) {
   
	$.each( $("input[type='radio']"), function(index, value) {
		if ( $(value).val() == $("#current_song_id").val() ) {
			$(value).attr("checked", "checked");
		}
	});
};
