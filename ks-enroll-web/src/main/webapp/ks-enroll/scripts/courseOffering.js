function handleToolbar(atpType) {
    var enableAddButton = getSelector("enableAddButton");
    var enableApproveButton = getSelector("enableApproveButton");
    var enableSuspendButton = getSelector("enableSuspendButton");
    var enableCancelButton = getSelector("enableCancelButton");
    var enableReinstateButton = getSelector("enableReinstateButton");
    var enableDeleteButton = getSelector("enableDeleteButton");
    var enableCopyButton = getSelector("enableCopyButton");
    var enableDraftButton = getSelector("enableDraftButton");

    var enableAddButtonVals=[];
    var enableApproveButtonVals=[];
    var enableSuspendButtonVals=[];
    var enableCancelButtonVals=[];
    var enableReinstateButtonVals=[];
    var enableDeleteButtonVals=[];
    var enableCopyButtonVals=[];
    var enableDraftButtonVals=[];

    var oTable = getDataTableHandle(atpType);

    var selectedRows =  jQuery(oTable.fnGetNodes()).filter(':has(:input:checkbox:checked)');

   jQuery(selectedRows).each(function(){
       var cellData = oTable.fnGetData(this);
       for(var i=0; i<cellData.length; i++)
       {
           //findColumn(cellData[i], enableAddButton, enableAddButtonVals);
           findColumn(cellData[i], enableApproveButton, enableApproveButtonVals);
           findColumn(cellData[i], enableSuspendButton, enableSuspendButtonVals);
           findColumn(cellData[i], enableCancelButton, enableCancelButtonVals);
           findColumn(cellData[i], enableReinstateButton, enableReinstateButtonVals);
           findColumn(cellData[i], enableDeleteButton, enableDeleteButtonVals);
           findColumn(cellData[i], enableCopyButton, enableCopyButtonVals);
           findColumn(cellData[i], enableDraftButton, enableDraftButtonVals);
       }
   });

    if(atpType == 'CO'){
      // handleButton(enableAddButtonVals, "KS-CourseOfferingManagement-ToolBar-Add-CO");
       handleButton(enableApproveButtonVals, "KS-CourseOfferingManagement-ToolBar-Approve-CO");
       handleButton(enableSuspendButtonVals, "KS-CourseOfferingManagement-ToolBar-Suspend-CO" );
       handleButton(enableCancelButtonVals, "KS-CourseOfferingManagement-ToolBar-Cancel-CO");
       handleButton(enableReinstateButtonVals, "KS-CourseOfferingManagement-ToolBar-Reinstate-CO");
       handleButton(enableDeleteButtonVals, "KS-CourseOfferingManagement-ToolBar-Delete-CO");
    }else{
      // handleButton(enableAddButtonVals, "KS-CourseOfferingManagement-ToolBar-Add-AO");
       handleButton(enableApproveButtonVals, "KS-CourseOfferingManagement-ToolBar-Approve-AO");
       handleButton(enableSuspendButtonVals, "KS-CourseOfferingManagement-ToolBar-Suspend-AO" );
       handleButton(enableCancelButtonVals, "KS-CourseOfferingManagement-ToolBar-Cancel-AO");
       handleButton(enableReinstateButtonVals, "KS-CourseOfferingManagement-ToolBar-Reinstate-AO");
       handleButton(enableDeleteButtonVals, "KS-CourseOfferingManagement-ToolBar-Delete-AO");
       handleButton(enableCopyButtonVals, "KS-CourseOfferingManagement-ToolBar-Copy-AO");
       handleButton(enableDraftButtonVals, "KS-CourseOfferingManagement-ToolBar-Draft-AO");
    }

}


function handleButton(arr, id) {
    if(jQuery.isEmptyObject(arr)){
        jQuery("#" + id).attr("disabled", "disabled");
    } else{
        if(jQuery.inArray("false", arr) != -1){
            jQuery("#" + id).attr("disabled", "disabled");
        } else{
           jQuery("#" + id).removeAttr("disabled");
        }
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