$(document).ready(function() {
    var list = $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "admin/performer/list_ajax?from=" + $("#from").val(),
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        },
        "aoColumns": [
                      {"sWidth" : "15%"},
                      {"sWidth" : "70%" },
                      {"sWidth" : "15%" }
                    ],
        "columnDefs": [
                       { "orderable": false, "targets": 2 }
                     ],
        "oSearch" : {"sSearch": $("#current_performer_name").val()},
        "aaSorting": [[ 1, "asc" ]]
    } );
    
    setTimeout(function (){
    	list.fnFilterOnEnter();
    }, 3000);
} );