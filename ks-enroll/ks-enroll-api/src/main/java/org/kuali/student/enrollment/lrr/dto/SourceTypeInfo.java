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
package org.kuali.student.enrollment.lrr.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
;


/**
 * Information about a single source type.
 *
 * @Author KSContractMojo
 * @Author sambit
 * @Since Wed May 04 15:34:11 PDT 2011
 * @See <a href="https://wiki.kuali.org/display/KULSTU/sourceTypeInfo+Structure">SourceTypeInfo</a>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceTypeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String name;

    @XmlElement
    private String desc;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

   
    @XmlAttribute(name="key")
    private String id;

    /**
     * Friendly name of the source type
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Narrative description of the source type
     */
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Date and time that this source type became effective. This is a similar concept to the effective date on enumerated values. When an expiration date has been specified, this field must be less than or equal to the expiration date.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date and time that this source type expires. This is a similar concept to the expiration date on enumerated values. If specified, this should be greater than or equal to the effective date. If this field is not specified, then no expiration date has been currently defined and should automatically be considered greater than the effective date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    /**
     * Unique identifier for the type of source for an academic record style record.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}