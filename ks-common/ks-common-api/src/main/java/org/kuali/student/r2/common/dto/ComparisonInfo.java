/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.Comparison;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComparisonInfo", propOrder = {
                "fieldKey", "operator", "values", "isIgnoreCase" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code

public class ComparisonInfo 
    implements Comparison, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String fieldKey;
    
    @XmlElement
    private String operator;
    
    @XmlElement
    private List<String> values;
    
    @XmlElement
    private boolean isIgnoreCase;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;


    /**
     * Constructs a new ComparisonInfo.
     */
    public ComparisonInfo() {
    }

    /**
     * Constructs a new ComparisonInfo from another Comparison.
     *
     * @param comparison the comparison to copy
     */
    public ComparisonInfo(Comparison comparison) {
        this.fieldKey = comparison.getFieldKey();
        this.operator = comparison.getOperator();

        if (comparison.getValues() != null) {
            this.values = Collections.unmodifiableList(comparison.getValues());
        }

        this.isIgnoreCase = comparison.getIsIgnoreCase();
    }

    @Override
    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    @Override
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = new ArrayList<String>(values);
    }

    @Override
    public Boolean getIsIgnoreCase() {
        return this.isIgnoreCase;
    }

    public void setIgnoreCase(Boolean ignoreCase) {
        this.isIgnoreCase = ignoreCase;
    }
}
