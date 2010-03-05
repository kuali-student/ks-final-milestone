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
	
	
	import java.io.Serializable;
	
	
	public class AmountBean
	 implements AmountInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String unitType;
		
		/**
		* Set Unit Type
		*
		* Type: string
		*
		* The kind of units associated with the quantity, such as hours/week. It is 
		* expected that in usage in other structures, this value will always be enumerated 
		* based on that context.
		*/
		@Override
		public void setUnitType(String unitType)
		{
			this.unitType = unitType;
		}
		
		/**
		* Get Unit Type
		*
		* Type: string
		*
		* The kind of units associated with the quantity, such as hours/week. It is 
		* expected that in usage in other structures, this value will always be enumerated 
		* based on that context.
		*/
		@Override
		public String getUnitType()
		{
			return this.unitType;
		}
						
		private String unitQuantity;
		
		/**
		* Set Unit Quantity
		*
		* Type: string
		*
		* The amount of units. Allowed values consist of numeric values as well as the 
		* string "unbounded".
		*/
		@Override
		public void setUnitQuantity(String unitQuantity)
		{
			this.unitQuantity = unitQuantity;
		}
		
		/**
		* Get Unit Quantity
		*
		* Type: string
		*
		* The amount of units. Allowed values consist of numeric values as well as the 
		* string "unbounded".
		*/
		@Override
		public String getUnitQuantity()
		{
			return this.unitQuantity;
		}
						
	}

