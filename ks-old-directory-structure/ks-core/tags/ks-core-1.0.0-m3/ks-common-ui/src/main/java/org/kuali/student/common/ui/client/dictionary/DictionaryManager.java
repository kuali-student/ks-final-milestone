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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;

import com.google.gwt.core.client.GWT;

/**
 * This singleton class is a repository for dictionary defs.   
 * This class holds a cache of dictionary defs loaded by an external process
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class DictionaryManager {

    private static final char DICT_KEY_SEPARATOR = ':';

    private Validator validator = null;//FIXME! inject this somehow new Validator(); 
    private Map<String, Map<String, Field>> indexedFields = new HashMap<String, Map<String,Field>>();


    private static DictionaryManager manager = new DictionaryManager();
    /**
     * This constructs a ...
     * 
     */
    private DictionaryManager() {
        super();
    }

    public static DictionaryManager getInstance() {
        return manager;        
    }

    /**
     * This method will load the field definitions from a dictionary ObjectStructure into a HashMap
     * keyed by ObjectStructure name e.g. cluInfo, proposalInfo, type, e.g. Credit Course, Program, etc. and state, e.g. Draft, Inactive.
     * 
     *  Map may then be accessed later to retrieve field definitions for any object of a particular type
     *  in a particular state, e.g. cluIdentifierInfo for a Proposed Non-Credit Course  
     *  
     *  @param  structure  The ObjectStructure to be loaded into the cache
     * 
     */
    public void loadStructure(ObjectStructure structure) {

        Map<String, Field> result = null ;

        for (Type t : structure.getType()) {
            for (State s : t.getState()) {
                result = new HashMap<String, Field>();
                for (Field f : s.getField()) {
                    if (f.getFieldDescriptor().getObjectStructure() != null) {
                        loadSubStructure(result, f);
                    }
                    
                    result.put(f.getKey().toLowerCase(), f);
                }                
                indexedFields.put(structure.getKey().toLowerCase() + DICT_KEY_SEPARATOR + t.getKey().toLowerCase() + DICT_KEY_SEPARATOR +   s.getKey().toLowerCase() , result);
            }
       }
       GWT.log("DictionaryManager loaded type:" + structure.getKey(), null);

    }

    public void loadSubStructure(Map<String, Field> result, Field field) {

        for (Type t : field.getFieldDescriptor().getObjectStructure().getType()) {
            for (State s : t.getState()) {
                if (!s.getField().isEmpty()) {
                    if (s.getKey().equals("active")) { // temp while we work out how to handle diff states of cluIdentifierInfo
                        for (Field f : s.getField()) {
                            result.put(field.getKey().toLowerCase() + '.' + f.getKey().toLowerCase(), f);
//                            GWT.log(field.getKey() + "." + f.getKey(), null);
                        }
                    }
                }

                result.put(field.getKey().toLowerCase(), field);
//                GWT.log(field.getKey(), null);
            }
        }
    }

    /**
     *  Returns Map of all Field definitions for a particular object of a particular type in a 
     *  particular state, e.g.  fields for cluInfo for a Proposed Non-Credit Course, keyed by
     *  field name
     *  
     *  @param  objectKey  The name of the ObjectStructure defs to be returned
     *  @param  type       The object type that further qualifies the defs to be returned
     *  @param  state      The state that further qualifies the defs to be returned
     *  @return            A map of the field defs for this object/type/state combo 
     *    
     */
    public Map<String, Field> getFields(String objectKey, String type, String state) {

        return indexedFields.get(objectKey.toLowerCase() +  DICT_KEY_SEPARATOR +
                type.toLowerCase() + DICT_KEY_SEPARATOR + 
                state.toLowerCase());

    }

    /**
     *  Returns Field definition for one field in an object of a particular type in a 
     *  particular state, e.g.  def for longName for cluInfo for a Proposed Non-Credit Course. 
     *  
     *  @param  objectKey  The name of the ObjectStructure defs to be returned
     *  @param  type       The object type that further qualifies the defs to be returned
     *  @param  state      The state that further qualifies the defs to be returned
     *  @param  fieldName  The specific field def to be returned
     *  @return            A map of the field defs for this object/type/state combo 
     *    
     */     
    public Field getField(String objectKey, String type, String state, String fieldName) {

        String fieldKey = objectKey.toLowerCase() +  DICT_KEY_SEPARATOR +  type.toLowerCase() + DICT_KEY_SEPARATOR + state.toLowerCase();
        Map<String, Field> map =  indexedFields.get(fieldKey);
        if(map!=null){
            Field f = map.get(fieldName.toLowerCase());
            return f;
        }

        //TODO what should be returned if this field def exists but not for this type/state combo?

        //Fix me default in case something bad happened
        Field f = null;
        /*f.setFieldDescriptor(new FieldDescriptor());
        f.getFieldDescriptor().setName(fieldName);*/
        return f;
    }

    public Set<String> getTypes() {
        return indexedFields.keySet();
    }
    
    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
    
    
}
