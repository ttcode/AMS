$(document).ready(
    function()
    {
        $('#update_flight').click(
            function()
            {
                $('#flight_alert').html('Updating flight...');
                $('#flight_alert').fadeIn('slow');
                var form_data = {
                    flight_number: $('#flight_number').val(),
                    flight_type: $('#flight_type').val(),
                    from: $('#from').val(),
                    to: $('#to').val(),
                    dhour: $('#dhour').val(),
                    dminute: $('#dminute').val(),
                    hour: $('#hour').val(),
                    minute: $('#minute').val(),
                    flight_route: $('#flight_route').val(),
                    repeat_rule: $('#repeat_rule').val()
            };

            $.ajax({
                type: 'POST',
                url: 'updateFlightFormCheck',
                data: form_data,
                dataType: 'json',
                success: function(response)
                {
                    if (response.result != '1')
                    {
                        $('#flight_alert').html(response.error);
                        $('#flight_alert').fadeIn('slow');
                    }
                    else
                    {
                        $('#flight_alert').html("Flight updated.");
                        $('#flight_alert').fadeIn('slow');
                        document.location.href = 'manageFlight';
                    }
                }
            });
                return false;
            }
        );
    }
);