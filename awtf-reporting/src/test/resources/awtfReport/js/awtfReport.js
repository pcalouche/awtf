var SORT_DIRECTION = {
	ASC: "ASC",
	DESC: "DESC"
};

var AwtfReporterModel = function(data) {
	var self = this;
	self.stepData = ko.observableArray(data.stepData);
	self.tagNameData = ko.observableArray(data.tagNameData);

	self.handleSortFilterClick = function(dataToSort, fieldToSortOn, data, event) {
		var element = $(event.currentTarget);
		if (element.hasClass("inactive")) {
			element.parents("tr").find("i").addClass("inactive fa-sort");
			element.parents("tr").find("i").removeClass("fa-sort-asc fa-sort-desc");
			element.removeClass("inactive fa-sort").addClass("fa-sort-asc");
			self.sortData(dataToSort, fieldToSortOn, SORT_DIRECTION.ASC);
		} else if (element.hasClass("fa-sort-asc")) {
			element.removeClass("fa-sort-asc").addClass("fa-sort-desc");
			self.sortData(dataToSort, fieldToSortOn, SORT_DIRECTION.DESC);
		} else if (element.hasClass("fa-sort-desc")) {
			element.removeClass("fa-sort-desc").addClass("fa-sort-asc");
			self.sortData(dataToSort, fieldToSortOn, SORT_DIRECTION.ASC);
		}
	}

	self.sortData = function(dataToSort, fieldToSortOn, sortDirection) {
		if (sortDirection == SORT_DIRECTION.ASC) {
			self[dataToSort].sort(function(a, b) {
				return a[fieldToSortOn] < b[fieldToSortOn] ? -1 : 1;
			})
		} else if (sortDirection == SORT_DIRECTION.DESC) {
			self[dataToSort].sort(function(a, b) {
				return b[fieldToSortOn] < a[fieldToSortOn] ? -1 : 1;
			});
		}
	}
	// Do Initial Sort
	self.sortData("stepData", "step", SORT_DIRECTION.ASC);
	self.sortData("tagNameData", "tagName", SORT_DIRECTION.ASC);
};

$(function() {
	$.ajax({
		url: "./data/awtfReport.json",
		type: "get",
		dataType: "json",
	}).done(function(data, textStatus, jqXHR) {
		var awtfRepoterModel = new AwtfReporterModel(data);
		ko.applyBindings(awtfRepoterModel);
		hljs.initHighlighting();
	}).fail(function(data, textStatus, jqXHR) {
		console.error("Failed to load reporter data");
	});
});