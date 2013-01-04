/* used by ks-enroll-ksink */

// Allow cross-domain scripting from wiki.kuali.org to work, hopefully:
//if (document.domain != "localhost") document.domain = "kuali.org";
/* if (window.self != window.top) { // must be in a frame
    var dd=String(document.domain).match(/^(?:[a-z0-9-]+\.)*kuali\.org$/);
    //if(dd)document.domain="kuali.org";
    if(dd)document.write("<span style='color:white;'>document.domain = 'kuali.org'</span>");
} */


var originalSelect;
//var tempSelect;
var tempUl;

/* nice on env2.ks.kuali.org, but not wiki.kuali.org
jQuery(document).ready(function() {
    // focus on first text or textarea field
    var fields = jQuery("input[type='text'],textarea");
    if (fields.length > 0) {
        fields.get(0).focus();
    }
});*/

function showProps(id, name, props) {
    var propStr = "Properties for:\t" + name + "\n";
    propStr += "KRAD Property   :   HTML Property\n";

    jQuery.each(props, function (key, value) {
        propStr += key + "    :   " + value + "\n";
    });
    alert(propStr);

}

function togglePropertiesFor(id, target, name, props) {
    var tableId = "Props_" + target;
    if (jQuery('#' + tableId).length > 0) {
        var isPropVisible = jQuery('#' + tableId).is(':visible');
        if (isPropVisible) {
            jQuery('#' + id).prop('value', 'Show Properties');
            jQuery('#' + tableId).hide();
        }
        else {
            jQuery('#' + id).prop('value', 'Hide Properties');
            jQuery('#' + tableId).show();
        }
    } else {
        var table = '<table border="1" id="' + tableId + '">';
        table += '<thead><tr style="background-color: #FFFF99"><th>HTML Property</th><th>KRAD Property</th></tr></thead>'
        jQuery.each(props, function (key, value) {
            table += '<tr><td>' + key + '</td><td>' + value + '</tr>';
        });
        table += '</table>';
        jQuery('#' + target).append(table);
    }
}

function containsValues(control, value) {
    return (jQuery(control).find(":contains('" + value + "')").length > 0)
}

function setFilterBox(textBox, value, target) {
//    jQuery('#' + textBox).val(jQuery(jQuery('#' + control) + 'option:selected').text());
    jQuery('#' + textBox).val(value);
//    jQuery('#' + control).attr('size', "1");
    jQuery('#' + target).hide();
}

function getContainingSelectValues(control, value) {
    if (originalSelect == null) {
        originalSelect = jQuery('#' + control).clone();
    }
//    if (tempSelect == null) {
//        tempSelect = jQuery('#' + control).clone();
//    }

    var items = jQuery(originalSelect).children('option').filter(function (i, e) {
        if (e.innerText.indexOf(jQuery('#' + value).val()) != -1) {
            return e.innerText;
        }
    });
    return items;
}

function expandSelectBox(dropdown, textBox, target) {
    jQuery('#' + textBox).val('');
    filterSelectBox(dropdown, textBox, target);
}

function filterSelectBox(dropdown, textBox, target) {

    var values = getContainingSelectValues(dropdown, textBox);
    jQuery('#ul_id_temp li').remove();
    createLinks(textBox, values, target);

    if (jQuery('#ul_id_temp').length > 0) {
        var isPropVisible = jQuery('#' + target).is(':visible');
        if (isPropVisible) {
            jQuery('#' + target).hide();
        }
        else {
            jQuery('#' + target).show();
        }
    } else {
        jQuery('#' + target).append(tempUl);
    }
}

function createLinks(textBox, values, target) {
    if (tempUl == null) {
        tempUl = jQuery('<ul/>', {id:"ul_id_temp" });
    }
    jQuery(values).each(function () {
        tempUl.append(jQuery('<li/>', {onclick: "setFilterBox('" + textBox + "','" + jQuery(this).text() + "','" + target + "');", text: jQuery(this).text() }));
    });
}