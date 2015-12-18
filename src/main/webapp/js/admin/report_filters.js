$(document).ready(function() {
	initDisabledSection();
} );

var selectedPeriod = "week";

function activateMonthSelection() {
	$("#month_selection").removeClass("disabled-selection");	
	$("#month_selection .selection_content select").removeAttr("disabled");
	$("#week_selection").addClass("disabled-selection");
	
	initDisabledSection();
	
	selectedPeriod = "month";
}

function activateWeekSelection() {
	$("#week_selection").removeClass("disabled-selection");	
	$("#week_selection .selection_content select").removeAttr("disabled");
	$("#month_selection").addClass("disabled-selection");
	
	initDisabledSection();
	
	selectedPeriod = "week";
}

function initDisabledSection() {
	$(".disabled-selection .selection_content select").val("");
	$(".disabled-selection .selection_content select").attr("disabled","disabled");
}

function validateForm() {
	
	var valid = true;
	
	if (selectedPeriod == "month"){
		if ( $("#month_selection select[name='month']").val() == "" ) {
			$("#main-msg").html("Debe seleccionar un mes. ");
			valid = false;
		}
		
		if ( $("#month_selection select[name='weekFrom']").val() == "" ) {
			$("#main-msg").append("Debe seleccionar una semana inicial. ");
			valid = false;
		}
		
		if ( $("#month_selection select[name='weekTo']").val() == "" ) {
			$("#main-msg").append("Debe seleccionar una semana final. ");
			valid = false;
		}
		
	} else {
		
		if ( $("#week_selection select[name='weekFrom']").val() == "" ) {
			$("#main-msg").append("Debe seleccionar una semana inicial. ");
			valid = false;
		}
	}
	
	return valid;
}