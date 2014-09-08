var totalErrorCount = 0;
var numberOfControlsOpen = 0;

jQuery(function () {
    if(console){
        console.log("Document loaded");
    }
    if (tinymce != undefined) {
        initEditors();
        initReadonlyComments();
    }else{
        if(console){
            console.log();"tinymce is not loaded yet."
        }
    }
});

function initEditors() {
    tinymce.init({
        //        mode: "none",
        selector: 'textarea.richtextitem',
        plugins: "advlist link spellchecker hr",
        statusbar : false,
        browser_spellcheck: true,
        spellchecker_rpc_url: 'plugins/spellchecker/rpc.php',
        style_formats: [
            {title: "Headers", items: [
                {title: "Header 1", format: "h1"},
                {title: "Header 2", format: "h2"},
                {title: "Header 3", format: "h3"},
                {title: "Header 4", format: "h4"},
                {title: "Header 5", format: "h5"},
                {title: "Header 6", format: "h6"}
            ]},
            {title: "Blocks", items: [
                {title: "Paragraph", format: "p"},
                {title: "Blockquote", format: "blockquote"},
                {title: "Div", format: "div"},
                {title: "Pre", format: "pre"}
            ]}
        ],
        extended_valid_elements: 'a[href|target=_blank]',
        link_title: false,
        target_list: [
            {title: 'New Page', value: '_blank'}
        ],
        menu: {
            edit: {title: 'Edit', items: 'undo redo | cut copy paste pastetext | selectall'},
            insert: {title: 'Insert', items: 'link | hr'},
            format: {title: 'Format', items: 'bold italic underline strikethrough | formats'}
        },
        toolbar1: "undo redo | bold italic underline strikethrough | numlist bullist outdent indent | link hr",
        setup: function (ed) {
            ed.on('blur', function () {
                editorOnChangeHandler(ed);
            });
        }
    });
}


function editorOnChangeHandler(ed) {
    var elem = jQuery("#" + ed.id).each(function () {
        jQuery(this).val(tinyMCE.get(ed.id).getContent());
        if (tinyMCE.get(ed.id).isNotDirty) {
            if (jQuery(this).hasClass('dirty')) {
                jQuery(this).removeClass('dirty');
                dirtyFormState.dirtyFieldCount--;
            }
        } else{
            if (!jQuery(this).hasClass('dirty')) {
                jQuery(this).addClass('dirty');
                dirtyFormState.dirtyFieldCount++;
            }
        }
    });
}

function initReadonlyComments(){
    jQuery('[id^="KS-CommentField_UI_ID_line"]').each(function(){
        var content = jQuery(this).text();
        jQuery(this).text("");
        jQuery(this).html(content);
    });
}

function getRowContainer(elem) {
    var id = jQuery(elem).attr('id');
    var index = id.substring(id.lastIndexOf('_line') + '_line'.length, id.length);
    return jQuery("#KS-collection-rowId_line" + index);
}

function toggleCommentButtons(elem) {
    var rowContainer = getRowContainer(elem);
    jQuery(rowContainer).find('.ks-toggleable').each(function () {
        jQuery(this).toggle();
    });
}

function toggleDeleteElements(elem) {
    var rowContainer = getRowContainer(elem);
    jQuery(rowContainer).find('.ks-toggle-delete').each(function () {
        jQuery(this).toggle();
    });
    jQuery(rowContainer).find('.ks-deleted-comment').each(function () {
        jQuery(this).toggleClass("ks-deleting-comment");
    });
}

function preAddComment(elementId) {
    tinyMCE.triggerSave();
}

function afterAddComment(elementId) {
    if (console) {
        console.log("Reassigning the editors ........");
    }
    numberOfControlsOpen = 0;
    tinymce.remove();
    initEditors();
    initReadonlyComments();
    if(jQuery("#KS-CommentSection_ID_messages").length){
        totalErrorCount = 1;
    }else{
        totalErrorCount = 0;
        if (tinymce.get('KS-NewCommentField_control') != null) {
            tinymce.get('KS-NewCommentField_control').setContent("");
        }
    }

    for (edId in tinyMCE.editors){
        //tinyMCE.editors[edId].save();
        var id = tinymce.editors[edId].id;
        var controlIndex = id.indexOf("_control");
        if(controlIndex > 0){
            var roc = id.substring(0, controlIndex);
            var splitIndex = roc.indexOf("_ID_");
            if(splitIndex > 0){
                var roId = roc.substring(0, splitIndex) + "_UI" + roc.substring(splitIndex, roc.length);
                if(jQuery("#" + roId).length){
                    var value = jQuery("#" + roId).html();
                    tinymce.editors[edId].setContent(value);
                }
            }
        }
    }
}

function deleteComment(baseUrl, controllerUrl, elem) {
    if (console) {
        console.log("deleteComment()... dirtyFieldCount = " + dirtyFormState.dirtyFieldCount);
    }
    var rowContainer = getRowContainer(elem);
    var data = jQuery(elem).data('submit_data');
    var index = parseInt(data['actionParameters[selectedLineIndex]']);
    var formData = jQuery('#kualiForm').serialize() + '&' + jQuery.param(data);
    var targetUrl = baseUrl + "/kr-krad" + controllerUrl + "?methodToCall=ajaxDeleteComment";

    jsonPost(baseUrl, targetUrl, formData, jQuery(elem).attr("id"), index, function(data){
        jQuery(rowContainer).remove();
        dirtyFormState.dirtyFieldCount--;
        jQuery("#Comment_list_Header").find("span").text("Comments(" + data.count + ")");
        jQuery('[id^="KS-collection-rowId_line"]').each(function(){
            jQuery(this).find("button[data-submit_data]").each(function(){
                var submitData = jQuery(this).data('submit_data');
                var i = parseInt(submitData['actionParameters[selectedLineIndex]']);
                if(i > index){
                    submitData['actionParameters[selectedLineIndex]'] = i - 1;
                    jQuery(this).attr('data-submit_data',JSON.stringify(submitData));
                }
            });
        });
    });
}

function updateComment(baseUrl, controllerUrl, elem) {
    if(console){
        console.log("updateComment()... dirtyFieldCount = " + dirtyFormState.dirtyFieldCount);
    }
    var rowContainer = getRowContainer(elem);
    var submitData = jQuery(elem).data('submit_data');
    var index = parseInt(submitData['actionParameters[selectedLineIndex]']);
    var formData = jQuery('#kualiForm').serialize() + '&' + jQuery.param(submitData);
    var targetUrl = baseUrl + "/kr-krad" + controllerUrl + "?methodToCall=ajaxUpdateComment";

    jsonPost(baseUrl, targetUrl, formData, jQuery(elem).attr("id"), index, function(data){
        toggleCommentButtons(elem);
        jQuery("#KS-CommentField_UI_ID_line" + index ).html(data.commentWrapper.commentTextUI);
        jQuery("#lastEditor-container-id_line" + index).show();
        jQuery("#lastEditor-name-id_line" + index).text(data.commentWrapper.lastEditorName);
        jQuery("#lastEditor-date-id_line" + index).text(data.commentWrapper.lastEditedDate);
        jQuery(rowContainer).find(".dirty").each(function(){
            dirtyFormState.dirtyFieldCount--;
            this.defalutValue = data.commentWrapper.commentTextUI;
            jQuery(this).removeClass("dirty");
        });
    });
}

function jsonPost(baseUrl, targetUrl, formData, spinnerElementId, index, callbackFunction){
    var spinner = jQuery('<div id="spiner_' + spinnerElementId + '" class="blockUI blockMsg blockElement"><img src="' + baseUrl + '/themes/ksboot/images/loader.gif" alt="Loading..."> Loading...</div>');
    jQuery.ajax({
        dataType: "json",
        url: targetUrl,
        type: "POST",
        data: formData,
        beforeSend: function () {
            var spinnerElement = jQuery('#' + spinnerElementId);
            spinner.attr('style', 'position: absolute; top: ' + spinnerElement.offset().top + 'px; left:' + spinnerElement.offset().left + 'px !important; width: ' +  spinnerElement.outerWidth() + 'px;');
            jQuery(spinnerElement).append(spinner).show();
        },
        complete: function () {
            jQuery('#spiner_' + spinnerElementId).remove();
        },
        success: function (data, textStatus, jqXHR) {
            if(data.hasErrors){
                processErrors(data, 'KS-collection-rowId_line'+index, baseUrl);
            }else{
                callbackFunction(data);
                clearSectionErrorMessage('KS-collection-rowId_line'+index);
                clearPageError(baseUrl);
                numberOfControlsOpen--;
                toggleAddButton();
            }
        },
        error: function (jqXHR, status, error) {
            processException(jqXHR, 'KS-collection-rowId_line'+index, baseUrl);
        }
    });
}

function toggleAddButton() {
    if (numberOfControlsOpen > 0) {
        jQuery("#KS-Comment_AddButton_Id").attr('disabled','disabled');
    } else {
        jQuery("#KS-Comment_AddButton_Id").removeAttr('disabled');
    }
}

function processException(request, parentId, baseUrl){
    // global errors on form
    var globalErrorsUl = jQuery('<ul id="pageValidationList" class="uif-validationMessagesList" aria-labelledby="pageValidationHeader" />');

    var globalErrorLi = createGlobalErrorLi(request.responseText, baseUrl, parentId, 1);
    jQuery(globalErrorsUl).append(globalErrorLi);

    // global errors on form
    var globalErrorsDiv = createGlobalErrorsDiv(baseUrl, "KS-Comment-pageId", 1, globalErrorsUl);
    jQuery("#KS-Comment-pageId" + " header:eq(0)").after(globalErrorsDiv);
}

function processErrors(data, parentId, baseUrl){
    processSectionErrors(data, parentId);
    processHeader(baseUrl);
    var pageMessages = jQuery("#KS-Comment-pageId_messages");
    jQuery(pageMessages).remove();
    var page = jQuery("#KS-Comment-pageId");
     jQuery(page).prepend(createPageErrors(data, parentId, baseUrl));
}

function createPageErrors(data, parentId, baseUrl){
    var div;
    if(totalErrorCount > 0){
        var li = createPageErrorLink(baseUrl);
        var ul = jQuery('<ul class="uif-validationMessagesList uif-pageValidationMessage-single" id="pageValidationList" aria-labelledby="pageValidationHeader"></ul>');
        div = jQuery('<div id="KS-Comment-pageId_messages" class="uif-pageValidationMessages alert alert-danger" data-messages_for="KS-Comment-pageId" style=""></div>');
        ul.append(li);
        div.append(ul);
    }
    return div;
}

function processHeader(baseUrl){
    var header = jQuery("#KS-CommentSection_Header_ID");
    var div = jQuery(header).find('div.uif-messageCount');
    jQuery(div).remove();
    if (totalErrorCount > 0) {
        var h1 = jQuery('<h1 class="uif-headerText"></h1>');
        var div = jQuery('<div class="uif-messageCount"></div>');
        var image = createErrorImage(baseUrl);
        var message = totalErrorCount + (totalErrorCount > 1 ? "errors." : "error." );
        h1.append(image);
        h1.text(message);
        div.append(h1);
    }
}

function createErrorImage(baseUrl){
    return jQuery('<img class="uif-validationImage" src="' + baseUrl + '/themes/ksboot/images/validation/error.png" alt="Error" />');
}

function createPageErrorLink(baseUrl) {
    var sectionHeader = jQuery("#KS-CommentSection_Header_ID");
    // filter to get the first one
    var headerText = jQuery(sectionHeader).text();
    var message = headerText + totalErrorCount + (totalErrorCount > 1 ? " errors." : " error." );
    var li =  jQuery('<li class="uif-errorMessageItem" data-messageitemfor="KS-CommentSection_ID"></li>');
    var errorImage = createErrorImage(baseUrl);
    li.append(errorImage);
    var anchor = jQuery('<a href="#">' + message  + '</a>');
    li.append(anchor);
    return li;
}

function clearSectionErrorMessage(parentId){
    var commentSectionError = jQuery("#" + parentId + "_messages");
    if(commentSectionError.length){
        var errorCount = jQuery(commentSectionError).find('a').length;
        if(errorCount != undefined){
            totalErrorCount -= errorCount;
        }
        jQuery(commentSectionError).remove();
    }
}

function refreshPageError(baseUrl){
    var pageErrorMessage = jQuery("#KS-Comment-pageId_messages");
    var li = createPageErrorLink(baseUrl);
    jQuery(pageErrorMessage).find('li').replaceWith(li);
}

function clearPageError(baseUrl){
    if(totalErrorCount == 0){
        jQuery("#KS-Comment-pageId_messages").remove();
    }else{
        refreshPageError(baseUrl);
    }
}

function createSectionErrorMessage(commentSectionId){
    return jQuery('<div id="' + commentSectionId + '_messages" class="alert alert-danger" data-messages_for="' + commentSectionId + '" style="">' +
        '<ul class="uif-validationMessagesList"></ul></div>');
}

function processSectionErrors(data, parentId){
    var commentSection = jQuery("#" + parentId);
    clearSectionErrorMessage(parentId);
    var commentSectionError = createSectionErrorMessage(parentId);
    var ul = jQuery(commentSectionError).find('ul');
    jQuery.each(data.translatedErrorMessages, function(key, value){
        var controlId = jQuery("#" + parentId).find('textarea[name*="' + key + '"]').attr("id");
        var li = jQuery('<li data-messageitemfor="' + controlId + '" class="uif-errorMessageItem"></li>');
        var anchor = jQuery('<a href="#">' + value[0] + '</a>');
        jQuery(li).append(anchor);
        jQuery(ul).append(li);
        totalErrorCount++;
    });
    jQuery(commentSection).prepend(commentSectionError);
}

function cancelEditComment(elem, baseUrl){
    if(console){
        console.log("cancelEditComment() ... dirtyFieldCount = " + dirtyFormState.dirtyFieldCount);
    }
    var rowContainer = getRowContainer(elem);
    jQuery(rowContainer).find(".dirty").each(function(){
        dirtyFormState.dirtyFieldCount--;
        this.value = this.defaultValue;
        tinymce.get(this.id).setContent(this.value);
        jQuery(this).removeClass("dirty");
    });
    clearSectionErrorMessage(jQuery(rowContainer).attr("id"));
    clearPageError(baseUrl);
    toggleCommentButtons(elem);
    numberOfControlsOpen--;
    toggleAddButton();
}

function cancelDeleteComment(elem, baseUrl){
    if(console){
        console.log("cancelDeleteComment()... dirtyFieldCount = " + dirtyFormState.dirtyFieldCount);
    }
    var rowContainer = getRowContainer(elem);
    dirtyFormState.dirtyFieldCount--;
    jQuery(this).removeClass("dirty");
    clearSectionErrorMessage(jQuery(rowContainer).attr("id"));
    clearPageError(baseUrl);
    toggleDeleteElements(elem);
    numberOfControlsOpen--;
    toggleAddButton();
}

function confirmEditComment(elem){
    if(console){
        console.log("confirmEditComment()... dirtyFieldCount = " + dirtyFormState.dirtyFieldCount);
    }
    toggleCommentButtons(elem);
    numberOfControlsOpen++;
    toggleAddButton();
}

function confirmDeleteComment(elem){
    if(console){
        console.log("confirmDeleteComment()... dirtyFieldCount = " + dirtyFormState.dirtyFieldCount);
    }
    var rowContainer = getRowContainer(elem);
    dirtyFormState.dirtyFieldCount++;
    jQuery(this).addClass("dirty");
    toggleDeleteElements(elem);
    numberOfControlsOpen++;
    toggleAddButton();
}