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

var validCharacters = getAttributeString("validCharacters", "");
if (validCharacters != "") {
	for (var i=0; i<value.length; i++) {
		if (validCharacters.indexOf(value.charAt(i)) == -1) {
			addError(getMessage("failedValidCharacters"));
			break;
		}
	}
}

var invalidCharacters = getAttributeString("invalidCharacters", "");
if (invalidCharacters != "") {
	for (var i=0; i<value.length; i++) {
		if (invalidCharacters.indexOf(value.charAt(i)) != -1) {
			addError(getMessage("failedInvalidCharacters"));
			break;
		}
	}
}


