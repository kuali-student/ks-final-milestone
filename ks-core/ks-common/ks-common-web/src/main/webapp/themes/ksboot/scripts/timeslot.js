
var selectedTimeSlotOptions;
var availableTimeSlotTypes;

/**
 *  This method initializes the popup for both edit and add action. As we're using the same
 *  popup for both add and edit, it's necessary to tweak the html dom.
 */
function showTimeSlotLightBox(event, addOrEditAction, editLineIndex)
{

    var parentSection;
    if (addOrEditAction == 'EDIT'){
        parentSection = "#KS-TimeSlot-EditTimeSlotPopupForm ";
    } else {
        parentSection = "#KS-TimeSlot-AddTimeSlotPopupForm ";
    }

    jQuery(parentSection + '#addOrEditDays_control').val('');
    jQuery(parentSection + '#addOrEditTermKey_control').val('');
    jQuery(parentSection + '#addOrEditStartTime_control').val('');
    jQuery(parentSection + '#addOrEditStartTimeAmPm_control_0').attr('checked',true);
    jQuery(parentSection + '#addOrEditEndTime_control').val('');
    jQuery(parentSection + '#addOrEditEndTimeAmPm_control_0').attr('checked',true);
    jQuery(parentSection + '#addOrEditTermKey_control').empty();
    jQuery(parentSection + '#addOrEditDays_control').val('');

   if (jQuery(parentSection + "#addOrEditDays_errors").length) {
        jQuery(parentSection + "#addOrEditDays_errors").empty();
    }
    if (jQuery(parentSection + "#addOrEditTermKey_errors").length) {
        jQuery(parentSection + "#addOrEditTermKey_errors").empty();
    }
    if (jQuery(parentSection + "#addOrEditStartTime_errors").length) {
        jQuery(parentSection + "#addOrEditStartTime_errors").empty();
    }
    if (jQuery(parentSection + "#addOrEditStartTimeAmPm_errors").length) {
        jQuery(parentSection + "#addOrEditStartTimeAmPm_errors").empty();
    }
    if (jQuery(parentSection + "#addOrEditEndTime_errors").length) {
        jQuery(parentSection + "#addOrEditEndTime_errors").empty();
    }
    if (jQuery(parentSection + "#addOrEditEndTimeAmPm_errors").length) {
        jQuery(parentSection + "#addOrEditEndTimeAmPm_errors").empty();
    }
    if (jQuery(parentSection + "#addOrEditTermKey_errors").length) {
        jQuery(parentSection + "#addOrEditTermKey_errors").empty();
    }

    if (addOrEditAction == 'EDIT'){

        jQuery(parentSection + '#addOrEdit_action').data("submit_data", {methodToCall:"editTimeSlot","actionParameters[selectedCollectionPath]":"timeSlotResults","actionParameters[actionEvent]":"editCOonManageCOsPage","actionParameters[selectedLineIndex]":editLineIndex});
//        jQuery(parentSection + '#addOrEdit_action').text('Save');
//        jQuery(parentSection + '#addOrEdit_cancel').show();

        var termType = jQuery('#termType_line' + editLineIndex + '_h0').val();
        var days = jQuery('#days_line' + editLineIndex + '_control').text();
        var startTimeWithAmPm = jQuery('#startTime_line' + editLineIndex + '_control').text().trim();

        var split = startTimeWithAmPm.split(" ");
        var startTime = split[0];
        var startTimeAmPm = split[1].trim();

        var endTimeWithAmPm = jQuery('#endTime_line' + editLineIndex + '_control').text().trim();

        var split = endTimeWithAmPm.split(" ");
        var endTime = split[0];
        var endTimeAmPm = split[1].trim();
        var spaceStrippedDays = days.trim().replace(/ /g,"");

        jQuery(parentSection + '#addOrEditDays_control').val(spaceStrippedDays);

        jQuery(parentSection + '#addOrEditStartTime_control').val(startTime);
        if (startTimeAmPm == 'AM'){
            jQuery(parentSection + '#addOrEditStartTimeAmPm_control_0').attr('checked',true);
        } else {
            jQuery(parentSection + '#addOrEditStartTimeAmPm_control_1').attr('checked',true);
        }

        jQuery(parentSection + '#addOrEditEndTime_control').val(endTime);

        if (endTimeAmPm == 'AM'){
            jQuery(parentSection + '#addOrEditEndTimeAmPm_control_0').attr('checked',true);
        } else {
            jQuery(parentSection + '#addOrEditEndTimeAmPm_control_1').attr('checked',true);
        }

        var $options = jQuery("#timeSlotTypeSelection_control > option").clone();
        jQuery(parentSection + "#addOrEditTermKey_control").append($options);
        jQuery(parentSection + '#addOrEditTermKey_control').val(termType);
        var timeSlotCode = jQuery('#timeSlotCode_line' + editLineIndex + '_control').text().trim();
        jQuery(parentSection + ' .uif-headerText-span').text("Edit Time Slot " + timeSlotCode);

        jQuery(parentSection + ".edit_ts_control").removeClass("error");
        jQuery(parentSection + ".edit_ts").removeClass("uif-hasError");

        var overrideOptions = { autoDimensions:true, afterShow:function () {
            jQuery(parentSection + '#addOrEditTermKey_control').focus();
//            initBubblePopups();
        }};

        showLightboxComponent('KS-TimeSlot-EditTimeSlotPopupForm',overrideOptions);

    } else {
//        jQuery(parentSection + '#addOrEdit_action').data("submit_data", {methodToCall:"createTimeSlot"});
//        jQuery(parentSection + '#addOrEdit_action').text('Add Slot');
        jQuery(parentSection + ".new_ts_control").removeClass("error");
        jQuery(parentSection + ".new_ts").removeClass("uif-hasError");

        jQuery(parentSection + "#addOrEditTermKey_control").append(selectedTimeSlotOptions);

        var overrideOptions = { autoDimensions:true, afterShown:function () {
            jQuery(parentSection + '#addOrEditTermKey_control').focus();
        }};
        openPopupContent(event,'KS-TimeSlot-AddTimeSlotPopupForm',overrideOptions);
    }

}

/**
 * Displays the delete confirmation dialog
 */
function showTimeSlotDeleteConfirmation() {
    var overrideOptions = { autoDimensions:false, width:500 };
    showLightboxComponent('deleteTimeSlotsConfirmationDialog-lightbox', overrideOptions);
}

function validateTimeSlot(cssSelector){
    var addEditTSComponents = jQuery('.' + cssSelector);
//    var values = jQuery('.new_ts_control').val();
    var result = validateLineFields(addEditTSComponents);
    if (!result){
        showClientSideErrorNotification();
    } else {
        closeLightbox();
    }
    return result;
}

/**
 * This method marks all the selected option with data attribute selected=true sothat the same
 * options will be used for Add timeslot. This is a workaround when user
 * initially selected types and clicked on show timeslot and then
 * the user selects different type. Relaying on selected types on the
 * list doesnt work in this situation
 */
function setupTimeSlotTypeDropdown(){
    selectedTimeSlotOptions = jQuery("#timeSlotTypeSelection_control option:selected").clone();
    availableTimeSlotTypes = jQuery("#timeSlotTypeSelection_control option").clone();
}

function resetTSValidSelection(){
    jQuery("#timeSlotTypeSelection_control").find('option').remove();
    jQuery("#timeSlotTypeSelection_control").append(availableTimeSlotTypes);
}

function toggleTimeSlotShowButton() {
    var inputVal = jQuery("#timeSlotTypeSelection_control").val();
    if (inputVal !==null) {
        jQuery("#timeSlotShowButton").removeAttr("disabled");
    } else {
        jQuery("#timeSlotShowButton").attr("disabled", "disabled");
    }
}



/**
 * https://jira.kuali.org/browse/KSENROLL-13807
 * Sorting logic for time slot page
 */



/* Define two custom functions (asc and desc) for string sorting */
jQuery.fn.dataTableExt.oSort['days_sort-asc']  = function(a,b) {

    var x = getDaysValueFromCell(a);
    var y = getDaysValueFromCell(b);
    return compareToAsc(x,y);
};

jQuery.fn.dataTableExt.oSort['days_sort-desc'] = function(a,b) {
    var x = getDaysValueFromCell(a);
    var y = getDaysValueFromCell(b);
    return compareToDesc(x,y);
};


jQuery.fn.dataTableExt.oSort['term_type_sort-asc']  = function(x,y) {
    return compareToAsc(getValueFromCell(x),getValueFromCell(y));
};

jQuery.fn.dataTableExt.oSort['term_type_sort-desc'] = function(x,y) {
    return compareToDesc(getValueFromCell(x),getValueFromCell(y));
};


jQuery.fn.dataTableExt.oSort['hour_sort-asc']  = function(x,y) {
    return compareToAsc(convertFormatDate(getValueFromCell(x)),convertFormatDate(getValueFromCell(y)));
};

jQuery.fn.dataTableExt.oSort['hour_sort-desc'] = function(x,y) {
    return compareToDesc(convertFormatDate(getValueFromCell(x)),convertFormatDate(getValueFromCell(y)));
};

function compareToAsc(x,y)
{
    return ((x < y) ? -1 : ((x > y) ?  1 : 0))
}

function compareToDesc(x,y)
{
    return ((x < y) ?  1 : ((x > y) ? -1 : 0));
}


/**
 * get the equivalent day number to the day form the text in the cell
 * of teh dataTable
 */
function getDaysValueFromCell(cell)
{
    return numberEquivalentToDay(getValueFromCell(cell));
}

/**
 * get the text value from teh dataTable
 */
function getValueFromCell(cell)
{
    return jQuery(cell).find("span").text().trim();
}

function numberEquivalentToDay (day)
{
    var finalSt= "";
    for ( var i =0 ; i < day.length;i++)
    {
        finalSt= finalSt + decodeDay(day.substring(i,i+1));
    }
    return finalSt;
}

/**
 *  Decode days in numbers EX: M,F  => 1,5
 */
function decodeDay (day)
{

    if (day==="M")  return "1" ;
    else if (day === "T") return "2" ;
    else if (day === "W") return "3" ;
    else if (day === "H") return "4" ;
    else if (day === "F") return "5" ;
    else if (day === "S") return "6" ;
    else if (day === "U") return "7" ;

}

/**
 * Save the normal sorting by "term_type" and creates the update, save and delete sorting strategy by all
 * the fields of the table which is the default sort used in teh server side
 */

function updateSortTable() {
    var oTable = jQuery('#TimeSlotSearchResultsDisplayTable').find('.dataTable').dataTable();
    // Sort immediately with column

    var term_type_sort_asc = jQuery.fn.dataTableExt.oSort['term_type_sort-asc'];
    var term_type_sort_desc =jQuery.fn.dataTableExt.oSort['term_type_sort-desc'];

    setUpNewSort();

    oTable.fnSort([[2,'asc',0]] );

    jQuery.fn.dataTableExt.oSort['term_type_sort-asc'] = term_type_sort_asc;
    jQuery.fn.dataTableExt.oSort['term_type_sort-desc'] = term_type_sort_desc;

}

/*
 * Creates the new sort strategy by all the fields in the time slot dataTable
 */
function setUpNewSort()
{
    jQuery.fn.dataTableExt.oSort['term_type_sort-asc']  = function(a,b) {

        var x = getObjectTimeSlot(a);
        var y = getObjectTimeSlot(b);

        var diffStartTimeDisplay = compareToAsc (x.startTime, y.startTime);
        var diffEndTimeDisplay = compareToAsc (x.endTime, y.endTime);
        var diffDays = compareToAsc (x.days, y.days);
        var diffTypeName= compareToAsc (x.termType, y.termType);

        if(diffTypeName === 0)
        {
            if(diffDays === 0)
            {
                if( diffStartTimeDisplay === 0)
                {
                    return diffEndTimeDisplay;

                }return  diffStartTimeDisplay;

            }return diffDays;

        }else return  diffTypeName ;

    }

    jQuery.fn.dataTableExt.oSort['term_type_sort-desc'] = function(a,b) {
        var x = getObjectTimeSlot(a);
        var y = getObjectTimeSlot(b);

        var diffStartTimeDisplay = compareToDesc (x.startTime, y.startTime);
        var diffEndTimeDisplay = compareToDesc (x.endTime, y.endTime);
        var diffDays = compareToDesc (x.days, y.days);
        var diffTypeName= compareToDesc (x.termType, y.termType);

        if(diffTypeName === 0)
        {
            if(diffDays === 0)
            {
                if( diffStartTimeDisplay === 0)
                {
                    return diffEndTimeDisplay;

                }return  diffStartTimeDisplay;

            }return diffDays;

        }else return  diffTypeName ;

    };
}


/*
 * Convert the row into a timeSlot object based on teh position of the element in the dataTable
 */
function getObjectTimeSlot(element)
{
    var keyDays = "days_line";
    var keyTimeType = "termType_line";
    var keyStartTime = "startTime_line";
    var keyEndTime ="endTime_line";

    var pos = jQuery(element).attr( "id" ).replace("termType_line","");

    var timeSlot = {
        days:getDaysValueFromCell(jQuery("#"+keyDays+ pos )),
        termType:getValueFromCell(jQuery("#"+ keyTimeType+ pos )),
        startTime:convertFormatDate(getValueFromCell(jQuery("#" +keyStartTime  +pos ))),
        endTime: convertFormatDate(getValueFromCell(jQuery("#"+ keyEndTime+pos )))
    };

    return timeSlot;

}


/**
 * Convert 10:00  to 22:00
 * @param hour
 */
function convertFormatDate(hour)
{
    var hh =  hour.substring(0,2);
    var min = hour.substring(3,5);

    var amORpm = hour.substring(6,8)

    if(amORpm==="PM" && hh!=="12" )
    {
            hh = (parseInt(hh) + 12).toString();

    }else if(amORpm==="AM" && hh==="12")
    {
        hh = "00";
    }

    return hh+min;
}
