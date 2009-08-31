/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.configuration;

import java.util.StringTokenizer;

/**
 * This class used by LUM to help with dictionary access.  
 * 
 * Method buildJavaScriptKey cannot be in class LUDictionaryManager as this method needs to be called
 * from a jsp and jsps cannot use 1.5 syntax which is used in other methods in LUDictionaryManager
 * 
 * Separate class created for jsp compatible methods. 
 * 
 * @deprecated
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class LUDictionaryHelper {
    

    /**
     * 
     * This method removes the dots from the supplied String and returns an 'undotted' version
     * 
     * Used in LUMMain.jsp and LUMMainEntryPoint to access the serialized dictionary structure strings.
     * JavaScript doesn't like dots in variable names 
     * 
     * @param javaKey
     * @return
     */public static String buildJavaScriptKey (String javaKey) {
        
        StringTokenizer st=new StringTokenizer(javaKey, ".");
        int count = st.countTokens();

        String words[]=new String [15];
        int i=0; 
        while (st.hasMoreTokens()) {
            words[i]=st.nextToken(); i++;
        }
    
        StringBuffer jsKey = new StringBuffer();
        for (i=0;i<count; i++) {
           jsKey.append(words[i].trim());          
        }
        
        return jsKey.toString();
        
    }

}
