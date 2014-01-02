/*
 * Copyright 2010 The Kuali Foundation Licensed under the
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
package org.kuali.student.r2.core.exemption.dto;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.rice.core.api.util.type.KualiDecimal;

import org.kuali.student.r2.core.exemption.infc.ValueOverride;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValueOverrideInfo", propOrder = {"leftComparisonValue",
    "rightComparisonValue", "_futureElements"})
public class ValueOverrideInfo implements ValueOverride, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private KualiDecimal leftComparisonValue;
    @XmlElement
    private KualiDecimal rightComparisonValue;
    @XmlAnyElement
    private List<Object> _futureElements;

    public ValueOverrideInfo() {
        super();
    }

    /**
     * Copy Constructor
     *
     * @param orig value override to copy
     */
    public ValueOverrideInfo(ValueOverride orig) {
        super();
        if (null != orig) {
            this.leftComparisonValue = orig.getLeftComparisonValue();
            this.rightComparisonValue = orig.getLeftComparisonValue();
        }
    }

    @Override
    public KualiDecimal getLeftComparisonValue() {
        return leftComparisonValue;
    }

    public void setLeftComparisonValue(KualiDecimal leftComparisonValue) {
        this.leftComparisonValue = leftComparisonValue;
    }

    @Override
    public KualiDecimal getRightComparisonValue() {
        return rightComparisonValue;
    }

    public void setRightComparisonValue(KualiDecimal rightComparisonValue) {
        this.rightComparisonValue = rightComparisonValue;
    }
}
