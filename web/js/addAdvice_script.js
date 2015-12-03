$(document).ready(
    function()
    {
        $('#add_advice').click(
            function()
            {
                $('#home_alert').html('Publishing advice...');
                $('#home_alert').fadeIn('slow');
                var form_data = {
                    advice: $('#advice').val()
            };

            $.ajax({
                type: 'POST',
                url: 'addAdviceFormCheck',
                data: form_data,
                dataType: 'json',
                success: function(response)
                {
                    if (response.result != '1')
                    {
                        $('#home_alert').html(response.error);
                        $('#home_alert').fadeIn('slow');
                    }
                    else
                    {
                        $('#home_alert').html("Advice published.");
                        $('#home_alert').fadeIn('slow');
                        document.location.href = 'home';
                    }
                }
            });
                return false;
            }
        );
    }
);