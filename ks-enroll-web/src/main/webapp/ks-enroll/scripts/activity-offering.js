/**
 * This method handles colocate/unclocate AO.
 *
 */
 function handleColocation(){
    setupColoCheckBoxChange(jQuery("#is_co_located_control"));

    if(jQuery("#is_co_located_control").is(":checked")) {
        //colo UI changes
    }else {
        // handle un-colocation in server side
        if(jQuery("#colocatedAO_value_control").val()=="true") {
            var str = "Break colocation:\n\nSelecting this option will break any colocation relationships associated with this Activity.\n\nDelivery Logistics" +
                            "\nThe Activity you are editing will forfit any requested and actual delivery logistics as a result of breaking colocation. Delivery logistics will be retained by any remaining Activities.\n\n";

            if (jQuery("#maxEnrollmentShared_value_control").val() == "true") {
                str = str + "Maximum Enrollment\nThe maximum enrollment was shared across collocated activities. Any remaining activities will retain the entire value. The Activity you are editing will require its own maximum enrollment.\n";
            } else {
                str = str + "Maximum Enrollment\nThe maximum enrollment was managed individually for all collocated activities. Each Activity, including the one you are editing, will retain its unique maximum enrollment value but may need revision.\n";
            }


            var retVal =confirm(str);
            if (retVal==true) {
                // If breaking the current ao from the coloset, set the max enrollment to 0 if it's shared
                if(jQuery("#share_seats_control_0").length != 0 && jQuery("#share_seats_control_0").is(":checked")) {
                    jQuery("#maximumEnrollment_control").val('0');
                }
            } else {
                jQuery("#is_co_located_control").attr('checked', 'checked');
                setupColoCheckBoxChange(jQuery("#is_co_located_control"));
            }
        }
    }
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
 * This method handles the on load event
 *
 */
function activityEditDocumentOnLoad(){

    jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate :text").addClass("ignoreValid");
    jQuery("#shared_max_enr_control").addClass("ignoreValid");

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
 * This method refreshes all the related components when the user adds/deletes an AO from the colocated set.
 */
function addColocatedAOSuccessCallBack(){

    /* KSENROLL-6378 mods the checkbox-component used in this function (ID: ActivityOffering-CoLocated-checkbox) because we are not
     * yet supporting partial-colocation; therefore, simple text is implemented in lieu of checkboxes, making this code obsolete.
     * This code is likely to be used in the future once partial-colocation is implemented, so I've left this code laying here
     * but commented-out.  Of note, I had to comment-out the part that updates just the checkbox-component and instead update it's
     * parent-container because doing otherwise was generating a stack-trace.
     *
     * See ActivityOfferingEdit-ScheduleSection.xml id="ActivityOffering-CoLocated-checkbox"
     * ~brandon.gresham
     */
    retrieveComponent('enr_shared_table',undefined, function () {
        //If 'Manage Enrollments separately' is not checked, add ignore class.
        if (!jQuery('#share_seats_control_1').is(":checked")){
            jQuery("#ActivityOfferingEdit-CoLocatedEnrollmentSeperate :text").addClass("ignoreValid");
        }
        //retrieveComponent('ActivityOffering-CoLocated-checkbox', undefined, function () {
        retrieveComponent('ActivityOffering-DeliveryLogistic-New', undefined, function() {
            onColoCheckBoxChange(true);
            retrieveComponent('ActivityOffering-DeliveryLogistic-Requested');
        });
    });
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
    var valid = validateLineFields(components);
    if (!valid) {
        showClientSideErrorNotification();

        return false;
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

function calculatePercent(jqObject){

    if(jqObject) {
        var currentId = jqObject.attr('id');
        if (currentId.indexOf("_control") == -1) {
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
    var seatpoolCount = jQuery('#seatpoolCount span[class=uif-message]');
    var seatsRemaining = jQuery('#seatsRemaining span[class=uif-message]');
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
                var seatsNum = jQuery(this).val();
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