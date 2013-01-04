
function ks_krad_collection_rowSelectionPrep() {
    // hide the first column of the table, the "Select?" column of checkboxes
    jQuery('table.ks-krad-collection-rowSelectionHighlight th:first-child').attr('style','display:none');
    jQuery('table.ks-krad-collection-rowSelectionHighlight td:first-child').attr('style','display:none');

    // reset any checked selection checkboxes to false
    jQuery('table.ks-krad-collection-rowSelectionHighlight td:first-child input:checked').prop('checked',false);

    // Add a click handler to all non-addLine rows
    jQuery('table.ks-krad-collection-rowSelectionHighlight tr:not(.uif-collectionAddItem)').click( function(event) {
        if (event.target.nodeName == "A") return;  // ignore click event bubbled up from A/anchor link
        // NOTE: if used on editable collection, INPUT, SELECT and TEXTAREA should also be skipped

        // if only one row is selectable, remove the currently highlighted row
        jQuery('table.ks-krad-collection-allowOneRow tr.ks-krad-selected:first')
            .removeClass('ks-krad-selected');

        // remove ks-krad-selected class from prior selected row(s)
        var jRow = jQuery(this);
        if (jRow.hasClass('ks-krad-selected')) {
            jRow.removeClass('ks-krad-selected');
        }
        else {
            jRow.addClass('ks-krad-selected');
        }
    } );
}


function ks_krad_collection_setSelectedRows() {
    var selected = jQuery('table.ks-krad-collection-rowSelectionHighlight tr.ks-krad-selected');
    if (selected.length == 0) {
        alert("No rows were selected.");
        return false;
    }

    // Selection checkbox should be the first one found in each row
    selected.each( function() {
        jQuery(this).find("input[type='checkbox']:first").prop('checked',true);
    });
    return true;
}
