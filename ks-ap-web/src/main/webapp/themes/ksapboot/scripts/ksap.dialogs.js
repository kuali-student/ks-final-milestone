// Javascripts for the KSAP Dialog Functionality.

/**
 * Retrieves default popup/dialog settings
 *
 * @return object containing default options.
 */
function getPopupOptionsDefaults(){
    var popupOptionsDefault = {
        themePath:getConfigParam("kradUrl")+"/../plugins/jquery-popover/jquerypopover-theme/",
        manageMouseEvents:true,
        selectable:true,
        tail:{align:"middle", hidden:false},
        position:"left",
        align:"center",
        alwaysVisible:false,
        themeMargins:{total:"20px", difference:"5px"},
        themeName:"ksap",
        distance:"10px",
        openingSpeed:5,
        closingSpeed:5
    };

    return popupOptionsDefault;
}

/**
 * Open a dialog which loads via ajax a separate view's component
 *
 * @param getId - Id of the component from the separate view to select to insert into popup.
 * @param retrieveData - Object of data used to passed to generate the separate view.
 * @param formAction - The action param of the popup inner form.
 * @param popupStyle - Object of css styling to apply to the initial inner div of the popup (will be replaced with remote component)
 * @param popupOptions - Object of settings to pass to the Bubble Popup jQuery Plugin.
 * @param e - An object containing data that will be passed to the event handler.
 */
function openDialogWindow(getId, retrieveData, formAction, popupStyle, popupOptions, e) {
    // Stop any running events and close any existing dialogs
    stopEvent(e);
    fnClosePopup();

    // Find dialog parent object
    var target = (e.currentTarget) ? e.currentTarget : e.srcElement;
    var popupItem = (typeof popupOptions.selector == "undefined") ? jQuery(target) : jQuery(target).parents(popupOptions.selector);

    // Setup dialog popup options and settings
    if (!popupItem.HasPopOver()) popupItem.CreatePopOver({manageMouseEvents:false});
    var popupOptionsDefaults = getPopupOptionsDefaults();
    var popupSettings = jQuery.extend(popupOptionsDefaults, popupOptions);
    var popupHtml = jQuery('<div />').attr("id", "KSAP-Popover");
    if (popupStyle) {
        jQuery.each(popupStyle, function (property, value) {
            popupHtml.css(property, value);
        });
    } else {
        popupHtml.css({
            width: "315px",
            height: "16px"
        });
    }
    popupSettings.innerHtml = popupHtml.wrap("<div>").parent().clone().html();

    popupItem.ShowPopOver(popupSettings, false);
    popupItem.FreezePopOver();

    var popupId = popupItem.GetPopOverID();

    fnPositionDialogWindow(popupId);
    if (!popupOptions.sticky) {
        clickOutsideDialog(popupId, popupItem);
    }

    // Setup dialog information placeholder
    var retrieveForm = '<form id="retrieveForm" action="' + retrieveData.action + '" method="post" />'
    jQuery("body").append(retrieveForm);

    // Set item to display loading in
    var elementToBlock = jQuery("#KSAP-Popover");

    // Setup callback to finish placing and display dialog once loaded
    var successCallback = function (htmlContent) {

        // Set up and configure content to display
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

        // Display dialog content and final style changes
        if (jQuery("#KSAP-Popover").length) {
            popupItem.SetPopOverInnerHtml(component);
            fnPositionDialogWindow(popupId);
            //@TODO ksap-961 Convert to icon font instead of image
            var imageUrl = getConfigParam("kradUrl")+"/../themes/ksapboot/images/btnClose.png";
            if (popupOptions.close || typeof popupOptions.close === 'undefined') jQuery("#" + popupId + " .jquerypopover-innerHtml").append('<img src="'+imageUrl+'" class="ksap-popup-close"/>');
            jQuery("#" + popupId + " img.ksap-popup-close").on('click', function () {
                popupItem.HidePopOver();
                fnClosePopup();
            });
        }

        // Run hidden scripts and remove loader
        skipPageSetup = true; // Ajax return, set true to prevent reload of whole page.
        runHiddenScripts(getId);
        elementToBlock.unblock();
    };

    // Submit dialog
    ksapAjaxSubmitForm(retrieveData, successCallback, elementToBlock, "retrieveForm");

    // Clean up placeholder
    jQuery("form#retrieveForm").remove();
}

/**
 * Sets up and opens a dialog in the planner
 *
 * @param pageId - Id for the page being displayed
 * @param action - The controller mapping to use
 * @param methodToCall - The identifier for the method being mapped to
 * @param target - The html object its being opened on
 * @param e - Current event going on.
 * @param additionalData - Any additional data to be sent to the controller
 */
function ksapOpenDialog(pageId, action, methodToCall, target, e, additionalData) {
    // Compile data for opening the popup
    var t = jQuery(target);
    var retrieveData = {
        action : action,
        methodToCall : methodToCall,
        pageId : pageId
    };

    jQuery.extend(retrieveData, additionalData);

    // Setup popup options
    var popupOptions = {
        tail : {
            hidden : true
        },
        align : 'top',
        close : true
    };

    // Remove and close existing popup objects from window
    jQuery("#popupForm").remove();
    fnClosePopup();

    // Open window
    openDialogWindow(pageId + "_inner", retrieveData, action, {}, popupOptions, e);

    // Set character set for created popup object
    var form = jQuery("#popupForm");
    form.attr("accept-charset", "UTF-8");
}

/**
 * Sets up and submits a dialog to the controller.
 *
 * @param methodToCall - The identifier for the method being mapped to
 * @param e - Current event going on
 */
function ksapSubmitDialog(methodToCall, e, successCallback, errorCallback) {
    var button = jQuery(e.currentTarget);

    // Transpose loader icon on top of submit button
    var imageUrl = getConfigParam("kradUrl")+"/../themes/ksapboot/images/ajaxLoader.gif";
    button.block({
        message : '<img src="'+imageUrl+'"/>',
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

    // Set callbacks if needed
    if(successCallback == null ) successCallback = ksapAjaxSubmitSuccessCallback;
    if(errorCallback == null) errorCallback = ksapAjaxSubmitErrorCallback;

    // Setup ajax call for submit
    var form = jQuery("#popupForm");
    form.ajaxSubmit({
        data : ksapAdditionalFormData({
            methodToCall: methodToCall
        }),
        dataType : 'json',
        success : successCallback,
        error : errorCallback,
        complete : function() {
            // Remove loader
            button.unblock();
        }
    });

}

/**
 * Registers events to close the dialog if user clicks outside it.
 *
 * @param popoverId - Id of the dialog to close
 * @param element - Object the dialog is opened on.
 */
function clickOutsideDialog(popoverId, element) {
    jQuery("body").off("click");
    jQuery("body").on("click", function (e) {
        var tempTarget = (e.target) ? e.target : e.srcElement;
        if (jQuery(tempTarget).parents("#" + popoverId).length === 0) {
            element.RemovePopOver();
            jQuery("body").off("click");
        }
    });
}

/**
 * Closes and removes any popups or popovers on the page.
 */
function fnClosePopup() {
    var popover = jQuery("div.jquerypopover");
    var popup = jQuery("div.jquerybubblepopup");
    popover.remove();
    popup.remove();
}

/**
 * Used to position dialog in the center of the window
 *
 * @param popupBoxId - Id of the dialog to be positioned
 */
function fnPositionDialogWindow(popupBoxId) {
    if (parseFloat(jQuery("#" + popupBoxId).css("top")) < 0 || parseFloat(jQuery("#" + popupBoxId).css("left")) < 0 || jQuery("#" + popupBoxId + " main").hasClass("ksap-dialog-centered")) {
        var top = (document.documentElement && document.documentElement.scrollTop) || document.body.scrollTop;
        var left = (document.documentElement && document.documentElement.scrollLeft) || document.body.scrollLeft;
        var iTop = ( top + ( jQuery(window).height() / 2 ) ) - ( jQuery("#" + popupBoxId).height() / 2 );
        var iLeft = ( left + ( jQuery(window).width() / 2 ) ) - ( jQuery("#" + popupBoxId).width() / 2 );
        jQuery("#" + popupBoxId).css({top:iTop + 'px', left:iLeft + 'px'});
    }
}


/**
 * Handles the return events of a general ajax submit in ksap.
 * Triggering any needed events
 * Handle success or failure of call
 * Display response message
 *
 * @param response - Json string from the controller with events
 * @param textStatus - Text status to display if error occurs
 * @param jqXHR - Page status.
 */
function ksapAjaxSubmitSuccessCallback(response, textStatus, jqXHR) {
    if (response.success) {
        // Trigger events
        for (var key in response) {
            if (!response.hasOwnProperty(key))
                continue;
            var data = response[key];
            jQuery.event.trigger(key,[data])
        }

        // Display success response message in growl message
       /* if (response.message != null) {
            showGrowl(response.message);
        }*/

        // Close any open popups
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
 * Handles failure to submit ajax call
 *
 * @param response - Json string from the controller with events
 * @param textStatus - Text status to display if error occurs
 * @param jqXHR - Page status.
 */
function ksapAjaxSubmitErrorCallback(jqXHR, textStatus, errorThrown){
    // Display error growl
    if (textStatus == "parsererror")
        textStatus = "JSON Parse Error";
    showGrowl(errorThrown, jqXHR.status + " " + textStatus);
    fnClosePopup();
}

/**
 * Set message length options for planner notes
 *
 * @param jqObject - Element to set options on
 * @param opts - Options to set
 */
function setNoteMessageLength(jqObject, opts){
    jqObject.characterCount(opts);
}

function ksapOpenPlanDialog(pageId, action, methodToCall, target, e) {
    var t = jQuery(target);
    var retrieveData = {
        learningPlanId : t.data('learningplanid'),
        termId : t.data('termid'),
        planItemId : t.data('planitemid'),
        courseId : t.data('courseid'),
        backup : t.data('backup'),
        uniqueId : t.data('uniqueid')
    };
    jQuery.extend(retrieveData);
    ksapOpenDialog(pageId, action, methodToCall, target, e, retrieveData)
}

/**
 * Handles marking planned terms as disabled in the "Add to Plan" dialog.
 * Expects there to be a data attribute defined that holds a comma separated list of terms IDs that are already planned
 * @param inputElementId - Id of the input element that contains the select control and data attributes
 */
function ksapDisableTermsAsPlanned(inputElementId) {
    var inputElement = jQuery(inputElementId);
    var plannedTermIdString = inputElement.data('plannedtermids');
    var plannedTermIds = plannedTermIdString.split(",");
    for (i=0; i<plannedTermIds.length; i++) {
        var termId = plannedTermIds[i];
        // Find an option corresponding to this term and disable it
        var option = jQuery(inputElementId + "_control").find('option[value="' + termId + '"]');
        option.attr('disabled', 'disabled');
        // If this option is disabled and selected (probably the first in the list), select the next one
        if (option.is(':selected')) {
            option.next().attr('selected', 'selected');
        }
    }
}
