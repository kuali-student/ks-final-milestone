/*
 * Copyright 2010 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.service.enumerable.dev.api;


import java.util.Date;
import java.util.List;


public interface EnumeratedValueInfo
{
	
	/**
	* Set Code
	*
	* Type: string
	*
	* Typically coincides with a code representation. Likely the key if this is a 
	* reference to another object.
	*/
	public void setCode(String code);
	
	/**
	* Get Code
	*
	* Type: string
	*
	* Typically coincides with a code representation. Likely the key if this is a 
	* reference to another object.
	*/
	public String getCode();
	
	
	
	/**
	* Set Abbreviated Value
	*
	* Type: string
	*
	* Typically coincides with a shortened name. May be equal to the code or value 
	* fields.
	*/
	public void setAbbrevValue(String abbrevValue);
	
	/**
	* Get Abbreviated Value
	*
	* Type: string
	*
	* Typically coincides with a shortened name. May be equal to the code or value 
	* fields.
	*/
	public String getAbbrevValue();
	
	
	
	/**
	* Set Value
	*
	* Type: string
	*
	* Typically coincides with a name for display.
	*/
	public void setValue(String value);
	
	/**
	* Get Value
	*
	* Type: string
	*
	* Typically coincides with a name for display.
	*/
	public String getValue();
	
	
	
	/**
	* Set Effective Date
	*
	* Type: date
	*
	* Date that this enumerated value became effective. If referring to another 
	* object, this may correspond with the effective date, created date, date of a 
	* state transition, or some arbitrarily defined date. For code/value pairs with no 
	* dates, the current date may be returned.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get Effective Date
	*
	* Type: date
	*
	* Date that this enumerated value became effective. If referring to another 
	* object, this may correspond with the effective date, created date, date of a 
	* state transition, or some arbitrarily defined date. For code/value pairs with no 
	* dates, the current date may be returned.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set Expiration Date
	*
	* Type: date
	*
	* Date that this enumerated value expires. If referring to another object, this 
	* may correspond with the expiration date, date of a state transition, or some 
	* arbitrarily defined date. If this field is not specified, then no expiration 
	* date has been currently defined. For code/value pairs with no dates, this date 
	* may not be specified.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get Expiration Date
	*
	* Type: date
	*
	* Date that this enumerated value expires. If referring to another object, this 
	* may correspond with the expiration date, date of a state transition, or some 
	* arbitrarily defined date. If this field is not specified, then no expiration 
	* date has been currently defined. For code/value pairs with no dates, this date 
	* may not be specified.
	*/
	public Date getExpirationDate();
	
	
	
	/**
	* Set Sort key
	*
	* Type: string
	*
	* Default position for the enumerated value. This might or might not exist, 
	* particularly in cases where the enumeration consists solely of a view.
	*/
	public void setSortKey(String sortKey);
	
	/**
	* Get Sort key
	*
	* Type: string
	*
	* Default position for the enumerated value. This might or might not exist, 
	* particularly in cases where the enumeration consists solely of a view.
	*/
	public String getSortKey();
	
	
	
	/**
	* Set Applicable contexts
	*
	* Type: enumContextValueInfoList
	*
	* Indicates which context types and values this particular enumerated value 
	* participates in.
	*/
	public void setContexts(List<EnumContextValueInfo> contexts);
	
	/**
	* Get Applicable contexts
	*
	* Type: enumContextValueInfoList
	*
	* Indicates which context types and values this particular enumerated value 
	* participates in.
	*/
	public List<EnumContextValueInfo> getContexts();
	
	
	
}

