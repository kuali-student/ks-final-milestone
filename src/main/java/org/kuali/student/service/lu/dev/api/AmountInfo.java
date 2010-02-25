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



public interface AmountInfo
{
	
	/**
	* Set Unit Type
	*
	* The kind of units associated with the quantity, such as hours/week. It is 
	* expected that in usage in other structures, this value will always be enumerated 
	* based on that context.
	*/
	public void setUnitType(String unitType);
	
	/**
	* Get Unit Type
	*
	* The kind of units associated with the quantity, such as hours/week. It is 
	* expected that in usage in other structures, this value will always be enumerated 
	* based on that context.
	*/
	public String getUnitType();
	
	
	
	/**
	* Set Unit Quantity
	*
	* The amount of units. Allowed values consist of numeric values as well as the 
	* string "unbounded".
	*/
	public void setUnitQuantity(String unitQuantity);
	
	/**
	* Get Unit Quantity
	*
	* The amount of units. Allowed values consist of numeric values as well as the 
	* string "unbounded".
	*/
	public String getUnitQuantity();
	
	
	
}

