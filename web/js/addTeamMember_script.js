$(document).ready(
    function()
    {
        $('#add_team_member').click(
            function()
            {
                $('#team_alert').html('Registering team member...');
                $('#team_alert').fadeIn('slow');
                var form_data = {
                    employee_id: $('#employee_id').val(),
                    team_id: $('#team_id').val()
            };

            $.ajax({
                type: 'POST',
                url: 'addTeamMemberFormCheck',
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
                        $('#team_alert').html("Team member registered.");
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