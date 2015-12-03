$(document).ready(
    function()
    {
        $('#add_news').click(
            function()
            {
                $('#home_alert').html('Publishing news...');
                $('#home_alert').fadeIn('slow');
                var form_data = {
                    title: $('#title').val(),
                    content: $('#content').val()
            };

            $.ajax({
                type: 'POST',
                url: 'addNewsFormCheck',
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
                        $('#home_alert').html("News published.");
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