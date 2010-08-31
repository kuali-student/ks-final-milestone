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


import java.util.Date;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;


public class EnumeratedValueInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		CODE ("code"),
		ABBREV_VALUE ("abbrevValue"),
		VALUE ("value"),
		EFFECTIVE_DATE ("effectiveDate"),
		EXPIRATION_DATE ("expirationDate"),
		SORT_KEY ("sortKey"),
		CONTEXTS ("contexts");
		
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
	
	private EnumeratedValueInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static EnumeratedValueInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new EnumeratedValueInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setCode (String value)
	{
		data.set (Properties.CODE.getKey (), value);
	}
	
	
	public String getCode ()
	{
		return (String) data.get (Properties.CODE.getKey ());
	}
	
	
	public void setAbbrevValue (String value)
	{
		data.set (Properties.ABBREV_VALUE.getKey (), value);
	}
	
	
	public String getAbbrevValue ()
	{
		return (String) data.get (Properties.ABBREV_VALUE.getKey ());
	}
	
	
	public void setValue (String value)
	{
		data.set (Properties.VALUE.getKey (), value);
	}
	
	
	public String getValue ()
	{
		return (String) data.get (Properties.VALUE.getKey ());
	}
	
	
	public void setEffectiveDate (Date value)
	{
		data.set (Properties.EFFECTIVE_DATE.getKey (), value);
	}
	
	
	public Date getEffectiveDate ()
	{
		return (Date) data.get (Properties.EFFECTIVE_DATE.getKey ());
	}
	
	
	public void setExpirationDate (Date value)
	{
		data.set (Properties.EXPIRATION_DATE.getKey (), value);
	}
	
	
	public Date getExpirationDate ()
	{
		return (Date) data.get (Properties.EXPIRATION_DATE.getKey ());
	}
	
	
	public void setSortKey (String value)
	{
		data.set (Properties.SORT_KEY.getKey (), value);
	}
	
	
	public String getSortKey ()
	{
		return (String) data.get (Properties.SORT_KEY.getKey ());
	}
	
	
	public void setContexts (Data value)
	{
		data.set (Properties.CONTEXTS.getKey (), value);
	}
	
	
	public Data getContexts ()
	{
		return (Data) data.get (Properties.CONTEXTS.getKey ());
	}
	
}

