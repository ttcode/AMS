function submitCreateAircraft()
{
    $("#alert_box").html("Creating Aircraft...");
    $("#alert_box").fadeIn("slow");
    $.ajax(
    {
        type: "POST",
        data: $("form").serialize(),
        dataType: "json",
        cache: false,
        url: "CreateAircraftProcess",
        success: function(response)
        {
            if (response.result != '1')
            {
                $("#alert_box").html(response.error);
            }
            else
                document.location.href = 'AircraftManagement';
        }
    });
    return (false);
}