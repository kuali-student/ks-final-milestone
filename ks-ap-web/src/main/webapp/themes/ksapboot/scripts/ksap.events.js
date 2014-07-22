// Registering Course Search Results Facets events
function registerPlannerEvents(jqObjects){
    jQuery(jqObjects)
        .on('PLAN_ITEM_ADDED', function(event, data) {
            ksapPlannerAddPlanItem(data);
        })
        .on('PLAN_ITEM_DELETED', function(event, data) {
            ksapPlannerRemovePlanItem(data);
        })
        .on('PLAN_ITEM_UPDATED', function(event, data) {
            ksapPlannerUpdatePlanItem(data);
        })
        .on('TERM_NOTE_UPDATED', function(event, data) {
            ksapPlannerUpdateTermNote(data);
        })
        .on('UPDATE_NEW_TERM_TOTAL_CREDITS', function(event, data) {
            ksapPlannerUpdateCredits(data);
        })
        .on('UPDATE_OLD_TERM_TOTAL_CREDITS', function(event, data) {
            ksapPlannerUpdateCredits(data);
        })
        .on('BOOKMARK_ADDED', function(event, data) {
            ksapBookmarkAddItem(data);
        })
        .on('UPDATE_BOOKMARK_TOTAL', function(event, data) {
            ksapBookmarkUpdateTotal(data);
        });
}

/**
 * Adds an html object into the calendar by copying a hidden template copy
 * and filling in placeholders with the needed data
 *
 * @param data - Data for the new object
 */
function ksapPlannerAddPlanItem (data) {
    // Add plan item to the planner calendar
    if(jQuery("#planner_item_template").length){
        var item = jQuery("#planner_item_template").html();
        for (var key in data)
            if (data.hasOwnProperty(key))
                item = eval("item.replace(/__KSAP__"+key.toUpperCase()+"__/gi,'"+data[key]+"')");
        item = item.replace(/id=\"(u\d+)\"/gi,"id=\""+data.uid+"_$1\"");
        var termUid = data.termId.replace(/\./g,'-');
        var itemElement = jQuery("<div/>").html(item);
        if(data.courseNoteRender == 'false'){
            itemElement.find(".coursenote").addClass("invisible");
        }else{
            itemElement.find(".coursenote").removeClass("invisible");
        }
        itemElement.find("input[data-for='"+data.uid+"']")
            .removeAttr("script")
            .attr("name", "script");
        itemElement
            .attr("id", data.uid+"_wrap")
            .attr("class", "uif-collectionItem uif-boxCollectionItem")
            .appendTo(".ksap-carousel-term." + termUid + ".ksap-term-" + data.category)
            .css({backgroundColor:"#ffffcc"})
            .hide()
            .fadeIn(250, function() {
                var bucket = jQuery("ks-plan-Bucket-Footer.ksap-term-" + data.category + "." + termUid);
                var unitcell = bucket.find(".ksap-carousel-term-total");
                unitcell.addClass("ks-plan-Bucket-footer-show");
                unitcell.removeClass("ks-plan-Bucket-footer-hide");
            })
            .animate({backgroundColor:"#ffffff"}, 1500, function() {
                itemElement.css({background: "none"});
                runHiddenScripts(data.uid);
            });
        //Set static ids on some element for AFTs
        ksapSetStaticCourseIDs(data.uid + "_code", data.uid + "_courseNote");
    }

    // Change status on the course search page
    if(jQuery("#"+data.courseId+"_status").length){
        var item = jQuery("#"+data.courseId+"_status");
        item.addClass("planned").html("Planned");
    }

}

/**
 * Changes the displayed value for an plan item's html credit text.
 *
 * @param data - Data for the new credit value
 */
function ksapPlannerUpdatePlanItem (data) {
    var item = jQuery("#" + data.uniqueId);
    item.find(".credit p").text(data.credit);
    item.find(".coursenote").attr("title",data.courseNote);
    if(data.courseNoteRender == 'false'){
        item.find(".coursenote").addClass("invisible");
    }else{
        item.find(".coursenote").removeClass("invisible");
    }
}

/**
 * Removes a plan item html object from the calendar
 *
 * @param data - Data needed to removed the object
 */
function ksapPlannerRemovePlanItem (data) {
    jQuery("#" + data.uid).fadeOut(250, function(){
        jQuery(this).parent().remove();
    });
}

/**
 * Changes the displayed value for a term section's html credit text.
 *
 * @param data - Data needed to removed the object
 */
function ksapPlannerUpdateCredits (data) {
    var planbucket = jQuery(".ksap-term-planned." + data.termId);
    var planunitcell = planbucket.find(".ksap-carousel-term-total");
    planunitcell.find(".credits p.uif-message").fadeOut(250, function() {
        jQuery(this).text(data.totalCredits).fadeIn(250);
    });

    var cartbucket = jQuery(".ksap-term-cart." + data.termId);
    var cartunitcell = cartbucket.find(".ksap-carousel-term-total");
    cartunitcell.find(".credits p.uif-message").fadeOut(250, function() {
        jQuery(this).text(data.cartCredits).fadeIn(250);
    });
}

/**
 * Changes the displayed value for a term's html note text
 *
 * @param data - Data needed to removed the object
 */
function ksapPlannerUpdateTermNote (data) {
    if (data.termNote == null || data.termNote == "") {
        jQuery("#"+data.uniqueId+"_termNote").attr("title","");
        //@TODO: ksap-961 convert to icon font instead of image
        jQuery("#"+data.uniqueId+"_termNote").attr("src","../ks-ap/images/btnAdd.png");
    } else {
        jQuery("#"+data.uniqueId+"_termNote").attr("title",data.termNote);
        //@TODO: ksap-961 convert to icon font instead of image
        jQuery("#"+data.uniqueId+"_termNote").attr("src","../ks-ap/images/iconInfo.png");
    }

}


/**
 * Updates the html object containing the total number of bookmarks for the bookmark side bar.y
 *
 * @param data - Data for the new object
 */
function ksapBookmarkUpdateTotal (data) {
    if(jQuery("#bookmark_summary_number").length){
        var item = jQuery("#bookmark_summary_number");
        item.attr("title","View all "+data.bookmarkTotal+" courses in full details");
        item.html("View all "+data.bookmarkTotal+" courses in full details");
    }

    var bookmarkCountLink=jQuery("#Ksap-Header-Bookmark-Count");
    var bookmarkCountValue=jQuery("#Ksap-Header-Bookmark-Count-Value");
    var bookmarkDetailsPageCountValue=jQuery("#bookmarkHeaderCount");

    //update the count in the secondary navigation
    if (bookmarkCountValue.length) {
        bookmarkCountValue.html(data.bookmarkTotal);
    }

    //update the styling and clickability of the link
    if (bookmarkCountLink.length) {
        if (data.bookmarkTotal !=null && data.bookmarkTotal <=0) {
            bookmarkCountLink.addClass("disabled ks-fontello-icon-star-empty");
            bookmarkCountLink.removeClass("ks-fontello-icon-star");
            bookmarkCountLink.attr("disabled","disabled");
        } else {
            bookmarkCountLink.addClass("ks-fontello-icon-star");
            bookmarkCountLink.removeClass("disabled ks-fontello-icon-star-empty");
            bookmarkCountLink.removeAttr("disabled")
        }
    }

    //update the count in the sidebar
    if (jQuery("#bookmarkSidebarHeaderCount").length) {
        var bookmarkSidebarCountValue=jQuery("#bookmarkSidebarHeaderCount");
        bookmarkSidebarCountValue.html(data.bookmarkTotal);
    }

    //update the count on the Bookmark details page
    if (bookmarkDetailsPageCountValue.length) {
        bookmarkDetailsPageCountValue.html(data.bookmarkTotal);
    }
}