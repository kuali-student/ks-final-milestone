/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.organization.assembly.data.client;


import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;



public class OrgHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		TYPE ("type"),
		NAME ("name"),
		ABBR ("abbr"),
		_RUNTIME_DATA ("_runtimeData"),
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
	
	private OrgHelper (Data data)
	{
		this.data = data;
	}
	
	public static OrgHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new OrgHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setType (String value)
	{
		data.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return (String) data.get (Properties.TYPE.getKey ());
	}
	
	
	public void setName (String value)
	{
		data.set (Properties.NAME.getKey (), value);
	}
	
	
	public String getName ()
	{
		return (String) data.get (Properties.NAME.getKey ());
	}
	
	
	public void setAbbr (String value)
	{
		data.set (Properties.ABBR.getKey (), value);
	}
	
	
	public String getAbbr ()
	{
		return (String) data.get (Properties.ABBR.getKey ());
	}
	
	
	public void set_runtimeData (RuntimeDataHelper value)
	{
		data.set (Properties._RUNTIME_DATA.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RuntimeDataHelper get_runtimeData ()
	{
		return RuntimeDataHelper.wrap ((Data) data.get (Properties._RUNTIME_DATA.getKey ()));
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

