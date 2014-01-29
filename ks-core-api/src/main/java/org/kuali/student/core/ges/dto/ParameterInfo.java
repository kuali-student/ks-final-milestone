/*
 * Copyright 2013 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.ges.dto;

import org.kuali.student.core.ges.infc.Parameter;
import org.kuali.student.core.ges.service.ValueType;
import org.kuali.student.r2.common.dto.KeyEntityInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterInfo", propOrder = {"key", "typeKey", "stateKey","name","descr",
        "gesValueType","requireUniquePriorities", "meta", "attributes", "_futureElements" })
public class ParameterInfo extends KeyEntityInfo implements Parameter {

    @XmlElement
    private ValueType gesValueType;
    @XmlElement
    private Boolean requireUniquePriorities;
    @XmlAnyElement
    private List<Object> _futureElements;

    public ParameterInfo() {
    }

    public ParameterInfo(Parameter parameter) {
        super(parameter);

        if(parameter != null) {
            gesValueType = parameter.getGesValueType();
            requireUniquePriorities = parameter.getRequireUniquePriorities();
        }
    }

    public ValueType getGesValueType() {
        return gesValueType;
    }

    public void setGesValueType(ValueType gesValueType) {
        this.gesValueType = gesValueType;
    }

    @Override
    public Boolean getRequireUniquePriorities() {
        return requireUniquePriorities;
    }

    public void setRequireUniquePriorities(Boolean requireUniquePriorities) {
        this.requireUniquePriorities = requireUniquePriorities;
    }


}
