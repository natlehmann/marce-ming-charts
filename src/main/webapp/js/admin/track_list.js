$(document).ready(function() {
    var list = $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "admin/track/list_ajax?songId=" + $("#songId").val(),
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        },
        "bAutoWidth" : false,
        "aoColumns": [
                      {"sWidth" : "26%"},
                      {"sWidth" : "26%" },
                      {"sWidth" : "20%" },
                      {"sWidth" : "20%"},
                      {"sWidth" : "8%" }
                    ],
        "columnDefs": [
                       { "orderable": false, "targets": 4 }
                     ],
         "aaSorting": [[ 1, "asc" ]]
    } );
    
    setTimeout(function (){
    	list.fnFilterOnEnter();
    }, 3000);
} );