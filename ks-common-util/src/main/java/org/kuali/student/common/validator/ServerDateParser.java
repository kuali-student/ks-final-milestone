package org.kuali.student.common.validator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerDateParser implements DateParser {
    SimpleDateFormat[] formats = {new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"), new SimpleDateFormat("yyyy-MM-dd")};
    
    public Date parseDate(String input) {
        Date result = null;
        
        for (SimpleDateFormat format : formats) {
                try {
                    result = format.parse(input);
//                    System.out.printf("format: %s -> %s%n",format.toPattern(),result); // keeping this here in case I need to debug Java Date/Time again
                } catch (Exception e) {
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

    /**
     * @see org.kuali.student.common.validator.DateParser#toString(java.util.Date)
     */
    @Override
    public String toString(Date date) {
        String result = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss,SSS");
        result = format.format(date);

        return result;        
    }
}
