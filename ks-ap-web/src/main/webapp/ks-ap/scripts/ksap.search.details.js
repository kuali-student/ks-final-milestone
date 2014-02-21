jQuery(function(){
    if( (jQuery('#CourseDetails-InquiryView').length >0) && (localStorage.getItem('last_search') != null) ) {
        //setting a sentinel value for back button use
        localStorage.setItem('back_search', localStorage.getItem('last_search'));
    }
});