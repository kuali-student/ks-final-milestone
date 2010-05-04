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


public class FieldDescriptorInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		NAME ("name"),
		DESC ("desc"),
		DATA_TYPE ("dataType"),
		CONSTRAINTS ("constraints"),
		COMPLEX_STRUCTURE ("complexStructure"),
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
	
	private FieldDescriptorInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static FieldDescriptorInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new FieldDescriptorInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setName (String value)
	{
		data.set (Properties.NAME.getKey (), value);
	}
	
	
	public String getName ()
	{
		return (String) data.get (Properties.NAME.getKey ());
	}
	
	
	public void setDesc (String value)
	{
		data.set (Properties.DESC.getKey (), value);
	}
	
	
	public String getDesc ()
	{
		return (String) data.get (Properties.DESC.getKey ());
	}
	
	
	public void setDataType (String value)
	{
		data.set (Properties.DATA_TYPE.getKey (), value);
	}
	
	
	public String getDataType ()
	{
		return (String) data.get (Properties.DATA_TYPE.getKey ());
	}
	
	
	public void setConstraints (Data value)
	{
		data.set (Properties.CONSTRAINTS.getKey (), value);
	}
	
	
	public Data getConstraints ()
	{
		return (Data) data.get (Properties.CONSTRAINTS.getKey ());
	}
	
	
	public void setComplexStructure (ObjectStructureInfoHelper value)
	{
		data.set (Properties.COMPLEX_STRUCTURE.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public ObjectStructureInfoHelper getComplexStructure ()
	{
		return ObjectStructureInfoHelper.wrap ((Data) data.get (Properties.COMPLEX_STRUCTURE.getKey ()));
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

