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
package org.kuali.student.service.lu.dev.api;


import java.util.Date;


public interface AffiliatedOrgInfo
{
	
	/**
	* Set Organization Identifier
	*
	* Type: orgId
	*
	* Unique identifier for an organization.
	*/
	public void setOrgId(String orgId);
	
	/**
	* Get Organization Identifier
	*
	* Type: orgId
	*
	* Unique identifier for an organization.
	*/
	public String getOrgId();
	
	
	
	/**
	* Set effectiveDate
	*
	* Type: date
	*
	* Specifies a date with no time component.
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get effectiveDate
	*
	* Type: date
	*
	* Specifies a date with no time component.
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set expirationDate
	*
	* Type: date
	*
	* Specifies a date with no time component.
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get expirationDate
	*
	* Type: date
	*
	* Specifies a date with no time component.
	*/
	public Date getExpirationDate();
	
	
	
	/**
	* Set percentage
	*
	* Type: long
	*
	* A long numeric value without a fractional component.
	*/
	public void setPercentage(Long percentage);
	
	/**
	* Get percentage
	*
	* Type: long
	*
	* A long numeric value without a fractional component.
	*/
	public Long getPercentage();
	
	
	
}

