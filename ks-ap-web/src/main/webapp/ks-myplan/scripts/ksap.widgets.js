/* This function may be redefined to add additional inputs to forms before posting */
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

function ksapAdditionalFormData(additionalData) {
    return additionalData;
}

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

function readUrlParam(key) {
    var aParams = window.location.search.replace('?', '').split('&');
    var oParams = {};
    jQuery.each(aParams, function (index, value) {
        oParams[value.split('=')[0]] = value.split('=')[1];
    });
    if (oParams[key]) {
        return decodeURIComponent(oParams[key]);
    } else {
        return false;
    }
}

function setUrlParam(key, value) {
    var aParams = [];
    if (window.location.search) {
        aParams = window.location.search.replace('?', '').split('&');
    }
    var oParams = {};
    if (aParams.length > 0) {
        jQuery.each(aParams, function (index, value) {
            oParams[value.split('=')[0]] = value.split('=')[1];
        });
        var oTemp = {};
        oTemp[key] = value;
        jQuery.extend(oParams, oTemp);
    } else {
        oParams[key] = value;
    }
    aParams = [];
    for (var key in oParams) {
        if (key != "" && oParams[key] != "") aParams.push(encodeURIComponent(key) + "=" + encodeURIComponent(oParams[key]));
    }
    window.location.replace(window.location.protocol + "//" + window.location.host + window.location.pathname + "?" + aParams.join("&"));
}

/* This is for DOM changes to refresh the view on back to keep the view updated */
if (readUrlHash("modified")) {
    var url = window.location.href;
    var aHash = window.location.href.split("#")[1].replace("#", "").split("&");
    aHash.splice(aHash.indexOf("modified=true"), 1);
    window.location.assign(url.split("#")[0] + ((aHash.length > 0) ? "#" + aHash.join("&") : ""));
}

jQuery(document).ready(function () {
    jQuery("head").append('<!--[if ie 9]><style type="text/css" media="screen"> \
        button.uif-primaryActionButton,button.uif-secondaryActionButton, \
        button.uif-primaryActionButton:hover,button.uif-secondaryActionButton:hover,\
        button.uif-primaryActionButton[disabled="true"],\
        button.uif-primaryActionButton[disabled="disabled"],\
        button.uif-primaryActionButton[disabled="true"]:hover,\
        button.uif-primaryActionButton[disabled="disabled"]:hover,\
        button.uif-secondaryActionButton[disabled="true"],\
        button.uif-secondaryActionButton[disabled="disabled"],\
        button.uif-secondaryActionButton[disabled="true"]:hover,\
        button.uif-secondaryActionButton[disabled="disabled"]:hover{ \
            filter:none !important;}</style><![endif]-->\
    ');
});

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

function openDocument(url) {
    var newUrl;
    if (url.substring(0, 4) == "http") {
        newUrl = url;
    } else {
        newUrl = window.location.protocol + "//" + window.location.host + window.location.pathname.substring(0, window.location.pathname.lastIndexOf("/")) + "/" + url;
    }
    if (newUrl == window.location.href) {
        window.location.reload(true);
    } else {
        window.location.assign(newUrl);
    }
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
        themePath:getConfigParam("kradUrl")+"/../ks-myplan/jquery-popover/jquerypopover-theme/",
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
        themePath:getConfigParam("kradUrl")+"/../ks-myplan/jquery-popover/jquerypopover-theme/",
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
 *
 *
 * @param sText -
 * @param e - An object containing data that will be passed to the event handler.
 * @param close -
 */
function openDialog(sText, e, close) {
    stopEvent(e);

    var popupOptionsDefault = {
        themePath:getConfigParam("kradUrl")+"/../ks-myplan/jquery-popover/jquerypopover-theme/",
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

    var popupBox = jQuery("body");

    fnCloseAllPopups();

    if (!popupBox.HasPopOver()) popupBox.CreatePopOver({manageMouseEvents: false});

    popupOptionsDefault.tail.hidden = true;
    popupOptionsDefault.innerHtml = '<div style="width:300px;">' + sText + '</div>';

    popupBox.ShowPopOver(popupOptionsDefault, false);
    var popupBoxId = popupBox.GetPopOverID();
    popupBox.FreezePopOver();

    if (close || typeof close === 'undefined') jQuery("#" + popupBoxId + " .jquerypopover-innerHtml").append('<img src="../ks-ap/images/btnClose.png" class="ksap-popup-close"/>');

    fnPositionPopUp(popupBoxId);

    clickOutsidePopOver(popupBoxId, popupBox);

    jQuery("#" + popupBoxId + " img.ksap-popup-close").on('click', function () {
        popupBox.HidePopOver();
        fnCloseAllPopups();
    });
}
/**
 *
 */
function editNote(obj, e) {
    var planItemId = obj.data("planitemid");
    var atpId = obj.data("atpid");
    var category = obj.data("category");
    jQuery("#" + category + "_" + atpId.replace(/\./g, "-") + "_" + planItemId + "_note").HideBubblePopup();
    var retrieveData = {
        action: 'plan',
        viewId: 'PlannedCourse-FormView',
        methodToCall: 'startAddPlannedCourseForm',
        planItemId: planItemId,
        atpId: atpId,
        pageId: 'edit_note_page'
    };
    var popupOptions = {
        tail: {
            hidden: true
        },
        align: 'top',
        close: true,
        selector: "body"
    };
    openPopup('edit_note_page', retrieveData, 'plan', null, popupOptions, e);
}

/**
 *
 *
 * @param additionalFormData -
 * @param type -
 * @param methodToCall -
 * @param e - An object containing data that will be passed to the event handler.
 */
function submitHiddenForm(formAction, additionalFormData, e) {
    stopEvent(e);
    var form = jQuery('<form />').attr("id", "popupForm").attr("action", formAction).attr("method", "post");
    jQuery("body").append(form);
    submitPopupForm(additionalFormData, e, true);
    fnCloseAllPopups();
    jQuery("form#popupForm").remove();
}
/**
 * Prepares a dialog for submission to the controller.
 *
 * @param additionalFormData - Variables and data sent from the dialog
 * @param e - An object containing data that will be passed to the event handler.
 * @param bDialog - Boolean
 */
function submitPopupForm(additionalFormData, e, bDialog) {
    var target = (e.currentTarget) ? e.currentTarget : e.srcElement;
    var targetText = ( jQuery.trim(jQuery(target).text()) != '') ? jQuery.trim(jQuery(target).text()) : "Error";
    var elementToBlock = (target.nodeName != 'INPUT') ? jQuery(target) : jQuery(target).parent();
    var successCallback = function (htmlContent) {
        var pageId = jQuery("#pageId", htmlContent).val();
        var status = jQuery.trim(jQuery("#requestStatus", htmlContent).text().toLowerCase());
        eval(jQuery("input[data-role='script'][data-for='" + pageId + "']", htmlContent).val().replace("#" + pageId, "body"));
        var data = {};
        data.message = '<img src="/student/ks-ap/images/pixel.gif" alt="" class="icon"><div class="message"><span /></div>';
        data.cssClass = "ksap-feedback " + status;
        switch (status) {
            case 'success':
                data.message = data.message.replace("<span />", jQuery("#plan_item_action_response_page", htmlContent).data(kradVariables.VALIDATION_MESSAGES).serverInfo[0]);
                var jsonText = jQuery.trim(jQuery("#jsonEvents", htmlContent).text()).replace(/\n/g,"\\n");
                var json = jQuery.parseJSON(jsonText);
                for (var key in json) {
                    if (json.hasOwnProperty(key)) {
                        eval('jQuery.event.trigger("' + key + '", ' + JSON.stringify(jQuery.extend(json[key], data)) + ');');
                    }
                }
                setUrlHash('modified', 'true');
                break;
            case 'error':
                data.message = data.message.replace("<span />", jQuery("#plan_item_action_response_page", htmlContent).data(kradVariables.VALIDATION_MESSAGES).serverErrors[0]);
                if (bDialog) {
                    var sContent = jQuery("<div />").append(data.message).addClass("ksap-feedback error").css({"background-color":"#fff"});
                    var sHtml = jQuery("<div />").append('<div class="uif-headerField uif-sectionHeaderField"><h3 class="uif-header">' + targetText + '</h3></div>').append(sContent);
                    if (jQuery("body").HasPopOver()) jQuery("body").HidePopOver();
                    openDialog(sHtml.html(), e);
                } else {
                    eval('jQuery.event.trigger("ERROR", ' + JSON.stringify(data) + ');');
                }
                break;
        }
    };
    var blockOptions = {
        message:'<img src="../ks-ap/images/btnLoader.gif"/>',
        css:{
            width:'100%',
            border:'none',
            backgroundColor: 'transparent',
            width: (elementToBlock.outerWidth() - 2) + "px",
            height: (elementToBlock.outerHeight() - 2) + "px",
            lineHeight: (elementToBlock.outerHeight() - 4) + "px"
        },
        overlayCSS:{
            backgroundColor:'#fff',
            opacity: 0.3,
            padding: '0px',
            margin: '0px',
            top: '-1px',
            left: '-1px',
            width: (elementToBlock.outerWidth()) + "px",
            height: (elementToBlock.outerHeight()) + "px"
        }
    };
    ksapAjaxSubmitForm(additionalFormData, successCallback, elementToBlock, "popupForm", blockOptions);
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

/**
 * Only used in StudentAcademicPlanner-FormView
 *
 * @param propertyName
 * @param propertyValue
 * @param formId
 */
function ksapWriteHiddenToForm(propertyName, propertyValue, formId) {
    //removing because of performFinalize bug
    jQuery('input[name="' + escapeName(propertyName) + '"]').remove();

    if (propertyValue.indexOf("'") != -1) {
        jQuery("<input type='hidden' name='" + propertyName + "'" + ' value="' + propertyValue + '"/>').appendTo(jQuery("#" + formId));
    } else {
        jQuery("<input type='hidden' name='" + propertyName + "' value='" + propertyValue + "'/>").appendTo(jQuery("#" + formId));
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

    ksapAjaxSubmitGenericForm(methodToCall, updateRefreshableComponentCallback, {reqComponentId:id, skipViewInit:"false"}, elementToBlock, id, elementBlockingSettings);
    jQuery("form#" + id + "_form").remove();
}
/*
 ######################################################################################
 Function:   KRAD's ajax submit function modified to allow submission of a form
 other then the kuali form
 ######################################################################################
 */
function ksapAjaxSubmitGenericForm(methodToCall, successCallback, additionalData, elementToBlock, formId, elementBlockingSettings) {
    var data = {};

    // methodToCall checks
    if (methodToCall == null) {
        var methodToCallInput = jQuery("input[name='methodToCall']");
        if (methodToCallInput.length > 0) {
            methodToCall = jQuery("input[name='methodToCall']").val();
        }
    }

    // check to see if methodToCall is still null
    if (methodToCall != null || methodToCall !== "") {
        data.methodToCall = methodToCall;
    }

    data.renderFullView = false;

    // remove this since the methodToCall was passed in or extracted from the page, to avoid issues
    jQuery("input[name='methodToCall']").remove();

    if (additionalData != null) {
        jQuery.extend(data, additionalData);
    }

    var viewState = jQuery(document).data(kradVariables.VIEW_STATE);
    if (!jQuery.isEmptyObject(viewState)) {
        var jsonViewState = jQuery.toJSON(viewState);

        // change double quotes to single because escaping causes problems on URL
        jsonViewState = jsonViewState.replace(/"/g, "'");
        jQuery.extend(data, {clientViewState:jsonViewState});
    }

    var submitOptions = {
        data:data,
        success:function (response) {
            var tempDiv = document.createElement('div');
            tempDiv.innerHTML = response;
            var hasError = checkForIncidentReport(response);
            if (!hasError) {
                successCallback(tempDiv);
            }
            jQuery("#formComplete").empty();
        },
        error:function (jqXHR, textStatus) {
            alert("Request failed: " + textStatus);
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
                    elementToBlock.block(jQuery.extend(elementBlockingDefaults, elementBlockingSettings));
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
            },
            statusCode : {
                500 : function() {
                    showGrowl(
                        "500 Internal Server Error",
                        "Fatal Error");
                }
            }

        };
    }
    jQuery.extend(submitOptions, elementBlockingOptions);
    var form;
    if (formId) {
        form = jQuery("#" + formId + "_form");
    } else {
        form = jQuery("#kualiForm");
    }
    form.ajaxSubmit(submitOptions);
}

/*
 ######################################################################################
 Function:   Truncate (ellipse) a single horizontally aligned item so all items
 fit on one line.
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

function indicateViewingAudit(id, category) {
    var open = false;
    var currentAudit = jQuery("." + category + ".auditHtml .ksap-audit-report");
    var currentAuditId = currentAudit.attr("auditid");

    jQuery("#" + id + " .uif-collectionItem").not(".pending").each(function (index) {
        if (jQuery(this).attr("id").replace("link_", "") == currentAuditId && currentAudit.is(":visible")) {
            if (category == 'degreeAudit') {
                jQuery(this).find(".uif-label label").html("Viewing");
            }
            if (category == 'planAudit') {
                if (index > 1) open = true;
                jQuery(this).addClass("viewing");
            }
        } else {
            if (category == 'degreeAudit') {
                jQuery(this).find(".uif-label label").html("View");
            }
            if (category == 'planAudit') {
                jQuery(this).removeClass("viewing");
            }
        }
    });
    if (open) {
        jQuery("#plan_audit_toggle_link").click();
    }
}
/*
 ######################################################################################
 Function:   Slide into view hidden horizontally aligned items specifying the id
 of the item being brought into view.
 ######################################################################################
 */
function fnPopoverSlider(showId, parentId, direction) {
    var newDirection;
    if (direction === 'left') {
        newDirection = 'right';
    } else {
        newDirection = 'left';
    }
    jQuery("#" + parentId + " > .uif-horizontalBoxLayout > div.uif-boxLayoutHorizontalItem:visible").hide("slide", {
        direction:direction
    }, 100, function () {
        jQuery("#" + parentId + " > .uif-horizontalBoxLayout > div.uif-boxLayoutHorizontalItem").filter("#" + showId).show("slide", {
            direction:newDirection
        }, 100, function () {
            jQuery(this).focus();
        });
    });
}

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

/*
 ######################################################################################
 Function:   Build Term Plan View heading
 ######################################################################################
 */
function fnBuildTitle(aView) {
    var aFirst = jQuery.trim(jQuery(aView[0]).find("div:hidden[id^='plan_base_atpId']").text());
    var aLast = jQuery.trim(jQuery(aView[aView.length - 1]).find("div:hidden[id^='plan_base_atpId']").text());
    jQuery("#planned_courses_detail .ksap-plan-header").html(aFirst+ ' - ' + aLast);
}

/*
 ######################################################################################
 Function:   expand/collapse backup course set within plan view
 ######################################################################################
 */
function ksapCreateLightBoxLink(controlId, options) {
    jQuery(function () {
        var showHistory = false;

        // Check if this is called within a light box
        if (!jQuery(".fancybox-wrap", parent.document).length) {

            // Perform cleanup when lightbox is closed
            options['beforeClose'] = cleanupClosedLightboxForms;

            // If this is not the top frame, then create the lightbox
            // on the top frame to put overlay over whole window
            if (top == self) {
                jQuery("#" + controlId).fancybox(options);
            } else {
                jQuery("#" + controlId).click(function (e) {
                    e.preventDefault();
                    top.jQuery.fancybox(options);
                });
            }
        } else {
            //jQuery("#" + controlId).attr('target', '_self');
            showHistory = true;
        }

        // Set the renderedInLightBox = true param
        if (options['href'].indexOf('&renderedInLightBox=true') == -1) {
            options['href'] = options['href'] + '&renderedInLightBox=true'
                + '&showHome=false' + '&showHistory=' + showHistory
                + '&history=' + jQuery('#formHistory\\.historyParameterString').val();
        }
    });
}

function ksapLightBoxLink(href, options, e) {
    stopEvent(e);
    var target = (e.currentTarget) ? e.currentTarget : e.srcElement;
    options['autoHeight'] = true;
    options['href'] = href;
    //options['beforeClose'] = cleanupClosedLightboxForms;
    top.jQuery.fancybox(options);
}

function disabledCheck(disableCompId, disableCompType, condition) {
    if (disableCompType == "radioGroup" || disableCompType == "checkboxGroup") {
        if (condition()) {
            jQuery("input[id^='" + disableCompId + "']").prop("disabled", true);
        }
        else {
            jQuery("input[id^='" + disableCompId + "']").prop("disabled", false);
        }
    }
    else {
        var disableControl = jQuery("#" + disableCompId);
        if (condition()) {
            disableControl.prop("disabled", true);
            disableControl.addClass("disabled");
            if (disableCompType === "actionLink" || disableCompType === "action") {
                disableControl.attr("tabIndex", "-1");
            }
        }
        else {
            disableControl.prop("disabled", false);
            disableControl.removeClass("disabled");
            if (disableCompType === "actionLink" || disableCompType === "action") {
                disableControl.attr("tabIndex", "0");
            }
        }
    }
}

function getAuditProgram(param, type) {
    var campus;
    switch (parseFloat(jQuery("input[name='" + type + "Audit.campusParam']:checked").val())) {
        case 306:
            campus = "Seattle";
            break;
        case 310:
            campus = "Bothell";
            break;
        case 323:
            campus = "Tacoma";
            break;
        default:
            campus = null;
    }
    if (param == 'id') {
        return jQuery("select[name='" + type + "Audit.programParam" + campus + "']").val();
    } else {
        return jQuery("select[name='" + type + "Audit.programParam" + campus + "'] option:selected").text();
    }
}

var pendingPlanAuditHeadingText = 'We are currently auditing your plan for \'<span class="programName"></span>\'.';
var pendingDegreeAuditHeadingText = 'We are currently auditing your degree for \'<span class="programName"></span>\'.';

var blockPendingAuditStyle = {
    message: '<img src="../ks-ap/images/ajaxAuditRunning32.gif" alt="" class="icon"/><div class="heading"></div>',
    fadeIn: 250,
    fadeOut: 250,
    css: {
        padding: '30px 30px 30px 82px',
        margin: '30px',
        width: 'auto',
        textAlign: 'left',
        border: 'solid 1px #ffd14c',
        backgroundColor: '#fffdd7',
        'border-radius': '15px',
        '-webkit-border-radius': '15px',
        '-moz-border-radius': '15px'
    },
    overlayCSS: {
        backgroundColor: '#fff',
        opacity: 0.85,
        border: 'none',
        cursor: 'wait'
    }
};

var replaceBlockPendingAudit;

function changeLoadingMessage(selector, programName, auditType) {
    replaceBlockPendingAudit = setInterval(function () {
        setLoadingMessage(selector, programName, auditType)
    }, 100);
}

function setLoadingMessage(selector, programName, auditType) {
    if (jQuery(selector + ' div.blockUI.blockMsg.blockElement').length > 0) {
        fnAddLoadingText(selector, programName, auditType);
    }
}

function fnAddLoadingText(selector, programName, auditType) {
    clearInterval(replaceBlockPendingAudit);
    jQuery(selector + " div.blockUI.blockOverlay").css(blockPendingAuditStyle.overlayCSS);
    jQuery(selector + " div.blockUI.blockMsg.blockElement").html(blockPendingAuditStyle.message).css(blockPendingAuditStyle.css).data("growl","false");
    if (auditType == "plan") {
        jQuery(selector + " div.blockUI.blockMsg.blockElement .heading").html(pendingPlanAuditHeadingText);
    } else {
        jQuery(selector + " div.blockUI.blockMsg.blockElement .heading").html(pendingDegreeAuditHeadingText);
    }
    jQuery(selector + " div.blockUI.blockMsg.blockElement .programName").text(programName);
}

function removeCookie() {
    jQuery.cookie("ksap_audit_running", null, {expires: new Date().setTime(0)});
}

function setPendingAudit(obj, minutes) {
    if (jQuery.cookie("ksap_audit_running") == null) {
        var data = {};
        data.expires = new Date();
        data.expires.setTime(data.expires.getTime() + (minutes * 60 * 1000));
        data.programId = getAuditProgram('id', obj.data("audittype"));
        data.programName = getAuditProgram('name', obj.data("audittype"));
        data.recentAuditId = obj.data("recentauditid");
        data.auditType = obj.data("audittype");
        if (typeof data.recentAuditId === 'undefined') data.recentAuditId = '';

        if (data.programId != 'default') {
            changeLoadingMessage('.ksap-audit-report', data.programName, data.auditType);
            jQuery.ajax({
                url: "/student/ap/audit/status",
                data:{"programId":data.programId, "auditId":data.recentAuditId},
                dataType:"json",
                beforeSend:null,
                success:function (response) {
                    if (response.status == "PENDING") {
                        jQuery.cookie('ksap_audit_running', JSON.stringify(data), {expires:data.expires});
                        disabledCheck(obj.attr("id"), 'action', function () {
                            return true;
                        });
                        jQuery.event.trigger('REFRESH_AUDITS', data);
                        setUrlHash('modified', 'true');
                    }
                },
                statusCode: {
                    400 : function() {
                        showGrowl(
                            "400 Bad Request",
                            "Fatal Error");
                    },
                    500: function () {
                        showGrowl(
                            "500 Internal Server Error",
                            "Fatal Error");
                    }
                }
            });
        }
    } else {
        showGrowl("Another audit is currently pending. Please allow audit to complete.", "Running Audit Error", "errorGrowl");
    }
}

function getPendingAudit(id, type) {
    if (jQuery.cookie('ksap_audit_running')) {
        var data = jQuery.parseJSON(decodeURIComponent(jQuery.cookie('ksap_audit_running')));
        if (type == data.auditType) {
            var component = jQuery("#" + id + " .uif-stackedCollectionLayout");
            if (data) {
                var item = jQuery("<div />").addClass("uif-collectionItem pending").html('<img src="../ks-ap/images/ajaxPending16.gif" class="icon"/><span class="title">Auditing <span class="program">' + data.programName + '</span></span>');
                component.prepend(item);
                pollPendingAudit(data.programId, data.recentAuditId, data.auditType);
            }
            if (component.prev(".ksap-emptyCollection").length > 0) {
                component.prev(".ksap-emptyCollection").remove();
            }
        }
    }
}

function blockPendingAudit(data) {
    var id = "audit_section";
    if (data.auditType == "plan") id = "plan_audit_section";
    var elementToBlock = jQuery("#" + id);
    elementToBlock.block(blockPendingAuditStyle);
    jQuery("#" + id + " div.blockUI.blockMsg.blockElement").data("growl","true");
    if (data.auditType == "plan") {
        jQuery("#" + id + " div.blockUI.blockMsg.blockElement .heading").html(pendingPlanAuditHeadingText);
    } else {
        jQuery("#" + id + " div.blockUI.blockMsg.blockElement .heading").html(pendingDegreeAuditHeadingText);
    }
    jQuery("#" + id + " div.blockUI.blockMsg.blockElement .programName").text(data.programName);
    jQuery("#" + id).on('AUDIT_COMPLETE', function (event, data) {
        window.location.assign(window.location.href.split("#")[0]);
    });
}

function pollPendingAudit(programId, recentAuditId, auditType) {
    jQuery.ajaxPollSettings.pollingType = "interval";
    jQuery.ajaxPollSettings.interval = 250; // polling interval in milliseconds

    jQuery.ajaxPoll({
        url: "/student/ap/audit/status",
        data: {"programId":programId, "auditId":recentAuditId},
        dataType: "json",
        beforeSend: null,
        successCondition: function(response) {
            return (response.status == 'DONE' || response.status == 'FAILED' || jQuery.cookie("ksap_audit_running") == null);
        },
        success:function (response) {
            var growl = true;
            if (readUrlParam("viewId") == "DegreeAudit-FormView") {
                growl = jQuery(".ksap-audit-report div.blockUI.blockMsg.blockElement").data("growl");
                if (readUrlParam(auditType + "Audit.auditId") != false) jQuery("body").on('AUDIT_COMPLETE', function (event, data) {
                    setUrlParam(auditType + "Audit.auditId", "");
                });
            }
            var title = "Degree Audit";
            if (auditType == "plan") {
                title = "Plan Audit";
            }
            if (jQuery.cookie("ksap_audit_running") == null || response.status == 'FAILED') {
                if (growl) showGrowl("Your " + title + " was unable to complete.", title + " Error", "errorGrowl");
            } else {
                var data = jQuery.parseJSON(decodeURIComponent(jQuery.cookie("ksap_audit_running")));
                if (growl) showGrowl(data.programName + " " + title + " is ready to view.", title + " Completed", "infoGrowl");
            }
            jQuery.cookie("ksap_audit_running", null, {expires:new Date().setTime(0)});
            jQuery.event.trigger("AUDIT_COMPLETE", {"auditType": auditType});
        }
    });
}

function buttonState(parentId, buttonId) {
    var disabled = false;
    var button = jQuery("button#" + buttonId);
    jQuery("#" + parentId + " .ksap-required").each(function () {
        var value;
        if (jQuery(this).val()) {
            value = jQuery(this).val().replace(/\n/g, '');
        } else {
            value = "";
        }
        if (value == "" || value == "default") {
            disabled = true;
        }
    });
    if (disabled) {
        button.addClass("disabled").attr("disabled", disabled);
    } else {
        button.removeClass("disabled").attr("disabled", disabled);
    }
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
                var message = "<span class='fl-font-size-120 ksap-text-bold'>--</span><br />";
                if (enrlObject.status) {
                    if (enrlObject.status == "open") {
                        message = "<span class='fl-text-green fl-font-size-120 ksap-text-bold'>Open</span><br />";
                    }
                    else if (enrlObject.status == "closed") {
                        message = "<span class='fl-font-size-120 ksap-text-bold'>Closed</span><br />";
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

function updateHiddenScript(id, script) {
    jQuery("#" + id).unbind();
    var input = jQuery("input[data-for='" + id + "'][data-role='script']");
    input.removeAttr("script").attr("name", "script").val(script);
    runScriptsForId(id);
}

function switchFetchAction(actionId, toggleId) {
    var script = "jQuery('#' + '" + actionId + "').click(function(e){ toggleSections('" + actionId + "', '" + toggleId + "', 'ksap-section-planned', 'Show all scheduled sections', 'Hide non-selected sections'); });";
    updateHiddenScript(actionId, script);
    jQuery("#" + actionId).text("Hide non-selected sections").removeAttr("data-hidden").data("hidden", false);
}

function toggleSections(actionId, toggleId, showClass, showText, hideText) {
    var group = jQuery("#" + toggleId + " table tbody tr.row").not("." + showClass);
    var action = jQuery("#" + actionId);
    if (action.data("hidden")) {
        group.each(function () {
            var toggle = jQuery(this).find("a[id^='toggle_']");
            if (toggle.data("hidden") || typeof toggle.data("hidden") == "undefined") {
                jQuery(this).show();
            } else {
                jQuery(this).show().next("tr.collapsible").show().next("tr.collapsible").show();
            }
        });
        jQuery(".ksap-quarter-detail .activityInstitutionHeading").show();
        action.text(hideText).data("hidden", false);
    } else {
        group.each(function () {
            var toggle = jQuery(this).find("a[id^='toggle_']");
            if (toggle.data("hidden") || typeof toggle.data("hidden") == "undefined") {
                jQuery(this).hide();
            } else {
                jQuery(this).hide().next("tr.collapsible").hide().next("tr.collapsible").hide();
            }
        });
        jQuery(".ksap-quarter-detail .activityInstitutionHeading").hide();
        action.text(showText).data("hidden", true);
    }
}

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

function toggleRegisteredDetails(sectionRow, obj) {
    var collapsibleRow = sectionRow.next("tr.collapsible");
    if (collapsibleRow.is(":visible")) {
        obj.parents("td").attr("rowspan", "1");
        collapsibleRow.hide();
        obj.find("img.uif-image").toggleClass("expanded");
    } else {
        obj.parents("td").attr("rowspan", "2");
        collapsibleRow.show();
        obj.find("img.uif-image").toggleClass("expanded");
    }
}

function toggleComponentContent(obj, sectionId, selector, expandText, collapseText) {
    var action = jQuery(obj);
    if (typeof action.data("hidden") == "undefined") {
        action.data("hidden", true);
    }
    if (action.data("hidden")) {
        jQuery("#" + sectionId).find(selector).show();
        action.text(collapseText).data("hidden", false);
    } else {
        jQuery("#" + sectionId).find(selector).hide();
        action.text(expandText).data("hidden", true);
    }
}

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

function expandPlanAuditSummary(selector, expandText, collapseText) {
    if (jQuery(selector).is(":visible")) {
        jQuery(selector).each(function () {
            jQuery(this).attr('style', 'display:none').slideUp(250)
        });
        if (expandText) {
            jQuery('#plan_audit_toggle_link').text(expandText);
        }
    } else {
        jQuery(selector).each(function () {
            jQuery(this).attr('style', 'display:block').slideDown(250)
        });
        if (collapseText) {
            jQuery('#plan_audit_toggle_link').text(collapseText);
        }
    }
}

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
(function ($) {
    // TODO remove publish method after old audits have been purged as audit FTL inline scripted a publish call
    $.publish = function (event) {
        return true;
    };
    $.fn.characterCount = function (options) {
        var oDefaults = {
            maxLength: 999,
            warningLength: 10,
            classCounter: "counter",
            classWarning: "warning"
        };

        function calculate(obj, options) {
            var iCount = $(obj).val().length;
            var iAvailable = options.maxLength - iCount;
            var sValue = $(obj).val();
            if (iCount > options.maxLength) {
                $(obj).val(sValue.substr(0, options.maxLength));
            }
            if (iAvailable <= options.warningLength && iAvailable >= 0) {
                $('.' + options.classCounter).addClass(options.classWarning);
            } else {
                $('.' + options.classCounter).removeClass(options.classWarning);
            }
            $('.' + options.classCounter).html("<strong>" + iAvailable + "</strong>" + " character" + ((iAvailable != 1) ? "s" : "") + " remaining");
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

function fnCreateDate(sData) {
    var jTemp = jQuery(sData);
    jTemp.find("legend, .ksap-sort-remove").remove();
    var sDate = jQuery.trim(jTemp.text());
    if (sDate.length > 2) {
        return Date.parse(sDate);
    } else {
        return 0;
    }
}

jQuery.fn.dataTableExt.oSort['longdate-asc'] = function (x, y) {
    x = fnCreateDate(x);
    y = fnCreateDate(y);

    return ((x < y) ? -1 : ((x > y) ? 1 : 0));
};
jQuery.fn.dataTableExt.oSort['longdate-desc'] = function (x, y) {
    x = fnCreateDate(x);
    y = fnCreateDate(y);

    return ((x < y) ? 1 : ((x > y) ? -1 : 0));
};
Array.max = function (array) {
    return Math.max.apply(Math, array);
};

// TODO: Redefinition of coerceValue function not needed after bug resolved - https://jira.kuali.org/browse/KULRICE-9883
function coerceValue(name) {
    var value = "";
    var nameSelect = "[name='" + escapeName(name) + "']";
    // when group is opened in lightbox make sure to get the value from field in the lightbox
    // if that field is in the lightbox
    var parent = document;
    if (jQuery(nameSelect, jQuery(".fancybox-wrap")).length) {
        parent = jQuery(".fancybox-wrap");
    }
    if (jQuery(nameSelect + ":checkbox", parent).length == 1) {
        value = jQuery(nameSelect + ":checked", parent).val();
    }
    else if (jQuery(nameSelect + ":checkbox", parent).length > 1) {
        value = [];
        jQuery(nameSelect + ":checked", parent).each(function () {
            value.push(jQuery(this).val());
        });
    }
    else if (jQuery(nameSelect + ":radio", parent).length) {
        value = jQuery(nameSelect + ":checked", parent).val();
    }
    else if (jQuery(nameSelect, parent).length) {
        if (jQuery(nameSelect, parent).hasClass("watermark")) {
            jQuery.watermark.hide(nameSelect, parent);
            value = jQuery(nameSelect, parent).val();
            jQuery.watermark.show(nameSelect, parent);
        }
        else {
            value = jQuery(nameSelect, parent).val();
        }
    }
    if (value == null) {
        value = "";
    }
    if (value == "true") {
        value = true
    }
    if (value == "false") {
        value = false
    }

    return value;
}
// TODO: Redefinition of setupProgressiveCheck function not needed if improvement resolved - https://jira.kuali.org/browse/KULRICE-9992
function setupProgressiveCheck(controlName, disclosureId, baseId, condition, alwaysRetrieve, methodToCall, onKeyUp) {
    if (!baseId.match("\_c0$")) {
        var theControl = jQuery("[name='" + escapeName(controlName) + "']");
        var eventType = 'change';

        if (onKeyUp && (theControl.is("textarea") || theControl.is("input[type='text'], input[type='password']"))) {
            eventType = 'ready focus keyup paste cut contextmenu mouseout blur';
        }

        theControl.on(eventType, function () {
            var refreshDisclosure = jQuery("#" + disclosureId);
            if (refreshDisclosure.length) {
                var displayWithId = disclosureId;

                if (condition()) {
                    if (refreshDisclosure.data("role") == "placeholder" || alwaysRetrieve) {
                        retrieveComponent(disclosureId, methodToCall);
                    }
                    else {
                        refreshDisclosure.addClass(kradVariables.PROGRESSIVE_DISCLOSURE_HIGHLIGHT_CLASS);
                        refreshDisclosure.show();

                        if (refreshDisclosure.parent().is("td")) {
                            refreshDisclosure.parent().show();
                        }

                        refreshDisclosure.animate({backgroundColor: "transparent"}, 6000);

                        //re-enable validation on now shown inputs
                        hiddenInputValidationToggle(disclosureId);

                        var displayWithLabel = jQuery(".displayWith-" + displayWithId);
                        displayWithLabel.show();
                        if (displayWithLabel.parent().is("td") || displayWithLabel.parent().is("th")) {
                            displayWithLabel.parent().show();
                        }
                    }
                }
                else {
                    refreshDisclosure.hide();

                    // ignore validation on hidden inputs
                    hiddenInputValidationToggle(disclosureId);

                    var displayWithLabel = jQuery(".displayWith-" + displayWithId);
                    displayWithLabel.hide();
                    if (displayWithLabel.parent().is("td") || displayWithLabel.parent().is("th")) {
                        displayWithLabel.parent().hide();
                    }
                }
            }
        });

        if (onKeyUp && (theControl.is("textarea") || theControl.is("input[type='text'], input[type='password']"))) {
            theControl.trigger("ready");
        }
    }
}

function ksapInitializePlanItems(pageSize) {
    var popupStyle = {width:'300px', height:'16px'};
    var popupOptions = {tail:{hidden:true}, position:'right', align:'top', close:true};

    jQuery('.ksap-carousel-list li .ksap-term-current.open, .ksap-carousel-list li .ksap-term-future.open').find('.ksap-term-planned .uif-stackedCollectionLayout, .ksap-term-backup .uif-stackedCollectionLayout').each(function(){
        var atpId = jQuery(this).parents('.ksap-carousel-term').data('atpid');
        var backup = jQuery(this).parents('.ksap-carousel-term').data('plantype');
        var size = jQuery(this).parents('.ksap-carousel-term').data('size');
        var jQuickAdd = jQuery('<div />')
            .addClass('quick-add-cell ks-plan-Bucket-addItem')
            .html('Add a course to plan')
            .click(function(e){
                var retrieveData = {
                    action : 'plan' ,
                    viewId : 'PlannedCourse-FormView' ,
                    methodToCall : 'startAddPlannedCourseForm' ,
                    atpId : atpId ,
                    backup : backup ,
                    pageId : 'plan_item_add_page'
                };
                openPopup('plan_item_add_page', retrieveData, 'plan', popupStyle, popupOptions, e);
            });
        // TODO: consider removing size limit enforcement
        if (size < 8) {
            jQuery(this).append(jQuickAdd).show();
        } else {
            jQuery(this).append(jQuickAdd).show();
        }
    });

    if (jQuery('#planned_courses_detail_list ul:not(.errorLines) li').length > 0) {
        var iMaxHeight = Math.max.apply(null, jQuery('#planned_courses_detail_list ul:not(.errorLines) li')
            .map(function() {
                return jQuery(this).height();
            }).get(	));
        jQuery('#planned_courses_detail_list ul:not(.errorLines) li').height(iMaxHeight);
        var iStart = 0;
        if (readUrlHash('planView')) {
            iStart = parseFloat(readUrlHash('planView'));
        } else if ( jQuery("div:hidden[id^='atp_start_index']").length > 0 ) {
            iStart = parseFloat(jQuery("div:hidden[id^='atp_start_index']").text());
        }

        jQuery('#planned_courses_detail_list').jCarouselLite({
            btnNext: '.ksap-carousel-next',
            btnPrev: '.ksap-carousel-prev',
            scroll: pageSize,
            visible: pageSize,
            start: iStart,
            afterEnd: function(a) {
                fnBuildTitle(a);
                var planView = jQuery(a[0]).index();
                if (planView == 0 && a.length < pageSize) {
                    planView = a.length - pageSize;
                }
                setUrlHash('planView', planView);
            },
            initCallback: function(a) { fnBuildTitle(a); jQuery.unblockUI(); }
        });
    }

    truncateField('planned_courses_detail_list', true);
    jQuery('#planned_courses_detail_list')
        .subscribe('PLAN_ITEM_ADDED', function(data){
            var campusCode = data.courseDetails.campusCode;
            var courseCode = data.courseDetails.courseCode;
            var activityCode = data.courseDetails.activityCode;
            var code;
            if (campusCode != null)
                code = campusCode + " " + courseCode;
            else
                code = courseCode;
            if (activityCode != null)
                code += " " + activityCode;
            fnAddPlanItem(data.atpId, data.category, data.planItemId, code, data.courseDetails.courseTitle, data.courseDetails.credit, data.showAlert, data.termName, data.timeScheduleOpen);
        })
        .subscribe('PLAN_ITEM_DELETED', function(data){
            fnRemovePlanItem(data.atpId, data.category, data.planItemId);
        })
        .subscribe('UPDATE_NEW_TERM_TOTAL_CREDITS', function(data){
            fnUpdateCredits(data.atpId, data.totalCredits, data.cartCredits);
        })
        .subscribe('UPDATE_OLD_TERM_TOTAL_CREDITS', function(data){
            fnUpdateCredits(data.atpId, data.totalCredits, data.cartCredits);
        });
    jQuery('#planned_courses_detail_list').on('PLAN_ITEM_ADDED', function(event, data) {
        fnAddPlanItem(data.atpId, data.category, data.planItemId, data.courseDetails.code, data.courseDetails.courseTitle, data.courseDetails.credit, data.showAlert, data.termName, data.timeScheduleOpen);
    }).on('PLAN_ITEM_DELETED', function(event, data) {
            fnRemovePlanItem(data.atpId, data.category, data.planItemId);
        }).on('PLAN_ITEM_UPDATED', function(event, data) {
            fnUpdatePlanItem(data); // TODO: REMOVE
        }).on('TERM_NOTE_UPDATED', function(event, data) {
            fnUpdateTermNote(data.atpId, data.termNote);
        }).on('UPDATE_NEW_TERM_TOTAL_CREDITS', function(event, data) {
            fnUpdateCredits(data.atpId, data.totalCredits, data.cartCredits);
        }).on('UPDATE_OLD_TERM_TOTAL_CREDITS', function(event, data) {
            fnUpdateCredits(data.atpId, data.totalCredits, data.cartCredits);
        });
}

/**
 * Creates the tooltip widget for an component
 *
 * @param id - id for the component to apply the tooltip to
 * @param options - options for the tooltip
 */
function createTooltip(id, text, options, onMouseHoverFlag, onFocusFlag) {
    var elementInfo = getHoverElement(id);
    var element = elementInfo.element;

    options['innerHtml'] = text;
    options['manageMouseEvents'] = false;
    if (onFocusFlag) {
        // Add onfocus trigger
        jQuery("#" + id).focus(function () {
//            if (!jQuery("#" + id).IsBubblePopupOpen()) {
            // TODO : use data attribute to check if control
            if (!isControlWithMessages(id)) {
                jQuery("#" + id).SetBubblePopupOptions(options, true);
                jQuery("#" + id).SetBubblePopupInnerHtml(options.innerHTML, true);
                jQuery("#" + id).ShowBubblePopup();
            }
//            }
        });
        jQuery("#" + id).blur(function () {
            jQuery("#" + id).HideBubblePopup();
        });
    }else if (onMouseHoverFlag) {
        // Add mouse hover trigger
        jQuery("#" + id).hover(function () {
            if (!jQuery("#" + id).IsBubblePopupOpen()) {
                if (!isControlWithMessages(id)) {
                    jQuery("#" + id).SetBubblePopupOptions(options, true);
                    jQuery("#" + id).SetBubblePopupInnerHtml(options.innerHTML, true);
                    jQuery("#" + id).ShowBubblePopup();
                }
            }
        }, function (event) {
            if (!onFocusFlag || !jQuery("#" + id).is(":focus")) {
                var result = mouseInTooltipCheck(event, id, element, this, elementInfo.type);
                if (result) {
                    mouseLeaveHideTooltip(id, jQuery("#" + id), element, elementInfo.type);
                }
            }
        });
    }else{
        // Add mouse hover trigger
        jQuery("#" + id).click(function () {
            if (!jQuery("#" + id).IsBubblePopupOpen()) {
                hideBubblePopups();
                if (!isControlWithMessages(id)) {
                    jQuery("#" + id).SetBubblePopupOptions(options, true);
                    jQuery("#" + id).SetBubblePopupInnerHtml(options.innerHTML, true);
                    jQuery("#" + id).ShowBubblePopup();
                }
            }else{
                hideBubblePopups();
            }
        });
    }
}

function registerClosePopups(){
    jQuery(document).on('click', function (e) {
        var tempTarget = (e.target) ? e.target : e.srcElement;
        if (jQuery(tempTarget).parents("div.uif-tooltip").length === 0) {
            hideBubblePopups();
        }
    });
}

