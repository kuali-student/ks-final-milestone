/**
 * Configure toolbar on Manage CO page.
 * divId -- The section contains hiddencolumns
 * hiddenColumns -- each row of CO list contains hiddenColumns storing values processed in server side.
 * buttons -- buttons on the toolbar
 * Note: Each hiddenColumn matches with one button. hiddenColumns and buttons are in the same order.
 */
function handleCOToolbar(){
    var divId = "KS-CourseOfferingManagement-CourseOfferingListSection";

//    var hiddenColumns=['enableApproveButton', 'enableSuspendButton', 'enableCancelButton', 'enableReinstateButton', 'enableDeleteButton'];
//
//    var bottons = ['KS-CourseOfferingManagement-ToolBar-Approve-CO', 'KS-CourseOfferingManagement-ToolBar-Suspend-CO',
//                   'KS-CourseOfferingManagement-ToolBar-Cancel-CO', 'KS-CourseOfferingManagement-ToolBar-Reinstate-CO',
//                   'KS-CourseOfferingManagement-ToolBar-Delete-CO'];

    var hiddenColumns=['enableApproveButton', 'enableDeleteButton'];

    var bottons = ['KS-CourseOfferingManagement-ToolBar-Approve-CO', 'KS-CourseOfferingManagement-ToolBar-Delete-CO'];

    handleToolbar(divId, hiddenColumns, bottons);
}

/**
 * Configure toolbar on Manage AO page.
  * divId -- The section contains hiddencolumns
  * hiddenColumns -- each row of AO list contains hiddenColumns storing values processed in server side.
  * buttons -- buttons on the toolbar
  * Note: Each hiddenColumn matches with one button. hiddenColumns and buttons are in the same order.
 */
function handleAOToolbar(){
    var divId = "KS-CourseOfferingManagement-ActivityOfferingListSection";

//    var hiddenColumns=['enableApproveButton', 'enableSuspendButton', 'enableCancelButton', 'enableReinstateButton', 'enableDeleteButton', 'enableDraftButton'];
//
//    var buttons = ['KS-CourseOfferingManagement-ToolBar-Approve-AO', 'KS-CourseOfferingManagement-ToolBar-Suspend-AO',
//                   'KS-CourseOfferingManagement-ToolBar-Cancel-AO', 'KS-CourseOfferingManagement-ToolBar-Reinstate-AO',
//                   'KS-CourseOfferingManagement-ToolBar-Delete-AO', 'KS-CourseOfferingManagement-ToolBar-Draft-AO'];

    var hiddenColumns=['enableApproveButton', 'enableDeleteButton', 'enableDraftButton'];

    var buttons = ['KS-CourseOfferingManagement-ToolBar-Approve-AO', 'KS-CourseOfferingManagement-ToolBar-Delete-AO',
                    'KS-CourseOfferingManagement-ToolBar-Draft-AO'];

    handleToolbar(divId, hiddenColumns, buttons)
}

function selectAllAOs(){
    var table = jQuery("#KS-CourseOfferingManagement-ActivityOfferingListSection").find("table");
    jQuery('td input:checkbox', table).attr('checked', 'checked');
    handleAOToolbar();
}



function handleCOSelection(component) {
    var coId = jQuery("#edit_co_select_control").val();
    handleCONavigation(component, coId);
}

function handleCONavigation(component, coId) {

    var isValidForm = validateForm();

    // "Save and Continue"
    var saveAndContinue = jQuery("#edit_co_save_and_continue").attr("data-submit_data");
    var submit_data_array = saveAndContinue.split(',');
    var i = 0;
    for (; i<submit_data_array.length; ) {
        var data = submit_data_array[i].split(':');
        if( data[0] == '"actionParameters[coId]"') {
            data[1] = '"' + coId + '"';
            submit_data_array[i] = data[0] + ":" + data[1];
            break;
        }
        i++;
    }
    jQuery("#edit_co_save_and_continue").attr("data-submit_data", submit_data_array.join());

    // "Continue Without Saving"
    var cancelSaveAndLoad = jQuery("#edit_co_cancel").attr("data-submit_data");
    var cancel_data_array = cancelSaveAndLoad.split(',');
    i = 0;
    for(; i<cancel_data_array.length; ) {
        var data = cancel_data_array[i].split(':');
        if( data[0] == '"actionParameters[coId]"') {
            data[1] = '"' + coId + '"';
            cancel_data_array[i] = data[0] + ":" + data[1];
            break;
        }
        i++;
    }
    jQuery("#edit_co_cancel").attr("data-submit_data", cancel_data_array.join());

    showLightboxComponent( 'CourseOfferingEdit-NavigationConfirmation' );

}
