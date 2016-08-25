$(document).ready(function () {
    //$('#MiForm\\:MiTabla').DataTable();
    //$('.table').DataTable();
    $('.table').dataTable( {
       'bSort': false,
       'style' : 'Bootstrap',
       'aoColumns': [
             { sWidth: "50%", bSearchable: true, bSortable: false },
             { sWidth: "50%", bSearchable: true, bSortable: false }
       ],
       "oLanguage": {
            "sLengthMenu": "Mostrando _MENU_ Registros Por Pagina",
            "sZeroRecords": "No Se Encontraron Registros",
            "sInfo": "Mostrando _START_ de _END_ ( de _TOTAL_ Registros)",
            "sInfoEmpty": "Mostrando 0 de 0 Registros",
            "sInfoFiltered": "",
            "sSearch":"Buscar: ",
            "sPrev":"Anterior",
            sFirst:"Primero",sLast:"Ultimo",sNext:"Siguiente",sPrevious:"Anterior"
        },
       "scrollY":        "200px",
       "scrollCollapse": false,
       "info":           true,
       "paging":         true
   });

    
});