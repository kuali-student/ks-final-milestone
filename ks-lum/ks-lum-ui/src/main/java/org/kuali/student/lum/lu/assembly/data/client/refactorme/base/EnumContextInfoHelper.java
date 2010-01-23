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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.base;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class EnumContextInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		CONTEXT_VALUE_DESCRIPTOR ("contextValueDescriptor"),
		TYPE ("type");
		
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
	
	private EnumContextInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static EnumContextInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new EnumContextInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setContextValueDescriptor (FieldDescriptorInfoHelper value)
	{
		data.set (Properties.CONTEXT_VALUE_DESCRIPTOR.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public FieldDescriptorInfoHelper getContextValueDescriptor ()
	{
		return FieldDescriptorInfoHelper.wrap ((Data) data.get (Properties.CONTEXT_VALUE_DESCRIPTOR.getKey ()));
	}
	
	
	public void setType (String value)
	{
		data.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return (String) data.get (Properties.TYPE.getKey ());
	}
	
}

