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
package org.kuali.student.enrollment.lrr.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lrr.infc.ResultSource;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultSourceInfo", propOrder = {"id", "typeKey", "descr", "articulationId", "resultTransformationId", "metaInfo", "attributes", "_futureElements"})
public class ResultSourceInfo extends IdEntityInfo implements ResultSource, Serializable {

    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private String articulationId;

    @XmlElement
    private String resultTransformationId;

    @XmlAnyElement
    private List<Element> _futureElements;

    public String getArticulationId() {
        return articulationId;
    }

    public String getResultTransformationId() {
        return resultTransformationId;
    }

    public void setArticulationId(String articulationId) {
        this.articulationId = articulationId;
    }

    public void setResultTransformationId(String resultTransformationId) {
        this.resultTransformationId = resultTransformationId;
    }   
}