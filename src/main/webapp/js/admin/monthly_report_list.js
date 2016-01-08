$(document).ready(function() {
    $('.datatable-monthly').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "report/monthly/list_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        },
        "columnDefs": [ { "targets": 7, "orderable": false },  { "targets": 8, "orderable": false } ],
        "bAutoWidth" : false,
        "aoColumns": [
                      {"sWidth" : "7%"},
                      {"sWidth" : "10%"},
                      {"sWidth" : "10%"},
                      {"sWidth" : "10%"},
                      {"sWidth" : "10%" },
                      {"sWidth" : "15%" },
                      {"sWidth" : "16%" },
                      {"sWidth" : "15%" },
                      {"sWidth" : "7%" }
                    ]
    } );
} );