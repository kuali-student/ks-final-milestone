function collapseReq(obj, onload) {
    var height = 23;
    if (onload) {
        obj.removeClass("expanded").addClass("collapsed").css({
            height: height + "px"
        }).children(".header").find(".title").css({
            whiteSpace: "nowrap",
            overflow: "hidden",
            height: height + "px"
        });
    } else {
        obj.removeClass("expanded").addClass("collapsed").animate({
            height: height + "px"
        }, 300, function() {
            jQuery(this).children(".header").find(".title").css({
                whiteSpace: "nowrap",
                overflow: "hidden",
                height: height + "px"
            });
        });
    }
}

function expandReq(obj, onload) {
    var height = obj.data("height");
    if (onload) {
        obj.removeClass("collapsed").addClass("expanded").css({
            height: "auto"
        }).children(".header").find(".title").css({
            whiteSpace: "normal",
            overflow: "auto",
            height: "auto"
        });
    } else {
        obj.removeClass("collapsed").addClass("expanded").animate({
            height: height
        }, 300
        ).children(".header").find(".title").css({
            whiteSpace: "normal",
            overflow: "auto",
            height: "auto"
        });
    }
}

function initAuditActions() {

    jQuery(".requirement").each(function() {
        jQuery(this).data("height", jQuery(this).height());
        if (jQuery(this).is(".Status_OK")) {
            collapseReq(jQuery(this), true);
        } else {
            expandReq(jQuery(this), true);
        }
    });
    jQuery(".requirement > .header > .toggle, .requirement > .header > .title").click(function(e) {
        var target = (e.target) ? e.target.nodeName.toLowerCase() : e.srcElement.nodeName.toLowerCase();
        if (target != "a") {
            var jRequirement = jQuery(this).parents(".requirement");
            if (jRequirement.hasClass("expanded") && !jRequirement.hasClass("collapsed")) {
                collapseReq(jRequirement, false);
            } else { // if (jRequirement.hasClass("collapsed")) {
                expandReq(jRequirement, false);
            }
        }
    });
    jQuery(".control-toolbar #requirement-status").change(function() {
        var data = jQuery(this).val();

        //jQuery(".requirement").each(function() {
        jQuery(".myplan-audit-report .requirement[class*='Status']").not(".Status_NONE").each(function() {

            //if (jQuery(this).hasClass(data) || data == 'all' || jQuery(this).hasClass("Status_NONE") || !jQuery(this).is("div[class*='Status']")) {
            if (data == 'unmet' && jQuery(this).hasClass("Status_OK")) {
                jQuery(this).hide();
            } else {
                jQuery(this).show();
            }
        });

        var jAuditMessage = jQuery(".myplan-status.audit-filtered");
        if (data == "all") {
            jAuditMessage.hide();
        } else {
            jAuditMessage.show();
        }

        jQuery(".section").each(function(){
            var jSectionMessage = jQuery(this).find(".myplan-status.all-reqs-filtered");
            if (jQuery(this).find(".requirement:visible").length > 0) {
                jSectionMessage.hide();
            } else {
                jSectionMessage.show();
            }
        });
    });
}