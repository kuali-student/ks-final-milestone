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

package org.kuali.student.lum.lrc.entity;

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
@Table(name = "KSLU_LRC_RESCOMP")
@NamedQueries( {
    @NamedQuery(name = "ResultComponent.getResultComponentIdsByResult", query = "SELECT rc.id FROM ResultComponent rc JOIN rc.resultValues rv WHERE rv.id = :resultValueId AND rc.type.id = :resultComponentTypeKey"),
    @NamedQuery(name = "ResultComponent.getResultComponentIdsByResultComponentType", query = "SELECT rc.id FROM ResultComponent rc WHERE rc.type.id = :resultComponentTypeKey"),
    @NamedQuery(name = "ResultComponent.getResultComponentType", query = "SELECT rc.type FROM ResultComponent rc WHERE rc.type.id = :resultComponentTypeKey")
})

public class ResultComponent extends MetaEntity implements AttributeOwner<ResultComponentAttribute> {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private LrcRichText descr;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resultComponent")
    private List<ResultValue> resultValues;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<ResultComponentAttribute> attributes;

    @ManyToOne
    @JoinColumn(name = "TYPE")
    private ResultComponentType type;

    @Column(name = "STATE")
    private String state;

    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void prePersist() {
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the descr
     */
    public LrcRichText getDescr() {
        return descr;
    }

    /**
     * @param descr the descr to set
     */
    public void setDescr(LrcRichText descr) {
        this.descr = descr;
    }

    /**
     * @return the resultValues
     */
    public List<ResultValue> getResultValues() {
        return resultValues;
    }

    /**
     * @param resultValues the resultValues to set
     */
    public void setResultValues(List<ResultValue> resultValues) {
        this.resultValues = resultValues;
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

    /**
     * @return the type
     */
    public ResultComponentType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ResultComponentType type) {
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

    @Override
    public List<ResultComponentAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<ResultComponentAttribute> attributes) {
        this.attributes = attributes;
    }

}
