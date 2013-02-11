function handleCOToolbar(){
    var divId = "KS-CourseOfferingManagement-CourseOfferingListSection";

    var hiddenColumns=['enableApproveButton', 'enableSuspendButton', 'enableCancelButton', 'enableReinstateButton', 'enableDeleteButton'];

    var bottons = ['KS-CourseOfferingManagement-ToolBar-Approve-CO', 'KS-CourseOfferingManagement-ToolBar-Suspend-CO',
                   'KS-CourseOfferingManagement-ToolBar-Cancel-CO', 'KS-CourseOfferingManagement-ToolBar-Reinstate-CO',
                   'KS-CourseOfferingManagement-ToolBar-Delete-CO'];

    handleToolbar(divId, hiddenColumns, bottons);
}

function handleAOToolbar(){
    var divId = "KS-CourseOfferingManagement-ActivityOfferingListSection";

    var hiddenColumns=['enableApproveButton', 'enableSuspendButton', 'enableCancelButton', 'enableReinstateButton', 'enableDeleteButton', 'enableDraftButton'];

    var buttons = ['KS-CourseOfferingManagement-ToolBar-Approve-AO', 'KS-CourseOfferingManagement-ToolBar-Suspend-AO',
                   'KS-CourseOfferingManagement-ToolBar-Cancel-AO', 'KS-CourseOfferingManagement-ToolBar-Reinstate-AO',
                   'KS-CourseOfferingManagement-ToolBar-Delete-AO', 'KS-CourseOfferingManagement-ToolBar-Draft-AO'];

    handleToolbar(divId, hiddenColumns, buttons)
}

function selectAllAOs(){
    var table = jQuery("#KS-CourseOfferingManagement-ActivityOfferingListSection").find("table");
    jQuery('td input:checkbox', table).attr('checked', 'checked');
    handleAOToolbar();
}
