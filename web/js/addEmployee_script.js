$(document).ready(
    function()
    {
        $('#add_employee').click(
            function()
            {
                $('#employee_alert').html('Registering employee...');
                $('#employee_alert').fadeIn('slow');
                var form_data = {
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
                url: 'addEmployeeFormCheck',
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
                        $('#employee_alert').html("Employee registered.");
                        $('#employee_alert').fadeIn('slow');
                        document.location.href = '';
                    }
                }
            });
                return false;
            }
        );
    }
);