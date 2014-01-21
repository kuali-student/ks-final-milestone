
var selectedTimeSlotOptions;
var availableTimeSlotTypes;

/**
 *  This method initializes the popup for both edit and add action. As we're using the same
 *  popup for both add and edit, it's necessary to tweak the html dom.
 */
function showTimeSlotLightBox(event, addOrEditAction, editLineIndex)
{

    var parentSection;
    if (addOrEditAction == 'EDIT'){
        parentSection = "#KS-TimeSlot-EditTimeSlotPopupForm ";
    } else {
        parentSection = "#KS-TimeSlot-AddTimeSlotPopupForm ";
    }

    jQuery(parentSection + '#addOrEditDays_control').val('');
    jQuery(parentSection + '#addOrEditTermKey_control').val('');
    jQuery(parentSection + '#addOrEditStartTime_control').val('');
    jQuery(parentSection + '#addOrEditStartTimeAmPm_control_0').attr('checked',true);
    jQuery(parentSection + '#addOrEditEndTime_control').val('');
    jQuery(parentSection + '#addOrEditEndTimeAmPm_control_0').attr('checked',true);
    jQuery(parentSection + '#addOrEditTermKey_control').empty();
    jQuery(parentSection + '#addOrEditDays_control').val('');

   if (jQuery(parentSection + "#addOrEditDays_errors").length) {
        jQuery(parentSection + "#addOrEditDays_errors").empty();
    }
    if (jQuery(parentSection + "#addOrEditTermKey_errors").length) {
        jQuery(parentSection + "#addOrEditTermKey_errors").empty();
    }
    if (jQuery(parentSection + "#addOrEditStartTime_errors").length) {
        jQuery(parentSection + "#addOrEditStartTime_errors").empty();
    }
    if (jQuery(parentSection + "#addOrEditStartTimeAmPm_errors").length) {
        jQuery(parentSection + "#addOrEditStartTimeAmPm_errors").empty();
    }
    if (jQuery(parentSection + "#addOrEditEndTime_errors").length) {
        jQuery(parentSection + "#addOrEditEndTime_errors").empty();
    }
    if (jQuery(parentSection + "#addOrEditEndTimeAmPm_errors").length) {
        jQuery(parentSection + "#addOrEditEndTimeAmPm_errors").empty();
    }
    if (jQuery(parentSection + "#addOrEditTermKey_errors").length) {
        jQuery(parentSection + "#addOrEditTermKey_errors").empty();
    }

    if (addOrEditAction == 'EDIT'){

        jQuery(parentSection + '#addOrEdit_action').data("submit_data", {methodToCall:"editTimeSlot","actionParameters[selectedCollectionPath]":"timeSlotResults","actionParameters[actionEvent]":"editCOonManageCOsPage","actionParameters[selectedLineIndex]":editLineIndex});
//        jQuery(parentSection + '#addOrEdit_action').text('Save');
//        jQuery(parentSection + '#addOrEdit_cancel').show();

        var termType = jQuery('#termType_line' + editLineIndex + '_h0').val();
        var days = jQuery('#days_line' + editLineIndex + '_control').text();
        var startTimeWithAmPm = jQuery('#startTime_line' + editLineIndex + '_control').text().trim();

        var split = startTimeWithAmPm.split(" ");
        var startTime = split[0];
        var startTimeAmPm = split[1].trim();

        var endTimeWithAmPm = jQuery('#endTime_line' + editLineIndex + '_control').text().trim();

        var split = endTimeWithAmPm.split(" ");
        var endTime = split[0];
        var endTimeAmPm = split[1].trim();
        var spaceStrippedDays = days.trim().replace(/ /g,"");

        jQuery(parentSection + '#addOrEditDays_control').val(spaceStrippedDays);

        jQuery(parentSection + '#addOrEditStartTime_control').val(startTime);
        if (startTimeAmPm == 'AM'){
            jQuery(parentSection + '#addOrEditStartTimeAmPm_control_0').attr('checked',true);
        } else {
            jQuery(parentSection + '#addOrEditStartTimeAmPm_control_1').attr('checked',true);
        }

        jQuery(parentSection + '#addOrEditEndTime_control').val(endTime);

        if (endTimeAmPm == 'AM'){
            jQuery(parentSection + '#addOrEditEndTimeAmPm_control_0').attr('checked',true);
        } else {
            jQuery(parentSection + '#addOrEditEndTimeAmPm_control_1').attr('checked',true);
        }

        var $options = jQuery("#timeSlotTypeSelection_control > option").clone();
        jQuery(parentSection + "#addOrEditTermKey_control").append($options);
        jQuery(parentSection + '#addOrEditTermKey_control').val(termType);
        var timeSlotCode = jQuery('#timeSlotCode_line' + editLineIndex + '_control').text().trim();
        jQuery(parentSection + ' .uif-headerText-span').text("Edit Time Slot " + timeSlotCode);

        jQuery(parentSection + ".edit_ts_control").removeClass("error");
        jQuery(parentSection + ".edit_ts").removeClass("uif-hasError");

        var overrideOptions = { autoDimensions:true, afterShow:function () {
            jQuery(parentSection + '#addOrEditTermKey_control').focus();
//            initBubblePopups();
        }};

        showLightboxComponent('KS-TimeSlot-EditTimeSlotPopupForm',overrideOptions);

    } else {
//        jQuery(parentSection + '#addOrEdit_action').data("submit_data", {methodToCall:"createTimeSlot"});
//        jQuery(parentSection + '#addOrEdit_action').text('Add Slot');
        jQuery(parentSection + ".new_ts_control").removeClass("error");
        jQuery(parentSection + ".new_ts").removeClass("uif-hasError");

        jQuery(parentSection + "#addOrEditTermKey_control").append(selectedTimeSlotOptions);

        var overrideOptions = { autoDimensions:true, afterShown:function () {
            jQuery(parentSection + '#addOrEditTermKey_control').focus();
        }};
        openPopupContent(event,'KS-TimeSlot-AddTimeSlotPopupForm',overrideOptions);
    }

}

/**
 * Displays the delete confirmation dialog
 */
function showTimeSlotDeleteConfirmation() {
    var overrideOptions = { autoDimensions:false, width:500 };
    showLightboxComponent('deleteTimeSlotsConfirmationDialog-lightbox', overrideOptions);
}

function validateTimeSlot(cssSelector){
    var addEditTSComponents = jQuery('.' + cssSelector);
//    var values = jQuery('.new_ts_control').val();
    var result = validateLineFields(addEditTSComponents);
    if (!result){
        showClientSideErrorNotification();
    } else {
        closeLightbox();
    }
    return result;
}

/**
 * This method marks all the selected option with data attribute selected=true sothat the same
 * options will be used for Add timeslot. This is a workaround when user
 * initially selected types and clicked on show timeslot and then
 * the user selects different type. Relaying on selected types on the
 * list doesnt work in this situation
 */
function setupTimeSlotTypeDropdown(){
    selectedTimeSlotOptions = jQuery("#timeSlotTypeSelection_control option:selected").clone();
    availableTimeSlotTypes = jQuery("#timeSlotTypeSelection_control option").clone();
}

function resetTSValidSelection(){
    jQuery("#timeSlotTypeSelection_control").find('option').remove();
    jQuery("#timeSlotTypeSelection_control").append(availableTimeSlotTypes);
}

function toggleTimeSlotShowButton() {
    var inputVal = jQuery("#timeSlotTypeSelection_control").val();
    if (inputVal !==null) {
        jQuery("#TimeSlotShowButton").removeAttr("disabled");
    } else {
        jQuery("#TimeSlotShowButton").attr("disabled", "disabled");
    }
}


