/**
 *
 */
function reInitializePopupModel(event, addOrEditAction, editLineIndex)
{
    jQuery('#addOrEditDays_control').val('');
    jQuery('#addOrEditTermKey_control').val('');
    jQuery('#addOrEditStartTime_control').val('');
    jQuery('#addOrEditStartTimeAmPm_control').val('');
    jQuery('#addOrEditEndTime_control').val('');
    jQuery('#addOrEditEndTimeAmPm_control').val('');
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
        jQuery('#addOrEditStartTimeAmPm_control').val(startTimeAmPm);
        jQuery('#addOrEditEndTime_control').val(endTime);
        jQuery('#addOrEditEndTimeAmPm_control').val(endTimeAmPm);

        var $options = jQuery("#timeSlotTypeSelection_control > option").clone();
        jQuery("#addOrEditTermKey_control").append($options);
        jQuery('#addOrEditTermKey_control').val(termType);

    } else {
        jQuery('#addOrEdit_action').data("submit_data", {methodToCall:"createTimeSlot"});
        jQuery('#addOrEdit_action').text('Add Slot');
        var $options = jQuery("#timeSlotTypeSelection_control option:selected").clone();
        jQuery("#addOrEditTermKey_control").append($options);
    }
}

function showDeleteDialog() {
    var overrideOptions = { autoDimensions:false, width:500 };
    showLightboxComponent('deleteTimeSlotsConfirmationDialog-lightbox', overrideOptions);
}

function validateTimeSlot(){
    var addEditTSComponents = jQuery('.new_ts');
    return validateLineFields(addEditTSComponents);
}