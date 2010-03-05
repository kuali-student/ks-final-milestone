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
	import java.util.List;
	import java.util.Map;
	
	
	public class CluAccountingBean
	 implements CluAccountingInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private List<AffiliatedOrgInfo> affiliatedOrgInfoList;
		
		/**
		* Set List of Affiliated Organizations
		*
		* Type: affiliatedOrgInfoList
		*
		* List of affiliated organizations.
		*/
		@Override
		public void setAffiliatedOrgInfoList(List<AffiliatedOrgInfo> affiliatedOrgInfoList)
		{
			this.affiliatedOrgInfoList = affiliatedOrgInfoList;
		}
		
		/**
		* Get List of Affiliated Organizations
		*
		* Type: affiliatedOrgInfoList
		*
		* List of affiliated organizations.
		*/
		@Override
		public List<AffiliatedOrgInfo> getAffiliatedOrgInfoList()
		{
			return this.affiliatedOrgInfoList;
		}
						
		private Map<String,String> attributes;
		
		/**
		* Set Generic/dynamic attributes
		*
		* Type: attributeInfoList
		*
		* List of key/value pairs, typically used for dynamic attributes.
		*/
		@Override
		public void setAttributes(Map<String,String> attributes)
		{
			this.attributes = attributes;
		}
		
		/**
		* Get Generic/dynamic attributes
		*
		* Type: attributeInfoList
		*
		* List of key/value pairs, typically used for dynamic attributes.
		*/
		@Override
		public Map<String,String> getAttributes()
		{
			return this.attributes;
		}
						
	}

