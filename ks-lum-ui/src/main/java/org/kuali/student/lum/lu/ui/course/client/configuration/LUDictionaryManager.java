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

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.dictionary.dto.Field;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dictionary.dto.State;
import org.kuali.student.core.dictionary.dto.Type;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class LUDictionaryManager {
    
    private static final char DICT_KEY_SEPARATOR = ':';
    public static final String STRUCTURE_CLU_INFO = "cluInfo";
    public static final String STRUCTURE_PROPOSAL_INFO = "proposalInfo";
    
    private Validator validator = new Validator();
    private static final Map<String, Map<String, Field>> indexedFields = new HashMap<String, Map<String,Field>>();
    
    
    private static LUDictionaryManager manager = new LUDictionaryManager();
    /**
     * This constructs a ...
     * 
     */
    private LUDictionaryManager() {
        super();
    }

    public static LUDictionaryManager getInstance() {
        return manager;        
    }
    
    public void loadStructure(ObjectStructure structure) {

        Map<String, Field> result = new HashMap<String, Field>();

        for (Type t : structure.getType()) {
            for (State s : t.getState()) {
                for (Field f : s.getField()) {
                    result.put(f.getKey(), f);
                }
                indexedFields.put(structure.getObjectTypeKey().toLowerCase() + DICT_KEY_SEPARATOR + t.getKey().toLowerCase() + DICT_KEY_SEPARATOR +   s.getKey().toLowerCase() , result);
            }
        }
    }

    public Map<String, Field> getFields(String objectKey, String type, String state) {

        return indexedFields.get(objectKey +  DICT_KEY_SEPARATOR +
                type.toLowerCase() + DICT_KEY_SEPARATOR + 
                state.toLowerCase());

    }

    public Field getField(String objectKey, String type, String state, String fieldName) {

        String fieldKey = objectKey.toLowerCase() +  DICT_KEY_SEPARATOR +  type.toLowerCase() + DICT_KEY_SEPARATOR + state.toLowerCase();
        Map<String, Field> map =  indexedFields.get(fieldKey);
        Field f = map.get(fieldName);
        
        return f;

    }

    public Validator getValidator() {
        return validator;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
