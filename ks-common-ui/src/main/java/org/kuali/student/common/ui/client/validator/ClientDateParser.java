package org.kuali.student.common.ui.client.validator;

import java.util.Date;

import org.kuali.student.common.validator.DateParseException;
import org.kuali.student.common.validator.DateParser;

import com.google.gwt.i18n.client.DateTimeFormat;

@Deprecated
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
    
    public String toString(Date date){
        String result = null;
        DateTimeFormat format = DateTimeFormat.getFormat("yyyy-MM-ddTHH:mm:ss,SSS");
        result = format.format(date);

        return result;        
    }
    
    
}
