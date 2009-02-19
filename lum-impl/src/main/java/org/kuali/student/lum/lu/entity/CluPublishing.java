/*
 * Copyright 2008 The Kuali Foundation
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
package org.kuali.student.lum.lu.entity;
 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.AttributeOwner;

@Entity
@Table(name = "KS_CLU_PUBLISHING_T")
public class CluPublishing implements AttributeOwner<CluPublishingAttribute> {

    private static final long serialVersionUID = 1L;

    @Column(name = "CLUPUB_STARTCYCLE")
    private String startCycle;

    @Column(name = "CLUPUB_ENDCYCLE")
    private String endCycle;

    @OneToMany
    @JoinTable(name = "KS_CLU_PUBLISHING_INSTRUCTOR_T", joinColumns = @JoinColumn(name = "CLU_PUBLISHING_ID"), inverseJoinColumns = @JoinColumn(name = "CLU_INSTRUCTOR_ID"))
    private List<CluInstructor> instructors;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CluPublishingAttribute> attributes;

    @Column(name = "CLUPUB_TYPE")
    private String type;

    @Column(name = "CLUPUB_STATE")
    private String state;

    @Id
    @Column(name = "ID")
    private String id;

    public String getStartCycle() {
        return startCycle;
    }

    public void setStartCycle(String startCycle) {
        this.startCycle = startCycle;
    }

    public String getEndCycle() {
        return endCycle;
    }

    public void setEndCycle(String endCycle) {
        this.endCycle = endCycle;
    }

    public List<CluInstructor> getInstructors() {
        if (instructors == null) {
            instructors = new ArrayList<CluInstructor>();
        }
        return instructors;
    }

    public void setInstructors(List<CluInstructor> instructors) {
        this.instructors = instructors;
    }

    public List<CluPublishingAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<CluPublishingAttribute>();
        }
        return attributes;
    }

    public void setAttributes(List<CluPublishingAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}