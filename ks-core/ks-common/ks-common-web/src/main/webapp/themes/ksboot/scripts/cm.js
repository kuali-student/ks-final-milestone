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

    //  Removed the non-breaking spaces from <pre/>s
    common.removeNonBreakingSpacesFromPreTags();

    fixReadOnlyInputSizes();

    fixComparisonDataSizes();
}

/**
 * TODO: Remove this method once we upgraded to rice 2.5 (KSCM-2650). Also, we need to find a way to
 * not load the bubble popup here as we dont want that here from review page.
 *
 * @param validate
 */
function setupReviewPage(validate) {
    time(true, "page-setup");

    dirtyFormState.resetDirtyFieldCount();

    //if we are skipping this page setup, reset the flag, and return (this logic is for redirects)
    if (skipPageSetup) {
        skipPageSetup = false;
        return;
    }

    // update the top group per page
    var topGroupUpdateDiv = jQuery("#" + kradVariables.TOP_GROUP_UPDATE);
    var topGroupUpdate = topGroupUpdateDiv.find(">").detach();
    if (topGroupUpdate.length && !initialViewLoad) {
        jQuery("#Uif-TopGroupWrapper >").replaceWith(topGroupUpdate);
    }
    topGroupUpdateDiv.remove();

    // update the view header per page
    var headerUpdateDiv = jQuery("#" + kradVariables.VIEW_HEADER_UPDATE);
    var viewHeaderUpdate = headerUpdateDiv.find(".uif-viewHeader-contentWrapper").detach();
    if (viewHeaderUpdate.length && !initialViewLoad) {
        var currentHeader = jQuery(".uif-viewHeader-contentWrapper");
        if (currentHeader.data("offset")) {
            viewHeaderUpdate.data("offset", currentHeader.data("offset"));
        }
        jQuery(".uif-viewHeader-contentWrapper").replaceWith(viewHeaderUpdate);
        stickyContent = jQuery("[data-sticky='true']:visible");
    }
    headerUpdateDiv.remove();

    originalPageTitle = document.title;

    setupImages();

    //reinitialize sticky footer content because page footer can be sticky
    jQuery("[data-role='Page']").on(kradVariables.EVENTS.ADJUST_STICKY, function(){
        stickyFooterContent = jQuery("[data-sticky_footer='true']");
        initStickyFooterContent();
        handleStickyFooterContent();
        initStickyContent();
    });

    // Initialize global validation defaults
    if (groupValidationDefaults == undefined || fieldValidationDefaults == undefined) {
        groupValidationDefaults = jQuery("[data-role='View']").data(kradVariables.GROUP_VALIDATION_DEFAULTS);
        fieldValidationDefaults = jQuery("[data-role='View']").data(kradVariables.FIELD_VALIDATION_DEFAULTS);
    }

    if (actionDefaults == undefined) {
        actionDefaults = jQuery("[data-role='View']").data(kradVariables.ACTION_DEFAULTS);
    }

    //Reset summary state before processing each field - summaries are shown if server messages
    // or on client page validation
    messageSummariesShown = false;

    //flag to turn off and on validation mechanisms on the client
    validateClient = validate;

    // select current page
    var pageId = getCurrentPageId();

    jQuery("ul.uif-navigationMenu").selectMenuItem({selectPage: pageId});
    jQuery("ul.uif-tabMenu").selectTab({selectPage: pageId});

    // update URL to reflect the current page
    updateRequestUrl(pageId);

    prevPageMessageTotal = 0;
    //skip input field iteration and validation message writing, if no server messages
    var hasServerMessagesData = jQuery("[data-role='Page']").data(kradVariables.SERVER_MESSAGES);
    if (hasServerMessagesData) {
        //Handle messages at field, if any
        jQuery("div[data-role='InputField']").each(function () {
            var id = jQuery(this).attr('id');
            handleMessagesAtField(id, true);
        });

        //Write the result of the validation messages
        writeMessagesForPage();
        messageSummariesShown = true;
    }

    //focus on pageValidation header if there are messages on this page
    if (jQuery(".uif-pageValidationHeader").length) {
        jQuery(".uif-pageValidationHeader").focus();
    }

    setupValidator(jQuery('#kualiForm'));

    jQuery(".required").each(function () {
        jQuery(this).attr("aria-required", "true");
    });

    jQuery(document).trigger(kradVariables.VALIDATION_SETUP_EVENT);

    pageValidatorReady = true;

    jQuery(document).trigger(kradVariables.PAGE_LOAD_EVENT);

    jQuery.watermark.showAll();

    time(false, "page-setup");
}

/**
 *  Fix the width of the comparison data to match the size of the read only input fields if it exists.
 */
function fixComparisonDataSizes() {
     //  This will have to be reworked for use outside of review course proposal.
     var w = jQuery("#CM-ViewCourseView-CourseInfo-Course-Titlenew_control").width();
    if ( w != null && w != 0 ) {
         jQuery("pre").each(function (index) {
             jQuery(this).width(w + "px");
         });
    }
}

/**
 * Examines all of the read-only text areas on the page and shrink the height to fit the content.
 */
function fixReadOnlyInputSizes() {
    var textAreaEmptyHeight = 18;
    jQuery(".cm_readonly_control").each(function (index) {
        var element = jQuery(this);
        if (element.is("textarea")) {
            element.height(textAreaEmptyHeight);
            if (element.val() != "" && jQuery(element)[0].scrollHeight != 0) {
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
    var anchorElement = jQuery("#CM-Proposal-Course-Create-View > header");
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

    var tabPanelHeader = jQuery("#CM-Proposal-Course-View-TabHeader");
    var tabPanel = jQuery(tabListId);
    if (tabPanel.length == 0) {
        console.error('Unable to find the tab panel. Nav elements may not be positioned correctly.');
        return;
    }
    var tabPanelFooter = jQuery("#CM-Proposal-Course-View-ReviewProposalLink");

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
    var oversightComponent = jQuery("#CM-Proposal-Course-Governance-CurriculumOversight-Section");
    if (oversightComponent != null) {
        oversightComponent.remove();
    }
}

function reDisplayAdministeringOrganization() {
    var administeringOrganizationComponent = jQuery("#CM-Proposal-Course-Governance-AdministeringOrganization");
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
    var crosslistComponent = jQuery("#CM-Proposal-Course-CourseInfo-CrossList");
    if (crosslistComponent.css('display') == "none") {
        crosslistComponent.show();
    }
}

function reDisplayJointlyOfferedCourse() {
    var jointlyOfferedComponent = jQuery("#CM-Proposal-Course-CourseInfo-JointlyOffered");
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
    if (currentSectionId && currentSectionId !== "CM-Proposal-Course-CourseInfo-Section") {
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
    jQuery(".cm-charcount-max-200").jqEasyCounter({'maxChars':200, 'maxCharsWarning':180, 'msgTextAlign':'left'});
    jQuery(".cm-charcount-max-255").jqEasyCounter({'maxChars':255, 'maxCharsWarning':220, 'msgTextAlign':'left'});
    jQuery(".cm-charcount-max-500").jqEasyCounter({'maxChars':500, 'maxCharsWarning':450, 'msgTextAlign':'left'});
    jQuery(".cm-charcount-max-1000").jqEasyCounter({'maxChars':1000, 'maxCharsWarning':950, 'msgTextAlign':'left'});
}

function applyIndentationStyling() {
    jQuery(".uif-collectionItem[data-parent='CM-Proposal-Course-LearningObjectives-Section']").each(function (index) {
        var indentString = jQuery('#CM-Proposal-Course-LearningObjectives-IndentLevel_line' + index + '_control').prop('value');
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

    var actualShowMsg = jQuery("#CM-Proposal-Course-View-Admin-Message").text();

    if (actualShowMsg != null && actualShowMsg == showingRequired) {    // display all
        jQuery("#CM-Proposal-Course-View-Admin-Message").text(showingAll);
        jQuery("#CM-Proposal-Course-View-Admin-Message-Expand-Optional-Link").text(showRequired);
        jQuery(".admin-not-required-field").show();
        jQuery(".hide-when-show-all-fields").hide();
    } else {
        jQuery("#CM-Proposal-Course-View-Admin-Message").text(showingRequired);
        jQuery("#CM-Proposal-Course-View-Admin-Message-Expand-Optional-Link").text(showAll);
        jQuery(".admin-not-required-field").hide();
        jQuery(".hide-when-show-all-fields").show();
    }
    jQuery("#CM-Proposal-Course-CourseInfo-ProposalTitle_control").focus();

}

function setupCharCoutnersForVersionCodes() {
    jQuery(".cm-charcount-max-150").jqEasyCounter({'maxChars':150, 'maxCharsWarning':125, 'msgTextAlign':'left'});
}


function createCourseShowHideObjectiveElements(hideId, showId) {
    jQuery("#" + hideId).hide();
    jQuery("#" + showId).show();

}

function updateStickyHeaderText() {
    jQuery("#CM-Proposal-Course-Create-View").find('.uif-viewHeader-supportTitle').text("Review Proposal");

}

/**
 * This method retrieves/refreshes the outcome component sothat the result value clears out for the specific
 * index row
 * @param index
 */
function refreshOutcome(index) {

    var successFunction = function () {
        var selected = jQuery('#CM-Proposal-Course-Logistics-Outcome-Type_line' + index + '_control').val();
        if (selected != null && selected.length > 2) {
            jQuery('#CM-Proposal-Course-Logistics-Outcome-Credit_line' + index + '_control').focus();
        } else {
            jQuery('#CM-Proposal-Course-Logistics-AddOutcome').focus();
        }
    };
    // When outcome type changes it affects the credit value input field also. The retrieveComponent
    // is used to refresh the outcome component to apply the type changes to the component
    retrieveComponent('CM-Proposal-Course-Logistics-Outcome-Section', 'refreshCourseLogistics', successFunction, {outComeIndex:index});

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
            if (element.id.indexOf('CM-Proposal-Course-CourseInfo-SubjectCode') == 0) {
                retrieveComponent('CM-Proposal-Course-Governance-CurriculumOversight-Section', 'refreshOversightSection');
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
        return this.optional(element) || comparePersonDisplayNameInput(value, element);
    }, "Instructor name/ID combo is invalid")

jQuery.validator.addMethod("validCollaboratorNameAndID",
    function (value, element) {
        return this.optional(element) || comparePersonDisplayNameInput(value, element);
    }, "Collaborator name/ID combo is invalid")

jQuery.validator.addMethod("validProposalID",
    function (value, element) {
        return this.optional(element) || validateProposalId(value, element);
    }, "Proposal title is invalid")

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
                if (data.resultData[i].courseCode.toUpperCase() == value.toUpperCase()) {
                    isValid = true;
                    jQuery( "input[name='courseId']" ).val(data.resultData[i].courseId);
                    jQuery( "input[name='courseId']" ).removeAttr('disabled');
                    break;
                }
            }
        }
    };

    compareInputWithAutosuggestFromAjax(value, element, successFunction);

    return isValid;
}

function validateProposalId(value, element) {
    var isValid;
    if (value == null || value.length < 2) {
        return false;
    }

    var successFunction = function (data) {
        if (data == null || data.resultData == null) {
            isValid = false;
        } else {
            for (var i = 0; len = data.resultData.length, i < len; i++) {
                if (data.resultData[i].name.toUpperCase() == value.toUpperCase()) {
                    isValid = true;
                    jQuery( "input[name='proposalId']" ).val(data.resultData[i].id);
                    jQuery( "input[name='proposalId']" ).removeAttr('disabled');
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
function comparePersonDisplayNameInput(value, element) {
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
        var durationType = jQuery('#CM-Proposal-Course-Logistics-DurationType_control').val();
        var durationCount = jQuery('#CM-Proposal-Course-Logistics-DurationCount_control').val();

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
    var durationType = jQuery('#CM-Proposal-Course-Logistics-DurationType_control').val();
    var durationCount = jQuery('#CM-Proposal-Course-Logistics-DurationCount_control').val();
    if (durationType == '' && durationCount == '') {
        return;
    }
    if (event.relatedTarget == null || event.relatedTarget.id != "CM-Proposal-Course-Logistics-DurationCount_control") {
        validateFieldValue(jQuery("#CM-Proposal-Course-Logistics-DurationType_control"));
        validateFieldValue(jQuery("#CM-Proposal-Course-Logistics-DurationCount_control"));
        return;
    }
    return;
}


function setupCharCountersForLo() {
    jQuery(".lo-charcount-max-500").jqEasyCounter({'maxChars':500, 'maxCharsWarning':450, 'msgTextAlign':'left'});
}


function durationCountOnBlur() {
    validateFieldValue(jQuery("#CM-Proposal-Course-Logistics-DurationCount_control"));
    validateFieldValue(jQuery("#CM-Proposal-Course-Logistics-DurationType_control"));
    return;
}

function showHideReviewProposalErrorFields(sectionId,isCompareView) {
    var hideMissed = "Hide error highlighting.";
    var showMissed = "Show what's missing.";

    var actualShowMsg = jQuery("#CM-Proposal-Review-Error-Message-Expand-Option-Link").text();

    if (actualShowMsg != null && actualShowMsg.toString().trim() == showMissed) {
        jQuery("#CM-Proposal-Review-Error-Message-Expand-Option-Link").text(hideMissed);
        /* highlight the missing element rows */
        highlightMissingElements(sectionId, true,isCompareView);
    } else {
        jQuery("#CM-Proposal-Review-Error-Message-Expand-Option-Link").text(showMissed);
        highlightMissingElements(sectionId, false,isCompareView);
    }
    jQuery("#CM-Proposal-Review-CourseInfo-Edit-Link").focus();

}

/*Tried using jQuery("#kualiForm").validate().resetForm() to remove the validation results so that we can have the
 page contents displayed without the validation results. But There is no effect after ran the method.
 Then looping through all text areas and according to it has value or not to toggle the default border from the
 validated result border.
 */
function highlightMissingElements(sectionId, showError,isCompareView) {
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
                if (isCompareView && jQuery('#' + this.id).parent().parent().parent().find('th span.uif-requiredMessage').length != 0) {
                    jQuery('#' + this.id).parents('tr').show();
                }
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
                if (isCompareView && jQuery('#' + this.id).parent().parent().parent().find('th span.uif-requiredMessage').length != 0) {
                    jQuery('#' + this.id).parents('tr').hide();
                }
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
    if (jQuery('#CM-Proposal-Course-ActiveDates-PilotCourse_control').prop('checked')) {
        retrieveComponent('CM-Proposal-Course-ActiveDates-EndTerm');
    }
}

function resetEndTerm(){
     jQuery('#CM-Proposal-Course-ActiveDates-EndTerm_control').prop("disabled", false)
     jQuery('#CM-Proposal-Course-ActiveDates-EndTerm_control').val('');
}
/*
 * For modify course proposals, updates the current course end term when the start term of the draft course
 * is updated.
 */
function refreshCurrentCourseEndTerm() {
   // Component id, methodToCall
   retrieveComponent('CM-Proposal-Course-ActiveDates-CurrentCourseEndTerm', 'refreshCurrentCourseEndTerm');
}

function hideName() {
    var criteria = jQuery('#searchByCriteria_control').val();
    if (criteria == 'ORGANIZATIONTYPE') {
        retrieveComponent('searchByOrgTypeField');
        jQuery('#searchByField_control').hide();
        jQuery('#searchByOrgTypeField_control').show();
    } else {
        jQuery('#searchByOrgTypeField_control').hide();
        jQuery('#searchByField_control').show();
        retrieveComponent('searchByField');
    }
}

function setCommentEditFieldFocus() {

    if (jQuery('#CM-Proposal-Course-Comment-List-Header') != null && jQuery('#CM-Proposal-Course-Comment-List-Header').length > 0) {
        var lpos = jQuery('#CM-Proposal-Course-Comment-List-Header').text().trim().indexOf("(") + 1;
        var rpos = jQuery('#CM-Proposal-Course-Comment-List-Header').text().trim().indexOf(")");

        var total = jQuery('#CM-Proposal-Course-Comment-List-Header').text().trim().substr(lpos, rpos - lpos);
        for (i = 0; i < total; i++) {
            if (jQuery('#CM-Proposal-Course-Comment-Add-Field_line' + i + '_control').length > 0 && jQuery('#CM-Proposal-Course-Comment-Add-Field_line' + i + '_control').attr('readonly') != "readonly") {
                jQuery('#CM-Proposal-Course-Comment-Add-Field_line' + i + '_control').focus();
            }
        }
    }
}

function setReadonlyTextWidthForComment() {
    if (jQuery('#CM-Proposal-Course-Comment-NewComment_control') != null) {
        var editAreaWidth = jQuery('#CM-Proposal-Course-Comment-NewComment_control').width();
    }

    if (jQuery('#CM-Proposal-Course-Comment-List-Header') != null && jQuery('#CM-Proposal-Course-Comment-List-Header').length > 0) {
        var lpos = jQuery('#CM-Proposal-Course-Comment-List-Header').text().trim().indexOf("(") + 1;
        var rpos = jQuery('#CM-Proposal-Course-Comment-List-Header').text().trim().indexOf(")");

        var total = jQuery('#CM-Proposal-Course-Comment-List-Header').text().trim().substr(lpos, rpos - lpos);
        for (i = 0; i < total; i++) {
            if (jQuery('#CM-Proposal-Course-Comment-Add-Field_line' + i).length > 0) {
                if (jQuery('#CM-Proposal-Course-Comment-Add-Field_line' + i + '_control').attr('readonly') == "readonly") {
                    var width = jQuery('#CM-Proposal-Course-Comment-Header_line' + i).width();
                    if (width < 1) {
                        width = 580;
                        /* set the default value */
                    }
                    jQuery('#CM-Proposal-Course-Comment-Add-Field_line' + i + '_control').width(width);
                }
            }
        }
    }
}

jQuery.validator.addMethod("validLoCategory",
    function (value, element) {
        return this.optional(element) || validateNewLoCategoryAndType(value, element);
    }, "")


function validateNewLoCategoryAndType(value, element) {

    var loCategoryType_control = element.id;
    var hiddenFieldId = loCategoryType_control.replace("_control", "_h0");
    //If user selected the LO name from autosuggest, then this Id should be already available.
    var loId = jQuery("#" + hiddenFieldId).val();

    loCategoryType_control = loCategoryType_control.replace('CM-Proposal-Course-LearningObjectives-Category', 'CM-Proposal-Course-LearningObjectives-CategoryType');
    var loCategoryType = jQuery('#' + loCategoryType_control).parent();
    var loCategoryInfoMessage = jQuery('#' + loCategoryType_control).closest('div[id^="learning_objective_section"]').find('p.ks-informational-message-for-field');

    //If user doesnt select from autosuggest and keyed in their own data, show the type to pick
    if (value != '' && loId == '') {
        if (!loCategoryType.is(':visible')) {
            //  Show the category type drop down and give it focus.
            loCategoryType.show();
            jQuery('#' + loCategoryType_control).focus();
            //  Add a message indicating that a category type needs to chosen.
            var imgOuterHTML = loCategoryInfoMessage.find('img')[0].outerHTML
            loCategoryInfoMessage.html(imgOuterHTML + ' You must add a category type to create the new category ' + value + '.');
            loCategoryInfoMessage.show();
            // Add the requiredness for Category Type when it is visible
            checkForRequiredness(jQuery('#' + loCategoryType_control).attr('name'), function () {
                return true;
            }, '*');
        }
    } else {
        if (loCategoryType.is(':visible')) {
            loCategoryType.hide();
            loCategoryInfoMessage.hide();
            jQuery("#" + loCategoryType_control + " option[value='']").attr('selected', true);
            // Hide the required messages if any exist
            hideMessageTooltip(loCategoryType.attr('id'));
            jQuery('#' + loCategoryType_control).removeClass('error');
            // Remove the requiredness for Category Type when it is hidden
            checkForRequiredness(jQuery('#' + loCategoryType_control).attr('name'), function () {
                return false;
            }, '*');
        }
    }
    return true;
}

/* This method checks all checkboxes when selectAll is true
 * otherwise uncheck all the checkboxes on the Category lookup lightbox
 */
function categoryTypeSelection(selectAll) {
    if (selectAll == 'true') {
        jQuery('#CM-Proposal-Course-LoCategory-ListTypeGroup').find('input').each(function (index) {
            jQuery('#CM-Proposal-Course-LoCategory-ListTypeGroup_control_' + index).selected(true);
        });

    } else {
        jQuery('#CM-Proposal-Course-LoCategory-ListTypeGroup').find('input').each(function (index) {
            jQuery('#CM-Proposal-Course-LoCategory-ListTypeGroup_control_' + index).selected(false);
        });
    }
    filterCategoriesByTypes();
}

function filterCategoriesByName() {
    var inputVal = jQuery('#CM-Proposal-Course-LoCategory-Filter-Input_control').val().trim();
    jQuery("#uLookupResults_layout").dataTable().fnFilter(inputVal);
}

/* This method filters all the types selected in the checkbox group
 * Step1 construct the filter text as type1 | type2 ...  where type1 and type2 are checked types
 * Step2 apply the filter text to the type column in the data table
 */
function filterCategoriesByTypes() {
    var labels = '';
    var i = 0;
    jQuery('#CM-Proposal-Course-LoCategory-ListTypeGroup').find('input').each(function (index) {
        if (jQuery('#CM-Proposal-Course-LoCategory-ListTypeGroup_control_' + index).is(':checked') == true) {
            var label = jQuery('#CM-Proposal-Course-LoCategory-ListTypeGroup_control_' + index).next("label").text();
            if (i > 0) {
                labels = labels + '|' + label;
            } else {
                labels = label;
                i++;
            }
        }
    });

    if (i == 0) {
        jQuery("#uLookupResults_layout").dataTable().fnFilter('|', 2);
    } else {
        jQuery("#uLookupResults_layout").dataTable().fnFilter('', 2, true, false);
        jQuery("#uLookupResults_layout").dataTable().fnFilter(labels, 2, true, false);
    }
}

/**
 * Writes category id and label to inputs after an autocomplete item is selected.
 */
function loCategoryAutocomplete(ui, control) {
    var id = jQuery(control).attr('id');
    var hiddenFieldId = id.replace("_control", "_h0");
    jQuery("#" + id).val(ui.item.label);
    jQuery("#" + hiddenFieldId).val(ui.item.id);
    return false;
}

function filterObjectivesByName() {
    var inputVal = jQuery('#CM-Proposal-Course-Lo-Filter-Input_control').val().trim();
    jQuery("#uLookupResults_layout").dataTable().fnFilter(inputVal);
}

/**
 * Hides the Category Type dropdown and Information message label.
 * @param index
 */
function hideCategoryTypeAndInfoMsg(index) {
    jQuery('#CM-Proposal-Course-LearningObjectives-CategoryType_line' + index + '_add').hide();
    jQuery('#CM-Proposal-Course-LearningObjectives-CategoryType_line' + index + '_add').closest('div[id^="learning_objective_section"]').find('p.ks-informational-message-for-field').hide();
}

function setDirtyManually(dirtyFlag) {
    dirtyFormState.dirtyFormInput.val(dirtyFlag);
}

function onCourseRetireLoad(isCurriculumSpecialist, currentSectionId) {
    /*
     * Handler for fixing nav position when the window is scrolled (because the sticky header changes).
     *
     */
    if (!hasScrollEvent(leftNavPositioningEventNamespace)) {
        jQuery(window).on('scroll.' + leftNavPositioningEventNamespace, handleNavPanelRepositioningOnWindowScrollForRetire);
    }

    if (isCurriculumSpecialist) {
        initializeForCurriculumSpecialistRetire(currentSectionId);
    }

    fixLeftNavElementPositioningForRetire(true);
}

function handleNavPanelRepositioningOnWindowScrollForRetire() {
    //  Fix the position of the nav elements if the header changes.
    fixLeftNavElementPositioningForRetire(false);
}

/**
 * Hacks for Curriculum Specialist single-page view.
 */
function initializeForCurriculumSpecialistRetire(currentSectionId) {
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
    if (currentSectionId && currentSectionId !== "CM-Proposal-Course-RetireInfo-Section") {
        scrollToSection("#" + currentSectionId, true);
    }
}

/**
 * Do fixed positioning of left nav elements.
 *
 * Some elements are only present for certain proposal doc types, so the presence of each element it checked except the
 * tab panel itself and the elements are positioned to the bottom of the previous element, starting at the bottom of the
 * sticky header.
 */
function fixLeftNavElementPositioningForRetire(initial) {
    //  Get the position of the element that the nav components need to align under.
    var anchorElement = jQuery("#CM-Proposal-Course-Retire-View > header");
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

    var tabPanelHeader = jQuery("#CM-Proposal-Course-Retire-TabHeader");
    var tabPanel = jQuery(tabListId);
    if (tabPanel.length == 0) {
        console.error('Unable to find the tab panel. Nav elements may not be positioned correctly.');
        return;
    }
    var tabPanelFooter = jQuery("#CM-Proposal-Course-Retire-ReviewProposalLink");

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

function closeCommentUndo() {
    jQuery("#CM-Proposal-Course-Comment-CloseCommentDeleteUndoInfo").hide();
}

/**
 * Common scripts.
 */
var common = {
    removeNonBreakingSpacesFromPreTags: function() {
        jQuery("pre").each(function (index) {
            var text = jQuery(this).html().replace(/&nbsp;/g, ' ');
            jQuery(this).html(text);
        });
    },
    /**
     * Shows the comment view in a lightbox. To always get the view from the server, we need to have unique url so that fancy
     * box not caches the html. Here, we're adding the datetime to make sure url random
     *
     * @param href
     */
    showViewInLightBox: function(href) {
        if (!Date.now) {
            Date.now = function () {
                return new Date().getTime();
            };
        }
        var href1 = href + "&fake=" + Date.now();
        showLightboxUrl(href1);
    }
}

/**
 * Scripts used in the Course Versions view.
 */
var courseVersions = {

    /*
     * Checkbox tracking. The most recently checked checkbox goes in slot one.
     */
    checkedCheckboxes: {slot1: "", slot2: ""},

    /*
     * Make sure a maximum of two checkboxes are checked and hanled enable/disable of submit button.
     */
    manageWidgets: function (item) {
        changedCheckBoxId = item.id + "_control";

        //  Get the current contents of the checkbox tracking slots for convenience
        currentSlotId1 = this.checkedCheckboxes['slot1'];
        currentSlotId2 = this.checkedCheckboxes['slot2'];

        hasBecomeChecked = jQuery("#" + changedCheckBoxId).is(':checked');

        //  If the checkbox change was that it became checked then manage the slots
        if (hasBecomeChecked) {
            //  Uncheck the oldest/slot2 checkbox if it has a value
            if (currentSlotId2) {
                jQuery("#" + currentSlotId2).prop('checked', false);
            }
            //  Write slot1 into slot2 if it has a value
            if (currentSlotId1) {
                this.checkedCheckboxes['slot2'] = this.checkedCheckboxes['slot1'];
            }
            //  Write the changed checkbox id into slot1
            this.checkedCheckboxes['slot1'] = changedCheckBoxId;
        } else {
            //  If a checkbox has become unchecked. No 'else' needed here.
            if (changedCheckBoxId === currentSlotId1) {
                //  Copy slot 2 to slot one and make slot 2 empty
                this.checkedCheckboxes['slot1'] = this.checkedCheckboxes['slot2'];
            }
            //  Make slot 2 empty
            this.checkedCheckboxes['slot2'] = "";
        }

        //  Adjust the button based on the contents of slot 1
        if (this.checkedCheckboxes['slot1']) {
            jQuery("#CM-CourseVersion-Button-ShowVersions").removeAttr("disabled");
            jQuery("#CM-CourseVersion-Button-ShowVersions").removeClass("disabled");
        } else {
            jQuery("#CM-CourseVersion-Button-ShowVersions").attr("disabled", "disabled");
            jQuery("#CM-CourseVersion-Button-ShowVersions").addClass("disabled");
        }
    },

    /*
     * Called by the Show Version(s) button successCallback. Redirects to View Course
     * for the selected versions.
     */
    redirectToViewCourse: function () {
        var queryData = {};
        queryData.methodToCall = 'getRedirectUri';
        queryData.ajaxReturnType = 'update-none';
        queryData.ajaxRequest = true;
        queryData.formKey = jQuery("input[name='" + kradVariables.FORM_KEY + "']").val();

        //  Perform the redirect on success. Otherwise, the view course page will be displayed in the iframe.
        var successFunction = function (data) {
            window.top.location.href = data['uri'];
        };

        //  Call back to the controller to get the URI to redirect to.
        jQuery.ajax({
            url:jQuery('form#kualiForm').attr("action"),
            dataType:"json",
            async:false,
            beforeSend:null,
            complete:null,
            data:queryData,
            success:successFunction,
        });
    }
}

function courseRetireErrorStyling(isCurriculumSpecialist) {
    if (!isCurriculumSpecialist) {
       jQuery("#CM-Proposal-Course-Retire-Page").addClass("retire_messages");
    }
}

/**
 * This function performs a ajax call and fixes the left Nav overlaying issues after the successful ajax call
 */
function submitCancelDialog() {

    var fixLeftNavElementPositioningOnDialogClose = function() {
        if (jQuery('#CM-Proposal-Course-Retire-View').length) {
            fixLeftNavElementPositioningForRetire(true);
        }
        if (jQuery('#CM-Proposal-Course-Create-View').length) {
            fixLeftNavElementPositioning(true);
        }
    }
    submitForm('cancel', {}, false, true, fixLeftNavElementPositioningOnDialogClose);
}