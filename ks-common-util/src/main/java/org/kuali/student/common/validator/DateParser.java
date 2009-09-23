package org.kuali.student.common.validator;

import java.util.Date;

public interface DateParser {
    public Date parseDate(String input);
    
    public String toString(Date date);
    
}
