

/**
 * Once we get the next rice release, have to remove this override
 *
 * FIXME: Delete this method once KULRICE-9978 resolved
 * @param tableId
 * @param pageNumber
 */
function openDataTablePage(tableId, pageNumber) {
    var oTable = getDataTableHandle(tableId);
    if (oTable == null) {
        oTable = getDataTableHandle(jQuery('#' + tableId).find('.dataTable').attr('id'));
    }
    if (oTable != null) {
        if (pageNumber == "first" || pageNumber == "last") {
            oTable.fnPageChange(pageNumber);
        } else {
            var numericPage = Number(pageNumber) - 1;
            oTable.fnPageChange(numericPage);
        }
    }
}

/*
 * FIXME KSENROLL-10557: Remove once jira is fixed.
 *
 * Function that returns lookup results by script
 */
function returnLookupResultByScript(fieldName, value) {
    var returnField;
    if (parent.jQuery('iframe[id*=easyXDM_]').length > 0) {
        // portal and content on same domain
        returnField = top.jQuery('iframe[id*=easyXDM_]').contents().find('#' + kradVariables.PORTAL_IFRAME_ID).contents().find('[name="' + escapeName(fieldName) + '"]');
    } else if (parent.parent.jQuery('#' + kradVariables.PORTAL_IFRAME_ID).length > 0) {
        // portal and content on different domain
        returnField = parent.parent.jQuery('#' + kradVariables.PORTAL_IFRAME_ID).contents().find('[name="' + escapeName(fieldName) + '"]');
    } else {
        returnField = top.jq('[name="' + escapeName(fieldName) + '"]');
    }
    if (!returnField.length) {
        return;
    }

    returnField.val(value);

    //Don't put focus on hidden fields.
    if(returnField.is(":visible")) {
        returnField.focus();
        returnField.blur();
        returnField.focus();

        // trigger change event
        returnField.change();
    }
}
