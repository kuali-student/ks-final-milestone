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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.AttributeOwner;

@Entity
@Table(name = "KSLU_CLU_INSTR")
public class CluInstructor implements AttributeOwner<CluInstructorAttribute> {
    
    @Id
    @Column(name = "ID")
    private String id;
    
    @Column(name = "ORG_ID")
    private String orgId;

    @Column(name = "PERS_ID")
    private String personId;
    
    @Column(name = "TYPE")
    private String type;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "OWNER")
    private List<CluInstructorAttribute> attributes;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<CluInstructorAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<CluInstructorAttribute>();
        }
        return attributes;
    }

    public void setAttributes(List<CluInstructorAttribute> attributes) {
        this.attributes = attributes;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}