

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

/*
 * FIXME KSENROLL-10557: Remove once jira is fixed.
 *
 * Function that returns lookup results by script
 */
function returnLookupResultByScript(fieldName, value) {
    var returnField;
    if (parent.jQuery('iframe[id*=easyXDM_]').length > 0) {
        // portal and content on same domain
        returnField = top.jQuery('iframe[id*=easyXDM_]').contents().find('#' + kradVariables.PORTAL_IFRAME_ID).contents().find('[name="' + escapeName(fieldName) + '"]');
    } else if (parent.parent.jQuery('#' + kradVariables.PORTAL_IFRAME_ID).length > 0) {
        // portal and content on different domain
        returnField = parent.parent.jQuery('#' + kradVariables.PORTAL_IFRAME_ID).contents().find('[name="' + escapeName(fieldName) + '"]');
    } else {
        returnField = top.jq('[name="' + escapeName(fieldName) + '"]');
    }
    if (!returnField.length) {
        return;
    }

    returnField.val(value);

    //Don't put focus on hidden fields.
    if(returnField.is(":visible")) {
        returnField.focus();
        returnField.blur();
        returnField.focus();

        // trigger change event
        returnField.change();
    }
}

/**
 * Uses jQuery DataTable plug-in to decorate a table with functionality like
 * sorting and page. The second argument is a Map of options that are available
 * for the plug-in. See <a href=http://www.datatables.net/usage/>datatables</a> for
 * documentation on these options
 *
 * Once we get the next rice release (Rice 2.4.1), have to remove this override
 * Override to correct KSENROLL-12880
 * Todo: KSENROLL-12894
 *
 * @param tableId id for the table that should be decorated
 * @param additionalOptions map of additional or override option settings (option name/value pairs) for the plugin
 * @param groupingOptions (optional) if supplied, the collection will use rowGrouping with these options
 */
function createTable(tableId, additionalOptions, groupingOptions) {
    jQuery(document).ready(function () {
        var table = jQuery("#" + tableId);
        var detailsOpen = table.parent().data("details_default_open");
        table.data("open", detailsOpen);
        if (groupingOptions) {
            table.attr("data-groups", "true");
        }
        var options = {
            "bDestory": true,
            "bStateSave": true,
            "fnStateSave": function (oSettings, oData) {
                setComponentState(tableId, 'richTableState', oData);
            },
            "fnStateLoad": function (oSettings) {
                var oData = getComponentState(tableId, 'richTableState');
                return oData;
            }
        }
        options = jQuery.extend(options, additionalOptions);
        var hideActionColumnOption = {
            "fnDrawCallback": function (oSettings) {
                hideEmptyActionColumn(tableId, ".uif-collection-column-action");
            }
        }
        options = jQuery.extend(options, hideActionColumnOption);
        var exportOptions = {
            "sDownloadSource": additionalOptions.sDownloadSource,
            "oTableTools": {
                "aButtons": [
                    {
                        "sExtends": "text",
                        "sButtonText": "csv",
                        "fnClick": function (nButton, oConfig) {
                            window.location.href = additionalOptions.sDownloadSource + "&methodToCall=tableCsvRetrieval&formatType=csv";
                        },
                    },
                    {
                        "sExtends": "text",
                        "sButtonText": "xml",
                        "fnClick": function (nButton, oConfig) {
                            window.location.href = additionalOptions.sDownloadSource + "&methodToCall=tableXmlRetrieval&formatType=xml";
                        },
                    },
                    {
                        "sExtends": "text",
                        "sButtonText": "xls",
                        "fnClick": function (nButton, oConfig) {
                            window.location.href = additionalOptions.sDownloadSource + "&methodToCall=tableXlsRetrieval&formatType=xls";
                        },
                    }
                ]
            }
        }
        // check that the export feature is turned on
        if (options.sDom && options.sDom.search("T") >= 0) {
            options = jQuery.extend(options, exportOptions)
        }
        var oTable = table.dataTable(options);
        //make sure scripts are run after table renders (must be done here for deferred rendering)
        runHiddenScripts(tableId, false, true);
        initBubblePopups();
        //insure scripts (if any) are run on each draw, fixes bug with scripts lost when paging after a refresh
        jQuery(oTable).on("dataTables.tableDraw", function (event) {
            if (event.currentTarget != event.target) {
                return;
            }
            runHiddenScripts(tableId, false, true);
            jQuery("div[data-role='InputField'][data-has_messages='true']", "#" + tableId).each(function () {
                var id = jQuery(this).attr('id');
                var validationData = getValidationData(jQuery("#" + id));
                if (validationData && validationData.hasOwnMessages) {
                    handleMessagesAtField(id);
                }
            });
        });
        //handle row details related functionality setup
        if (detailsOpen != undefined) {
            jQuery(oTable).on("dataTables.tableDraw", function (event) {
                if (event.currentTarget != event.target) {
                    return;
                }
                if (table.data("open")) {
                    openAllDetails(tableId);
                }
                else {
                    closeAllDetails(tableId);
                }
            });
            if (detailsOpen) {
                openAllDetails(tableId, false);
            }
        }
        // allow table column size recalculation on window resize
        jQuery(window).bind('resize', function () {
            // passing false to avoid copious ajax requests during window resize
            oTable.fnAdjustColumnSizing(false);
        });
        if (groupingOptions) {
            oTable.rowGrouping(groupingOptions);
        }
    });
}


/**
 * Write the messages out for the group that are present its data
 *
 * Once we get the next rice release (Rice 2.4.1), have to remove this override
 * Override to correct KSENROLL-12943
 * Todo: KSENROLL-12994
 *
 * @param id id of the group
 * @param data validationData for the group
 * @param forceWrite forces the group write to be processed
 */
function writeMessagesForGroup(id, data, forceWrite, skipCalculateTotals) {
    var group = jQuery("#" + id);
    var parent = group.data("parent");

    if (data) {
        var messageMap = data.messageMap;
        var pageLevel = data.pageLevel;
        var order = data.order;
        var sections = data.sections;

        //retrieve header for section
        if (data.isSection === undefined) {
            var sectionHeader = jQuery("[data-header_for='" + id + "']").find("> :header, > label");
            data.isSection = sectionHeader.length;
        }

        //show messages if data is received as force show or if this group is considered a section
        var showMessages = data.isSection || data.forceShow;

        //TabGroups rely on tab error indication to indicate messages - don't show messages here
        var type = group.data("type");
        if (type && type === kradVariables.TAB_GROUP_CLASS) {
            showMessages = false;
        }

        //if this group is in a tab in a tab group show your messages because TabGroups will not
        if (parent) {
            var parentType = jQuery("#" + parent).data("type");
            if (parentType && parentType === kradVariables.TAB_GROUP_CLASS) {
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

        if (!skipCalculateTotals) {
            data = calculateMessageTotals(id, data);
        }

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

                if (messageBlock.length === 0) {
                    var cssClasses = "uif-validationMessages uif-groupValidationMessages";
                    if (pageLevel){
                        cssClasses = cssClasses + " uif-pageValidationMessages";
                    }

                    messageBlock = jQuery("<div id='" + id + "_messages' class='"
                        + cssClasses + "' data-messages_for='" + id + "' "
                        + "style='display: none;'>");

                    var disclosureBlock = group.find("#" + id + "_disclosureContent");
                    if (disclosureBlock.length) {
                        disclosureBlock.prepend(messageBlock);
                    } else if (!data.isSection) {
                        group.prepend(messageBlock);
                    } else if (data.isSection) {
                        var header = group.find("> .uif-header-contentWrapper");
                        if (header.length === 0){
                            header = group.find("> [data-header_for='" + id + "']");
                        }
                        header.after(messageBlock);
                    }
                }

                //remove old block styling
                messageBlock.removeClass("alert");
                messageBlock.removeClass(kradVariables.PAGE_VALIDATION_MESSAGE_ERROR_CLASS);
                messageBlock.removeClass("alert-warning");
                messageBlock.removeClass(kradVariables.PAGE_VALIDATION_MESSAGE_INFO_CLASS);
                messageBlock.removeClass("alert-success");

                //give the block styling
                if (data.errorTotal > 0) {
                    messageBlock.removeClass("uif-validationMessages");
                    messageBlock.removeClass("uif-groupValidationMessages");
                    messageBlock.addClass("alert");
                    messageBlock.addClass(kradVariables.PAGE_VALIDATION_MESSAGE_ERROR_CLASS);
                }
                else if (data.warningTotal > 0) {
                    messageBlock.removeClass("uif-validationMessages");
                    messageBlock.removeClass("uif-groupValidationMessages");
                    messageBlock.addClass("alert");
                    messageBlock.addClass("alert-warning");
                }
                else if (data.infoTotal > 0) {
                    messageBlock.removeClass("uif-validationMessages");
                    messageBlock.removeClass("uif-groupValidationMessages");
                    messageBlock.addClass("alert");
                    messageBlock.addClass(kradVariables.PAGE_VALIDATION_MESSAGE_INFO_CLASS);
                }
                else {
                    messageBlock.addClass("alert");
                    messageBlock.addClass("alert-success");
                }

                //clear and write the new list of summary items
                clearMessages(id, false);
                handleTabStyle(id, data.errorTotal, data.warningTotal, data.infoTotal);
                writeMessages(id, newList);

                //page level validation messsage header handling
                if (pageLevel) {
                    if (newList.children().length) {

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
                            pageSummaryClass = "alert-warning";
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

                        messageBlock.prepend(pageValidationHeader);

                        //Handle special classes
                        pageValidationHeader.parent().removeClass(kradVariables.PAGE_VALIDATION_MESSAGE_ERROR_CLASS);
                        pageValidationHeader.parent().removeClass("alert-warning");
                        pageValidationHeader.parent().removeClass(kradVariables.PAGE_VALIDATION_MESSAGE_INFO_CLASS);
                        pageValidationHeader.parent().addClass(pageSummaryClass);

                        if (!data.showPageSummaryHeader && !single) {
                            pageValidationHeader.hide();
                        }

                        messageBlock.find(".uif-validationMessagesList").attr("id", "pageValidationList");
                        messageBlock.find(".uif-validationMessagesList").attr("aria-labelledby",
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