var SORT_DIRECTION = {
	ASC: "ASC",
	DESC: "DESC"
};

var AwtfReporterModel = function(data) {
	var self = this;
	self.stepData = ko.observableArray(data.stepData);
	self.tagNameData = ko.observableArray(data.tagNameData);
	self.stepDataFilter = ko.observable("");
	self.tagNameDataFilter = ko.observable("");
	self.highlightAgain = true;

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

	self.filteredStepData = ko.computed(function() {
		self.highlightAgain = true;
		var lowerCaseVal = self.stepDataFilter().toLowerCase();
		if (lowerCaseVal.length == 0) {
			return self.stepData();
		} else {
			return ko.utils.arrayFilter(self.stepData(), function(object) {
				for (key in object) {
					if (String(object[key]).toLowerCase().search(lowerCaseVal) != -1) {
						return true;
					}
				}
				return false;
			});
		}
	});

	self.filteredTagNameData = ko.computed(function() {
		var lowerCaseVal = self.tagNameDataFilter().toLowerCase();
		if (lowerCaseVal.length == 0) {
			return self.tagNameData();
		} else {
			return ko.utils.arrayFilter(self.tagNameData(), function(object) {
				for (key in object) {
					if (String(object[key]).toLowerCase().search(lowerCaseVal) != -1) {
						return true;
					}
				}
				return false;
			});
		}
	});

	// Do Initial Sort
	self.sortData("stepData", "customOrder", SORT_DIRECTION.ASC);
	self.sortData("tagNameData", "tagName", SORT_DIRECTION.ASC);
};

$(function() {
	// Create KO model and apply bindings
	var awtfRepoterModel = new AwtfReporterModel($.parseJSON(awtfReportData));
	ko.applyBindings(awtfRepoterModel);
	/*
	 * Set interval to re-highlight Gherkin code HTML because filtering will
	 * add/remove items from the DOM. When items are added back they won't have
	 * the highlighting on initially
	 */
	setInterval(function() {
		if (awtfRepoterModel.highlightAgain) {
			$("pre code").each(function(i, block) {
				hljs.highlightBlock(block);
			});
			awtfRepoterModel.highlightAgain = false;
		}
	}, 1500);
});