//Called onDocumentReady in CourseDetailsUI.xml (Course Details page) to detect page referrer
function detectReferrerForBackLinkText() {
    var backLink = jQuery('.uif-link.cdp-back-link:visible');

    //check for empty document.referrer
    if (document.referrer != null && document.referrer.length > 0) {
        var referrer = document.referrer.split('kr-krad/')[1].split('?')[0];

        if ( referrer == "course" ) {
            //from course search, change link text
            backLink.text('Return to search results');
        } else if ( referrer == "planner" ) {
            //from planner, change link test
            backLink.text('Return to plan');
        }
    }
}


//Sections with multiple offerings checkbox behavior
function checkboxSelectAndHighlight() {

    //Select checkbox if clicking anywhere on the row
    jQuery('tr').click(function(event) {
        if (event.target.type !== 'checkbox') {
            jQuery(':checkbox', this).trigger('click');
        }
    });

    //Don't select checkbox if clicking on link
    jQuery('tr a').click(function(event) {
        event.stopPropagation();
    });

    //Add and remove class when checkbox is toggled
    jQuery(".uif-checkboxControl").change(function () {
        if(jQuery(this).is(":checked")){
            jQuery(this).closest('tr').addClass("ksap-selected-row");
        }else{
            jQuery(this).closest('tr').removeClass("ksap-selected-row");
        }
    });
}