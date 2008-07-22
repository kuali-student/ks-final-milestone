// TODO include strict numeric validation that is locale aware
var defaultMinValue = -999999999999;
var defaultMaxValue = 999999999999;

var minValue = getAttributeNumber("minValue", defaultMinValue);
var maxValue = getAttributeNumber("maxValue", defaultMaxValue);

var value = getValueString();

if (typeof(value) == "undefined" || value == null || trim(value).length == 0) {
	// hack to work around bug in getAttributeBoolean
	// TODO fix getAttributeBoolean
    if (getAttributeString("required") == "true") {
        addError(getMessage("isRequired"));
    }
} else {
    var num = parseFloat(value);
    if (isNaN(num)) {
        addError(getMessage("mustBeNumeric"));
    } else {
	    if (minValue != defaultMinValue && maxValue != defaultMaxValue) {
	        // validating range
	        if (value < minValue || value > maxValue) {
	            addError(getMessage("failedRange"));
	        }
	    } else {
			if (value < minValue) {
			    addError(getMessage("failedMinValue"));
			}
			
			if (value > maxValue) {
			    addError(getMessage("failedMaxValue"));
			}
	    }
	}
}