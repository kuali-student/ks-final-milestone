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

package org.kuali.student.enrollment.academicrecord.dto;

import org.kuali.student.enrollment.academicrecord.infc.GPA;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GPAInfo", propOrder = {
        "id", "meta", "attributes",
        "value", "calculationTypeKey", "scaleKey",
        "typeKey", "stateKey", "_futureElements"})

public class GPAInfo
        extends IdNamelessEntityInfo
        implements GPA, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String value;

    @XmlElement
    private String calculationTypeKey;

    @XmlElement
    private String scaleKey;

    @XmlAnyElement
    List<Element> _futureElements;

    public GPAInfo() {
        value = null;
        calculationTypeKey = null;
        scaleKey = null;
        _futureElements = null;
    }

    /**
     * Constructs a new GPAInfo from another GPA.
     *
     * @param gpa the GPA to copy
     */
    public GPAInfo(GPA gpa) {
        super(gpa);
        this.value = gpa.getValue();
        this.calculationTypeKey = gpa.getCalculationTypeKey();
        this.scaleKey = gpa.getScaleKey();
        _futureElements = null;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getCalculationTypeKey() {
        return calculationTypeKey;
    }

    public void setCalculationTypeKey(String calculationTypeKey) {
        this.calculationTypeKey = calculationTypeKey;
    }

    @Override
    public String getScaleKey() {
        return scaleKey;
    }

    public void setScaleKey(String scaleKey) {
        this.scaleKey = scaleKey;
    }
}
