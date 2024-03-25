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
	    var maxDate = new Date(today.setDate(today.getDate() + 1)).toISOString().split("T")[0];
	    document.getElementsByName("fin")[0].setAttribute("min", maxDate);
}