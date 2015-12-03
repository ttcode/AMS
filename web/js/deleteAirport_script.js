function submitDeleteAirport(icao)
{
    if (confirm("Are you sure to delete '" + icao + "' ?") == false)
        return (false);
    $("#alert_box").html("Deleting Airport...");
    $("#alert_box").fadeIn("slow");
    var form_data = {icao: icao};
    $.ajax(
    {
        type: "POST",
        url: "deleteAirportProcess",
        data: form_data,
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