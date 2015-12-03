function submitUpdateModel()
{
    $("#alert_box").html("Updating Model...");
    $("#alert_box").fadeIn("slow");
    $.ajax(
    {
        type: "POST",
        data: $("form").serialize(),
        dataType: "json",
        cache: false,
        url: "updateModelProcess",
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