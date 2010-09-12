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


public class FieldSelectorInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		FIELD_DESCRIPTOR ("fieldDescriptor"),
		IS_SELECTOR ("isSelector"),
		IS_DYNAMIC ("isDynamic"),
		KEY ("key");
		
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
	
	private FieldSelectorInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static FieldSelectorInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new FieldSelectorInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setFieldDescriptor (FieldDescriptorInfoHelper value)
	{
		data.set (Properties.FIELD_DESCRIPTOR.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public FieldDescriptorInfoHelper getFieldDescriptor ()
	{
		return FieldDescriptorInfoHelper.wrap ((Data) data.get (Properties.FIELD_DESCRIPTOR.getKey ()));
	}
	
	
	public void setIsSelector (Boolean value)
	{
		data.set (Properties.IS_SELECTOR.getKey (), value);
	}
	
	
	public Boolean isIsSelector ()
	{
		return (Boolean) data.get (Properties.IS_SELECTOR.getKey ());
	}
	
	
	public void setIsDynamic (Boolean value)
	{
		data.set (Properties.IS_DYNAMIC.getKey (), value);
	}
	
	
	public Boolean isIsDynamic ()
	{
		return (Boolean) data.get (Properties.IS_DYNAMIC.getKey ());
	}
	
	
	public void setKey (String value)
	{
		data.set (Properties.KEY.getKey (), value);
	}
	
	
	public String getKey ()
	{
		return (String) data.get (Properties.KEY.getKey ());
	}
	
}

