jQuery('#course_search #text_searchQuery_control').ready(function(){
    var sessionId = jQuery("#formInfo").find("[name='sessionId']").val();
    var key = sessionId + '_last_search';
    if(sessionStorage.getItem(key) != null) {
        //Coming back to the search page.  Set the values back to the previous search values, pulled from sessionStorage
        if(jQuery('#text_searchQuery_control').val().length == 0){
            var inputData = JSON.parse( sessionStorage.getItem(key) );
            jQuery('#text_searchQuery_control').val(inputData.searchQuery);
            jQuery("select[name='searchTerm']").val(inputData.searchTerm);
        }
    }
});

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
        'sTitle' : '<span>Code</span>',
        'sClass' : 'ksap-text-nowrap sortable',
        'sWidth' : '78px',
        'sType' : 'string',
        'sKsapColIdSuffix' : '_code'
    }, {
        'bSortable' : true,
        'bSearchable' : true,
        'sTitle' : '<span>Title</span>',
        'sClass' : 'sortable details_link',
        'sWidth' : '225px',
        'sKsapColIdSuffix' : '_title'
    }, {
        'bSortable' : false,
        'bSearchable' : true,
        'sTitle' : '<span>Credits</span>',
        'sWidth' : '68px',
        'sKsapColIdSuffix' : '_credits'
    },{
        'bSortable' : false,
        'bSearchable' : true,
        'sTitle' : '<span>Scheduled Terms</span>',
        'sClass' : 'ksap-data-list',
        'sWidth' : '130px',
        'sKsapColIdSuffix' : '_scheduledTerms'
    },{
        'bSortable' : false,
        'bSearchable' : true,
        'sTitle' : '<span>Projected Terms</span>',
        'sClass' : 'ksap-data-list',
        'sKsapColIdSuffix' : '_projectedTerms'
    }, {
        'bSortable' : false,
        'bSearchable' : true,
        'sTitle' : '<span>Gen Ed</span>',
        'sWidth' : '55px',
        'sKsapColIdSuffix' : '_gened'
    }, {
        'bSortable' : false,
        'bSearchable' : true,
        'sTitle' : '',
        'sClass' : 'ksap-status-column',
        'sWidth' : '41px',
        'sKsapColIdSuffix' : '_status'
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
    //Get the search inputs and save them in sessionStorage for later retrieval
    var inputData = {searchQuery: jQuery('#text_searchQuery_control').val(), searchTerm: jQuery("select[name='searchTerm'] option:selected").val()};
    var sessionId = jQuery("#formInfo").find("[name='sessionId']").val();
    var key = sessionId + '_last_search';
    sessionStorage.setItem( key, JSON.stringify(inputData) );

	var results = jQuery("#" + parentId); // course_search_results_panel
	results.fadeOut("fast");
    setupImages();

    // Display Loading message while waiting on results
	showLoading("Loading...");
    // Load Search Facets
	fnLoadFacets();

    // Load Search Results
	oTable = jQuery("#" + id)
			.dataTable(
					{
						aLengthMenu : [ 20, 50, 100 ],
						aaSorting : [],
						aoColumns : ksapCourseSearchColumns(),
						bAutoWidth : false,
						bDeferRender : true,
						bDestroy : true,
						bJQueryUI : false,
						bProcessing : false,
						bScrollCollapse : true,
						bServerSide : true,
						bSortClasses : false,
						bStateSave : true,     // Turn save state on to allow for saving pagination when moving between pages
                        "fnStateSave": function (oSettings, oData) {
                            jQuery.extend(oData,inputData);
                            sessionStorage.setItem( 'DataTables_SearchQuery', JSON.stringify(oData) );
                        },
                        "fnStateLoad": function (oSettings) {
                            var oData = JSON.parse( sessionStorage.getItem('DataTables_SearchQuery') );
                            if(oData!=null){
                                if(oData.searchQuery!=jQuery('#text_searchQuery_control').val() || oData.searchTerm!=jQuery("select[name='searchTerm'] option:selected").val()){
                                    sessionStorage.removeItem('DataTables_SearchQuery');
                                }
                            }
                            return JSON.parse( sessionStorage.getItem('DataTables_SearchQuery') );
                        },
						iCookieDuration : 600,
						iDisplayLength : 20,
                        fnCreatedRow : function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
                            jQuery(nRow).attr("id", aData[0]).attr("data-display-index", iDisplayIndex);
                            for (var i = 0; i < aData.length; i++) {
                                var idSuffix = ksapCourseSearchColumns()[i].sKsapColIdSuffix;
                                if (idSuffix) {
                                    jQuery(jQuery(nRow).find('td').get(i)).attr("id", aData[0] + idSuffix)
                                }
                            }
                            updateTermsOfferedDisplay(jQuery(nRow));
                            return nRow;
                        },
						fnDrawCallback : function() {
                            // Create search summary line
                            if(this.fnSettings().aoData.length){
                                if (jQuery('#course_search_results_info span[class=search_keyword]').size()==0) {
                                    // fnDrawCallback seems to get called multiple times on occasion, so only append the search_keyword once
                                    var searchKeyword = jQuery('#text_searchQuery_control').val();
                                    jQuery('<span/>', {text: ' for '}).appendTo('#course_search_results_info');
                                    jQuery('<span/>', {text: searchKeyword, class: 'search_keyword'}).appendTo('#course_search_results_info');
                                }
                                jQuery("#course_search_results_facets").removeClass("invisible");
                            }

							if (Math
									.ceil((this.fnSettings().fnRecordsDisplay()) / this.fnSettings()._iDisplayLength) > 1) {
								jQuery(".dataTables_paginate .ui-button").not(
										".first, .last").show();
							} else {
								jQuery(".dataTables_paginate .ui-button")
										.hide();
							}

                            //Hide bottom pagination if there is less than 1 page of results - Check each refresh
                            if (this.fnSettings()._iDisplayLength > this.fnSettings().fnRecordsDisplay()) {
                                jQuery('#course_search_results_wrapper').find('.dataTables_paginate').hide();
                            } else {
                                jQuery('#course_search_results_wrapper').find('.dataTables_paginate').show();
                            }
						},
						fnInitComplete : function(oSettings, json) {
							hideLoading();
							results.fadeIn("fast");
							results.find("table#" + id).width(
									ksapCourseSearchTableWidth());

                            var newheader = jQuery("#termsOfferedPlaceholder").clone(true);
                            var oldheader = jQuery("[aria-label='Projected Terms']");
                            oldheader.empty();
                            newheader.removeClass("ksap-hide");
                            oldheader.append(newheader);

                            newheader = jQuery("#genEdPlaceholder").clone(true);
                            oldheader = jQuery("[aria-label='Gen Ed']");
                            oldheader.empty();
                            newheader.removeClass("ksap-hide");
                            oldheader.append(newheader);

							ksapSearchComplete();

                            //Hide dropdown pagination if there is less than X (default=20) # of items in results - Check once
                            if (this.fnSettings()._iDisplayLength > this.fnSettings().fnRecordsDisplay()) {
                                jQuery('#course_search_results_length').addClass('invisible');
                            } else {
                                jQuery('#course_search_results_length').removeClass('invisible');
                            }

                            results.removeClass("ksap-hide");
						},
						fnServerData : function(sSource, aoData, fnCallback) {
							jQuery
									.ajax({
										dataType : 'json',
										type : "GET",
										url : sSource,
										data : aoData,
                                        success : function(data, textStatus, jqXHR){
                                            if(data.LimitExceeded>0){
                                                var message = jQuery('#searchValidationMessages p');
                                                var text = message.html();
                                                var formatted = text.replace('__KSAP_SEARCH_LIMIT__',data.LimitExceeded+'');
                                                jQuery('#searchValidationMessages p').html(formatted);
                                                jQuery('#searchValidationMessages').removeClass("ksap-hide");
                                            }else{
                                                jQuery('#searchValidationMessages').addClass("ksap-hide");
                                            }
                                            fnCallback(data,textStatus,jqXHR);
                                        },
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
							"sZeroRecords" : "0 results found",
                            "oPaginate":
                            {
                                "sFirst" : "&#8249;",
                                "sPrevious" : "&#8249;",
                                "sNext" : "&#8250;",
                                "sLast" : "&#8250;"
                            }
						},
						sAjaxSource : 'course/search' + ksapGetSearchParams(),
						sCookiePrefix : "ksap_",
						sDom : "ilrtSp",
						sPaginationType : "full_numbers"
					});
}

/**
 * Load initial facet state from the server based on new search criteria.
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
                    var facets = data.oFacetState;
                    if(jQuery.isEmptyObject(facets)){
                        jQuery("#course_search_results_facets").addClass("invisible");
                    }else{
                        jQuery("#course_search_results_facets").removeClass("invisible");
                    }
					jQuery(
							".ksap-facets-group .uif-disclosureContent .uif-boxLayout")
							.each(function() {
								jQuery(this).empty();
							});
                    jQuery.event.trigger("GENERATE_FACETS");
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
		url : 'course/facetValues' + ksapGetSearchParams() + '&fclick=' + encodeURIComponent(sFilter) + '&fcol=' + fcol,
		success : function(data, textStatus, jqXHR) {
			var i = data.oSearchColumn[fcol];
			oFacets = data;
			jQuery.event.trigger("UPDATE_FACETS");
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
function fnGenerateFacetGroup(domObj) {
    var obj = jQuery(domObj);
	var fcol = obj.attr("id");
	var oData = oFacets.oFacetState[fcol];
	var jFacets = obj.find(".uif-disclosureContent");
	jFacets.empty();
	var bOne = false; // exactly one facet value
	var bMore = false; // more than one facet value
    if (oData == null)
        return
	for (key in oData){
		if (bMore)
			continue;
		else if (oData.hasOwnProperty(key))
			bMore = !(bOne = !bOne);
    }
    var bAll = true;
    for ( var key in oData)
        if (!bAll)
            continue;
        else if (oData.hasOwnProperty(key) && !oData[key].checked)
            bAll = false;
	if (bMore) {
		jFacets.append(jQuery('<div class="all"><ul /></div>'));
		var jAll = jQuery('<li />').attr("title", "All").data("facetid", fcol)
            .addClass("all").html('<a href="#">Clear</a>').click(
						function(e) {
							var t = jQuery(this);
							fnClickFacet('All', t.data("facetid"), e);
						});
        if(bAll){
            jAll.addClass("checked")
        }
		jFacets.find(".all ul").append(jAll);
	}
	jFacets.append(jQuery('<div class="facets"><ul /></div>'));
	var ful = jFacets.find(".facets ul");
	for (key in oData) {
		var jItem = jQuery('<li />').attr('title', oData[key].description).data("facetkey", key)
				.data("facetid", fcol).html(
						'<input type="checkbox"/> <a href="#">' + oData[key].value + '</a><span>(' + oData[key].count
								+ ')</span>').click(function(e) {
					var t = jQuery(this);
					fnClickFacet(t.data("facetkey"), t.data("facetid"), e);
				});
        if(fcol == "facet_genedureq" || fcol == "facet_quarter"){
            if(key=="None"){
                jQuery(jItem).addClass("ksap-hide");
            }
        }
        if (!bAll && oData[key].checked){
            jQuery(jItem).addClass("checked");
            jQuery(jItem).find(":checkbox").prop('checked',true);
        }
        else{
            jQuery(jItem).removeClass("checked")
            jQuery(jItem).find(":checkbox").prop('checked',false);
        }
		if (bOne){
			jItem.addClass("static").find(":checkbox").prop('checked',true);
        }
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
function fnUpdateFacetList(domObj) {
	// Update the style on the 'All' facet option (checked if none in the group
	// are selected, not checked if any are selected)
    var obj = jQuery(domObj);
	var bAll = true;
	var oData = oFacets.oFacetState[obj.attr("id")];
	for ( var key in oData)
		if (!bAll)
			continue;
		else if (oData.hasOwnProperty(key) && !oData[key].checked)
			bAll = false;
	if (bAll){
		obj.find("ul li.all").addClass("checked");
        obj.find("ul li.all").find(":checkbox").prop('checked',true);
    }
	else{
		obj.find("ul li.all").removeClass("checked");
        obj.find("ul li.all").find(":checkbox").prop('checked',false);
    }
	// Update the style (checked/not checked) on facet links and the count view
	obj.find("li").not(".all").each(function() {
        if(jQuery(this).hasClass("static")){
            jQuery(this).find(":checkbox").prop('checked',true);
        } else{
		var key = jQuery(this).data("facetkey");
            if (oData.hasOwnProperty(key)) {
                var oFcb = oData[key];
                if (!bAll && oFcb.checked){
                    jQuery(this).addClass("checked");
                    jQuery(this).find(":checkbox").prop('checked',true);
                }
                else{
                    jQuery(this).removeClass("checked");
                    jQuery(this).find(":checkbox").prop('checked',false);
                }
                jQuery(this).find("span").text("(" + oFcb.count + ")");
            } else
                jQuery(this).find("span").text("(0)");
        }
	});
}

//Handle search criteria input and button actions
jQuery(document).on(kradVariables.PAGE_LOAD_EVENT, function(e ) {
    // content of setupCourseSearchCriteriaActions function
    var sb = jQuery('#searchForCourses');
    var ip = jQuery('#text_searchQuery_control');

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
});

// Registering Course Search Results events
//Refactored from KSAP-254 - Remove JS from Bean xml files
function registerCourseSearchResultsEvents(jqObject) {
    jQuery(jqObject)
        .on('PLAN_ITEM_DELETED', function(event, data){
            if (data.category === 'wishlist') {
                ksapBookmarkRemoveOnSearch(data);
            }
        })
        .on('BOOKMARK_ADDED', function(event, data){
            ksapBookmarkAddOnSearch(data);
        })
        .on('PLAN_ITEM_ADDED', function(event, data) {
            ksapPlannerAddPlanItemOnSearch(data);
        });
}

// Registering Course Search Results Facets events
function registerCourseSearchResultsFacetsEvents(jqObjects){
    jQuery(jqObjects).each(function() {
        jQuery(this)
            .on('GENERATE_FACETS', function() {
                fnGenerateFacetGroup(this);
            })
            .on('UPDATE_FACETS', function() {
                fnUpdateFacetList(this);
            });
	});
}

/**
 * Sets up the Projected terms display on the search results data table
 * @param jqObject - The field in the table being setup
 */
function updateTermsOfferedDisplay(jqObject){
    var terms = jQuery('#course_search_results_panel').data('terms-abbrev').split(",");
    // use the column id suffix "_projectedTerms" to find the correct column
    var baseDL = jqObject.find('td[id$=_projectedTerms] dl');
    if (baseDL.has('dd.projected').length < 1) {
        var fixEmptyList = baseDL.append(jQuery('<dd />').addClass("projected"));
        var baseArray = [];
    } else {
        var baseArray = baseDL.find('dd.projected dl dd').map(function(){ return jQuery(this).text(); }).get();
    }
    var newList = jQuery('<dl />');
    for (var i = 0; i < terms.length; i++) {
        var newDD = jQuery('<dd/>').text(terms[i]);
        if (jQuery.inArray(terms[i],baseArray) !== -1) {
            newDD.addClass("ks-ProjectedTerms-term");
        }else{
            newDD.text("--");
            newDD.addClass("ks-ProjectedTerms-term--empty");
        }
        newList.append(newDD);
    }
    baseDL.find('dd.projected').empty().append(newList);
}

/**
 * Handles the dynamic updating when a bookmark is added on the course search page.
 *
 * @param data - Data for the event
 */
function ksapBookmarkAddOnSearch(data){
    // Change status on course search page
    var item = jQuery("#"+data.courseId+"_bookmark_anchor");
    if(item.length){
        item.addClass("ks-fontello-icon-star");
        item.removeClass("ks-fontello-icon-star-empty");
        item.attr('onclick', "deleteBookmarkCourse('', jQuery(this).data('courseid'), event);");
        item.attr('title', "Remove Bookmark");
    }
}

/**
 * Handles the dynamic updating when a bookmark is removed on the course search page.
 *
 * @param data - Data for the event
 */
function ksapBookmarkRemoveOnSearch(data){
    // Change status on course search page
    var item = jQuery("#"+data.courseId+"_bookmark_anchor");
    if(item.length){
        item.removeClass("ks-fontello-icon-star");
        item.addClass("ks-fontello-icon-star-empty");
        item.attr('onclick', "bookmarkCourse(jQuery(this).data('courseid'), event);");
        item.attr('title', "Bookmark");
    }
}

/**
 * Handles the dynamic updating when a plan item is added on the course search page
 * @param data - Data for the event
 */
function ksapPlannerAddPlanItemOnSearch(data){
    // Change status on course search page
    var item = jQuery("#"+data.courseId+"_add_to_plan_anchor");
    if(item.length){
        item.addClass("ks-fontello-icon-ok-circled");
        item.removeClass("ks-fontello-icon-hollow-circled-plus");
        item.attr('title', "Planned");
        item.attr('onclick', null);
    }
}