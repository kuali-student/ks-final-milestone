jQuery(function(){
    if( (jQuery('#CourseDetails-InquiryView').length >0) && (sessionStorage.getItem('last_search') != null) ) {
        //setting a sentinel value for back button use
        sessionStorage.setItem('back_search', sessionStorage.getItem('last_search'));
    }
});