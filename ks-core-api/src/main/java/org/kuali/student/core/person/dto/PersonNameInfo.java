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
import org.kuali.student.core.person.infc.PersonName;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonNameInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "personId",
    "namePrefix",
    "nameTitle",
    "firstName",
    "middleName",
    "lastName",
    "nameSuffix",
    "nameChangedDate",
    "meta",
    "attributes", "_futureElements"})
public class PersonNameInfo extends IdNamelessEntityInfo implements PersonName, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String personId;
    @XmlElement
    private String namePrefix;
    @XmlElement
    private String firstName;
    @XmlElement
    private String middleName;
    @XmlElement
    private String lastName;
    @XmlElement
    private String nameSuffix;
    @XmlElement
    private String nameTitle;
    @XmlElement
    private Date nameChangedDate;

    @XmlAnyElement
    private List<Object> _futureElements;

    public PersonNameInfo() {
    }

    public PersonNameInfo(PersonName infc) {
        super(infc);
        this.setPersonId(infc.getPersonId());
        this.setNamePrefix(infc.getNamePrefix());
        this.setFirstName(infc.getFirstName());
        this.setMiddleName(infc.getMiddleName());
        this.setLastName(infc.getLastName());
        this.setNameSuffix(infc.getNameSuffix());
        this.setNameTitle(infc.getNameTitle());
        if (infc.getNameChangedDate() != null) {
            this.setNameChangedDate(new Date(infc.getNameChangedDate().getTime()));
        }
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    @Override
    public String getNamePrefix() {
        return namePrefix;
    }

    @Override
    public String getNameTitle() {
        return nameTitle;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getNameSuffix() {
        return nameSuffix;
    }

    @Override
    public Date getNameChangedDate() {
        return nameChangedDate;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

    public void setNameChangedDate(Date nameChangedDate) {
        this.nameChangedDate = nameChangedDate;
    }

}
