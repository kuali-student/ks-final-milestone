var BUBBLEPOPUP_DEFAULT_OPTIONS = {
    position: 'bottom',
    align: 'left',
    tail: { align:'left', hidden:false },
    manageMouseEvents: false,
    themePath: '../krad/plugins/tooltip/jquerybubblepopup-theme/',
    themeName: 'ks-form',
    closingSpeed: 125
};


/**
 * Uses jQuery UI Auto-complete widget to provide suggest options for the given
 * field.  See <link>http://jqueryui.com/demos/autocomplete/</link> for
 * documentation on this widget
 *
 * @param controlId -
 *     id for the html control the autocomplete will be enabled for
 * @param options -
 *     map of option settings (option name/value pairs) for the widget
 * @param queryFieldId -
 *     id for the attribute field the control belongs to, used when making the
 *     request to execute the associated attribute query
 * @param queryParameters -
 *     map of parameters that should be sent along with the query. map key gives
 * @param localSource -
 *     indicates whether the suggest options will be provided locally instead of
 *     by a query
 * @param suggestOptions -
 *     when localSource is set to true provides the suggest options the name of
 *     the parameter to send, and the value gives the name of the field to pull
 *     the value from
 * @param labelProp the property name that holds the label for the plugin
 * @param valueProp the property name that holds the value for the plugin
 * @param returnCustomObj if true, the full object is expected as return value
 * @param loadingImageEnabled -
 *     When true, an animated loading image is displayed in the input field
 *     while the search is executing.
 * @param customEntryAllowed -
 *     When true, the value entered in the input does not have to match the
 *     search results.  Otherwise the value will be removed and a brief error
 *     message displayed inside the input field.
 * @param comboboxButtonEnabled -
 *     Adds combobox button which starts search when clicked.  If no search term
 *     is given all items will be returned.
 */
function createSuggest2(controlId, options, queryFieldId, queryParameters, localSource, suggestOptions,
                        labelProp, valueProp, returnCustomObj,
                        loadingImageEnabled, customEntryAllowed, comboboxButtonEnabled) {
//CODE COPIED FROM RICE'S KRAD.WIDGET.JS createSuggest(..) FUNCTION (WITH ONE ADDITION)
    if (localSource) {
        options.source = suggestOptions;
    }
    else {
        options.source = function (request, response) {
            var successFunction = function (data) {
                response(data.resultData);
            };

            //special success logic for the object return case with label/value props specified
            if (returnCustomObj && (labelProp || valueProp)) {
                successFunction = function (data) {
                    var isObject = false;

                    if (data.resultData && data.resultData.length && data.resultData[0]) {
                        isObject = (data.resultData[0].constructor === Object);
                    }

                    if (data.resultData && data.resultData.length && isObject) {
                        //find and match props, and set them into each object so the autocomplete plugin can read them
                        jQuery.each(data.resultData, function (index, object) {
                            if (labelProp && object[labelProp]) {
                                object.label = object[labelProp];
                            }

                            if (valueProp && object[valueProp]) {
                                object.value = object[valueProp];
                            }
                        });
                        response(data.resultData);
                    }
                    else {
                        response(data.resultData);
                    }
                };
            }

            var queryData = {};

            queryData.blankQueryTermAllowed = (String(comboboxButtonEnabled) == "true"); // added
            queryData.methodToCall = 'performFieldSuggest';
            queryData.ajaxRequest = true;
            queryData.ajaxReturnType = 'update-none';
            queryData.formKey = jQuery("input#formKey").val();
            queryData.queryTerm = request.term;
            queryData.queryFieldId = queryFieldId;

            for (var parameter in queryParameters) {
                queryData['queryParameter.' + parameter] = coerceValue(queryParameters[parameter]);
            }

            jQuery.ajax({
                url: jQuery("form#kualiForm").attr("action"),
                dataType: "json",
                beforeSend: null,
                complete: null,
                error: null,
                data: queryData,
                success: successFunction
            });
        };
    }

    jQuery(document).ready(function () {
        jQuery("#" + controlId).autocomplete(options);
    });
//CODE COPIED FROM RICE - END

    if (loadingImageEnabled) {
        jQuery("#" + controlId).addClass("ks-loading-image");
    }

    var input = jQuery("#" + controlId);
    input.wrap( "<span class='ks-ui-autocomplete-input'/>" );

    input.keypress(function(event) {
        // prevent Enter in input when autosuggest is open, and close menu
        if ((input.autocomplete("widget").is(":visible"))
        &&  (event.keyCode == 10 || event.keyCode == 13)) {
            stopEvent(event);
            input.autocomplete("close");
        }
    });

    if (!customEntryAllowed) {  // make sure input value matches autosuggest list
        //input.on("autocompletechange", function(event,ui) {
        input.on("blur", function(event) {  // blur seems to work better than autocompletechange
            var value = input.val().trim();
            if (value) {
                var valueLowerCase = value.toLowerCase(),
                    valid = false;
                jQuery("ul.ui-autocomplete > li.ui-menu-item").each(function() {
                    if ( jQuery(this).text().trim().toLowerCase() === valueLowerCase) {
                        valid = true;
                        return false;
                    }
                });
                if (!valid ) {
                    // Show error message and remove the invalid value
                    var w = input.css("width");  // hmm - error class changes width
                    input.addClass("ks-ui-autocomplete-input-error").css("width",w).val("NO MATCH");
                    var timer1 = setTimeout("_removeError()",3000);
                    input.on("focus.autocomplete-error", function() {
                        clearTimeout(timer1);
                        _removeError();
                    });

                    _removeError = function() {
                        input.val(value).removeClass("ks-ui-autocomplete-input-error");
                        input.off("focus.autocomplete-error");
                    }
                }
            }
        });
    }

    if (comboboxButtonEnabled) {
        jQuery("<a>")
            .attr("tabIndex", -1)
            .attr("title", "Show All Items")
            .insertAfter(input)
            .button({
                icons: { primary:"ui-icon-triangle-1-s" },
                text: false
            })
            .removeClass("ui-corner-all")
            .click(function(event,ab) {
                input.focus();
                var savedMinLength = input.autocomplete("option","minLength");
                var term = input.val();//jQuery.trim(input.val());
                input.autocomplete("option", "minLength", term.length);
                input.autocomplete("search", term);
                input.autocomplete("option", "minLength", savedMinLength); // reset to original value
            });
    }
}


 /*******************************************************************************
 *  Open a hidden section in a jquery bubblepopup using an event like onclick
 *  and freeze it until the user clicks outside of the popup, or on the
 *  optional close button.
 *  Parameters:
 *      e            : event object (required)
 *      contentId    : id of hidden section with content (required)
 *      popupOptions : map of bubblepopup options (optional)
 *      closeButton  : when true, a small close button is rendered in the
 *                     top-right corner of the popup (optional)
 ******************************************************************************/
function openPopupContent(e, contentId, popupOptions, closeButton) {
   _openPopupContentFunction(e, contentId, popupOptions, closeButton)
}

/*******************************************************************************
 *  Open a hidden section in a jquery bubblepopup after an ajax call and
 *  freeze it until the user clicks outside of the popup, or on the optional
 *  close button.
 *  Parameters:
 *      targetId     : target id to receive popup (required)
 *      contentId    : id of hidden section with content (required)
 *      popupOptions : map of bubblepopup options (optional)
 *      closeButton  : when true, a small close button is rendered in the
 *                     top-right corner of the popup (optional)
 ******************************************************************************/
function openPopupContentViaAjax(targetId, contentId, popupOptions, closeButton) {
    _openPopupContentFunction(targetId, contentId, popupOptions, closeButton);
}


var gCurrentBubblePopupId;
// PAGE_LOAD_EVENT is probably better, but not available in Rice 2.2.0
jQuery(document).on(kradVariables.VALIDATION_SETUP_EVENT, function(event) {
    openPopupContentsWithErrors();
});


/*******************************************************************************
 *  Locate all bubblepopup content and see if any have a error displayed (via
 *  class "uif-hasError").  If so, locate the action which opens the content
 *  and submit the click event for that action.
 ******************************************************************************/
function openPopupContentsWithErrors() {
    var hiddenScript,popupFormId;
    var bubblePopupContent = {};
    jQuery("div.uif-bubblepopup-content").each(function(){
        if (bubblePopupContent[this.id] == true) {
            // .detach() apparently creates duplicates in the DOM, and this code eliminates them
            return false;
        }
        bubblePopupContent[this.id] = true;

        if (jQuery('.uif-hasError',jQuery(this)).length > 0) {
            popupFormId = this.id;
            // find the action linked to this popup via the openPopupContent() function:
            jQuery('.uif-action').siblings('input[type="hidden"][data-role="script"]').each(function(){
                hiddenScript = jQuery(this).val();
                if ((hiddenScript.indexOf('openPopupContent') != -1)
                    &&  (hiddenScript.indexOf(popupFormId) != -1)) {
                    var saveSpeed = (typeof BUBBLEPOPUP_DEFAULT_OPTIONS['openingSpeed'] === "undefined") ?
                        -1 : BUBBLEPOPUP_DEFAULT_OPTIONS['openingSpeed'];
                    BUBBLEPOPUP_DEFAULT_OPTIONS['openingSpeed'] = 1;  // open popup as fast as possible
                    jQuery('#'+jQuery(this).attr('data-for')).click();
                    BUBBLEPOPUP_DEFAULT_OPTIONS['openingSpeed'] = saveSpeed;
                    return false;  // break from .each
                }
            });
        }
    });
}


/*******************************************************************************
 *  Attempt to prevent event from bubbling up
 ******************************************************************************/
function stopEvent(e) {
    if (!e) {
        var e = window.event
    };
    if (e.stopPropagation) {
        e.preventDefault();
        e.stopPropagation();
    } else {
        e.returnValue = false;
        e.cancelBubble = true;
    }
    return false;
}



/*** PRIVATE METHODS **********************************************************/

// See documentation for "openPopupContent" functions, above
function _openPopupContentFunction(source, contentId, popupOptions, closeButton) {
    var popupTarget;
    if (typeof source === "string") {
        popupTarget = jQuery("#" + source);
    } else {  // source is a trigger event
        stopEvent(source);
        popupTarget = jQuery((source.currentTarget) ? source.currentTarget : source.srcElement);
    }

    // save open property before closing popups
    var isPopupOpen = popupTarget.IsBubblePopupOpen();

    // close the current popup
    if (gCurrentBubblePopupId) {
        _hideBubblePopup(jQuery("#"+gCurrentBubblePopupId));
    }
    //jQuery(".uif-tooltip").HideAllBubblePopups(); // probably not needed

    if (isPopupOpen) {  // action toggles popup on/off
        return;
    }

    gCurrentBubblePopupId = popupTarget.attr('id');
    var clickName = "click." + gCurrentBubblePopupId;

    // add required class uif-tooltip to action and create popup
    if (!popupTarget.HasBubblePopup()) {
        popupTarget.addClass("uif-tooltip");
        //initBubblePopups();  // shotgun approach to CreateBubblePopup, versus...
        popupTarget.CreateBubblePopup(".uif-tooltip");

        var popupContent = jQuery("#" + contentId);

        if (closeButton) {
            var closeButton = jQuery('<div class="uif-popup-closebutton"/>');
            closeButton.on( clickName, function(){_hideBubblePopup(popupTarget)} );
            popupContent.prepend(closeButton);
        }

        // Odd error work-around with 2 popup forms: pressing Enter in a text
        // field on the second popup causes a click event on the first button.
        // True.
        jQuery("input,select",popupContent).keypress(function(event){
            if (event.keyCode == 10 || event.keyCode == 13) {
                stopEvent(event);
            }
        });
    }

    var clonedDefaultOptions = jQuery.extend({}, BUBBLEPOPUP_DEFAULT_OPTIONS);
    jQuery.extend(clonedDefaultOptions, popupOptions, {innerHtmlId:contentId});
    popupTarget.ShowBubblePopup(clonedDefaultOptions,true);
    popupTarget.FreezeBubblePopup();

    // close popup on any click outside current popup
    jQuery(document).on(clickName, function(e) {
        var docTarget = jQuery((e.target) ? e.target : e.srcElement);
        if (docTarget.parents("div.jquerybubblepopup").length === 0) {
            _hideBubblePopup(popupTarget);
        }
    });

    // Note: afterHidden property causes openPopupContentsWithErrors() to break
    function _hideBubblePopup(target) {
        target.HideBubblePopup();
        jQuery(document).off("click."+target.attr('id'));
        gCurrentBubblePopupId = "";
    }
}
