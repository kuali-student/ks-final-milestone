jQuery(function () {
    if(console){
        console.log("Document loaded");
    }
    initEditors();
    initReadonlyComments();
});

function initEditors() {
    tinymce.init({
        selector: 'textarea.richtextitem',
//        mode: "none",
        statusbar : false,
        browser_spellcheck: true,
        spellchecker_rpc_url: 'plugins/spellchecker/rpc.php',
        plugins: "link spellchecker hr",
        menu: {
            edit: {title: 'Edit', items: 'undo redo | cut copy paste pastetext | selectall'},
            insert: {title: 'Insert', items: 'link | hr'},
            format: {title: 'Format', items: 'bold italic underline strikethrough | formats | removeformat'},
            table: {title: 'Table', items: 'inserttable tableprops deletetable | cell row column'}
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
    tinymce.remove();
    initEditors();
    initReadonlyComments();
    if (tinymce.get('KS-NewCommentField_control') != null) {
        tinymce.get('KS-NewCommentField_control').setContent("");
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

    jsonPost(baseUrl, targetUrl, formData, jQuery(elem).attr("id"), function(data){
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

    jsonPost(baseUrl, targetUrl, formData, jQuery(elem).attr("id"), function(data){
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

function jsonPost(baseUrl, targetUrl, formData, spinnerElementId, callbackFunction){
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
            }
        },
        error: function (jqXHR, status, error) {
            processException(jqXHR, 'KS-collection-rowId_line'+index, baseUrl);
        }
    });
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
    // global errors on form
    var globalErrorsUl;
    if(data.messageMap.errorCount > 1) {
        globalErrorsUl = jQuery('<ul id="pageValidationList" class="uif-validationMessagesList" aria-labelledby="pageValidationHeader" />');
    } else {
        globalErrorsUl = jQuery('<ul id="pageValidationList" class="uif-validationMessagesList uif-pageValidationMessage-single" aria-labelledby="pageValidationHeader" />');
    }

    jQuery.each(data.translatedErrorMessages, function(key, value){
        var globalErrorLi = createGlobalErrorLi(value[0], baseUrl, parentId, data.messageMap.errorCount);
        jQuery(globalErrorsUl).append(globalErrorLi);
    });

    // global errors on form
    var globalErrorsDiv = createGlobalErrorsDiv(baseUrl, "KS-Comment-pageId", data.messageMap.errorCount, globalErrorsUl);
    jQuery("#KS-Comment-pageId" + " header:eq(0)").after(globalErrorsDiv);
}

function cancelEditComment(elem){
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
    toggleCommentButtons(elem);
}

function cancelDeleteComment(elem){
    if(console){
        console.log("cancelDeleteComment()... dirtyFieldCount = " + dirtyFormState.dirtyFieldCount);
    }
    var rowContainer = getRowContainer(elem);
    dirtyFormState.dirtyFieldCount--;
    jQuery(this).removeClass("dirty");
    toggleDeleteElements(elem);
}

function confirmEditComment(elem){
    if(console){
        console.log("confirmEditComment()... dirtyFieldCount = " + dirtyFormState.dirtyFieldCount);
    }
    toggleCommentButtons(elem);
}

function confirmDeleteComment(elem){
    if(console){
        console.log("confirmDeleteComment()... dirtyFieldCount = " + dirtyFormState.dirtyFieldCount);
    }
    var rowContainer = getRowContainer(elem);
    dirtyFormState.dirtyFieldCount++;
    jQuery(this).addClass("dirty");
    toggleDeleteElements(elem);
}