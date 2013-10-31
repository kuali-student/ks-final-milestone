
function replaceDisclosureWithText(elementId, text) {
	var href = jQuery("#" + elementId + " a[data-role='disclosureLink']");
	//jQuery("#u84_line1 a[data-role='disclosureLink']")
	
	
	href.toggle();
	href.parent().html(text);	
}
