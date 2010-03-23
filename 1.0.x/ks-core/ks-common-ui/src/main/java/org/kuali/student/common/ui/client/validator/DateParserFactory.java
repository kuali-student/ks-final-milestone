/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import org.kuali.student.common.validator.DateParser;
import org.kuali.student.common.validator.ServerDateParser;

import com.google.gwt.core.client.GWT;

/**
 * This will return a DateParser based on if date parsing is being down GWT client
 * side or server side 
 * 
 * FIXME:
 *  Is there a way to implement this 1) without requiring gwt dependencies for ks-common-util
 *  and 2) without requiring an inherit on the ServerDateParser for gwt modules. Currently 
 *  implemented via method 2.
 *  
 * @author Kuali Student Team
 *
 */
public class DateParserFactory {
    public static DateParser parser = null;
    
    public static DateParser getDateParser(){
        if (parser == null){
            if (GWT.isClient()){
                parser = new ClientDateParser();
            } else {
                parser = new ServerDateParser();
            }
        }
        
        return parser;
    }
}
