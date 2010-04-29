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

package org.kuali.student.common.ui.client.dictionary;

import java.util.StringTokenizer;

public class DictionaryHelper {

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
