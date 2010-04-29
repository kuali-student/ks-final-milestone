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

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;

/**
 * Defines methods used to convert between ModelDTO and Java beans.
 * 
 * @author Kuali Student Team
 *
 */
/**
 * This is a description of what this class does - wilj don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public interface BeanMapper {
    /**
     * 
     * Recursively converts a Java bean into a ModelDTO
     * 
     * @param value the Java bean to be converted
     * @param context the mapping context for the recursive operation
     * @return ModelDTO containing recursive copy of the Java bean
     * @throws BeanMappingException
     */
    public ModelDTO fromBean(Object value, MapContext context) throws BeanMappingException;
    
    /**
     * Recursively converts a ModelDTO into the Java bean structure that it represents 
     * 
     * @param value the ModelDTO to be converted
     * @param context the mapping context for the recursive operation
     * @return Java bean structure that the ModelDTO structure represents
     * @throws BeanMappingException
     */
    public Object fromModelDTO(ModelDTO value, MapContext context) throws BeanMappingException;
    
    /**
     * 
     * Adds a custom property mapping for a given bean property
     * 
     * @param key the name of the bean property that this mapping is associated with
     * @param mapping the PropertyMapping object used for the conversion
     */
    public void addPropertyMapping(String key, PropertyMapping mapping);
}
