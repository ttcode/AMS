function submitUpdateAircraft()
{
    $("#alert_box").html("Updating Aircraft...");
    $("#alert_box").fadeIn("slow");
    $.ajax(
    {
        type: "POST",
        data: $("form").serialize(),
        dataType: "json",
        cache: false,
        url: "UpdateAircraftProcess",
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