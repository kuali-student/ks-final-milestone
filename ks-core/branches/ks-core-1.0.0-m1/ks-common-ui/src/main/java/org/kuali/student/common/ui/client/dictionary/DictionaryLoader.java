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
package org.kuali.student.common.ui.client.dictionary;

import java.util.List;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.core.dictionary.dto.ObjectStructure;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamFactory;

public class DictionaryLoader {
   
    public static void loadDictionary() {
        try {
            List<String> types = getDictSerializedObject("objectTypes");
            for (String key: types) {
                ObjectStructure structure =  getDictSerializedObject( DictionaryHelper.buildJavaScriptKey(key));
                DictionaryManager.getInstance().loadStructure(structure);
                Application.getApplicationContext().addDictionaryData(key, structure);
            }
        } catch (SerializationException e) {
            GWT.log("loadDictionary failed " ,  e);
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T getDictSerializedObject(String key  ) throws SerializationException
    {
        SerializationStreamFactory ssf = GWT.create( BaseRpcService.class); // magic
        String serialized = getString( key );
        return (T)ssf.createStreamReader( serialized ).readObject();
    }
    
    public static native String getString(String name) /*-{
        return eval("$wnd."+name);
    }-*/;
}
