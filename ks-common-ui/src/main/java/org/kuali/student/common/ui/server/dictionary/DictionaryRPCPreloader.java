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
package org.kuali.student.common.ui.server.dictionary;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.ui.server.serialization.KSSerializationPolicy;
import org.kuali.student.common.ui.server.serialization.SerializationUtils;
import org.kuali.student.core.dictionary.dto.CaseConstraint;
import org.kuali.student.core.dictionary.dto.ConstraintDescriptor;
import org.kuali.student.core.dictionary.dto.ConstraintSelector;
import org.kuali.student.core.dictionary.dto.Context;
import org.kuali.student.core.dictionary.dto.Dictionary;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.core.dictionary.dto.LookupConstraint;
import org.kuali.student.core.dictionary.dto.LookupKeyConstraint;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.OccursConstraint;
import org.kuali.student.core.dictionary.dto.RequireConstraint;
import org.kuali.student.core.dictionary.dto.SearchSelector;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;
import org.kuali.student.core.dictionary.dto.TypeStateCaseConstraint;
import org.kuali.student.core.dictionary.dto.TypeStateWhenConstraint;
import org.kuali.student.core.dictionary.dto.ValidCharsConstraint;
import org.kuali.student.core.dictionary.dto.WhenConstraint;
import org.kuali.student.core.dictionary.service.DictionaryService;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;

public class DictionaryRPCPreloader {

    DictionaryService dictionaryService ;
    Map<Class<?>, Boolean> whitelist;
    KSSerializationPolicy myPolicy;
    
    private String serviceName;


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
     * Note: Cannot currently use Java 5 constructs e.g. generics, as this class is called from a jsp.
     * Hosted mode runs Java 1.4 jsp compiler so does not handle generics.
     *  
     */
    public DictionaryRPCPreloader(String serviceName) {
        super();
        this.serviceName = serviceName;
        buildWhitelist();
        myPolicy = new KSSerializationPolicy(whitelist);
    }

    /**
     * 
     * This method passes the supplied object key to the dictionary implementation in this domain 
     * and returns the result as a serialized string 
     * 
     * @param    objectKey   Name of dictionary ObjectStructure
     * @return               Serialized string of the dictionary def for this structure
     *  
     */public String getObjectStructureEncodedString(String objectKey){

         String result = null;
         try {             
             Method serviceMethod = DictionaryService.class.getMethod("getObjectStructure", String.class);
             ObjectStructure structure = getDictionaryService().getObjectStructure(objectKey);

             result = serializeData(serviceMethod, structure);               

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

     /**
      * 
      * This method returns a list of ObjectStructure types defined in this domain
      * as a serialized string 
      * 
      * @return       Serialized string of the ObjectStructure names defined in this domain
      * 
      */public String getObjectTypesEncodedString(){


          String result = null;
          try {             
              Method serviceMethod = DictionaryService.class.getMethod("getObjectTypes");
              List<String> types = getDictionaryService().getObjectTypes();

              result = serializeData(serviceMethod, types);               

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
      
     /**
      * 
      * This method returns an array of ObjectStructure types defined in this domain
      * 
      * @return    String[] of the ObjectStructure names defined in this domain
      */
     public String[] getObjectTypes(){

         String[] strResult = null;  
         try {             
             List<String> result = null;
             result = dictionaryService.getObjectTypes();
             result = getDictionaryService().getObjectTypes();
             strResult=new String[result.size()];  
             result.toArray(strResult);

         } catch (SecurityException e) {
             e.printStackTrace();
         }
         return  strResult;

     }

//   /**
//   * Note: This method not yet ready for use!
//   * 
//   * This method passes the supplied object keys to the dictionary implementation in this domain 
//   * and returns an array of serialized strings 
//   * 
//   * @param objectKey name of dictionary ObjectStructure
//   * @return
//   */public String[] getObjectStructuresEncodedString(String[] objectKeys){

//   String[] result = null;
//   ApplicationContext context = new ClassPathXmlApplicationContext("gwt-context.xml");
//   try {             
//   dictService = (DictionaryService)context.getBean(serviceName);
//   Method serviceMethod = DictionaryService.class.getMethod("getObjectStructure", String.class);

//   List structures = new ArrayList();

//   for (String key: objectKeys) {
//   ObjectStructure structure = dictService.getObjectStructure(key);
//   String s = serializeData(serviceMethod, structure); 
//   structures.add(s);

//   }
//   result = (String[])structures.toArray();


//   } catch (SecurityException e) {
//   e.printStackTrace();
//   } catch (NoSuchMethodException e) {
//   e.printStackTrace();
//   } catch (SerializationException e) {
//   System.out.println(e.getMessage());
//   e.printStackTrace();
//   }

//   return result;
//   }

     private String serializeData(Method serviceMethod, Object object) throws SerializationException {
         String serializedData = RPC.encodeResponseForSuccess(serviceMethod, object, myPolicy);
         return  SerializationUtils.escapeForSingleQuotedJavaScriptString(serializedData);
     }
     
     private void buildWhitelist() {
         whitelist = new HashMap<Class<?>, Boolean>();
         whitelist.put(CaseConstraint.class, true);
         whitelist.put(ConstraintDescriptor.class, true);
         whitelist.put(ConstraintSelector.class, true);
         whitelist.put(Context.class, true);
         whitelist.put(Dictionary.class, true);
         whitelist.put(Field.class, true);
         whitelist.put(FieldDescriptor.class, true);
         whitelist.put(LookupConstraint.class, true);
         whitelist.put(LookupKeyConstraint.class, true);
         whitelist.put(ObjectStructure.class, true);
         whitelist.put(OccursConstraint.class, true);
         whitelist.put(RequireConstraint.class, true);
         whitelist.put(SearchSelector.class, true);
         whitelist.put(State.class, true);
         whitelist.put(Type.class, true);
         whitelist.put(TypeStateCaseConstraint.class, true);
         whitelist.put(TypeStateWhenConstraint.class, true);
         whitelist.put(ValidCharsConstraint.class, true);
         whitelist.put(WhenConstraint.class, true);

     }

    public DictionaryService getDictionaryService() {
        if (dictionaryService == null){
            dictionaryService = (DictionaryService)GlobalResourceLoader.getService(QName.valueOf(serviceName));
        }
        return dictionaryService;
    }

    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

}
