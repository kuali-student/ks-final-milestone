/**
 * SCRIPT METHODS USED BY MULTIPLE .JS FILES
 */

var tabPanelId = "#course_tabs_tabs";
var activateTabEventNamespace = "leftNavActiveTab";
var leftNavPositioningEventNamespace = "leftNavPositioning";

/**
 * Initialize the Course Details page.
 *
 * The course details page has two modes of operation ... "normal" and Curriculum Specialist (CS). Normal mode has a tab
 * group with the tab panel on the left and a tab for each course details section. CS mode displays all of the sections
 * on a single page and clicking the tab panel scrolls the page to the corresponding section. The
 * initializeForCurriculumSpecialist() function basically overrides the normal tab group functionality. This includes
 * registering a handler for window scroll events so that the active tab will change as the window scrolls, a click handler
 * on the tab panel to scroll the window to the corresponding tab. Unfortunately, these two operations don't play nicely
 * together so the window scroll handler has to be turned off when the click scroll is happening.
 *
 * There is also a window scroll handler to reposition the tab panel when the sticky header changes size. This is active
 * in both modes.
 *
 * The tab panel is initialized to the current panel (meaning the correct item in the tab panel will be active) in the
 * KRAD markup for the tab group. In addition to that CS mode requires a scrollToSection() call to position the page on
 * the correct section.
 *
 * @param isCurriculumSpecialist True if the user is a CS.
 * @param currentSectionId The DOM id of the required tab section.
 */
function onCourseLoad(isCurriculumSpecialist, currentSectionId) {
    /*
     * Handler for fixing nav position when the window is scrolled (because the sticky header changes).
     *
     */
    if (!hasScrollEvent(leftNavPositioningEventNamespace)) {
        jQuery(window).on('scroll.' + leftNavPositioningEventNamespace, handleNavPanelRepositioningOnWindowScroll);
    }

    if (isCurriculumSpecialist) {
        initializeForCurriculumSpecialist(currentSectionId);
    }

    fixLeftNavElementPositioning(true);
}

/**
 * Initialize the Review Course Proposal page.
 */
function onProposalReviewLoad() {
    updateStickyHeaderText();
    /* The window scroll listeners on for the Course Details page will continue to exist when the Review Proposal page
     * is loaded so remove them if they exist.
     */
    if (hasScrollEvent(activateTabEventNamespace)) {
        jQuery(window).off('scroll.' + activateTabEventNamespace);
    }
    if (hasScrollEvent(leftNavPositioningEventNamespace)) {
        jQuery(window).off('scroll.' + leftNavPositioningEventNamespace);
    }

    fixReadOnlyInputSizes();

    /*
     * Remove validation error popups. It doesn't work to simply call .Remove here because it causes an error in the
     * initialization that happens after this method completes. Putting it in ready() executes it near the end.
     */
    jQuery(document).ready(
        function () {
            jQuery("[id$=_control]").RemoveBubblePopup();
        }
    );
}

/**
 * Examines all of the read-only text areas on the page and shrink the height to fit the content.
 */
function fixReadOnlyInputSizes() {
    var textAreaEmptyHeight = 18;
    jQuery(".review_readonly_control").each(function (index) {
        var element = jQuery(this);
        if (element.is("textarea")) {
            element.height(textAreaEmptyHeight);
            if (element.val() != "") {
                // Set the height of the text area to the scroll height.
                element.height(jQuery(element)[0].scrollHeight);
            }
        }
    });
}


var previousAnchorBottom = 0;
/**
 * Do fixed positioning of left nav elements.
 *
 * Some elements are only present for certain proposal doc types, so the presence of each element it checked except the
 * tab panel itself and the elements are positioned to the bottom of the previous element, starting at the bottom of the
 * sticky header.
 */
function fixLeftNavElementPositioning(initial) {
    //  Get the position of the element that the nav components need to align under.
    var anchorElement = jQuery("#KS-CourseView > header.uif-viewHeader-contentWrapper");
    if (anchorElement.length == 0) {
        console.error('Unable to find an anchor element. Nav elements were not positioned correctly.');
        return;
    }

    var anchorBottom = getBottom(anchorElement);
    if (!initial) {
        //  If we are repositioning then see if the view header has moved. If it hasn't then no need to proceed.
        if (anchorBottom != previousAnchorBottom) {
            previousAnchorBottom = anchorBottom;
        } else {
            return;
        }
    }

    var tabListId = "#course_tabs_tabList";

    var tabPanelHeader = jQuery("#course_tabs_header");
    var tabPanel = jQuery(tabListId);
    if (tabPanel.length == 0) {
        console.error('Unable to find the tab panel. Nav elements may not be positioned correctly.');
        return;
    }
    var tabPanelFooter = jQuery("#KS-CourseView-ReviewProposalLink");

    //  If the header text for the tab exists then position it.
    if (tabPanelHeader.length != 0) {
        tabPanelHeader.css({ top:anchorBottom });
        anchorElement = tabPanelHeader;
        anchorBottom = getBottom(anchorElement);
    }

    //  Position the tab panel.
    tabPanel.css({top:anchorBottom});

    //  Position the text under the tab panel if it exists.
    if (tabPanelFooter.length != 0) {
        anchorBottom = getBottom(tabPanel);
        tabPanelFooter.css({ top:anchorBottom });
    }
}

//remove the stacked collection

function removeCurrciulumOversight() {
    var oversightComponent = jQuery("#KS-Course-CurriculumOversight-Section");
    if (oversightComponent != null) {
        oversightComponent.remove();
    }
}

function reDisplayAdministeringOrganization() {
    var administeringOrganizationComponent = jQuery("#administering-organization");
    if (administeringOrganizationComponent.css('display') == "none") {
        administeringOrganizationComponent.show();
    }
}

function reDisplayInstructor() {
    var instructorComponent = jQuery("#instructor-section");
    if (instructorComponent.css('display') == "none") {
        instructorComponent.show();
    }
}

function reDisplayCrosslistedCourse() {
    var crosslistComponent = jQuery("#crosslisted-course");
    if (crosslistComponent.css('display') == "none") {
        crosslistComponent.show();
    }
}

function reDisplayJointlyOfferedCourse() {
    var jointlyOfferedComponent = jQuery("#jointlyOffered-section");
    if (jointlyOfferedComponent.css('display') == "none") {
        jointlyOfferedComponent.show();
    }
}

function reDisplayVersionCode() {
    var versionComponent = jQuery("#version-code");
    if (versionComponent.css('display') == "none") {
        versionComponent.show();
    }
}


/**
 * Checks if a particular scroll event handler has been bound to the window.
 * @param namespace The scroll event namespace.
 * @returns {boolean} True if a scroll event was found with the given namespace, otherwise false.
 */
function hasScrollEvent(namespace) {
    var scrollEvents = jQuery._data(jQuery(window)[0], 'events').scroll;
    var rVal = false;
    jQuery.each(scrollEvents, function (index, event) {
        if (event.namespace == namespace) {
            rVal = true;
            return false; // Break the each loop
        }
    });
    return rVal;
}

/**
 * Determine the position of the bottom of an element.
 * Using .css(top) instead of .offset().top because offset wasn't providing sane results.
 *
 * @param e An element.
 * @returns The bottom position of the given element.
 */
function getBottom(e) {
    return parseInt(e.css("top").replace('px', '')) + e.outerHeight(true);
}

/*
 * Used to keep track of the currently selected tab when scrolling in Curriculum Specialist view.
 */
var focusedTab;

/**
 * Hacks for Curriculum Specialist single-page view.
 */
function initializeForCurriculumSpecialist(currentSectionId) {
    //  Add a CSS class that prevents the tabs from being hidden.
    jQuery("div[data-type='TabWrapper']").addClass('never_hide');

    //  Register a handler for tab clicks.
    jQuery(tabPanelId).on("tabsactivate",
        function (event, ui) {
            //  Checking the event type to make sure it's a click.
            if (typeof event.originalEvent.originalEvent !== 'undefined'
                && event.originalEvent.originalEvent.type == "click") {
                //  Scroll to the section corresponding to the clicked tab.
                scrollToSection("#" + ui.newPanel.attr('id').replace('_tab', ''), true);
            }
        }
    );

    //  Bind window scroll handler for active tab.
    jQuery(window).on('scroll.' + activateTabEventNamespace, handleActiveTabOnWindowScroll);

    /*
     * Scroll to the appropriate section unless it is the top one. Scrolling in that case causes the top part of the
     * header to disappear
     */
    if (currentSectionId && currentSectionId !== "KS-CourseView-CourseInfo-Section") {
        scrollToSection("#" + currentSectionId, true);
    }
}

/**
 * Window scroll handler for tab panel positioning.
 */
function handleNavPanelRepositioningOnWindowScroll() {
    //  Fix the position of the nav elements if the header changes.
    fixLeftNavElementPositioning(false);
}

/**
 * Window scroll handler to change the active tab.
 */
function handleActiveTabOnWindowScroll() {
    /*
     * When the window is scrolled, once a tab has a "focus point" in the window select/activate it.
     * This will cause the 'tabsactivate' handler above to get called.
     */
    jQuery("div[data-type='TabWrapper']").each(function () {
        var tab = this;
        if (isOnFocusPoint(tab) && focusedTab !== tab) {
            focusedTab = tab;
            jQuery(tabPanelId).tabs("select", "#" + tab.id);
            return false;
        }
    });
}

/**
 * Scolls the page to the given section.
 * @param sectionId  The DOM id of the section.
 * @param focus If true gives focus to the first input element in the section.
 */
function scrollToSection(sectionId, focus) {
    //  Remove the window scroll handler. Will re-enable when the animation completes.
    jQuery(window).off('scroll.' + activateTabEventNamespace);

    //  Scroll to the selected tab.
    jQuery('body, html').animate(
        { scrollTop:(jQuery(sectionId).offset().top - jQuery(".uif-viewHeader-contentWrapper").outerHeight(true))},
        {
            duration:750,
            queue:false,
            complete:function () {
                //  Re-bind window scroll handler for active tab.
                jQuery(window).on('scroll.' + activateTabEventNamespace, handleActiveTabOnWindowScroll);
                //  Give focus to the first input widget.
                if (focus) {
                    jQuery(sectionId).find("input[type!='hidden'],textarea,button,select,a").first().focusWithoutScrolling();
                }
            }
        }
    );
}

/**
 * Determine if an element is on a particular point (the focus point).
 * @param e The element to test.
 * @returns {boolean} True is the element is on the focus point. Otherwise, false.
 */
function isOnFocusPoint(e) {
    var docViewTop = jQuery(window).scrollTop();
    var focusPoint = docViewTop + jQuery(".uif-viewHeader-contentWrapper").outerHeight(true);
    var elemTop = jQuery(e).offset().top;
    var elemBottom = elemTop + jQuery(e).outerHeight(true);
    return elemTop <= focusPoint && elemBottom >= focusPoint;
}

/**
 * Give focus to an element without scrolling the page.
 *
 * @returns {jQuery.fn} 'this' for chaining.
 */
jQuery.fn.focusWithoutScrolling = function () {
    var x = window.scrollX, y = window.scrollY;
    this.focus();
    window.scrollTo(x, y);
    return this;
};

/**
 * Enter key causes next action button to fire, so this routine turns the Enter
 * keypress into a Tab keypress, essentially
 */
function tabToNextInput(e) {
    if (e.keyCode === 13) {
        e.preventDefault();
        var inputs = jq("input:visible,select:visible");
        var next = inputs.get(inputs.index(this) + 1);
        if (next) {
            next.focus();
        }
    }
}

function confirmDeletion(msg) {
    var response = confirm(msg);

    if (response == true) {
        submitForm();
    }
}

function confirmDeletionResponse(msg) {
    var response = confirm(msg);

    return response;
}

function confirmDeletion(msg) {
    var response = confirm(msg);

    if (response == true) {
        submitForm();
    }
}

function stepBrowserBack() {
    window.history.back(-2);
}

function showLightbox(componentId, url) {
    // Insert the following css files before the lightbox appearing

    var isPortal = false;
    parent.jQuery('head').find('link').each(function () {
            var link = jQuery(this);
            var href = jQuery(link).attr('href');
            if (href.indexOf('rice-portal/css/portal.css') > -1) {
                link.remove();
                isPortal = true;
                return false;   // exit .each() loop early
            }
        }
    );

    parent.jQuery('head').append('<link href="' + url + '/kr-dev/rice-portal/css/lightbox.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="' + url + '/krad/css/global/fss-reset.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="' + url + '/krad/css/global/fss-layout.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="' + url + '/krad/css/global/fss-text.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="' + url + '/krad/plugins/rice/datatables/jquery.dataTables.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="' + url + '/krad/plugins/rice/datatables/TableTools.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="' + url + '/krad/plugins/fancybox/jquery.fancybox.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="' + url + '/krad/css/global/base.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="' + url + '/krad/css/ks/theme-krad.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="' + url + '/krad/css/ks/fss-theme-krad.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="' + url + '/ks-enroll/css/enroll.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="' + url + '/ks-enroll/css/acal.css" rel="stylesheet" type="text/css">');
    parent.jQuery('head').append('<link href="' + url + '/ks-enroll/css/ks-jquery.css" rel="stylesheet" type="text/css">');

    var overrideOptions = { afterClose:function () {
        // Remove the following css files before the lightbox appearing
        parent.jQuery('link[href="' + url + '/kr-dev/rice-portal/css/lightbox.css"]').remove();
        parent.jQuery('link[href="' + url + '/krad/css/global/fss-reset.css"]').remove();
        parent.jQuery('link[href="' + url + '/krad/css/global/fss-layout.css"]').remove();
        parent.jQuery('link[href="' + url + '/krad/css/global/fss-text.css"]').remove();
        parent.jQuery('link[href="' + url + '/krad/plugins/rice/datatables/jquery.dataTables.css"]').remove();
        parent.jQuery('link[href="' + url + '/krad/plugins/rice/datatables/TableTools.css"]').remove();
        parent.jQuery('link[href="' + url + '/krad/plugins/fancybox/jquery.fancybox.css"]').remove();
        parent.jQuery('link[href="' + url + '/krad/css/global/base.css"]').remove();
        parent.jQuery('link[href="' + url + '/krad/css/ks/theme-krad.css"]').remove();
        parent.jQuery('link[href="' + url + '/krad/css/ks/fss-theme-krad.css"]').remove();
        parent.jQuery('link[href="' + url + '/ks-enroll/css/enroll.css"]').remove();
        parent.jQuery('link[href="' + url + '/ks-enroll/css/acal.css"]').remove();
        parent.jQuery('link[href="' + url + '/ks-enroll/css/ks-jquery.css"]').remove();
        if (isPortal) {
            parent.jQuery('head').append('<link href="' + url + '/rice-portal/css/portal.css" rel="stylesheet" type="text/css">');
        }
    }};
    showLightboxComponent(componentId, overrideOptions);
}

function setupCharCounters() {
    jQuery(".cm-charcount-max-24").jqEasyCounter({'maxChars':24, 'maxCharsWarning':14, 'msgTextAlign':'left'});
    jQuery(".cm-charcount-max-255").jqEasyCounter({'maxChars':255, 'maxCharsWarning':220, 'msgTextAlign':'left'});
    jQuery(".cm-charcount-max-500").jqEasyCounter({'maxChars':500, 'maxCharsWarning':450, 'msgTextAlign':'left'});
    jQuery(".cm-charcount-max-1000").jqEasyCounter({'maxChars':1000, 'maxCharsWarning':950, 'msgTextAlign':'left'});
}

/*Learning Objective functionality*/

function moveLo(button, collectionGroupId, controllerMethod) {
    performLOToolbarAction(button, collectionGroupId, controllerMethod);
    retrieveComponent(collectionGroupId, controllerMethod);
}

function performLOToolbarAction(button, collectionGroupId, controllerMethod) {
    var id = button.id;
    var lineSuffixIndex = id.lastIndexOf("_line");
    var lineNumberIndex = lineSuffixIndex + "_line".length;
    var index = id.substr(lineNumberIndex);
    /* Set the 'selected' property on the LoItem */
    jQuery('#KS-LoDisplayInfoWrapper-selected_line' + index + '_control').prop('value', true);
}

function applyIndentationStyling() {
    jQuery(".uif-collectionItem[data-parent='LearningObjective-CollectionSection']").each(function (index) {
        var indentString = jQuery('#KS-LoDisplayInfoWrapper-indentLevel_line' + index + '_control').prop('value');
        var indentLevel = parseInt(indentString);

        var collectionItem = jQuery(this);
        collectionItem.removeClass("cm-lo-indent-0 cm-lo-indent-50 cm-lo-indent-100 cm-lo-indent-150 cm-lo-indent-200 cm-lo-indent-250 cm-lo-indent-300 cm-lo-indent-350 cm-lo-indent-400 cm-lo-indent-450 cm-lo-indent-500");
        collectionItem.addClass(getIndentationClass(indentLevel));
    });
}

function getIndentationClass(indentLevel) {
    var indentationClass;
    switch (indentLevel) {
        case 0:
            indentationClass = "cm-lo-indent-0";
            break;
        case 1:
            indentationClass = "cm-lo-indent-50";
            break;
        case 2:
            indentationClass = "cm-lo-indent-100";
            break;
        case 3:
            indentationClass = "cm-lo-indent-150";
            break;
        case 4:
            indentationClass = "cm-lo-indent-200";
            break;
        case 5:
            indentationClass = "cm-lo-indent-250";
            break;
        case 6:
            indentationClass = "cm-lo-indent-300";
            break;
        case 7:
            indentationClass = "cm-lo-indent-350";
            break;
        case 8:
            indentationClass = "cm-lo-indent-400";
            break;
        case 9:
            indentationClass = "cm-lo-indent-450";
            break;
        case 10:
            indentationClass = "cm-lo-indent-500";
            break;
    }
    return indentationClass;
}

function showHideCreateCourseOptionalElements() {
    var showingAll = "Showing required and optional fields";
    var showAll = "Show all fields."
    var showingRequired = "Showing only required fields.";
    var showRequired = "Show only required fields.";

    var actualShowMsg = jQuery("#Create-CourseView-Admin-Message").text();

    if (actualShowMsg != null && actualShowMsg == showingRequired) {    // display all
        jQuery("#Create-CourseView-Admin-Message").text(showingAll);
        jQuery("#Create-CourseView-Admin-Message-expand-optional-link").text(showRequired);
        jQuery(".admin-not-required-field").show();
        jQuery(".hide-when-show-all-fields").hide();
    } else {
        jQuery("#Create-CourseView-Admin-Message").text(showingRequired);
        jQuery("#Create-CourseView-Admin-Message-expand-optional-link").text(showAll);
        jQuery(".admin-not-required-field").hide();
        jQuery(".hide-when-show-all-fields").show();
    }
    jQuery("#CreateCourseProposalTitleInputField_control").focus();

}

function setupCharCoutnersForVersionCodes() {
    jQuery(".cm-charcount-max-150").jqEasyCounter({'maxChars':150, 'maxCharsWarning':125, 'msgTextAlign':'left'});
}


function createCourseShowHideObjectiveElements(hideId, showId) {
    jQuery("#" + hideId).hide();
    jQuery("#" + showId).show();

}

function updateStickyHeaderText() {
    jQuery("#KS-CourseView").find('.uif-viewHeader-supportTitle').text("Review Proposal");
}

/**
 * This method retrieves/refreshes the outcome component sothat the result value clears out for the specific
 * index row
 * @param index
 */
function refreshOutcome(index) {

    var successFunction = function () {
        var selected = jQuery('#typeKey_line' + index + '_control').val()
        if (selected != null && selected.length > 2) {
            jQuery('#creditValue_line' + index + '_control').focus();
        } else {
            jQuery('#outcome-addline').focus();
        }
    };
    retrieveComponent('KS-CourseView-CourseLogisticsPage-Outcome-Widgets', 'refreshCourseLogistics', successFunction, {outComeIndex:index});

}

function compareSubjectCodeInput(value, element) {
    var isValid;
    if (value == null || value.length < 2) {
        isValid = false;
    }

    var successFunction = function (data) {
        if (data == null || data.resultData == null || data.resultData.length != 1) {
            isValid = false;
        } else {
            jQuery("#" + element.id).attr('value', data.resultData[0].value);
            isValid = true;
            if (element.id.indexOf('KS-SubjectArea-Field') == 0) {
                retrieveComponent('KS-Course-CurriculumOversight-Section', 'refreshOversightSection');
            }
        }
    };

    compareInputWithAutosuggestFromAjax(value, element, successFunction);

    return isValid;
}

jQuery.validator.addMethod("validSubjectCode",
    function (value, element) {
        return this.optional(element) || compareSubjectCodeInput(value, element);
    }, "Subject code is invalid")

jQuery.validator.addMethod("validCourseCode",
    function (value, element) {
        return this.optional(element) || compareCourseCode(value, element);
    }, "Course code is invalid")

jQuery.validator.addMethod("validInstructorNameAndID",
    function (value, element) {
        return this.optional(element) || compareInstructorNameInput(value, element);
    }, "Instructor name/ID combo is invalid")


/**
 * This method validates on blur from the joint course code field. This validates whether the user
 * entered a valid course code or not.
 *
 * @param value
 * @param element
 * @returns {*}
 */
function compareCourseCode(value, element) {
    var isValid;
    if (value == null || value.length < 2) {
        return false;
    }

    var successFunction = function (data) {
        if (data == null || data.resultData == null) {
            isValid = false;
        } else {
            for (var i = 0; len = data.resultData.length, i < len; i++) {
                if (data.resultData[i].value == value) {
                    isValid = true;
                    break;
                }
            }
        }
    };

    compareInputWithAutosuggestFromAjax(value, element, successFunction);

    return isValid;
}

/*Compare the input instructor from the autofill suggest results: data. If the input is in the result data
 it is valid input instructor */
function compareInstructorNameInput(value, element) {
    var isValid = false;
    if (value == null || value.length < 6) {
        return isValid;
    }
    var queryVal = value.split(',')[0];

    var successFunction = function (data) {
        var lastName = value.split(',')[0];
        var restVal = value.split(',')[1];
        var firstName = '';
        var nameID = '';
        if (restVal != null) {
            firstName = restVal.split('(')[0];
            nameID = restVal.split('(')[1];
        }
        if (data == null || data.resultData == null) {
            isValid = false;
        } else {
            for (var i = 0; len = data.resultData.length, i < len; i++) {
                var correctName = data.resultData[i].displayName;
                if (correctName.indexOf(lastName) >= 0 && correctName.indexOf(firstName) > 1 && correctName.indexOf(nameID) > 2) {
                    isValid = true;
                    break;
                }
            }
        }
    };

    compareInputWithAutosuggestFromAjax(queryVal, element, successFunction);

    return isValid;
}

jQuery.validator.addMethod("validOrganizationName",
    function (value, element) {
        return this.optional(element) || compareOrganizationNameInput(value, element);
    }, "Invalid administering organization")

/*Compare the input Organization Name from the autofill suggest results: data. If the input is in the result data
 it is valid input Organization */
function compareOrganizationNameInput(value, element) {
    var isValid;
    if (value == null || value.length < 2) {
        return false;
    }

    var successFunction = function (data) {
        if (data == null || data.resultData == null) {
            isValid = false;
        } else {
            for (var i = 0; len = data.resultData.length, i < len; i++) {
                if (data.resultData[i].organizationName == value) {
                    isValid = true;
                    break;
                }
            }
        }
    };

    compareInputWithAutosuggestFromAjax(value, element, successFunction);

    return isValid;
}

jQuery.validator.addMethod("validDurationTypeAndCountInput",
    function (value, element) {
        var durationType = jQuery('#KS-DurationTypeDropDown_control').val();
        var durationCount = jQuery('#KS-DurationTimeQuantity-Field_control').val();

        if (durationCount == '') {
            if (durationType != '') {
                return false;
            } else {
                return true;
            }
        }

        return durationType != '' && durationCount != '';
    }, "Must provide a duration type and a duration count")

function durationTypeOnBlur() {
    var durationType = jQuery('#KS-DurationTypeDropDown_control').val();
    var durationCount = jQuery('#KS-DurationTimeQuantity-Field_control').val();
    if (durationType == '' && durationCount == '') {
        return;
    }
    if (event.relatedTarget == null || event.relatedTarget.id != "KS-DurationTimeQuantity-Field_control") {
        validateFieldValue(jQuery("#KS-DurationTypeDropDown_control"));
        validateFieldValue(jQuery("#KS-DurationTimeQuantity-Field_control"));
        return;
    }
    return;
}

function durationCountOnBlur() {
    validateFieldValue(jQuery("#KS-DurationTimeQuantity-Field_control"));
    validateFieldValue(jQuery("#KS-DurationTypeDropDown_control"));
    return;
}

function showHideReviewProposalErrorFields(sectionId) {
    var hideMissed = "Hide missing-fields indicator.";
    var showMissed = "Show what's missing.";

    var actualShowMsg = jQuery("#ReviewProposal-Error-Message-expand-optional-link").text();

    if (actualShowMsg != null && actualShowMsg.toString().trim() == showMissed) {
        jQuery("#ReviewProposal-Error-Message-expand-optional-link").text(hideMissed);
        /* highlight the missing element rows */
        highlightMissingElements(sectionId, true);
    } else {
        jQuery("#ReviewProposal-Error-Message-expand-optional-link").text(showMissed);
        highlightMissingElements(sectionId, false);
    }
    jQuery("#CourseInfo-Review-Edit-link").focus();

}

/*Tried using jQuery("#kualiForm").validate().resetForm() to remove the validation results so that we can have the
 page contents displayed without the validation results. But There is no effect after ran the method.
 Then looping through all text areas and according to it has value or not to toggle the default border from the
 validated result border.
 */
function highlightMissingElements(sectionId, showError) {
    var whiteBorderStyle = "border: rgb(255,255,255) !important;";
    var style = jQuery('#' + sectionId).find('table td textarea').attr("style");
    var hasBorderStyle = style.indexOf(whiteBorderStyle);
    var originalStyle = " ";

    if (hasBorderStyle >= 0) {
        originalStyle = style.replace(whiteBorderStyle, "");
    } else {
        originalStyle = style;
    }

    var whiteBorder = whiteBorderStyle.concat(originalStyle);

    if (showError) {
        jQuery('#' + sectionId).find('table td textarea').each(function (index) {
            var inputLength = 0;
            var inputVal = jQuery('#' + this.id).val();
            if (inputVal != null) {
                inputLength = inputVal.length;
            }
            if (inputLength < 1) {
                jQuery(this).attr("style", originalStyle);
                jQuery('#' + jQuery(this).GetBubblePopupID()).removeClass("alwaysHide");
            }
        });
    } else {
        jQuery('#' + sectionId).find('table td textarea').each(function (index) {
            var inputLength = 0;
            var inputVal = jQuery('#' + this.id).val();
            if (inputVal != null) {
                inputLength = inputVal.length;
            }
            if (inputLength < 1) {
                jQuery(this).attr("style", whiteBorder);
                jQuery('#' + jQuery(this).GetBubblePopupID()).addClass("alwaysHide");
            }
        });
    }
}

jQuery.validator.addMethod("rangeMaxMinCheck",
    function (value, element) {
        return this.optional(element) || verifyMaxAndMinInput(value, element);
    },
    "Invalid range"
);

function verifyMaxAndMinInput(value, element) {
    var res = value.split("-");
    if (res.length != 2) {
        return false;
    }
    return res[0] != '' && res[1] != '' && res[0] < res[1];

}


function compareInputWithAutosuggestFromAjax(value, element, successFunction) {
    var queryData = {};

    var qFieldId = jQuery('#' + element.id).data('control_for');
    queryData.methodToCall = 'performFieldSuggest';
    queryData.ajaxRequest = true;
    queryData.ajaxReturnType = 'update-none';
    queryData.formKey = jQuery("input[name='" + kradVariables.FORM_KEY + "']").val();
    queryData.queryTerm = value;
    queryData.queryFieldId = qFieldId;

    jQuery.ajax({
        url:jQuery("form#kualiForm").attr("action"),
        dataType:"json",
        async:false,
        beforeSend:null,
        complete:null,
        error:null,
        data:queryData,
        success:successFunction
    });

}

function refreshEndTerm() {
    if (jQuery('#CourseView-ActiveDatesPage-PilotCourse_control').prop('checked')) {
        retrieveComponent('CourseView-ActiveDatesPage-EndTerm');
    }
}