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
    if (! hasScrollEvent(leftNavPositioningEventNamespace)) {
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
    var anchorElement = jQuery( "#KS-CourseView > div.uif-viewHeader-contentWrapper" );
    if (anchorElement.length == 0) {
        console.error('Unable to find an anchor element. Nav elements were not positioned correctly.');
        return;
    }

    var anchorBottom = getBottom(anchorElement);
    if (! initial) {
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
        tabPanelHeader.css({ top: anchorBottom });
        anchorElement = tabPanelHeader;
        anchorBottom = getBottom(anchorElement);
    }

    //  Position the tab panel.
    tabPanel.css({top: anchorBottom});

    //  Position the text under the tab panel if it exists.
    if (tabPanelFooter.length != 0) {
        anchorBottom = getBottom(tabPanel);
        tabPanelFooter.css({ top: anchorBottom });
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
    jQuery.each(scrollEvents, function(index, event) {
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
    jQuery(tabPanelId).on( "tabsactivate",
        function(event, ui) {
            //  Checking the event type to make sure it's a click.
            if (typeof event.originalEvent.originalEvent !== 'undefined'
                && event.originalEvent.originalEvent.type == "click") {
                //  Scroll to the section corresponding to the clicked tab.
                scrollToSection("#" + ui.newPanel.attr('id').replace('_tab',''), true);
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
    jQuery("div[data-type='TabWrapper']").each(function() {
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
    jQuery('html body').animate(
        { scrollTop: (jQuery(sectionId).offset().top - jQuery(".uif-viewHeader-contentWrapper").outerHeight(true))},
		{
            duration: 750,
            queue: false,
            complete: function() {
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
jQuery.fn.focusWithoutScrolling = function() {
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
        if(isPortal){
            parent.jQuery('head').append('<link href="' + url + '/rice-portal/css/portal.css" rel="stylesheet" type="text/css">');
        }
    }};
    showLightboxComponent(componentId, overrideOptions);
}

function setupCharCounters() {
	jQuery(".cm-charcount-max-24").jqEasyCounter({'maxChars': 24, 'maxCharsWarning': 14, 'msgTextAlign': 'left'});
	jQuery(".cm-charcount-max-255").jqEasyCounter({'maxChars': 255, 'maxCharsWarning': 220, 'msgTextAlign': 'left'});
	jQuery(".cm-charcount-max-500").jqEasyCounter({'maxChars': 500, 'maxCharsWarning': 450, 'msgTextAlign': 'left'});
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
	 jQuery(".uif-collectionItem[data-parent='LearningObjective-CollectionSection']").each(function(index) {
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

function showHideCreateCourseOptionalElements () {
    var showingAll = "Showing required and optional fields";
    var showAll = "Show all fields."
    var showingRequired = "Showing only required fields.";
    var showRequired = "Show only required fields.";

    var actualShowMsg = jQuery("#Create-CourseView-Admin-Message_span").text();

    if(actualShowMsg != null && actualShowMsg == showingRequired) {    // display all
        jQuery("#Create-CourseView-Admin-Message_span").text(showingAll);
        jQuery("#Create-CourseView-Admin-Message-expand-optional-link").text(showRequired);
        jQuery(".admin-not-required-field").show();
        jQuery(".hide-when-show-all-fields").hide();
    }  else  {
        jQuery("#Create-CourseView-Admin-Message_span").text(showingRequired);
        jQuery("#Create-CourseView-Admin-Message-expand-optional-link").text(showAll);
        jQuery(".admin-not-required-field").hide();
        jQuery(".hide-when-show-all-fields").show();
    }
    jQuery("#CreateCourseProposalTitleInputField_control").focus();

}

function createCourseShowHideObjectiveElements(hideId, showId) {
    jQuery("#"+hideId).hide();
    jQuery("#"+showId).show();

}

function updateStickyHeaderText() {
    jQuery("#KS-CourseView").find('.uif-viewHeader-supportTitle').text("Review Proposal");
}

function reDrawOutcomeComponent() {
    retrieveComponent('KS-CourseView-CourseLogisticsPage-Outcome-Widgets');
}