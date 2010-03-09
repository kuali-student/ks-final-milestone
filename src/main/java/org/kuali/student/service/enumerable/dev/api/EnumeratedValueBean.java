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
	
	
	import java.io.Serializable;
	import java.util.Date;
	import java.util.List;
	
	
	public class EnumeratedValueBean
	 implements EnumeratedValueInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String code;
		
		/**
		* Set Code
		*
		* Type: string
		*
		* Typically coincides with a code representation. Likely the key if this is a 
		* reference to another object.
		*/
		@Override
		public void setCode(String code)
		{
			this.code = code;
		}
		
		/**
		* Get Code
		*
		* Type: string
		*
		* Typically coincides with a code representation. Likely the key if this is a 
		* reference to another object.
		*/
		@Override
		public String getCode()
		{
			return this.code;
		}
						
		private String abbrevValue;
		
		/**
		* Set Abbreviated Value
		*
		* Type: string
		*
		* Typically coincides with a shortened name. May be equal to the code or value 
		* fields.
		*/
		@Override
		public void setAbbrevValue(String abbrevValue)
		{
			this.abbrevValue = abbrevValue;
		}
		
		/**
		* Get Abbreviated Value
		*
		* Type: string
		*
		* Typically coincides with a shortened name. May be equal to the code or value 
		* fields.
		*/
		@Override
		public String getAbbrevValue()
		{
			return this.abbrevValue;
		}
						
		private String value;
		
		/**
		* Set Value
		*
		* Type: string
		*
		* Typically coincides with a name for display.
		*/
		@Override
		public void setValue(String value)
		{
			this.value = value;
		}
		
		/**
		* Get Value
		*
		* Type: string
		*
		* Typically coincides with a name for display.
		*/
		@Override
		public String getValue()
		{
			return this.value;
		}
						
		private Date effectiveDate;
		
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
		@Override
		public void setEffectiveDate(Date effectiveDate)
		{
			this.effectiveDate = effectiveDate;
		}
		
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
		@Override
		public Date getEffectiveDate()
		{
			return this.effectiveDate;
		}
						
		private Date expirationDate;
		
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
		@Override
		public void setExpirationDate(Date expirationDate)
		{
			this.expirationDate = expirationDate;
		}
		
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
		@Override
		public Date getExpirationDate()
		{
			return this.expirationDate;
		}
						
		private String sortKey;
		
		/**
		* Set Sort key
		*
		* Type: string
		*
		* Default position for the enumerated value. This might or might not exist, 
		* particularly in cases where the enumeration consists solely of a view.
		*/
		@Override
		public void setSortKey(String sortKey)
		{
			this.sortKey = sortKey;
		}
		
		/**
		* Get Sort key
		*
		* Type: string
		*
		* Default position for the enumerated value. This might or might not exist, 
		* particularly in cases where the enumeration consists solely of a view.
		*/
		@Override
		public String getSortKey()
		{
			return this.sortKey;
		}
						
		private List<EnumContextValueInfo> contexts;
		
		/**
		* Set Applicable contexts
		*
		* Type: enumContextValueInfoList
		*
		* Indicates which context types and values this particular enumerated value 
		* participates in.
		*/
		@Override
		public void setContexts(List<EnumContextValueInfo> contexts)
		{
			this.contexts = contexts;
		}
		
		/**
		* Get Applicable contexts
		*
		* Type: enumContextValueInfoList
		*
		* Indicates which context types and values this particular enumerated value 
		* participates in.
		*/
		@Override
		public List<EnumContextValueInfo> getContexts()
		{
			return this.contexts;
		}
						
	}

