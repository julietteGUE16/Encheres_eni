 function upload() {
		var uploadImage = document.getElementById('imageArticle');
		var display = document.getElementById('display');
		display.src=URL.createObjectURL(event.target.files[0]);
	};
	
function minDebutDate() {
		var today = new Date();
	    var minDate = new Date(today.setDate(today.getDate())).toISOString().split("T")[0];
	    document.getElementsByName("debut")[0].setAttribute("min", minDate);
	};
	
function minFinDate() {
		var today = new Date();
		var debut_date_value = document.getElementById("debut").getAttribute("value");
		
		document.getElementById("debut").addEventListener("change", function() {
   			var input = this.value;
    		var dateEntered = new Date(input);
    		console.log("date debut = " + input);
    		
    		document.getElementsByName("fin")[0].removeAttribute("disabled");
	   		var maxDate = new Date(dateEntered.setDate(dateEntered.getDate() + 1)).toISOString().split("T")[0];
    	});	
    	
    	document.getElementById("debut").addEventListener("unload", function() {
   			var input = this.value;
    		var dateEntered = new Date(input);
    		console.log("date debut = " + input);
    		
    		document.getElementsByName("fin")[0].removeAttribute("disabled");
	   		var maxDate = new Date(dateEntered.setDate(dateEntered.getDate() + 1)).toISOString().split("T")[0];
	    	document.getElementsByName("fin")[0].setAttribute("min", maxDate);
    	});		
};


function backButton() {
	document.getElementById("debut").removeAttribute("required");
	document.getElementById("fin").removeAttribute("required");
}