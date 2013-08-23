/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.cm.course.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.cm.course.infc.CourseProposal;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.infc.Course;

/**
 * Wrapper around {@link CourseInfo} and {@link ProposalInfo} classes for maintenance document
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
@XmlType(name = "CoursePropsalInfo", propOrder = {"courseInfo",
        "proposalInfo" }) 

@XmlAccessorType(XmlAccessType.FIELD)
public class CourseProposalInfo extends IdEntityInfo implements CourseProposal, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private CourseInfo course;
    @XmlElement
    private ProposalInfo proposal;

    @Override
    public ProposalInfo getProposal() {
        return proposal;
    }

    public void setProposal(final ProposalInfo proposal) {
        this.proposal = proposal;
    }

    @Override
    public CourseInfo getCourse() {
        return course;
    }

    public void setCourse(final CourseInfo course) {
        this.course = course;
    }

}
