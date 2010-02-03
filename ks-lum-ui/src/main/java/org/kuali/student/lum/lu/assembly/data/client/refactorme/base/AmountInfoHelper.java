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


public class AmountInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		UNIT_TYPE ("unitType"),
		UNIT_QUANTITY ("unitQuantity");
		
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
	
	private AmountInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static AmountInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new AmountInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setUnitType (String value)
	{
		data.set (Properties.UNIT_TYPE.getKey (), value);
	}
	
	
	public String getUnitType ()
	{
		return (String) data.get (Properties.UNIT_TYPE.getKey ());
	}
	
	
	public void setUnitQuantity (String value)
	{
		data.set (Properties.UNIT_QUANTITY.getKey (), value);
	}
	
	
	public String getUnitQuantity ()
	{
		return (String) data.get (Properties.UNIT_QUANTITY.getKey ());
	}
	
}

