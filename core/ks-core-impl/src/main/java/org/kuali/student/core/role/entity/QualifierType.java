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

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REFRESH;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

//import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
@Table(name="KR_KIM_QLF_TYPE")
public class QualifierType {

    @Id
    private String id;
    private String name;
    // Used to identify if a QualiferType is a composite key
    boolean composite;

    // For compatibility with Rice ORM (OJB)
    @Column(name="OBJ_ID", length=36)
    private String objectId;

    @Version
    @Column(name="VER_NBR")
    private int versionNumber;

    @OneToMany(mappedBy = "qualifierType")
    private List<Qualifier> qualifiers = new ArrayList<Qualifier>();

    // Loopback - Represents the allowable qualifier types for Composite Primary keys, see qualifier.
    @OneToMany(cascade = {PERSIST,MERGE,REFRESH}, mappedBy = "compositeQualifierType")
    private List<QualifierType> pkQualifierTypes = new ArrayList<QualifierType>();

    @ManyToOne(fetch = FetchType.LAZY)
    private QualifierType compositeQualifierType;

    @ManyToMany(mappedBy="qualifierTypes")
    private List<QualifierHierarchy> qualifierHierarchys = new ArrayList<QualifierHierarchy>();

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

    public List<QualifierType> getPkQualifierTypes() {
        return pkQualifierTypes;
    }

    public void setPkQualifierTypes(List<QualifierType> pkQualifierTypes){
        this.pkQualifierTypes = pkQualifierTypes;
    }

    public QualifierType getCompositeQualifierType() {
        return compositeQualifierType;
    }

    public void setCompositeQualifierType(QualifierType compositeQualifierType) {
        this.compositeQualifierType = compositeQualifierType;
    }

	/**
	 * @return the qualifierHierarchys
	 */
	public List<QualifierHierarchy> getQualifierHierarchys() {
		return qualifierHierarchys;
	}

	/**
	 * @param qualifierHierarchys the qualifierHierarchys to set
	 */
	public void setQualifierHierarchys(List<QualifierHierarchy> qualifierHierarchys) {
		this.qualifierHierarchys = qualifierHierarchys;
	}

    /**
     * @return the composite
     */
    public boolean isComposite() {
        return composite;
    }

    /**
     * @param composite the composite to set
     */
    public void setComposite(boolean composite) {
        this.composite = composite;
    }

}
