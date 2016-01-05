$(document).ready(function() {
    $('.datatable').dataTable( {
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
                      {"sWidth" : "8%"},
                      {"sWidth" : "25%"},
                      {"sWidth" : "12%"},
                      {"sWidth" : "8%"},
                      {"sWidth" : "20%" },
                      {"sWidth" : "20%" },
                      {"sWidth" : "7%" }
                    ]
    } );
} );