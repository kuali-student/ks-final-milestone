/**
 * handle toolbar to make buttons enabled/disabled based on values stored in the hiddenColumns
 * @param divId -- section contains hiddencolumns
 * @param hiddenColumns -- each hiddenColumn has true/false value
 * @param buttons -- button id in the toolbar
 */

function handleToolbar(divId, hiddenColumns, buttons){
    var hiddenColumnsMap = new Object();
    var buttonsMap = new Object();
    for(var j=0; j<hiddenColumns.length; j++) {
        var field = hiddenColumns[j];
        hiddenColumnsMap[field]=[];
        buttonsMap[field]=buttons[j];
    }

    processToolbar(divId, hiddenColumnsMap, buttonsMap);
}

function processToolbar(divId, hiddenColumnsMap, buttonsMap) {
    jQuery("#" + divId).find("table").each(function(){
        var id=jQuery(this).attr('id');
        var oTable = jQuery("#" + id).dataTable();
        var selectedRows =  jQuery(oTable.fnGetNodes()).filter(':has(:input:checkbox:checked)');
        var tableColumns = oTable.fnGetSettings().aoColumns;

        jQuery(selectedRows).each(function(){
            var cellData = oTable.fnGetData(this);
            for(var i=0; i<cellData.length; i++)
            {
                for(var key in hiddenColumnsMap){
                    if(jQuery.trim(jQuery(tableColumns[i].sTitle.toString()).text()) == key) {
                        if(jQuery.trim(jQuery(cellData[i]).text()) == 'true' || jQuery.trim(jQuery(cellData[i]).text()) == 'false') {
                            hiddenColumnsMap[key].push(jQuery.trim(jQuery(cellData[i]).text()));
                        }
                    }
                }
            }
        });

    });
    handleButtons(hiddenColumnsMap, buttonsMap);
}

function handleButtons(hiddenColumnsMap, buttonsMap) {
   for(var key in hiddenColumnsMap)
   {
       handleButton(hiddenColumnsMap[key], buttonsMap[key]);
   }
}

function handleButton(arr, id) {
    if(jQuery.isEmptyObject(arr)){
        disableTBButton(id);
    } else{
        evaluateButton(arr, id);
    }
}

function evaluateButton(arr, id){
    if(jQuery.inArray("true", arr) != -1){
        enableTBButton(id);
    } else{
        disableTBButton(id);
    }
}

function enableTBButton(id){
    jQuery("#" + id).removeClass('disabled');
    jQuery("#" + id).removeAttr("disabled");
}

function disableTBButton(id){
    jQuery("#" + id).addClass("disabled");
    jQuery("#" + id).attr("disabled", "disabled");
}

function refreshAddAODropdowns(){
    retrieveComponent('activityOfferingTypeDropDown',undefined, function () {
        retrieveComponent('clusterDropDown');
    });
}

function decorateToolbar(id) {
    var div = jQuery('#' + id);
    if (jQuery(div).hasClass('ks-toolbar-group-padded')) {
        var firstItem = true;
        jQuery(div).find(':button').each(function () {
            if (!firstItem) {
                jQuery(this).addClass("ks-toolbar-no-left-border");
            }
            firstItem = false;
        });
    }
}

/*
    Use Glyph Icons insstead of images.
    This removes an image with a given class name and adds the class to the span containing the image.
 */
function replaceImageWithGlyph(containerId, className) {
    var container = jQuery("#" + containerId);
    var image = jQuery(container).find('img.' + className);
    if (image.length) {
        var span = jQuery(image).parents('span');
        if (span.length) {
            span.addClass(className);
        }
        jQuery(image).remove();
    }
}