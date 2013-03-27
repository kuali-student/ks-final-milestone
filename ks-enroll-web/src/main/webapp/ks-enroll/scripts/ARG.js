/**
 * Configure toolbar on Manage AO page.
 * divId -- The section contains hiddencolumns
 * hiddenColumns -- each row of AO list contains hiddenColumns storing values processed in server side.
 * buttons -- buttons on the toolbar
 * Note: Each hiddenColumn matches with one button. hiddenColumns and buttons are in the same order.
 */
function handleARGAOToolbar(){
    var divId = "KS-ARGCourseOfferingManagement-ActivityOfferingListSection";

//    var hiddenColumns=['enableApproveButton', 'enableSuspendButton', 'enableCancelButton', 'enableReinstateButton', 'enableDeleteButton', 'enableDraftButton'];

    var buttons = ['KS-CourseOfferingManagement-ToolBar-Approve-AO-ARG', 'KS-CourseOfferingManagement-ToolBar-Suspend-AO-ARG',
                   'KS-CourseOfferingManagement-ToolBar-Cancel-AO-ARG', 'KS-CourseOfferingManagement-ToolBar-Reinstate-AO-ARG',
                   'KS-CourseOfferingManagement-ToolBar-Delete-AO-ARG', 'KS-CourseOfferingManagement-ToolBar-Draft-AO-ARG'];

   var hiddenColumns=['enableApproveButton', 'enableDeleteButton', 'enableDraftButton'];
//
//    var buttons = ['KS-CourseOfferingManagement-ToolBar-Approve-AO', 'KS-CourseOfferingManagement-ToolBar-Delete-AO',
//        'KS-CourseOfferingManagement-ToolBar-Draft-AO'];

    handleToolbar(divId, hiddenColumns, buttons)
}