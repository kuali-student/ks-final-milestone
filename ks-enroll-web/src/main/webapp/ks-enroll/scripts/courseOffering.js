function handleToolbar(atpType) {
    var enableApproveButton = getSelector("enableApproveButton");
    var enableSuspendButton = getSelector("enableSuspendButton");
    var enableCancelButton = getSelector("enableCancelButton");
    var enableReinstateButton = getSelector("enableReinstateButton");
    var enableDeleteButton = getSelector("enableDeleteButton");
    var enableCopyButton = getSelector("enableCopyButton");
    var enableDraftButton = getSelector("enableDraftButton");

    var enableApproveButtonVals=[];
    var enableSuspendButtonVals=[];
    var enableCancelButtonVals=[];
    var enableReinstateButtonVals=[];
    var enableDeleteButtonVals=[];
    var enableDraftButtonVals=[];

    var coEnableButtonVals=[enableApproveButtonVals,enableSuspendButtonVals, enableCancelButtonVals,
        enableReinstateButtonVals,enableDeleteButtonVals];

    var aoEnableButtonVals=[enableApproveButtonVals,enableSuspendButtonVals, enableCancelButtonVals,
        enableReinstateButtonVals,enableDeleteButtonVals,enableDraftButtonVals];

    var oTable = getDataTableHandle(atpType);

    var selectedRows =  jQuery(oTable.fnGetNodes()).filter(':has(:input:checkbox:checked)');

   jQuery(selectedRows).each(function(){
       var cellData = oTable.fnGetData(this);
       for(var i=0; i<cellData.length; i++)
       {
           findColumn(cellData[i], enableApproveButton, enableApproveButtonVals);
           findColumn(cellData[i], enableSuspendButton, enableSuspendButtonVals);
           findColumn(cellData[i], enableCancelButton, enableCancelButtonVals);
           findColumn(cellData[i], enableReinstateButton, enableReinstateButtonVals);
           findColumn(cellData[i], enableDeleteButton, enableDeleteButtonVals);
           findColumn(cellData[i], enableDraftButton, enableDraftButtonVals);
       }
   });

    if(atpType == 'CO'){
        var ids = ['KS-CourseOfferingManagement-ToolBar-Approve-CO', 'KS-CourseOfferingManagement-ToolBar-Suspend-CO',
                    'KS-CourseOfferingManagement-ToolBar-Cancel-CO', 'KS-CourseOfferingManagement-ToolBar-Reinstate-CO',
                    'KS-CourseOfferingManagement-ToolBar-Delete-CO'];
        handleButtons(coEnableButtonVals, ids);
    }else{
        var ids = ['KS-CourseOfferingManagement-ToolBar-Approve-AO', 'KS-CourseOfferingManagement-ToolBar-Suspend-AO',
                    'KS-CourseOfferingManagement-ToolBar-Cancel-AO', 'KS-CourseOfferingManagement-ToolBar-Reinstate-AO',
                    'KS-CourseOfferingManagement-ToolBar-Delete-AO', 'KS-CourseOfferingManagement-ToolBar-Draft-AO'];
        handleButtons(aoEnableButtonVals, ids);
    }

}

function handleButtons(buttonVals, ids) {
    for(var i=0; i<buttonVals.length; i++)
   {
       handleButton(buttonVals[i], ids[i]);
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
           disable = /\_disabled\.jpg$/ ,
           enable = /\.jpg$/ ;

    if(src.match(disable)) {
        img.attr('src', src.replace(disable, '.jpg'));
    }

}

function disableButton(id){
    jQuery("#" + id).addClass("disabled");
    jQuery("#" + id).attr("disabled", "disabled");
    var img = jQuery("#" + id).find('img') ,
           src = img.attr('src') ,
           disable = /\_disabled\.jpg$/ ,
           enable = /\.jpg$/ ;

    if(src.match(enable)) {
        img.attr('src', src.replace(enable, '_disabled.jpg'));
    }
}

function getDataTableHandle(atpType){
    var divId;

    if(atpType == 'CO'){
        divId ="KS-CourseOfferingManagement-CourseOfferingListSection";
    }else {
        divId = "KS-CourseOfferingManagement-ActivityOfferingListSection";
    }

    var id = jQuery("#" + divId).find("table").attr('id');
    var oTable = jQuery("#" + id).dataTable();
    return oTable;
}

function getSelector(name){
    return "input[name$='" + name +"']";
}

function findColumn(cellData, selector, arr){
    if(jQuery(cellData).find(selector).val() == 'true' || jQuery(cellData).find(selector).val() == 'false'){
        arr.push(jQuery(cellData).find(selector).val());
     }
}
