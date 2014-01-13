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
        jQuery(".ksap-audit-report .requirement[class*='Status']").not(".Status_NONE").each(function() {

            //if (jQuery(this).hasClass(data) || data == 'all' || jQuery(this).hasClass("Status_NONE") || !jQuery(this).is("div[class*='Status']")) {
            if (data == 'unmet' && jQuery(this).hasClass("Status_OK")) {
                jQuery(this).hide();
            } else {
                jQuery(this).show();
            }
        });

        var jAuditMessage = jQuery(".ksap-status.audit-filtered");
        if (data == "all") {
            jAuditMessage.hide();
        } else {
            jAuditMessage.show();
        }

        jQuery(".section").each(function(){
            var jSectionMessage = jQuery(this).find(".ksap-status.all-reqs-filtered");
            if (jQuery(this).find(".requirement:visible").length > 0) {
                jSectionMessage.hide();
            } else {
                jSectionMessage.show();
            }
        });
    });
}

/**
 * Audit Functionality from ksap.widgets.js
 */

// Audit functionality, unused
function expandPlanAuditSummary(selector, expandText, collapseText) {
    if (jQuery(selector).is(":visible")) {
        jQuery(selector).each(function () {
            jQuery(this).attr('style', 'display:none').slideUp(250)
        });
        if (expandText) {
            jQuery('#plan_audit_toggle_link').text(expandText);
        }
    } else {
        jQuery(selector).each(function () {
            jQuery(this).attr('style', 'display:block').slideDown(250)
        });
        if (collapseText) {
            jQuery('#plan_audit_toggle_link').text(collapseText);
        }
    }
}

// Only used in setPendingAudit()
function disabledCheck(disableCompId, disableCompType, condition) {
    if (disableCompType == "radioGroup" || disableCompType == "checkboxGroup") {
        if (condition()) {
            jQuery("input[id^='" + disableCompId + "']").prop("disabled", true);
        }
        else {
            jQuery("input[id^='" + disableCompId + "']").prop("disabled", false);
        }
    }
    else {
        var disableControl = jQuery("#" + disableCompId);
        if (condition()) {
            disableControl.prop("disabled", true);
            disableControl.addClass("disabled");
            if (disableCompType === "actionLink" || disableCompType === "action") {
                disableControl.attr("tabIndex", "-1");
            }
        }
        else {
            disableControl.prop("disabled", false);
            disableControl.removeClass("disabled");
            if (disableCompType === "actionLink" || disableCompType === "action") {
                disableControl.attr("tabIndex", "0");
            }
        }
    }
}

// Audit functionality that will probably be replaced, unused
function getAuditProgram(param, type) {
    var campus;
    switch (parseFloat(jQuery("input[name='" + type + "Audit.campusParam']:checked").val())) {
        case 306:
            campus = "Seattle";
            break;
        case 310:
            campus = "Bothell";
            break;
        case 323:
            campus = "Tacoma";
            break;
        default:
            campus = null;
    }
    if (param == 'id') {
        return jQuery("select[name='" + type + "Audit.programParam" + campus + "']").val();
    } else {
        return jQuery("select[name='" + type + "Audit.programParam" + campus + "'] option:selected").text();
    }
}

var pendingPlanAuditHeadingText = 'We are currently auditing your plan for \'<span class="programName"></span>\'.';
var pendingDegreeAuditHeadingText = 'We are currently auditing your degree for \'<span class="programName"></span>\'.';

var blockPendingAuditStyle = {
    message: '<img src="../ks-ap/images/ajaxAuditRunning32.gif" alt="" class="icon"/><div class="heading"></div>',
    fadeIn: 250,
    fadeOut: 250,
    css: {
        padding: '30px 30px 30px 82px',
        margin: '30px',
        width: 'auto',
        textAlign: 'left',
        border: 'solid 1px #ffd14c',
        backgroundColor: '#fffdd7',
        'border-radius': '15px',
        '-webkit-border-radius': '15px',
        '-moz-border-radius': '15px'
    },
    overlayCSS: {
        backgroundColor: '#fff',
        opacity: 0.85,
        border: 'none',
        cursor: 'wait'
    }
};

var replaceBlockPendingAudit;

// Audit functionality that will probably be replaced (DegreeAuditDetailsUI)
function changeLoadingMessage(selector, programName, auditType) {
    replaceBlockPendingAudit = setInterval(function () {
        setLoadingMessage(selector, programName, auditType)
    }, 100);
}

// Used only changeLoadingMessage
function setLoadingMessage(selector, programName, auditType) {
    if (jQuery(selector + ' div.blockUI.blockMsg.blockElement').length > 0) {
        fnAddLoadingText(selector, programName, auditType);
    }
}

// Used only setLoadingMessage
function fnAddLoadingText(selector, programName, auditType) {
    clearInterval(replaceBlockPendingAudit);
    jQuery(selector + " div.blockUI.blockOverlay").css(blockPendingAuditStyle.overlayCSS);
    jQuery(selector + " div.blockUI.blockMsg.blockElement").html(blockPendingAuditStyle.message).css(blockPendingAuditStyle.css).data("growl","false");
    if (auditType == "plan") {
        jQuery(selector + " div.blockUI.blockMsg.blockElement .heading").html(pendingPlanAuditHeadingText);
    } else {
        jQuery(selector + " div.blockUI.blockMsg.blockElement .heading").html(pendingDegreeAuditHeadingText);
    }
    jQuery(selector + " div.blockUI.blockMsg.blockElement .programName").text(programName);
}

// Audit functionality that will probably be replaced (DegreeAuditConstants)
function removeCookie() {
    jQuery.cookie("ksap_audit_running", null, {expires: new Date().setTime(0)});
}

// Audit functionality that will probably be replaced (DegreeAuditDetailsUI)
function setPendingAudit(obj, minutes) {
    if (jQuery.cookie("ksap_audit_running") == null) {
        var data = {};
        data.expires = new Date();
        data.expires.setTime(data.expires.getTime() + (minutes * 60 * 1000));
        data.programId = getAuditProgram('id', obj.data("audittype"));
        data.programName = getAuditProgram('name', obj.data("audittype"));
        data.recentAuditId = obj.data("recentauditid");
        data.auditType = obj.data("audittype");
        if (typeof data.recentAuditId === 'undefined') data.recentAuditId = '';

        if (data.programId != 'default') {
            changeLoadingMessage('.ksap-audit-report', data.programName, data.auditType);
            jQuery.ajax({
                url: "/student/kr-krad/audit/status",
                data:{"programId":data.programId, "auditId":data.recentAuditId},
                dataType:"json",
                beforeSend:null,
                success:function (response) {
                    if (response.status == "PENDING") {
                        jQuery.cookie('ksap_audit_running', JSON.stringify(data), {expires:data.expires});
                        disabledCheck(obj.attr("id"), 'action', function () {
                            return true;
                        });
                        jQuery.event.trigger('REFRESH_AUDITS', data);
                        setUrlHash('modified', 'true');
                    }
                },
                statusCode: {
                    400 : function() {
                        showGrowl(
                            "400 Bad Request",
                            "Fatal Error");
                    },
                    500: function () {
                        showGrowl(
                            "500 Internal Server Error",
                            "Fatal Error");
                    }
                }
            });
        }
    } else {
        showGrowl("Another audit is currently pending. Please allow audit to complete.", "Running Audit Error", "errorGrowl");
    }
}

// Audit functionality that will probably be replaced (DegreeAuditUI)
function getPendingAudit(id, type) {
    if (jQuery.cookie('ksap_audit_running')) {
        var data = jQuery.parseJSON(decodeURIComponent(jQuery.cookie('ksap_audit_running')));
        if (type == data.auditType) {
            var component = jQuery("#" + id + " .uif-stackedCollectionLayout");
            if (data) {
                var item = jQuery("<div />").addClass("uif-collectionItem pending").html('<img src="../ks-ap/images/ajaxPending16.gif" class="icon"/><span class="title">Auditing <span class="program">' + data.programName + '</span></span>');
                component.prepend(item);
                pollPendingAudit(data.programId, data.recentAuditId, data.auditType);
            }
            if (component.prev(".ksap-emptyCollection").length > 0) {
                component.prev(".ksap-emptyCollection").remove();
            }
        }
    }
}

// Audit functionality that will probably be replaced (DegreeAuditDetailsUI)
function blockPendingAudit(data) {
    var id = "audit_section";
    if (data.auditType == "plan") id = "plan_audit_section";
    var elementToBlock = jQuery("#" + id);
    elementToBlock.block(blockPendingAuditStyle);
    jQuery("#" + id + " div.blockUI.blockMsg.blockElement").data("growl","true");
    if (data.auditType == "plan") {
        jQuery("#" + id + " div.blockUI.blockMsg.blockElement .heading").html(pendingPlanAuditHeadingText);
    } else {
        jQuery("#" + id + " div.blockUI.blockMsg.blockElement .heading").html(pendingDegreeAuditHeadingText);
    }
    jQuery("#" + id + " div.blockUI.blockMsg.blockElement .programName").text(data.programName);
    jQuery("#" + id).on('AUDIT_COMPLETE', function (event, data) {
        window.location.assign(window.location.href.split("#")[0]);
    });
}

// Only used in getPendingAudit
function pollPendingAudit(programId, recentAuditId, auditType) {
    jQuery.ajaxPollSettings.pollingType = "interval";
    jQuery.ajaxPollSettings.interval = 250; // polling interval in milliseconds

    jQuery.ajaxPoll({
        url: "/student/kr-krad/audit/status",
        data: {"programId":programId, "auditId":recentAuditId},
        dataType: "json",
        beforeSend: null,
        successCondition: function(response) {
            return (response.status == 'DONE' || response.status == 'FAILED' || jQuery.cookie("ksap_audit_running") == null);
        },
        success:function (response) {
            var growl = true;
            if (readUrlParam("viewId") == "DegreeAudit-FormView") {
                growl = jQuery(".ksap-audit-report div.blockUI.blockMsg.blockElement").data("growl");
                if (readUrlParam(auditType + "Audit.auditId") != false) jQuery("body").on('AUDIT_COMPLETE', function (event, data) {
                    setUrlParam(auditType + "Audit.auditId", "");
                });
            }
            var title = "Degree Audit";
            if (auditType == "plan") {
                title = "Plan Audit";
            }
            if (jQuery.cookie("ksap_audit_running") == null || response.status == 'FAILED') {
                if (growl) showGrowl("Your " + title + " was unable to complete.", title + " Error", "errorGrowl");
            } else {
                var data = jQuery.parseJSON(decodeURIComponent(jQuery.cookie("ksap_audit_running")));
                if (growl) showGrowl(data.programName + " " + title + " is ready to view.", title + " Completed", "infoGrowl");
            }
            jQuery.cookie("ksap_audit_running", null, {expires:new Date().setTime(0)});
            jQuery.event.trigger("AUDIT_COMPLETE", {"auditType": auditType});
        }
    });
}

// Audit functionality that will probably be replaced (DegreeAuditUI)
function indicateViewingAudit(id, category) {
    var open = false;
    var currentAudit = jQuery("." + category + ".auditHtml .ksap-audit-report");
    var currentAuditId = currentAudit.attr("auditid");

    jQuery("#" + id + " .uif-collectionItem").not(".pending").each(function (index) {
        if (jQuery(this).attr("id").replace("link_", "") == currentAuditId && currentAudit.is(":visible")) {
            if (category == 'degreeAudit') {
                jQuery(this).find(".uif-label label").html("Viewing");
            }
            if (category == 'planAudit') {
                if (index > 1) open = true;
                jQuery(this).addClass("viewing");
            }
        } else {
            if (category == 'degreeAudit') {
                jQuery(this).find(".uif-label label").html("View");
            }
            if (category == 'planAudit') {
                jQuery(this).removeClass("viewing");
            }
        }
    });
    if (open) {
        jQuery("#plan_audit_toggle_link").click();
    }
}

// Degree audit functionality DegreeAuditUI.xml
function openDocument(url) {
    var newUrl;
    if (url.substring(0, 4) == "http") {
        newUrl = url;
    } else {
        newUrl = window.location.protocol + "//" + window.location.host + window.location.pathname.substring(0, window.location.pathname.lastIndexOf("/")) + "/" + url;
    }
    if (newUrl == window.location.href) {
        window.location.reload(true);
    } else {
        window.location.assign(newUrl);
    }
}

// Audit Functionality used only for pollPendingAudit()
function setUrlParam(key, value) {
    var aParams = [];
    if (window.location.search) {
        aParams = window.location.search.replace('?', '').split('&');
    }
    var oParams = {};
    if (aParams.length > 0) {
        jQuery.each(aParams, function (index, value) {
            oParams[value.split('=')[0]] = value.split('=')[1];
        });
        var oTemp = {};
        oTemp[key] = value;
        jQuery.extend(oParams, oTemp);
    } else {
        oParams[key] = value;
    }
    aParams = [];
    for (var key in oParams) {
        if (key != "" && oParams[key] != "") aParams.push(encodeURIComponent(key) + "=" + encodeURIComponent(oParams[key]));
    }
    window.location.replace(window.location.protocol + "//" + window.location.host + window.location.pathname + "?" + aParams.join("&"));
}
// Audit Functionality (DegreeAuditDetailsUI)
function readUrlParam(key) {
    var aParams = window.location.search.replace('?', '').split('&');
    var oParams = {};
    jQuery.each(aParams, function (index, value) {
        oParams[value.split('=')[0]] = value.split('=')[1];
    });
    if (oParams[key]) {
        return decodeURIComponent(oParams[key]);
    } else {
        return false;
    }
}