$(document).ready(function () {
    $('#FORM_ELEM').formValidation({
        framework: 'bootstrap',
        excluded: ':disabled',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            codi: {
                validators: {
                    notEmpty: {
                        message: 'The username is required'
                    }
                }
            },
            nomb: {
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    }
                }
            }
        }
    });
});
