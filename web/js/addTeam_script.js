$(document).ready(
    function()
    {
        $('#add_team').click(
            function()
            {
                $('#team_alert').html('Registering team...');
                $('#team_alert').fadeIn('slow');
                var form_data = {
                    team_name: $('#team_name').val(),
                    aircraft: $('#aircraft').val()
            };

            $.ajax({
                type: 'POST',
                url: 'addTeamFormCheck',
                data: form_data,
                dataType: 'json',
                success: function(response)
                {
                    if (response.result != '1')
                    {
                        $('#team_alert').html(response.error);
                        $('#team_alert').fadeIn('slow');
                    }
                    else
                    {
                        $('#team_alert').html("Team registered.");
                        $('#team_alert').fadeIn('slow');
                        document.location.href = '';
                    }
                }
            });
                return false;
            }
        );
    }
);