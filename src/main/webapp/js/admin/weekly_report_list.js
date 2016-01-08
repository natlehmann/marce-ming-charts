$(document).ready(function() {
    $('.datatable-weekly').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "report/weekly/list_ajax",
        "bJQueryUI": true,
        "sPaginationType": "full_numbers",
        "oLanguage": {
            "sUrl": $("#contexto").val() + "js/datatables_ES.txt"
        },
        "columnDefs": [ { "targets": 4, "orderable": false },  { "targets": 5, "orderable": false } ],
        "bAutoWidth" : false,
        "aoColumns": [
                      {"sWidth" : "8%"},
                      {"sWidth" : "15%"},
                      {"sWidth" : "15%"},
                      {"sWidth" : "15%"},
                      {"sWidth" : "25%" },
                      {"sWidth" : "15%" },
                      {"sWidth" : "7%" }
                    ]
    } );
} );