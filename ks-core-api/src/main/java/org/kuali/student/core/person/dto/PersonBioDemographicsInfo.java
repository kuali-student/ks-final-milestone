/*
 * Copyright 2013 The Kuali Foundation Licensed under the
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import org.kuali.student.core.person.infc.PersonBioDemographics;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonBioDemographicInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "personId",
    "deceasedDate",
    "birthDate",
    "genderCode",
    "meta",
    "attributes", "_futureElements"})
public class PersonBioDemographicsInfo extends IdNamelessEntityInfo implements PersonBioDemographics, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String personId;
    @XmlElement
    private Date deceasedDate;
    @XmlElement
    private Date birthDate;
    @XmlElement
    private String genderCode;

    @XmlAnyElement
    private List<Object> _futureElements;

    public PersonBioDemographicsInfo() {
    }

    public PersonBioDemographicsInfo(PersonBioDemographics infc) {
        super(infc);
        this.setPersonId(infc.getPersonId());
        if (infc.getDeceasedDate() != null) {
            this.setDeceasedDate(new Date(infc.getDeceasedDate().getTime()));
        }
        if (infc.getBirthDate() != null) {
            this.setBirthDate(new Date(infc.getBirthDate().getTime()));
        }
        this.setGenderCode(infc.getGenderCode());
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public Date getDeceasedDate() {
        return deceasedDate;
    }

    public void setDeceasedDate(Date deceasedDate) {
        this.deceasedDate = deceasedDate;
    }

    @Override
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

}
