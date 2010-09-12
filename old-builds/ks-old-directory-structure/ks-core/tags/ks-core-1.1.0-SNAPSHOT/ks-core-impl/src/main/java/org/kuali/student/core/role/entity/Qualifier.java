/**
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

//import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
@Table(name="KR_KIM_QLF")
public class Qualifier {

    @Id
    private String id;
    private String name;

    // For compatibility with Rice ORM (OJB)
    @Column(name="OBJ_ID", length=36)
    private String objectId;

    @Version
    @Column(name="VER_NBR")
    private int versionNumber;

    // Loopback - Represents Tree of Qualifier
    @OneToMany(cascade = {PERSIST,MERGE,REFRESH} , mappedBy = "parent")
    private List<Qualifier> qualifiers = new ArrayList<Qualifier>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Qualifier parent;

    // Loopback - Represents Composite Primary keys, where each qualifier in the list is a key
    @OneToMany(cascade = {PERSIST,MERGE,REFRESH}, mappedBy = "compositeQualifier")
    private List<Qualifier> pkQualifiers = new ArrayList<Qualifier>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Qualifier compositeQualifier;

    @ManyToOne(fetch = FetchType.LAZY)
    private QualifierType qualifierType;

    @ManyToOne(fetch = FetchType.LAZY)
    private QualifierHierarchy qualifierHierarchy;

    //for the tree stuff
    int leftVisit,
        rightVisit;

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

    public Qualifier getParent() {
        return parent;
    }

    public void setParent(Qualifier parent) {
        this.parent = parent;
    }

    public int getLeftVisit() {
        return leftVisit;
    }

    public void setLeftVisit(int leftVisit) {
        this.leftVisit = leftVisit;
    }

    public int getRightVisit() {
        return rightVisit;
    }

    public void setRightVisit(int rightVisit) {
        this.rightVisit = rightVisit;
    }

    public QualifierHierarchy getQualifierHierarchy() {
        return qualifierHierarchy;
    }

    public void setQualifierHierarchy(QualifierHierarchy qualifierHierarchy) {
        this.qualifierHierarchy = qualifierHierarchy;
    }

    public QualifierType getQualifierType() {
        return qualifierType;
    }

    public void setQualifierType(QualifierType qualifierType) {
        this.qualifierType = qualifierType;
    }

    public List<Qualifier> getPkQualifiers() {
        return pkQualifiers;
    }

    public void setPkQualifiers(List<Qualifier> pkQualifiers) {
        this.pkQualifiers = pkQualifiers;
    }

    public Qualifier getCompositeQualifier() {
        return compositeQualifier;
    }

    public void setCompositeQualifier(Qualifier compositeQualifier) {
        this.compositeQualifier = compositeQualifier;
    }

}
