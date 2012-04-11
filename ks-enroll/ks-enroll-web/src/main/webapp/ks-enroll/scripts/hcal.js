jq(function() {
    // code to execute when the DOM is ready

    jq("#KS-HolidayCalendar-MetaSection_div input[type='text']").bind('keypress', tabToNextInput);
});