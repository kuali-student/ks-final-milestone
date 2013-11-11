// KSAP 0.7.5 refactored planner scripts

function ksapLoadPlannerItems(imageUrl) {
	myplanRetrieveComponent(
			'planner_lookup_wrapper',
			'PlannerLoad-FormView',
			'load',
			'planner',
			ksapAdditionalFormData({}),
			null,
			{
				message : '<p><img src="'
						+ imageUrl
						+ 'ajaxAuditRunning32.gif" alt="loading..." /></p><p>Please wait while we are retrieving your plan...</p>',
				fadeIn : 0,
				fadeOut : 0
			});
}

function ksapInitializePlannerItems(pageSize) {
	var detailList = jQuery('#planner_courses_detail_list');
	var detailCols = detailList.find('ul:not(.errorLines) li');
	if (detailCols.length > 0) {
		var iStart = 0;
		if (readUrlHash('planView')) {
			iStart = parseFloat(readUrlHash('planView'));
		} else if (detailList.length > 0) {
			iStart = jQuery("#planner_courses_detail_page").data("focustermindex");
		}

		detailList.jCarouselLite({
			btnNext : '.myplan-carousel-next',
			btnPrev : '.myplan-carousel-prev',
			scroll : pageSize,
			visible : pageSize,
			start : iStart,
			afterEnd : function(a) {
				ksapPlannerUpdateTitle(a);
				var planView = jQuery(a[0]).index();
				if (planView == 0 && a.length < pageSize) {
					planView = a.length - pageSize;
				}
				setUrlHash('planView', planView);
			},
			initCallback : function(a) {
				ksapPlannerUpdateTitle(a);
				jQuery.unblockUI();
			}
		});
	}
	ksapPlannerCreateTooltips();
}

function ksapPlannerUpdateTitle(a) {
	var aFirst = jQuery.trim(jQuery(a[0]).find(
			"div:hidden[id^='plan_base_atpId']").text());
	var aLast = jQuery.trim(jQuery(a[a.length - 1]).find(
			"div:hidden[id^='plan_base_atpId']").text());
	jQuery("#planner_courses_detail .myplan-plan-header")
			.html(aFirst + ' - ' + aLast);
}

function ksapPlannerOpenDialog(pageId, action, methodToCall, target, e) {
	var t = jQuery(target);
	var retrieveData = {
		action : action,
		methodToCall : methodToCall,
		learningPlanId : t.data('learningplanid'),
		termId : t.data('termid'),
		planItemId : t.data('planitemid'),
		courseId : t.data('courseid'),
		backup : t.data('backup'),
		uniqueId : t.data('uniqueid'),
		pageId : pageId + "_page"
	};
	var popupOptions = {
		tail : {
			hidden : true
		},
		align : 'top',
		close : true
	};
	jQuery("#popupForm").remove();
	fnClosePopup();
	openPopup(pageId + "_inner", retrieveData, action, {}, popupOptions, e);
	var form = jQuery("#popupForm");
	form.attr("accept-charset", "UTF-8");
}

function ksapPlannerSubmitDialog(e) {
	var button = jQuery(e.currentTarget);

	button.block({
		message : '<img src="../ks-myplan/images/btnLoader.gif"/>',
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
		success : ksapPlannerUpdateEvent,
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

function ksapPlannerUpdateType(backup, target, e) {
	var t = jQuery(target);
	var retrieveData = ksapAdditionalFormData({
			methodToCall : "updatePlanItemType",
			learningPlanId : t.data('learningplanid'),
			termId : t.data('termid'),
			planItemId : t.data('planitemid'),
			courseId : t.data('courseid'),
			uniqueId : t.data('uniqueid'),
			backup : backup,
		});
	var form = jQuery("<form/>")
			.attr("action", "planner")
			.attr("method", "post");
	form.ajaxSubmit({
		data : retrieveData,
		dataType : 'json',
		success : ksapPlannerUpdateEvent,
		error : function(jqXHR, textStatus, errorThrown) {
			if (textStatus == "parsererror")
				textStatus = "JSON Parse Error";
			showGrowl(errorThrown, jqXHR.status + " " + textStatus);
			fnClosePopup();
		},
		complete : function() {
			form.remove();
		}
	});
}

function ksapPlannerUpdateEvent(response, textStatus, jqXHR) {
	if (response.success) {
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

		if (response.message != null) {
			showGrowl(response.message);
		}
		fnClosePopup();

	} else {
		var feedback = jQuery("#popupForm").find(".myplan-feedback");
		feedback.empty().append("<span/>").text(response.message);
		feedback.addClass("error");
		feedback.removeClass("success");
		feedback.show();
	}
}

function ksapPlannerAddPlanItem (data) {
    var item = jQuery("#planner_item_template").html();
    for (var key in data)
		if (data.hasOwnProperty(key))
			item = eval("item.replace(/__KSAP__"+key.toUpperCase()+"__/gi,'"+data[key]+"')");
    item = item.replace(/id=\"(u\d+)\"/gi,"id=\""+data.uid+"_$1\"");
    var termUid = data.termId.replace(/\./g,'-');
    var itemElement = jQuery("<div/>").html(item);
    itemElement.find("input[data-for='"+data.uid+"']")
		.removeAttr("script")
		.attr("name", "script");
    itemElement
    		.attr("id", data.uid+"_wrap")
    		.attr("class", "uif-group uif-boxGroup uif-verticalBoxGroup ks-plan-Bucket-collection uif-collectionItem uif-boxCollectionItem uif-boxLayoutHorizontalItem")
    		.prependTo("." + termUid + ".myplan-term-" + data.type + " .uif-stackedCollectionLayout")
    		.css({backgroundColor:"#ffffcc"})
    		.hide()
    		.fadeIn(250, function() {
    	        var bucket = jQuery(".myplan-term-" + data.type + "." + termUid);
    	        var unitcell = bucket.find(".myplan-carousel-term-total");
    	    	unitcell.addClass("ks-plan-Bucket-footer-show");
    	    	unitcell.removeClass("ks-plan-Bucket-footer-hide");
    	    	var nocourses = bucket.find(".no-courses-cell");
    	    	nocourses.addClass("ks-plan-Bucket-footer-hide");
    	    	nocourses.removeClass("ks-plan-Bucket-footer-show");
    		})
    		.animate({backgroundColor:"#ffffff"}, 1500, function() {
    			runHiddenScripts(data.uid);
    		});
}

function ksapPlannerUpdatePlanItem (data) {
    var item = jQuery("#" + data.uniqueId);
    item.find(".credit span").text(data.credit);
}

function ksapPlannerRemovePlanItem (data) {
    jQuery("#" + data.uid).fadeOut(250, function(){
        jQuery(this).remove();
        var bucket = jQuery(".myplan-term-" + data.type + "." + data.termId);
        var unitcell = bucket.find(".myplan-carousel-term-total");
        if (bucket.find(".ks-plan-Bucket-item").length == 0) {
        	unitcell.removeClass("ks-plan-Bucket-footer-show");
        	unitcell.addClass("ks-plan-Bucket-footer-hide");
        	var nocourses = bucket.find(".no-courses-cell");
        	nocourses.removeClass("ks-plan-Bucket-footer-hide");
        	nocourses.addClass("ks-plan-Bucket-footer-show");
        }
    });
}

function ksapPlannerUpdateCredits (data) {
    var planbucket = jQuery(".myplan-term-planned." + data.termId);
    var planunitcell = planbucket.find(".myplan-carousel-term-total");
    planunitcell.find(".credits span.uif-message").fadeOut(250, function() {
        jQuery(this).text(data.totalCredits).fadeIn(250);
    });

    var cartbucket = jQuery(".myplan-term-cart." + data.termId);
    var cartunitcell = cartbucket.find(".myplan-carousel-term-total");
    cartunitcell.find(".credits span.uif-message").fadeOut(250, function() {
        jQuery(this).text(data.cartCredits).fadeIn(250);
    });
}

function ksapPlannerUpdateTermNote (data) {
    jQuery("#"+data.uniqueId+"_termnote_message_span").fadeOut(250, function() {
    	if (data.termNote == null || data.termNote == "") {
            jQuery(this).text("No term notes").addClass("ks-plan-TermNote-input-empty").fadeIn(250);
    	} else {
            jQuery(this).text(data.termNote).removeClass("ks-plan-TermNote-input-empty").fadeIn(250);
    	}
    });
}

function ksapPlannerCreateTooltips() {
	var options = {
	        position: 'top',
	        align: 'left',
	        alwaysVisible: false,
	        tail: {
	            align: 'left',
	            hidden: false
	        },
	        themeMargins: {total:'22px', difference:'10px'},
	        themeName: 'black',
	        selectable: true,
	        width: '250px',
	        closingDelay: 500,
	        manageMouseEvents: false
	    };
	jQuery(".ks-Plan-helpimage").hover(function () {
		var t = jQuery(this);
        if (!t.IsBubblePopupOpen()) {
        	if (!t.hasClass("uif-tooltip")) {
        		t.addClass("uif-tooltip");
        	    initBubblePopups();
        	}
        	var tip = jQuery("<div/>");
        	tip.addClass("ks-Tooltip");
        	tip.text(t.data("helptext"));
        	
            t.SetBubblePopupOptions(options, true);
            t.SetBubblePopupInnerHtml(jQuery("<div/>").append(tip).html(), true);
            t.ShowBubblePopup();
        }
    }, function (event) {
		var t = jQuery(this);
	    if (t.IsBubblePopupOpen()) {
	    	t.HideBubblePopup();
	    }
    });
}

