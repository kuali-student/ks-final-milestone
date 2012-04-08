jq(function() {
    // code to execute when the DOM is ready

    jq("#KS-AcademicCalendar-MetaSection_div input[type='text']").bind('keypress', tabToNextInput);
});