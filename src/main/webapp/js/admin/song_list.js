$(document).ready(function() {
    $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "admin/song/list_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        },
        "bAutoWidth" : false,
        "aoColumns": [
                      {"sWidth" : "10%"},
                      {"sWidth" : "30%"},
                      {"sWidth" : "25%" },
                      {"sWidth" : "25%" },
                      {"sWidth" : "10%" }
                    ]
    } );
} );