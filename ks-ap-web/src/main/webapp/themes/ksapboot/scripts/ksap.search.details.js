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

function ksapAddCourseSection (data){
    activityId = data.activityOfferingId;
    var courseOfferingMarker = jQuery(".ksap-section-planned-marker");
    if(courseOfferingMarker.length){
        courseOfferingMarker.removeClass("ksap-hide");
    }

    var addLink = jQuery("#"+data.activityOfferingId+"_addLink");
    if(addLink.length){
        addLink.removeClass("ks-fontello-icon-hollow-circled-plus");
        addLink.addClass("ks-fontello-icon-ok-circled");
    }

    var plannedContainer = jQuery("#KSAP-CourseSectionDetails-PlannedSections_"+data.courseOfferingId);
    plannedContainer.removeClass("ksap-hide");
    var plannedCount = parseInt(plannedContainer.attr("data-numberplanned"));
    plannedCount = plannedCount+1;
    plannedContainer.attr("data-numberplanned",plannedCount);
    var plannedHeaderText = plannedContainer.find(".uif-headerText-span");
    var newText = "Planned Registration Groups ("+plannedCount+")";
    plannedHeaderText[0].innerHTML = newText;

    var plannedTemplate = jQuery("#KSAP-CourseSectionDetails-PlannedSection-Template");

    if(plannedTemplate.length){
        var item = plannedTemplate.html();
        for (var key in data)
            if (data.hasOwnProperty(key))
                item = eval("item.replace(/__KSAP__"+key.toUpperCase()+"__/gi,'"+data[key]+"')");
        item = item.replace(/id=\"(u\d+)\"/gi,"id=\""+data.uid+"_$1\"");
        var itemElement = jQuery("<div/>").html(item);
        if(data.honors == false){
            itemElement.find("#honors").addClass("invisible");
        }else{
            itemElement.find("#honors").removeClass("invisible");
        }

        if(data.classUrl.length == 0){
            itemElement.find("#classUrl").addClass("invisible");
        }else{
            itemElement.find("#classUrl").removeClass("invisible");
        }

        if(data.requirementsUrl.length == 0){
            itemElement.find("#requirementsUrl").addClass("invisible");
        }else{
            itemElement.find("#requirementsUrl").removeClass("invisible");
        }

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