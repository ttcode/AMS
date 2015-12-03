function submitDeleteAircraft()
{
    if (confirm("Are you sure to delete aircraft ?") == false)
        return (false);
    $("#alert_box").html("Deleting Aircraft...");
    $("#alert_box").fadeIn("slow");
    $.ajax(
    {
        type: "POST",
        cache: false,
        data: $("form").serialize(),
        dataType: "json",
        url: "DeleteAircraftProcess",
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