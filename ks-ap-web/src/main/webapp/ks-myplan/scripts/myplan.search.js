// Override to redefine course search table width 
function ksapCourseSearchTableWidth() {
	return 548;
}

// Override to redefine course search columns
function ksapCourseSearchColumns() {
	return [ {
		'aTargets' : [ 0, 1 ],
		'bSortable' : true,
		'bSearchable' : true,
	}, {
		'aTargets' : [ 2, 3, 4 ],
		'bSortable' : false,
		'bSearchable' : true,
	}, {
		'aTargets' : [ 5 ],
		'bSortable' : false,
		'bSearchable' : false,
	}, {
		'aTargets' : [ 0 ],
		'sTitle' : 'Code',
		'sClass' : 'myplan-text-nowrap sortable',
		'sWidth' : '73px',
		'sType' : 'string'
	}, {
		'aTargets' : [ 1 ],
		'sTitle' : 'Course Name',
		'sClass' : 'sortable',
		'sWidth' : '170px'
	}, {
		'aTargets' : [ 2 ],
		'sTitle' : 'Credits',
		'sWidth' : '34px'
	}, {
		'aTargets' : [ 3 ],
		'sTitle' : 'Terms Offered',
		'sClass' : 'myplan-data-list',
		'sWidth' : '76px'
	}, {
		'aTargets' : [ 4 ],
		'sTitle' : 'Gen Edu Req',
		'sWidth' : '66px'
	}, {
		'aTargets' : [ 5 ],
		'sTitle' : '',
		'sClass' : 'myplan-status-column',
		'sWidth' : '69px'
	} ];
}

var oTable;
var oFacets = new Object();

var oProjectedTermOrder = {
	"AU" : 1,
	"FA" : 1,
	"WI" : 2,
	"SP" : 3,
	"SU" : 4
};

var oScheduledTermOrder = {
	"WI" : 1,
	"SP" : 2,
	"SU" : 3,
	"AU" : 4,
	"FA" : 4
};

Object.size = function(obj) {
	var size = 0, key;
	for (key in obj) {
		if (obj.hasOwnProperty(key))
			size++;
	}
	return size;
};

function numeric(a, b) {
	if (parseInt(a) > parseInt(b))
		return 1;
	else if (parseInt(a) < parseInt(b))
		return -1;
	else
		return 0;
}

function alpha(a, b) {
	// Unknown is always last.
	if (a == 'Unknown' || a == 'None')
		return 1;
	if (b == 'Unknown' || b == 'None')
		return -1;
	if (a > b)
		return 1;
	else if (a < b)
		return -1;
	else
		return 0;
}

function terms(a, b) {
	// Unknown is always last.
	if (a == 'Unknown' || a == 'None')
		return 1;
	if (b == 'Unknown' || b == 'None')
		return -1;

	// If the facet items that end with a year are scheduled terms and should
	// precede terms.
	var bYearA = a.match(/.*\d{2}$/gi);
	var bYearB = b.match(/.*\d{2}$/gi);

	// Two scheduled terms.
	if (bYearA && bYearB) {
		var sTermA = a.replace(/\d{2}/gi, "").replace(" ", "").toUpperCase();
		var sTermB = b.replace(/\d{2}/gi, "").replace(" ", "").toUpperCase();
		var iYearA = parseInt(a.replace(/\D*/gi, ""));
		var iYearB = parseInt(b.replace(/\D*/gi, ""));

		if (iYearA != iYearB) {
			if (iYearA < iYearB)
				return -1;
			return 1;
		} else {
			if (oScheduledTermOrder[sTermA] < oScheduledTermOrder[sTermB])
				return -1;
			else if (oScheduledTermOrder[sTermA] > oScheduledTermOrder[sTermB])
				return 1;
			else
				return 0;
		}
	}

	if (bYearA && !bYearB)
		return -1;
	if (!bYearA && bYearB)
		return 1;

	// Two terms.
	if (!bYearA && !bYearB) {
		var sTermA = a.replace("Projected ", "").toUpperCase();
		var sTermB = b.replace("Projected ", "").toUpperCase();
		if (oProjectedTermOrder[sTermA] < oProjectedTermOrder[sTermB])
			return -1;
		else if (oProjectedTermOrder[sTermA] > oProjectedTermOrder[sTermB])
			return 1;
		else
			return 0;
	}
}

jQuery.fn.iterateSorted = function(sorter, print) {
	var keys = [];
	jQuery.each(this[0], function(key) {
		keys.push(key);
	});
	keys.sort(sorter);
	for ( var i = 0; i < keys.length; i++) {
		print(keys[i]);
	}
};

/**
 * Perform a course search.
 * 
 * @param id
 *            The ID of the course search DataTables table element.
 * @param parentId
 *            The ID of the course search panel div element. This element will
 *            be hidden while the initial search is taking place.
 */
function searchForCourses(id, parentId) {
	var results = jQuery("#" + parentId); // course_search_results_panel
	results.fadeOut("fast");
	showLoading("Searching. Please wait...");
	var sQuery = jQuery("input[name='searchQuery']").val();
	var sTerm = jQuery("select[name='searchTerm'] option:selected").val();
	var aCampus = new Array();
	jQuery.each(jQuery("input[name='campusSelect']:checked"), function() {
		aCampus.push(jQuery(this).val());
	});
	fnLoadFacets(sQuery, sTerm, aCampus);
	oTable = jQuery("#" + id)
			.dataTable(
					{
						aLengthMenu : [ 20, 50, 100 ],
						aaSorting : [],
						aoColumnDefs : ksapCourseSearchColumns(),
						bAutoWidth : false,
						bDeferRender : true,
						bDestroy : true,
						bJQueryUI : true,
						bProcessing : false,
						bScrollCollapse : true,
						bServerSide : true,
						bSortClasses : false,
						bStateSave : false,
						iCookieDuration : 600,
						iDisplayLength : 20,
						fnDrawCallback : function() {
							if (Math
									.ceil((this.fnSettings().fnRecordsDisplay())
											/ this.fnSettings()._iDisplayLength) > 1) {
								jQuery(".dataTables_paginate .ui-button").not(
										".first, .last").show();
							} else {
								jQuery(".dataTables_paginate .ui-button")
										.hide();
							}
							if (this.fnSettings()._iDisplayStart != 0
									&& jQuery("#" + parentId).height() > jQuery(
											window).height()) {
								var targetOffset = jQuery("#" + parentId)
										.offset().top;
								jQuery('html,body').animate({
									scrollTop : targetOffset
								}, 250);
							}
						},
						fnInitComplete : function(oSettings, json) {
							oTable.fnDraw();
							hideLoading();
							results.fadeIn("fast");
							results.find("table#" + id).width(
									ksapCourseSearchTableWidth());
						},
						fnServerData : function(sSource, aoData, fnCallback) {
							jQuery
									.ajax({
										dataType : 'json',
										type : "GET",
										url : sSource,
										data : aoData,
										success : fnCallback,
										error : function(jqXHR, textStatus,
												errorThrown) {
											hideLoading();
											showGrowl(textStatus + " "
													+ errorThrown,
													"Search Error");
										},
										statusCode : {
											500 : function() {
												showGrowl(
														"500 Internal Server Error",
														"Fatal Error");
											}
										}
									});
						},
						oLanguage : {
							"sEmptyTable" : '<div class="myplan-course-search-empty"><p class="fl-font-size-130">We couldn&#39;t find anything matching your search.</p><p>A few suggestions:</p><ul><li>Check your spelling</li><li>Try a more general search (Any quarter, ENGL 1xx)</li><li>Use at least 3 characters</li></ul></div>',
							"sInfo" : "Showing _START_-_END_ of _TOTAL_ results",
							"sInfoEmpty" : "0 results found",
							"sInfoFiltered" : "",
							"sLengthMenu" : "Show _MENU_",
							"sZeroRecords" : "0 results found"
						},
						sAjaxSource : 'course/search?queryText='
								+ escape(sQuery) + '&termParam=' + sTerm
								+ '&campusParam=' + aCampus + '&time='
								+ new Date().getTime(),
						sCookiePrefix : "myplan_",
						sDom : "ilrtSp",
						sPaginationType : "full_numbers"
					});
}

/**
 * Load initial facet state from the server based on new search criteria.
 * 
 * @param sQuery
 *            Search criteria.
 * @param sTerm
 *            Term selection.
 * @param aCampus
 *            Campus selections.
 */
function fnLoadFacets(sQuery, sTerm, aCampus) {
	showLoading("Loading", jQuery("#course_search_results_facets"));
	jQuery
			.ajax({
				dataType : 'json',
				type : "GET",
				url : 'course/facetValues?queryText=' + escape(sQuery)
						+ '&termParam=' + sTerm + '&campusParam=' + aCampus,
				success : function(data, textStatus, jqXHR) {
					oFacets = data;
					jQuery(
							".myplan-facets-group .uif-disclosureContent .uif-boxLayout")
							.each(function() {
								jQuery(this).empty();
							});
					if (oFacets.aFacetState.length > 0)
						jQuery.publish("GENERATE_FACETS");
					hideLoading(jQuery("#course_search_results_facets"));
				},
				error : function(jqXHR, textStatus, errorThrown) {
					hideLoading(jQuery("#course_search_results_facets"));
					showGrowl(textStatus + " " + errorThrown, "Search Error");
				},
				statusCode : {
					500 : function() {
						showGrowl("500 Internal Server Error", "Fatal Error");
					}
				}
			});
}

/**
 * Send a facet click event to the server, and receive an updated facet state in
 * oFacets.
 * 
 * @param sFilter
 *            The facet key that was clicked.
 * @param i
 *            The facet column index.
 * @param e
 *            The JS click event.
 */
function fnClickFacet(sFilter, i, e) {
	stopEvent(e);
	jQuery.ajax({
		dataType : 'json',
		type : "GET",
		url : 'course/facetValues?queryText=' + escape(oFacets.sQuery)
				+ '&termParam=' + oFacets.sTerm + '&campusParam='
				+ oFacets.aCampus + '&fclick=' + sFilter + '&fcol=' + i,
		success : function(data, textStatus, jqXHR) {
			oFacets = data;
			if (oFacets.aFacetState.length > 0)
				jQuery.publish("UPDATE_FACETS");
			if (sFilter === 'All')
				oTable.fnFilter('', i, true, false);
			else {
				// Build filter regex query
				var ofl = oFacets.aFacetState[i];
				var aSelections = [];
				for ( var key in ofl)
					if (ofl.hasOwnProperty(key) && ofl[key].checked === true)
						aSelections.push(";" + key + ";");
				oTable.fnFilter(aSelections.join('|'), i, true, false);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			showGrowl(textStatus + " " + errorThrown, "Search Error");
		},
		statusCode : {
			500 : function() {
				showGrowl("500 Internal Server Error", "Fatal Error");
			}
		}
	});
}

/**
 * GENERATE_FACETS event handler.
 * 
 * <p>
 * This event is sent to all facet groups after receiving initial facet state
 * from the server in conjunction with a search.
 * </p>
 * 
 * @param i
 *            The facet columns index.
 * @param obj
 *            A handle to the facet group div element.
 * @param sorter
 *            Function to use for sorting facet keys.
 */
function fnGenerateFacetGroup(i, obj, sorter) {
	var oData = oFacets.aFacetState[i];
	var jFacets = obj.find(".uif-disclosureContent .uif-boxLayout");
	if (Object.size(oData) > 1) {
		jFacets.append(jQuery('<div class="all"><ul /></div>'));
		var jAll = jQuery('<li />').attr("title", "All")
				.addClass("all checked").html('<a href="#">All</a>').click(
						function(e) {
							fnFacetFilter('All', i, e);
						});
		jFacets.find(".all ul").append(jAll);
	}
	jFacets.append(jQuery('<div class="facets"><ul /></div>'));
	jQuery(oData).iterateSorted(
			sorter,
			function(key) {
				var jItem = jQuery('<li />').data("facetkey", key).html(
						'<a href="#">' + key + '</a><span>(' + oData[key].count
								+ ')</span>').click(function(e) {
					fnClickFacet(key, i, e);
				});
				if (Object.size(oData) == 1)
					jItem.addClass("static");
				jFacets.find(".facets ul").append(jItem);
			});
}

/**
 * UPDATE_FACETS event handler.
 * 
 * <p>
 * This event is sent to all facet groups after receiving updated facet state
 * from the server.
 * </p>
 * 
 * @param i
 *            The facet column index.
 * @param obj
 *            A handle to the facet group div element.
 */
function fnUpdateFacetList(i, obj) {
	// Update the style on the 'All' facet option (checked if none in the group
	// are selected, not checked if any are selected)
	var bAll = true;
	var ofl = oFacets.aFacetState[i];
	for ( var key in ofl)
		if (!bAll)
			continue;
		else if (ofl.hasOwnProperty(key) && !ofl[key].checked)
			bAll = false;
	if (bAll)
		obj.find("ul li.all").addClass("checked");
	else
		obj.find("ul li.all").removeClass("checked");
	// Update the style (checked/not checked) on facet links and the count view
	obj.find("li").not(".all").each(function() {
		var key = jQuery(this).data("facetkey");
		if (ofl.hasOwnProperty(key)) {
			var oFcb = ofl[jQuery(this).data("facetkey")];
			if (!bAll && oFcb.checked)
				jQuery(this).addClass("checked");
			else
				jQuery(this).removeClass("checked");
			jQuery(this).find("span").text("(" + oFcb.count + ")");
		} else
			jQuery(this).find("span").text("(0)");
	});
}
