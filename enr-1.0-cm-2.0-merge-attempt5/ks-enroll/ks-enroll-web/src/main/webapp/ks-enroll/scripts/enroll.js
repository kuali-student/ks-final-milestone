/**
 * Fix for KULRICE-7795 as suggested by Brian. We need to remove this method once we get the same in next rice upgrade M3/M4
 */
writeMessagesForPage = function () {
    var page = jQuery("[data-type='Page']");
    var pageId = page.attr("id");
    var data = page.data(kradVariables.VALIDATION_MESSAGES);
    var messageMap = data.messageMap;
    if (!messageMap) {
        messageMap = {};
        data.messageMap = messageMap;
    }
    writeMessagesForGroup(pageId, data);
    writeMessagesForChildGroups(pageId);
    jQuery(".uif-errorMessageItem > div").show();
}

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

function createErrorDivFor(parent, message, url) {
    var parentId = jQuery(parent).attr("id");
    var childId = parentId + "_errors";
    var div1 = jQuery("<div id='" + childId + "' class='uif-validationMessages' style='display: none' data-messagesfor='" + parentId + "'>");
    var div2 = jQuery("<div class='uif-clientMessageItems uif-clientErrorDiv'>");
    jQuery(div1).append(div2);
    var ul = jQuery("<ul/>");
    var li = jQuery("<li class='uif-errorMessageItem-field'/>");
    var image = jQuery("<img class='uif-validationImage' src='" + url + "/krad/images/validation/error.png' alt='Error'/>");
    jQuery(image).text(message);
    jQuery(li).append(image);
    jQuery(ul).append(li);
    jQuery(div2).append(ul);

    return div1;
}


function validateCredits(textBox, url, courseTypeKey) {
    var table = jQuery('<table id="errorTable" style="display: none; position: absolute;"/>');
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
    var div2 = jQuery('<div class="uif-clientMessageItems uif-clientErrorDiv"/>');
    jQuery(td2).append(div2);
    var ul = jQuery('<ul style="padding : 0px;">');
    jQuery(div2).append(ul);
    var li = jQuery('<li class="uif-errorMessageItem-field"/>');
    jQuery(ul).append(li);
    var image2 = jQuery('<img class="uif-validationImage" src="' + url + '/krad/images/validation/error.png" alt="Error" />');
    jQuery(li).append(image2);
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
    jQuery(document).append(table);

    if (courseTypeKey) {
        if (jQuery(textBox).val().trim() != '') {
            var foundMatch = false;
            var textValue;
            var labelValue;
            var allowedValues = '';
            jQuery("input[name='document.newMaintainableObject.dataObject.creditOption.credits']").each(function () {
                var labelForId = jQuery(this).attr("id");
                textValue = jQuery(textBox).val().trim();
                var label = jQuery("label[for='" + labelForId + "']");
                labelValue = parseFloat(jQuery(label).text());
                allowedValues += labelValue + ', ';
                if (textValue == labelValue) {
                    foundMatch = true;
                }
            });

            var lastComaIndex = allowedValues.lastIndexOf(',');
            if (lastComaIndex > 0) {
                allowedValues = allowedValues.substring(0, lastComaIndex);
            }

            var div = jQuery(jQuery(textBox)).closest('div');
            var errorMessage = "Please enter one of the values: " + allowedValues;
            //var errorChild = createErrorDivFor(div, errorMessage  , url);
            //jQuery(div).append(errorChild);
            div.find('#errorTable').remove();
            if (!foundMatch) {
                jQuery(textBox).addClass("error").removeClass("valid");
                jQuery(textBox).attr("aria-invalid", "true");
                jQuery(div).addClass("uif-hasError");
                if (jQuery(div).find('img').length == 0) {
                    jQuery(div).append('<img class="uif-validationImage" src="' + url + '/krad/images/validation/error.png" alt="Error" />');
                }
                // jQuery(div).attr('title', 'Allowed values are: ' + allowedValues);
                jQuery(li).append(errorMessage);
                jQuery(div).prepend(table);
                var moveLeft = -20;
                var moveDown = -60;
                jQuery(div).hover(
                    function (e) {
                        jQuery('table#errorTable').show().css('top', jQuery(textBox).offset().top + moveDown).css('left', jQuery(textBox).offset().left + moveLeft);
                    },
                    function (e) {
                        jQuery('table#errorTable').hide();
                    }
                );
            } else {
                if (jQuery(textBox).attr("aria-invalid") != undefined) {
                    jQuery(textBox).attr("aria-invalid").remove();
                }
                jQuery(textBox).addClass("valid").removeClass("error");
                jQuery(div).removeClass("uif-hasError");
                jQuery(div).find('img').remove();
                jQuery(div).unbind('mouseenter mouseleave');
            }
        }
    }
}

function showErrorPopup(formId) {

    var div1 = jQuery('<div class="fancybox-wrap fancybox-desktop fancybox-type-html fancybox-opened" style="width: 418px; height: auto; display: block; position: fixed; top: 474px; left: 743px; opacity: 1;">');
    var div2 = jQuery('<div class="fancybox-skin" style="padding: 15px;">');
    var div3 = jQuery('<div class="fancybox-outer">');
    var div4 = jQuery('<div class="fancybox-inner" style="width: 388px; height: auto; overflow: auto;">');
    var text = 'The form contains errors. Please correct these errors and try again.';
    var div5 = jQuery('<div title="Close" class="fancybox-item fancybox-close">');

    jQuery(div1).append(div2);
    jQuery(div2).append(div3);
    jQuery(div3).append(div4);
    jQuery(div4).text(text);
    jQuery(div4).append(div5);

    jQuery(div1).appendTo(jQuery('body'));
    jQuery(div1).show();
    jQuery('#formId').unbind('submit');
    return false;
}

function showFixedOptions(textBox, url, courseTypeKey) {
    var parent = jQuery("#KS-CourseOfferingEdit-CreditType_OptionTypeFixed");
    jQuery(textBox).attr("disabled", "disabled");

    var child;

    if (jQuery(parent).find('#div_fixed_options').length > 0) {
        child = jQuery('#div_fixed_options');
    } else {
        child = jQuery('<div id="div_fixed_options" style="position: absolute; display: none; border: 1px solid black; line-style: none;"/>');
        jQuery(child).width(jQuery(textBox).width() + 10);
        jQuery(child).css("background-color", jQuery(textBox).css("background-color"));
        jQuery(child).css("opacity", "2");
        jQuery(parent).append(child);

        var ul = jQuery('<ul/>');
        var emptyLi = jQuery('<li/>');
        jQuery(emptyLi).text(jQuery(textBox).val());
        emptyLi.click(function () {
            jQuery(textBox).val(jQuery(this).text());
            jQuery(textBox).removeAttr("disabled");
            jQuery('#div_fixed_options').hide();
        });
        jQuery(ul).append(emptyLi);

        jQuery(child).append(ul);
        jQuery("input[name='document.newMaintainableObject.dataObject.creditOption.credits']").each(function () {
            var labelForId = jQuery(this).attr("id");
            textValue = jQuery(textBox).val().trim();
            var label = jQuery("label[for='" + labelForId + "']");
            labelValue = parseFloat(jQuery(label).text());

            var li = jQuery('<li/>');
            jQuery(li).text(labelValue);
            jQuery(ul).append(li);

            li.click(function () {
                jQuery(textBox).val(jQuery(this).text());
                jQuery(textBox).removeAttr("disabled");
                jQuery('#div_fixed_options').hide();
            });

        });
    }

    jQuery(child).mouseleave(function () {
        jQuery(textBox).removeAttr("disabled");
        jQuery('#div_fixed_options').hide();
        //jQuery(parent).find('#div_fixed_options').remove();
    });

    jQuery(textBox).mouseenter(function () {
        jQuery(textBox).attr("disabled", "disabled");
        jQuery('#div_fixed_options').hide();
        jQuery('#div_fixed_options').show().css('top', jQuery(textBox).offset().top).css('left', jQuery(textBox).offset().left);
    });

    jQuery('#div_fixed_options').show().css('top', jQuery(textBox).offset().top).css('left', jQuery(textBox).offset().left);
}

function removeColumns(isReadOnly, columns, componentId) {
    if (isReadOnly) {
        var div = jQuery('#' + componentId);
        var table = jQuery(div).find('table');
        var tableId = jQuery(table).attr('id');

        jQuery.each(columns, function (index, column) {
            var columIndex = column - index
            var th = jQuery('#' + tableId + ' thead tr').find('th:nth-child(' + columIndex + ')');
            jQuery(th).remove();
            jQuery('#' + tableId + ' tbody tr').find('td:nth-child(' + columIndex + ')').each(function () {
                jQuery(this).remove();
            });
            var tf = jQuery('#' + tableId + ' tfoot tr').find('th:nth-child(' + columIndex + ')');
            jQuery(tf).remove();
        });
    }
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
                "methodToCall":"loadAOs",
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
 * Fix for KSENROLL-4394 as suggested by Brian. We need to remove this method once we get the same in next rice upgrade 2.2.1-M1
 *
 * In KRAD it's not checking that sectionData != null so that check is added below.  Overwriting method in krad.validate.js
 */
function displayHeaderMessageCount(sectionId, sectionData) {
    var sectionHeader = jQuery("[data-headerFor='" + sectionId + "']").find("> :header, > label");

    if(errorImage == undefined){
        setupImages();
    }

    if (sectionHeader.length && sectionData && sectionData.messageTotal) {

        var countMessage = generateCountString(sectionData.errorTotal, sectionData.warningTotal, sectionData.infoTotal);
        var image = "";
        if (sectionData.errorTotal) {
            image = errorImage;
        }
        else if (sectionData.warningTotal) {
            image = warningImage;
        }
        else if (sectionData.infoTotal) {
            image = infoImage;
        }

        jQuery(sectionHeader).find("div." + kradVariables.MESSAGE_COUNT_CLASS).remove();

        if (countMessage != "") {
            jQuery("<div class='" + kradVariables.MESSAGE_COUNT_CLASS + "'>[" + image + " " + countMessage + "]</div>").appendTo(sectionHeader);
        }
    }
}


/*
 function updateCollectionAndRelatedItem(jqObject, collectionGroupId, updateAfterId){
 if(jqObject && collectionGroupId){
 collectionGroupId = jqObject.closest("[id^='" + collectionGroupId + "']").attr("id");
 collectionGroupId = collectionGroupId.replace("_group", "");
 var otherElementToBlock = jq("#" + updateAfterId + "_div");
 var updateComponentCallback = function(htmlContent){
 var component = jq("#" + updateAfterId + "_div", htmlContent);

 otherElementToBlock.unblock({onUnblock: function(){
 //replace component
 if(jq("#" + updateAfterId + "_div").length){
 jq("#" + updateAfterId + "_div").replaceWith(component);
 }
 runHiddenScripts(updateAfterId + "_div");
 }
 });
 };
 var elementToBlock = jq("#" + collectionGroupId + "_div");
 var updateCollectionCallback = function(htmlContent){
 var component = jq("#" + collectionGroupId + "_div", htmlContent);

 elementToBlock.unblock({onUnblock: function(){
 //replace component
 if(jq("#" + collectionGroupId + "_div").length){
 jq("#" + collectionGroupId + "_div").replaceWith(component);
 }
 runHiddenScripts(collectionGroupId + "_div");
 ajaxSubmitForm("updateComponent", updateComponentCallback,
 {reqComponentId: updateAfterId, skipViewInit: "true"}, otherElementToBlock);
 }
 });
 };

 var methodToCall = jq("input[name='methodToCall']").val();
 ajaxSubmitForm(methodToCall, updateCollectionCallback, {reqComponentId: collectionGroupId, skipViewInit: "true"},
 elementToBlock);
 }
 }

 function removeFromCart(){
 var row = jq(this).closest("tr.keyRow");
 var name = jq(row).find(".timeKeyName").text();
 if(confirm("Remove "+ name +" from your cart?")){
 var id = jq(row).attr("name");
 writeHiddenToForm("methodToCall", "removeFromCart");
 writeHiddenToForm('jumpToId' , 'TOP');
 writeHiddenToForm('renderFullView' , 'true');
 writeHiddenToForm('actionParameters[itemId]' , id);
 jq('#kualiForm').submit();
 }
 }*/
