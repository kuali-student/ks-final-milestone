/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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
package org.kuali.student.ap.academicplan.dto;

import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * LearningPlan message structure
 *
 * @author Kuali Student Team
 * @version 1.0 (Dev)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LearningPlanInfo", propOrder = {"studentId", "id", "typeKey", "stateKey", "shared", "name", "descr", "meta", "attributes", "_futureElements"})
public class LearningPlanInfo extends IdEntityInfo implements LearningPlan {

    @XmlElement
    private String studentId;

    //TODO: KSAP-1014 - Add 'name' attribute to LearningPlan and PlanItem

    @XmlElement
    private Boolean shared;

    @XmlAnyElement
    private List<Element> _futureElements;


    public LearningPlanInfo() {
    }

    public LearningPlanInfo(LearningPlan plan) {
        super(plan);

        if(null != plan) {
            this.setId(plan.getId());
            this.studentId = plan.getStudentId();
            this.setDescr((null != plan.getDescr()) ? new RichTextInfo(plan.getDescr()) : null);
            this.setShared(plan.getShared());
        }
    }

    @Override
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }
}
