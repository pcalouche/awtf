$(function() {
	$("button[value=toggleErrorMessages]").on("click", function(event, data) {
		$(".error").toggle();
	});

	$("button[value=startLoadMasks]").on("click", function(event, data) {
		var button = $(this);
		button.hide();
		$(".load-mask-large, .load-mask-medium, .load-mask-small").show();
		setTimeout(function() {
			$(".load-mask-large, .load-mask-medium, .load-mask-small").hide();
			button.show();
		}, 59000);
	});

	$("div.nav").on("click", "li a", function(event, data) {
		$("#navClickResult").html("You clicked " + $(this).text() + "!");
	});

	$(".info-icon").on("mouseover", function(event, data) {
		var position = $(this).position();
		var width = $(this).outerWidth();
		$("#infoIconTooltip").css({
			top: String(position).top + "px",
			left: String(position.left + width + 5) + "px"
		});
		$("#infoIconTooltip").show();
	});

	$(".help-icon").on("mouseover", function(event, data) {
		var position = $(this).position();
		var width = $(this).outerWidth();
		$("#helpIconTooltip").css({
			top: String(position).top + "px",
			left: String(position.left + width + 5) + "px"
		});
		$("#helpIconTooltip").show();
	});

	$(".info-icon, .help-icon").on("mouseleave", function(event, data) {
		$(".tooltip").hide();
	});

	$("button[value='Show Modal']").on("click", function(event, data) {
		$(".load-mask-large, .load-mask-medium, .load-mask-small").hide();
		showModal($(".modal"), true);
	});

	$("button[value='Close']").on("click", function(event, data) {
		showModal($(".modal"), false);
	});

	$("div.row-expander").on("click", function(event, data) {
		var div = $(this);
		if (div.hasClass("expanded")) {
			div.removeClass("expanded").addClass("collapsed");
			div.parents("tr").next("tr").addClass("hidden-row");
		} else {
			div.removeClass("collapsed").addClass("expanded");
			div.parents("tr").next("tr").removeClass("hidden-row");
		}
	});
});

function showModal(modal, show) {
	if (show) {
		var modalMask = $("<div></div>").addClass("modal-mask");
		$("body").prepend(modalMask);
		/* Reset modal position on show in it was dragged on previous show */
		modal.css("top", "120px");
		modal.css("left", "50%");
		modal.show();
	} else {
		$(".modal-mask").remove();
		modal.hide();
	}
}