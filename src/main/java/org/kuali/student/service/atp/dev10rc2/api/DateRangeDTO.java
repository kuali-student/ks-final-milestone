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
	
	
	public class DateRangeDTO
	 implements DateRangeInfo
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
						
		private RichTextInfo desc;
		
		@Override
		public void setDesc(RichTextInfo desc)
		{
			this.desc = desc;
		}
		
		@Override
		public RichTextInfo getDesc()
		{
			return this.desc;
		}
						
		private String atpKey;
		
		@Override
		public void setAtpKey(String atpKey)
		{
			this.atpKey = atpKey;
		}
		
		@Override
		public String getAtpKey()
		{
			return this.atpKey;
		}
						
		private Date startDate;
		
		@Override
		public void setStartDate(Date startDate)
		{
			this.startDate = startDate;
		}
		
		@Override
		public Date getStartDate()
		{
			return this.startDate;
		}
						
		private Date endDate;
		
		@Override
		public void setEndDate(Date endDate)
		{
			this.endDate = endDate;
		}
		
		@Override
		public Date getEndDate()
		{
			return this.endDate;
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
						
		private MetaInfo metaInfo;
		
		@Override
		public void setMetaInfo(MetaInfo metaInfo)
		{
			this.metaInfo = metaInfo;
		}
		
		@Override
		public MetaInfo getMetaInfo()
		{
			return this.metaInfo;
		}
						
		private String type;
		
		@Override
		public void setType(String type)
		{
			this.type = type;
		}
		
		@Override
		public String getType()
		{
			return this.type;
		}
						
		private String state;
		
		@Override
		public void setState(String state)
		{
			this.state = state;
		}
		
		@Override
		public String getState()
		{
			return this.state;
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

