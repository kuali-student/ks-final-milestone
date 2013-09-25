

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

/**
 * Remove once Rice bug has been fixed and KS updated to correct Rice Version (see https://jira.kuali.org/browse/KSENROLL-9716)
 */
function generateSummaries(id, messageMap, sections, order, newList) {
    var data = getValidationData(jQuery("#" + id), true);
    //if no nested sections just output the fieldLinks
    if (sections.length == 0 || data.isTableCollection == "true") {
        for (var key in messageMap) {
            var link = generateFieldLink(messageMap[key], key, data.collapseFieldMessages, data.displayLabel);
            newList = writeMessageItemToList(link, newList);
        }
    }
    else {
        var currentFields = [];
        var currentSectionId;

        //if sections are present iterate over the fields and sections in order by collecting fields that are considered
        //"direct" descendants of this section - contained in a group (not a section) or just on the section itself.
        //when the iterator hits a section generate a summary sublist which describes the fields that occur before
        //that section (or field group) and add it to the list - then generate the summary link for that section (or
        //or field group).  Add both to the list be generated.

        jQuery.each(order, function (index, value) {
            //if it doesn't start with an s$ its not a section or f$ its not a field group
            if (!(value.indexOf("s$") == 0) && !(value.indexOf("f$") == 0)) {
                currentFields.push(value);
            }
            else if (value.indexOf("c$") == 0) {
                var collectionId = value.substring(2);
                var collectionData = getValidationData(jquery("#" + collectionId), true);
                for (var key in collectionData.messageMap) {
                    var link = generateFieldLink(collectionData.messageMap[key],
                        key, collectionData.collapseFieldMessages, collectionData.displayLabel);
                    newList = writeMessageItemToList(link, newList);
                }
            }
            else {
                var sectionId = value.substring(2);
                if (jQuery("#" + sectionId).data("role") && jQuery("#" + sectionId).data("role") == "placeholder") {
                    //do nothing this is a blank/non-visible section
                }
                else {
                    currentSectionId = sectionId;
                    var sublist = generateFieldLinkSublist(data, currentFields, messageMap, currentSectionId, true);
                    newList = writeMessageItemToList(sublist, newList);
                    var summaryLink = generateSummaryLink(currentSectionId);
                    newList = writeMessageItemToList(summaryLink, newList);
                    currentFields = [];
                }
            }
        });

        //if there are more fields which occur after the final section - generate a sublist summary of those fields
        if (currentFields.length > 0) {
            var sublist = generateFieldLinkSublist(data, currentFields, messageMap, currentSectionId, false);
            newList = writeMessageItemToList(sublist, newList);
        }
    }
    return newList;
}

/**
 *  Fix for KSENROLL 4390 and 4675. Adds a null check for sectionTitle that is missing from krad.validate.js, similar to above method.
 */
function generateFieldLinkSublist(parentSectionData, currentFields, messageMap, sectionId, before) {

    var sectionTitle = jQuery("[data-header_for='" + sectionId + "']").find("> :header .uif-headerText-span, "
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

