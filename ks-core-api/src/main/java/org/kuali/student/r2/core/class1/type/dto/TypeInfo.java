/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.class1.type.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.class1.type.infc.Type;
//import org.w3c.dom.Element;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeInfo", propOrder = {
                 "key", "name", "descr", "effectiveDate", "expirationDate", 
                 "refObjectUri", "serviceUri", "meta", "attributes" , "_futureElements" }) 

public class TypeInfo 
    extends HasAttributesAndMetaInfo 
    implements Type, Serializable {
	
    @XmlAttribute
    private String key;
	
    @XmlElement
    private String name;
    
    @XmlElement
    private RichTextInfo descr;
    
    @XmlElement
    private Date effectiveDate;
    
    @XmlElement
    private Date expirationDate;
    
    @XmlElement
    private String refObjectUri;

    @XmlElement
    private String serviceUri;
    
    @XmlAnyElement
    private List<Object> _futureElements;  
	
    
    /**
     * Constructs a new TypeInfo.
     */
    public TypeInfo() {
    }
	
    /**
     * Constructs a new TypeInfo from another Type.
     *
     * @param type the type to copy
     */	
    public TypeInfo(Type type) {
        super(type);
        
        if (type != null) {
            this.key = type.getKey();
            this.name = type.getName();
            if (type.getDescr() != null) {
                this.descr = new RichTextInfo(type.getDescr());
            }
            this.effectiveDate = null != type.getEffectiveDate() ? new Date(type.getEffectiveDate().getTime()) : null;
            this.expirationDate = null != type.getExpirationDate() ? new Date(type.getExpirationDate().getTime()) : null;
            this.refObjectUri = type.getRefObjectUri();
            this.serviceUri = type.getServiceUri();
        }
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
    public RichTextInfo getDescr() {
        return descr;
    }
    
    public void setDescr(RichTextInfo descr) {
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
    public String getRefObjectUri() {
        return refObjectUri;
    }
    
    public void setRefObjectUri(String refObjectUri) {
        this.refObjectUri = refObjectUri;
    }

    /**
     * Getters and Setters for ServiceUri
     */
    public String getServiceUri(){
        return serviceUri;
    }

    public void setServiceUri(String serviceUri){
        this.serviceUri = serviceUri;
    }
}
