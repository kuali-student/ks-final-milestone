package org.kuali.student.common.ui.server.dictionary;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.server.serialization.KSSerializationPolicy;
import org.kuali.student.common.ui.server.serialization.SerializationUtils;
import org.kuali.student.core.dictionary.dto.Context;
import org.kuali.student.core.dictionary.dto.ContextDescriptor;
import org.kuali.student.core.dictionary.dto.ContextValueDescriptor;
import org.kuali.student.core.dictionary.dto.Dictionary;
import org.kuali.student.core.dictionary.dto.Enum;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dictionary.dto.FieldItem;
import org.kuali.student.core.dictionary.dto.ObjectFactory;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;

public class DictionaryRPCPreloader {

    DictionaryService dictService ;
    String serviceName;

    /**
     * This class calls dictionary methods and returns the results as a serialized String. This String may then be stored
     * in a JavaScript variable to make the result available to the GWT code executing in the browser  
     * 
     * As each business domain may have the dictionary methods implemented by a different business service,
     * the ctor requires that the implementing service name is passed in. This name should be the bean name
     * for the service that has implemented the dictionary methods in this 'domain'.
     * 
     * This class may be called from the main entry jsp for the domain, e.g. LUMMain.jsp
     *  
     */
    public DictionaryRPCPreloader(String serviceName) {
        //TODO Make it more flexible by defining a dictionaryImpl in each domain so that this class 
        // always just looks up dictionaryImpl. Service name wouldnh't have to be passed around then.
        super();
        this.serviceName = serviceName;
    }

    /**
     * 
     * This method passes the supplied parameter to the dictionary implementation in this domain 
     * 
     * @param objectKey name of dictionary ObjectStructure
     * @return
     */public String getObjectStructuresEncodedString(String objectKey){

         String result = null;
         ApplicationContext context = new ClassPathXmlApplicationContext("gwt-context.xml");
         try {             
             dictService = (DictionaryService)context.getBean(serviceName);

             Method serviceMethod = DictionaryService.class.getMethod("getObjectStructure", String.class);

             ObjectStructure structure = dictService.getObjectStructure(objectKey);

             Map<Class<?>, Boolean> whitelist = new HashMap<Class<?>, Boolean>();
             whitelist.put(DictionaryService.class, true);
             whitelist.put(Context.class, true);
             whitelist.put(ContextDescriptor.class, true);
             whitelist.put(ContextValueDescriptor.class, true);
             whitelist.put(Dictionary.class, true);
             whitelist.put(Enum.class, true);
             whitelist.put(Field.class, true);
             whitelist.put(FieldDescriptor.class, true);
             whitelist.put(FieldItem.class, true);
             whitelist.put(ObjectFactory.class, true);
             whitelist.put(ObjectStructure.class, true);
             whitelist.put(State.class, true);
             whitelist.put(Type.class, true);

             KSSerializationPolicy myPolicy = new KSSerializationPolicy(whitelist);

             String serializedData = RPC.encodeResponseForSuccess(serviceMethod, structure, myPolicy);

             result =  SerializationUtils.escapeForSingleQuotedJavaScriptString(serializedData);               

         } catch (SecurityException e) {
             e.printStackTrace();
         } catch (NoSuchMethodException e) {
             e.printStackTrace();
         } catch (SerializationException e) {
             System.out.println(e.getMessage());
             e.printStackTrace();
         }

         return result;
     }


}
