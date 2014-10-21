/* Add class to iframes so that we can style them differently, when needed */
jQuery(function(){
    var isInIframe = (window.location !== window.parent.location) ? true : false;
    if (isInIframe === true) {
        jQuery('html').addClass('ks-iframe');
    }
});