

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