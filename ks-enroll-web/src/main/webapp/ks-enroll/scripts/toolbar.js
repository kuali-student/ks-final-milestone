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
        var field = getSelector(hiddenColumns[j]);
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

        jQuery(selectedRows).each(function(){
            var cellData = oTable.fnGetData(this);
            for(var i=0; i<cellData.length; i++)
            {
                for(var key in hiddenColumnsMap){
                    if(jQuery(cellData[i]).find(key).val() == 'true' || jQuery(cellData[i]).find(key).val() == 'false'){
                        hiddenColumnsMap[key].push(jQuery(cellData[i]).find(key).val());
                    }
                }
            }
        });

    });
    handleButtons(hiddenColumnsMap, buttonsMap);
}

/** The following method only get one table from specified divId. We want to support the case with
 *  multiple tables. so replace it with the above one.
function processToolbar(divId, hiddenColumnsMap, buttonsMap){
    var oTable = getDataTableFromDiv(divId);

    var selectedRows =  jQuery(oTable.fnGetNodes()).filter(':has(:input:checkbox:checked)');

   jQuery(selectedRows).each(function(){
       var cellData = oTable.fnGetData(this);
       for(var i=0; i<cellData.length; i++)
       {
           for(var key in hiddenColumnsMap){
               if(jQuery(cellData[i]).find(key).val() == 'true' || jQuery(cellData[i]).find(key).val() == 'false'){
                   hiddenColumnsMap[key].push(jQuery(cellData[i]).find(key).val());
               }
           }
       }
   });

   handleButtons(hiddenColumnsMap, buttonsMap);
}

function getDataTableFromDiv(divId){
 var id = jQuery("#" + divId).find("table").attr('id');
 var oTable = jQuery("#" + id).dataTable();
 return oTable;
 }
*/

function getSelector(name){
    return "input[name$='" + name +"']";
}

function getDataTableHandle(divId){
    var id = jQuery("#" + divId).attr('id');
    var oTable = jQuery("#" + id).dataTable();
    return oTable;
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
        console.log("found if");
        var firstItem = true;
        jQuery(div).find(':button').each(function () {
            if (!firstItem) {
                jQuery(this).addClass("ks-toolbar-no-left-border");
            }
            firstItem = false;
        });
    }
}