/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.orchestration.orch;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class RuntimeDataHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		IS_CREATED ("isCreated"),
		IS_DELETED ("isDeleted"),
		IS_UPDATED ("isUpdated"),
		VERSIONS ("versions");
		
		private final String key;
		
		private Properties (final String key)
		{
			this.key = key;
		}
		
		@Override
		public String getKey ()
		{
			return this.key;
		}
	}
	private Data data;
	
	public RuntimeDataHelper (Data data)
	{
		this.data = data;
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setIsCreated (String value)
	{
		data.set (Properties.IS_CREATED.getKey (), value);
	}
	
	
	public String getIsCreated ()
	{
		return (String) data.get (Properties.IS_CREATED.getKey ());
	}
	
	
	public void setIsDeleted (String value)
	{
		data.set (Properties.IS_DELETED.getKey (), value);
	}
	
	
	public String getIsDeleted ()
	{
		return (String) data.get (Properties.IS_DELETED.getKey ());
	}
	
	
	public void setIsUpdated (String value)
	{
		data.set (Properties.IS_UPDATED.getKey (), value);
	}
	
	
	public String getIsUpdated ()
	{
		return (String) data.get (Properties.IS_UPDATED.getKey ());
	}
	
	
	public void setVersions (Data value)
	{
		data.set (Properties.VERSIONS.getKey (), value);
	}
	
	
	public Data getVersions ()
	{
		return (Data) data.get (Properties.VERSIONS.getKey ());
	}
	
}

