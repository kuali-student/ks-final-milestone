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

package org.kuali.student.r1.common.validator;

import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;

import java.util.Date;

@Deprecated
public class ServerDateParser implements DateParser {
   
	private static ThreadLocal<KSDateTimeFormatter[]> formats = new ThreadLocal<KSDateTimeFormatter[]>() {

		protected KSDateTimeFormatter[] initialValue() {
			return new KSDateTimeFormatter[] {
		    		new KSDateTimeFormatter("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
		    		new KSDateTimeFormatter("yyyy-MM-dd"),
		    		new KSDateTimeFormatter("yyyy-MMM-dd"),
		    		new KSDateTimeFormatter("dd-MM-yyyy"),
		    		new KSDateTimeFormatter("dd-MMM-yyyy")
		    };
		}

	};
	
    
    public Date parseDate(String input) {
        Date result = null;
        
        for (KSDateTimeFormatter format : formats.get()) {
                try {
                    result = format.parse(input);
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
     * @see org.kuali.student.r1.common.validator.old.DateParser#toString(java.util.Date)
     */
    @Override
    public String toString(Date date) {
        String result = null;
        result = DateFormatters.SERVER_DATE_PARSER_FORMATTER.format(date);

        return result;        
    }
}
