$(function() {
	$("button[value='signOn']").on("click", function(event, data) {
		$("#loginPage").hide();
		$("#mainPage").show();
	});
});