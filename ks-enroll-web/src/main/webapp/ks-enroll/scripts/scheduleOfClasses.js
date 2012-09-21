var originalRowHeight;
var openImage = "../ks-enroll/images/details_open.png";
var closeImage = "../ks-enroll/images/details_close.png";

function ajaxCallActivityOfferings(controllerMethod, courseOfferingId, description) {
    var divTempId = 'findThisId_' + courseOfferingId;
    var image = jQuery('#' + courseOfferingId).children('img');

    var isDivVisible = jQuery('#' + divTempId).is(':visible');
    if (isDivVisible) {
        var tr = jQuery(jQuery('#' + courseOfferingId)).parents('tr');
        var height = window.originalRowHeight;
        jQuery('#' + divTempId).slideUp(1000).remove();
        jQuery(tr).css("height", height);
        image.attr("src", openImage);
    }
    else {
        ajaxSubmitForm(controllerMethod, updateTable, {courseOfferingId:courseOfferingId}, jQuery('#' + courseOfferingId), null, "update-page");
    }

    function updateTable() {
        if (jQuery('#scheduleOfClassesSearchResults').length > 0) {
            var rowContent = jQuery('#scheduleOfClassesSearchResults').html();
            var div = jQuery('<div/>');
            div.attr('id', divTempId);
            div.html(rowContent);
            var descPara = jQuery('</p>');
            jQuery(descPara).css('margin', '15px 0px');
            descPara.html(description);
            div.prepend(descPara);

            // Calculate the height of the row containing the new div           
            var tr = jQuery(jQuery('#' + courseOfferingId)).parents('tr');
            var td = jQuery(jQuery('#' + courseOfferingId)).parents('td');
            td = jQuery(td).next();
            var trHeight = jQuery(tr).height();
            window.originalRowHeight = trHeight;
            jQuery(div).css("position", "absolute");
            jQuery(div).css("background", "inherit");
            jQuery(div).css("min-width", "600px");
            jQuery(div).css("max-width", "960px");
            td.slideDown(1000).append(div);
            var divHeight = jQuery(div).height();
            console.log(trHeight + ", " + divHeight);
            jQuery(tr).css("height", "+=" + divHeight);
            var image = jQuery('#' + courseOfferingId).children('img');
            image.attr("src", closeImage);
        }
    }
}