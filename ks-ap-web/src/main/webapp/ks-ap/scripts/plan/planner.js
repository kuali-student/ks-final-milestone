// Javascripts for the KSAP Module Planner Page.

/**
 * Loads the items for the Planner Calendar.
 * If they are already loaded initializes events and base settings for the planner.
 *
 * @param loaded - Boolean on whether the plan items have already been loaded or not
 * @param pageSize - The number of terms to display for each view of the calender.
 */
function ksapLoadPlannerItems(loaded,pageSize) {
    if(!loaded){
        setupImages();
        retrieveComponent('planner_courses_detail','load');
    }else{
        ksapInitializePlannerItems(pageSize);
    }
}

/**
 * Initialize the Carousel settings for the calender and any additional settings or events needed.
 *
 * @param pageSize - The number of terms to display for each view of the calender.
 */
function ksapInitializePlannerItems(pageSize) {
	var detailList = jQuery('#planner_courses_detail_list');
	var detailCols = detailList.find('ul:not(.errorLines) li');
	if (detailCols.length > 0) {
		var iStart = 0;
		if (readUrlHash('planView')) {
			iStart = parseFloat(readUrlHash('planView'));
		} else if (detailList.length > 0) {
			iStart = jQuery("#planner_courses_detail_list").data("focustermindex");
		}

		detailList.jCarouselLite({
			btnNext : '.ksap-carousel-next',
			btnPrev : '.ksap-carousel-prev',
			scroll : pageSize,
			visible : pageSize,
			start : iStart,
			afterEnd : function(a) {
				ksapPlannerUpdateTitle(a);
				var planView = jQuery(a[0]).index();
				if (planView == 0 && a.length < pageSize) {
					planView = a.length - pageSize;
				}
				setUrlHash('planView', planView);
			},
			initCallback : function(a) {
				ksapPlannerUpdateTitle(a);
				jQuery.unblockUI();
			}
		});
	}
    registerClosePopups();
}

/**
 * Updates the calendar title information when the views are changed
 *
 * @param a
 */
function ksapPlannerUpdateTitle(a) {
	var aFirst = jQuery.trim(jQuery(a[0]).find(
			"div:hidden[id^='plan_base_atpId']").text());
	var aLast = jQuery.trim(jQuery(a[a.length - 1]).find(
			"div:hidden[id^='plan_base_atpId']").text());
	jQuery("#planner_courses_detail .ksap-plan-header")
			.html(aFirst + ' - ' + aLast);
}

/**
 * Sets up and opens a dialog in the planner
 *
 * @param pageId - Id for the page being displayed
 * @param action - The controller mapping to use
 * @param methodToCall - The identifier for the method being mapped to
 * @param target - The html object its being opened on
 * @param e - Current event going on.
 */
function ksapPlannerOpenDialog(pageId, action, methodToCall, target, e) {
	var t = jQuery(target);
	var retrieveData = {
		action : action,
		methodToCall : methodToCall,
		learningPlanId : t.data('learningplanid'),
		termId : t.data('termid'),
		planItemId : t.data('planitemid'),
		courseId : t.data('courseid'),
		backup : t.data('backup'),
		uniqueId : t.data('uniqueid'),
		pageId : pageId + "_page"
	};
	var popupOptions = {
		tail : {
			hidden : true
		},
		align : 'top',
		close : true
	};
	jQuery("#popupForm").remove();
	fnClosePopup();
	openPopup(pageId + "_inner", retrieveData, action, {}, popupOptions, e);
	var form = jQuery("#popupForm");
	form.attr("accept-charset", "UTF-8");
}

/**
 * Sets up and submits a dialog to the controller.
 * @param e - Current even going on
 */
function ksapPlannerSubmitDialog(e) {
	var button = jQuery(e.currentTarget);

	button.block({
		message : '<img src="../ks-ap/images/ajaxLoader.gif"/>',
		css : {
			width : '100%',
			border : 'none',
			backgroundColor : 'transparent'
		},
		overlayCSS : {
			backgroundColor : '#fff',
			opacity : 0.6,
			padding : '0px 1px',
			margin : '0px -1px'
		}
	});

	var form = jQuery("#popupForm");
	form.ajaxSubmit({
		data : ksapAdditionalFormData(),
		dataType : 'json',
		success : ksapPlannerUpdateEvent,
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "parsererror")
				textStatus = "JSON Parse Error";
			showGrowl(errorThrown, jqXHR.status + " " + textStatus);
			fnClosePopup();
		},
		complete : function() {
			button.unblock();
		}
	});

}

/**
 * Sets up and executes moving a plan item from on type to another
 *
 * @param backup - Is the item being moved to backup
 * @param target - The html object its being called on
 * @param e - Current event going on
 */
function ksapPlannerUpdateCategory(backup, target, e) {
	var t = jQuery(target);
	var retrieveData = ksapAdditionalFormData({
			methodToCall : "updatePlanItemCategory",
			learningPlanId : t.data('learningplanid'),
			termId : t.data('termid'),
			planItemId : t.data('planitemid'),
			courseId : t.data('courseid'),
			uniqueId : t.data('uniqueid'),
			backup : backup
		});
	var form = jQuery("<form/>")
			.attr("action", "planner")
			.attr("method", "post");
	form.ajaxSubmit({
		data : retrieveData,
		dataType : 'json',
		success : ksapPlannerUpdateEvent,
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "parsererror")
				textStatus = "JSON Parse Error";
			showGrowl(errorThrown, jqXHR.status + " " + textStatus);
			fnClosePopup();
		},
		complete : function() {
			form.remove();
		}
	});
}

/**
 * Routes json responses from the controller executing the corresponding events to change the display of the planner page.
 * @param response - Json string from the controller with events
 * @param textStatus - Text status to display if error occurs
 * @param jqXHR - Page status.
 */
function ksapPlannerUpdateEvent(response, textStatus, jqXHR) {
	if (response.success) {
        // Execute changes to the planner
		for (var key in response) {
			if (!response.hasOwnProperty(key))
				continue;
			var data = response[key];

			if (key == "PLAN_ITEM_ADDED") {
				ksapPlannerAddPlanItem(data);

			} else if (key == "PLAN_ITEM_DELETED") {
				ksapPlannerRemovePlanItem(data);

			} else if (key == "PLAN_ITEM_UPDATED") {
				ksapPlannerUpdatePlanItem(data);

			} else if (key == "TERM_NOTE_UPDATED") {
				ksapPlannerUpdateTermNote(data);

			} else if (key == "UPDATE_NEW_TERM_TOTAL_CREDITS"
					|| key == "UPDATE_OLD_TERM_TOTAL_CREDITS") {
				ksapPlannerUpdateCredits(data);
			} else if (key == "BOOKMARK_ADDED"){
                ksapBookmarkAddItem(data);
            } else if (key == "UPDATE_BOOKMARK_TOTAL"){
                ksapBookmarkUpdateTotal(data);
            }
		}

        // Display success response message in growl message
		if (response.message != null) {
			showGrowl(response.message);
		}
		fnClosePopup();

	} else {
        // Display error response message on dialog
		var feedback = jQuery("#popupForm").find(".ksap-feedback");
		feedback.empty().append("<span/>").text(response.message);
		feedback.addClass("error");
		feedback.removeClass("success");
		feedback.show();
	}
}

/**
 * Adds an html object into the calendar by copying a hidden template copy
 * and filling in placeholders with the needed data
 *
 * @param data - Data for the new object
 */
function ksapPlannerAddPlanItem (data) {
    // Add plan item to the planner calendar
    if(jQuery("#planner_item_template").length){
        var item = jQuery("#planner_item_template").html();
        for (var key in data)
            if (data.hasOwnProperty(key))
                item = eval("item.replace(/__KSAP__"+key.toUpperCase()+"__/gi,'"+data[key]+"')");
        item = item.replace(/id=\"(u\d+)\"/gi,"id=\""+data.uid+"_$1\"");
        var termUid = data.termId.replace(/\./g,'-');
        var itemElement = jQuery("<div/>").html(item);
        if(data.courseNoteRender == 'false'){
            itemElement.find(".coursenote").addClass("invisible");
        }else{
            itemElement.find(".coursenote").removeClass("invisible");
        }
        itemElement.find("input[data-for='"+data.uid+"']")
            .removeAttr("script")
            .attr("name", "script");
        itemElement
                .attr("id", data.uid+"_wrap")
                .attr("class", "uif-group uif-boxGroup uif-verticalBoxGroup ks-plan-Bucket-collection uif-collectionItem uif-boxCollectionItem uif-boxLayoutHorizontalItem")
                .prependTo("." + termUid + ".ksap-term-" + data.category + " .uif-stackedCollectionLayout")
                .css({backgroundColor:"#ffffcc"})
                .hide()
                .fadeIn(250, function() {
                    var bucket = jQuery(".ksap-term-" + data.category + "." + termUid);
                    var unitcell = bucket.find(".ksap-carousel-term-total");
                    unitcell.addClass("ks-plan-Bucket-footer-show");
                    unitcell.removeClass("ks-plan-Bucket-footer-hide");
                })
                .animate({backgroundColor:"#ffffff"}, 1500, function() {
                    runHiddenScripts(data.uid);
                });
        //Set static ids on some element for AFTs
        ksapSetStaticCourseIDs(data.uid + "_code", data.uid + "_courseNote");
    }

    // Change status on the course search page
    if(jQuery("#"+data.courseId+"_status").length){
        var item = jQuery("#"+data.courseId+"_status");
        item.addClass("planned").html("Planned");
    }

    // Hide add to plan button on the course details page
    if(jQuery("#"+data.courseId+"_addPlannedCourse").length){
        var item = jQuery("#"+data.courseId+"_addPlannedCourse");
        item.hide();
    }
}

/**
 * Changes the displayed value for an plan item's html credit text.
 *
 * @param data - Data for the new credit value
 */
function ksapPlannerUpdatePlanItem (data) {
    var item = jQuery("#" + data.uniqueId);
    item.find(".credit span").text(data.credit);
    item.find(".coursenote").attr("title",data.courseNote);
    if(data.courseNoteRender == 'false'){
        item.find(".coursenote").addClass("invisible");
    }else{
        item.find(".coursenote").removeClass("invisible");
    }
}

/**
 * Removes a plan item html object from the calendar
 *
 * @param data - Data needed to removed the object
 */
function ksapPlannerRemovePlanItem (data) {
    jQuery("#" + data.uid).fadeOut(250, function(){
        jQuery(this).remove();
        var bucket = jQuery(".ksap-term-" + data.category + "." + data.termId);
        var unitcell = bucket.find(".ksap-carousel-term-total");
        if (bucket.find(".ks-plan-Bucket-item").length == 0) {
        	unitcell.removeClass("ks-plan-Bucket-footer-show");
        	unitcell.addClass("ks-plan-Bucket-footer-hide");
        }
    });
}

/**
 * Changes the displayed value for a term section's html credit text.
 *
 * @param data - Data needed to removed the object
 */
function ksapPlannerUpdateCredits (data) {
    var planbucket = jQuery(".ksap-term-planned." + data.termId);
    var planunitcell = planbucket.find(".ksap-carousel-term-total");
    planunitcell.find(".credits span.uif-message").fadeOut(250, function() {
        jQuery(this).text(data.totalCredits).fadeIn(250);
    });

    var cartbucket = jQuery(".ksap-term-cart." + data.termId);
    var cartunitcell = cartbucket.find(".ksap-carousel-term-total");
    cartunitcell.find(".credits span.uif-message").fadeOut(250, function() {
        jQuery(this).text(data.cartCredits).fadeIn(250);
    });
}

/**
 * Changes the displayed value for a term's html note text
 *
 * @param data - Data needed to removed the object
 */
function ksapPlannerUpdateTermNote (data) {
    if (data.termNote == null || data.termNote == "") {
        jQuery("#"+data.uniqueId+"_termNote").attr("title","");
        //@TODO: ksap-961 convert to icon font instead of image
        jQuery("#"+data.uniqueId+"_termNote").attr("src","../ks-ap/images/btnAdd.png");
    } else {
        jQuery("#"+data.uniqueId+"_termNote").attr("title",data.termNote);
        //@TODO: ksap-961 convert to icon font instead of image
        jQuery("#"+data.uniqueId+"_termNote").attr("src","../ks-ap/images/iconInfo.png");
    }

}

/**
 * Registers an event handler clicking on the page to close tooltips unless the target is suppose to open one instead.
 */
function registerClosePopups(){
    jQuery(document).on('click', function (e) {
        var tempTarget = (e.target) ? e.target : e.srcElement;
        if (jQuery(tempTarget).parents("div.uif-tooltip").length === 0) {
            hideBubblePopups();
        }
    });
}

/**
 * Blank Javascript for buttons so actionscript is used but nothing happens.
 */
function doNothing(){

}

/**
 * Adds an html object into the bookmark sidebar by copying a hidden template copy
 * and filling in placeholders with the needed data
 *
 * @param data - Data for the new object
 */
function ksapBookmarkAddItem (data) {
    // Add Bookmark sidebar item
    if(jQuery("#bookmark_sidebar_item_template").length){
        var item = jQuery("#bookmark_sidebar_item_template").html();
        for (var key in data)
            if (data.hasOwnProperty(key))
                item = eval("item.replace(/__KSAP__"+key.toUpperCase()+"__/gi,'"+data[key]+"')");
        item = item.replace(/id=\"(u\d+)\"/gi,"id=\""+data.uid+"_$1\"");
        var itemElement = jQuery("<li/>").html(jQuery("<div/>").html(item));
        itemElement.find(".ksap-text-ellipsis-holder").addClass("ksap-text-ellipsis").removeClass("ksap-text-ellipsis-holder");
        itemElement.find("input[data-for='"+data.uid+"']")
            .removeAttr("script")
            .attr("name", "script");
        itemElement
            .prependTo("#bookmark_summary ul")
            .animate({backgroundColor:"#ffffff"}, 1500, function() {
                runHiddenScripts(data.uid);
            });
        truncateField('bookmark_summary');
    }

    // Change status on course search page
    if(jQuery("#"+data.courseId+"_status").length){
        var item = jQuery("#"+data.courseId+"_status");
        item.addClass("bookmarked").html("Bookmarked");
    }

    // Hide bookmark button on course detail page
    if(jQuery("#"+data.courseId+"_addSavedCourse").length){
        var item = jQuery("#"+data.courseId+"_addSavedCourse");
        item.hide();
    }
}

/**
 * Updates the html object containing the total number of bookmarks for the bookmark side bar.y
 *
 * @param data - Data for the new object
 */
function ksapBookmarkUpdateTotal (data) {
    if(jQuery("#bookmark_summary_number").length){
        var item = jQuery("#bookmark_summary_number");
        item.attr("title","View all "+data.bookmarkTotal+" courses in full details");
        item.html("View all "+data.bookmarkTotal+" courses in full details");
    }
}

/**
 * Constructs a static ID for the course code element and replaces the existing one
 *
 * Set static IDs on course code elements in the planner for AFTs to run
 *
 * @param - variable number of IDs of elements to reset to static IDs
 */
function ksapSetStaticCourseIDs(){
    //Set up a loop in case we have more than one thing to set a static ID on
    for ( var i = 0; i < arguments.length; i++ ) {
        var courseCodeJqObj = jQuery("#" + arguments[i]);
        var elementType = arguments[i].split("_")[1]; //XX_code, XX_courseNote, XX_label, etc.
        var termIdFormatted = courseCodeJqObj.data('termid').replace(/\./g,'-').replace(' ', '-');
        var courseCode = courseCodeJqObj.data('coursecode');

        //planner section information from the parent container for unique identification
        //examples: planner_planned... | planner_backup... | planner_completed...
        var collectionType = courseCodeJqObj.closest("div[id^='planner_']").attr('id').split("_")[1];
        var newCourseId = termIdFormatted + "_" + collectionType + "_" + courseCode + "_" + elementType;

        courseCodeJqObj.attr('id', newCourseId);
    }
}

/**
 * Utility function to focus on an element
 *
 * @param jqObject - Element to focus
 */
function focusOnElement(jqObject){
    jqObject.focus();
}

/**
 * Set message length options for planner notes
 *
 * @param jqObject - Element to set options on
 * @param opts - Options to set
 */
function setPlannerNoteMessageLength(jqObject, opts){
    jqObject.characterCount(opts);
}