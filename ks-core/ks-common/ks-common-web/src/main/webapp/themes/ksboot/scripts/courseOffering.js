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
    var coId = jQuery(component).val();
    handleCONavigation(coId);
}

function handleCONavigation(coId) {

    var isValidForm = validateForm();

    // "Save and Continue"
    injectValueInDataAttribute("#edit_co_save_and_continue", coId, 'coId');
    // "Continue Without Saving"
    injectValueInDataAttribute("#edit_co_cancel", coId, 'coId');

    if(!dirtyFormState.isDirty()) {
        jQuery("#edit_co_cancel").click();
        return;
    }


    showLightboxComponent( 'CourseOfferingEdit-NavigationConfirmation' );

}
// Set label based on the passed in condition
function setLabelIfElse(setId,condition,trueString,falseString){
    if (condition) {
        jQuery('#'+setId).text(trueString);
    }else {
        jQuery('#'+setId).text(falseString);
    }
}
//revert waitlist to active if user selects Cancel
function handleWaitListPrompt(dialog) {
    var dialogResponses = jQuery('input.uif-dialogButtons',dialog);
    for(i =0; i < dialogResponses.length; i++){
        if (dialogResponses[i].checked==true) {
            if (dialogResponses[i].value=='yes') {
                jQuery('#KS-CourseOfferingEdit-HasWaitlist_control').prop('checked',false);
                jQuery('#KS-CourseOfferingEdit-WailtList-Message-Section').hide();
            }
            //Uncheck checked button ...because krad implemented it as a radio bttn!!
            dialogResponses[i].checked=false;
        }
        setValue('dialogResponse','');
    }

    //set label style-class-states back to default
    //TODO: (KSENROLL-9194) research why the following style classes are reverted again sometime
    //      before it is displayed 2nd and subsequent times!!!
    var labels = jQuery("label.uif-primaryDialogButton",dialog);
    labels.removeClass('ui-state-active');
    labels.addClass('ui-state-default');
    closeLightbox();
}

/*
 * Since there is no onClose event triggered for the lightbox we need this to do
 * some dom maipulation prior to open the lightbox
 */
function handleWaitListShowDialog(dialog){
    jQuery('#KS-CourseOfferingEdit-HasWaitlist_control').prop('checked',true);
    jQuery('#KS-CourseOfferingEdit-WailtList-Message-Section').show();

    // This shoud be done before the dialog opens
    //set label style-class-states back to default
    //TODO: (KSENROLL-9194) research why the following style classes are reverted again sometime
    //      before it is displayed 2nd and subsequent times!!!

    var labels = jQuery("label.uif-primaryDialogButton",dialog);
    labels.removeClass('ui-state-active');
    labels.addClass('ui-state-default');
}

function updateExamDriverInFOTable(finalExamDropDownId, finalExamTableCellId, parentReadOnly) {
    if (!parentReadOnly) {
        var finalExamDropDown = jQuery("#" + finalExamDropDownId + "_control");
        var finalExamDriverUI = "";
        var test = finalExamDropDown.val();
        if (finalExamDropDown.val() == "kuali.lu.exam.driver.CourseOffering") {
            finalExamDriverUI = "Course Offering";
        } else if (finalExamDropDown.val() == "kuali.lu.exam.driver.ActivityOffering") {
            finalExamDriverUI = "Activity Offering";
        }

        var finalExamTableCells = jQuery('[id^="' + finalExamTableCellId + '_line"]');
        finalExamTableCells.each(function () {
            jQuery(this).text(finalExamDriverUI);
        });

        var finalExamTableCells1 = jQuery('[id^="edit_co_final_exam_type_line"][id$="_control"]');
        finalExamTableCells1.each(function () {
            if (finalExamDriverUI == "Activity Offering") {
                jQuery(this).show();
            } else {
                jQuery(this).hide();
            }
        });
    }
}

function retrieveDeliveryFormatsComponent(event, baseUrl, id1, id2, finalExamType, readOnly) {
    //retrieveComponent(id1, undefined);
    if(!readOnly){
        ajaxGetValuePair(event, baseUrl, id1, 'ajaxGetGradeRosterLevelTypes');
    }
    if (finalExamType == "STANDARD") {
        //retrieveComponent(id2, undefined);
        updateExamDriverInFOTable('KS-CourseOfferingEdit-FinalExamDriver', 'final_exam_driver_ui', readOnly);
        if(!readOnly){
            ajaxGetValuePair(event, baseUrl, id2, 'ajaxGetFinalExamDriverTypes');
        }
    }

}

function ajaxGetValuePair(event, baseUrl, selectToUpdate, methodToCall) {
    var spinner = jQuery('<div id="spiner_' + selectToUpdate + '" class="blockUI blockMsg blockElement"><img src="' + baseUrl + '/themes/ksboot/images/loader.gif" alt="Loading..."> Loading...</div>');
    var selectedFormat = jQuery(event.target).val();
    var formData = jQuery('#kualiForm').serialize() + '&' + jQuery.param({formatId: selectedFormat});
    var targetUrl = baseUrl + "/kr-krad/courseOfferingCreate?methodToCall=" + methodToCall;
    jQuery.ajax({
        dataType: "json",
        url: targetUrl,
        type: "POST",
        data: formData,
        beforeSend: function () {
            var dropDownElement = jQuery('#' + selectToUpdate + '_control');
            spinner.attr('style', 'position: absolute; top: ' + dropDownElement.offset().top + 'px; left:' + dropDownElement.offset().left + 'px !important; width: ' +  dropDownElement.outerWidth() + 'px;');
            //spinner.width(dropDownElement.width);
            jQuery('#' + selectToUpdate).append(spinner).show();
        },
        complete: function () {
            jQuery('#spiner_' + selectToUpdate).remove();
        },
        success: function (data, textStatus, jqXHR) {
            var dropDownElement = jQuery('#' + selectToUpdate + '_control');
            dropDownElement.find('option').remove().end();
            dropDownElement.removeAttr("disabled");
            for (var i = 0; i < data.length; i++) {
                var newOption = jQuery('<option>' + data[i].value + '</option>');
                newOption.attr('value', data[i].key);
                dropDownElement.append(newOption);
            }
        },
        error: function (jqXHR, status, error) {
            if (console) {
                console.log("Error Occured...............");
            }
        }
    });
}

