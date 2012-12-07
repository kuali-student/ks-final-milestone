var originalRowHeight;
var openImage = "../ks-enroll/images/details_open.png";
var closeImage = "../ks-enroll/images/details_close.png";

function ajaxCallActivityOfferings(controllerMethod, courseOfferingId, description) {
    var divTempId = 'findThisId_' + courseOfferingId;
    var image = jQuery('#' + courseOfferingId).children('img');

    var isDivVisible = jQuery('#' + divTempId).is(':visible');
    if (isDivVisible) {
        var tr = jQuery(jQuery('#' + courseOfferingId)).parents('tr');
        var height = window.originalRowHeight;
        jQuery('#' + divTempId).slideUp(1000).remove();
        jQuery(tr).css("height", height);
        image.attr("src", openImage);

        // Temporal solution to display 2 AO lists simultaneously.
        var displayCoId = jQuery('span[id=displayCoId]').text().trim();
        if (courseOfferingId == displayCoId) {
            jQuery('span[id=displayCoId]').text('');
        } else {
            jQuery('span[id=displayCoIdAdd]').text('');
        }
    }
    else {
        // Temporal solution to display 2 AO lists simultaneously.
        var displayCoId = jQuery('span[id=displayCoId]').text().trim();
        var displayCoIdAdd = jQuery('span[id=displayCoIdAdd]').text().trim();

        var kradRequest = new KradRequest();

        kradRequest.methodToCall = controllerMethod;
        kradRequest.additionalData = {courseOfferingId:courseOfferingId, displayCoId:displayCoId, displayCoIdAdd:displayCoIdAdd};
        kradRequest.successCallback = updateTable;
        kradRequest.elementToBlock = jQuery('#' + courseOfferingId);
        kradRequest.ajaxReturnType = 'update-page';
        kradRequest.send();
    }

    function updateTable() {
        if (jQuery('#scheduleOfClassesSearchResults').length > 0) {
            var rowContent = jQuery('#scheduleOfClassesSearchResults').html();
            var div = jQuery('<div/>');
            div.attr('id', divTempId);
            div.html(rowContent);
            var descPara = jQuery('</p>');
            jQuery(descPara).css('margin', '15px 0px');
            descPara.html(description);
            div.prepend(descPara);

            // Calculate the height of the row containing the new div           
            var tr = jQuery(jQuery('#' + courseOfferingId)).parents('tr');
            var td = jQuery(jQuery('#' + courseOfferingId)).parents('td');
            td = jQuery(td).next();
            var trHeight = jQuery(tr).height();
            window.originalRowHeight = trHeight;
            jQuery(div).css("position", "absolute");
            var bgc = jQuery(tr).css('background-color');
            jQuery(div).css("background-color", bgc);
            jQuery(div).css("min-width", "600px");
            jQuery(div).css("max-width", "960px");
            td.slideDown(1000).append(div);
            var divHeight = jQuery(div).height();
            console.log(trHeight + ", " + divHeight);
            jQuery(tr).css("height", "+=" + divHeight);
            var image = jQuery('#' + courseOfferingId).children('img');
            image.attr("src", closeImage);
        }

         // Temporal solution to display 2 AO lists simultaneously.
        var courseOfferingIdAdd = jQuery('span[id=displayCoIdAdd]').text().trim();
        var divTempIdAdd = 'findThisId_' + courseOfferingIdAdd;

        if (courseOfferingIdAdd != null && courseOfferingIdAdd.length > 0 && jQuery('#scheduleOfClassesSearchResultsAdd').length > 0) {
            var rowContent = jQuery('#scheduleOfClassesSearchResultsAdd').html();
            var div = jQuery('<div/>');
            div.attr('id', divTempIdAdd);
            div.html(rowContent);
            var descPara = jQuery('</p>');
            jQuery(descPara).css('margin', '15px 0px');
            descPara.html(description);
            div.prepend(descPara);

            // Calculate the height of the row containing the new div
            var tr = jQuery(jQuery('#' + courseOfferingIdAdd)).parents('tr');
            var td = jQuery(jQuery('#' + courseOfferingIdAdd)).parents('td');
            td = jQuery(td).next();
            var trHeight = jQuery(tr).height();
            window.originalRowHeight = trHeight;
            jQuery(div).css("position", "absolute");
            var bgc = jQuery(tr).css('background-color');
            jQuery(div).css("background-color", bgc);
            jQuery(div).css("min-width", "600px");
            jQuery(div).css("max-width", "960px");
            td.slideDown(1000).append(div);
            var divHeight = jQuery(div).height();
            console.log(trHeight + ", " + divHeight);
            jQuery(tr).css("height", "+=" + divHeight);
            var image = jQuery('#' + courseOfferingIdAdd).children('img');
            image.attr("src", closeImage);
        }
    }
}

function toggleShowButton() {
    var termCodeText = jQuery("#termCode_control").find(":selected").text();
    var searchTypeVal = jQuery("#searchType_control").find(":selected");
    var searchTypeText = searchTypeVal.text();
    var searchTextBoxVal = '';
    console.log(searchTypeVal.val());
    switch(searchTypeVal.val()){
        case 'course' : searchTextBoxVal = jQuery("#course_search_text_control").val(); break;
        case 'department' : searchTextBoxVal = jQuery("#department_search_text_control").val(); break;
        case 'instructor' : searchTextBoxVal = jQuery("#instructor_search_text_control").val(); break;
        case 'titleDesc' : searchTextBoxVal = jQuery("#title_description_search_text_control").val(); break;
    }

    if (termCodeText != '' && searchTypeText != '' && searchTextBoxVal != '') {
        jQuery("#show_button").removeAttr("disabled");
    } else {
        jQuery("#show_button").attr("disabled", "disabled");
    }
}