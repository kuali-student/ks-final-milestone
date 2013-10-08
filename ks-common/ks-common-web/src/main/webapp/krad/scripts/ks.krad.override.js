

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
