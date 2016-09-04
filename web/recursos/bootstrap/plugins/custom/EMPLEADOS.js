$(document).ready(function() {
    $.fn.fnGuarElem = function () {
        $(this).unbind("click").on("click", function (e)
        {
            guarElem();
            return false;
        });
    };
    
    $.fn.fnActuElem = function () {
        $(this).unbind("click").on("click", function (e)
        {
            actuElem();
            return false;
        });
    };
    $.fn.fnElimElem = function(e) {
        $(this).confirmation({popout:true,
                    onConfirm: function () {
                        
                        $('[data-toggle="confirmation-popout"]').confirmation('hide');
                        return false;
                    },
                    onCancel: function ()
                    {
                        $('[data-toggle="confirmation-popout"]').confirmation('hide');
                        return false;
                    }
        });
    };
    
    REFR_OBJE_ROL();
});

function ABRI_FORM()
{
    $('#MODA_ELEM').modal('show');
    $('#MODA_ELEM').on('shown.bs.modal', function () {
        REFR_OBJE_FORM_ROL();
    });
}

function REFR_OBJE_FORM_ROL()
{
    $('#FORM_ELEM\\:btnGuarElem').fnGuarElem();
    $('#FORM_ELEM\\:btnEditElem').fnActuElem(); 
    $('#FORM_ELEM\\:btnElimElem').fnElimElem(); 
}

function REFR_OBJE_ROL()
{
    //La tabla
    $('#TABL_ROL').bootstrapTable('destroy');
    $('#TABL_ROL').bootstrapTable()
        .on('check.bs.table', function (e, row) {
            //Consultamos el elemento seleccionado
            consElem([{name: 'codi', value: row.id.trim()}]);
        });
//        .on('uncheck.bs.table', function (e, row) {
//    });
}