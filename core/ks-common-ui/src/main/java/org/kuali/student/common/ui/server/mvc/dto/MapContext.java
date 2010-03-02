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
package org.kuali.student.common.ui.server.mvc.dto;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;

/**
 * Defines the set of BeanMapper instances to be used for a bean <-> ModelDTO conversion 
 * 
 * @author Kuali Student Team
 *
 */
public class MapContext {
    private final DefaultBeanMapper defaultMapperInstance = new DefaultBeanMapper();
    private Map<String, BeanMapper> mappings = new HashMap<String, BeanMapper>();
    
    
    /**
     * Associates a BeanMapper instance with a given classname
     * 
     * @param className the name of the Java bean class that this mapper is associated with
     * @param mapper the BeanMapper to use for conversion
     */
    public void addBeanMapper(String className, BeanMapper mapper) {
        mappings.put(className, mapper);
    }
    
    
    /**
     * Returns the BeanMapper associated with the specified classname
     * 
     * @param className the name of the Java bean class
     * @return
     */
    public BeanMapper getBeanMapper(String className) {
        return getBeanMapper(className, null);
    }
    
    /**
     * Returns the BeanMapper associated with the specified classname.
     * If the specified class does not have a custom BeanMapper, then the specified defaultMapper is returned
     * 
     * @param className
     * @param defaultMapper
     * @return
     */
    public BeanMapper getBeanMapper(String className, BeanMapper defaultMapper) {
        BeanMapper result = mappings.get(className);
        if (result == null) {
            result = defaultMapper;
        }
        return result;
    }
    
    
    /**
     * Converts a Java bean into a ModelDTO
     * 
     * @param value the Java bean to be converted
     * @return ModelDTO representing a recursive copy of the Java bean structure
     * @throws BeanMappingException
     */
    public ModelDTO fromBean(Object value) throws BeanMappingException {
        ModelDTO result = null;
        
        if (value != null) {
            BeanMapper mapper = getBeanMapper(value.getClass().getName(), defaultMapperInstance);
            result = mapper.fromBean(value, this);
        }
        
        return result;
    }
    
    /**
     * Converts a ModelDTO structure into a Java bean structure
     * 
     * @param value the ModelDTO to be converted
     * @return Java bean structure as represented by the ModelDTO
     * @throws BeanMappingException
     */
    public Object fromModelDTO(ModelDTO value) throws BeanMappingException {
        Object result = null;
        
        if (value != null) {
            BeanMapper mapper = getBeanMapper(value.getClassName(), defaultMapperInstance);
            result = mapper.fromModelDTO(value, this);
        }
        
        return result;
    }
}
