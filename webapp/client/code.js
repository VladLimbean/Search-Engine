
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
			
            $("#responsesize").html("<p>" + data.time + "</p>");
            buffer = "<div>";
            $.each(data.websites, function(index, value) {
				buffer += "<div class=\"g\"><div class=\"rc\">";
				
				//Heading
				buffer += "<h3 class=\"r\"><a href=\"" + value.url + "\">" + value.title + "</a></h3>";
                
				//URL and extract
				buffer += "<div class=\"s\"><div><div class=\"f kv _SWb\"><cite class=\"Rm\"><b>" 
						  + value.url + "</b></cite></div><span class=\"st\">" + value.extract + "</span></div></div>";
				
				buffer += "</div></div>";
            });
            buffer += "</div>";
            $("#urllist").html(buffer);
        });
    });
});
