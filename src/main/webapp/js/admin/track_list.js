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
                      {"sWidth" : "20%"},
                      {"sWidth" : "8%"},
                      {"sWidth" : "18%" },
                      {"sWidth" : "18%" },
                      {"sWidth" : "17%" },
                      {"sWidth" : "12%"},
                      {"sWidth" : "7%" }
                    ],
        "columnDefs": [
                       { "orderable": false, "targets": 6 }
                     ],
         "aaSorting": [[ 2, "asc" ]]
    } );
    
    setTimeout(function (){
    	list.fnFilterOnEnter();
    }, 3000);
} );