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
