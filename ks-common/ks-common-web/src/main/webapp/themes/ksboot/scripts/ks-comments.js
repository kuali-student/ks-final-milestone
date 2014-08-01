var deletedObjects = [];

function initComments() {
    deletedObjects = [];
}

jQuery(function () {
    initComments();
});

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

function addDeletedObject(elem){
    var rowContainer = getRowContainer(elem);
    var rowId = jQuery(rowContainer).attr('id');
    var deletedObject = {};

    jQuery(rowContainer).find('.ks-comment-textArea-field').each(function () {
        jQuery(this).find(":input").each(function () {
            var textId = jQuery(this).attr('id');
            var textValue = jQuery(this).val();
//            deletedObject["key"] = textId;
            deletedObject["textField"] = {"id": textId, "value": textValue};
        });
    });
    jQuery(rowContainer).find('.ks-comment-metaData-container').each(function () {
        var metadata = [];
        jQuery(this).find('.ks-comment-metaData-item').each(function () {
            var keyValuePair = {};
            keyValuePair["key"] = jQuery(this).attr('id');
            var value = jQuery(this).val();
            if (value === '') {
                value = jQuery(this).text();
            }
            keyValuePair["value"] = value;
            metadata.push(keyValuePair);
        });
        deletedObject['metadata'] = metadata;
    });
    deletedObjects.push({"key": rowId, "deletedObject": deletedObject});
}

function restoreDeletedObject(){
    var rowContainer = getRowContainer(elem);
    var itemToRemove = getDeletedComment(rowContainer.attr('id'));

    jQuery(rowContainer).find('.ks-comment-textArea-field').each(function () {
        jQuery(this).find(":input").each(function () {
            var textId = jQuery(this).attr('id');
            var textValue = itemToRemove.deletedObject.textField.filter(function (){
                return this.id = textId;
            });
            jQuery(this).val(textValue);
        });
    });
    jQuery(rowContainer).find('.ks-comment-metaData-container').each(function () {
        jQuery(this).find('.ks-comment-metaData-item').each(function () {
            var key = jQuery(this).attr('id');
            var value = itemToRemove.deletedObject.metadata.filter(function (){
                return this.key = key;
            })
            jQuery(this).text(value);
        });
    });
    deletedObjects.splice(jQuery.inArray(itemtoRemove, deletedObjects), 1);
}

function getDeletedComment(key) {
    deletedObjects.filter(function () {
        return this.key === key;
    });
}

function deleteComment(baseUrl, controllerUrl, elem) {
    var rowContainer = getRowContainer(elem);
    var data = jQuery(elem).data('submit_data');
    var index = parseInt(data['actionParameters[selectedLineIndex]']);
    var formData = jQuery('#kualiForm').serialize() + '&' + jQuery.param(data);
    var targetUrl = baseUrl + "/kr-krad/" + controllerUrl + "?methodToCall=ajaxDeleteComment";

    jQuery.ajax({
        dataType: "json",
        url: targetUrl,
        type: "POST",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            jQuery(rowContainer).remove();
            jQuery("#Comment_list_Header").find("span").text("Comments(" + data + ")");
            jQuery('[id^="KS-collection-rowId_line"]').each(function(){
                jQuery(this).find("button").each(function(){
                    var submitData = jQuery(this).data('submit_data');
                    var i = parseInt(submitData['actionParameters[selectedLineIndex]']);
                    if(i > index){
                        submitData['actionParameters[selectedLineIndex]'] = i - 1;
                        jQuery(this).attr('data-submit_data',JSON.stringify(submitData));
                    }
                });
            });
        },
        error: function (jqXHR, status, error) {
            console.log("error occured");
        }
    });
}

function updateComment(baseUrl, controllerUrl, elem) {
    var rowContainer = getRowContainer(elem);
    var submitData = jQuery(elem).data('submit_data');
    var index = parseInt(submitData['actionParameters[selectedLineIndex]']);
    var formData = jQuery('#kualiForm').serialize() + '&' + jQuery.param(submitData);
    var targetUrl = baseUrl + "/kr-krad/" + controllerUrl + "?methodToCall=ajaxUpdateComment";

    jQuery.ajax({
        dataType: "json",
        url: targetUrl,
        type: "POST",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            toggleCommentButtons(elem);
            jQuery("#KS-CommentField_UI_ID_line" + index ).text(data.commentTextUI);
            jQuery("#lastEditor-container-id_line" + index).show();
            jQuery("#lastEditor-name-id_line" + index).text(data.lastEditorName);
            jQuery("#lastEditor-date-id_line" + index).text(data.lastEditedDate);
        },
        error: function (jqXHR, status, error) {
            console.log("error occured");
        }
    });
}