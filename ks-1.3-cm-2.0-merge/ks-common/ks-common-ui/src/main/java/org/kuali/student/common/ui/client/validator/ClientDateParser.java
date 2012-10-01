/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.validator;

import java.util.Date;

import org.kuali.student.r1.common.validator.DateParseException;
import org.kuali.student.r1.common.validator.DateParser;

import com.google.gwt.i18n.client.DateTimeFormat;

@Deprecated
public class ClientDateParser implements DateParser {
    DateTimeFormat[] formats = {DateTimeFormat.getFormat("yyyy-MM-dd"), DateTimeFormat.getFormat("yyyy-MM-ddTHH:mm:ss,SSS")};
    
    public Date parseDate(String input) {
        Date result = null;
        
        for (DateTimeFormat format : formats) {
                try {
                    result = format.parseStrict(input);
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
