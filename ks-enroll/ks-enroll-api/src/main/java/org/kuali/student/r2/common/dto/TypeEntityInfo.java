/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.common.dto;

import org.kuali.student.r2.common.infc.Type;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@XmlTransient
public abstract class TypeEntityInfo extends HasAttributesInfo implements Type, Serializable {

    @XmlAttribute
    private String key;

    @XmlElement
    private String name;

    @XmlElement
    private String descr;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private String refObjectURI;

    public TypeEntityInfo() {
        key = null;
        name = null;
        descr = null;
        effectiveDate = null;
        expirationDate = null;
        refObjectURI = null;
    }

    public TypeEntityInfo(Type type) {
        super(type);
        this.key = type.getKey();
        this.name = type.getName();
        this.descr = type.getDescr();
        this.effectiveDate = null != type.getEffectiveDate() ? new Date(type.getEffectiveDate().getTime()) : null;
        this.expirationDate = null != type.getExpirationDate() ? new Date(type.getExpirationDate().getTime()) : null;
        this.refObjectURI = type.getRefObjectURI();
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescr() {
        return descr;
    }


    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }


    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }


    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String getRefObjectURI() {
        return refObjectURI;
    }


    public void setRefObjectURI(String refObjectURI) {
        this.refObjectURI = refObjectURI;
    }
}
