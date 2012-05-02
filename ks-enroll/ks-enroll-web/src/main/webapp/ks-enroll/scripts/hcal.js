jq(function() {
    // code to execute when the DOM is ready

    // the following code is not currently being used, because a hidden ActionButton which
    // does nothing was used instead to change the behavior of the Enter key
    //jq("#KS-HolidayCalendar-MetaSection_div input[type='text']").bind('keypress', tabToNextInput);

    // until Rice 2.2 allows onXxxScript properties to work...
    jq('#viewlayout_div').delegate('#KS-HolidayCalendar-HolidaySection_group input[id^=allDayCheckbox]', 'click', function() {
        setAttributesFromAllDayCheckbox(jq(this));
    });
    jq('#viewlayout_div').delegate('#KS-HolidayCalendar-HolidaySection_group input[id^=dateRangeCheckbox]', 'click', function() {
        setAttributesFromDateRangeCheckbox(jq(this));
    });
    jq('#viewlayout_div').delegate('#KS-HolidayCalendar-HolidaySection_group input[id^=timeField]', 'keyup', function() {
        if (this.id.indexOf('AmPm') == -1) {  // ignore timeFieldXxxAmPm fields
            setAttributesFromTimeField(jq(this));
        }
    });
    // end of Rice 2.2 work-around
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

        // only enable am/pm fields if corresponding time field is not blank
        if (timeFieldStart.val()) {
            row.find('select[id^=timeFieldStartAmPm_]').removeAttr('disabled');//.removeAttr('title');
        }
        if (timeFieldEnd.val()) {
            row.find('select[id^=timeFieldEndAmPm_]').removeAttr('disabled');//.removeAttr('title');
        }
    }
}
function setAttributesFromDateRangeCheckbox(checkbox) {
    var row = checkbox.closest('tr');
    var fields = row.find('input[id^=endDate]');
    if (row.find('input[id^=dateRangeCheckbox]:checked').length == 1) {
        // enable end date field
        fields.removeAttr('disabled');//.removeAttr('title');
    }
    else {
        fields.attr('disabled','true');//.attr('title','Enable by un-checking "Date Range"');
    }
}
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