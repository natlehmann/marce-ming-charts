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
                      {"sWidth" : "30%"},
                      {"sWidth" : "30%" },
                      {"sWidth" : "30%" },
                      {"sWidth" : "10%" }
                    ],
        "columnDefs": [
                       { "orderable": false, "targets": 3 }
                     ],
        "oSearch" : {"sSearch": $("#current_song_name").val()},        
        "fnDrawCallback" : searchCallback,
        "aaSorting": [[ 0, "asc" ]]
    } );
    
    setTimeout(function (){
    	table.fnFilterOnEnter();
    }, 3000);
    
} );
