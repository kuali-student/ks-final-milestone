package org.kuali.student.common.validator;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class ClientDateParser implements DateParser {
    DateTimeFormat[] formats = {DateTimeFormat.getFormat("yyyy-MM-dd"), DateTimeFormat.getFormat("yyyy-MM-ddTHH:mm:ss,SSS")};
    
    public Date parseDate(String input) {
        Date result = null;
        
        for (DateTimeFormat format : formats) {
                try {
                    result = format.parse(input);
                } catch (IllegalArgumentException e) {
                    // just eat it
                }
                if (result != null) {
                    break;
                }
            
        }
        
        if (result == null) {
            throw new DateParseException("Invalid date value: " + input);
        }
        
        return result;
    }
}
