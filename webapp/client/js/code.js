
$(document).ready(function() {
    var baseUrl = "http://localhost:8080";

    $("#searchbutton").click(function() {
        console.log("Sending request to server.");
        $.ajax({
            method: "GET",
            url: baseUrl + "/search",
            data: {query: $('#searchbox').val()}
        }).success( function (data) { 
            console.log("Received response " + data);
            $("#responsesize").html("<p > There are " + data.length/4 + " websites in our secret Intel</p>");
            buffer = "<ul>\n";
            $.each(data, function(index, value) { 
                buffer += "<li>"+value+"</li>\n";
            });
            buffer += "</ul>";
            $("#urllist").html(buffer);
        });
    });
});
