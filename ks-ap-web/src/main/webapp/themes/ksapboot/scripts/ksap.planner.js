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
            circular:false,
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
        success : ksapAjaxSubmitSuccessCallback,
        error : ksapAjaxSubmitErrorCallback,
        complete : function() {
            form.remove();
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
 *
 *
 * @param id - Id of component menu is being opened on
 * @param getId - Id of the component from the template insert into menu.
 * @param atpId - Id of term menu is being opened in
 * @param e - An object containing data that will be passed to the event handler.
 * @param selector -
 * @param popupClasses -
 * @param popupOptions - Object of settings to pass to the Bubble Popup jQuery Plugin.
 * @param close -
 */
function openMenu(id, getId, atpId, e, selector, popupClasses, popupOptions, close) {
    stopEvent(e);
    if (atpId != null) {
        var openForPlanning = jQuery('input[id^="' + atpId + '_plan_status"]').val()

        if (openForPlanning == "false" && getId != "completed_menu_items") {
            getId = "completed_backup_menu_items"
        }
    }

    var popupBox;
    var target = (e.currentTarget && e.currentTarget != document) ? e.currentTarget : e.srcElement;
    if (selector === null) {
        popupBox = jQuery(target);
    } else {
        popupBox = jQuery(target).parents(selector);
    }

    var popupHtml = jQuery('<div />').attr("id", id + "_popup").attr("class", popupClasses)
        .html(jQuery("#" + getId).html()).wrap("<div>").parent().clone().html();
    jQuery.each(popupBox.data(), function (key, value) {
        popupHtml = eval("popupHtml.replace(/__KSAP__"+key+"__/gi,'"+value+"')");
    });
    var menuPopupOptions = {
        innerHtml:popupHtml,
        openingSpeed:0,
        closingSpeed:0
    };
    var mergedOptions = jQuery.extend(popupOptions, menuPopupOptions);
    var popupOptionsDefaults = getPopupOptionsDefaults();
    var popupSettings = jQuery.extend(popupOptionsDefaults, mergedOptions);

    fnClosePopup();

    popupBox.addClass("uif-tooltip");
    initBubblePopups();
    popupBox.SetBubblePopupOptions(popupSettings, true);
    popupBox.SetBubblePopupInnerHtml(popupSettings.innerHTML, true);
    popupBox.ShowBubblePopup();
    var popupBoxId = popupBox.GetBubblePopupID();
    popupBox.FreezeBubblePopup();

    jQuery("#" + id + "_popup a").each(function () {
        var linkId = jQuery(this).attr("id");
        var nlid = linkId + "_popup_" + id;
        jQuery(this).siblings("input[data-for='" + linkId + "']")
            .removeAttr("script")
            .attr("name", "script")
            .attr("data-for", nlid)
            .val(function (index, value) {
                return value.replace(linkId, nlid);
            });
        jQuery(this).attr("id", nlid);
        jQuery.each(jQuery(target).data(), function (key, value) {
            jQuery("#" + nlid).attr("data-" + key, value);
        });
    });

    //@TODO: ksap-961 Convert to icon font instead of image
    if (close || typeof close === 'undefined') jQuery("#" + popupBoxId + " .jquerybubblepopup-innerHtml").append('<img src="../themes/ksapboot/images/btnClose.png" class="ksap-popup-close"/>');

    runHiddenScripts(id + "_popup");

}

function registerClickOutsideMenu(e){
    jQuery(document).on('click', function(e){
        var tempTarget = (e.target) ? e.target : e.srcElement;
        if(jQuery("div.jquerybubblepopup.jquerybubblepopup-ksap").length === 0){
            return;
        }
        if (jQuery(tempTarget).parents("div.jquerybubblepopup.jquerybubblepopup-ksap").length === 0 && jQuery(tempTarget).parents("div.uif-tooltip").length === 0) {
            fnClosePopup();
        }
    });
}