//action="deleteModelProcess"

function submitDeleteModel()
{
    if (confirm("Are you sure to delete model ?") == false)
        return (false);
    $("#alert_box").html("Deleting Model...");
    $("#alert_box").fadeIn("slow");
    $.ajax(
    {
        type: "POST",
        cache: false,
        data: $("form").serialize(),
        dataType: "json",
        url: "deleteModelProcess",
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