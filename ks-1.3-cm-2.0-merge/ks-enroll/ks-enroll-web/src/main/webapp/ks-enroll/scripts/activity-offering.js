
function clearRoomResourcesSelections(sourceLink) {
    var divInputField = sourceLink.siblings('div.uif-inputField');
    divInputField.find('option').each(function() {
            jq(this).removeAttr('selected');
        });
}

function calculatePercent(jqObject){

    if(jqObject) {
        var currentId = jqObject.attr('id');
        if (currentId.indexOf("_control") == -1) {
            currentId = currentId + "_control";
        }
        var element = jQuery('#' + currentId);
        var numValue = element.val();
        var numericExpression = /^[0-9]+$/;
        if(!numValue.match(numericExpression) && numValue != '') {
            if (currentId == 'maximumEnrollment') {
                alert("Maximum enrollment must be a number!");
            } else {
                alert("Number of seats must be a number!");
            }
            element.val('');
        }
    }

//    var seatLimitAdd =  jQuery('#seatLimit_add_control');
//    var seatLimitPercentAdd =  jQuery('#seatLimitPercent_add #_span');
    var maxEnroll =  jQuery('#maximumEnrollment_control');
    var seatpoolCount = jQuery('#seatpoolCount #_span');
    var seatsRemaining = jQuery('#seatsRemaining #_span');

    var count = 0;
    var seatsTotal = 0;
    var rows = jQuery("[id^='seatLimit_line']");
    rows.each(function () {
        var id = jQuery(this).attr('id');
        if(id.indexOf("_control") != -1) {
            var num = id.substring(14, id.length - 8);
            var elemPct = jQuery('#seatLimitPercent_line' + num);
            count += 1;
            if (maxEnroll.val() != "" && jQuery(this).val() != "") {
                seatsTotal = parseInt(seatsTotal) + parseInt(jQuery(this).val());
                var result = (jQuery(this).val() / maxEnroll.val()) * 100;
                elemPct.text(Math.round(result) + "%");
            } else {
                elemPct.text("");
            }
        }
    });
    seatpoolCount.text(count);

    if (maxEnroll.val() != "") {
        var seatsRemain = (maxEnroll.val() > seatsTotal) ? (maxEnroll.val() - seatsTotal) : 0;
        var percTotal = Math.round((seatsTotal / maxEnroll.val()) * 100);
        var percRemain = (seatsRemain > 0) ? (100 - percTotal) : 0;
        seatsRemaining.text(percRemain + "% | " + seatsRemain + " Seats (Max Enrollment = " + maxEnroll.val() + ")");
    } else {
        seatsRemaining.text("");
    }
}