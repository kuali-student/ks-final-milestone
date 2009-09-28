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