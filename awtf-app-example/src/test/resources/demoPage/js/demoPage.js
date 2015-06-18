$(function() {
	setTimeout(function() {
		$(".load-mask-large, .load-mask-medium, .load-mask-small").hide();
	}, 59000);

	$(".info-icon").on("mouseover", function(event, data) {
		var position = $(this).position();
		var width = $(this).outerWidth();
		$("#infoIconTooltip").css({
			top: position.top + "px",
			left: (position.left + width + 5) + "px"
		});
		$("#infoIconTooltip").show();
	})
	
		$(".help-icon").on("mouseover", function(event, data) {
		var position = $(this).position();
		var width = $(this).outerWidth();
		$("#helpIconTooltip").css({
			top: position.top + "px",
			left: (position.left + width + 5) + "px"
		});
		$("#helpIconTooltip").show();
	})

 $(".info-icon, .help-icon").on("mouseleave", function(event, data) {
	 $(".tooltip").hide();
 });
	
	$("button[value='Show Modal']").on("click", function (event, data) {
		$(".load-mask-large, .load-mask-medium, .load-mask-small").hide();
		showModal($(".modal"), true);
	});

	$("button[value='Close']").on("click", function (event, data){
		showModal($(".modal"), false);
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
  }
  else {
      $(".modal-mask").remove();
      modal.hide();
  }
}