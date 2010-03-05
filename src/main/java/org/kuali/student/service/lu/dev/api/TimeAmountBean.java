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
	
	
	public class TimeAmountBean
	 implements TimeAmountInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String atpDurationTypeKey;
		
		/**
		* Set Academic Time Period Duration Type Key
		*
		* Type: atpDurationTypeKey
		*
		* The kind of units associated with the quantity, such as Semesters
		*/
		@Override
		public void setAtpDurationTypeKey(String atpDurationTypeKey)
		{
			this.atpDurationTypeKey = atpDurationTypeKey;
		}
		
		/**
		* Get Academic Time Period Duration Type Key
		*
		* Type: atpDurationTypeKey
		*
		* The kind of units associated with the quantity, such as Semesters
		*/
		@Override
		public String getAtpDurationTypeKey()
		{
			return this.atpDurationTypeKey;
		}
						
		private Integer timeQuantity;
		
		/**
		* Set Time Quantity
		*
		* Type: integer
		*
		* The amount of time
		*/
		@Override
		public void setTimeQuantity(Integer timeQuantity)
		{
			this.timeQuantity = timeQuantity;
		}
		
		/**
		* Get Time Quantity
		*
		* Type: integer
		*
		* The amount of time
		*/
		@Override
		public Integer getTimeQuantity()
		{
			return this.timeQuantity;
		}
						
	}

