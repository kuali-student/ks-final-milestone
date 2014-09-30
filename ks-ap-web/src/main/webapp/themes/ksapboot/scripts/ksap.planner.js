// Javascripts for the KSAP Module Planner Page.

/**
 * Initialize the Carousel settings for the calender and any additional settings or events needed.
 *
 * @param pageSize - The number of terms to display for each view of the calender.
 */
function ksapInitializePlannerItems(pageSize) {
    setupTermNotePopovers();
    setupCourseNotePopovers();

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
            btnNext : '.ksap-carousel-btn-next',
            btnPrev : '.ksap-carousel-btn-prev',
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
            },
            init : function(opts, a) {
                // a is the full list, so need to get the visible list
                ksapPlannerUpdateTitle(a.slice(opts.start, opts.start + opts.visible-1));
            }
        });
    }
}

/**
 * Setup and trigger bootstrap popovers for the term notes.
 */
function setupTermNotePopovers(){
    var popoverOptions = getPopoverOptions();
    jQuery(".filled-termnote").popover(popoverOptions);
}

/**
 * Setup and trigger bootstrap popovers for the course notes.
 */
function setupCourseNotePopovers(){
    var popoverOptions = getPopoverOptions();
    jQuery(".coursenote").popover(popoverOptions);
}

function getPopoverOptions(){
    var popoverOptions = {
        trigger: 'hover',
        container: 'body',
        template: '<div class="popover ksap-tooltip-popover" role="tooltip"><div class="arrow"></div><div class="popover-content"></div></div>',
        placement: 'top'
    };
    return popoverOptions;
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
    jQuery("#planner_courses_detail .ksap-planner-term-year")
        .html(aFirst + ' &ndash; ' + aLast);
    // Remove focus on carousel buttons
    jQuery('.ksap-planner-header a.uif-actionLink').blur();
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

// Registering Course Search Results Facets events
function registerPlannerEvents(jqObjects){
    jQuery(jqObjects)
        .on('PLAN_ITEM_ADDED', function(event, data) {
            ksapPlannerAddPlanItem(data);
        })
        .on('PLAN_ITEM_DELETED', function(event, data) {
            ksapPlannerRemovePlanItem(data);
        })
        .on('PLAN_ITEM_UPDATED', function(event, data) {
            ksapPlannerUpdatePlanItem(data);
        })
        .on('TERM_NOTE_UPDATED', function(event, data) {
            ksapPlannerUpdateTermNote(data);
        })
        .on('UPDATE_NEW_TERM_TOTAL_CREDITS', function(event, data) {
            ksapPlannerUpdateCredits(data);
        })
        .on('UPDATE_OLD_TERM_TOTAL_CREDITS', function(event, data) {
            ksapPlannerUpdateCredits(data);
        })
        .on('BOOKMARK_ADDED', function(event, data) {
            ksapBookmarkAddItem(data);
        });
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
            if (data.hasOwnProperty(key)){
                var value = data[key].replace("\'","\\'");
                item = eval("item.replace(/__KSAP__"+key.toUpperCase()+"__/gi,'"+value+"')");
            }
        item = item.replace(/id=\"(u\d+)\"/gi,"id=\""+data.uid+"_$1\"");
        var termUid = data.termId.replace(/\./g,'-');
        var itemElement = jQuery("<div/>").html(item);
        if(data.courseNoteRender == 'false'){
            itemElement.find(".coursenote").addClass("ksap-hide");
        }else{
            var field = itemElement.find(".coursenote");
            var popoverOptions = getPopoverOptions();
            field.popover(popoverOptions);
            var popover = field.data("popover");
            popover.options.content = field.attr("data-content");
            field.removeClass("ksap-hide");
        }
        if(data.statusMessageRender == 'false'){
            itemElement.find(".ksap-planner-status-message").addClass("ksap-hide");
        }else{
            itemElement.find(".ksap-planner-status-message").removeClass("ksap-hide");
        }

        var addDiv = jQuery(".ksap-planner-section." + termUid + ".ksap-planner-term-" + data.category + " .ksap-planner-add");
        var addDivParent = addDiv.parent();
        itemElement.find("input[data-for='"+data.uid+"']")
            .removeAttr("script")
            .attr("name", "script");
        itemElement
            .attr("id", data.uid+"_wrap")
            .attr("class", "uif-collectionItem uif-boxCollectionItem");
        addDivParent.before(itemElement);
        itemElement.css({backgroundColor:"#ffc"})
            .hide()
            .fadeIn(250, function() {
                var bucket = jQuery("ksap-planner-footer.ksap-planner-term-" + data.category + "." + termUid);
                var unitcell = bucket.find(".ksap-planner-credits-total");
                unitcell.addClass("ks-plan-Bucket-footer-show");
                unitcell.removeClass("ks-plan-Bucket-footer-hide");
            })
            .animate({backgroundColor:"#ffffff"}, 1500, function() {
                itemElement.css({background: "none"});
                runHiddenScripts(data.uid);
            });
        //Set static ids on some element for AFTs
        ksapSetStaticCourseIDs(data.uid + "_code", data.uid + "_courseNote");

        // If there are blank slots remove one
        var blankDivs = jQuery(".ksap-planner-section." + termUid + ".ksap-planner-term-" + data.category + " .ksap-planner-cell-blank");
        if(blankDivs.length){
            blankDivs.first().parent().remove();
        }

    }

    // Change status on the course search page
    if(jQuery("#"+data.courseId+"_status").length){
        var item = jQuery("#"+data.courseId+"_status");
        item.html("Planned");
    }

}

/**
 * Changes the displayed value for an plan item's html credit text.
 *
 * @param data - Data for the new credit value
 */
function ksapPlannerUpdatePlanItem (data) {
    var item = jQuery("#" + data.uniqueId);
    item.find(".ksap-planner-credits p").text(data.credit);
    var field = item.find(".coursenote");

    if(data.courseNoteRender == 'false'){
        field.addClass("ksap-hide");
        if(field.attr("data-content").length){
            field.popover("destroy");
        }
        field.attr("data-content","");
    }else{
        field.attr("data-content",data.courseNote);
        var popoverOptions = getPopoverOptions();
        field.popover(popoverOptions);
        var popover = field.data("popover");
        popover.options.content = field.attr("data-content");
        field.removeClass("ksap-hide");
    }
}

/**
 * Removes a plan item html object from the calendar
 *
 * @param data - Data needed to removed the object
 */
function ksapPlannerRemovePlanItem (data) {
    var target = jQuery("#" + data.uid);
    if(target.length){
        target.fadeOut(250, function(){
            jQuery(this).parent().remove();
        });
    }else{
        if(data.category=="wishlist"){
            var bookmarkList = jQuery("#bookmark_summary");
            var bookmark = bookmarkList.find("[data-planitemid='"+data.planItemId+"'].ksap-bookmark-widget-row");
            bookmark.fadeOut(250, function(){
                // remove contianing li
                jQuery(this).parent("li").remove();
            });
        }
    }
}

/**
 * Changes the displayed value for a term section's html credit text.
 *
 * @param data - Data needed to removed the object
 */
function ksapPlannerUpdateCredits (data) {
    var creditLine = jQuery(".ksap-planner-credits-total." + data.termId);
    creditLine.find(".ksap-planner-credits p.uif-message").fadeOut(250, function() {
        var text = data.totalCredits.replace("&ndash;","-");
        jQuery(this).text(text).fadeIn(250);
    });
}

/**
 * Changes the displayed value for a term's html note text
 *
 * @param data - Data needed to removed the object
 */
function ksapPlannerUpdateTermNote (data) {
    var field = jQuery("#"+data.uniqueId+"_termNote");
    if (data.termNote == null || data.termNote == "") {
        if(field.attr("data-content").length){
            field.popover("destroy");
        }
        field.attr("data-content","");

        field.addClass("empty-termnote");
        field.removeClass("filled-termnote");
        field.attr("title","Click to add notes for "+data.termName);
    } else {
        field.attr("title","");
        field.attr("data-content",data.termNote);
        field.addClass("filled-termnote");
        field.remove("empty-termnote");

        var popoverOptions = getPopoverOptions();
        field.popover(popoverOptions);
        var popover = field.data("popover");
        popover.options.content = field.attr("data-content");
    }

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

    //@TODO: ksap-2006 Convert to icon font instead of image
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