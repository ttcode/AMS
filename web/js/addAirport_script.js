function submitAddAirport()
{
    $("#alert_box").html("Creating Airport...");
    $("#alert_box").fadeIn("slow");
    $.ajax(
    {
        type: "POST",
        data: $("form").serialize(),
        cache: false,
        url: "addAirportProcess",
        dataType: "json",
        success: function(response)
        {
            if (response.result != '1')
            {
                $("#alert_box").html(response.error);
            }
            else
                document.location.href = 'airportManagement';
        }
    });
    return (false);
}