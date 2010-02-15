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


public class RichTextInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		PLAIN ("plain"),
		FORMATTED ("formatted");
		
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
	
	private RichTextInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static RichTextInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new RichTextInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setPlain (String value)
	{
		data.set (Properties.PLAIN.getKey (), value);
	}
	
	
	public String getPlain ()
	{
		return (String) data.get (Properties.PLAIN.getKey ());
	}
	
	
	public void setFormatted (String value)
	{
		data.set (Properties.FORMATTED.getKey (), value);
	}
	
	
	public String getFormatted ()
	{
		return (String) data.get (Properties.FORMATTED.getKey ());
	}
	
}

