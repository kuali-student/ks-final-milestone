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

package org.kuali.student.r1.core.person.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.r1.common.dto.HasAttributes;
import org.kuali.student.r1.common.dto.Idable;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 *Detailed information about a single person.
 */ 
@Deprecated
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonInfo implements Serializable, Idable, HasAttributes {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<String> personTypeKeyList;

    @XmlElement
    private List<PersonNameInfo> personNameInfoList;

    @XmlElement
    private List<PersonCitizenshipInfo> personCitizenshipInfoList;

    @XmlElement
    private List<PersonVisaInfo> personVisaInfoList;

    @XmlElement
    private List<PersonResidencyInfo> personResidencyInfoList;

    @XmlElement
    private List<PersonEthnicityInfo> personEthnicityInfoList;

    @XmlElement
    private Date birthDate;

    @XmlElement
    private Boolean isTestPerson;

    @XmlElement
    private String gender;

    @XmlElement
    private String photo;

    @XmlElement
    private Date deceasedDate;

    @XmlElement
    private String maritalStatusCode;

    @XmlElement
    private String primaryLanguageCode;

    @XmlElement
    private String secondaryLanguageCode;

    @XmlElement
    private String countryofBirthCode;

    @XmlElement
    private String stateofBirth;

    @XmlElement
    private String cityofBirth;

    @XmlElement
    private String geographicOrigin;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> attributes;

    @XmlElement
    private MetaInfo metaInfo;

    @XmlElement
    private String state;

    @XmlAttribute
    private String id;

    /**
     * List of identifiers for types of people.
     */
    public List<String> getPersonTypeKeyList() {
        if (personTypeKeyList == null) {
            personTypeKeyList = new ArrayList<String>();
        }
        return personTypeKeyList;
    }

    public void setPersonTypeKeyList(List<String> personTypeKeyList) {
        this.personTypeKeyList = personTypeKeyList;
    }

    /**
     * List containing information about a person's names.
     */
    public List<PersonNameInfo> getPersonNameInfoList() {
        if (personNameInfoList == null) {
            personNameInfoList = new ArrayList<PersonNameInfo>();
        }
        return personNameInfoList;
    }

    public void setPersonNameInfoList(List<PersonNameInfo> personNameInfoList) {
        this.personNameInfoList = personNameInfoList;
    }

    /**
     * List of detailed information about a person's citizenship.
     */
    public List<PersonCitizenshipInfo> getPersonCitizenshipInfoList() {
        if (personCitizenshipInfoList == null) {
            personCitizenshipInfoList = new ArrayList<PersonCitizenshipInfo>();
        }
        return personCitizenshipInfoList;
    }

    public void setPersonCitizenshipInfoList(List<PersonCitizenshipInfo> personCitizenshipInfoList) {
        this.personCitizenshipInfoList = personCitizenshipInfoList;
    }

    /**
     * List of detailed information about a person's visa.
     */
    public List<PersonVisaInfo> getPersonVisaInfoList() {
        if (personVisaInfoList == null) {
            personVisaInfoList = new ArrayList<PersonVisaInfo>();
        }
        return personVisaInfoList;
    }

    public void setPersonVisaInfoList(List<PersonVisaInfo> personVisaInfoList) {
        this.personVisaInfoList = personVisaInfoList;
    }

    /**
     * List of detailed information about a student's residency.
     */
    public List<PersonResidencyInfo> getPersonResidencyInfoList() {
        if (personResidencyInfoList == null) {
            personResidencyInfoList = new ArrayList<PersonResidencyInfo>();
        }
        return personResidencyInfoList;
    }

    public void setPersonResidencyInfoList(List<PersonResidencyInfo> personResidencyInfoList) {
        this.personResidencyInfoList = personResidencyInfoList;
    }

    /**
     * List of detailed information about a person's ethnicity ( and sub-ethnicity?).
     */
    public List<PersonEthnicityInfo> getPersonEthnicityInfoList() {
        if (personEthnicityInfoList == null) {
            personEthnicityInfoList = new ArrayList<PersonEthnicityInfo>();
        }
        return personEthnicityInfoList;
    }

    public void setPersonEthnicityInfoList(List<PersonEthnicityInfo> personEthnicityInfoList) {
        this.personEthnicityInfoList = personEthnicityInfoList;
    }

    /**
     * Date of birth
     */
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Indicates if the record is for a test person
     */
    public Boolean getIsTestPerson() {
        return isTestPerson;
    }

    public void setIsTestPerson(Boolean isTestPerson) {
        this.isTestPerson = isTestPerson;
    }

    /**
     * Gender
     */
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * photo file name (.jpg)
     */
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Date of death
     */
    public Date getDeceasedDate() {
        return deceasedDate;
    }

    public void setDeceasedDate(Date deceasedDate) {
        this.deceasedDate = deceasedDate;
    }

    /**
     * Marital status
     */
    public String getMaritalStatusCode() {
        return maritalStatusCode;
    }

    public void setMaritalStatusCode(String maritalStatusCode) {
        this.maritalStatusCode = maritalStatusCode;
    }

    /**
     * Primary Language Code
     */
    public String getPrimaryLanguageCode() {
        return primaryLanguageCode;
    }

    public void setPrimaryLanguageCode(String primaryLanguageCode) {
        this.primaryLanguageCode = primaryLanguageCode;
    }

    /**
     * secondary Language Code
     */
    public String getSecondaryLanguageCode() {
        return secondaryLanguageCode;
    }

    public void setSecondaryLanguageCode(String secondaryLanguageCode) {
        this.secondaryLanguageCode = secondaryLanguageCode;
    }

    /**
     * Country of Birth
     */
    public String getCountryofBirthCode() {
        return countryofBirthCode;
    }

    public void setCountryofBirthCode(String countryofBirthCode) {
        this.countryofBirthCode = countryofBirthCode;
    }

    /**
     * State/Province of Birth.
     */
    public String getStateofBirth() {
        return stateofBirth;
    }

    public void setStateofBirth(String stateofBirth) {
        this.stateofBirth = stateofBirth;
    }

    /**
     * city of Birth.
     */
    public String getCityofBirth() {
        return cityofBirth;
    }

    public void setCityofBirth(String cityofBirth) {
        this.cityofBirth = cityofBirth;
    }

    /**
     * the jurisdiction (county, state, country - however defined by the institution) where a learner lives at the point of application to the institution
     */
    public String getGeographicOrigin() {
        return geographicOrigin;
    }

    public void setGeographicOrigin(String geographicOrigin) {
        this.geographicOrigin = geographicOrigin;
    }

    /**
     * Date and time that this person became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this person record expires. This is a similar concept to the expiration date on enumerated values. If specified, this should be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * List of key/value pairs, typically used for dynamic attributes.
     */
    public Map<String, String> getAttributes() {
        if (attributes == null) {
            attributes = new HashMap<String, String>();
        }
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
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
     * Indicates the state of this person record
     */
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * Unique identifier for a person record. Optional due to assignment at creation time. Once the person has been created, this field should no longer be treated as optional.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
