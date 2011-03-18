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
package org.kuali.student.enrollment.lpr.dto;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Date;
import org.kuali.student.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationInfc;

/**
 * Detailed information about a single LUI to Person Relation.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue Mar 01 15:53:57 PST 2011
 * @See <a href="https://wiki.kuali.org/display/KULSTU/luiPersonRelationInfo+Structure">LuiPersonRelationInfo</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LuiPersonRelationInfo extends HasAttributesAndMetaInfo
  implements LuiPersonRelationInfc, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String luiId;

    @XmlElement
    private String personId;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String state;

    @XmlAttribute
    private String id;

    /**
     * Name: LUI identifier
     * <p/>
     * Unique identifier for a Learning Unit Instance (LUI).
     */
    public String getLuiId() {
        return luiId;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }

    /**
     * Name: Person Identifier
     * <p/>
     * Unique identifier for a person record.
     */
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     * Name:Effective Date
     * <p/>
     * Date/time this relationship became effective. Must be less than or equal to the expirationDate specified.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Name: Expiration Date
     * <p/>
     * Date/time this relationship is no longer effective. Must be greater than or equal to the effectiveDate specified.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    /**
     * Name: LUI Person Relation Type
     * <p/>
     * Unique identifier for the type of LUI to Person relation.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Name: Relation State
     * <p/>
     * Unique identifier for the state of the relationship between a LUI and person.
     */
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Name: LUI Person Relation identifier
     * <p/>
     * Unique identifier for the LUI to Person relation. This is optional, due to the identifier being set at the time of creation. Once the relation has been created, this should be seen as required.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}