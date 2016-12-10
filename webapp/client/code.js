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
			
            $("#responsesize").html("<p>" + data.websites.length + " results found in " + data.time + " ms</p>");
            buffer = "<div class=\"results-container\">";
            $.each(data.websites, function(index, value) {
				buffer += "<div class=\"result-entry\">";
				
				//Heading
				buffer += "<a class=\"red-text\" href=\"" + value.url + "\"><h3>" + value.title + "</h3></a>";
                
				//URL
				buffer += "<div class=\"link-text\"><a href=\"" + value.url + "\">" + value.url + "</a></div>";

				//Extracts
				buffer += "<span>" + value.extract + "</span>";
				
				buffer += "</div>";
            });
            buffer += "</div>";
            $("#urllist").html(buffer);
        });
    });
});
