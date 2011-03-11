/*
* Copyright 2011 The Kuali Foundation
*
* Licensed under the Educational Community License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may	obtain a copy of the License at
*
* 	http://www.osedu.org/licenses/ECL-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.kuali.student.enrollment.lpr.infc;

import java.io.Serializable;

public class LuiPersonRelationCriteriaBean
        implements LuiPersonRelationCriteriaInfc, Serializable {

    private static final long serialVersionUID = 1L;
    private String fieldKey;

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * Name: Field
     * Dot path notation to identity the name of field to be compared
     */
    @Override
    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * Name: Field
     * Dot path notation to identity the name of field to be compared
     */
    @Override
    public String getFieldKey() {
        return this.fieldKey;
    }

    private String value;

    /**
     * Set ????
     * <p/>
     * Type: String
     * <p/>
     * Name: Criteria
     * Value to be compared
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get ????
     * <p/>
     * Type: String
     * <p/>
     * Name: Criteria
     * Value to be compared
     */
    @Override
    public String getValue() {
        return this.value;
    }
}

