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
	
	
	public class MetaDTO
	 implements MetaInfo
	{
		
		private String versionInd;
		
		@Override
		public void setVersionInd(String versionInd)
		{
			this.versionInd = versionInd;
		}
		
		@Override
		public String getVersionInd()
		{
			return this.versionInd;
		}
						
		private Date createTime;
		
		@Override
		public void setCreateTime(Date createTime)
		{
			this.createTime = createTime;
		}
		
		@Override
		public Date getCreateTime()
		{
			return this.createTime;
		}
						
		private String createId;
		
		@Override
		public void setCreateId(String createId)
		{
			this.createId = createId;
		}
		
		@Override
		public String getCreateId()
		{
			return this.createId;
		}
						
		private Date updateTime;
		
		@Override
		public void setUpdateTime(Date updateTime)
		{
			this.updateTime = updateTime;
		}
		
		@Override
		public Date getUpdateTime()
		{
			return this.updateTime;
		}
						
		private String updateId;
		
		@Override
		public void setUpdateId(String updateId)
		{
			this.updateId = updateId;
		}
		
		@Override
		public String getUpdateId()
		{
			return this.updateId;
		}
						
	}

