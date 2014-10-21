/*
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// TODO: KSENROLL-9721: Need to create a confirmation dialog in browser as opposed to make a server side round trip

KradRequest.prototype.confirm = function (dialogId) {
        if (!this.confirmed) {
            var kradRequest = this;
            jQuery("#" + dialogId).on('dialogresponse.uif', function (e) {
                kradRequest.handleConfirmation(e);
            });

            showLightboxComponent(dialogId);

            return false;
        }

        return true;
    };

    // handles the response of a confirmation, resending the action if the user selected yes
KradRequest.prototype.handleConfirmation = function (event) {
        closeLightbox();

        if (event.value == "Y") {
            this.confirmed = true;

            this.send();
        }
    };


// TODO: KSENROLL-12930: Remove this override once WHITESPACE_ONLY is the option the js compiler uses during minification
// Overriding send.  This was taken from rice 2.4.0.  Only change was to comment out the var kradRequest = this; in favor of the eval line below it.
KradRequest.prototype.send = function () {
    var data = {};

    // invoke validateForm if validate flag is true, if returns false do not continue
    if (this.validate && !validateForm()) {
        clearHiddens();

        return;
    }

    // invoke the preSubmitCall script, if it evaluates to false return
    if (this.preSubmitCall) {
        // expose a variable for preSubmitCode
//        var kradRequest = this;
        eval("var kradRequest = this;");
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
        dirtyFormState.reset();

        // submit non ajax call
        this._submit();
        clearHiddens();

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

            // for lightbox copy data back into lightbox
            if (lightboxCompId !== undefined) {
                jQuery('#' + lightboxCompId + "_dialogPlaceholder").empty();
            }

            // create a response object to process the response contents
            var kradResponse = new KradResponse(responseContents);
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

    // for lightbox copy data back into form because its content exist outside it
    // TODO: do we need this here again? Already in the success callback
    if (lightboxCompId !== undefined) {
        var component = jQuery('#' + lightboxCompId).clone(true, true);

        jQuery('#' + lightboxCompId + "_dialogPlaceholder").append(component);
    }

    jQuery("#" + kradVariables.KUALI_FORM).ajaxSubmit(submitOptions);
};