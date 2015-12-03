$(document).ready(
    function()
    {
        $('#edit_team').click(
            function()
            {
                $('#team_alert').html('Editing team...');
                $('#team_alert').fadeIn('slow');
                var form_data = {
                    team_id: $('#team_id').val(),
                    team_name: $('#team_name').val(),
                    aircraft: $('#aircraft').val()
            };

            $.ajax({
                type: 'POST',
                url: 'modifyTeamFormCheck',
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
                        $('#team_alert').html("Team edited.");
                        $('#team_alert').fadeIn('slow');
                        document.location.href = 'manageTeam';
                    }
                }
            });
                return false;
            }
        );
    }
);