function ajaxCallActivityOfferings(controllerMethod, courseOfferingId, courseOfferingDesc) {

    var container;
    var rowContent;

//    ajaxSubmitForm(controllerMethod, updateTable,
//        {courseOfferingId:courseOfferingId, coDisplayInfoId:courseOfferingId },
//        jQuery('#' + courseOfferingId), null, "update-component");

    ajaxSubmitForm(controllerMethod, updateTable, {courseOfferingId: courseOfferingId}, null, null, null);

    function updateTable() {

        container = jQuery('#' + courseOfferingId);
//        row = jQuery(container).parents('tr');

        if (jQuery('#scheduleOfClassesSearchResults').length > 0) {
            rowContent = jQuery('#scheduleOfClassesSearchResults').html();
//            row.after('<tr><td colspan="5" width="100%">' + rowContent + '</td></tr>');
            container.append('<p>' + courseOfferingDesc + '</p>' + '<p>' + rowContent + '</p>')
        }

//        return false;
    }
}