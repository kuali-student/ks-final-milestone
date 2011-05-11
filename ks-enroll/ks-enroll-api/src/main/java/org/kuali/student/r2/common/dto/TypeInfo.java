/*
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

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.Type;
import org.w3c.dom.Element;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeInfo", propOrder = {"key", "name", "descr", "effectiveDate", "expirationDate", "refObjectURI", "attributes", "_futureElements"})
public class TypeInfo extends HasAttributesInfo implements Type, Serializable {
	
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
	
    @XmlAnyElement
    private List<Element> _futureElements;    
	

    public static TypeInfo newInstance() {
        return new TypeInfo();
    }
    
    public static TypeInfo getInstance(TypeInfo typeInfo) {
        return new TypeInfo(typeInfo);
    }
    
	private TypeInfo() {
		key = null;
		name = null;
		descr = null;
		effectiveDate = null;
		expirationDate = null;
		refObjectURI = null;
		_futureElements = null;
	}
		
	public TypeInfo(Type type) {
		super(type);
		this.key = type.getKey();
		this.name = type.getName();
		this.descr = type.getDescr();
    	this.effectiveDate = null != type.getEffectiveDate() ? new Date(type.getEffectiveDate().getTime()) : null;
    	this.expirationDate = null != type.getExpirationDate() ? new Date(type.getExpirationDate().getTime()) : null;
    	this.refObjectURI = type.getRefObjectURI();
    	this._futureElements = null;
	}
	
	@Override
    public String getKey() {
        return key;
    }

	@Override
    public void setKey(String key) {
        this.key = key;
    }

	@Override
    public String getName() {
        return name;
    }

	@Override
    public void setName(String name) {
        this.name = name;
    }

	@Override
    public String getDescr() {
        return descr;
    }

	@Override
    public void setDescr(String descr) {
        this.descr = descr;
    }

	@Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

	@Override
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

	@Override
    public Date getExpirationDate() {
        return expirationDate;
    }
    
	@Override
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String getRefObjectURI() {
        return refObjectURI;
    }

	@Override
    public void setRefObjectURI(String refObjectURI) {
        this.refObjectURI = refObjectURI;
    }
}
