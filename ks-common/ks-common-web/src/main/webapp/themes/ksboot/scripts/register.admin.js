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
var REFRESH_DETAIL_METHOD_NAME = "refreshRegistrationResults";

var POLLING_HIDE = ".hide-when-polling";
var POLLING_SHOW = ".show-when-polling";
var ERROR_RESULT = ".error-result";
var WARNING_RESULT = ".warning-result";
var SUCCESS_RESULT = ".success-result";
var GROWL_SUCCESS = "SUCCESS";

var REGISTRATION_TABS_ID = "KS-AdminRegistration-RegistrationTabs";
var REGISTERED_TAB_ID = "KS-AdminRegistration-RegisteredTab_tab";
var RESULTS_COLL_ID = "KS-AdminRegistration-Results";

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

        jq(POLLING_HIDE).each(function () {
            jq(this).hide();
        });

        jq(POLLING_SHOW).each(function () {
            jq(this).show();
        });
    }

}

/**
 * Interrupt the polling action by setting the polling flag to false.
 */
function stopPolling() {
    polling = false;
    delay = null;

    jq(POLLING_HIDE).each(function () {
        jq(this).show();
    });

    jq(POLLING_SHOW).each(function () {
        jq(this).hide();
    });
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

                // if no more updates expected, stop polling
                var stop = data.stop;
                if (!stop) {
                    //using recursive setTimeout instead of setInterval so that long running polls don't interfere with subsequent polls
                    setTimeout(sendPoll, delay);
                } else {
                    stopPolling();
                }

                var refresh = data.refresh;
                if (refresh) {
                    refreshRegistrationDetail(data);
                }

                var growlMessages = data.growlMessages;
                if(growlMessages){
                    jQuery(growlMessages).each(function (index,message) {
                        showGrowl(message,'',GROWL_SUCCESS);
                    });

                }

            }
        });
    }
}

function refreshRegistrationDetail(data){

    setClientState(data.clientState);

    var registeredCredits = data.registeredCredits;
    var waitlistedCredits = data.waitlistedCredits;
    var title = "Registered (" + registeredCredits + ") / Waitlist (" + waitlistedCredits + ")";
    jQuery("#" + REGISTRATION_TABS_ID + " a[href=#" + REGISTERED_TAB_ID + "]").text(title);

    retrieveComponent(RESULTS_COLL_ID, REFRESH_DETAIL_METHOD_NAME);

    var updateIds = data.updateIds;
    jQuery(updateIds).each(function (index, id) {
        retrieveComponent(id);
    });
}

function setClientState(state) {
    if (state) {
        jQuery("input[name='" + CLIENT_STATE_PROPERTY_NAME + "']").val(state);
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
    setClientState(state);

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

    jq(ERROR_RESULT).each(function () {
        jq(this).closest("tr").addClass(kradVariables.PAGE_VALIDATION_MESSAGE_ERROR_CLASS);
    });

    jq(WARNING_RESULT).each(function () {
        jq(this).closest("tr").addClass('alert-warning');
    });

    jq(SUCCESS_RESULT).each(function () {
        jq(this).closest("tr").addClass('alert-success');
    });
}