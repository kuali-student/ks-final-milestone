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

package org.kuali.student.core.person.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.dto.MetaInfo;

/**
 *Detailed information about a person's visa.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonVisaInfo implements Serializable, Idable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String personId;

    @XmlElement
    private String visaTypeKey;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private String visaEntry;

    @XmlElement
    private String visaId;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlAttribute
    private String id;

    /**
     * Unique identifier for a person record. This is optional, due to the identifier being set at the time of creation of the person. Once the person has been created, this should be seen as required.
     */
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    /**
     * key Code for the visa type (e.g.landed immigrant, on student visa, diplomat)
     */
    public String getVisaTypeKey() {
        return visaTypeKey;
    }

    public void setVisaTypeKey(String visaTypeKey) {
        this.visaTypeKey = visaTypeKey;
    }

    /**
     * Date/Time this became effective
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date/Time this became no longer effective
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * port of entry
     */
    public String getVisaEntry() {
        return visaEntry;
    }

    public void setVisaEntry(String visaEntry) {
        this.visaEntry = visaEntry;
    }

    /**
     * visa identification number
     */
    public String getVisaId() {
        return visaId;
    }

    public void setVisaId(String visaId) {
        this.visaId = visaId;
    }

    /**
     * Create and last update info for the structure. This is optional and treated as read only since the data is set by the internals of the service during maintenance operations.
     */
    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    /**
     * Unique identifier for the citizenship record. This is set by the service to be able to determine changes and alterations to the structure as well as provides a handle for searches. This structure is not accessible through unique operations, and it is strongly recommended that no external references to this particular identifier be maintained. Once this identifier is set by the service, it should be seen as required and readonly.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
