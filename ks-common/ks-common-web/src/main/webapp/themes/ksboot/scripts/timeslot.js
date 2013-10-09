
var selectedTimeSlotOptions;

/**
 *  This method initializes the popup for both edit and add action. As we're using the same
 *  popup for both add and edit, it's necessary to tweak the html dom.
 */
function showTimeSlotLightBox(event, addOrEditAction, editLineIndex)
{

    jQuery('#addOrEditDays_control').val('');
    jQuery('#addOrEditTermKey_control').val('');
    jQuery('#addOrEditStartTime_control').val('');
    jQuery('#addOrEditStartTimeAmPm_control_0').attr('checked',true);
    jQuery('#addOrEditEndTime_control').val('');
    jQuery('#addOrEditEndTimeAmPm_control_0').attr('checked',true);
    jQuery('#addOrEditTermKey_control').empty();
    jQuery('#addOrEditDays_control').val('');

   if (jQuery("#addOrEditDays_errors").length) {
        jQuery("#addOrEditDays_errors").empty();
    }
    if (jQuery("#addOrEditTermKey_errors").length) {
        jQuery("#addOrEditTermKey_errors").empty();
    }
    if (jQuery("#addOrEditStartTime_errors").length) {
        jQuery("#addOrEditStartTime_errors").empty();
    }
    if (jQuery("#addOrEditStartTimeAmPm_errors").length) {
        jQuery("#addOrEditStartTimeAmPm_errors").empty();
    }
    if (jQuery("#addOrEditEndTime_errors").length) {
        jQuery("#addOrEditEndTime_errors").empty();
    }
    if (jQuery("#addOrEditEndTimeAmPm_errors").length) {
        jQuery("#addOrEditEndTimeAmPm_errors").empty();
    }
    if (jQuery("#addOrEditTermKey_errors").length) {
        jQuery("#addOrEditTermKey_errors").empty();
    }

    jQuery(".new_ts").removeClass("uif-hasError");
    jQuery(".new_ts_control").removeClass("error");

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
        var timeSlotCode = jQuery('#timeSlotCode_line' + editLineIndex + '_control').text().trim();
        jQuery('#KS-TimeSlot-AddTimeSlotPopupForm .uif-headerText-span').text("Edit Time Slot " + timeSlotCode);

    } else {
        jQuery('#addOrEdit_action').data("submit_data", {methodToCall:"createTimeSlot"});
        jQuery('#addOrEdit_action').text('Add Slot');
        jQuery("#addOrEditTermKey_control").append(selectedTimeSlotOptions);
        jQuery('#KS-TimeSlot-AddTimeSlotPopupForm .uif-headerText-span').text("Add TimeSlot");
    }
          //hideBubblePopups()
    var overrideOptions = { autoDimensions:true, beforeClose:function () {
        removeMessageTooltip("addOrEditDays");
                removeMessageTooltip("addOrEditTermKey");
                removeMessageTooltip("addOrEditStartTime");
                removeMessageTooltip("addOrEditStartTimeAmPm");
                removeMessageTooltip("addOrEditEndTime");
                removeMessageTooltip("addOrEditEndTimeAmPm");
            removeMessageTooltip("addOrEditTermKey");
    }};
    showLightboxComponent('KS-TimeSlot-AddTimeSlotPopupForm',overrideOptions);
}

function showDeleteDialog() {
    var overrideOptions = { autoDimensions:false, width:500 };
    showLightboxComponent('deleteTimeSlotsConfirmationDialog-lightbox', overrideOptions);
}

function validateTimeSlot(){
    var addEditTSComponents = jQuery('.new_ts_control');
    result = validateLineFields(addEditTSComponents);
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
}

function removeMessageTooltip(fieldId) {
    var elementInfo = getHoverElement(fieldId);
    var element = elementInfo.element;
    if (elementInfo.type == "fieldset") {
        //for checkbox/radio fieldsets we put the tooltip on the label of the first input
        element = jQuery(element).filter(".uif-tooltip");
    }

    var data = getValidationData(jQuery("#" + fieldId));
    if (data && data.showTimer) {
        clearTimeout(data.showTimer);
    }

    var tooltipId = jQuery(element).GetBubblePopupID();

    if (tooltipId) {
        //this causes the tooltip to be IMMEDIATELY hidden, rather than wait for animation
        jQuery("#" + tooltipId).css("opacity", 0);
        jQuery("#" + tooltipId).hide();
        jQuery(element).RemoveBubblePopup();
    }
    else {
        jQuery(element).RemoveBubblePopup();
    }
}