/**
 * SCRIPT METHODS USED BY MULTIPLE .JS FILES
 */


/**
 * Enter key causes next action button to fire, so this routine turns the Enter
 * keypress into a Tab keypress, essentially
 */
function tabToNextInput(e) {
    if (e.keyCode === 13) {
        e.preventDefault();
        var inputs = jq("input:visible,select:visible");
        var next = inputs.get(inputs.index(this) + 1);
        if (next) {
            next.focus();
        }
    }
}

function confirmDeletion(msg) {
    var response = confirm(msg);

    if (response == true) {
        submitForm();
    }
}

function confirmDeletionResponse(msg) {
    var response = confirm(msg);

    return response;
}

function confirmDeletion(msg) {
    var response = confirm(msg);

    if (response == true) {
        submitForm();
    }
}

function stepBrowserBack() {
    window.history.back(-2);
}

function showLightbox(componentId) {
    // Insert the following css files before the lightbox appearing
    parent.jQuery('link[href="rice-portal/css/portal.css"]').remove();
    parent.jQuery('head').append('<link href="kr-dev/rice-portal/css/lightbox.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="krad/css/global/base.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="krad/css/ks/theme-krad.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="krad/css/ks/fss-theme-krad.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="ks-enroll/css/enroll.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="ks-enroll/css/acal.css" rel="stylesheet" type="text/css">');

    var overrideOptions = { afterClose:function () {
        // Remove the following css files before the lightbox appearing
        parent.jQuery('link[href="rice-portal/css/lightbox.css"]').remove();
        parent.jQuery('link[href="krad/css/ks/theme-krad.css"]').remove();
        parent.jQuery('link[href="krad/css/ks/fss-theme-krad.css"]').remove();
        parent.jQuery('link[href="ks-enroll/css/enroll.css"]').remove();
        parent.jQuery('link[href="ks-enroll/css/acal.css"]').remove();
        parent.jQuery('link[href="krad/css/global/base.css"]').remove();
        parent.jQuery('head').append('<link href="kr-dev/rice-portal/css/portal.css" rel="stylesheet" type="text/css">');
    }};
    showLightboxComponent(componentId, overrideOptions);
}

