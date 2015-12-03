function loadData()
{
    if ($("#aircraft_registration").val() != "")
    {
        $.ajax({
            type: "POST",
            url: "MaintenanceData",
            dataType: "json",
            data: {id: $("#aircraft_registration").val()},
            cache: false,
            success: function(data)
            {
                $("#registration").html(data.registration);
                if (data.status == '2')
                    $("#actual_status").html("Maintenance Requested");
                else if (data.status == '2')
                    $("#actual_status").html("Maintenance in Progress");
                $("#actual_comment").html(data.comment);
                $("#info").css("display", "block");
            }
        });
    }
    else
        $("#info").css("display", "none");
}

function submitMaintenance()
{
    $("#alert_box").html("Submitting Maintenance...");
    $("#alert_box").fadeIn("slow");
    $.ajax(
    {
        type: "POST",
        data: $("form").serialize(),
        dataType: "json",
        cache: false,
        url: "MaintenanceProcess",
        success: function(response)
        {
            if (response.result != '1')
            {
                $("#alert_box").html(response.error);
            }
            else
                document.location.href = '';
        }
    });
    return (false);
}