package org.kuali.student.common.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerDateParser implements DateParser {
    SimpleDateFormat[] formats = {new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"), new SimpleDateFormat("yyyy-MM-dd")};
    
    public Date parseDate(String input) {
        Date result = null;
        
        for (SimpleDateFormat format : formats) {
                try {
                    result = format.parse(input);
                } catch (ParseException e) {
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
