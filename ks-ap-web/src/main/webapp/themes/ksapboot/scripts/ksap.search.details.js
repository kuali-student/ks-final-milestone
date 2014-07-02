//Called onDocumentReady in CourseDetailsUI.xml (Course Details page) to detect page referrer
function detectReferrerForBackLinkText() {
    var backLink = jQuery('.uif-link.cdp-back-link:visible');

    //check for empty document.referrer
    if (document.referrer != null && document.referrer.length > 0) {
        var referrer = document.referrer.split('kr-krad/')[1].split('?')[0];

        if ( referrer == "course" ) {
            //from course search, change link text
            backLink.text('Return to search results');
        } else if ( referrer == "planner" ) {
            //from planner, change link test
            backLink.text('Return to plan');
        }
    }
}


//Sections with multiple formats or offerings input behavior
function checkboxSelectAndHighlight() {

    //Select radio if clicking anywhere on the row
    jQuery('.uif-tooltip').click(function(event) {
        if (event.target.type !== 'radio') {
            jQuery(':radio', this).trigger('click');
        }
    });

    //Select checkbox if clicking anywhere on the row
    jQuery('tr').click(function(event) {
        if (event.target.type !== 'checkbox') {
            jQuery(':checkbox', this).trigger('click');
        }
    });

    //Don't select checkbox if clicking on link
    /*jQuery('tr a').click(function(event) {
        event.stopPropagation();
    });*/

    //Add and remove class when radio is toggled
    jQuery(".uif-verticalRadioControl").change(function () {
        if(jQuery(this).is(":checked")){
            jQuery(this).closest('.uif-tooltip').addClass('ksap-selected-row').siblings().removeClass('ksap-selected-row');
        }
    });

    //Add and remove class when checkbox is toggled
    jQuery('.uif-checkboxControl').change(function () {
        if(jQuery(this).is(":checked")){
            jQuery(this).closest('tr').addClass('ksap-selected-row');
        }else{
            jQuery(this).closest('tr').removeClass('ksap-selected-row');
        }
    });
}

// Registering Course Search Results Facets events
function registerCourseSectionEvents(jqObjects){
    jQuery(jqObjects)
        .on('COURSE_SECTION_ADDED', function(event, data) {
            ksapAddCourseSection(data);
        })
        .on('FILTER_COURSE_OFFERING', function(event, data) {
            ksapFilterCourseOffering(data);
        });
}

/**
 * Dynamic updating event for when an registration group is added to the page
 *
 * @param data - Information about the event
 */
function ksapAddCourseSection (data){

    // Display the planned marker on the course offering header if needed
    var courseOfferingMarker = jQuery(".ksap-section-planned-marker");
    if(courseOfferingMarker.length){
        courseOfferingMarker.removeClass("ksap-hide");
    }

    // Show the planned groups if needed and update the number of planned registration groups displayed in header
    var plannedContainer = jQuery("#KSAP-CourseSectionDetails-PlannedSections_"+data.courseOfferingId);
    plannedContainer.removeClass("ksap-hide");
    var plannedCount = parseInt(plannedContainer.attr("data-numberplanned"));
    plannedCount = plannedCount+1;
    plannedContainer.attr("data-numberplanned",plannedCount);
    var plannedHeaderText = plannedContainer.find(".uif-headerText-span");
    var newText = "Planned Registration Groups ("+plannedCount+")";
    plannedHeaderText[0].innerHTML = newText;

    // Add the new planned activities to the planned groups
    var plannedTemplate = jQuery("#KSAP-CourseSectionDetails-PlannedSection-Template");

    if(plannedTemplate.length){
        var item = plannedTemplate.html();

        // Replace placeholders
        for (var key in data)
            if (data.hasOwnProperty(key))
                item = eval("item.replace(/__KSAP__"+key.toUpperCase()+"__/gi,'"+data[key]+"')");
        item = item.replace(/id=\"(u\d+)\"/gi,"id=\""+data.uid+"_$1\"");
        var itemElement = jQuery("<div/>").html(item).attr("class", "uif-collectionItem uif-boxCollectionItem");

        // Add Activities
        var activities = data.activities;
        for(var i = 0; i<activities.length; i++){
            var activity = activities[i];
            var plannedActivityTemplate = jQuery("#KSAP-CourseSectionDetails-PlannedSection-Activity-Template");
            var item2 = plannedActivityTemplate.html();
            // Replace placeholders
            for (var key2 in activity)
                if (activity.hasOwnProperty(key2))
                    item2 = eval("item2.replace(/__KSAP__"+key2.toUpperCase()+"__/gi,'"+activity[key2]+"')");
            var item2Element = jQuery("<div/>").html(item2);

            // Hide needed fields
            if(activity.honors == false){
                item2Element.find("#honors").addClass("invisible");
            }

            if(activity.classUrl.length == 0){
                item2Element.find("#classUrl").addClass("invisible");
            }

            if(activity.requirementsUrl.length == 0){
                item2Element.find("#requirementsUrl").addClass("invisible");
            }

            // For now modify the add to plan button to indicate that it is added and display click functionality
            var addLink = jQuery("#"+activity.activityOfferingId+"_addLink");
            if(addLink.length){
                addLink.removeClass("ks-fontello-icon-hollow-circled-plus");
                addLink.addClass("ks-fontello-icon-ok-circled");
            }

            // Add activity
            item2Element.appendTo(itemElement.find("#Activities-Placeholder"));
        }


        // Add new element to the planned groups section
        itemElement.appendTo("#KSAP-CourseSectionDetails-PlannedSections_"+data.courseOfferingId)
            .css({backgroundColor:"#ffffcc"})
            .hide()
            .fadeIn(250, function() {
            })
            .animate({backgroundColor:"#ffffff"}, 1500, function() {
                itemElement.css({background: "none"});
                runHiddenScripts(data.uid);
            });
    }
}

/**
 * Dynamic updating event when recalculating valid status of AOs and FOs
 *
 * @param data - Information about the event
 */
function ksapFilterCourseOffering (data){
    // Get DOM Object for the course offering being updated
    var courseOffering = jQuery("#"+data.termId+"_"+data.courseOfferingCode+"_section");

    // Get the AO rows under the course offering
    var activities = courseOffering.find(".ksap-activity-row");
    var validActivityIds = data.activities;

    // Check each activity under the course offering
    for(var i = 0; i < activities.length; i++){
        var activity = activities[i];
        var activityId = activity.getAttribute("id");
        var valid = jQuery.inArray(activityId,validActivityIds);
        // inArray returns index of found object, -1 represents not found
        if(valid<0){
            jQuery(activity).addClass("ksap-invalid-activity");
        }else{
            jQuery(activity).removeClass("ksap-invalid-activity");
        }
    }

    // Get the FO radio objects under the course offering
    var formatRadioOptions = jQuery("#"+data.termId+"_"+data.courseOfferingCode+"_formatOfferingOptions");
    var formatRadios = formatRadioOptions.find(".ksap-format-radio");
    var validFormatIds = data.formatOfferings;

    // Check each radio under the course offering
    for(var i = 0; i < formatRadios.length; i++){
        var format = formatRadios[i];
        var formatId = format.getAttribute("value");
        var valid = jQuery.inArray(formatId,validFormatIds);
        // inArray returns index of found object, -1 represents not found
        if(valid<0){
            jQuery(format).addClass("ksap-invalid-format");

            // Include label in changes
            var radioId = jQuery(format).attr("id");
            var label = jQuery("[for='"+radioId+"']");
            label.addClass("ksap-invalid-format");
        }else{
            // Include label in changes
            jQuery(format).removeClass("ksap-invalid-format");
            var radioId = jQuery(format).attr("id");
            var label = jQuery("[for='"+radioId+"']");
            label.removeClass("ksap-invalid-format");
        }
    }

}

/**
 * Sets the activity ids for eah row in the activity tables
 */
function setupActivityIds(){
    var rowIds = jQuery(".rowActivityOfferingId");
    for(var i = 0;i<rowIds.length;i++){
        var rowId = rowIds[i];
        var parentRow = jQuery("#"+rowId.getAttribute("id")).parents(".ksap-activity-row");
        parentRow.attr("id",rowId.innerHTML.trim());
    }
}

/**
 * Toggle the divs on/off based on the selected FormatOffering radio option
 * @param element - Element containing the radio control
 * @param divIdPrefix - Prefix of the ID that will be built to toggle on/off
 */
function toggleFormatOfferingSections(element, divIdPrefix) {
    // Find selected format offering list
    var selectedElement = jQuery(element);
    var selectedInput = selectedElement.find('input:checked');
    var selectedFormatOfferingId = selectedInput.val();
    var idToToggleOn = divIdPrefix + selectedFormatOfferingId+"_section";
    var toggleOn = jQuery(idToToggleOn);

    // Hide all format offering lists
    jQuery.each(jQuery(element).find('.uif-verticalRadioControl'), function() {
        var unselectedElement = jQuery(this);
        var unselectedFormatOfferingId = unselectedElement.val();
        var idToToggleOff = divIdPrefix + unselectedFormatOfferingId +"_section";
        var toggleOff = jQuery(idToToggleOff);
        toggleOff.addClass('ksap-hide');
    });

    // Show selected format offering list
    jQuery(idToToggleOn).removeClass('ksap-hide');


}

