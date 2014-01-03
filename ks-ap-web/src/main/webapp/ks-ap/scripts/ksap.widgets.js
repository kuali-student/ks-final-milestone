/**
 *  This function may be redefined to add additional inputs to forms before posting
 */
function ksapAddPostOptionsToForm(tempForm) {
    return tempForm;
}

/**
 * Override at the institution level to specify additional parameters to append
 * to the query string for in-KSAP navigation links.
 */
function ksapAddGetParameters() {
    return '';
}

/**
 * Override at the institution level to specify additional parameters to append
 * to the query string for linking to other non-KSAP applicaitons in the same
 * enterprise environment.
 */
function ksapAddGetParametersExternal() {
    return '';
}

/**
 * Used to append additional data for an institution when calling back to the controller
 *
 * @param additionalData
 * @return {*}
 */
function ksapAdditionalFormData(additionalData) {
    return additionalData;
}

/**
 * Used to read appended information in the url
 * @param key
 * @return {*}
 */
function readUrlHash(key) {
    if (window.location.href.split("#")[1]) {
        var aHash = window.location.href.split("#")[1].replace('#', '').split('&');
        var oHash = {};
        jQuery.each(aHash, function (index, value) {
            oHash[value.split('=')[0]] = value.split('=')[1];
        });
        if (oHash[key]) {
            if (decodeURIComponent(oHash[key]) == "true" || decodeURIComponent(oHash[key]) == "false") {
                return (decodeURIComponent(oHash[key]) == "true");
            } else {
                return decodeURIComponent(oHash[key]);
            }
        } else {
            return false;
        }
    } else {
        return false;
    }
}

/**
 * Used to append information to the url
 * @param key
 * @param value
 */
function setUrlHash(key, value) {
    var aHash = [];
    if (window.location.href.split("#")[1]) {
        aHash = window.location.href.split("#")[1].replace('#', '').split('&');
    }
    var oHash = {};
    if (aHash.length > 0) {
        jQuery.each(aHash, function (index, value) {
            oHash[decodeURIComponent(value.split('=')[0])] = decodeURIComponent(value.split('=')[1]);
        });
        var oTemp = {};
        oTemp[key] = value;
        jQuery.extend(oHash, oTemp);
    } else {
        oHash[key] = value;
    }
    aHash = [];
    for (var key in oHash) {
        if (key !== "" && oHash[key] !== "") aHash.push(encodeURIComponent(key) + "=" + encodeURIComponent(oHash[key]));
    }
    window.location.replace("#" + aHash.join("&"));
}

/**
 *  This is for DOM changes to refresh the view on back to keep the view updated
 */
if (readUrlHash("modified")) {
    var url = window.location.href;
    var aHash = window.location.href.split("#")[1].replace("#", "").split("&");
    aHash.splice(aHash.indexOf("modified=true"), 1);
    window.location.assign(url.split("#")[0] + ((aHash.length > 0) ? "#" + aHash.join("&") : ""));
}

/**
 * Stops all window events in progress.
 * @param e
 * @return {boolean}
 */
function stopEvent(e) {
    if (!e) var e = window.event;
    if (e.stopPropagation) {
        e.preventDefault();
        e.stopPropagation();
    } else {
        e.returnValue = false;
        e.cancelBubble = true;
    }
    return false;
}

/**
 * Open a course item popup by id, mostly used in linking course codes in audit reports and course reqs text (course linkifier)
 *
 * @param courseId - GUID string of course to open in popup
 * @param e - An object containing data that will be passed to the event handler.
 */
function openCourse(courseId, e) {
    stopEvent(e);
    var target = (e.currentTarget) ? e.currentTarget : e.srcElement;
    if (jQuery(target).parents(".jquerypopover.jquerypopover-ksap").length > 0) {
        window.location = "inquiry?methodToCall=start&viewId=CourseDetails-InquiryView&courseId=" + courseId;
    } else {
        var retrieveData = {action: "plan", viewId: "PlannedCourse-FormView", methodToCall: "startAddPlannedCourseForm", courseId: courseId};
        var popupStyle = {width: "300px", height: "16px"};
        var popupOptions = {tail: {align: "left"}, align: "left", position: "bottom", close: true};
        openPopup("add_remove_course_popover_page", retrieveData, "plan", popupStyle, popupOptions, e);
    }
}

/**
 *
 *
 * @param id -
 * @param getId - Id of the component from the separate view to select to insert into popup.
 * @param atpId -
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

    var popupOptionsDefault = {
        innerHtml:popupHtml,
        themePath:getConfigParam("kradUrl")+"/../ks-ap/scripts/jquery-popover/jquerypopover-theme/",
        manageMouseEvents:false,
        selectable:true,
        tail:{align:'middle', hidden:false},
        position:'left',
        align:'center',
        alwaysVisible:false,
        themeMargins:{total:'20px', difference:'5px'},
        themeName:'ksap',
        distance:'0px',
        openingSpeed:0,
        closingSpeed:0
    };

    var popupSettings = jQuery.extend(popupOptionsDefault, popupOptions);

    fnCloseAllPopups();

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

    if (close || typeof close === 'undefined') jQuery("#" + popupBoxId + " .jquerybubblepopup-innerHtml").append('<img src="../ks-ap/images/btnClose.png" class="ksap-popup-close"/>');

    runHiddenScripts(id + "_popup");

    jQuery(document).on('click', function (e) {
        var tempTarget = (e.target) ? e.target : e.srcElement;
        if (jQuery(tempTarget).parents("div.jquerybubblepopup.jquerybubblepopup-ksap").length === 0 && jQuery(tempTarget).parents("div.uif-tooltip").length === 0) {
            popupBox.HideBubblePopup();
            fnCloseAllPopups();
        }
    });
}

/**
 *   Gathers information for submission to the controller via ajax
 *
 * @param data - Variables and data to be submitted to the controller
 * @param successCallback - Code block to run after a successful return from the controller
 * @param elementToBlock - The html object being effected by the controller call
 * @param formId - Id of the form the submit is being called on
 * @param blockingSettings - Settings for the html object
 */
function ksapAjaxSubmitForm(data, successCallback, elementToBlock, formId, blockingSettings) {
    data = ksapAdditionalFormData(data);

    var submitOptions = {
        data:data,
        success:function (response) {
            var tempDiv = document.createElement('div');
            tempDiv.innerHTML = response;
            var hasError = checkForIncidentReport(response);
            if (!hasError) successCallback(tempDiv);
            jQuery("#formComplete").empty();
        },
        error:function(jqXHR, textStatus,
                       errorThrown) {
            hideLoading();
            showGrowl(textStatus + " "
                + errorThrown,
                "Error");
        },
        statusCode : {
            400 : function() {
                showGrowl(
                    "400 Bad Request",
                    "Fatal Error");
            },
            500 : function() {
                showGrowl(
                    "500 Internal Server Error",
                    "Fatal Error");
            }
        }
    };

    if (elementToBlock != null && elementToBlock.length) {
        var elementBlockingOptions = {
            beforeSend:function () {
                if (elementToBlock.hasClass("unrendered")) {
                    elementToBlock.append('<img src="' + getConfigParam("kradImageLocation") + 'loader.gif" alt="Loading..." /> Loading...');
                    elementToBlock.show();
                }
                else {
                    var elementBlockingDefaults = {
                        baseZ:500,
                        message:'<img src="../ks-ap/images/ajaxLoader16.gif" alt="loading..." />',
                        fadeIn:0,
                        fadeOut:0,
                        overlayCSS:{
                            backgroundColor:'#fff',
                            opacity:0
                        },
                        css:{
                            border:'none',
                            width:'16px',
                            top:'0px',
                            left:'0px'
                        }
                    };
                    elementToBlock.block(jQuery.extend(elementBlockingDefaults, blockingSettings));
                }
            },
            complete:function () {
                elementToBlock.unblock();
            },
            error:function(jqXHR, textStatus,
                           errorThrown) {
                hideLoading();
                showGrowl(textStatus + " "
                    + errorThrown,
                    "Error");
                if (elementToBlock.hasClass("unrendered")) {
                    elementToBlock.hide();
                }
                else {
                    elementToBlock.unblock();
                }
            }
        };
    }
    jQuery.extend(submitOptions, elementBlockingOptions);
    var form = jQuery("#" + ((formId) ? formId : "kualiForm"));
    form.ajaxSubmit(submitOptions);
}

/**
 * Used to position dialog popups
 *
 * @param popupBoxId
 */
function fnPositionPopUp(popupBoxId) {
    if (parseFloat(jQuery("#" + popupBoxId).css("top")) < 0 || parseFloat(jQuery("#" + popupBoxId).css("left")) < 0) {
        var top = (document.documentElement && document.documentElement.scrollTop) || document.body.scrollTop;
        var left = (document.documentElement && document.documentElement.scrollLeft) || document.body.scrollLeft;
        var iTop = ( top + ( jQuery(window).height() / 2 ) ) - ( jQuery("#" + popupBoxId).height() / 2 );
        var iLeft = ( left + ( jQuery(window).width() / 2 ) ) - ( jQuery("#" + popupBoxId).width() / 2 );
        jQuery("#" + popupBoxId).css({top:iTop + 'px', left:iLeft + 'px'});
    }
}

/*
 ######################################################################################
 Function: Retrieve component content through ajax
 ######################################################################################
 */
function ksapRetrieveComponent(id, getId, methodToCall, action, retrieveOptions, highlightId, elementBlockingSettings) {
    var tempForm = '<form id="' + id + '_form" action="' + action + '" method="post" style="display:none;">';
    jQuery.each(retrieveOptions, function (name, value) {
        tempForm += '<input type="hidden" name="' + name + '" value="' + value + '" />';
        tempForm = ksapAddPostOptionsToForm(tempForm);
    });
    tempForm += '</form>';
    jQuery("body").append(tempForm);

    var elementToBlock = jQuery("#" + id);

    var updateRefreshableComponentCallback = function (htmlContent) {
        var component = jQuery("#" + getId, htmlContent);

        // replace component
        if (jQuery("#" + id).length) {
            jQuery("#" + id).replaceWith(component);
        }

        runHiddenScripts(getId);

        if (jQuery("input[data-role='script'][data-for='" + getId + "']", htmlContent).length > 0) {
            eval(jQuery("input[data-role='script'][data-for='" + getId + "']", htmlContent).val());
        }

        if (highlightId) {
            jQuery("[id^='" + highlightId + "']").animate({backgroundColor: "#faf5ca"}, 1).animate({backgroundColor: "#ffffff"}, 1500, function () {
                jQuery(this).removeAttr("style");
            });
        }

        elementToBlock.unblock();
    };

    if (!methodToCall) {
        methodToCall = "search";
    }
    var additionalData = {
        reqComponentId:id,
        skipViewInit:"false",
        methodToCall: methodToCall,
        renderFullView: false
    }
    var formId = id + "_form";
    ksapAjaxSubmitForm(additionalData, updateRefreshableComponentCallback, elementToBlock, formId, elementBlockingSettings);
    jQuery("form#" + id + "_form").remove();
}

/*
 ######################################################################################
 Function:   Truncate (ellipse) a single horizontally aligned item so all items
 fit on one line.  Used for bookmark side bar
 ######################################################################################
 */
function truncateField(id, floated) {
    jQuery("#" + id + " .uif-horizontalFieldGroup").each(function () {
        var itemSelector = ".uif-horizontalBoxGroup > .uif-horizontalBoxLayout > .uif-boxLayoutHorizontalItem";
        var ellipsisItem = jQuery(this).find(itemSelector + ".ksap-text-ellipsis");
        if (ellipsisItem.length != 0) {
            jQuery(this).css("display", "block");
            var fixed = 0;
            jQuery(this).find(itemSelector + ":not(.ksap-text-ellipsis)").each(function () {
                fixed = fixed + jQuery(this).outerWidth(true);
            });
            var available = jQuery(this).width() - ( fixed + ( ellipsisItem.outerWidth(true) - ellipsisItem.width() ) + 1 );
            ellipsisItem.css("white-space", "nowrap");
            if (!floated) {
                ellipsisItem.width(available);
            } else {
                if (ellipsisItem.width() >= available) {
                    ellipsisItem.width(available);
                }
            }
        }
    });
}

/**
 * Registers events to close a popup if user clicks outside it.
 *
 * @param popoverId
 * @param element
 */
function clickOutsidePopOver(popoverId, element) {
    jQuery("body").on("click", function (e) {
        var tempTarget = (e.target) ? e.target : e.srcElement;
        if (jQuery(tempTarget).parents("#" + popoverId).length === 0) {
            element.RemovePopOver();
            jQuery("body").off("click");
        }
    });
}

/*
 ######################################################################################
 Function:   Close all bubble popups
 ######################################################################################
 */
function fnCloseAllPopups() {
    hideBubblePopups();
    // Remove inner HTML for My Plan created popups
    jQuery(".jquerybubblepopup-ksap > .jquerybubblepopup-innerHtml").children().remove();
    // TODO remove after review: if (jQuery("body").HasBubblePopup()) jQuery("body").HideBubblePopup();
    // TODO remove after review: jQuery(document).off();
    // KRAD 2.2.0 uses a global event handler to update popups
}

/**
 * Close the current popup
 *
 * Used for the manual close of the popup.
 */
function fnClosePopup() {
    if (jQuery("body").HasPopOver()) {
        jQuery("body").HidePopOver();
        jQuery("body").RemovePopOver();
    }
    jQuery("div.jquerypopover").remove();
    jQuery("body").off("click");
}

/**
 * Retrieves the current enrollment status of an activity.
 * @param url
 * @param retrieveOptions
 * @param componentId
 */
function ksapGetSectionEnrollment(url, retrieveOptions, componentId) {
    var elementToBlock = jQuery(".ksap-enrl-data").parent();
    if (componentId) elementToBlock = jQuery("#" + componentId + " .ksap-enrl-data").parent();
    jQuery.ajax({
        url:url,
        data:retrieveOptions,
        dataType:"json",
        beforeSend:function () {
            elementToBlock.block({
                message:'<img src="../ks-ap/images/ajaxLoader16.gif" alt="Fetching enrollment data..." />',
                fadeIn:0,
                fadeOut:0,
                overlayCSS:{
                    backgroundColor:'#fff',
                    opacity:0
                },
                css:{
                    border:'none',
                    width:'16px',
                    top:'0px',
                    left:'0px'
                }
            });
        },
        error:function () {
            elementToBlock.fadeOut(250);
            elementToBlock.each(function () {
                jQuery(this).css("text-align", "center").find("img.ksap-enrl-data").addClass("alert").attr("alt", "Oops, couldn't fetch the data. Refresh the page.").attr("title", "Oops, couldn't fetch the data. Refresh the page.");
            });
            elementToBlock.fadeIn(250);
            elementToBlock.unblock();
        },
        success:function (response) {
            elementToBlock.fadeOut(250);
            jQuery.each(response, function (sectionId, enrlObject) {
                var message = "<span class='ksap-text-bold'>--</span><br />";
                if (enrlObject.status) {
                    if (enrlObject.status == "open") {
                        message = "<span class='ksap-text-bold'>Open</span><br />";
                    }
                    else if (enrlObject.status == "closed") {
                        message = "<span class='ksap-text-bold'>Closed</span><br />";
                    }
                }
                message += "<strong>" + enrlObject.enrollCount + "</strong> / " + enrlObject.enrollMaximum;
                var title = enrlObject.enrollCount + " enrolled out of " + enrlObject.enrollMaximum;
                if (enrlObject.enrollEstimate) {
                    message += "E";
                    title += " estimated";
                }
                title += " limit. Updated few minutes ago."
                var data = jQuery("<span />").addClass("ksap-enrl-data").attr("title", title).html(message);
                jQuery("#" + sectionId + " .ksap-enrl-data").replaceWith(data);
            });
            elementToBlock.fadeIn(250);
            elementToBlock.unblock();
        }
    });
}

/**
 * Toggles content for sections on course details page
 * @param sectionRow
 * @param obj
 * @param expandText
 * @param collapseText
 */
function toggleSectionDetails(sectionRow, obj, expandText, collapseText) {
    if (typeof obj.data("hidden") == "undefined") {
        obj.data("hidden", true);
    }
    var collapsibleRow = sectionRow.next("tr.collapsible");
    if (obj.data("hidden")) {
        sectionRow.find("td").first().attr("rowspan", "3");
        sectionRow.find("td").last().attr("rowspan", "3");
        collapsibleRow.show().next("tr.collapsible").show();
        obj.text(collapseText).data("hidden", false);
    } else {
        sectionRow.find("td").first().attr("rowspan", "1");
        sectionRow.find("td").last().attr("rowspan", "1");
        collapsibleRow.hide().next("tr.collapsible").hide();
        obj.text(expandText).data("hidden", true);
    }
}

/**
 * Expands curriculum information for sections on course details page
 *
 * @param actionComponent
 * @param expandText
 * @param collapseText
 */
function expandCurriculumComments(actionComponent, expandText, collapseText) {
    var curriculumMessage = jQuery(actionComponent).parent().find('.curriculum-comment');
    if (curriculumMessage.is(":visible")) {
        curriculumMessage.slideUp(250, function () {
            if (expandText) {
                jQuery(actionComponent).text(expandText);
            }
        });
    } else {
        curriculumMessage.slideDown(250, function () {
            if (collapseText) {
                jQuery(actionComponent).text(collapseText);
            }
        });
    }
}

/**
 * Builds hover text for sections in course details
 *
 * @param obj - Obj text is created for
 */
function buildHoverText(obj) {
    var message = '';
    var temp = '';
    // condition to check whether section is primary or secondary
    if (obj.data("primary")) {
        // Primary sections
        // condition to check if planned or not planned
        if (obj.data("planned")) {
            var secondarySections = [];
            // Find list of secondary sections associated
            jQuery("div[data-courseid='" + obj.data("courseid") + "'][data-primarysection='" + obj.data("coursesection") + "'][data-planned='true'][data-primary='false']").each(function () {
                secondarySections.push(jQuery(this).data("coursesection"));
            });
            // Build string of secondary sections associated
            if (secondarySections.length > 0) {
                if (secondarySections.length == 1) {
                    temp = " and " + secondarySections.join();
                } else {
                    // commas separated string of secondary sections
                    temp = ", " + secondarySections.slice(0, -1).join(", ") + ", and " + secondarySections[secondarySections.length - 1];
                }
            }
            // Text should give "Delete {primary section} {list of secondary sections if any exist}"
            message = "Delete " + obj.data("coursesection") + temp;
        } else {
            // Text should give "Add {primary section}"
            message = "Add " + obj.data("coursesection");
        }
    } else {
        // Secondary sections
        // condition to check if planned or not planned
        if (obj.data("planned")) {
            // Text should give "Delete {secondary section}"
            message = "Delete " + obj.data("coursesection");
        } else {
            // Text should give "Add {secondary section} and {primary section if not planned}"
            if (!jQuery("div[data-courseid='" + obj.data("courseid") + "'][data-coursesection='" + obj.data("primarysection") + "']").data("planned")) {
                temp = " and " + obj.data("primarysection");
            }
            message = "Add " + obj.data("coursesection") + temp;
        }
    }
    obj.attr("title", message).find("img.uif-image").attr("alt", message);
}

/**
 * Registers event to close tooltip popup when clicking on something other than a toolips
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
 * Bookmarks a course using an ajax call to the controller and growl message response
 *
 * @param courseid - Id of the course being bookmarked
 * @param e - An object containing data that will be passed to the event handler.
 */
function bookmarkCourse(courseId, e) {
    stopEvent(e);
    var form = jQuery('<form />').attr("id", "popupForm").attr("action", "planner").attr("method", "post");
    jQuery("body").append(form);
    var additionalFormData = {
        methodToCall:"addBookmark",
        courseId:courseId
    }
    form.ajaxSubmit({
        data : ksapAdditionalFormData(additionalFormData),
        dataType : 'json',
        success : ksapPlannerUpdateEvent,
        error : function(jqXHR, textStatus, errorThrown) {
            if (textStatus == "parsererror")
                textStatus = "JSON Parse Error";
            showGrowl(errorThrown, jqXHR.status + " " + textStatus);
            fnClosePopup();
        }
    });
    fnCloseAllPopups();
    jQuery("form#popupForm").remove();
}

/**
 * Bookmarks a course using an ajax call to the controller and growl message response
 *
 * @param courseid - Id of the course being bookmarked
 * @param e - An object containing data that will be passed to the event handler.
 */
function addCourseSection(courseId,sectionCode, e) {
    stopEvent(e);
    var form = jQuery('<form />').attr("id", "popupForm").attr("action", "planner").attr("method", "post");
    jQuery("body").append(form);
    var additionalFormData = {
        methodToCall:"addBookmark",
        courseId:courseId
    }
    form.ajaxSubmit({
        data : ksapAdditionalFormData(additionalFormData),
        dataType : 'json',
        success : ksapPlannerUpdateEvent,
        error : function(jqXHR, textStatus, errorThrown) {
            if (textStatus == "parsererror")
                textStatus = "JSON Parse Error";
            showGrowl(errorThrown, jqXHR.status + " " + textStatus);
            fnClosePopup();
        }
    });
    fnCloseAllPopups();
    jQuery("form#popupForm").remove();
}

/**
 * Open a popup which loads via ajax a separate view's component
 *
 * @param getId - Id of the component from the separate view to select to insert into popup.
 * @param retrieveData - Object of data used to passed to generate the separate view.
 * @param formAction - The action param of the popup inner form.
 * @param popupStyle - Object of css styling to apply to the initial inner div of the popup (will be replaced with remote component)
 * @param popupOptions - Object of settings to pass to the Bubble Popup jQuery Plugin.
 * @param e - An object containing data that will be passed to the event handler.
 */
function openPopup(getId, retrieveData, formAction, popupStyle, popupOptions, e) {
    stopEvent(e);
    fnCloseAllPopups();

    var popupOptionsDefault = {
        themePath:getConfigParam("kradUrl")+"/../ks-ap/scripts/jquery-popover/jquerypopover-theme/",
        manageMouseEvents:true,
        selectable:true,
        tail:{align:"middle", hidden:false},
        position:"left",
        align:"center",
        alwaysVisible:false,
        themeMargins:{total:"20px", difference:"5px"},
        themeName:"ksap",
        distance:"0px",
        openingSpeed:5,
        closingSpeed:5
    };

    var target = (e.currentTarget) ? e.currentTarget : e.srcElement;
    var popupItem = (typeof popupOptions.selector == "undefined") ? jQuery(target) : jQuery(target).parents(popupOptions.selector);

    if (!popupItem.HasPopOver()) popupItem.CreatePopOver({manageMouseEvents:false});
    var popupSettings = jQuery.extend(popupOptionsDefault, popupOptions);
    var popupHtml = jQuery('<div />').attr("id", "KSAP-Popover");
    if (popupStyle) {
        jQuery.each(popupStyle, function (property, value) {
            popupHtml.css(property, value);
        });
    } else {
        popupHtml.css({
            width: "300px",
            height: "16px"
        });
    }
    popupSettings.innerHtml = popupHtml.wrap("<div>").parent().clone().html();

    popupItem.ShowPopOver(popupSettings, false);
    popupItem.FreezePopOver();

    var popupId = popupItem.GetPopOverID();

    fnPositionPopUp(popupId);
    if (!popupOptions.sticky) {
        clickOutsidePopOver(popupId, popupItem);
    }

    var retrieveForm = '<form id="retrieveForm" action="' + retrieveData.action + '" method="post" />'
    jQuery("body").append(retrieveForm);

    var elementToBlock = jQuery("#KSAP-Popover");

    var successCallback = function (htmlContent) {
        var component;
        if (jQuery("#requestStatus", htmlContent).length <= 0) {
            var popupForm = jQuery('<form />').attr("id", "popupForm").attr("action", formAction).attr("method", "post");
            component = jQuery("#" + getId, htmlContent).wrap(popupForm).parent();
        } else {
            var pageId = jQuery("#pageId", htmlContent).val();
            eval(jQuery("input[data-role='script'][data-for='" + pageId + "']", htmlContent).val().replace("#" + pageId, "body"));
            var errorMessage = '<img src="/student/ks-ap/images/pixel.gif" alt="" class="icon"><div class="message">' + jQuery("#plan_item_action_response_page", htmlContent).data(kradVariables.VALIDATION_MESSAGES).serverErrors[0] + '</div>';
            component = jQuery("<div />").addClass("ksap-feedback error").html(errorMessage);
        }
        if (jQuery("#KSAP-Popover").length) {
            popupItem.SetPopOverInnerHtml(component);
            fnPositionPopUp(popupId);
            if (popupOptions.close || typeof popupOptions.close === 'undefined') jQuery("#" + popupId + " .jquerypopover-innerHtml").append('<img src="../ks-ap/images/btnClose.png" class="ksap-popup-close"/>');
            jQuery("#" + popupId + " img.ksap-popup-close").on('click', function () {
                popupItem.HidePopOver();
                fnCloseAllPopups();
            });
        }
        runHiddenScripts(getId);
        elementToBlock.unblock();
    };

    ksapAjaxSubmitForm(retrieveData, successCallback, elementToBlock, "retrieveForm");
    jQuery("form#retrieveForm").remove();
}

/**
 * Sets up character counting functionality for notes
 */
(function ($) {

    $.fn.characterCount = function (options) {
        var oDefaults = {
            maxLength: 999,
            warningLength: 10,
            classCounter: "counter",
            classWarning: "warning"
        };

        function calculate(obj, options) {
            var iCount = $(obj).val().length;
            var iAvailable = iCount;
            var sValue = $(obj).val();
            if (iCount > options.maxLength) {
                $(obj).val(sValue.substr(0, options.maxLength));
            }
            if (iAvailable <= options.warningLength && iAvailable >= 0) {
                $('.' + options.classCounter).addClass(options.classWarning);
            } else {
                $('.' + options.classCounter).removeClass(options.classWarning);
            }
            $('.' + options.classCounter).html(iAvailable + "/" +options.maxLength+ " characters");
        }

        this.each(function () {
            if (typeof $(this).attr("maxlength") != "undefined") {
                oDefaults.maxLength = parseInt($(this).attr("maxlength"));
            }
            var options = $.extend(oDefaults, options);
            calculate(this, options);
            $(this).on("keyup paste cut contextmenu change mouseout blur", function (e) {
                calculate(this, options);
            });
        });
    };
})(jQuery);