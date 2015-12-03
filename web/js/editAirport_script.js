function submitEditAirport()
{
    $("#alert_box").html("Editing Airport...");
    $("#alert_box").fadeIn("slow");
    $.ajax(
    {
        type: "POST",
        url: "editAirportProcess",
        data: $("form").serialize(),
        dataType: "json",
        success: function(response)
        {
            if (response.result != '1')
                $("#alert_box").html(response.error);
            else
                document.location.href = 'airportManagement';
        }
    });
    return (false);
}