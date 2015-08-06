$(function() {
	$("button[value='signOn']").on("click", function(event, data) {
		$("#loginPage").hide();
		$("#mainPage").show();
	});
	
	$("button[value='logout']").on("click", function(event, data) {
		$("#mainPage").hide();
		$("#logoutPage").show();
	});
});