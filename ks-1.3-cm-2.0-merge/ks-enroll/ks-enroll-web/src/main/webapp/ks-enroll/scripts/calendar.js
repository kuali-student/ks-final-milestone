jq(function() {
    // code to execute when the DOM is ready

    // the following code is not currently being used, because a hidden ActionButton which
    // does nothing was used instead to change the behavior of the Enter key
    //jq("#KS-HolidayCalendar-MetaSection_div input[type='text']").bind('keypress', tabToNextInput);
});


function setAttributesFromAllDayCheckbox(checkbox) {
    var row = checkbox.closest('tr');

    if (row.find('input[id^=allDayCheckbox]:checked').length == 1) {
        // disable hh:mm & am/pm fields
        row.find('(input|select)[id^=timeField]').attr('disabled','true');//.attr('title','Enable by un-checking "All Day"');
    }
    else {
        var timeFieldStart = row.find('input[id^=timeFieldStart_]');
        var timeFieldEnd = row.find('input[id^=timeFieldEnd_]');
        timeFieldStart.removeAttr('disabled');//.removeAttr('title');
        timeFieldEnd.removeAttr('disabled');//.removeAttr('title');
        row.find('select[id^=timeField]').removeAttr('disabled');//.removeAttr('title');
        // only enable am/pm fields if corresponding time field is not blank
        //if (timeFieldStart.val()) {
            //row.find('select[id^=timeFieldStartAmPm_]').removeAttr('disabled');//.removeAttr('title');
        //}
        //if (timeFieldEnd.val()) {
            //row.find('select[id^=timeFieldEndAmPm_]').removeAttr('disabled');//.removeAttr('title');
        //}
    }
}
//function setAttributesFromDateRangeCheckbox(checkbox) {
//    var row = checkbox.closest('tr');
//    var fields = row.find('input[id^=endDate]');
//    if (row.find('input[id^=dateRangeCheckbox]:checked').length == 1) {
//        // enable end date field
//        fields.removeAttr('disabled');//.removeAttr('title');
//    }
//    else {
//        fields.attr('disabled','true');//.attr('title','Enable by un-checking "Date Range"');
//    }
//}
function setAttributesFromTimeField(timeField) {
    var row = timeField.closest('tr');
    var amPmFieldId = (timeField.attr('id').indexOf("Start") != -1) ? "timeFieldStartAmPm" : "timeFieldEndAmPm";
    var amPmField = row.find('select[id^=' + amPmFieldId + ']');
    if (timeField.val()) {
        // user has entered something, so enable the am/pm menu
        amPmField.removeAttr('disabled');//.removeAttr('title');
    }
    else {
        amPmField.attr('disabled','true');//.attr('title','Enable by un-checking "Date Range"');
    }
}