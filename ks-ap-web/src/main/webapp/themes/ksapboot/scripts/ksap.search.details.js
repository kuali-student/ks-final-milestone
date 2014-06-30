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


//Sections with multiple offerings checkbox behavior
function checkboxSelectAndHighlight() {

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

    //Add and remove class when checkbox is toggled
    jQuery(".uif-checkboxControl").change(function () {
        if(jQuery(this).is(":checked")){
            jQuery(this).closest('tr').addClass("ksap-selected-row");
        }else{
            jQuery(this).closest('tr').removeClass("ksap-selected-row");
        }
    });
}

// Registering Course Search Results Facets events
function registerCourseSectionEvents(jqObjects){
    jQuery(jqObjects)
        .on('COURSE_SECTION_ADDED', function(event, data) {
            ksapAddCourseSection(data);
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
    var selectedFormatOfferingId = jQuery(element).find('input:checked').val();
    var idToToggleOn = divIdPrefix + selectedFormatOfferingId;

    jQuery(idToToggleOn).show();

    jQuery.each(jQuery(element).find('input:unchecked'), function() {
        var idToToggleOff = divIdPrefix + jQuery(this).val();
        jQuery(idToToggleOff).hide();
    });

}