$(document).ready(function() {
    var list = $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "admin/performer/list_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        }
    } );
    
    setTimeout(function (){
    	list.fnFilterOnEnter();
    }, 3000);
} );