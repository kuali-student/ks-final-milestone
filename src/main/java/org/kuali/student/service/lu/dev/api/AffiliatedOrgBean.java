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
	import java.util.Date;
	
	
	public class AffiliatedOrgBean
	 implements AffiliatedOrgInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String orgId;
		
		/**
		* Set Organization Identifier
		*
		* Unique identifier for an organization.
		*/
		@Override
		public void setOrgId(String orgId)
		{
			this.orgId = orgId;
		}
		
		/**
		* Get Organization Identifier
		*
		* Unique identifier for an organization.
		*/
		@Override
		public String getOrgId()
		{
			return this.orgId;
		}
						
		private Date effectiveDate;
		
		/**
		* Set effectiveDate
		*
		* Specifies a date with no time component.
		*/
		@Override
		public void setEffectiveDate(Date effectiveDate)
		{
			this.effectiveDate = effectiveDate;
		}
		
		/**
		* Get effectiveDate
		*
		* Specifies a date with no time component.
		*/
		@Override
		public Date getEffectiveDate()
		{
			return this.effectiveDate;
		}
						
		private Date expirationDate;
		
		/**
		* Set expirationDate
		*
		* Specifies a date with no time component.
		*/
		@Override
		public void setExpirationDate(Date expirationDate)
		{
			this.expirationDate = expirationDate;
		}
		
		/**
		* Get expirationDate
		*
		* Specifies a date with no time component.
		*/
		@Override
		public Date getExpirationDate()
		{
			return this.expirationDate;
		}
						
		private Long percentage;
		
		/**
		* Set percentage
		*
		* A long numeric value without a fractional component.
		*/
		@Override
		public void setPercentage(Long percentage)
		{
			this.percentage = percentage;
		}
		
		/**
		* Get percentage
		*
		* A long numeric value without a fractional component.
		*/
		@Override
		public Long getPercentage()
		{
			return this.percentage;
		}
						
	}

