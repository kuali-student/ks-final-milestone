
function registerPlannerEventsForBookmarks(jqObjects){
    jQuery(jqObjects)
        .on('PLAN_ITEM_DELETED', function(event, data) {
            removeBookmarkedItem(data);
        })
        .on('UPDATE_BOOKMARK_TOTAL', function(event, data) {
            ksapBookmarkUpdateSidebarTotal(data);
            ksapBookmarkUpdateSidebarEmptyResults(data);
            ksapBookmarkUpdateDetailsTotal(data);
            ksapBookmarkUpdateDetailsEmptyResults(data);
        });
}

/**
 * Removes a bookmarked plan item from the bookmark detail page
 *
 * @param data - Data needed to removed the object
 */
function removeBookmarkedItem (data) {
    jQuery("#" + data.uid).fadeOut(250, function(){
        jQuery(this).parent().remove();
    });
}

/**
 * Updates the html object containing the total number of bookmarks for the bookmark side bar
 *
 * @param data - Data for the new object
 */
function ksapBookmarkUpdateSidebarTotal (data) {
    //update the count in the sidebar
    if (jQuery("#bookmarkSidebarHeaderCount").length) {
        var bookmarkSidebarCountValue=jQuery("#bookmarkSidebarHeaderCount");
        bookmarkSidebarCountValue.html(data.bookmarkTotal);
    }
}

/**
 * Update the html object containing the "empty results" message in the sidebar footer
 * @param data
 */
function ksapBookmarkUpdateSidebarEmptyResults (data) {
    var sidebarResults = jQuery('#bookmarkSidebarEmptyResults');
    if (data.bookmarkTotal == 0) {
        //show
        sidebarResults.removeClass('ksap-hide');
    }
    else {
        //hide
        sidebarResults.addClass('ksap-hide');
    }

    //Show/hide the "View more details" link in the sidebar footer
    var sidebarFooterLink = jQuery('#bookmark_widget_footer');
    if (data.bookmarkTotal == 0) {
        //show
        sidebarFooterLink.addClass('ksap-hide');
    }
    else {
        //hide
        sidebarFooterLink.removeClass('ksap-hide');
    }
}

/**
 * Updates the html object containing the total number of bookmarks for the bookmark details page
 *
 * @param data - Data for the new object
 */
function ksapBookmarkUpdateDetailsTotal (data) {
    var bookmarkDetailsPageCountValue=jQuery("#bookmarkHeaderCount");

    //update the count on the Bookmark details page
    if (bookmarkDetailsPageCountValue.length) {
        bookmarkDetailsPageCountValue.html(data.bookmarkTotal);
    }
}

/**
 * Update the html object containing the "empty results" message on the Bookmark details page
 * @param data
 */
function ksapBookmarkUpdateDetailsEmptyResults (data) {
    var detailResults = jQuery('#bookmark_detail_list');
    var detailEmptyResults = jQuery('#bookmarkDetailEmptyList');

    if (data.bookmarkTotal == 0) {
        detailResults.addClass('ksap-hide');
        detailEmptyResults.removeClass('ksap-hide');
    }
    else {
        detailResults.removeClass('ksap-hide');
        detailEmptyResults.addClass('ksap-hide');
    }
}