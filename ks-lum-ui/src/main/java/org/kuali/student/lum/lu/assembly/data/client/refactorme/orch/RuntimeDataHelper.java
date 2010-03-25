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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;


import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;


public class RuntimeDataHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		CREATED ("created"),
		DELETED ("deleted"),
		UPDATED ("updated"),
		VERSIONS ("versions"),
		DIRTY ("dirty");
		
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
	
	private RuntimeDataHelper (Data data)
	{
		this.data = data;
	}
	
	public static RuntimeDataHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new RuntimeDataHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setCreated (Boolean value)
	{
		data.set (Properties.CREATED.getKey (), value);
	}
	
	
	public Boolean isCreated ()
	{
		return (Boolean) data.get (Properties.CREATED.getKey ());
	}
	
	
	public void setDeleted (Boolean value)
	{
		data.set (Properties.DELETED.getKey (), value);
	}
	
	
	public Boolean isDeleted ()
	{
		return (Boolean) data.get (Properties.DELETED.getKey ());
	}
	
	
	public void setUpdated (Boolean value)
	{
		data.set (Properties.UPDATED.getKey (), value);
	}
	
	
	public Boolean isUpdated ()
	{
		return (Boolean) data.get (Properties.UPDATED.getKey ());
	}
	
	
	public void setVersions (Data value)
	{
		data.set (Properties.VERSIONS.getKey (), value);
	}
	
	
	public Data getVersions ()
	{
		return (Data) data.get (Properties.VERSIONS.getKey ());
	}
	
	
	public void setDirty (Boolean value)
	{
		data.set (Properties.DIRTY.getKey (), value);
	}
	
	
	public Boolean isDirty ()
	{
		return (Boolean) data.get (Properties.DIRTY.getKey ());
	}
	
}

