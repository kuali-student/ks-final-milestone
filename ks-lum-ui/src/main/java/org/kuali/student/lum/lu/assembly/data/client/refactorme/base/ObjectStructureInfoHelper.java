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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.base;


import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;


public class ObjectStructureInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		TYPE ("type"),
		OBJECT_TYPE_KEY ("objectTypeKey");
		
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
	
	private ObjectStructureInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static ObjectStructureInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new ObjectStructureInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setType (Data value)
	{
		data.set (Properties.TYPE.getKey (), value);
	}
	
	
	public Data getType ()
	{
		return (Data) data.get (Properties.TYPE.getKey ());
	}
	
	
	public void setObjectTypeKey (String value)
	{
		data.set (Properties.OBJECT_TYPE_KEY.getKey (), value);
	}
	
	
	public String getObjectTypeKey ()
	{
		return (String) data.get (Properties.OBJECT_TYPE_KEY.getKey ());
	}
	
}

