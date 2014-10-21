/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 7/9/12
 */
package org.kuali.student.r2.common.assembler;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public abstract class DynamicAttrWrapper<DtoClass> extends DynamicAttrReadOnlyWrapper<DtoClass> {
    public DynamicAttrWrapper(DtoClass dtoInstance, Class clazz, String dynAttrName) {
        super(dtoInstance, clazz, dynAttrName);
    }

    /**
     * Reads in the string value from the EntityAttribute, uses a converter internally to store
     * it in the proper dynamic attribute in the DTO.
     * This modifies the DTO.
     * @param attrValue The string value as stored in the entity attribute
     */
    public abstract void readInStringValue(String attrValue);
}
