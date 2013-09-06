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
        disableButton(id);
    } else{
        evaluateButton(arr, id);
    }
}

function evaluateButton(arr, id){
    if(jQuery.inArray("true", arr) != -1){
        enableButton(id);
    } else{
        disableButton(id);
    }
}

function enableButton(id){
    jQuery("#" + id).removeClass('disabled');
    jQuery("#" + id).removeAttr("disabled");
    var img = jQuery("#" + id).find('img') ,
           src = img.attr('src') ,
           disable = /\_disabled\.png$/;

    if(src.match(disable)) {
        img.attr('src', src.replace(disable, '_enabled.png'));
    }

}

function disableButton(id){
    jQuery("#" + id).addClass("disabled");
    jQuery("#" + id).attr("disabled", "disabled");
    var img = jQuery("#" + id).find('img') ,
           src = img.attr('src') ,
           enable = /\_enabled\.png$/ ;

    if(src.match(enable)) {
        img.attr('src', src.replace(enable, '_disabled.png'));
    }
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