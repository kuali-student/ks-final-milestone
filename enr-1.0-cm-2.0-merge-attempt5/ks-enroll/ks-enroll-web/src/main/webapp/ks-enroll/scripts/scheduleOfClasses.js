var originalRowHeight;
var openImage = "../ks-enroll/images/details_open.png";
var closeImage = "../ks-enroll/images/details_close.png";

function ajaxCallActivityOfferings(controllerMethod, courseOfferingId, description, url) {

    var divTempId = 'findThisId_' + courseOfferingId;
    var image = jQuery('#' + courseOfferingId).children('img');
    var isDivVisible = jQuery('#' + divTempId).is(':visible');

    if (isDivVisible) {
        var tr = jQuery(jQuery('#' + courseOfferingId)).parents('tr');
        var height = window.originalRowHeight;
        jQuery('#' + divTempId).slideUp(1000).remove();
        jQuery(tr).css("height", height);
        image.attr("src", openImage);
    } else {
        var formData = jQuery('#kualiForm').serialize() + "&courseOfferingId=" + courseOfferingId + "&displayCoId=" + displayCoId + "&displayCoIdAdd=" + displayCoIdAdd;
        jQuery.ajax({
            url:url + "/kr-krad/scheduleOfClassesSearch/populateAjaxAos.do",
            type:"GET",
            data:formData,
            // callback handler that will be called on success
            success:function (data, textStatus, jqXHR) {
                updateTable(data, courseOfferingId, description);
            }
        });
    }

    function updateTable(data, courseOfferingId, description) {
        var scheduleOfClassesSearchResults = jQuery(data).find('#scheduleOfClassesSearchResults');
        var div = jQuery('<div/>');
        div.attr('id', divTempId);
        div.html(scheduleOfClassesSearchResults.html());
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
        jQuery(tr).css("height", "+=" + divHeight);
        var image = jQuery('#' + courseOfferingId).children('img');
        image.attr("src", closeImage);
    }
}

function toggleShowButton() {
    var termCodeText = jQuery("#termCode_control").find(":selected").text();
    var searchTypeVal = jQuery("#searchType_control").find(":selected");
    var searchTypeText = searchTypeVal.text();
    var searchTextBoxVal = '';
    switch (searchTypeVal.val()) {
        case 'course' :
            searchTextBoxVal = jQuery("#course_search_text_control").val();
            break;
        case 'department' :
            searchTextBoxVal = jQuery("#department_search_text_control").val();
            break;
        case 'instructor' :
            searchTextBoxVal = jQuery("#instructor_search_text_control").val();
            break;
        case 'titleDesc' :
            searchTextBoxVal = jQuery("#title_description_search_text_control").val();
            break;
    }

    if (termCodeText != '' && searchTypeText != '' && searchTextBoxVal != '') {
        jQuery("#show_button").removeAttr("disabled");
    } else {
        jQuery("#show_button").attr("disabled", "disabled");
    }
}