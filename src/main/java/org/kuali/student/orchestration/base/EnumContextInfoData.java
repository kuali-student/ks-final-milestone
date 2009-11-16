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
package org.kuali.student.orchestration.base;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.core.enumerationmanagement.dto.EnumContextInfo;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class EnumContextInfoData
	extends Data
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
	
	public EnumContextInfoData ()
	{
		// TODO: ask Wil if we want to really use the class name as the key
		super (EnumContextInfo.class.getName ());
	}
	
	public void setContextValueDescriptor (FieldDescriptorInfoData value)
	{
		super.set (Properties.CONTEXT_VALUE_DESCRIPTOR.getKey (), value);
	}
	
	
	public FieldDescriptorInfoData getContextValueDescriptor ()
	{
		return super.get (Properties.CONTEXT_VALUE_DESCRIPTOR.getKey ());
	}
	
	
	public void setType (String value)
	{
		super.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return super.get (Properties.TYPE.getKey ());
	}
	
}

