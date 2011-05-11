/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.enrollment.grading.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.grading.infc.CalculatedGrade;
import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.w3c.dom.Element;

/**
 * 
 * @author Kuali Student Team (Kamal)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CalculatedGradeInfo", propOrder = {"id", "metaInfo", "attributes", "_futureElements"})
public class CalculatedGradeInfo extends HasAttributesAndMetaInfo implements CalculatedGrade, Serializable  {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;
        
    @XmlAnyElement
    private List<Element> _futureElements;  

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }    
}
