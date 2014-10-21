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
 * Created by Charles on 7/10/12
 */
package org.kuali.student.r2.common.assembler;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public abstract class DynamicAttrReadOnlyWrapper<DtoClass> {
    private Class clazz;
    private DtoClass dtoInstance;
    private String dynAttrName;

    public DynamicAttrReadOnlyWrapper(DtoClass dtoInstance, Class clazz, String dynAttrName) {
        this.dtoInstance = dtoInstance;
        this.clazz = clazz;
        this.dynAttrName = dynAttrName;
    }

    /**
     * Checks to see if this dyn attribute has the same name as the parameter passed in
     * @param name A dyn attribute name (usually in the constants file)
     * @return Whether the string passed in matches the dyn attribute
     */
    public boolean hasDynAttrName(String name) {
        return dynAttrName.equals(name);
    }

    /**
     * @return The name of the dynamic attribute
     */
    public String getDynAttrName() {
        return dynAttrName;
    }

    public DtoClass getDto() {
        return dtoInstance;
    }

    // Writing out a value only requires reading the value from the DtoClass
    public abstract String writeOutStringValue();
}

