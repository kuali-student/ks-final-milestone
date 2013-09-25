var dirtyFieldsRefreshed = false;

function removeSelfFromDropdowns(headerTextNameContainerId) {
    jQuery('select[name=clusterIdForAOMove]').each(function () {
        var dropdownId = jQuery(this).attr('id');
        var lineIndex = dropdownId.indexOf('_line');
        var controlIndex = dropdownId.indexOf('_control');
        var postFix = '';
        if (lineIndex > -1) {
            if (controlIndex > -1) {
                postfix = dropdownId.substring(lineIndex, controlIndex);
            } else {
                postfix = dropdownId.substring(lineIndex);
            }
        }
        var headerContainer = headerTextNameContainerId + postfix;
        var headerText = jQuery("#" + headerContainer).find("label").text();
        var braketIndex = headerText.indexOf('(');
        if (braketIndex > -1) {
            headerText = headerText.substring(0, braketIndex);
        }

        jQuery("#" + dropdownId + " > option").each(function (i) {
            var optionText = jQuery.trim(jQuery.trim(jQuery(this).text()));
            headerText = jQuery.trim(headerText);
            if (headerText == optionText) {
                jQuery(this).remove();
            }
        });
    });
    toggleAssignButton();
}

function addDropdownGroup(dropdownId, prepend, groupText, optionList, withinPortal) {
    if (withinPortal) {
        return;
    }
    var dropdown = jQuery("#" + dropdownId);
    if (dropdown != 'undefined') {
        if (optionList != 'undefined') {
            if (prepend) {
                dropdown.prepend('<optgroup label="' + groupText + '"></optgroup>');
            } else {
                dropdown.append('<optgroup label="' + groupText + '"></optgroup>');
            }
            jQuery.each(optionList, function (key, value) {
                addOption(dropdown, prepend, key, value);
            });
        }
    }
}

function addOption(dropdown, prepend, key, value) {
    if (prepend) {
        dropdown.prepend(jQuery('<option>', {key:value}).text(value));
    } else {
        dropdown.append(jQuery('<option>', {value:key}).text(value));
    }
}

function populateLightboxForm(propertyContainerId, defaultPropertyValues) {
//    var lightboxForm = jQuery("#" + "kualiLightboxForm");
    jQuery.each(defaultPropertyValues, function (lightboxPropId, kualiPropId) {
        var lightboxInput = jQuery("#kualiLightboxForm :input[name='" + lightboxPropId + "']");
        var kualiProp = jQuery("#" + kualiPropId);
        lightboxInput.val(kualiProp.text());
    });
}

function toggleAssignButton() {
    var table = jQuery("#KS-ManageRegistrationGroups-UnassignedActivityOfferingsPerFormatSection").find("table");
    var checkedCheckboxesCount = jQuery(table).find('input:checkbox:checked').length;
    if (checkedCheckboxesCount > 0) {
        jQuery("#move_ao_button").removeAttr("disabled");
    } else {
        jQuery("#move_ao_button").attr("disabled", "disabled");
    }
}

function toggleAddAOCButton(buttonId, controlId) {
    if ((buttonId=="moveAOCButton" && jQuery("#clusterIDListForAOMove_control").val()== "createNewCluster")
        || buttonId=="addAOCButton" || buttonId=="renameAOCButton") {
        if (jQuery("#"+controlId).val() == "" ) {
            jQuery("#"+buttonId).attr("disabled", "disabled");
        } else {
            jQuery("#"+buttonId).removeAttr("disabled");
        }
    } else {
        jQuery("#"+buttonId).removeAttr("disabled");
    }
}

function renameDialogButtons(labelsToReplace) {
    var checkboxes = jQuery("#kualiLightboxForm :input[name='dialogResponse']");
    jQuery.each(labelsToReplace, function (key, newLabelValue) {
        jQuery(checkboxes).each(function () {
            if (jQuery(this).val() == key) {
                var labelForId = jQuery(this).attr("id");
                var label = jQuery("label[for='" + labelForId + "']");
                jQuery(label).text(newLabelValue);
            }
        });
    });
}

function removeCheckboxColumns(column, componentId, functionToCall) {
    var components = jQuery('div[id^="' + componentId + '"]');
    jQuery.each(components, function (index) {
        var subComponentId = jQuery(this).attr('id');
        var table = jQuery(this).find('table');
        var tableId = jQuery(table).attr('id');

        var foundCheckBox = false;

        jQuery('#' + tableId + ' tbody > tr > td:nth-child(' + column + ')').find('[type=checkbox]').each(function () {
            var div = jQuery(this).parent('div');
            if (jQuery(div).is(":visible")) {
                foundCheckBox = true;
                return foundCheckBox;
            }
        });

        if (!foundCheckBox) {
            var th = jQuery('#' + tableId + ' thead tr').find('th:nth-child(' + column + ')');
            jQuery(th).remove();
            jQuery('#' + tableId + ' tbody tr').find('td:nth-child(' + column + ')').each(function () {
                jQuery(this).remove();
            });
            var tf = jQuery('#' + tableId + ' tfoot tr').find('th:nth-child(' + column + ')');
            jQuery(tf).remove();
        } else {
            var toggleCheckbox = jQuery("<input type='checkbox' id='" + tableId + "_toggle_control_checkbox'/>");
            var isChecked = toggleCheckbox.prop('checked');
            toggleCheckbox.click(function (e) {
                jQuery('#' + tableId + ' tbody > tr > td:nth-child(' + column + ')').find('[type=checkbox]').each(function () {
                    jQuery(this).prop('checked', jQuery(toggleCheckbox).prop('checked'));
                });
                if (functionToCall) {
                    var target = jQuery.makeArray(functionToCall);
                    var clickFn = new Function(functionToCall)
                    clickFn.call(target);
                }
            });
            var th = jQuery('#' + tableId + ' thead tr').find('th:nth-child(' + column + ')');
            jQuery(th).append(toggleCheckbox);
        }

    });
}

function addActionColumn(isReadOnly, componentId) {
    if (isReadOnly) {
        // Find the table
        var div = jQuery('#' + componentId);
        var table = jQuery(div).find('table');
        var tableId = jQuery(table).attr('id');

        // Create the header
        var thHeader = jQuery('<th scope="col" colspan="1" rowspan="1" class="infoline sorting_disabled" role="columnheader" aria-label="ACTIONS"/>');
        var spanHeader = jQuery('<span id="action_manage_header_span" class="infoline"/>');
        var labelHeader = jQuery('<label id="action_manage_header_label" for="">ACTIONS</label>');
        spanHeader.append(labelHeader);
        thHeader.append(spanHeader);

        // Add the Header
        jQuery('#' + tableId + ' thead tr').append(thHeader);

        jQuery('#' + tableId + ' tbody tr').each(function () {

            var firstColumnDiv = jQuery(this).find('td').find('div');
            var fristColumnId = jQuery(firstColumnDiv).attr('id');
            var index = fristColumnId.substring(fristColumnId.lastIndexOf('_line') + '_line'.length, fristColumnId.length);

            // Create the body
            var tdBody = jQuery('<td role="presentation" colspan="1" rowspan="1" class="uif-field uif-fieldGroup uif-horizontalFieldGroup" style="text-align: left;"/>');
            var bodyDiv1 = jQuery('<div id="bodyDiv1_line' + index + '" class="uif-field uif-fieldGroup uif-horizontalFieldGroup" style="text-align: left;" data-parent="' + componentId + '" data-label="ACTIONS" data-group="bodyDiv2_line' + index + '"/>');
            tdBody.append(bodyDiv1);
            var bodyFieldset = jQuery('<fieldset aria-labelledby="bodyDiv1_line' + index + '_label" id="bodyDiv1_line' + index + '_fieldset"/>');
            bodyDiv1.append(bodyFieldset);
            var bodyLegend = jQuery('<legend style="display: none">ACTIONS</legend>');
            bodyFieldset.append(bodyLegend);
            var bodyDiv2 = jQuery('<div id="bodyDiv2_line' + index + '" class="uif-group uif-boxGroup uif-horizontalBoxGroup" style="text-align: left;" data-parent="bodyDiv1_line' + index + '"/>');
            bodyFieldset.append(bodyDiv2);
            var bodyDiv3 = jQuery('<div id="bodyDiv3_line' + index + '" class="uif-validationMessages uif-groupValidationMessages"style="display: none;" data-messagesfor="bodyDiv2_line' + index + '"/>');
            bodyDiv2.append(bodyDiv3);
            var bodyDiv4 = jQuery('<div id="bodyDiv4_line' + index + '_boxLayout" class="uif-boxLayout uif-horizontalBoxLayout clearfix"/>');
            bodyDiv2.append(bodyDiv4);
            var bodyAnchor = jQuery('<a id="bodyAnchor_line' + index + '" onclick="return false;" class="uif-action uif-actionLink uif-navigationActionLink" tabindex="0" data-ajaxreturntype="update-component" data-loadingmessage="Loading..." data-disableblocking="false" data-ajaxsubmit="false" data-refreshid="' + componentId + '" data-validate="false">Manage</a>');
            bodyDiv4.append(bodyAnchor);

            jQuery(this).append(tdBody);

            jQuery('#bodyAnchor_line' + index).data('submitData', {
                "methodToCall":"loadAOs_RGs_AOCs",
                "actionParameters[selectedCollectionPath]":"courseOfferingResultList",
                "actionParameters[selectedLineIndex]":index,
                "showHistory":"false",
                "showHome":"false",
                "jumpToId":componentId
            });

            jQuery('#' + 'bodyAnchor_line' + index).click(function (e) {
                e.preventDefault();
                if (jQuery(this).hasClass('disabled')) {
                    return false;
                }
                if (checkDirty(e) == false) {
                    actionInvokeHandler(this);
                }
            });

            jQuery('#bodyDiv2_line' + index).data('validationMessages', {
                summarize:true,
                displayMessages:true,
                collapseFieldMessages:true,
                displayLabel:true,
                hasOwnMessages:false,
                pageLevel:false,
                forceShow:true,
                sections:[],
                order:[],
                serverErrors:[],
                serverWarnings:[],
                serverInfo:[]
            });

        });
        // Create the footer
        var thFooter = jQuery('<th rowspan="1" colspan="1"/>');

        // Add the Footer
        jQuery('#' + tableId + ' tfoot tr').append(thFooter);
    }
}

/**
 *  Fix for KSENROLL 4390 and 4675. Adds a null check for sectionTitle that is missing from krad.validate.js, similar to above method.
 */

function generateFieldLinkSublist(parentSectionData, currentFields, messageMap, sectionId, before) {

    var sectionTitle = jQuery("[data-headerFor='" + sectionId + "']").find("> :header .uif-headerText-span, "
        + "> label .uif-headerText-span").text();
    var sectionType = "section";
    if (sectionTitle == null || sectionTitle == "") {
        //field group case
        sectionTitle = jQuery("#" + sectionId).data("label");
        sectionType = "field group";
    }

    var disclosureText = "";
    var links = [];
    var errorCount = 0;
    var warningCount = 0;
    var infoCount = 0;
    var disclosureLink = null;
    var image = "";
    var linkType = "";

    for (var i in currentFields) {
        if (currentFields[i] != null) {

            var fieldId = currentFields[i];

            var messageData = messageMap[fieldId];
            if (messageData != undefined && messageData != null) {
                errorCount = errorCount + messageData.serverErrors.length + messageData.errors.length;
                warningCount = warningCount + messageData.serverWarnings.length + messageData.warnings.length;
                infoCount = infoCount + messageData.serverInfo.length + messageData.info.length;

                var link = generateFieldLink(messageData, fieldId, parentSectionData.collapseFieldMessages,
                    parentSectionData.displayLabel);
                if (link != null) {
                    links.push(link);
                }
            }
        }
    }
    if (errorCount || warningCount || infoCount) {
        var locationText = getMessage(kradVariables.MESSAGE_BEFORE);
        if (!before) {
            locationText = getMessage(kradVariables.MESSAGE_AFTER);
        }

        if (errorCount) {
            image = errorImage;
            linkType = "uif-errorMessageItem";
        }
        else if (warningCount) {
            image = warningImage;
            linkType = "uif-warningMessageItem";
        }
        else if (infoCount) {
            image = infoImage;
            linkType = "uif-infoMessageItem";
        }

        var countMessage = generateCountString(errorCount, warningCount, infoCount);

        if(sectionTitle){
            sectionTitle.replace(/\r?\n/g, "");
        } else {
            sectionTitle="";
        }
        disclosureText = countMessage + " " + locationText + " " + getMessage(kradVariables.MESSAGE_THE) + " \"" + sectionTitle + "\" " + sectionType;

        if (links.length) {
            var subSummary = jQuery("<ul></ul>");
            for (var i in links) {
                jQuery(links[i]).appendTo(subSummary)
            }
            //jQuery(subSummary).hide();
        }

        //write disclosure link and div
        disclosureLink = jQuery("<li tabindex='0' class='" + linkType + "'>" + disclosureText + "</li>");
        jQuery(subSummary).appendTo(disclosureLink);
    }
    return disclosureLink;
}

function ajaxShow(url) {
    //var formData = jQuery('#kualiForm').serialize();
    var viewId = jQuery.trim(jQuery('#viewId').val());
    var formKey = jQuery.trim(jQuery('#formKey').val());
    var validateDirty = jQuery.trim(jQuery('#validateDirty').val());
    var renderedInLightBox = jQuery.trim(jQuery('#renderedInLightBox').val());
    var termCodeId_control = jQuery.trim(jQuery('#termCodeId_control').val());
    var inputCodeId_control = jQuery.trim(jQuery('#inputCodeId_control').val());
    var pageId = jQuery.trim(jQuery('#pageId').val());
    var pageTitle = jQuery.trim(jQuery('#pageTitle').val());
    var historyParameterString = jQuery.trim(jQuery('#historyParameterString').val());
    var _dialogResponse = "on";
    var formData = {
        "viewId":viewId,
        "formKey":formKey,
        "view.applyDirtyCheck":validateDirty,
        "renderedInLightBox":renderedInLightBox,
        "termCode":termCodeId_control,
        "inputCode":inputCodeId_control,
        "view.currentPageId":pageId,
        "view.currentPage.header.headerText":pageTitle,
        "_dialogResponse":_dialogResponse
    };
    // console.log("theDate: " + jQuery.now());
    // console.log("formData: " + jQuery(formData).serialize());
    // console.log("historyParameterString " + historyParameterString);
    jQuery.ajax({
        url:url + "/kr-krad/courseOfferingManagement/ajaxShow.do",
        type:"GET",
        data:formData,
        // callback handler that will be called on success
        success:function (data, textStatus, jqXHR) {
            updateTable(data);
        }
    });
//  return false;

    function updateTable(data) {

        var page = jQuery('<html/>').html(data);
        var div = jQuery('#Uif-PageContentWrapper');
        var tableContainer = jQuery('#tableContainer');
        if(tableContainer.length == 0){
            tableContainer = jQuery("<div id='tableContainer' />");
            div.append(tableContainer);
        }
        tableContainer.html(page);
    }
}


function jsonShow(url) {
    //var formData = jQuery('#kualiForm').serialize();
    var viewId = jQuery.trim(jQuery('#viewId').val());
    var formKey = jQuery.trim(jQuery('#formKey').val());
    var validateDirty = jQuery.trim(jQuery('#validateDirty').val());
    var renderedInLightBox = jQuery.trim(jQuery('#renderedInLightBox').val());
    var termCodeId_control = jQuery.trim(jQuery('#termCodeId_control').val());
    var inputCodeId_control = jQuery.trim(jQuery('#inputCodeId_control').val());
    var pageId = jQuery.trim(jQuery('#pageId').val());
    var pageTitle = jQuery.trim(jQuery('#pageTitle').val());
    var historyParameterString = jQuery.trim(jQuery('#historyParameterString').val());
    var _dialogResponse = "on";
    var formData = {
        "viewId":viewId,
        "formKey":formKey,
        "view.applyDirtyCheck":validateDirty,
        "renderedInLightBox":renderedInLightBox,
        "termCode":termCodeId_control,
        "inputCode":inputCodeId_control,
        "view.currentPageId":pageId,
        "view.currentPage.header.headerText":pageTitle,
        "_dialogResponse":_dialogResponse
    };
    jQuery.ajax({
        dataType:"json",
        url:url + "/kr-krad/courseOfferingManagement/jsonShow.do",
        type:"GET",
        data:formData,
        success:function (data, textStatus, jqXHR) {
            updateTable(data);
        }
    });

    return false;

    function updateTable(data) {

        var codeContainerIds = [];

        var table = jQuery("<table class='uif-tableCollectionLayout dataTable'/>");
        var tHead = jQuery("<thead/>").append("<tr/>")
            .append("<th scope='col' colspan='1' rowspan='1' class='sorting' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-label=': activate to sort column ascending'></th>")
            .append("<th scope='col' colspan='1' rowspan='1' class='sorting_asc' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-sort='ascending' aria-label=': activate to sort column descending'></th>")
            .append("<th scope='col' colspan='1' rowspan='1' class='sorting' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-label=': Status'>Status</th>")
            .append("<th scope='col' colspan='1' rowspan='1' class='sorting' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-label=': Title'>Title</th>")
            .append("<th scope='col' colspan='1' rowspan='1' class='sorting' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-label=': Credits'>Credits</th>")
            .append("<th scope='col' colspan='1' rowspan='1' class='sorting' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-label=': Grading'>Grading</th>");
//            .append("<th scope='col' colspan='1' rowspan='1' class='sorting' role='columnheader' tabindex='0' aria-controls='tableContainer' aria-label=': Actions'>Actions</th>");
        table.append(tHead);
        var tBody = jQuery("<tbody/>");
        var tableData = data.tableData;
        jQuery.each(tableData, function (rowNumber, columns) {
            var row = jQuery("<tr/>");
            var checkboxColumn = jQuery("<td/>");
            var checkbox = jQuery("<input type='checkbox' />");
            checkboxColumn.append(checkbox);
            row.append(checkboxColumn);

            var codeAnchor = jQuery("<a id='code_line" + rowNumber + "'"
                + "href='" + url + "/kr-krad/inquiry?courseOfferingId=" + columns.courseOfferingId +"&amp;methodToCall=start&amp;dataObjectClassName=org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper&amp;renderedInLightBox=true&amp;showHome=false&amp;showHistory=false&amp;history=" + historyParameterString
                + "' target='_self' class='uif-link' title='Course Offering =courseOfferingId'>" + columns.courseOfferingCode + "</a>");

//            <input type="hidden" data-role="script" value="
//                createLightBoxLink('u240_line83', {fitToView:true,height:'95%',width:'75%',autoSize:false,openEffect:'fade',closeEffect:'fade',openSpeed:200,closeSpeed:200,helpers:{overlay:{css:{cursor:'arrow'},closeClick:false}},type:'iframe'}, true);
//                " script="first_run">

            var id = "code_line" + rowNumber;
            codeContainerIds.push(id);

            var code = jQuery("<td role='presentation' colspan='1' rowspan='1' class='  sorting_1'/>");
            code.append(codeAnchor);
            row.append(code);

            var status = jQuery("<td>" + columns.courseOfferingStateDisplay + "</td>");
            row.append(status);

            var title = jQuery("<td>" + columns.courseOfferingDesc + "</td>");
            row.append(title);

            var credits = jQuery("<td>" + columns.courseOfferingCreditOptionDisplay + "</td>");
            row.append(credits);

            var grading = jQuery("<td>" + columns.courseOfferingGradingOptionDisplay + "</td>");
            row.append(grading);

            tBody.append(row);
        });
        table.append(tBody);
        var tFoot = jQuery("<tfoot/>").append("<tr/>")
            .append("<th rowspan='1' colspan='1' />")
            .append("<th rowspan='1' colspan='1' />")
            .append("<th rowspan='1' colspan='1' />")
            .append("<th rowspan='1' colspan='1' />")
            .append("<th rowspan='1' colspan='1' />")
            .append("<th rowspan='1' colspan='1' />");
//            .append("<th rowspan='1' colspan='1' />");
        table.append(tFoot);
        var div = jQuery('#Uif-PageContentWrapper');
        var tableContainer = jQuery('#tableContainer');
        if(tableContainer.length == 0){
            tableContainer = jQuery("<div id='tableContainer' />");
            div.append(tableContainer);
        }
        tableContainer.html(table);

        jQuery.each(codeContainerIds, function(index){
            createLightBoxLink(codeContainerIds[index] , {fitToView:true,height:'95%',width:'75%',autoSize:false,openEffect:'fade',closeEffect:'fade',openSpeed:200,closeSpeed:200,helpers:{overlay:{css:{cursor:'arrow'},closeClick:false}},type:'iframe'}, true);
        });
    }
}


function dataTableShow(url, containerId, tableId) {
    var termCodeId_control = jQuery.trim(jQuery('#termCodeId_control').val());
    var inputCodeId_control = jQuery.trim(jQuery('#inputCodeId_control').val());
    var viewId = jQuery.trim(jQuery('#viewId').val());
    var formKey = jQuery.trim(jQuery('#formKey').val());
    var source = url +  "/kr-krad/courseOfferingManagement/dataTableShow.do?viewId=" + viewId + "&formKey=" + formKey + "&termCode=" + termCodeId_control + "&inputCode=" + inputCodeId_control;

    var tableContainer = jQuery('#' + containerId);
    var theTable = jQuery('#' + tableId);
    if(theTable.length == 0){
        theTable = jQuery("<table id='" + tableId + "' />");
        tableContainer.append(theTable);
    }else{
        jQuery('#' + tableId).dataTable().fnDestroy();
    }

    jQuery('#' + tableId).dataTable({
        //  bServerSide: true,
        bPaginate: false,
        bFilter: false,
        asSorting: [ 2, 'asc' ],
        aoColumns: [
            {'sTitle':'', 'bSortable':false, 'bSearchable':false},
            {'sTitle':'', 'bSortable':true, 'bSearchable':true},
            {'sTitle':'STATUS', 'bSortable':true, 'bSearchable':true},
            {'sTitle':'TITLE', 'bSortable':true, 'bSearchable':true},
            {'sTitle':'CREDITS', 'bSortable':true, 'bSearchable':false},
            {'sTitle':'GRADING', 'bSortable':true, 'bSearchable':false}
        ],
        fnInitComplete:function(oSettings, json){
            jQuery('#' + tableId).find('a').each(function(index){
                var coId = jQuery(this).attr("id");
                createLightBoxLink(coId , {fitToView:true,height:'95%',width:'75%',autoSize:false,openEffect:'fade',closeEffect:'fade',openSpeed:200,closeSpeed:200,helpers:{overlay:{css:{cursor:'arrow'},closeClick:false}},type:'iframe'}, true);
            });
        },
        bRetrieve: true,
        sAjaxSource: source
    });
}

function addTabs(aoTabName , regTabName, aoDivIdPrefix, regDivIdPrefix){
    var aoDiv = jQuery('div[id^="' + aoDivIdPrefix + '"]');

    jQuery.each(aoDiv, function(index){
        var ul = jQuery("<ul class='ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all' />");
        var aoLi = jQuery("<li class='ui-state-default ui-corner-top ui-tabs-selected ui-state-active ui-state-hover'/>");
        var aoAnchor = jQuery("<a href='#'>" + aoTabName + "</a>");
        //ui-state-default ui-corner-top ui-tabs-selected ui-state-active ui-state-hover
//        ui-state-default ui-corner-top
        aoLi.html(aoAnchor);

        var rgLi = jQuery("<li class='ui-state-default ui-corner-top'/>");
        var rgAnchor = jQuery("<a href='#'>" + regTabName + "</a>");
        rgLi.html(rgAnchor);
        //jQuery(rgLi).text(regTabName);

        //jQuery(aoLi).text(aoTabName);
        var aoDivId = jQuery(this).attr('id');
        var index = aoDivId.split(aoDivIdPrefix)[1];
        var regDivId = regDivIdPrefix + index;
        aoLi.click(function () {
            jQuery(aoLi).addClass("ui-tabs-selected ui-state-active ui-state-hover");
            jQuery(rgLi).removeClass("ui-tabs-selected ui-state-active ui-state-hover");
            jQuery("#" + aoDivId).find("table").show();
            jQuery("#" + regDivId).hide();
        });
        ul.append(aoLi);
        rgLi.click(function () {
            jQuery(rgLi).addClass("ui-tabs-selected ui-state-active ui-state-hover");
            jQuery(aoLi).removeClass("ui-tabs-selected ui-state-active ui-state-hover");
            jQuery("#" + regDivId).show();
            jQuery("#" + aoDivId).find("table").hide();
        });
        ul.append(rgLi);
        jQuery(this).prepend(ul);

    });
}

function closeTooltip(e) {
    var popupTarget;
    if (typeof e === "string") {
        popupTarget = jQuery("#" + e);
    } else {  // source is a trigger event
        stopEvent(e);
        popupTarget = jQuery((e.currentTarget) ? e.currentTarget : e.srcElement);
    }

    // save open property before closing popups
    var isPopupOpen = popupTarget.IsBubblePopupOpen();
    if (isPopupOpen) {
        var b = jQuery(popupTarget).data("private_jquerybubblepopup_options");
        if (b != 'undefined') {
//            var bubbleId = jQuery(b).attr('privateVars').id = null;
            var isOpen = jQuery(b).attr('privateVars').is_open = null;
        }
    }
}

function addNewClusterOptionSuccessCallBack(){
    retrieveComponent('KS-CourseOfferingManagement-MoveAOCPopupForm',undefined, addNewClusterOption, undefined);
}

/* This function contains some temporary fixes for jira 6319 */
function addNewClusterOption(responseContents) {
    jQuery('#foNameForAOMoveId').hide();
    jQuery('#privateNameForMoveId').hide();
    jQuery('#publishedNameForMoveId').hide();

    var dropDown = jQuery('#clusterIDListForAOMove_control');
    var container = jQuery('#KS-CourseOfferingManagement-AOClustersCollection');
    var checkedCBs = jQuery(container).find(":checkbox:checked[name$=isCheckedByCluster]");
    if (checkedCBs.length == 0) {
        var createNewClusterOption = new Option("No Activity has been selected", "");
        jQuery('#clusterIDListForAOMove_control option').remove();
        jQuery(dropDown).append(createNewClusterOption);
        jQuery('#KS-CourseOfferingManagement-MoveAOCPopupForm').find(':contains("Move")').closest('button').attr("disabled", "disabled");
    } else {
        if (jQuery(dropDown).find('option[value=""]').length == 0) {
            if (jQuery(dropDown).find('option[value=createNewCluster]').length == 0) {
                var createNewClusterOption = new Option("Create new Cluster...", "createNewCluster");
                jQuery(dropDown).append(createNewClusterOption).change(function () {
                    if (jQuery(dropDown).val() == "createNewCluster") {
                        jQuery('#foNameForAOMoveId').show();
                        jQuery('#privateNameForMoveId').show();
                        jQuery('#publishedNameForMoveId').show();
                    } else {
                        jQuery('#foNameForAOMoveId').hide();
                        jQuery('#privateNameForMoveId').hide();
                        jQuery('#publishedNameForMoveId').hide();
                    }
                });
            }
        }
    }
}

function removeZebraColoring(id){
    var aoDivs = jQuery('div[id^="' + id + '"]');

    jQuery.each(aoDivs, function(index){
        var rows = jQuery(this).find('table tbody tr');
        jQuery.each(rows, function(index){
            jQuery(this).removeClass('odd');
            jQuery(this).removeClass('even');
        });
    });

}

function createErrorDiv(message, url, controlId) {
    var div = jQuery('<div id="' + controlId + '_messageDiv" class="uif-clientMessageItems uif-clientErrorDiv" style="display: none;"/>');
    var ul = jQuery("<ul/>");
    var li = jQuery("<li class='uif-errorMessageItem-field'/>");
    var image = jQuery("<img class='uif-validationImage' src='" + url + "/krad/images/validation/error.png' alt='Error'/>");
    jQuery(image).text(message);
    jQuery(li).append(image);
    jQuery(ul).append(li);
    jQuery(div).append(ul);

    return div;
}

function createErrorTable(url) {
    var table = jQuery("#errorTable");
    if (jQuery(table).length) {
        return table;
    }
    table = jQuery('<table id="errorTable" style="display: none; position: absolute;"/>');
    var tbody = jQuery('<tbody/>');
    jQuery(table).append(tbody);
    var tr1 = jQuery('<tr/>');
    jQuery(tr1).append('<td class="jquerybubblepopup-top-left" style="background-image:url(../krad/plugins/tooltip/jquerybubblepopup-theme/kr-error-cs/top-left.png); padding : 0px;"/>');
    jQuery(tr1).append('<td class="jquerybubblepopup-top-middle" style="background-image:url(../krad/plugins/tooltip/jquerybubblepopup-theme/kr-error-cs/top-middle.png); padding : 0px;"/>');
    jQuery(tr1).append('<td class="jquerybubblepopup-top-right" style="background-image:url(../krad/plugins/tooltip/jquerybubblepopup-theme/kr-error-cs/top-right.png); padding : 0px;"/>');
    var tr2 = jQuery('<tr/>');
    jQuery(tr2).append('<td class="jquerybubblepopup-middle-left" style="background-image:url(../krad/plugins/tooltip/jquerybubblepopup-theme/kr-error-cs/middle-left.png); padding : 0px;"/>');
    var td2 = jQuery('<td class="jquerybubblepopup-innerHtml" style="padding : 0px;" />');
    jQuery(tr2).append(td2);
    var div2 = jQuery('<div id="_messageDiv" class="uif-clientMessageItems uif-clientErrorDiv"/>');
    jQuery(td2).append(div2);
    jQuery(tr2).append('<td class="jquerybubblepopup-middle-right" style="background-image:url(../krad/plugins/tooltip/jquerybubblepopup-theme/kr-error-cs/middle-right.png); padding : 0px;"/>');
    var tr3 = jQuery('<tr/>');
    jQuery(tr3).append('<td class="jquerybubblepopup-bottom-left" style="background-image:url(../krad/plugins/tooltip/jquerybubblepopup-theme/kr-error-cs/bottom-left.png); padding : 0px;"/>');
    var td3 = jQuery('<td class="jquerybubblepopup-bottom-middle" style="background-image: url(http://localhost:8081/ks-with-rice-bundled-dev/krad/plugins/tooltip/jquerybubblepopup-theme/kr-error-cs/bottom-middle.png); text-align: left; padding : 0px; "/>');
    var image3 = jQuery('<img src="../krad/plugins/tooltip/jquerybubblepopup-theme/kr-error-cs/tail-bottom.png" alt="" class="jquerybubblepopup-tail"/>');
    jQuery(td3).append(image3);
    jQuery(tr3).append(td3);
    jQuery(tr3).append('<td class="jquerybubblepopup-bottom-right" style="background-image:url(../krad/plugins/tooltip/jquerybubblepopup-theme/kr-error-cs/bottom-right.png); padding : 0px;"/>');
    jQuery(tbody).append(tr1);
    jQuery(tbody).append(tr2);
    jQuery(tbody).append(tr3);
    jQuery(document.body).append(table);
    return table;
}


function highlightElements(validationJSONString, isValid, url) {
    if (!isValid) {
        var table = createErrorTable(url);
        jQuery.each(validationJSONString, function (id, message) {
            var errorContainer = jQuery('[id^="' + id + '"][id$="_errors"]:first');
            var controlDiv = jQuery('[id^="' + id + '"][id$="_control"]:first');
            var parentDiv = jQuery(controlDiv).parent().closest('div');
            var id = jQuery(parentDiv).attr("id");
            var errorDiv = createErrorDiv(message, url, id);
            console.log(jQuery(errorDiv).text());
            //var table = createErrorTable(url);

            jQuery(parentDiv).append(errorDiv);
            jQuery(controlDiv).addClass("ks-uif-hasError");
            jQuery(controlDiv).hover(
                function (e) {
                    var moveLeft = -20;
                    var moveDown = -60;
                    //var table = createErrorTable(url);
                    var id = jQuery(this).parent().closest('div').attr("id");
                    var thisErrorDiv = jQuery('#' + id + '_messageDiv');
                    console.log(jQuery(thisErrorDiv).html());
                    console.log(jQuery(thisErrorDiv).text());
                    var div = jQuery(table).find('[id$="_messageDiv"]');
                    jQuery(table).find('[id$="_messageDiv"]').html(jQuery(thisErrorDiv).html());
                    jQuery(table).find('[id$="_messageDiv"]').text(jQuery(thisErrorDiv).text());
                    console.log(jQuery(jQuery(table).find('[id$="_messageDiv"]')).text());
                    jQuery('table#errorTable').show().css('top', jQuery(this).offset().top + moveDown).css('left', jQuery(this).offset().left + moveLeft);
                },
                function (e) {
                    jQuery('table#errorTable').hide();
                }
            );
        });
    }
}

/*
 Because the context bar resides in the topGroup, it only gets loaded once when the view is loaded. Because the context
 bar needs to update every time the page loads, we have to create some custom JS to update items in the context bar.

 Note: passing null for the 1st-argument (or if an element for 1st-argument is not found) will result in element targeted
 by 2nd-argument being emptied without getting a replacement.
 */
function updateContextBar(srcId, contextBarId){

    jQuery( "#" + contextBarId).empty(); // remove any child elements so we can replace them later

    if( contextBarId == null ) return;

    var contextBar = jQuery("#" + contextBarId);    // grab the placeholder
    if( contextBar ) {
        if (contextBar.css('display') == "none") {
            contextBar.show();
        }
        var src = jQuery("#" + srcId);                  // grab the new context bar
        jQuery(contextBar).append(jQuery(src));         // add it to the context bar placeholder
        jQuery(src).show();
    }
}

/*
 This method is for removing the empty context bar from a page.
 The position of the page header is repositioned accordingly
 */
function removeEmptyContextBar(contextBarId){
    if ((contextBarId==null) || (contextBarId.length==0)) return;

    var contextBar = jQuery("#" + contextBarId);

    if (contextBar && contextBar.css('display') != "none") {
        //hide the empty context bar
        contextBar.hide();

        //re-position header.
        var headerDiv = jQuery(".uif-viewHeader-contentWrapper");
        if (headerDiv) {
            var headerOffsetTop = headerDiv.offset().top - contextBar.height();
            headerDiv.offset({top:headerOffsetTop});
            headerDiv.data("offset", headerDiv.offset());
        }
    }
}

/*
 This method is for putting the page header back to the original position
 */
function resetHeaderPosition(contextBarId){
    if ((contextBarId==null) || (contextBarId.length==0)) return;

    var contextBar = jQuery("#" + contextBarId);
    if (contextBar && contextBar.css('display') != "none") {

        var headerDiv = jQuery(".uif-viewHeader-contentWrapper");
        var headerOffsetTop = parseInt(headerDiv.offset().top);
        var stickyContentOffsetTop = parseInt(stickyContentOffset.top);

        //if headerOffsetTop equals to stickyContentOffsetTop, it means the header position has
        //been adjusted back and no need to adjust it again
        if (headerDiv && (headerOffsetTop != stickyContentOffsetTop)) {
            var headerOffsetTop = headerDiv.offset().top + contextBar.height();

            //adjust header back to the original position
            headerDiv.offset({top:headerOffsetTop});
            headerDiv.data("offset", headerDiv.offset());
        }
    }
}

function updateHeaderRightGroup(srcId, rightGroupId){

    if( rightGroupId == null ) return;

    var rightGroup = jQuery("#" + rightGroupId);    // grab the placeholder
    if( rightGroup ) {
        var src = jQuery("#" + srcId);                  // grab the new header right group
        jQuery(rightGroup).html(jQuery(src).html());         // copy the content of the right group to the place holder
    }
}

// note: this seems to only apply to views that use the unified-header; maybe this should be named more explicitly?
function updateViewHeader(newHeaderTextSource){
    // Get Header node
    var header = jQuery("div.ks-unified-header h1.uif-headerText span.uif-headerText-span");
    var source = jQuery("#"+newHeaderTextSource+"_span");
    var newHeaderText = source.html();
    // Update header text
    header.html(newHeaderText);
}

/*
 The users wanted to have a small strip of color that coincides with the term. If the user hasn't configured
 an explicit color then use the default coloring from a gradient.

 In this implementation we pass in the day of the year that you want to show a color for.
 Ie. Jan 1 = 1st day of year. The code does 1/365 = some %. that % is used to find how far
 down the gradient we pick a color.
 The default gradient goes : ice blue(winter), green(spring),yellow(summer), brown(fall), ice blue(winter).
 two winters because winter starts at the end of the year and goes through February.
 */
function setSeasonalColor(objectToColor, dayOfYear, baseUrl) {
    var image = jQuery('<img src="' + baseUrl + '/ks-enroll/images/season_gradient.png"/>');
    var objToColor = jQuery('#' + objectToColor);
    var percentage = dayOfYear / 365;

    image.load(function (){
        var canvas = document.createElement('canvas');
        var tw = this.width;
        var th = this.height;
        canvas.width = this.width;
        canvas.height = this.height;
        canvas.getContext('2d').drawImage(this, 0, 0, tw, th);
        var x = 10;
        var y = th * percentage;
        var p = canvas.getContext('2d').getImageData(x, y, 1, 1).data;
        var hex = "#" + ("000000" + rgbToHex(p[0], p[1], p[2])).slice(-6);

        objToColor.css("border-left", '8px solid ' + hex);
    });
}

function rgbToHex(r, g, b) {
    if (r > 255 || g > 255 || b > 255)
        throw "Invalid color component";
    return ((r << 16) | (g << 8) | b).toString(16);
}

/*
    This function changes the UifImage components to bootsrtap links
     It only converts images that have their styles starting with the word icon

     It was originally using the src attribute but according to Cody this might cause a problem
     with KRAD. I think if using the src attribute is not causing any problem for the KRAD we should
      go back to using the src attribute.
 */
function addBootstrapImageToLink(containerId) {
    jQuery("#" + containerId).find('img').each(function () {
        /*Style is used instead of src to prevent errors in krad*/
        var src = jQuery(this).attr('style');
        if (src.match("^icon-")) {
            var anchor = jQuery(this).parent();
            var aText = anchor.text();
            anchor.text("");
            var bsImage = '<i class="' + src + '"></i>' + jQuery.trim(aText);
            jQuery(anchor).append(bsImage);
        }
    });
}


/**
 * Processes the form before attempting to save.
 */
function saveAcalPreProcess(returnFieldId){
    //set the default tab to the current active tab
    var activeTabIndexId = jQuery("#acal_tabs_tabs").tabs("option", "active");
    var defaultTabToShow = jQuery("#default_tab_to_show_control");
    if (activeTabIndexId == "0") {
        defaultTabToShow.val("info");
    } else if (activeTabIndexId == "1") {
        defaultTabToShow.val("term");
    }

    //find the dirty fields
   findDirtyFields(returnFieldId);
}

/**
 * Gathers property names of all fields that have been changed on the page.
 * Properties are stored in a csv string and returned by the passed in object.
 */
function findDirtyFields(returnFieldId){
    var dirtyFields = jQuery('.dirty');
    var returnObject = jQuery('#'+returnFieldId+'_control');
    var returnString="";
    for (i=0;i<dirtyFields.length;i++){
        returnString=returnString+dirtyFields[i].name+",";

    }
    returnObject[0].value=returnString;
    dirtyFieldsRefreshed=false;
}

/*
 The users want the page titling to be dynamically-updated depending on which particular page is showing at the time.
 For example, when the ManageCO landing-page is first shown, by default the view-title is 'Course Offerings' but
 if you conduct a subject-search (say, WMST) they want the view-title to be "WMST: Women's Studies".
 */
function updateViewHeaderText( value ) {
    jQuery( 'div.uif-view h1.uif-headerText span.uif-headerText-span' ).html( value );
}

function createDisclosure(groupId, headerId, widgetId, defaultOpen, collapseImgSrc, expandImgSrc, animationSpeed, renderImage) {
    jQuery(document).ready(function () {
        var groupToggleLinkId = groupId + "_toggle";

        var expandImage = "";
        var collapseImage = "";
        if (renderImage) {
            var expandImage = "<img id='" + groupId + "_exp" + "' src='" + expandImgSrc + "' alt='" + getMessage(kradVariables.MESSAGE_EXPAND) + "' class='uif-disclosure-image'/>";
            var collapseImage = "<img id='" + groupId + "_col" + "' src='" + collapseImgSrc + "' alt='" + getMessage(kradVariables.MESSAGE_COLLAPSE) + "' class='uif-disclosure-image'/>";
        }

        var groupAccordionSpanId = groupId + "_disclosureContent";

        // perform initial open/close and insert toggle link and image
        var headerText = jQuery("#" + headerId + " > :header, #" + headerId + " > label").find(".uif-headerText-span");
        // Strip out whitespace that is causing problems for disclosures in chrome
        headerText.text(headerText.text().trim());
        if (defaultOpen) {
            jQuery("#" + groupAccordionSpanId).slideDown(000);
            headerText.prepend(expandImage);
        }
        else {
            jQuery("#" + groupAccordionSpanId).slideUp(000);
            headerText.prepend(collapseImage);
        }

        headerText.wrap("<a data-role='disclosureLink' data-linkfor='" + groupAccordionSpanId + "' href='#' "
            + "id='" + groupToggleLinkId + "'></a>");

        var animationFinishedCallback = function () {
            jQuery("#" + kradVariables.APP_ID).attr("data-skipResize", false);
        };
        var disclosureContent = jQuery("#" + groupAccordionSpanId);
        // perform slide and switch image
        if (defaultOpen) {
            disclosureContent.data("open", true);
            jQuery("#" + groupToggleLinkId).toggle(
                function () {
                    jQuery("#" + kradVariables.APP_ID).attr("data-skipResize", true);
                    disclosureContent.data("open", false);
                    disclosureContent.slideUp(animationSpeed, animationFinishedCallback);
                    jQuery("#" + groupId + "_exp").replaceWith(collapseImage);
                    setComponentState(widgetId, 'open', false);
                }, function () {
                    jQuery("#" + kradVariables.APP_ID).attr("data-skipResize", true);
                    disclosureContent.data("open", true);
                    disclosureContent.slideDown(animationSpeed, animationFinishedCallback);
                    jQuery("#" + groupId + "_col").replaceWith(expandImage);
                    setComponentState(widgetId, 'open', true);
                }
            );
        }
        else {
            disclosureContent.data("open", false);
            jQuery("#" + groupToggleLinkId).toggle(
                function () {
                    jQuery("#" + kradVariables.APP_ID).attr("data-skipResize", true);
                    disclosureContent.data("open", true);
                    disclosureContent.slideDown(animationSpeed, animationFinishedCallback);
                    jQuery("#" + groupId + "_col").replaceWith(expandImage);
                    setComponentState(widgetId, 'open', true);

                }, function () {
                    jQuery("#" + kradVariables.APP_ID).attr("data-skipResize", true);
                    disclosureContent.data("open", false);
                    disclosureContent.slideUp(animationSpeed, animationFinishedCallback);
                    jQuery("#" + groupId + "_exp").replaceWith(collapseImage);
                    setComponentState(widgetId, 'open', false);
                }
            );
        }
    });
}

/**
 * Function for a delayed removal of the highlighting of added collection items.
 *
 * @return {Function} - Function to be ran after delay.
 */
function removeNewItemHighlights(){
    return function(){
        var newItems = jQuery(".uif-newCollectionItem");
        newItems.removeClass('uif-newCollectionItem');
        newItems.addClass('highlight_fadeout');
    }

}


/**
 * KSENROLL-7852 Temp fix for KRAD bug KULRICE-9866
 * Runs client side validation on the entire form and returns the result (an alert is also given
 * if errors are encountered)
 *
 */
function validateForm() {
    clientErrorStorage = new Object();
    var summaryTextExistence = new Object();
    var validForm = true;

    jQuery.watermark.hideAll();
    pauseTooltipDisplay = true;

    if (validateClient) {
        // Turn on this flag to avoid prematurely writing out messages which will cause performance issues if MANY
        // fields have validation errors simultaneously (first we are only checking for errors, not checking and
        // writing simultaneously like normal)
        clientErrorExistsCheck = true;

        // Temporarily turn off this flag to avoid traversing unneeded logic (all messages will be shown at the end)
        messageSummariesShown = false;

        // Validate the whole form
        validForm = jq("#kualiForm").valid();

        // Handle field message bubbling manually, but do not write messages out yet
        jQuery("div[data-role='InputField']").each(function () {
            var id = jQuery(this).attr('id');
            var field = jQuery("#" + id);
            var data = field.data(kradVariables.VALIDATION_MESSAGES);
            data.errors = [];
            data.warnings = [];
            data.info = [];
            var parent = field.data("parent");
            handleMessagesAtGroup(parent, id, data, true);
        });

        // Toggle the flag back to default
        clientErrorExistsCheck = false;

        // Message summaries are going to be shown
        messageSummariesShown = true;

        // Finally, write the result of the validation messages
        writeMessagesForPage();
    }

    if (!validForm) {
        validForm = false;

        //ensure all non-visible controls are visible to the user
        jQuery(".error:not(:visible)").each(function () {
            cascadeOpen(jQuery(this));
        });

        jumpToTop();
        showClientSideErrorNotification();
        jQuery(".uif-pageValidationMessages li.uif-errorMessageItem:first > a").focus();
    }

    jq.watermark.showAll();
    pauseTooltipDisplay = false;

    return validForm;
}

/*
Replaces the checkbox inputs with radio in a table.
It does not change the name attribute and therefore each radio button
has a click event which deselects the previous selected radio button.
*/
function replaceCheckBoxWithRadio(containerId) {
    var selectedRadio;
    jQuery("#" + containerId).find("input:checkbox").each(function () {
        jQuery(this).replaceWith(
            jQuery("<input>", {
                    type:'radio',
                    id:jQuery(this).attr('id'),
                    name:jQuery(this).attr('name'),
                    value:jQuery(this).attr('value')
                }
            ).click(function(){
                    if(selectedRadio !== null){
                        jQuery(selectedRadio).prop("checked", false);
                    }
                    selectedRadio = this;
                })
        );
    });
}

/**
 * Resets all dialog responses to unchecked
 */
function resetDialogResponses(){
    var dialogResponses = jQuery('input.uif-dialogButtons');
    for(i =0; i < dialogResponses.length; i++){
        dialogResponses[i].checked=false;
    }
}

function resetDirtyFields(returnFieldId){
    if(!dirtyFieldsRefreshed){
        resetDialogResponses();
        var dirtyFields = jQuery('#'+returnFieldId+'_control');
        if(dirtyFields.length==0) return;
        if(typeof dirtyFields[0].value === 'undefined') return;
        var fields = dirtyFields[0].value.split(',');
        for(i =0; i<fields.length;i++){
            var field = fields[i];
            if(field.length==0) continue;
            var marker = jQuery('[name="'+field+'"]');
            marker.addClass('dirty');
        }
        dirtyFieldsRefreshed=true;
    }
}

/**
 * Once we get the next rice release, have to remove this override
 *
 * FIXME: Delete this method once KULRICE-9978 resolved
 * @param tableId
 * @param pageNumber
 */
function openDataTablePage(tableId, pageNumber) {
    var oTable = getDataTableHandle(tableId);
    if (oTable == null) {
        oTable = getDataTableHandle(jQuery('#' + tableId).find('.dataTable').attr('id'));
    }
    if (oTable != null) {
        if (pageNumber == "first" || pageNumber == "last") {
            oTable.fnPageChange(pageNumber);
        } else {
            var numericPage = Number(pageNumber) - 1;
            oTable.fnPageChange(numericPage);
        }
    }
}



//*********************************************************************************************
//START: Please REMOVE THIS CODE AFTER THE RICE 2.3-RC1 Upgrade!!!
/**
 * Performs client side validation on the list of given controls and returns whether the controls
 * are valid
 *
 * @param controlsToValidate - list of controls (jQuery wrapping objects) that should be validated
 */
function validateLineFields(controlsToValidate) {
    profile(true,"addLine validate");
    var valid = true;

    // skip completely if client validation is off
    if (!validateClient) {
        return valid;
    }

    jQuery.watermark.hideAll();

    // Turn on this flag to avoid prematurely writing out messages which will cause performance issues if MANY
    // fields have validation errors simultaneously (first we are only checking for errors, not checking and
    // writing simultaneously like normal)
    clientErrorExistsCheck = true;

    // Temporarily turn off this flag to avoid traversing unneeded logic (all messages will be shown at the end)
    messageSummariesShown = false;

    controlsToValidate.each(function () {
        var control = jQuery(this);
        var fieldId = jQuery(this).closest("div[data-role='InputField']").attr("id");
        var field = jQuery("#" + fieldId);
        var parent = field.data("parent");
        var validValue = true;

        // remove ignoreValid because there are issues with the plugin if it stays on
        control.removeClass("ignoreValid");

        haltValidationMessaging = true;

        if (!control.prop("disabled") && !control.hasClass("uif-readOnlyContent")) {
            control.valid();
            if (control.hasClass("error")) {
                validValue = false;
            }
        }

        var data = field.data(kradVariables.VALIDATION_MESSAGES);
        data.errors = [];
        data.warnings = [];
        data.info = [];
        handleMessagesAtGroup(parent, fieldId, data, true);

        haltValidationMessaging = false;

        //details visibility check
        if (control.not(":visible") && !validValue) {
            cascadeOpen(control);
        }

        if (!validValue) {
            valid = false;
        }

        control.addClass("ignoreValid");
    });

    // Toggle the flag back to default
    clientErrorExistsCheck = false;

    // Message summaries are going to be shown
    messageSummariesShown = true;

    // Finally, write the result of the validation messages
    writeMessagesForPage();

    jQuery.watermark.showAll();
    profile(false,"addLine validate");
    return valid;
}

function handleMessagesAtGroup(id, fieldId, fieldData, pageSetupPhase) {
    var group = jQuery("#" + id);
    var data = group.data(kradVariables.VALIDATION_MESSAGES);

    var pageLevel = false;
    var parent = group.data("parent");
    if (data) {
        var messageMap = data.messageMap;
        pageLevel = data.pageLevel;

        //init empty params
        if (!data.errors) {
            data.errors = [];
        }
        if (!data.warnings) {
            data.warnings = [];
        }
        if (!data.info) {
            data.info = [];
        }
        if (!messageMap) {
            messageMap = {};
            data.messageMap = messageMap;
        }

        //retrieve header for section
        if (data.isSection == undefined) {
            var sectionHeader = jQuery("[data-header_for='" + id + "']").find("> :header, > label");
            data.isSection = sectionHeader.length;
        }

        //add fresh data to group's message data based on the new field info
        messageMap[fieldId] = fieldData;

        //write messages for this group
        if (!pageSetupPhase) {
            var forceWrite = jQuery("div[data-messages_for='" + id + "']").find("li[data-messageitemfor='" + fieldId + "']").length;
            writeMessagesForGroup(id, data, forceWrite);
            displayHeaderMessageCount(id, data);
        }
    }

    if (!pageLevel && parent) {
        handleMessagesAtGroup(parent, fieldId, fieldData, pageSetupPhase);
    }
}

function writeMessagesForGroup(id, data, forceWrite) {
    var parent = jQuery("#" + id).data("parent");

    if (data) {
        var messageMap = data.messageMap;
        var pageLevel = data.pageLevel;
        var order = data.order;
        var sections = data.sections;

        //retrieve header for section
        if (data.isSection == undefined) {
            var sectionHeader = jQuery("[data-header_for='" + id + "']").find("> :header, > label");
            data.isSection = sectionHeader.length;
        }

        //show messages if data is received as force show or if this group is considered a section
        var showMessages = data.isSection || data.forceShow;

        //TabGroups rely on tab error indication to indicate messages - don't show messages here
        var type = jQuery("#" + id).data("type");
        if (type && type == kradVariables.TAB_GROUP_CLASS) {
            showMessages = false;
        }

        //if this group is in a tab in a tab group show your messages because TabGroups will not
        if (parent) {
            var parentType = jQuery("#" + parent).data("type");
            if (parentType && parentType == kradVariables.TAB_GROUP_CLASS) {
                showMessages = true;
            }
        }

        //init empty params
        if (!data.errors) {
            data.errors = [];
        }
        if (!data.warnings) {
            data.warnings = [];
        }
        if (!data.info) {
            data.info = [];
        }

        data = calculateMessageTotals(id, data);

        if (showMessages) {

            var newList = jQuery("<ul class='" + kradVariables.VALIDATION_MESSAGES_CLASS + "'></ul>");

            if (data.messageTotal || jQuery("span.uif-correctedError").length || forceWrite) {

                newList = generateSectionLevelMessages(id, data, newList);

                if (data.summarize) {
                    newList = generateSummaries(id, messageMap, sections, order, newList);
                }
                else {
                    //if not generating summaries just output field links
                    for (var key in messageMap) {
                        var link = generateFieldLink(messageMap[key], key, data.collapseFieldMessages, data.displayLabel);
                        newList = writeMessageItemToList(link, newList);
                    }
                }

                var messageBlock = jQuery("[data-messages_for='" + id + "']");

                //remove old block styling
                messageBlock.removeClass(kradVariables.PAGE_VALIDATION_MESSAGE_ERROR_CLASS);
                messageBlock.removeClass(kradVariables.PAGE_VALIDATION_MESSAGE_WARNING_CLASS);
                messageBlock.removeClass(kradVariables.PAGE_VALIDATION_MESSAGE_INFO_CLASS);

                //give the block styling
                if (data.errorTotal > 0) {
                    messageBlock.addClass(kradVariables.PAGE_VALIDATION_MESSAGE_ERROR_CLASS);
                }
                else if (data.warningTotal > 0) {
                    messageBlock.addClass(kradVariables.PAGE_VALIDATION_MESSAGE_WARNING_CLASS);
                }
                else if (data.infoTotal > 0) {
                    messageBlock.addClass(kradVariables.PAGE_VALIDATION_MESSAGE_INFO_CLASS);
                }

                //clear and write the new list of summary items
                clearMessages(id, false);
                handleTabStyle(id, data.errorTotal, data.warningTotal, data.infoTotal);
                writeMessages(id, newList);
                //page level validation messsage header handling
                if (pageLevel) {
                    if (newList.children().length) {
                        var messagesDiv = jQuery("[data-messages_for='" + id + "']");
                        var countMessage = generateCountString(data.errorTotal, data.warningTotal,
                            data.infoTotal);

                        //set the window title
                        addCountToDocumentTitle(countMessage);

                        var single = isSingularMessage(newList);
                        var pageValidationHeader;
                        if (!single) {
                            pageValidationHeader = jQuery("<h3 tabindex='0' class='" + kradVariables.VALIDATION_PAGE_HEADER_CLASS + "' "
                                + "id='pageValidationHeader'>This page has " + countMessage + "</h3>");
                        }
                        else {
                            pageValidationHeader = jQuery(newList).detach();
                        }

                        pageValidationHeader.find(".uif-validationImage").remove();
                        var pageSummaryClass = "";
                        var image = errorGreyImage;
                        if (data.errorTotal) {
                            pageSummaryClass = kradVariables.PAGE_VALIDATION_MESSAGE_ERROR_CLASS;
                            image = errorImage;
                        }
                        else if (data.warningTotal) {
                            pageSummaryClass = kradVariables.PAGE_VALIDATION_MESSAGE_WARNING_CLASS;
                            image = warningImage;
                        }
                        else if (data.infoTotal) {
                            pageSummaryClass = kradVariables.PAGE_VALIDATION_MESSAGE_INFO_CLASS;
                            image = infoImage;
                        }

                        if (!single) {
                            pageValidationHeader.prepend(image);
                        }
                        else {
                            pageValidationHeader.find("li").prepend(image);
                            pageValidationHeader.addClass("uif-pageValidationMessage-single")
                        }

                        messagesDiv.prepend(pageValidationHeader);

                        //Handle special classes
                        pageValidationHeader.parent().removeClass(kradVariables.PAGE_VALIDATION_MESSAGE_ERROR_CLASS);
                        pageValidationHeader.parent().removeClass(kradVariables.PAGE_VALIDATION_MESSAGE_WARNING_CLASS);
                        pageValidationHeader.parent().removeClass(kradVariables.PAGE_VALIDATION_MESSAGE_INFO_CLASS);
                        pageValidationHeader.parent().addClass(pageSummaryClass);

                        if (!data.showPageSummaryHeader && !single) {
                            pageValidationHeader.hide();
                        }

                        messagesDiv.find(".uif-validationMessagesList").attr("id", "pageValidationList");
                        messagesDiv.find(".uif-validationMessagesList").attr("aria-labelledby",
                            "pageValidationHeader");
                    }
                }
            }
            else {
                clearMessages(id, true);
            }
        }
    }
}

function runHiddenScripts(id, isSelector, skipValidationBubbling) {

    if (id) {

        var selector = "#" + id;
        if (isSelector) {
            selector = id;
        }

        evaluateScripts(selector);

        if(!isSelector){
            runScriptsForId(id);
        }

        //Interpret new server message state for refreshed InputFields and write them out
        profile(true,"hiddenscripts");
        if (!skipValidationBubbling) {

            //reinitialize BubblePopup
            initBubblePopups();

            jQuery(selector).find("div[data-role='InputField']").andSelf().filter("div[data-role='InputField']").each(function () {
                var id = jQuery(this).attr('id');
                var field = jQuery("#" + id);
                var data = field.data(kradVariables.VALIDATION_MESSAGES);
                data.errors = [];
                data.warnings = [];
                data.info = [];
                var parent = field.data("parent");
                writeMessagesAtField(id);
                handleMessagesAtGroup(parent, id, data, true);
            });

            writeMessagesForPage();
        }
        profile(false,"hiddenscripts");
    }
    else {
        evaluateScripts();

        //reinitialize BubblePopup
        initBubblePopups();
    }

}
//END: Please REMOVE THIS CODE AFTER THE RICE 2.3-RC1 Upgrade!!!
//*********************************************************************************************

function toggleTextBoxes(textBox){
    jQuery(textBox).bind('input', function(event) {
        jQuery(textBox).keyup();
        console.log(event);
    });
}
