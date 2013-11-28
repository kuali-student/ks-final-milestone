/**
 * POC to merge both time and am/pm in a single field. This is a validation to make sure
 * the user enters in the correct format
 */
jQuery.validator.addMethod("validTimeAMPMPattern",
    function(value, element) {
        return this.optional(element) || /^(0?[1-9]|1[012]):[0-5]\d{1} [AP][M]$/.test(value);
    }, "Valid time format hh:mm AM or hh:mm PM")


function parseAndReplaceTimeClause(startTimeTextField, daysPatternTextField) {
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

    /*//  Passing the hashmap here to make it easier to calculate the end time.
    var timeSlots = queryForTimeSlots(timeSlotType, daysPatternText, timeMap);

    endTimeTextField.val(timeSlots[0]['endTime']);*/
}

function formatTime(t) {
    return pad(t['hours'], 2) + ":" + t['minutes'] + " " + ((t['meridiem'] == "a") ? "AM" : "PM");
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