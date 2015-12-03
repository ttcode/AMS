$(document).ready(
    function()
    {
        $('#login-button').click(
            function()
            {
                $('#login_alert').html('Logging, please wait');
                $('#login_alert').fadeIn('slow');
                var form_data = {
                    login: $('#login').val(),
                    password: $('#password').val()
            };

            $.ajax({
                type: 'POST',
                url: 'loginCheck',
                data: form_data,
                dataType: 'json',
                success: function(response)
                {
                    if (response.result != '1')
                    {
                        $('#login_alert').html(response.error);
                        $('#login_alert').fadeIn('slow');
                    }
                    else
                        document.location.href = '';
                }
            });
                return false;
            }
        );
    }
);