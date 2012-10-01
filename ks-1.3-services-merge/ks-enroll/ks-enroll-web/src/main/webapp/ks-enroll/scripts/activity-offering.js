
function clearRoomResourcesSelections(sourceLink) {
    var divInputField = sourceLink.siblings('div.uif-inputField');
    divInputField.find('option').each(function() {
            jq(this).removeAttr('selected');
        });
}