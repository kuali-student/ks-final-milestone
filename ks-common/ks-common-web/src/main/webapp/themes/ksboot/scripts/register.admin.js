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
var polling = false;
var delay = null;

function startPolling(time) {
    if (!polling) {
        delay = time;
        polling = true;
        sendPoll();
    }
}

function stopPolling() {
    polling = false;
    delay = null;
}

function sendPoll() {

    if (polling) {
        var url = jQuery("#" + kradVariables.KUALI_FORM).attr("action");
        var queryData = {};

        console.log("Query");

        queryData.methodToCall = "queryForRegistrationStatus";
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
                var stop = data.stop;

                jQuery(updateIds).each(function (index, id) {
                    retrieveComponent(id);
                });

                // if no more updates expected, stop polling
                if (!stop) {
                    setTimeout(sendPoll, delay);
                } else {
                    stopPolling();
                }

            }
        });
    }
}

function goAction(component, state) {
    if(state) {
        jQuery("input[name='clientState']").val(state);
    }

    var action = jQuery(component);

    var kradRequest = new KradRequest(action);
    kradRequest.send();
}

function setValidation(controlName, requiredName, states, message) {

    var check = function(){
        var i;
        for (i = 0; i < states.length; i++) {
            if((coerceValue(controlName).toUpperCase() == states[i].toUpperCase())){
                return true;
            }
        }
        return false;
    };

    runValidationScript(function(){
        setupShowReqIndicatorCheck(controlName, requiredName, check);
    });

    runValidationScript(function(){
        jQuery('[name="'+requiredName+'"]').rules("add", {
            required: check,
            messages: {
                required: message
            }
        });
    });
}