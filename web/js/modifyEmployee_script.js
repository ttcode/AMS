$(document).ready(
    function()
    {
        $('#edit_employee').click(
            function()
            {
                $('#employee_alert').html('Editing employee...');
                $('#employee_alert').fadeIn('slow');
                var form_data = {
                    id : $('#id').val(),
                    account_type: $('#account_type').val(),
                    login: $('#login').val(),
                    password: $('#password').val(),
                    name: $('#name').val(),
                    phone_number: $('#phone_number').val(),
                    address: $('#address').val(),
                    job: $('#job').val(),
                    hub: $('#hub').val(),
                    salary: $('#salary').val(),
                    localisation: $('#localisation').val()
            };

            $.ajax({
                type: 'POST',
                url: 'modifyEmployeeFormCheck',
                data: form_data,
                dataType: 'json',
                success: function(response)
                {
                    if (response.result != '1')
                    {
                        $('#employee_alert').html(response.error);
                        $('#employee_alert').fadeIn('slow');
                    }
                    else
                    {
                        $('#employee_alert').html("Employee edited.");
                        $('#employee_alert').fadeIn('slow');
                        document.location.href = 'manageEmployee';
                    }
                }
            });
                return false;
            }
        );
    }
);