var table;

$(document).ready(function() {
    table = $('.datatable').dataTable( {
        "bProcessing": true,
        "bServerSide": true,
        "sAjaxSource": $("#contexto").val() + "admin/song/list_ajax?from=" + $("#from").val(),
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
                    ],
        "columnDefs": [
                       { "orderable": false, "targets": 4 }
                     ],
        "oSearch" : {"sSearch": $("#current_song_name").val()},
        
        "fnDrawCallback" : searchCallback
    } );
    
    setTimeout(function (){
    	table.fnFilterOnEnter();
    }, 3000);
    
} );
