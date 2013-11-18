// Supporting scripts for the Shopping Cart dialog

function cartPrimarySelect(uniqueId) {
	var clickedRadio = jQuery("#sb_cart_primary_radio_"+uniqueId+"_radio");
	var primaryOptions = clickedRadio.parents(".ksap-sb-cart-primary-options");
	var allRadios = primaryOptions.find(".ksap-sb-cart-primary-activity-radio");
	allRadios.each(function() {
		var _ = jQuery(this);
		var _radio = _.children("input");
		var _uid = _.data("uid");
		var primaryHeader = jQuery("#sb_cart_primary_activity_header_"+_uid);
		var secondaryOptions = jQuery("#sb_cart_secondary_options_"+_uid);
		if (_radio.is(":checked")) {
			primaryHeader.addClass("ksap-sb-cart-primary-selected");
			primaryHeader.removeClass("ksap-sb-cart-primary-deselected");
			secondaryOptions.addClass("ksap-sb-cart-secondary-selected");
			secondaryOptions.removeClass("ksap-sb-cart-secondary-deselected");
		} else {
			primaryHeader.removeClass("ksap-sb-cart-primary-selected");
			primaryHeader.addClass("ksap-sb-cart-primary-deselected");
			secondaryOptions.removeClass("ksap-sb-cart-secondary-selected");
			secondaryOptions.addClass("ksap-sb-cart-secondary-deselected");
		}
	});
}

function cartSecondarySelect(uniqueId) {
	var clickedRadio = jQuery("#sb_cart_secondary_radio_"+uniqueId+"_radio");
	clickedRadio.prop("checked", true);
	var secondaryOptions = clickedRadio.parents(".ksap-sb-cart-secondary-options");
	var allGroups = secondaryOptions.find(".ksap-sb-cart-secondary-activity-group");
	allGroups.each(function() {
		var _ = jQuery(this);
		var _radio = _.find("input");
		if (_radio.is(":checked")) {
			_.addClass("ksap-sb-cart-secondary-activity-group-selected");
			_.removeClass("ksap-sb-cart-secondary-activity-group-deselected");
		} else {
			_.removeClass("ksap-sb-cart-secondary-activity-group-selected");
			_.addClass("ksap-sb-cart-secondary-activity-group-deselected");
		}
	});
}

function ksapCartSubmitDialog(e) {
	var button = jQuery(e.currentTarget);

	button.block({
		message : '<img src="../ks-ap/images/btnLoader.gif"/>',
		css : {
			width : '100%',
			border : 'none',
			backgroundColor : 'transparent'
		},
		overlayCSS : {
			backgroundColor : '#fff',
			opacity : 0.6,
			padding : '0px 1px',
			margin : '0px -1px'
		}
	});

	var form = jQuery("#popupForm");
	form.ajaxSubmit({
		data : ksapAdditionalFormData(),
		dataType : 'json',
		success : ksapCartUpdateEvent,
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "parsererror")
				textStatus = "JSON Parse Error";
			showGrowl(errorThrown, jqXHR.status + " " + textStatus);
			fnClosePopup();
		},
		complete : function() {
			button.unblock();
		}
	});

}

function ksapCartUpdateEvent(response, textStatus, jqXHR) {
	if (response.multi) {

		for (var key in response.cartRequests) {
			var req = response.cartRequests[key];
			var res = jQuery("#sb_cart_request_"+req.uniqueId+"_span");
			if (req.message != null) {
				res.text(req.message);
				res.addClass("ksap-sb-cart-result-"+(req.error?"error":"info"));
				res.show();
			}
		}

		if (!response.error || true) {
			jQuery("#sb_cart_update_cart_button").hide();
			jQuery("#sb_cart_go_to_cart_button").show();
		}

	} else if (response.success) {
		for (var key in response) {
			if (!response.hasOwnProperty(key))
				continue;
			var data = response[key];

			if (key == "PLAN_ITEM_ADDED") {
				ksapPlannerAddPlanItem(data);

			} else if (key == "PLAN_ITEM_DELETED") {
				ksapPlannerRemovePlanItem(data);

			} else if (key == "PLAN_ITEM_UPDATED") {
				ksapPlannerUpdatePlanItem(data);

			} else if (key == "TERM_NOTE_UPDATED") {
				ksapPlannerUpdateTermNote(data);

			} else if (key == "UPDATE_NEW_TERM_TOTAL_CREDITS"
					|| key == "UPDATE_OLD_TERM_TOTAL_CREDITS") {
				ksapPlannerUpdateCredits(data);
			}
		}
		
				// <bean parent="Uif-Message" p:id="sb_cart_request_@{#line.uniqueId}" p:hidden="true" />

		if (response.message != null)
			showGrowl(response.message);
		
		fnClosePopup();

	} else {
		var feedback = jQuery("#popupForm").find(".ksap-feedback");
		feedback.empty().append("<span/>").text(response.message);
		feedback.addClass("error");
		feedback.removeClass("success");
		feedback.show();
	}
}
