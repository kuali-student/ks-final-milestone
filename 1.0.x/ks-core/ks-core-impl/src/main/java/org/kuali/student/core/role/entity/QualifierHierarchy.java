/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.role.entity;

/**
 * This is a description of what this class does - Rich don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

//import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
@Table(name="KR_KIM_QLF_HIERARCHY")
public class QualifierHierarchy {

    @Id
    String id;
    String name;

    // For compatibility with Rice ORM (OJB)
    @Column(name="OBJ_ID", length=36)
    private String objectId;

    @Version
    @Column(name="VER_NBR")
    private int versionNumber;

    @OneToMany(mappedBy = "qualifierHierarchy")
    List<Qualifier> qualifiers = new ArrayList<Qualifier>();

    @ManyToMany
    @JoinTable(name = "KR_KIM_QLF_HIERARCHY_QLF_TYPE")
    List<QualifierType> qualifierTypes = new ArrayList<QualifierType>();

/*    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getVersion() {
        return versionNumber;
    }

    public void setVersion(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Qualifier> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(List<Qualifier> qualifiers) {
        this.qualifiers = qualifiers;
    }

	/**
	 * @return the qualifierTypes
	 */
	public List<QualifierType> getQualifierTypes() {
		return qualifierTypes;
	}

	/**
	 * @param qualifierTypes the qualifierTypes to set
	 */
	public void setQualifierTypes(List<QualifierType> qualifierTypes) {
		this.qualifierTypes = qualifierTypes;
	}
}
