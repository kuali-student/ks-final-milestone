/**
 * This method handles the on load event
 *
 */
function activityEditDocumentOnLoad(){

    jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate :text").addClass("ignoreValid");
    jQuery("#shared_max_enr_control").addClass("ignoreValid");
    jQuery('.new_rdl_components').addClass("ignoreValid");

    if(jQuery("#is_co_located_control").length != 0) {
        setupColoCheckBoxChange(jQuery("#is_co_located_control"));
    }

    if (jQuery("#share_seats_control_0").length != 0){
        jQuery('#share_seats_control_0').change(function() {
            if(jQuery(this).is(":checked")) {
                jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate").hide();
                jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentShared").show();
                jQuery("#shared_max_enr_control").removeClass("ignoreValid");
                jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate :text").addClass("ignoreValid");
            } else {
                jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate").show();
                jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentShared").hide();
                jQuery("#shared_max_enr_control").addClass("ignoreValid");
                jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate :text").removeClass("ignoreValid");
            }
        });
    }

    if (jQuery("#share_seats_control_1").length != 0){
        jQuery('#share_seats_control_1').change(function() {
            if(jQuery(this).is(":checked")) {
                jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate").show();
                jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentShared").hide();
                jQuery("#shared_max_enr_control").addClass("ignoreValid");
                jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate :text").removeClass("ignoreValid");
            } else {
                jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate").hide();
                jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentShared").show();
                jQuery("#shared_max_enr_control").removeClass("ignoreValid");
                jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate :text").addClass("ignoreValid");
            }
        });
    }

    onColoCheckBoxChange(true);

}

/**
 * This method handles colocate/unclocate AO.
 *
 */
function handleColocation(){

    isColoCheckboxSet = jQuery("#is_co_located_control").is(":checked");
    isColoPersistedOnInfo = jQuery('#infoColoStatus_control').is(':checked');

    /* warn the user they are going to break colocation if they are both un-setting the
     * checkbox and the colo had previously been persisted at the database
     */
    if( !isColoCheckboxSet && isColoPersistedOnInfo ) {
        overrideOptions = { autoDimensions:false, width:500, afterClose:breakColoWarningHasClosed };
        showLightboxComponent('ActivityOfferingEdit-BreakColocateConfirmation', overrideOptions);
    }
    // failing that, just hide the colo-box
    else {
        setupColoCheckBoxChange(jQuery("#is_co_located_control"));
    }

}

/**
 * FancyBox provides an "X"-button in it's upper-right corner which fires the 'close'-event, but there is no way
 * to distinguish how the dialog was closed because closing the box via any other method fires the exact same event
 * (ie: using either the "Break Colocation"- or "Close"-buttons are indistinguishable from the "X"-button)
 *
 * And the "X"-button does not provide it's own handler to enable us to apply special logic when it's pressed.  It can
 * only be hidden, but the project uses this button throughout similar dialogs.
 *
 * Functionally, the "X"-button should behave exactly as if the user had used the "Close"-button.  That is, the
 * colo-checkbox should be re-set.
 *
 * Thus, we introduce the didConfirmBreakColo-variable to determine whether or not the dialog is closing due to the
 * user clicking the confirm-button.
 *
 * See KSENROLL-10868
 */
var didConfirmBreakColo = false;
function confirmBreakColo() {   // this method gets called only when the user clicks on the confirm-button
    didConfirmBreakColo = true;
    closeLightbox();
    return true;
}
var breakColoWarningHasClosed = function() {  // this method executes whenever the dialog is closing

    if( !didConfirmBreakColo ) { // user canceled
        jQuery("#is_co_located_control").attr('checked', 'checked');
    }
    else { // user confirmed
        didConfirmBreakColo = false;
    }
};

function breakColoCallBack(){
    setupColoCheckBoxChange(jQuery("#is_co_located_control"));
}

/**
 * This method handles the colocated checkbox event.
 * @param control colocated checkbox control
 *
 */
function setupColoCheckBoxChange(control,doRDLCheck){

    if(jQuery(control).is(":checked")) {

        jQuery("#maximumEnrollment").hide();
        jQuery("#ActivityOfferingEdit-CoLocation").show();

        if(jQuery("#share_seats_control_0").length != 0 && jQuery("#share_seats_control_0").is(":checked")) {
            jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate").hide();
            jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentShared").show();
            jQuery("#shared_max_enr_control").removeClass("ignoreValid");
            jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate :text").addClass("ignoreValid");
        } else {
            jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate").show();
            jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentShared").hide();
            jQuery("#shared_max_enr_control").addClass("ignoreValid");
            jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate :text").removeClass("ignoreValid");
        }
        onColoCheckBoxChange(true);

    } else {

        var rdlExists = jQuery("#ActivityOffering-DeliveryLogistic-Requested td").length > 0;

        if (rdlExists && doRDLCheck){
            jQuery(control).prop("checked","checked");
        } else {
            jQuery("#maximumEnrollment").show();
            jQuery("#ActivityOfferingEdit-CoLocation").hide();
            jQuery("#shared_max_enr_control").addClass("ignoreValid");
            jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate :text").addClass("ignoreValid");
            onColoCheckBoxChange(false);
        }
    }

}


/**
 * This method refreshes all the related components when the user adds/deletes an AO from the colocated set.
 */
function addColocatedAOSuccessCallBack(){
    retrieveComponent('enr_shared_table',undefined, function () {
        jQuery('#ActivityOfferingEdit-CoLocatedActivities').show();
    });
}

/**
 * Due to FancyBox limitations, we have to implement this extra variable (and related control-logic) to determine
 * which of the 3 lightbox buttons the user pressed.  For more detail, refer to the comments for the breaking-colocation
 * block of code (which is different functionality, but with the same work-around).
 */
var didConfirmRemoveAllOfferingsFromScheduler = false;
function confirmRemoveAllOfferingsFromScheduler() {  // this method gets called only when the user clicks on the confirm-button
    didConfirmRemoveAllOfferingsFromScheduler = true;
    closeLightbox();
    return true;
}
var removeAllOfferingsFromSchedulerConfirmationHasClosed = function() {  // this method executes whenever the dialog is closing
    if( !didConfirmRemoveAllOfferingsFromScheduler ) {  // user canceled
        jQuery('#send_RDLs_to_scheduler_control').prop('checked', false);
    }
    else {  // user confirmed
        didConfirmRemoveAllOfferingsFromScheduler = false;
    }
}

function refreshRemoveAllOfferingsFromSchedulerConfirmation() {
    retrieveComponent('ActivityOfferingEdit-RemoveAllOfferingsFromSchedulerConfirmation', undefined, function() {
        jQuery('#send_RDLs_to_scheduler_control').prop('checked', false);
    });
}

function showRemoveAllOfferingsFromSchedulerConfirmation() {
    isActivityInOfferedState = ( jQuery('#ActivityOfferingEdit-RemoveAllOfferingsFromSchedulerConfirmation_ActivityState_control').prop('value') == "Offered" ? true : false );
    isRdlListEmpty = ( jQuery('#ActivityOfferingEdit-RemoveAllOfferingsFromSchedulerConfirmation_IsRdlListRecentlyEmpty_control').prop('value') == "true" ? true : false );
    isUserSendingToScheduler = ( jQuery('#send_RDLs_to_scheduler_control').attr('checked') == "checked" ? true : false );

    if( isActivityInOfferedState && isRdlListEmpty && isUserSendingToScheduler  ) {
        overrideOptions = { autoDimensions:false, width:500, afterClose:removeAllOfferingsFromSchedulerConfirmationHasClosed };
        showLightboxComponent( 'ActivityOfferingEdit-RemoveAllOfferingsFromSchedulerConfirmation', overrideOptions );
    }
}

function reDisplayPersonnelSection() {
    var personnelComponent = jQuery("#ao-personnelgroup");
    if (personnelComponent.css('display') == "none") {
        personnelComponent.show();
    }
}

function reDisplaySeatPoolSection() {
    var spComponent = jQuery("#ao-seatpoolgroupZ");
    if (spComponent.css('display') == "none") {
        spComponent.show();
    }
}

function reDisplayRDLSection() {
    var rdlComponent = jQuery("#ActivityOffering-DeliveryLogistic-Requested");
        rdlComponent.show();
}

function reDisplayNewRDSection() {
    var newRDLComponent = jQuery("#ActivityOffering-DeliveryLogistic-New");
    if (newRDLComponent.css('display') == "none") {
        newRDLComponent.show();
    }
}

/**
 * This method shows a dialog box to user before adding activity to the Colo set.
 * @param component
 */
function addColocatedAO(component){
    var overrideOptions = { afterClose:function () {
        actionInvokeHandler(component);
    }};

    showLightboxComponent('colocateDialog',overrideOptions);
}

/**
 * This is needed for partial colo in future milestones
 * @return {boolean}
 */
/*function addRDLCallBack(){
 retrieveComponent('ActivityOffering-DeliveryLogistic-Requested');
 var scheduling_completed = jQuery('#scheduling_completed_control');
 if (scheduling_completed.length > 0){
 if (scheduling_completed.text().trim() === 'true'){
 jQuery('.unprocessed_changes_message').show();
 jQuery('.unprocessed_changes_checkbox').show();
 }
 }
 }*/


/**
 * On document submit or editing another RDL, make sure there is no activity is edit in progress
 *
 * @return {boolean}
 */
function checkAOEditWIP(){
    if (jQuery("#add_rdl_button").length > 0){
        var label = jQuery("#add_rdl_button").text().trim();
        if (label === 'Update') {
            showClientSideErrorNotification('Editing RDL in progress. Please update that first.');
            return false;
        }
    }
    return true;
}

function validatePopulationForSP(field, populationsJSONString) {
    var fieldId = jQuery(field).attr('id'); //id of the population name text box
    var populationName = jQuery(field).val();
    if(populationName && populationName.length)  {
        validateFieldValue(jQuery("#" + fieldId));
    }
    var spErrorMsgDiv = jQuery('#ao-seatpoolgroup').find('.uif-validationMessages.uif-groupValidationMessages').get(0); //div for error message display on top of SP table
    var ul = jQuery('#seatpool_validation_errorMessageUl');

    // validate if the input population name is valid in DB
    if (populationsJSONString && 0 !== populationsJSONString.length && populationName && 0 !== populationName) {
        var populationsObj = jQuery.parseJSON(populationsJSONString);
        var pos = 0;
        jQuery.each(populationsObj.populations, function (key, value) {
            if (populationName.toLowerCase() === value.toLowerCase()) {
                return false;
            }
            pos++;
        });

        var liId = fieldId + '_errorMessageLi';
        var li;
        if (pos >= Object.keys(populationsObj.populations).length) {
            if (ul.length == 0) {
                ul = jQuery("<ul id='seatpool_validation_errorMessageUl' class='uif-validationMessagesList'>");
            }
            if (jQuery('#' + liId).length == 0) {
                li = jQuery("<li id='" + liId + "' class='uif-errorMessageItem' tabindex='0'></li>");
                ul.append(li);
                jQuery(spErrorMsgDiv).append(ul);
            }
            li = jQuery('#' + liId);
            li.text('Population ' + populationName + ' is inactive or not found in the database.');
            jQuery(spErrorMsgDiv).show();
        } else {
            if (jQuery('#' + liId).length > 0) {
                jQuery('#' + liId).remove();
            }
        }

        // validate if there is any population name duplication
        var rows = jQuery("[id^='ao-seatpoolgroup-population-name_line'][id$='control']");
        var posPopDupCheck = 0;
        rows.each(function () {
            var id = jQuery(this).attr('id');
            if ((populationName.toLowerCase() === jQuery(this).val().toLowerCase()) && (fieldId != id)) {
                return false;
            }
            posPopDupCheck++;
        });

        var liIdPopDupCheck = fieldId + '_errorMessageLiPopDupCheck';
        var liPopDupCheck;
        var liAllliPopDupCheck = jQuery("[id^='ao-seatpoolgroup-population-name_line'][id$='_errorMessageLiPopDupCheck']");
        liAllliPopDupCheck.remove();

        if (posPopDupCheck >= rows.length) {
            if (jQuery('#' + liIdPopDupCheck).length > 0) {
            }
        } else {
            if (ul.length == 0) {
                ul = jQuery("<ul id='seatpool_validation_errorMessageUl' class='uif-validationMessagesList'>");
            }

            if (jQuery('#' + liIdPopDupCheck).length == 0) {
                liPopDupCheck = jQuery("<li id='" + liIdPopDupCheck + "' class='uif-errorMessageItem' tabindex='0'></li>");
                ul.append(liPopDupCheck);
                jQuery(spErrorMsgDiv).append(ul);
            }
            liPopDupCheck = jQuery('#' + liIdPopDupCheck);
            liPopDupCheck.text('Population name ' + populationName + ' already in use. Please enter a different, unique population name');
            jQuery(spErrorMsgDiv).show();
        }
        /*
         if (jQuery('#seatpool_validation_errorMessageUl li').length != 0) {
         jQuery('.uif-action.uif-primaryActionButton.uif-boxLayoutHorizontalItem').attr('disabled', true);
         } else {
         jQuery('.uif-action.uif-primaryActionButton.uif-boxLayoutHorizontalItem').attr('disabled', false);
         }
         */
        //if there is no error messages at all, delete the ul
        jQuery("#seatpool_validation_errorMessageUl:empty").remove();
    }
}

function showSPAddlinePriority(populationsJSONString){
    var rows = jQuery("[id^='seatPoolPriority_line'][id$='control']");
    var maxPriorityNum = 0;
    rows.each(function () {
        var id = jQuery(this).attr('id');
        if(id.indexOf("_control") != -1) {
            var currentPriorityNum = parseInt(jQuery(this).val());
            maxPriorityNum = (maxPriorityNum<currentPriorityNum) ? currentPriorityNum : maxPriorityNum;
        }
    });

    var lineNm = rows.length-1;
    var priorityNmToSet = parseInt(maxPriorityNum)+1;

    if (jQuery('#seatPoolPriority_line' + lineNm  + '_control').val() == '') {
        jQuery('#seatPoolPriority_line' + lineNm  + '_control').val(priorityNmToSet);
    }


}

function preSubmitCheck(){
    var result = checkAOEditWIP();
    if (result){
        jQuery('.new_rdl_components').addClass("ignoreValid");
        result = validateForm();
    }
    return result;
}

function preSubmitNewRDL(){
    var components = jQuery('.new_rdl_components');
    var isTba = jQuery('#rdl_tba_control').is(':checked');
    jQuery('#dirtyForm').val(true);
    if (!isTba){
        var valid = validateLineFields(components);
        if (!valid) {
            showClientSideErrorNotification();

            return false;
        }
    }

    return true;
}

/*function deleteRDLCallBack(){
 var rdlExists = jQuery("#ActivityOffering-DeliveryLogistic-Requested td").length > 0;
 //If authz is associated with this, check the hidden authz related flag before doing this.
 if (rdlExists){
 jQuery("#is_co_located_control").attr("disabled", true);
 } else {
 jQuery("#is_co_located_control").attr("disabled", false);
 }
 }*/

/**
 *
 * @param checked
 */
function onColoCheckBoxChange(checked){

    /* KSENROLL-6378 mods the checkbox-component used in this function (ID: ActivityOffering-CoLocated-checkbox) because we are not
     * yet supporting partial-colocation; therefore, simple text is implemented in lieu of checkboxes, making this code obsolete.
     * This code is likely to be used in the future once partial-colocation is implemented, so I've left this code laying here
     * but commented-out.  See ActivityOfferingEdit-ScheduleSection.xml id="ActivityOffering-CoLocated-checkbox"
     * ~brandon.gresham
     */
    if (checked){
        if(jQuery("#is_co_located_control").is(":checked")) {
            jQuery("#ActivityOffering-CoLocated-checkbox").show();
            //jQuery('input[id^="ActivityOffering-CoLocated-checkbox_control_"]').prop('checked', checked);
            //jQuery('label[for^="ActivityOffering-CoLocated-checkbox_control_"]').prop('onclick','');
            //jQuery('input[id^="ActivityOffering-CoLocated-checkbox_control_"]').click(function() {
            //    jQuery(this).prop('checked', true);
            //});
        } else {
            //jQuery('input[id^="ActivityOffering-CoLocated-checkbox_control_"]').unbind("click");
            //jQuery('input[id^="ActivityOffering-CoLocated-checkbox_control_"]').prop('checked', checked);
            //jQuery('label[for^="ActivityOffering-CoLocated-checkbox_control_"]').prop('onclick','');
            jQuery("#ActivityOffering-CoLocated-checkbox").hide();
        }
    } else {
        //jQuery('input[id^="ActivityOffering-CoLocated-checkbox_control_"]').unbind("click");
        //jQuery('input[id^="ActivityOffering-CoLocated-checkbox_control_"]').prop('checked', checked);
        //jQuery('label[for^="ActivityOffering-CoLocated-checkbox_control_"]').prop('onclick','');
        jQuery("#ActivityOffering-CoLocated-checkbox").hide();
    }

}


function clearFeaturesSelected(){
    jQuery('#featuresList_control option').attr('selected', false);
    return false;
}

function validateSeatsForSP(jqObject) {

    var valid = validateFieldValue(jqObject);

    if (!valid){
        return;
    }

    var spErrorMsgDiv = jQuery('#ao-seatpoolgroup').find('.uif-validationMessages.uif-groupValidationMessages').get(0);
    var ul = jQuery('#seatpool_validation_errorMessageUl');
    var liId = 'seatpool_validation_errorMessageLiSeatCheck';
    var li;

    var seatpoolCount = jQuery('#seatpoolCount p[class=uif-message]');
    var seatsRemaining = jQuery('#seatsRemaining p[class=uif-message]');
    var count = 0;
    var seatsTotal = 0;
    var maxEnrollValue = 0;

    maxEnrollValue = jQuery('#maximumEnrollment_control').val();
    var rows = jQuery("[id^='seatLimit_line']");
    rows.each(function () {
        var id = jQuery(this).attr('id');
        if (id.indexOf("_control") != -1) {
            var num = id.substring(14, id.length - 8);
            var elemPct = jQuery('#seatLimitPercent_line' + num);
            var seatsNum = jQuery(this).val();
            if(seatsNum != ""){
                count += 1;
                if (maxEnrollValue != "" && maxEnrollValue != 0 ) {
                    seatsTotal = parseInt(seatsTotal) + parseInt(seatsNum);
                    var result = (seatsNum / maxEnrollValue) * 100;
                    elemPct.text(Math.round(result) + "%");
                } else {
                    elemPct.text("");
                }
            }
        }
    });
    seatpoolCount.text(count);

    if (maxEnrollValue != "") {
        var seatsRemain = (maxEnrollValue > seatsTotal) ? (maxEnrollValue - seatsTotal) : 0;
        var percTotal = Math.round((seatsTotal / maxEnrollValue) * 100);
        var percRemain = (seatsRemain > 0) ? (100 - percTotal) : 0;
        if (maxEnrollValue >= seatsTotal){
            if(jQuery('#' + liId).length > 0) {
                jQuery('#' + liId).remove();
            }
            seatsRemaining.text(percRemain + "% | " + seatsRemain + " Seats (Max Enrollment = " + maxEnrollValue + ")");
            jq(seatsRemaining).css('color', 'black');
        } else {
            if (ul.length == 0) {
                ul = jQuery("<ul id='seatpool_validation_errorMessageUl' class='uif-validationMessagesList'>");
            }
            if (jQuery('#' + liId).length == 0) {

                li = jQuery("<li id='" + liId + "' class='uif-errorMessageItem' tabindex='0'></li>");
                ul.append(li);
                jQuery(spErrorMsgDiv).append(ul);
            }
            li=jQuery('#' + liId);
            li.text('WARNING: Total seats exceeding the total max enrollment quantity by ' +  (seatsTotal - maxEnrollValue) + ' seats.');
            jQuery(spErrorMsgDiv).show();

            seatsRemaining.text(percRemain + "% | " + seatsRemain + " Seats (Max Enrollment = " + maxEnrollValue + ")" +
                " - WARNING: Total seats exceeding the total max enrollment quantity by " + (seatsTotal - maxEnrollValue) + " seats!");
            jq(seatsRemaining).css('color', 'red');
        }
    } else {
        seatsRemaining.text("");
    }
/*
    if (jQuery('#seatpool_validation_errorMessageUl li').length != 0) {
        jQuery('.uif-action.uif-primaryActionButton.uif-boxLayoutHorizontalItem').attr('disabled', true);
    } else {
        jQuery('.uif-action.uif-primaryActionButton.uif-boxLayoutHorizontalItem').attr('disabled', false);
    }
*/
    //if there is no error messages at all, delete the ul
    jQuery("#seatpool_validation_errorMessageUl:empty").remove();
}

function calculatePercent(jqObject){

    if(jqObject) {
        var currentId = jqObject.attr('id');
        if (currentId.indexOf("_control") == 0) {
            currentId = currentId + "_control";
        }
        var element = jQuery('#' + currentId);
        var numValue = element.val();
        var numericExpression = /^[0-9]+$/;
        if(!numValue.match(numericExpression) && numValue != '') {
            if (currentId == 'maximumEnrollment') {
                alert("Maximum enrollment must be a number!");
            } else {
                alert("Number of seats must be a number!");
            }
            element.val('');
        }
    }

//    var seatLimitAdd =  jQuery('#seatLimit_add_control');
//    var seatLimitPercentAdd =  jQuery('#seatLimitPercent_add #_span');
    var seatpoolCount = jQuery('#seatpoolCount p[class=uif-message]');
    var seatsRemaining = jQuery('#seatsRemaining p[class=uif-message]');
    var count = 0;
    var seatsTotal = 0;
    var maxEnrollValue = 0;

    // 3 different calculations: when on Edit page and when on View page, unfortunately IDs set up differently by KRAD
    var maxEnrollView = jQuery('span[id=maximumEnrollment]');
    var maxEnroll =  jQuery('#maximumEnrollment_control');

    if (maxEnrollView.length > 0) { // View page
        maxEnrollValue = maxEnrollView.text().trim();
        var rows = jQuery('span[id^=seatLimit_line]');
        rows.each(function () {
            var id = jQuery(this).attr('id');
            if(id.match(/_/g).length == 1) {
                var num = id.substring(14);
                var elemPct = jQuery('#seatLimitPercent_line' + num + ' span[class=uif-message]');
                var seatsNum = jQuery(this).text().trim();
                if(seatsNum != ""){
                    count += 1;
                    if (maxEnrollValue != "" && maxEnrollValue != 0) {
                        seatsTotal = parseInt(seatsTotal) + parseInt(seatsNum);
                        var result = (seatsNum / maxEnrollValue) * 100;
                        elemPct.text(Math.round(result) + "%");
                    } else {
                        elemPct.text("");
                    }
                }
            }
        });
        seatpoolCount.text(count);
    } else if ( maxEnrollView.length == 0 && maxEnroll.hasClass("uif-readOnlyContent") ) { // View page
        maxEnrollValue = maxEnrollView.text().trim();
        if (maxEnrollValue == "") {
            maxEnrollValue = maxEnroll.text().trim();
        }
        var rows = jQuery('span[id^=seatLimit_line]');
        rows.each(function () {
            var id = jQuery(this).attr('id');
            if(id.indexOf("_control") != -1) {
                var num = id.substring(14,15);
                var elemPct = jQuery('#seatLimitPercent_line' + num + ' span[class=uif-message]');
                var seatsNum = jQuery(this).text().trim();
                count += 1;
                if (maxEnrollValue != "" && maxEnrollValue != 0 && seatsNum != "") {
                    seatsTotal = parseInt(seatsTotal) + parseInt(seatsNum);
                    var result = (seatsNum / maxEnrollValue) * 100;
                    elemPct.text(Math.round(result) + "%");
                } else {
                    elemPct.text("");
                }
            }
        });
        seatpoolCount.text(count);
    } else { // Edit page (different IDs)
        maxEnrollValue = maxEnroll.val();
        var rows = jQuery("[id^='seatLimit_line']");
        rows.each(function () {
            var id = jQuery(this).attr('id');
            if(id.indexOf("_control") != -1) {
                var num = id.substring(14, id.length - 8);
                var elemPct = jQuery('#seatLimitPercent_line' + num);
                var seatsNum;
                if(jQuery(this).is("span")){
                    seatsNum = jQuery(this).text();
                }else{
                    seatsNum = jQuery(this).val();
                }
                if(seatsNum != ""){
                count += 1;
                if (maxEnrollValue != "" && maxEnrollValue != 0) {
                    seatsTotal = parseInt(seatsTotal) + (parseInt(seatsNum) || 0);
                    if(seatsTotal == 0){
                        elemPct.text("");
                    }else{
                        var result = (seatsNum / maxEnrollValue) * 100;
                        elemPct.text(Math.round(result) + "%");
                    }
                } else {
                    elemPct.text("");
                }
            }
            }
        });
        seatpoolCount.text(count);
    }

    if (maxEnrollValue != "") {
        var seatsRemain = (maxEnrollValue > seatsTotal) ? (maxEnrollValue - seatsTotal) : 0;
        var percTotal = Math.round((seatsTotal / maxEnrollValue) * 100);
        var percRemain = (seatsRemain > 0) ? (100 - percTotal) : 0;
        if (maxEnrollValue >= seatsTotal){
            seatsRemaining.text(percRemain + "% | " + seatsRemain + " Seats (Max Enrollment = " + maxEnrollValue + ")");
            jq(seatsRemaining).css('color', 'black');
        } else {
            seatsRemaining.text(percRemain + "% | " + seatsRemain + " Seats (Max Enrollment = " + maxEnrollValue + ")" +
                " - WARNING: Total seats exceeding the total max enrollment quantity by " + (seatsTotal - maxEnrollValue) + " seats!");
            jq(seatsRemaining).css('color', 'red');
        }
    } else {
        seatsRemaining.text("");
    }


}

function setRequestedDeliveryLogisticsFieldRequired(jqObject,required){

}

function rePositionChangeLink(subTermNameId, popoverId) {
    /* re-position change link based on the sub term name selected */
    var changeLinkOffset = jQuery("#"+subTermNameId).offset().left + jQuery("#"+subTermNameId).width() + 10;
    jQuery("#" + popoverId).offset({ left: changeLinkOffset });
}

function displayAOsubTerm(subTermNameId, subTermTypeId, popoverId, dirtyId, subTermDatesJsonString) {
    var subTermName = jQuery("#"+subTermTypeId).find(":selected").text();
    var subTermId = jQuery("#"+subTermTypeId+"_control").find(":selected").val();

    /* set subTermName */
    jQuery("#"+subTermNameId).text(subTermName);

    /* re-position change link based on the sub term name selected */
    var changeLinkOffset = jQuery("#"+subTermNameId).offset().left + jQuery("#"+subTermNameId).width() + 10;
    jQuery("#" + popoverId).offset({ left: changeLinkOffset });

    /* Set indicated field control as dirty to register changes in form (may not be a subterm field.) */
    jQuery('#'+dirtyId+'_control').addClass('dirty');

    /* set subTerm Start/End date display */
    if (subTermName == "None") {
        subTermId="none";
    }
    if (subTermDatesJsonString && 0 !== subTermDatesJsonString.length && subTermId && 0 != subTermId.length) {
        var subTermDates = jQuery.parseJSON(subTermDatesJsonString);
        jQuery.each(subTermDates, function (key, value) {
            if (subTermId.toLowerCase() === key.toLowerCase()) {
                jQuery("#start_end_date_control").text(value);
            }
        });
    }

    jQuery("#"+popoverId).HideBubblePopup();
}

function handleAOSelection(component){
    var aoId = jQuery(component).val();
    handleAONavigation(aoId);
}

function handleAONavigation(aoId) {
    // "Save and Continue"
    injectValueInDataAttribute('#edit_ao_save_and_continue', aoId, 'aoId');
    // "Continue Without Saving"
    injectValueInDataAttribute('#edit_ao_cancel', aoId, 'aoId');

    if(!dirtyFormState.isDirty()) {
        jQuery("#edit_ao_cancel").click();
        return;
    }

    showLightboxComponent('ActivityOfferingEdit-NavigationConfirmation');
}


/**
 * On TBA check box click, we're refreshing the end time component. For TBA,
 *  the end time is always editable irrespective of authz.
 *
 * In future sprint, we'll having a user story to deal just with tba.
 */
function tbaOnClick(){
    retrieveComponent('rdl_endtime');
}

/**
 * This is a validation to make sure the end time is in correct format hh:mm am
 */
function endTimeOnBlur(){
    var endTime = jQuery("#rdl_endtime_control").val();
    if (endTime == ''){
        return;
    }
    parseAndReplaceTimeClause(jQuery("#rdl_endtime_control"), jQuery("#rdl_days_control"));
}

/**
 * This will be called on start time focus lost. Here, we load matching endtimes for the
 * days and start time.
 */
function rdlStartTimeOnBlur(baseUrl) {
    var startTime = jQuery("#rdl_starttime_control").val();
    var isMozilla = jQuery.browser.mozilla != null;

    var days = jQuery("#rdl_days_control").val();
    var tbaChecked = document.getElementById('rdl_tba_control').checked;

    jQuery("#rdl_endtime_control").val('');

    if (startTime != '') {
        parseAndReplaceTimeClause(jQuery("#rdl_starttime_control"), jQuery("#rdl_days_control"));
    }

    jQuery("#rdl_starttime_control").data("starttime", jQuery("#rdl_starttime_control").val());
    jQuery("#rdl_days_control").data("days", jQuery("#rdl_days_control").val());

    if (validateFieldValue(jQuery("#rdl_starttime_control")) == false ||
        validateFieldValue(jQuery("#rdl_days_control")) == false) {
        return;
    }

    ajaxGetValues(baseUrl, 'getAjaxSEndTimes', function (options) {

        if (jQuery('#rdl_endtime_control.ui-autocomplete-input').length) {
            jQuery('#rdl_endtime_control').autocomplete("option", "source", options);
        } else {
            if (options.length > 0) {
                jQuery('#rdl_endtime_control').val(options[0]);
                jQuery('#rdl_endtime_control').autocomplete({
                    source: options,
                    minLength: 0
                }).focus(function () {
                    jQuery(this).autocomplete("option", "minLength", 0);
                    jQuery(this).autocomplete("search");
                });
            }
        }
    });
}


function ajaxGetValues(baseUrl, methodToCall, callbackFunction) {
    var formData = jQuery('#kualiForm').serialize();
    var targetUrl = baseUrl + "/kr-krad/activityOffering?methodToCall=" + methodToCall;
    jQuery.ajax({
        dataType: "json",
        url: targetUrl,
        type: "POST",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            callbackFunction(data);
        },
        error: function (jqXHR, status, error) {
            if (console) {
                console.log("Error Occured...............");
            }
        }
    });
}

/**
 * This will be called on days lost focus. This method will reset both start and end time
 * and set the focus on start time.
 */
function rdlDaysOnBlur(){
    var days = jQuery("#rdl_days_control").val();
    var startTime = jQuery("#rdl_starttime_control").val();

    if (days == '' || jQuery("#rdl_days_control").val() == jQuery("#rdl_days_control").data("daysOnly")){
        jQuery("#rdl_endtime").show();
        return;
    }

    jQuery("#rdl_days_control").data("daysOnly", jQuery("#rdl_days_control").val());

    if (days == '' ||  startTime == '') {
        retrieveComponent('rdl_endtime','resetNewRDLTime',function () {
        jQuery("#rdl_endtime").show();
        });
    }
}

/**
 * KRAD displays true or false as the value for a readonly checkbox
 * This function replaces true or false with a disabled checkbox
 */
function makeReadOnlyCheckboxDisabled (componentId) {
    var domId = componentId + '_control';

    var checkboxValueSpan = jQuery('span[id="' + domId + '"]');
    if(checkboxValueSpan!==null && checkboxValueSpan.length>0 ) {
        var checked;
        if (checkboxValueSpan.text().indexOf('true') >= 0) {
            checked = true;
        } else if (checkboxValueSpan.text().indexOf('false') >= 0) {
            checked = false;
        }
        checkboxValueSpan.replaceWith(
            jQuery("<input>", {
                    type:'checkbox',
                    id:domId,
                    name:'document.newMaintainableObject.dataObject.aoInfo.isApprovedForNonStandardTimeSlots',
                    class:'uif-checkboxControl valid',
                    tabindex:'0',
                    disabled:'disabled'
                }
            )
        );
        jQuery('#'+domId).prop("checked", checked);
        var newComponent = jQuery('#'+domId);
    }
}

/**
 * Validate the seat pool seats and change the error message.
 */
jQuery.validator.addMethod("validSeatsAndPopulationName",
    function(value, element) {
        if(element) {
            var populationNameVal = jQuery(element).val();
            if(populationNameVal == "") return true;
            var populationId = jQuery(element).attr('id'); //id of the population name text box
            var seatId = populationId.replace("ao-seatpoolgroup-population-name", "seatLimit");
            return (jQuery("#" + seatId).val() && jQuery("#" + seatId).val().length);
        }
    },
    "Seats must be entered before Population Name.")
