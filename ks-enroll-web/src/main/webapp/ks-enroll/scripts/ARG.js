/**
 * Configure toolbar on Manage CO page.
 * divId -- The section contains hiddencolumns
 * hiddenColumns -- each row of CO list contains hiddenColumns storing values processed in server side.
 * buttons -- buttons on the toolbar
 * Note: Each hiddenColumn matches with one button. hiddenColumns and buttons are in the same order.
 */
function handleARGCOToolbar(){
    var divId = "KS-ARGCourseOfferingManagement-CourseOfferingListSection";

//    var hiddenColumns=['enableApproveButton', 'enableSuspendButton', 'enableCancelButton', 'enableReinstateButton', 'enableDeleteButton'];
//
//    var bottons = ['KS-CourseOfferingManagement-ToolBar-Approve-CO', 'KS-CourseOfferingManagement-ToolBar-Suspend-CO',
//                   'KS-CourseOfferingManagement-ToolBar-Cancel-CO', 'KS-CourseOfferingManagement-ToolBar-Reinstate-CO',
//                   'KS-CourseOfferingManagement-ToolBar-Delete-CO'];

    var hiddenColumns=['enableApproveButton', 'enableDeleteButton'];

    var bottons = ['KS-CourseOfferingManagement-ToolBar-Approve-CO-ARG', 'KS-CourseOfferingManagement-ToolBar-Delete-CO-ARG'];

    handleToolbar(divId, hiddenColumns, bottons);
}
/**
 * Configure toolbar on Manage AO page.
 * divId -- The section contains hiddencolumns
 * hiddenColumns -- each row of AO list contains hiddenColumns storing values processed in server side.
 * buttons -- buttons on the toolbar
 * Note: Each hiddenColumn matches with one button. hiddenColumns and buttons are in the same order.
 */
function handleARGAOToolbar(divId){
    var buttons = ['KS-CourseOfferingManagement-ToolBar-Approve-AO-ARG', 'KS-CourseOfferingManagement-ToolBar-Suspend-AO-ARG',
                   'KS-CourseOfferingManagement-ToolBar-Cancel-AO-ARG', 'KS-CourseOfferingManagement-ToolBar-Reinstate-AO-ARG',
                   'KS-CourseOfferingManagement-ToolBar-Delete-AO-ARG', 'KS-CourseOfferingManagement-ToolBar-Draft-AO-ARG'];

    var hiddenColumns=['enableApproveButton', 'enableDeleteButton', 'enableDraftButton'];

    handleToolbar(divId, hiddenColumns, buttons)
}
