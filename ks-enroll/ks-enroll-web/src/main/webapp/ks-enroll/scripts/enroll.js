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
    }