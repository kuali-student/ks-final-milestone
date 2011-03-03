/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.student.enrollment.lui.infc;


import java.util.Date;
import java.util.Map;


public interface LuiPersonRelationTypeInfc
{
	
	/**
	* Set ????
	*
	* Type: String
	*
	* ???
	*/
	public void setName(String name);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* ???
	*/
	public String getName();
	
	
	
	/**
	* Set ????
	*
	* Type: String
	*
	* ???
	*/
	public void setDescr(String descr);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* ???
	*/
	public String getDescr();
	
	
	
	/**
	* Set ????
	*
	* Type: Date
	*
	* ???
	*/
	public void setEffectiveDate(Date effectiveDate);
	
	/**
	* Get ????
	*
	* Type: Date
	*
	* ???
	*/
	public Date getEffectiveDate();
	
	
	
	/**
	* Set ????
	*
	* Type: Date
	*
	* ???
	*/
	public void setExpirationDate(Date expirationDate);
	
	/**
	* Get ????
	*
	* Type: Date
	*
	* ???
	*/
	public Date getExpirationDate();
	
	
	
	/**
	* Set ????
	*
	* Type: Map<String, String>
	*
	* ???
	*/
	public void setAttributes(Map<String, String> attributes);
	
	/**
	* Get ????
	*
	* Type: Map<String, String>
	*
	* ???
	*/
	public Map<String, String> getAttributes();
	
	
	
	/**
	* Set ????
	*
	* Type: String
	*
	* ???
	*/
	public void setKey(String key);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* ???
	*/
	public String getKey();
	
	
	
}

