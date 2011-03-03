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
	
	
	import java.io.Serializable;
	import java.util.Date;
	import java.util.Map;
	
	
	public class LuiPersonRelationStateBean
	 implements LuiPersonRelationStateInfc	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**
		* Set ????
		*
		* Type: String
		*
		* ???
		*/
		@Override
		public void setName(String name)
		{
			this.name = name;
		}
		
		/**
		* Get ????
		*
		* Type: String
		*
		* ???
		*/
		@Override
		public String getName()
		{
			return this.name;
		}
						
		private String descr;
		
		/**
		* Set ????
		*
		* Type: String
		*
		* ???
		*/
		@Override
		public void setDescr(String descr)
		{
			this.descr = descr;
		}
		
		/**
		* Get ????
		*
		* Type: String
		*
		* ???
		*/
		@Override
		public String getDescr()
		{
			return this.descr;
		}
						
		private Date effectiveDate;
		
		/**
		* Set ????
		*
		* Type: Date
		*
		* ???
		*/
		@Override
		public void setEffectiveDate(Date effectiveDate)
		{
			this.effectiveDate = effectiveDate;
		}
		
		/**
		* Get ????
		*
		* Type: Date
		*
		* ???
		*/
		@Override
		public Date getEffectiveDate()
		{
			return this.effectiveDate;
		}
						
		private Date expirationDate;
		
		/**
		* Set ????
		*
		* Type: Date
		*
		* ???
		*/
		@Override
		public void setExpirationDate(Date expirationDate)
		{
			this.expirationDate = expirationDate;
		}
		
		/**
		* Get ????
		*
		* Type: Date
		*
		* ???
		*/
		@Override
		public Date getExpirationDate()
		{
			return this.expirationDate;
		}
						
		private Map<String, String> attributes;
		
		/**
		* Set ????
		*
		* Type: Map<String, String>
		*
		* ???
		*/
		@Override
		public void setAttributes(Map<String, String> attributes)
		{
			this.attributes = attributes;
		}
		
		/**
		* Get ????
		*
		* Type: Map<String, String>
		*
		* ???
		*/
		@Override
		public Map<String, String> getAttributes()
		{
			return this.attributes;
		}
						
		private String key;
		
		/**
		* Set ????
		*
		* Type: String
		*
		* ???
		*/
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		/**
		* Get ????
		*
		* Type: String
		*
		* ???
		*/
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

