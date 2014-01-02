
var oTable;
var oFacets = new Object();

// Override to redefine course search table width
function ksapCourseSearchTableWidth() {

}

/**
 * Override this method to redefine course search columns.
 * 
 * <p>
 * NOTE: Display fields and search fields do not work on the same data on the
 * back end. There need to be enough columns defined to accommodate the largest
 * of these two lists. Column order for search fields (facets) is internal to
 * CourseSearchController
 * 
 * @returns aoColumns Value for DataTables.
 */
function ksapCourseSearchColumns() {
	return [ {
		'bSortable' : true,
		'bSearchable' : true,
		'sTitle' : 'Code',
		'sClass' : 'ksap-text-nowrap sortable',
		'sWidth' : '73px',
		'sType' : 'string'
	}, {
		'bSortable' : true,
		'bSearchable' : true,
		'sTitle' : 'Course Name',
		'sClass' : 'sortable',
		'sWidth' : '170px'
	}, {
		'bSortable' : false,
		'bSearchable' : true,
		'sTitle' : 'Credits',
		'sWidth' : '34px'
	}, {
		'bSortable' : false,
		'bSearchable' : true,
		'sTitle' : 'Terms Offered',
		'sClass' : 'ksap-data-list',
		'sWidth' : '76px'
	}, {
		'bSortable' : false,
		'bSearchable' : true,
		'sTitle' : 'Gen Edu Req',
		'sWidth' : '66px'
	}, {
		'bSortable' : false,
		'bSearchable' : true,
		'sTitle' : '',
		'sClass' : 'ksap-status-column',
		'sWidth' : '69px'
	} ];
}

function ksapEmptyTable() {
	return '<div class="ksap-course-search-empty"><p>We couldn&#39;t find anything matching your search.</p><p>A few suggestions:</p><ul><li>Check your spelling</li><li>Try a more general search (Any term, ENGL 1xx)</li><li>Use at least 3 characters</li></ul></div>'
}

function ksapGetCampusSelect() {
	var aCampus = new Array();
	jQuery.each(jQuery("input[name='campusSelect']:checked"), function() {
		aCampus.push(jQuery(this).val());
	});
	return aCampus;
}

function ksapGetSearchParams() {
	var sQuery = jQuery("input[name='searchQuery']").val();
	var sTerm = jQuery("select[name='searchTerm'] option:selected").val();
	var bSaved = jQuery("input[name='savedCourses']").val();
	var extra = ksapGetExtraSearchParams();
	var rv = '?searchQuery=' + escape(sQuery) + '&searchTerm=' + sTerm + '&savedCourses=' + bSaved;
	var cs = ksapGetCampusSelect();
	for (var i in cs)
		rv = rv + '&campusSelect=' + cs[i];
	rv = rv + extra + '&time=' + new Date().getTime();
	return rv;
}

function ksapGetExtraSearchParams() {
	return ''; // Override at institution level
}

function ksapSearchComplete() {
	// Override at institution level
}

function ksapSearchReset() {
	// Override at institution level
}

function fnSelectAllCampuses() {
	jQuery.each(jQuery("input[name='campusSelect']"), function() {
		this.checked = true;
	});
}

function fnClearSearch() {
	window.location = "?";
}

function doCourseSearch(crit) {
	jQuery("input#text_searchQuery_control").each(function() {
		jQuery(this).val(crit);
	    jQuery('button.course-search-submit').click();
	});
}

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
	fnLoadFacets();
	oTable = jQuery("#" + id)
			.dataTable(
					{
						aLengthMenu : [ 20, 50, 100 ],
						aaSorting : [[0,'asc']],
						aoColumns : ksapCourseSearchColumns(),
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

								var targetOffset = jQuery("#" + parentId)
										.offset().top;
								jQuery('html,body').animate({
									scrollTop : targetOffset
								}, 250);
						},
						fnInitComplete : function(oSettings, json) {
							oTable.fnDraw();
							hideLoading();
							results.fadeIn("fast");
							results.find("table#" + id).width(
									ksapCourseSearchTableWidth());
							ksapSearchComplete();
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
							"sEmptyTable" : ksapEmptyTable(),
							"sInfo" : "Showing _START_-_END_ of _TOTAL_ results",
							"sInfoEmpty" : "0 results found",
							"sInfoFiltered" : "",
							"sLengthMenu" : "Show _MENU_",
							"sZeroRecords" : "0 results found"
						},
						sAjaxSource : 'course/search' + ksapGetSearchParams(),
						sCookiePrefix : "ksap_",
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
function fnLoadFacets() {
	showLoading("Loading", jQuery("#course_search_results_facets"));
	jQuery
			.ajax({
				dataType : 'json',
				type : "GET",
				url : 'course/facetValues' + ksapGetSearchParams(),
				success : function(data, textStatus, jqXHR) {
					oFacets = data;
					jQuery(
							".ksap-facets-group .uif-disclosureContent .uif-boxLayout")
							.each(function() {
								jQuery(this).empty();
							});
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
function fnClickFacet(sFilter, fcol, e) {
	stopEvent(e);
	jQuery.ajax({
		dataType : 'json',
		type : "GET",
		url : 'course/facetValues' + ksapGetSearchParams() + '&fclick=' + sFilter + '&fcol=' + fcol,
		success : function(data, textStatus, jqXHR) {
			var i = data.oSearchColumn[fcol];
			oFacets = data;
			jQuery.publish("UPDATE_FACETS");
			if (sFilter === 'All')
				oTable.fnFilter('', i, true, false);
			else {
				// Build filter regex query
				var oData = oFacets.oFacetState[fcol];
				var aSelections = [];
				for ( var key in oData)
					if (oData.hasOwnProperty(key)
							&& oData[key].checked === true)
						aSelections.push(key);
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
function fnGenerateFacetGroup(obj) {
	var fcol = obj.attr("id");
	var oData = oFacets.oFacetState[fcol];
	var jFacets = obj.find(".uif-disclosureContent");
	jFacets.empty();
	var bOne = false; // exactly one facet value
	var bMore = false; // more than one facet value
	for (key in oData)
		if (bMore)
			continue;
		else if (oData.hasOwnProperty(key))
			bMore = !(bOne = !bOne);
	if (bMore) {
		jFacets.append(jQuery('<div class="all"><ul /></div>'));
		var jAll = jQuery('<li />').attr("title", "All").data("facetid", fcol)
				.addClass("all checked").html('<a href="#">All</a>').click(
						function(e) {
							var t = jQuery(this);
							fnClickFacet('All', t.data("facetid"), e);
						});
		jFacets.find(".all ul").append(jAll);
	}
	jFacets.append(jQuery('<div class="facets"><ul /></div>'));
	var ful = jFacets.find(".facets ul");
	for (key in oData) {
		var jItem = jQuery('<li />').data("facetkey", key)
				.data("facetid", fcol).html(
						'<a href="#">' + oData[key].value + '</a><span>(' + oData[key].count
								+ ')</span>').click(function(e) {
					var t = jQuery(this);
					fnClickFacet(t.data("facetkey"), t.data("facetid"), e);
				});
		if (bOne)
			jItem.addClass("static");
		ful.append(jItem);
	}
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
function fnUpdateFacetList(obj) {
	// Update the style on the 'All' facet option (checked if none in the group
	// are selected, not checked if any are selected)
	var bAll = true;
	var oData = oFacets.oFacetState[obj.attr("id")];
	for ( var key in oData)
		if (!bAll)
			continue;
		else if (oData.hasOwnProperty(key) && !oData[key].checked)
			bAll = false;
	if (bAll)
		obj.find("ul li.all").addClass("checked");
	else
		obj.find("ul li.all").removeClass("checked");
	// Update the style (checked/not checked) on facet links and the count view
	obj.find("li").not(".all").each(function() {
		var key = jQuery(this).data("facetkey");
		if (oData.hasOwnProperty(key)) {
			var oFcb = oData[key];
			if (!bAll && oFcb.checked)
				jQuery(this).addClass("checked");
			else
				jQuery(this).removeClass("checked");
			jQuery(this).find("span").text("(" + oFcb.count + ")");
		} else
			jQuery(this).find("span").text("(0)");
	});
}

//Handle search criteria input and button actions
//Refactored from KSAP-254 - Remove JS from Bean xml files
function setupCourseSearchCriteriaActions(jqInputObject, jqSubmitButtonObject) {
    var sb = jqSubmitButtonObject;
    var ip = jqInputObject;

    //immediate actions and tests
    ip.focus();

    if (ip.val() != '') {
        sb.attr('disabled', false).removeClass('disabled').click();
    }

    //Register some events
    jQuery(ip).on("change blur keyup", function(e){
        if (jQuery(this).val().length > 0) {
            sb.attr('disabled', false).removeClass('disabled');
        } else {
            sb.attr('disabled', true).addClass('disabled');
        }
    });

    jQuery(ip).on("keypress", function(e){
        var code = (e.keyCode ? e.keyCode : e.which);
        if (code == 13) {
            e.preventDefault();
            jQuery(sb).click();
        }
    });
}

// Registering Course Search Results events
//Refactored from KSAP-254 - Remove JS from Bean xml files
function registerCourseSearchResultsEvents(jqObject) {
    jQuery(jqObject)
        .on('PLAN_ITEM_DELETED', function(event, data){
            if (data.category === 'wishlist') {
                fnRestoreSearchAddButton(data.courseDetails.courseId);
            }
        })
        .on('PLAN_ITEM_ADDED', function(event, data){
            if (data.category === 'wishlist') {
                fnDisplayMessage('Bookmarked', 'bookmarked', data.courseDetails.courseId+'_status', false);
            }
        })
        .on('PLAN_ITEM_ADDED', function(event, data){
            if (data.category === 'planned') {
                fnDisplayMessage('Planned', 'planned', data.courseDetails.courseId+'_status', false);
            }
        })
        .on('PLAN_ITEM_ADDED', function(event, data){
            if (data.category === 'backup') {
                fnDisplayMessage('Planned', 'planned', data.courseDetails.courseId+'_status', false);
            }
        });
}

// Registering Course Search Results Facets events
//Refactored from KSAP-254 - Remove JS from Bean xml files
function registerCourseSearchResultsFacetsEvents(jqObjects){
    jQuery(jqObjects).each(function() {
        jQuery(this)
            .subscribe('GENERATE_FACETS', function() {
                fnGenerateFacetGroup(this);
            })
            .subscribe('UPDATE_FACETS', function() {
                fnUpdateFacetList(this);
            });
	});
}
