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
package org.kuali.student.core.comment.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 * Contains information about a constraint on a single field in a search.
 *
 * @Author KSContractMojo
 * @Author Neerav Agrawal
 * @Since Fri Jun 05 14:27:54 EDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTR/criterionInfo+Structure+v1.0-rc2">CriterionInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CriterionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private TypeAttributeInfo attribute;

    @XmlElement
    private String operator;

    @XmlElement
    private String value;

    @XmlElement
    private List<Value> values;

    /**
     * The attribute that the constraint should be applied to.
     */
    public TypeAttributeInfo getAttribute() {
        return attribute;
    }

    public void setAttribute(TypeAttributeInfo attribute) {
        this.attribute = attribute;
    }

    /**
     * The operator to use in the constraint.
     */
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * The value to use in the constraint. Use of this field may be constrained by the value of the operator field. Certain combinations may not be syntactically valid.
     */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * A list of values to use in the constraint. Use of this field may be constrained by the value of the operator field. Certain combinations may not be syntactically valid. At least one value should be provided in this listing.
     */
    public List<Value> getValues() {
        if (values == null) {
            values = new ArrayList<Value>(0);
        }
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }
}