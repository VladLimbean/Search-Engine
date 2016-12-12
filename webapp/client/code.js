$(document).ready(function() {
    var baseUrl = "http://localhost:8080";

    $("#searchbutton").click(function() {
		//Check input for correctness
		var input = $('#searchbox').val();
		if (input == "") {
			$("#responsesize").html("<p>Input is empty</p>");
			return;
		}
		
        console.log("Sending request for \"" + input + "\" to server.");
		var start = new Date();
        $.ajax({
            method: "GET",
            url: baseUrl + "/search",
            data: {query: input}
        }).success( function (data) { 
            console.log("Received response " + data);
			
			var totalTime = new Date() - start;
			
            $("#responsesize").html("<p>" + data.length + " results found in " + totalTime + " ms</p>");
			
			if (data.length == 0) {
				$("#urllist").html("");
				return;
			}
			
            buffer = "<div class=\"results-container\">";
            $.each(data, function(index, value) {
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
