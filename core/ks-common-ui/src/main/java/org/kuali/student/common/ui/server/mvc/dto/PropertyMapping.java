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

import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;

/**
 * Defines a custom property mapping between a Java bean property and a ModelDTO attribute 
 * 
 * @author Kuali Student Team
 *
 */
public interface PropertyMapping {
    /**
     * Converts a Java bean property to a ModelDTOValue
     * 
     * @param source the Java bean value to be converted
     * @param context the MapContext to be used for the conversion
     * @return ModelDTOValue representing the Java bean
     * @throws BeanMappingException
     */
    public ModelDTOValue toModelDTOValue(Object source, MapContext context) throws BeanMappingException;
    
    /**
     * Converts a ModelDTOValue to the Java bean value that it represents
     * 
     * @param value the ModelDTOValue to be converted
     * @param context the MapContext to be used for the conversion
     * @return Java bean value as represented by the ModelDTOValue
     * @throws BeanMappingException
     */
    public Object fromModelDTOValue(ModelDTOValue value, MapContext context) throws BeanMappingException;
}
