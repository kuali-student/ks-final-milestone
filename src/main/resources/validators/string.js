var failedMinLengthCheck = false;
var minLength = getAttributeNumber("minLength", 0);
var maxLength = getAttributeNumber("maxLength", 9999999999);

var value = getValueString("");
if (typeof(value) == "undefined" || value == null) {
    value = "";
}
value = trim(value);

if (value.length < minLength) {
    addError(getMessage("failedMinLength"));
    failedMinLengthCheck = true;
}

if (value.length > maxLength) {
    addError(getMessage("failedMaxLength"));
}

if (failedMinLengthCheck == false && getAttributeBoolean("required", false) && value == "") {
    addError(getMessage("isRequired"));
}
