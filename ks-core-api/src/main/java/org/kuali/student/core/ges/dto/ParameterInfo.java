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

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.core.ges.infc.Parameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterInfo", propOrder = {"id", "typeKey", "stateKey",
        "key", "valueTypeKey","requireUniquePriorities", "meta", "attributes", "_futureElements" })
public class ParameterInfo extends IdNamelessEntityInfo implements Parameter {

    @XmlElement
    private String key;
    @XmlElement
    private String valueTypeKey;
    @XmlElement
    private Boolean requireUniquePriorities;
    @XmlAnyElement
    private List<Object> _futureElements;

    public ParameterInfo() {
    }

    public ParameterInfo(Parameter parameter) {
        super(parameter);

        if(parameter != null) {
            key = parameter.getKey();
            valueTypeKey = parameter.getValueTypeKey();
            requireUniquePriorities = parameter.getRequireUniquePriorities();
        }
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getValueTypeKey() {
        return valueTypeKey;
    }

    public void setValueTypeKey(String valueTypeKey) {
        this.valueTypeKey = valueTypeKey;
    }

    @Override
    public Boolean getRequireUniquePriorities() {
        return requireUniquePriorities;
    }

    public void setRequireUniquePriorities(Boolean requireUniquePriorities) {
        this.requireUniquePriorities = requireUniquePriorities;
    }


}
