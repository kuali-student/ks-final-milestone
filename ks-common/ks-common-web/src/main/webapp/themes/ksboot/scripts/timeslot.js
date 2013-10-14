
var selectedTimeSlotOptions;
var availableTimeSlotTypes;

/**
 * POC to merge both time and am/pm in a single field. This is a validation to make sure
 * the user enters in the correct format
 */
jQuery.validator.addMethod("validTimeAMPMPattern",
    function(value, element) {
        return this.optional(element) || /^(0?[1-9]|1[012]):[0-5]\d{1}[AP][M]$/.test(value);
    }, "Valid time format hh:mmAM or hh:mmPM")

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

function parseAndReplaceTimeClause(startTimeTextField, endTimeTextField, daysPatternTextField, timeSlotType) {
    //  Get the value from the start time text field.
    var startTimeText = startTimeTextField.val();
    var daysPatternText = daysPatternTextField.val();

    //  The days pattern text field must have a value. If not, give it focus.
    if (! daysPatternText) {
        daysPatternTextField.focus();
        return;
    }

    // Parse hours and minutes and am/pm
    var timeMap = parseTimeString(startTimeText);
    if (! timeMap) {
        return;
    }
    var formattedStartTime = formatTime(timeMap);

    //  Write the processed value back into the text field.
    startTimeTextField.val(formattedStartTime);
    //  Force another client-side validation to clear out the validation of the abbreviated time format.
    validateFieldValue(startTimeTextField);

    //  Passing the hashmap here to make it easier to calculate the end time.
    var timeSlots = queryForTimeSlots(timeSlotType, daysPatternText, timeMap);

    endTimeTextField.val(timeSlots[0]['endTime']);
}

function formatTime(t) {
    return pad(t['hours'], 2) + ":" + t['minutes'] +  ((t['meridiem'] == "a") ? "AM" : "PM");
}

function queryForTimeSlots(timeSlotType, daysPattern, startTimeMap) {

    var startTime = formatTime(startTimeMap);

    // Here's where we would make an async call to get a list of possible time slots.
    // For now we'll just simulate a call that returns a single possiblity and fake the end time.
    var endTime = calculateEndTime(startTimeMap);
    var timeSlots = new Array();
    var ts = {'id': 1, 'endTime': endTime };
    timeSlots[0] = ts;
    return timeSlots;
}

//  This method should go away one a real time slot query is in place.
function calculateEndTime(startTimeMap) {
    var hours = startTimeMap['hours'];
    var minutes = startTimeMap['minutes'];
    var meridiem = startTimeMap['meridiem'];
    if (minutes == "00") {
        minutes = "50";
    } else {
        //  If the minutes aren't 00 and the hours are less that 12 just increment the hours.
        if (hours < 12) {
            if (hours == 11) {
                //  Swap AM/PM
                meridiem = ((meridiem == "a") ? "p" : "a");
            }
            hours++;
        } else {
            hours = 1;
            //  Swap AM/PM
            meridiem = ((meridiem == "a") ? "p" : "a");
        }
    }
    return formatTime({'hours': hours, 'minutes': minutes, 'meridiem': meridiem});
}

//  Parses a string of text into a standard time format.
//  Returns a map contains hours, minutes, and  meridiem if the parsing succeeds. Otherwise, null.
function parseTimeString(timeStringText) {

    var hours, minutes, meridiem;

    //  Clean up the input by stripping any characters which are not digits, A, or P, then convert it to lower case.
    //  The : can be removed because it is assumed that minutes are always two digits (unless a single digit is given,
    //  indicating the hour and 00 would be assumed for the minutes). We are also removing the trailing 'm'.
    //  After the clean up the resulting string should be in these formats: ha, hha, hmma, or hhmma  (where 'a' is either
    // 'a' or 'p')
    var timeString = timeStringText.replace(/[^\daApP]/g, "");
    timeString = timeString.replace(/^0/, ""); // Also, strip leading zeros.
    timeString = timeString.toLowerCase();

    if (! timeString) {
        return;
    }

    //  This logic can be condensed a bit I think.
    if (timeString.length == 2) {   //  9a
        meridiem = timeString.substring(1);
        hours = timeString.substring(0, 1);
        minutes = "00";
    } else if (timeString.length == 3) {  //  10a
        meridiem = timeString.substring(2);
        hours = timeString.substring(0, 2);
        minutes = "00";
    } else if (timeString.length == 4) {  //  930a
        meridiem = timeString.substring(3);
        hours = timeString.substring(0, 1);
        minutes = timeString.substring(1, 3);
    } else if (timeString.length == 5)  {   //  1230p
        meridiem = timeString.substring(4);
        hours = timeString.substring(0, 2);
        minutes = timeString.substring(2, 4);
    } else {
        //  If this happens the input was invalid.
        return;
    }

    if (! hours || ! minutes || ! meridiem) {
       return;
    }

    //  Validate hours and minutes
    if (! hours.match(/^[1-9]|1[012]$/) || ! minutes.match(/^[0-5][0-9]$/) || ! meridiem.match(/^[ap]{1}$/)) {
        return;
    }
    return  {'hours': hours, 'minutes': minutes, 'meridiem': meridiem};
}

function pad(number, width, char) {
    char = char || '0';
    number = number + '';
    return number.length >= width ? number : new Array(width - number.length + 1).join(char) + number;
}