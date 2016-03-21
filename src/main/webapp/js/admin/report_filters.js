$(document).ready(function() {
	initDisabledSection();
	selectedPeriod = $("#isMonthlyReport").val() == "true" ? "month" : "week";
	
	$("#dialog-report-exists").dialog({
		resizable : false,
		width : 350,
		modal : true,
		autoOpen : false
	});
	
	
	$("#dialog-show-email-addresses").dialog({
		resizable : false,
		width : 350,
		modal : true,
		autoOpen : false
	});
	
} );

var selectedPeriod;
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

function checkReportExistence(element) {
	
	$("#action").val( $(element).val() );
	
	$.get( $("#check_existence_url").val(), $("#form").serialize(), function(data) {
		if (data != "") {
			
			$("#dialog-report-exists .message").html(data);
			$("#dialog-report-exists").dialog("open");
			
		} else {
			saveReport();
		}
	});
}

function saveReport() {
	$("#form").attr("action", $("#form_action").val() );
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


function confirmDeleteWeeklyReport(id) {
	
	$("#dialog-eliminar-id").val(id);
	$("#dialog-eliminar form").attr("action", $("#contexto").val() + "report/weekly/delete");
	$("#dialog-eliminar").dialog("open");
}

function confirmDeleteMonthlyReport(id) {
	
	$("#dialog-eliminar-id").val(id);
	$("#dialog-eliminar form").attr("action", $("#contexto").val() + "report/monthly/delete");
	$("#dialog-eliminar").dialog("open");
}

function confirmSendMail(id) {
	
	$("#dialog-show-email-addresses-id").val(id);
	$('#rest-sources-emails').html("");
	
	$.getJSON( $("#get_email_addresses_url").val() , function(data) {
		$.each(data, function(index, element) {
	        $('#rest-sources-emails').append($('<li>', {
	            text: element.restSource.name + " : " + element.email
	        }));
	    });
		
		$("#dialog-show-email-addresses").dialog("open");
	});
	
}