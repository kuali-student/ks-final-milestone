var g_IsTriggeredByControlCheckbox = false;

/*
 *  Add a "control" checkbox to the first cell of the header row.  This checkbox
 *  will toggle all checkboxes in the first cell of the collection.  In addition,
 *  the control checkbox will be unchecked whenever one or more of the collection
 *  checkboxes is unchecked; it will only be checked if all checkboxes are checked.
 *  Parameters:
 *      readOnly        : If true, the control checkbox will not be added
 *      collectionId    : ID of collection to receive selection control checkbox
 *      callClickEvents : Determines whether the individual row selection checkboxes
 *                        will have their click event triggered, or just have the
 *                        "checked" property set to true.  Optional, default is false.
 *      onClickFunction : Optional function name that, if passed in, will be called
 *                        whenever the control checkbox is clicked.  If parameters
 *                        need to be passed in also, wrap the function in an anonymous
 *                        function, like so: function(){sampleFunction("arg1")}
 */
function ksAddRowSelectionCheckbox(readOnly, collectionId, callClickEvents, onClickFunction) {
    if (readOnly === true) {
        return;
    }

    var collectionCheckboxes = jQuery('div#'+collectionId+' tbody > tr').find('td:first input[type="checkbox"]');
    if (collectionCheckboxes.length == 1) {
        return;
    }

    var newCheckbox = jQuery("<input type='checkbox' id='"+collectionId+"_toggle_control_checkbox'/>");
    newCheckbox.click(function(){
        var checkbox = jQuery(this);
        var isChecked = checkbox.prop('checked');
        checkbox.closest('table').find('tbody > tr')
            .find('td:first input[type="checkbox"]').each(function(){
                var cb = jQuery(this);
                if (isChecked != cb.prop('checked')) {
                    if (callClickEvents === true) {
                        g_IsTriggeredByControlCheckbox = true;
                        cb.click();
                        g_IsTriggeredByControlCheckbox = false;
                    } else {
                        cb.prop('checked',isChecked);
                    }
                }
            });

        if (typeof onClickFunction === "function") {
            onClickFunction();
        }
    });
    jQuery('div#'+ collectionId +' table > thead > tr > th:first').prepend(newCheckbox);

    var clickName;
    collectionCheckboxes.each(function(ndx,ctl) {
            clickName = "click." + collectionId + "_row_" + ndx;
            jQuery(this).on(clickName, function(){
                _ksControlCheckboxStatus(collectionId,this);
            });
        });
}


/*
 *  Collection row checkboxes in the first cell will cause the control checkbox
 *  to be set or unset.  The control checkbox should only be checked when all
 *  collection checkboxes are set, otherwise it should be unchecked.
 */
function _ksControlCheckboxStatus(collectionId,source) {
    if (g_IsTriggeredByControlCheckbox) {
        return;  // clicks were triggered by control itself, ignore
    }

    var controlCheckbox = jQuery("#"+ collectionId +"_toggle_control_checkbox");

    // if any row selection checkbox is false, the control checkbox is false also
    if ( ! jQuery(source).prop('checked')) {
        controlCheckbox.prop('checked',false);
        return;
    }

    // if all row selection checkboxes are true, make the control checkbox true also
    var areAllRowsChecked = true;
    jQuery('div#'+collectionId+' tbody > tr').find('td:first input[type="checkbox"]')
        .each(function(ndx,ctl) {
            if (!jQuery(this).prop('checked')) {
                areAllRowsChecked = false;
                return false; // exit .each()
            }
        });
    controlCheckbox.prop('checked',areAllRowsChecked);
}
