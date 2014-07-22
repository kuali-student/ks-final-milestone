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

function deleteComment(baseUrl, elem) {
    commentAjaxController(elem, baseUrl, 'ajaxDeleteComment', function (data) {
        toggleDeleteElements(elem);
        //addDeletedObject(elem);
    });
}

function undeleteComment(baseUrl, elem) {
    var rowContainer = getRowContainer(elem);
    var deletedComment = getDeletedComment(rowContainer.attr('id'));
    commentAjaxController(elem, baseUrl, 'ajaxUndeleteComment', function (data) {
        toggleDeleteElements(elem);
        restoreDeletedObject(elem);
//        var rowContainer = getRowContainer(elem);
//        var rowId = jQuery(rowContainer).attr('id');
//        var itemtoRemove = deletedObjects[rowId];
//        deletedObjects.splice(jQuery.inArray(itemtoRemove, deletedObjects), 1);
    });
}

function getDeletedComment(key) {
    deletedObjects.filter(function () {
        return this.key === key;
    });
}

function commentAjaxController(elem, baseUrl, methodToCall, callbackFunction) {
    var index = -1;
    var rowContainer = getRowContainer(elem);
    var input = jQuery(rowContainer).find(':input')[0];
    if(input !== undefined){
        var name = jQuery(input).attr('name');
        index = name.match(/\[(\d+)\]/)[1];
    }
    var formData = jQuery('#kualiForm').serialize() + '&' + jQuery.param({"selectedLineIndex" : index});
    var targetUrl = baseUrl + "/kr-krad/ksComment?methodToCall=" + methodToCall;
    jQuery.ajax({
        dataType: "json",
        url: targetUrl,
        type: "POST",
        data: formData,
        success: function (data, textStatus, jqXHR) {
            callbackFunction(data);
        },
        error: function (jqXHR, status, error) {
            if (console) {
                console.log("Error Occured...............");
            }
        }
    });
}