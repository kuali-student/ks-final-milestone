/*
 * Copyright 2009 The Kuali Foundation
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
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComparisonInfo", propOrder = {"fieldKey", "operator", "values", "ignoreCase", "_futureElements"})    
public class ComparisonInfo implements Comparison, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String fieldKey;
    
    @XmlElement
    private String operator;
    
    @XmlElementWrapper(name="values")
    @XmlElement(name="value")
    private List<String> values;
    
    @XmlElement
    private boolean ignoreCase;

    @XmlAnyElement
    private List<Element> _futureElements;

    public static ComparisonInfo newInstance() {
        return new ComparisonInfo();
    }
    
    public static ComparisonInfo getInstance(Comparison infc) {
        return new ComparisonInfo(infc);
    }
    
    private ComparisonInfo() {
        this.fieldKey = null;
        this.operator = null;
        this.values = null;
        this.ignoreCase = false;
        this._futureElements = null;
    }

    private ComparisonInfo(Comparison bldr) {
        this.fieldKey = bldr.getFieldKey();
        this.operator = bldr.getOperator();
        if (bldr.getValues() == null)
        {
            this.values = null;
        }else
        {
            this.values = Collections.unmodifiableList(bldr.getValues());
        }
        this.ignoreCase = bldr.isIgnoreCase();
        this._futureElements = null;
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
    public Boolean isIgnoreCase() {
        return this.ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
}
