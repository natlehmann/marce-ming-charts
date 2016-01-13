$(document).ready(function() {
    var list = $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "admin/track/list_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        },
        "bAutoWidth" : false,
        "aoColumns": [
                      {"sWidth" : "25%"},
                      {"sWidth" : "12%"},
                      {"sWidth" : "8%"},
                      {"sWidth" : "28%" },
                      {"sWidth" : "20%" },
                      {"sWidth" : "7%" }
                    ],
        "columnDefs": [
                       { "orderable": false, "targets": 5 }
                     ],
         "aaSorting": [[ 3, "asc" ]]
    } );
    
    setTimeout(function (){
    	list.fnFilterOnEnter();
    }, 3000);
} );