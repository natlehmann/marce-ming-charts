$(document).ready(function() {
	initDisabledSection();
} );

var selectedPeriod = "week";
var timer;

function activateMonthSelection() {
	$("#month_selection").removeClass("disabled-selection");	
	$("#month_selection .selection_content select").removeAttr("disabled");
	$("#week_selection").addClass("disabled-selection");
	
	initDisabledSection();
	
	$("#select_button_month").removeClass("disabled-selection");
	$("#select_button_week").addClass("disabled-selection");
	
	selectedPeriod = "month";
}

function activateWeekSelection() {
	$("#week_selection").removeClass("disabled-selection");	
	$("#week_selection .selection_content select").removeAttr("disabled");
	$("#month_selection").addClass("disabled-selection");
	
	initDisabledSection();
	
	$("#select_button_week").removeClass("disabled-selection");
	$("#select_button_month").addClass("disabled-selection");
	
	selectedPeriod = "week";
}

function initDisabledSection() {
	$(".disabled-selection .selection_content select").val("");
	$(".disabled-selection .selection_content select").attr("disabled","disabled");
}

function validateForm(element) {
	
	var valid = true;
	$("#main-msg").html("");
	
	
	if (selectedPeriod == "month"){
		
		var weekFrom =$("#month_selection select[name='weekFrom']").val();
		var weekTo = $("#month_selection select[name='weekTo']").val();
		
		if ( $("#month_selection select[name='month']").val() == "" ) {
			$("#main-msg").append("Debe seleccionar un mes. ");
			valid = false;
		}
		
		if ( weekFrom == "" ) {
			$("#main-msg").append("Debe seleccionar una semana inicial. ");
			valid = false;
		}
		
		if ( weekTo == "" ) {
			$("#main-msg").append("Debe seleccionar una semana final. ");
			valid = false;
			
		} else {
			
			if ( weekFrom != "" && parseInt(weekTo) < parseInt(weekFrom) ) {
				$("#main-msg").append("La semana final debe ser posterior a la semana inicial. ");
				valid = false;
			}
		}
		
	} else {
		
		if ( $("#week_selection select[name='weekFrom']").val() == "" ) {
			$("#main-msg").append("Debe seleccionar una semana inicial. ");
			valid = false;
		}
	}
	
	if (valid) {
		$("#form").attr("action", $("#form_action").val() );
		$("#action").val( $(element).val() );
		$("#form").submit();
		
		$("#archivar_reporte").show();
		$("#crear_reporte").hide();
		timer = setTimeout(enableSaveButton, 1000);
	}
}

function saveReport(element) {
	$("#form").attr("action", $("#form_action").val() );
	$("#action").val( $(element).val() );
	$("#form").submit();
}

function clearSelection() {
	clearTimeout(timer);
	$("#archivar_reporte").hide();
	$("#crear_reporte").show();
}

function enableSaveButton() {

	$.get( $("#enable_save_url").val() , function(data) {
		if (data == true) {
			$("#save_button").removeAttr("disabled");
			
		} else {
			timer = setTimeout(enableSaveButton, 1000);
		}
	});
	
}