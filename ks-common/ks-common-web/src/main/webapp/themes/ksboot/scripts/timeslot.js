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

    if (addOrEditAction == 'EDIT'){
        jQuery('#addOrEdit_action').text('Save');
        var termTypeId = 'timeSlotResults[' + editLineIndex + '].typeKey';
        var termType = jQuery(name=termTypeId).text().trim();
        var days = jQuery('#days_line' + editLineIndex + '_control').text();
        var startTimeWithAmPm = jQuery('#startTime_line' + editLineIndex + '_control').text().trim();
        var split = startTimeWithAmPm.split(" ");
        var startTime = split[0];
        var startTimeAmPm = split[1].trim();
        var endTimeWithAmPm = jQuery('#startTime_line' + editLineIndex + '_control').text().trim();
        var split = endTimeWithAmPm.split(" ");
        var endTime = split[0];
        var endTimeAmPm = split[1].trim();

        var spaceStrippedDays = days.trim().replace(/ /g,"");
        jQuery('#addOrEditDays_control').val(spaceStrippedDays);

        jQuery('#addOrEditTermKey_control').val(termType);
        jQuery('#addOrEditStartTime_control').val(startTime);
        jQuery('#addOrEditStartTimeAmPm_control').val(startTimeAmPm);
        jQuery('#addOrEditEndTime_control').val(endTime);
        jQuery('#addOrEditEndTimeAmPm_control').val(endTimeAmPm);

    } else {
        jQuery('#addOrEdit_action').text('Add Slot');
    }
}

function showDeleteDialog() {
    var overrideOptions = { autoDimensions:false, width:500 };
    showLightboxComponent('deleteTimeSlotsConfirmationDialog-lightbox', overrideOptions);
}