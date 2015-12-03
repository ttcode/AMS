function submitMaintenanceRequest()
{
    if (confirm("Are you sure to submit a maintenance request ?") == false)
        return (false);
    $("#alert_box").html("Submitting Maintenance Request...");
    $("#alert_box").fadeIn("slow");
    $.ajax(
    {
        type: "POST",
        data: $("form").serialize(),
        dataType: "json",
        cache: false,
        url: "maintenanceRequestProcess",
        success: function(response)
        {
            if (response.result != '1')
            {
                $("#alert_box").html(response.error);
            }
            else
                document.location.href = 'maintenanceRequest';
        }
    });
    return (false);
}