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

package org.kuali.student.core.comment.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;




@Entity
@Table(name = "KSCO_TAG")
@NamedQueries( {
        @NamedQuery(name = "Tag.getTags", query = "SELECT  tag FROM Tag tag JOIN tag.reference r1 WHERE r1.referenceId =:refId AND r1.referenceType.id=:refTypeId"),
        @NamedQuery(name = "Tag.getTag", query = "SELECT  tag FROM Tag tag JOIN tag.reference r1 WHERE r1.referenceId =:refId AND r1.referenceType.id=:refTypeId"),
        @NamedQuery(name = "Tag.getTagsByType", query = "SELECT  tag FROM Tag tag JOIN tag.reference r1 WHERE r1.referenceId =:refId AND r1.referenceType.id=:refTypeId AND tag.type.id=:tagTypeId"),
        @NamedQuery(name = "Tag.getTagsByRefId", query="SELECT tag FROM Tag tag JOIN tag.reference r1 WHERE r1.referenceId=:refId")})
public class Tag extends MetaEntity implements AttributeOwner<TagAttribute>{

    @Id
    @Column(name = "ID")
    private String id;


    @Column(name = "NAME_SPACE")
    private String namespace;

    @Column(name = "PREDICATE")
    private String predicate;

    @Column(name = "VAL")
    private String value;


    @ManyToOne
    @JoinColumn(name = "REFERENCE")
    private Reference reference;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<TagAttribute> attributes;

    @ManyToOne
    @JoinColumn(name = "TYPE")
    private TagType type;

    @Column(name = "STATE")
    private String state;

    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void beforePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }


    public String getNamespace(){
        return namespace;
    }

    public void setNamespace(String namespace){
        this.namespace=namespace;
    }

    public String getPredicate(){
        return predicate;
    }

    public void setPredicate(String predicate){
        this.predicate=predicate;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value=value;
    }


    public Reference getReferennce(){
        return reference;
    }

    public void setReference(Reference reference){
        this.reference=reference;
    }



    /**
     * @return the effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    @Override
    public List<TagAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<TagAttribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the type
     */
    public TagType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(TagType type) {
        this.type = type;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

}
