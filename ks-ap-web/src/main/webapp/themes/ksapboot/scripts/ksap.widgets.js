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
 * Open a course item popup by id, mostly used in linking course codes in course reqs text (course linkifier)
 *
 * @param courseId - GUID string of course to open in popup
 * @param e - An object containing data that will be passed to the event handler.
 */
function openCourse(courseId, e) {
    stopEvent(e);
    var target = (e.currentTarget) ? e.currentTarget : e.srcElement;
    if (jQuery(target).parents(".jquerypopover.jquerypopover-ksap").length > 0) {
        // if already in popup go to course details page instead
        window.location = "inquiry?methodToCall=start&viewId=CourseDetails-InquiryView&courseId=" + courseId;
    } else {
        var retrieveData = {action: "inquiry", viewId: "CourseDetailsPopover-InquiryView", methodToCall: "start", courseId: courseId};
        var popupStyle = {width: "300px", height: "16px"};
        var popupOptions = {tail: {align: "left"}, align: "left", position: "bottom", close: true};
        jQuery("#popupForm").remove();
        fnClosePopup();
        openDialogWindow("course_details_popover_page", retrieveData, "inquiry", popupStyle, popupOptions, e);
    }
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
 * Retrieves a component from another view created based on a ajax call then copies a section from it and places it
 * into the view that is currently being displayed in the browser.
 *
 * @param id - Id of the component to paste into
 * @param getId - Id of the component being copied
 * @param methodToCall - Method in the controller to call for the new view
 * @param action - Controller mapping to use
 * @param retrieveOptions - Data parameters to pass to the form
 * @param highlightId - Id of component to highlight after function is called
 * @param elementBlockingSettings - Settings for the html object
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

/**
 * Formats a component so each ellipsis item within it fit into a single line
 *
 * @param id - Id of the component with items to format
 * @param floated - If floated only items with widths greater than available are reset. Else are items are reset.
 */
function truncateField(id, floated) {
    jQuery("#" + id + " .uif-horizontalFieldGroup").each(function () {
        var itemSelector = ".uif-horizontalBoxGroup > .uif-boxLayoutHorizontalItem";
        var ellipsisItem = jQuery(this).find(itemSelector + ".ksap-text-ellipsis");
        if (ellipsisItem.length != 0) {
            jQuery(this).css("display", "block");

            // Calculated available width
            var fixed = 0;
            jQuery(this).find(itemSelector + ":not(.ksap-text-ellipsis)").each(function () {
                fixed = fixed + jQuery(this).outerWidth(true);
            });
            var available = jQuery(this).width() - ( fixed + ( ellipsisItem.outerWidth(true) - ellipsisItem.width() ) + 1 );
            ellipsisItem.css("white-space", "nowrap");

            // Reset widths
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
 * Retrieves the current enrollment status of an activity.
 * @param url
 * @param retrieveOptions
 * @param componentId
 */
function ksapGetSectionEnrollment(url, retrieveOptions, componentId) {
    var elementToBlock = jQuery(".ksap-enrl-data").parent();
    if (componentId) elementToBlock = jQuery("#" + componentId + " .ksap-enrl-data").parent();
    var imageUrl = getConfigParam("kradUrl")+"/../themes/ksapboot/images/ajaxLoader.gif";
    jQuery.ajax({
        url:url,
        data:retrieveOptions,
        dataType:"json",
        beforeSend:function () {
            elementToBlock.block({
                message:'<img src='+imageUrl+' alt="Fetching enrollment data..." />',
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
 * Bookmarks a course using an ajax call to the controller and growl message response
 *
 * @param courseid - Id of the course being bookmarked
 * @param e - An object containing data that will be passed to the event handler.
 */
function bookmarkCourse(courseId, e) {
    stopEvent(e);
    var form = jQuery('<form />').attr("id", "popupForm").attr("action", "bookmark").attr("method", "post");
    jQuery("body").append(form);
    var additionalFormData = {
        methodToCall:"addBookmark",
        courseId:courseId
    }
    form.ajaxSubmit({
        data : ksapAdditionalFormData(additionalFormData),
        dataType : 'json',
        success : ksapAjaxSubmitSuccessCallback,
        error : ksapAjaxSubmitErrorCallback
    });
    fnClosePopup();
    jQuery("form#popupForm").remove();
}

/**
 * Deletes a bookmarked course using an ajax call to the controller
 *
 * @param courseid - Id of the course being bookmarked
 * @param e - An object containing data that will be passed to the event handler.
 */
function deleteBookmarkCourse(courseId, e) {
    stopEvent(e);
    var form = jQuery('<form />').attr("id", "popupForm").attr("action", "bookmark").attr("method", "post");
    jQuery("body").append(form);
    var additionalFormData = {
        methodToCall:"deleteBookmark",
        courseId:courseId
    }
    form.ajaxSubmit({
        data : ksapAdditionalFormData(additionalFormData),
        dataType : 'json',
        success : ksapAjaxSubmitSuccessCallback,
        error : ksapAjaxSubmitErrorCallback
    });
    fnClosePopup();
    jQuery("form#popupForm").remove();
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

/**
 * Registers events for the messages widget
 *
 * @param messagewidget - A jQuery messagewidget object
 */
function registerMessageEvents(jqObject){
    jqObject.on('MESSAGE_SENT', function(){
        ksapRetrieveComponent('messages_summary','messages_summary','search','lookup',{viewId:'MessagesSummary-LookupView'});
    }).on('COMMENT_SENT', function(){
        ksapRetrieveComponent('messages_summary','messages_summary','search','lookup',{viewId:'MessagesSummary-LookupView'});
    });
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
 * Gathers information for submission to the controller via ajax
 *
 * @param data - Variables and data to be submitted to the controller
 * @param successCallback - Code block to run after a successful return from the controller
 * @param elementToBlock - The html object being effected by the controller call
 * @param formId - Id of the form the submit is being called on
 * @param blockingSettings - Settings for the html object
 */
function ksapAjaxSubmitForm(data, successCallback, elementToBlock, formId, blockingSettings) {
    data = ksapAdditionalFormData(data);

    // Setup ajax submit options
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

    // Setup loading icon display options for element being loaded on
    if (elementToBlock != null && elementToBlock.length) {
        var imageUrl = getConfigParam("kradUrl")+"/../themes/ksapboot/images/ajaxLoader.gif";
        var elementBlockingOptions = {
            beforeSend:function () {
                if (elementToBlock.hasClass("unrendered")) {
                    elementToBlock.append('<img src="' + getConfigParam("kradImageLocation") + 'loader.gif" alt="Loading..." /> Loading...');
                    elementToBlock.show();
                }
                else {
                    var elementBlockingDefaults = {
                        baseZ:500,
                        message:'<div class="ksap-loader">Loading...</div>',
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

jQuery(function(){
    //Under normal circumstances the search input values are saved for later use.  Clear the saved values when clicking
    // the "Find Courses" link in the top application navigation section
    jQuery('#courseSearchNav').on('click', 'a', function(){
        sessionStorage.removeItem('last_search');
    });
});

//Initialize the Header Bookmark count label & value
function initBookmarkCount(evenObject,bookMarkLabelText,e){
    //Register BM refresh event
    jQuery(evenObject)
        .on('UPDATE_BOOKMARK_TOTAL', function(event, data) {
            refreshBookmarkCount(data);
        });

    //Set bookmark count label text
    var bookmarkCountLabel=jQuery("#Ksap-Header-Bookmark-Count-Label");
    bookmarkCountLabel.html(bookMarkLabelText);

    //refresh bookmarkCount ...for initial page display
    requestBookmarkCountRefresh(e);
}

/**
 * Refresh Bookmark Count displayed in KSAP Page Headers  (see Ksap
 */
function requestBookmarkCountRefresh(e) {
    stopEvent(e);
    var form = jQuery('<form />').attr("id", "refreshBookmarkFormId").attr("action", "bookmark").attr("method", "post");
    jQuery("body").append(form);
    var additionalFormData = {
        methodToCall:"refreshBookmarkCount"
    }
    form.ajaxSubmit({
        data : ksapAdditionalFormData(additionalFormData),
        dataType : 'json',
        success : ksapAjaxSubmitSuccessCallback,
        error : ksapAjaxSubmitErrorCallback
    });
    fnClosePopup();
    jQuery("form#refreshBookmarkFormId").remove();
}

/**
 * Updates the html object containing the total number of bookmarks for the secondary navigation
 *
 * @param data - Data for the new object
 */
function refreshBookmarkCount (data) {
    var bookmarkCountLink=jQuery("#Ksap-Header-Bookmark-Count");
    var bookmarkCountValue=jQuery("#Ksap-Header-Bookmark-Count-Value");
    var bookmarkDetailsPageCountValue=jQuery("#bookmarkHeaderCount");

    //update the count in the secondary navigation
    if (bookmarkCountValue.length) {
        bookmarkCountValue.html(data.bookmarkTotal);
    }

    //update the styling and clickability of the link
    if (bookmarkCountLink.length) {
        if (data.bookmarkTotal !=null && data.bookmarkTotal <=0) {
            bookmarkCountLink.addClass("disabled ks-fontello-icon-star-empty");
            bookmarkCountLink.removeClass("ks-fontello-icon-star");
            bookmarkCountLink.attr("disabled","disabled");
        } else {
            bookmarkCountLink.addClass("ks-fontello-icon-star");
            bookmarkCountLink.removeClass("disabled ks-fontello-icon-star-empty");
            bookmarkCountLink.removeAttr("disabled")
        }
    }
}
