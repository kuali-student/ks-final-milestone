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
package org.kuali.student.core.assembly.helper;


import org.kuali.student.core.assembly.data.Data;



public class RuntimeDataVersionsHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		TYPE ("type"),
		ID ("id"),
		VERSION_IND ("versionInd");
		
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
	
	private RuntimeDataVersionsHelper (Data data)
	{
		this.data = data;
	}
	
	public static RuntimeDataVersionsHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new RuntimeDataVersionsHelper (data);
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
	
	
	public void setId (String value)
	{
		data.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return (String) data.get (Properties.ID.getKey ());
	}
	
	
	public void setVersionInd (String value)
	{
		data.set (Properties.VERSION_IND.getKey (), value);
	}
	
	
	public String getVersionInd ()
	{
		return (String) data.get (Properties.VERSION_IND.getKey ());
	}
	
}

