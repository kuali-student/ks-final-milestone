function enableCreateOffering(){

		var addFields = jQuery(".KS-FormatOfferingSubSection-addField:visible");
        jQuery.watermark.hideAll();

		var valid = true;
		addFields.each(function(){
            jQuery(this).removeClass("ignoreValid");
            jQuery(this).valid();
			if(jQuery(this).hasClass("error")){
				valid = false;
			}
            jQuery(this).addClass("ignoreValid");
		});

    if (valid) {
        jq('#createOfferingButton').removeAttr('disabled');
    }
}