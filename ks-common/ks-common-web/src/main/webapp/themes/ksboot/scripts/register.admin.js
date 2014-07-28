/*
 * Copyright 2005-2014 The Kuali Foundation
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

var CLIENT_STATE_PROPERTY_NAME = "clientState";
var POLLING_QUERY_METHOD_NAME = "queryForRegistrationStatus";
var REGISTERING_BARS_ID = "KS-AdminRegistration-Registering";
var REGISTER_BUTTON_ID = "KS-AdminRegistration-RegisterButton";

//////////////////////////////////////
// Polling Functions
//////////////////////////////////////

/**
 *
 * @type {boolean}
 */
var polling = false;

/**
 *
 * @type {null}
 */
var delay = null;

/**
 * This method starts the polling action if it is not already running
 * @param time
 */
function startPolling(time) {
    if (!polling) {
        delay = time;
        polling = true;
        sendPoll();

        var registerButton = jQuery("#" + REGISTER_BUTTON_ID);
        if (registerButton) {
            registerButton.hide();
        }
    }
}

/**
 * Interrupt the polling action by setting the polling flag to false.
 */
function stopPolling() {
    polling = false;
    delay = null;

    var busyIndicator = jQuery("#" + REGISTERING_BARS_ID);
    if (busyIndicator) {
        busyIndicator.hide();
    }

    var registerButton = jQuery("#" + REGISTER_BUTTON_ID);
    if (registerButton) {
        registerButton.show();
    }
}

/**
 * This method polls the course registration status at set intervals,
 * until all the courses have been processed
 */
function sendPoll() {

    if (polling) {
        var url = jQuery("#" + kradVariables.KUALI_FORM).attr("action");
        var queryData = {};

        console.log("Query");

        queryData.methodToCall = POLLING_QUERY_METHOD_NAME;
        queryData.ajaxRequest = true;
        queryData.ajaxReturnType = "update-none";
        queryData.formKey = jQuery("input[name='" + kradVariables.FORM_KEY + "']").val();

        jQuery.ajax({
            url: url,
            dataType: "json",
            beforeSend: null,
            complete: null,
            error: null,
            data: queryData,
            success: function (data) {

                var updateIds = data.updateIds;
                jQuery(updateIds).each(function (index, id) {
                    retrieveComponent(id);
                });

                // if no more updates expected, stop polling
                var stop = data.stop;
                if (!stop) {
                    //using recursive setTimeout instead of setInterval so that long running polls don't interfere with subsequent polls
                    setTimeout(sendPoll, delay);
                } else {
                    stopPolling();
                }

                var state = data.clientState;
                if (state) {
                    jQuery("input[name='" + CLIENT_STATE_PROPERTY_NAME + "']").val(state);
                }

            }
        });
    }
}

//////////////////////////////////////
// State Validation Functions
//////////////////////////////////////

/**
 * Called when a 'Go' button is clicked on the admin registration screen to set client state to an earlier state
 * for validation purposes.
 *
 * @param component
 * @param state
 */
function goAction(component, state) {
    if (state) {
        jQuery("input[name='" + CLIENT_STATE_PROPERTY_NAME + "']").val(state);
    }

    var action = jQuery(component);

    var kradRequest = new KradRequest(action);
    kradRequest.send();
}

/**
 * Using the KRAD validation framework to add rules to components.
 *
 * Alternatively we could've used the Case and When Constraints on the objects but that does not allow
 * us to display a custom message when the validation fails.
 *
 * @param controlName
 * @param requiredName
 * @param states
 * @param message
 */
function setValidation(requiredName, states, message) {

    var check = function () {
        var i;
        for (i = 0; i < states.length; i++) {
            if ((coerceValue(CLIENT_STATE_PROPERTY_NAME) == states[i])) {
                return true;
            }
        }
        return false;
    };

    runValidationScript(function () {
        setupShowReqIndicatorCheck(CLIENT_STATE_PROPERTY_NAME, requiredName, check);
    });

    runValidationScript(function () {
        jQuery('[name="' + requiredName + '"]').rules("add", {
            required: check,
            messages: {
                required: message
            }
        });
    });
}

// Registration results
function renderResults() {

    jq('.error-result').each(function () {
        jq(this).parentsUntil("tr").parent().addClass('alert-danger');
    });

    jq('.warning-result').each(function () {
        jq(this).parentsUntil("tr").parent().addClass('alert-warning');
    });

    jq('.success-result').each(function () {
        jq(this).parentsUntil("tr").parent().addClass('alert-success');
    });
}