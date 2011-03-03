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

package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.HasAttributes;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class StateInfo implements Serializable, Idable, HasAttributes {
	
	@XmlAttribute(name="key")
	private String id;
	
	@XmlElement
	private String name;
	
	@XmlElement(name ="desc")
	private String descr;

	@XmlElement
	private Date effectiveDate;
	
	@XmlElement
	private Date expirationDate;
	
	@XmlElement
	@XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
	private Map<String, String> attributes;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getDescr(){
		return descr;
	}
	
	public void setDescr(String descr){
		this.descr = descr;
	}
	
	public Date getEffectiveDate(){
		return effectiveDate;
	}
	
	public void setEffectiveDate(Date effectiveDate){
		this.effectiveDate = effectiveDate;
	}
	
	public Date getExpirationDate(){
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate){
		this.expirationDate = expirationDate;
	}
	
	public Map<String, String> getAttributes(){
		if(attributes == null){
			attributes = new HashMap<String, String>();
		}
		return attributes;
	}
	
	public void setAttributes(Map<String, String> attributes){
		this.attributes = attributes;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
}
