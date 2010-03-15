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


public class ValidationResultInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ELEMENT ("element"),
		ERROR_LEVEL ("errorLevel"),
		MESSAGE ("message"),
		TASK ("task");
		
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
	
	private ValidationResultInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static ValidationResultInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new ValidationResultInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setElement (String value)
	{
		data.set (Properties.ELEMENT.getKey (), value);
	}
	
	
	public String getElement ()
	{
		return (String) data.get (Properties.ELEMENT.getKey ());
	}
	
	
	public void setErrorLevel (String value)
	{
		data.set (Properties.ERROR_LEVEL.getKey (), value);
	}
	
	
	public String getErrorLevel ()
	{
		return (String) data.get (Properties.ERROR_LEVEL.getKey ());
	}
	
	
	public void setMessage (String value)
	{
		data.set (Properties.MESSAGE.getKey (), value);
	}
	
	
	public String getMessage ()
	{
		return (String) data.get (Properties.MESSAGE.getKey ());
	}
	
	
	public void setTask (String value)
	{
		data.set (Properties.TASK.getKey (), value);
	}
	
	
	public String getTask ()
	{
		return (String) data.get (Properties.TASK.getKey ());
	}
	
}

