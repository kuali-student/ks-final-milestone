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

function toggleDeleteElements(elem){
    var rowContainer = getRowContainer(elem);
    jQuery(rowContainer).find('.ks-toggle-delete').each(function () {
        jQuery(this).toggle();
    });
}

function deleteComment(elemId) {
    var elem = jQuery("#" + elemId);
    toggleDeleteElements(elem);
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

function undeleteComment(elemId){
    var elem = jQuery("#" + elemId);
//    toggleDeleteElements(elem);
    var rowContainer = getRowContainer(elem);
    var rowId = jQuery(rowContainer).attr('id');
    var itemtoRemove = deletedObjects[rowId];
    deletedObjects.splice(jQuery.inArray(itemtoRemove, deletedObjects),1);
}

function getDeletedComment(key){
    deletedObjects.filter(function(){
        return this.key === key;
    });
}