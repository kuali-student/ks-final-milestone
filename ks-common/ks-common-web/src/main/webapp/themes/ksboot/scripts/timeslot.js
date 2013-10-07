
var selectedTimeSlotOptions;

/**
 *  This method initializes the popup for both edit and add action. As we're using the same
 *  popup for both add and edit, it's necessary to tweak the html dom.
 */
function reInitializePopupModel(event, addOrEditAction, editLineIndex)
{
    jQuery('#addOrEditDays_control').val('');
    jQuery('#addOrEditTermKey_control').val('');
    jQuery('#addOrEditStartTime_control').val('');
    jQuery('#addOrEditStartTimeAmPm_control_0').attr('checked',true);
    jQuery('#addOrEditEndTime_control').val('');
    jQuery('#addOrEditEndTimeAmPm_control_0').attr('checked',true);
    jQuery('#addOrEditTermKey_control').empty();

    if (addOrEditAction == 'EDIT'){
        jQuery('#addOrEdit_action').data("submit_data", {methodToCall:"editTimeSlot","actionParameters[selectedCollectionPath]":"timeSlotResults","actionParameters[actionEvent]":"editCOonManageCOsPage","actionParameters[selectedLineIndex]":editLineIndex});
        jQuery('#addOrEdit_action').text('Save');
        var termTypeId = 'timeSlotResults[' + editLineIndex + '].typeKey';
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
        jQuery('#addOrEditDays_control').val(spaceStrippedDays);

        jQuery('#addOrEditStartTime_control').val(startTime);
        if (startTimeAmPm == 'AM'){
            jQuery('#addOrEditStartTimeAmPm_control_0').attr('checked',true);
        } else {
            jQuery('#addOrEditStartTimeAmPm_control_1').attr('checked',true);
        }

        jQuery('#addOrEditEndTime_control').val(endTime);

        if (endTimeAmPm == 'AM'){
            jQuery('#addOrEditEndTimeAmPm_control_0').attr('checked',true);
        } else {
            jQuery('#addOrEditEndTimeAmPm_control_1').attr('checked',true);
        }

        var $options = jQuery("#timeSlotTypeSelection_control > option").clone();
        jQuery("#addOrEditTermKey_control").append($options);
        jQuery('#addOrEditTermKey_control').val(termType);

    } else {
        jQuery('#addOrEdit_action').data("submit_data", {methodToCall:"createTimeSlot"});
        jQuery('#addOrEdit_action').text('Add Slot');
        jQuery("#addOrEditTermKey_control").append(selectedTimeSlotOptions);
    }
}

function showDeleteDialog() {
    var overrideOptions = { autoDimensions:false, width:500 };
    showLightboxComponent('deleteTimeSlotsConfirmationDialog-lightbox', overrideOptions);
}

function validateTimeSlot(){
    var addEditTSComponents = jQuery('.new_ts');
    result = validateLineFields(addEditTSComponents);
    if (!result){
        showGrowl('The form contains errors. Please correct these errors and try again.', 'Javascript Error', 'errorGrowl');
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
}