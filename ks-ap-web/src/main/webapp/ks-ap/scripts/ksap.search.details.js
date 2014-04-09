jQuery(function(){
    if( (jQuery('#CourseDetails-InquiryView').length >0) && (sessionStorage.getItem('last_search') != null) ) {
        //setting a sentinel value for back button use
        sessionStorage.setItem('back_search', sessionStorage.getItem('last_search'));
    }
});

function detectReferrerForBackLinkText() {
    var backLink = jQuery('.uif-link.ks-fontello-icon-left-dir.cdp-back-link:visible');
    var referrer = document.referrer.split('/')[5].split('?')[0];
    if ( referrer == "course" ) {
        //from course search, change link text
        backLink.text('Return to search results');
    } else if ( referrer == "planner" ) {
        //from planner, change link test
        backLink.text('Return to planner');
    }

}