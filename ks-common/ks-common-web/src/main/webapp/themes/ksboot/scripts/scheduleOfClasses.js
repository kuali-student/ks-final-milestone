

function toggleShowButton() {
    var termCodeText = jQuery("#termCode_control").find(":selected").text();
    var searchTypeVal = jQuery("#searchType_control").find(":selected");
    var searchTypeText = searchTypeVal.text();
    var searchTextBoxVal = '';
    switch (searchTypeVal.val()) {
        case 'course' :
            searchTextBoxVal = jQuery("#course_search_text_control").val();
            break;
        case 'department' :
            searchTextBoxVal = jQuery("#department_search_text_control").val();
            break;
        case 'instructor' :
            searchTextBoxVal = jQuery("#instructor_search_text_control").val();
            break;
        case 'titleDesc' :
            searchTextBoxVal = jQuery("#title_description_search_text_control").val();
            break;
    }

    if (termCodeText != '' && searchTypeText != '' && searchTextBoxVal != '') {
        jQuery("#show_button").removeAttr("disabled");
    } else {
        jQuery("#show_button").attr("disabled", "disabled");
    }
}


/**
 * This method expands or collapses all the AO clusters for a specific CO section.
 *
 * @param expandAction expand/collapse link id of a particular CO section
 * @param headerId AO cluster header Id
 */
function showOrHideAOClusters(expandAction, headerId) {

    var expandText = jQuery("#" + expandAction).text().trim();


    var aSelector = headerId  + "_line";

    jQuery("[id^=" + aSelector + "]").find("a[data-role='" + kradVariables.DATA_ROLES.DISCLOSURE_LINK + "']").each(function () {
        var contentId = jQuery(this).attr("data-linkfor");
        if (expandText == 'Expand All'){
            if (jQuery("#" + contentId).attr(kradVariables.ATTRIBUTES.DATA_OPEN) == "false") {
                jQuery(this).click();
            }
        } else {
            if (jQuery("#" + contentId).attr(kradVariables.ATTRIBUTES.DATA_OPEN) == "true") {
                jQuery(this).click();
            }
        }
    });

    if (expandText == 'Expand All'){
        jQuery("#" + expandAction).text('Collapse All');
    } else {
        jQuery("#" + expandAction).text('Expand All');
    }

}

/**
 * This method sets the expandAction expand/collapse link
 *
 * @param event AO cluster disclosure event
 * @param expandAction expand/collapse link id of a particular CO section
 * @param headerId AO cluster header Id
 */
function clusterDisclosureClicked(event, expandAction, headerId) {

    var expandText = jQuery("#" + expandAction).text().trim();
    var clickedDisclosureId = jQuery(event).attr('currentTarget').id;

    var aSelector = headerId  + "_line";
    var length = jQuery("[id^=" + aSelector + "]").find("a[data-role='" + kradVariables.DATA_ROLES.DISCLOSURE_LINK + "']").length;
    var allExpanded = 0;

    for(var i = 0; i<length; i++) {
        var disclosureId = jQuery("[id^=" + aSelector + "]").find("a[data-role='" + kradVariables.DATA_ROLES.DISCLOSURE_LINK + "']")[i].id;

        var contentId = jQuery(jQuery("[id^=" + aSelector + "]").find("a[data-role='" + kradVariables.DATA_ROLES.DISCLOSURE_LINK + "']")[i]).attr("data-linkfor");
        if (jQuery("#" + contentId).attr(kradVariables.ATTRIBUTES.DATA_OPEN) == "true") {
            allExpanded += 1;
            if(clickedDisclosureId == disclosureId)  {
                allExpanded -= 1;
            }
        } else {
            if(clickedDisclosureId == disclosureId)  {
                allExpanded += 1;
            }
        }
    }

    if(length == allExpanded) {
        jQuery("#" + expandAction).text('Collapse All');
    } else {
        jQuery("#" + expandAction).text('Expand All');
    }
}