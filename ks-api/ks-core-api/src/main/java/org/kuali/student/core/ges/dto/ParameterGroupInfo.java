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
import org.kuali.student.core.ges.infc.GesValueTypeEnum;
import org.kuali.student.core.ges.infc.ParameterGroup;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.KeyEntityInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterGroupInfo", propOrder = {"key", "typeKey", "stateKey","name","descr",
        "meta", "attributes", "_futureElements" })
/**
 * Represents a group of Parameters.
 * @author Mezba Mahtab, Kuali Student
 */
public class ParameterGroupInfo
        extends KeyEntityInfo
        implements ParameterGroup, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAnyElement
    private List<Object> _futureElements;

    /**
     * Constructs a new blank ParameterGroupInfo.
     */
    public ParameterGroupInfo() {
    }

    /**
     * Constructs a new ParameterGroupInfo from another
     * ParameterGroup.
     *
     * @param parameterGroup the ParameterGroup to copy from
     */

    public ParameterGroupInfo(ParameterGroup parameterGroup) {
        super(parameterGroup);

        if(parameterGroup != null) {
            /*
             * if data attributes are added in the future to this entity,
             * this is where they would be copied.
             */
        }
    }

    @Override
    public String toString() {
        return "ParameterGroupInfo{" +
                "key='" + getKey() + "' }";
    }
}
