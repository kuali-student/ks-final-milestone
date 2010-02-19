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
	package org.kuali.student.service.atp.dev10rc2.api;
	
	
	import java.util.Date;
	import java.util.Map;
	
	
	public class AtpTypeDTO
	 implements AtpTypeInfo
	{
		
		private String name;
		
		@Override
		public void setName(String name)
		{
			this.name = name;
		}
		
		@Override
		public String getName()
		{
			return this.name;
		}
						
		private String desc;
		
		@Override
		public void setDesc(String desc)
		{
			this.desc = desc;
		}
		
		@Override
		public String getDesc()
		{
			return this.desc;
		}
						
		private String durationType;
		
		@Override
		public void setDurationType(String durationType)
		{
			this.durationType = durationType;
		}
		
		@Override
		public String getDurationType()
		{
			return this.durationType;
		}
						
		private String seasonalType;
		
		@Override
		public void setSeasonalType(String seasonalType)
		{
			this.seasonalType = seasonalType;
		}
		
		@Override
		public String getSeasonalType()
		{
			return this.seasonalType;
		}
						
		private Date effectiveDate;
		
		@Override
		public void setEffectiveDate(Date effectiveDate)
		{
			this.effectiveDate = effectiveDate;
		}
		
		@Override
		public Date getEffectiveDate()
		{
			return this.effectiveDate;
		}
						
		private Date expirationDate;
		
		@Override
		public void setExpirationDate(Date expirationDate)
		{
			this.expirationDate = expirationDate;
		}
		
		@Override
		public Date getExpirationDate()
		{
			return this.expirationDate;
		}
						
		private Map<String,String> attributes;
		
		@Override
		public void setAttributes(Map<String,String> attributes)
		{
			this.attributes = attributes;
		}
		
		@Override
		public Map<String,String> getAttributes()
		{
			return this.attributes;
		}
						
		private String key;
		
		@Override
		public void setKey(String key)
		{
			this.key = key;
		}
		
		@Override
		public String getKey()
		{
			return this.key;
		}
						
	}

