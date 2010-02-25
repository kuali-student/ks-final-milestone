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



public interface TimeAmountInfo
{
	
	/**
	* Set Academic Time Period Duration Type Key
	*
	* The kind of units associated with the quantity, such as Semesters
	*/
	public void setAtpDurationTypeKey(String atpDurationTypeKey);
	
	/**
	* Get Academic Time Period Duration Type Key
	*
	* The kind of units associated with the quantity, such as Semesters
	*/
	public String getAtpDurationTypeKey();
	
	
	
	/**
	* Set Time Quantity
	*
	* The amount of time
	*/
	public void setTimeQuantity(Integer timeQuantity);
	
	/**
	* Get Time Quantity
	*
	* The amount of time
	*/
	public Integer getTimeQuantity();
	
	
	
}

