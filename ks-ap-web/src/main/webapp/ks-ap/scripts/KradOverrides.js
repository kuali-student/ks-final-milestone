/**
 * Fix under KSAP-265
 * Override of krad.actions.js.  Allowing alternate form name to be set on retrieve.
 */

function retrieveComponent(id, methodToCall, successCallback, additionalData, disableBlocking, formName) {
    var refreshComp = jQuery("#" + id);

    // if a call is made from refreshComponentUsingTimer() and the component does not exist on the page or is hidden
    // then get the handle of the refreshTimer and clear the timer. Also remove it from the refreshTimerComponentMap
    if (refreshComp === undefined || refreshComp.filter(':visible').length === 0) {
        var refreshHandle = refreshTimerComponentMap[id];
        if (!(refreshHandle === undefined)) {
            clearInterval(refreshHandle);
            delete refreshTimerComponentMap[id];

            return;
        }
    }

    if (!methodToCall) {
        methodToCall = kradVariables.REFRESH_METHOD_TO_CALL;
    }

    var kradRequest = new KradRequest();

    kradRequest.methodToCall = methodToCall;
    kradRequest.ajaxReturnType = kradVariables.RETURN_TYPE_UPDATE_COMPONENT;
    kradRequest.successCallback = successCallback;
    kradRequest.additionalData = additionalData;
    kradRequest.refreshId = id;
    if (formName != null) {
        kradRequest.formName = formName;
    }

    if (disableBlocking) {
        kradRequest.disableBlocking = disableBlocking;
    }

    kradRequest.send();
}

/**
 * Fix under KSAP-265
 * Override of krad.request.js.  Allowing alternate form name to be set on retrieve.
 */
KradRequest.prototype = {
    // name of the form to post
    formName: kradVariables.KUALI_FORM,

    // name of the controller method to be invoked
    methodToCall: "refresh",

    // additional data to send with the request (in addition to form data)
    additionalData: {},

    // indicates whether the request should be made with ajax or standard browser submit
    ajaxSubmit: true,

    // for ajax requests, specifies how the response should be handled
    ajaxReturnType: "update-page",

    // when the return type is update-component, indicates the id for the component that
    // should be updated
    refreshId: null,

    // indicates whether client side validation should be performed before making
    // the request (see ajaxReturnHandlers)
    validate: false,

    dirtyOnAction: false,

    clearDirtyOnAction: false,

    // when blocking is enabled will display this text with the blocking overlay
    loadingMessage: getMessage(kradVariables.MESSAGE_LOADING),

    // jQuery object that should be blocked while the request is sent, if empty
    // and return type is update-component, the component will be blocked, else the full window
    // will be blocked
    elementToBlock: null,

    // indicates whether blocking should be disabled for the request
    disableBlocking: false,

    // function or script that should be invoked before the request is sent
    // if the function returns false the request is not carried out
    // the function can optionally take the request object and modify any of the
    // request attributes (for example add additional data)
    // Note as well: the preSubmitCall can be given as a string or function object. When given as a string it may
    // optionally take the request by including the parameter 'this'. Other literal parameters may be passed as well
    // (literal on client, but useful for passing server side variables)
    preSubmitCall: null,

    // function or script that is invoked after a successful ajax request
    // the function may take the response contents as a parameter
    // Note as well: the successCallback can be given as a string or function object. When given as a string it may
    // optionally take the response contents by including the parameter 'responseContents'. Other literal parameters
    // may be passed as well (literal on client, but useful for passing server side variables)
    successCallback: null,

    // function or script that is invoked after an error is encountered from an ajax request
    // (including when an incident page is returned. The function may take the response contents as a parameter
    // Note as well: the successCallback can be given as a string or function object. When given as a string it may
    // optionally take the response contents by including the parameter 'responseContents'. Other literal parameters
    // may be passed as well (literal on client, but useful for passing server side variables)
    errorCallback: null,

    // called to make the request and handle the response
    send: function () {
        var data = {};

        // invoke validateForm if validate flag is true, if returns false do not continue
        if (this.validate && !validateForm()) {
            clearHiddens();

            return;
        }

        // invoke the preSubmitCall script, if it evaluates to false return
        if (this.preSubmitCall) {
            // expose a variable for preSubmitCode
            var kradRequest = this;
            if (typeof this.preSubmitCall == "string") {
                var preSubmitCode = "(function(){" + this.preSubmitCall + "})();";
                var preSubmitValid = eval(preSubmitCode);
            } else {
                var preSubmitValid = this.preSubmitCall(this);
            }

            if (!preSubmitValid) {
                clearHiddens();

                return;
            }
        }

        //reset dirty form state
        if (this.clearDirtyOnAction){
            dirtyFormState.reset();
        }

        //increase dirty field count when this flag is true
        if (this.dirtyOnAction){
            dirtyFormState.incrementDirtyFieldCount();
        }

        // check for non-ajax request
        if (!this.ajaxSubmit) {
            this._submit();

            return;
        }

        data.methodToCall = this.methodToCall;
        data.ajaxReturnType = this.ajaxReturnType;
        data.ajaxRequest = this.ajaxSubmit;

        if (this.refreshId) {
            data.updateComponentId = this.refreshId;
        }

        if (this.additionalData) {
            jQuery.extend(data, this.additionalData);
        }

        var jsonViewState = getSerializedViewState();
        if (jsonViewState) {
            jQuery.extend(data, {clientViewState: jsonViewState});
        }

        // check if called from a lightbox, if it is set the lightboxCompId
        var lightboxCompId = undefined;
        if (jQuery('#kualiLightboxForm').children(':first').length == 1) {
            lightboxCompId = jQuery('#kualiLightboxForm').children(':first').attr('id');
        }

        // create a reference to the request for ajax callbacks
        var request = this;

        var submitOptions = {
            data: data,
            success: function (response) {
                var responseContents = document.createElement('div');
                responseContents.innerHTML = response;

                // create a response object to process the response contents
                var kradResponse = new KradResponse(responseContents);
                kradResponse.formName = request.formName;
                kradResponse.processResponse();

                var hasError = checkForIncidentReport(response);
                if (!hasError) {
                    if (request.successCallback) {
                        if (typeof request.successCallback == "string") {
                            eval(request.successCallback);
                        } else {
                            request.successCallback(responseContents);
                        }
                    }
                } else if (request.errorCallback) {
                    if (typeof request.errorCallback == "string") {
                        eval(request.errorCallback);
                    } else {
                        request.errorCallback(responseContents);
                    }
                }

                clearHiddens();

                // for lightbox copy data back into lightbox
                if (lightboxCompId !== undefined) {
                    var component = jQuery('#' + lightboxCompId).clone(true, true);

                    addIdPrefix(jQuery('#' + lightboxCompId), 'tmpForm_');
                    jQuery('#tmpLightbox_' + lightboxCompId).replaceWith(component);
                    jQuery('#' + lightboxCompId).css('display', '');
                }

            },
            error: function (jqXHR, textStatus) {
                if (request.errorCallback) {
                    if (typeof request.errorCallback == "string") {
                        eval(request.errorCallback);
                    } else {
                        request.errorCallback();
                    }
                }
                else {
                    alert("Request failed: " + textStatus);
                }
            }
        };

        this._setupBlocking(submitOptions);

        // for lightbox copy data back into form
        // TODO: do we need this here again? Already in the success callback
        if (lightboxCompId !== undefined) {
            var component = jQuery('#' + lightboxCompId).clone(true, true);

            addIdPrefix(jQuery('#' + lightboxCompId), 'tmpLightbox_');
            jQuery('#tmpForm_' + lightboxCompId).replaceWith(component);
        }

        jQuery("#" + this.formName).ajaxSubmit(submitOptions);
    },

    // handles the request as standard form submit
    _submit: function () {
        // write out methodToCall as hidden
        writeHiddenToForm("methodToCall", this.methodToCall);

        // if additional data write out as hiddens
        for (key in this.additionalData) {
            writeHiddenToForm(key, this.additionalData[key]);
        }

        // start the loading indicator (will be removed on page load)
        if (!this.disableBlocking) {
            showLoading(this.loadingMessage);
        }

        var jsonViewState = getSerializedViewState();
        if (jsonViewState) {
            writeHiddenToForm("clientViewState", jsonViewState);
        }

        // check for file inputs and set encoding, this is handled for us with the ajax submits (using jqform)
        var fileInputs = jQuery('input[type=file]:enabled[value!=""]', '#' + this.formName);

        var hasFileInputs = fileInputs.length > 0;
        if (hasFileInputs) {
            jQuery('#' + this.formName).attr('enctype', 'multipart/form-data');
        }

        // submit
        jQuery('#kualiForm').submit();
    },

    // sets up the component or page blocking for an ajax request
    _setupBlocking: function (options) {
        // initialize element to block if necessary
        if (!this.elementToBlock && !this.disableBlocking &&
            (this.ajaxReturnType == kradVariables.RETURN_TYPE_UPDATE_COMPONENT) && this.refreshId) {
            this.elementToBlock = jQuery("#" + this.refreshId);
        }

        // create a reference to the request for ajax callbacks
        var request = this;

        // adding blocking configuration to ajax options
        var elementBlockingOptions = {
            beforeSend: function () {
                if (nonEmpty(request.elementToBlock) && (request.elementToBlock.is(":hidden, .uif-placeholder"))) {
                    var replaceElement = true;
                    request.elementToBlock.show();
                }

                if (!request.disableBlocking) {
                    showLoading(request.loadingMessage, request.elementToBlock, replaceElement);
                }
            },
            complete: function (jqXHR, textStatus) {
                // note that if you want to unblock simultaneous with showing the new retrieval
                // you must do so in the successCallback
                if (!request.disableBlocking) {
                    hideLoading(request.elementToBlock);
                }

                resetSessionTimers();
            },
            error: function () {
                if (nonEmpty(request.elementToBlock) && request.elementToBlock.hasClass("uif-placeholder")) {
                    request.elementToBlock.hide();
                }
                else if (!request.disableBlocking) {
                    hideLoading(request.elementToBlock);
                }
            },
            statusCode: {403: function (jqXHR, textStatus) {
                if (nonEmpty(request.elementToBlock) && request.elementToBlock.hasClass("uif-placeholder")) {
                    request.elementToBlock.hide();
                }
                else if (!request.disableBlocking) {
                    hideLoading(request.elementToBlock);
                }

                handleAjaxSessionTimeout(jqXHR.responseText);
            }}
        };

        jQuery.extend(options, elementBlockingOptions);
    }
}

/**
 * Fix under KSAP-265
 * Override of krad.response.js.  Allowing alternate form name to be set on retrieve.
 */
KradResponse.prototype = {
    // name of the form to posted by the request
    formName: kradVariables.KUALI_FORM,

    // full response contents
    responseContents: null,

    // maps return types to handler function names
    handlerMapping: {"update-page": "updatePageHandler", "update-component": "updateComponentHandler",
        "update-view": "updateViewHandler", "redirect": "redirectHandler",
        "display-lightbox": "displayLightBoxHandler", "update-dialog":"updateDialogHandler"},

    // invoked to process the response contents by invoking necessary handlers
    processResponse: function () {
        var responseFn = this;

        // iterate over returned contents divs and invoke handler
        jQuery(this.responseContents).children().each(function () {
            var div = jQuery(this);

            // get the return type sent by the server
            var returnType = div.data("returntype");

            // find the handler function from the mapping
            var functionName = responseFn.handlerMapping[returnType];

            // invoke the handler function
            if (functionName in responseFn) {
                eval("responseFn."+functionName+"(div, div.data())");
            }

            hideEmptyCells();
        });
    },

    // finds the page content in the returned content and updates the page, then processes breadcrumbs and hidden
    // scripts. While processing, the page contents are hidden
    updatePageHandler: function (content, dataAttr) {
        var page = jQuery("#page_update", content);

        // TODO: should this be hiding page or pageInLayout?
        page.hide();

        // give a selector that will avoid the temporary iframe used to hold ajax responses by the jquery form plugin
        var pageInLayout = "#" + this.formName
            + " #" + kradVariables.VIEW_CONTENT_HEADER_CLASS
            + " #" + kradVariables.PAGE_CONTENT_WRAPPER;
        hideBubblePopups(pageInLayout);

        // update page contents from response
        jQuery(pageInLayout).empty().append(page.find(">*"));

        pageValidatorReady = false;
        runHiddenScripts(pageInLayout, true, true);

        jQuery(pageInLayout).show();
    },


    // finds the dialog content in the returned content and updates the view
    updateDialogHandler: function (content, dataAttr) {
        var id = dataAttr.updatecomponentid;
        var component = jQuery("#" + id + "_update", content);

        // remove old stuff
        if (jQuery("#" + id + "_errors").length) {
            jQuery("#" + id + "_errors").remove();
        }

        jQuery("input[data-for='" + id + "']").each(function () {
            jQuery(this).remove();
        });

        // replace component
        if (jQuery("#" + id).length) {
            jQuery("#" + id).replaceWith(component.html());
        }

        runHiddenScripts(id);
    },


    // retrieves the component with the matching id from the server and replaces a matching
    // _refreshWrapper marker span with the same id with the result.  In addition, if the result contains a label
    // and a displayWith marker span has a matching id, that span will be replaced with the label content
    // and removed from the component.  This allows for label and component content separation on fields
    updateComponentHandler: function (content, dataAttr) {
        var id = dataAttr.updatecomponentid;
        var elementToBlock = jQuery("#" + id);

        hideBubblePopups(elementToBlock);

        var component = jQuery("#" + id + "_update", content);

        var displayWithId = id;

        // special label handling, if any
        var theLabel = jQuery("#" + displayWithId + "_label_span", component);
        if (jQuery(".displayWith-" + displayWithId).length && theLabel.length) {
            theLabel.addClass("displayWith-" + displayWithId);
            jQuery("span.displayWith-" + displayWithId).replaceWith(theLabel);
            component.remove("#" + displayWithId + "_label_span");
        }

        // remove old stuff
        if (jQuery("#" + id + "_errors").length) {
            jQuery("#" + id + "_errors").remove();
        }

        jQuery("input[data-for='" + id + "']").each(function () {
            jQuery(this).remove();
        });

        // replace component
        if (jQuery("#" + id).length) {
            jQuery("#" + id).replaceWith(component.html());
        }

        if (jQuery("#" + id).parent().is("td")) {
            jQuery("#" + id).parent().show();
        }

        // lightbox specific processing
        if (jQuery('#renderedInLightBox').val() == 'true') {
            jQuery("#" + id).css('display', 'none');
        }

        var newComponent = jQuery("#" + id);

        var displayWithLabel = jQuery(".displayWith-" + displayWithId);
        displayWithLabel.show();
        if (displayWithLabel.parent().is("td") || displayWithLabel.parent().is("th")) {
            displayWithLabel.parent().show();
        }

        // assume this content is open if being refreshed
        var open = newComponent.attr("data-open");
        if (open != undefined && open == "false"){
            newComponent.attr("data-open", "true");
            newComponent.show();
        }

        // runs scripts on the span or div with id
        runHiddenScripts(id);

        // Only for table layout collections. Keeps collection on same page.
        var currentPage = retrieveFromSession(id + ":currentPageRichTable");
        if (currentPage != null) {
            openDataTablePage(id, currentPage);
        }

        elementToBlock.unblock({onUnblock: function () {
            jQuery(component).find("#" + id).addClass(kradVariables.PROGRESSIVE_DISCLOSURE_HIGHLIGHT_CLASS);
            newComponent.animate({backgroundColor:"transparent"}, 6000);
            jQuery(component).find("#" + id).animate({backgroundColor:"transparent"}, 6000);
        }
        });
    },

    // performs a redirect to the URL found in the returned contents
    redirectHandler: function (content, dataAttr) {
        // get contents between div and do window.location = parsed href
        window.location.href = jQuery(content).text();
    },

    // replaces the view with the given content and run the hidden scripts
    updateViewHandler: function (content, dataAttr) {
        jQuery('#' + kradVariables.APP_ID).replaceWith(content);

        runHiddenScriptsAgain();
    },

    // displays the response contents in a lightbox
    displayLightBoxHandler: function (content, dataAttr) {
        showLightboxContent(content);
    }
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