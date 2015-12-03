function submitPirep()
{
    if (confirm("Are you sure to submit the PIREP ?") == false)
        return (false);
    $("#alert_box").html("Submitting PIREP...");
    $("#alert_box").fadeIn("slow");
    $.ajax(
    {
        type: "POST",
        data: $("form").serialize(),
        dataType: "json",
        cache: false,
        url: "sendPirepProcess",
        success: function(response)
        {
            if (response.result != '1')
            {
                $("#alert_box").html(response.error);
            }
            else
                document.location.href = 'flightAssignment';
        }
    });
    return (false);
}