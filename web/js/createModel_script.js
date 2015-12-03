function submitCreateModel()
{
    $("#alert_box").html("Creating model...");
    $("#alert_box").fadeIn("slow");
    $.ajax(
    {
        type: "POST",
        data: $("form").serialize(),
        cache: false,
        url: "createModelProcess",
        dataType: "JSON",
        success: function(response)
        {
            if (response.result != '1')
            {
                $("#alert_box").html(response.error);
            }
            else
                document.location.href = 'modelManagement';
        }
    });
    return (false);
}