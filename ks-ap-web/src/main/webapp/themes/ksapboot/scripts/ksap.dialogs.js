// Javascripts for the KSAP Dialog Functionality.

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
    stopEvent(e);
    fnClosePopup();

    var target = (e.currentTarget) ? e.currentTarget : e.srcElement;
    var popupItem = (typeof popupOptions.selector == "undefined") ? jQuery(target) : jQuery(target).parents(popupOptions.selector);

    if (!popupItem.HasPopOver()) popupItem.CreatePopOver({manageMouseEvents:false});

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
        distance:"0px",
        openingSpeed:5,
        closingSpeed:5
    };

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

    fnPositionDialogWindow(popupId);
    if (!popupOptions.sticky) {
        clickOutsideDialog(popupId, popupItem);
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
            fnPositionDialogWindow(popupId);
            //@TODO ksap-961 Convert to icon font instead of image
            if (popupOptions.close || typeof popupOptions.close === 'undefined') jQuery("#" + popupId + " .jquerypopover-innerHtml").append('<img src="../themes/ksapboot/images/btnClose.png" class="ksap-popup-close"/>');
            jQuery("#" + popupId + " img.ksap-popup-close").on('click', function () {
                popupItem.HidePopOver();
                fnClosePopup();
            });
        }
        skipPageSetup = true; // Ajax return, set true to prevent reload of whole page.
        runHiddenScripts(getId);
        elementToBlock.unblock();
    };

    ksapAjaxSubmitForm(retrieveData, successCallback, elementToBlock, "retrieveForm");
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
 */
function ksapOpenDialog(pageId, action, methodToCall, target, e) {
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
    openDialogWindow(pageId + "_inner", retrieveData, action, {}, popupOptions, e);
    var form = jQuery("#popupForm");
    form.attr("accept-charset", "UTF-8");
}

/**
 * Sets up and submits a dialog to the controller.
 * @param methodToCall
 * @param e - Current event going on
 */
function ksapSubmitDialog(methodToCall, e) {
    var button = jQuery(e.currentTarget);

    button.block({
        message : '<img src="../themes/ksapboot/images/ajaxLoader.gif"/>',
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
        data : ksapAdditionalFormData({
            methodToCall: methodToCall
        }),
        dataType : 'json',
        success : ksapDialogCallback,
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
 * Close the current popup
 *
 * Used for the manual close of the popup.
 */
function fnClosePopup() {
    var body = jQuery("body");
    var popover = jQuery("div.jquerypopover");
    var popup = jQuery("div.jquerybubblepopup");

    if (body.HasPopOver()) {
        body.HidePopOver();
        body.RemovePopOver();
    }

    popover.remove();
    popup.remove();
    body.off("click");
}

/**
 * Used to position dialog in the center of the window
 *
 * @param popupBoxId - Id of the dialog to be positioned
 */
function fnPositionDialogWindow(popupBoxId) {
    if (parseFloat(jQuery("#" + popupBoxId).css("top")) < 0 || parseFloat(jQuery("#" + popupBoxId).css("left")) < 0) {
        var top = (document.documentElement && document.documentElement.scrollTop) || document.body.scrollTop;
        var left = (document.documentElement && document.documentElement.scrollLeft) || document.body.scrollLeft;
        var iTop = ( top + ( jQuery(window).height() / 2 ) ) - ( jQuery("#" + popupBoxId).height() / 2 );
        var iLeft = ( left + ( jQuery(window).width() / 2 ) ) - ( jQuery("#" + popupBoxId).width() / 2 );
        jQuery("#" + popupBoxId).css({top:iTop + 'px', left:iLeft + 'px'});
    }
}

/**
 * Routes json responses from the controller executing the corresponding events to change the display of the planner page.
 * @param response - Json string from the controller with events
 * @param textStatus - Text status to display if error occurs
 * @param jqXHR - Page status.
 */
function ksapDialogCallback(response, textStatus, jqXHR) {
    if (response.success) {
        // Execute changes to the planner
        for (var key in response) {
            if (!response.hasOwnProperty(key))
                continue;
            var data = response[key];
            jQuery.event.trigger(key,[data])
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