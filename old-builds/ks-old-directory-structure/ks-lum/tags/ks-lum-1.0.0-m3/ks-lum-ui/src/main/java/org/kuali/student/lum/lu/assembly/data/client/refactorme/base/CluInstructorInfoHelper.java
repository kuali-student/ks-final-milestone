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


public class CluInstructorInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ORG_ID ("orgId"),
		PERSON_ID ("personId"),
		ATTRIBUTES ("attributes");
		
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
	
	private CluInstructorInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static CluInstructorInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CluInstructorInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setOrgId (String value)
	{
		data.set (Properties.ORG_ID.getKey (), value);
	}
	
	
	public String getOrgId ()
	{
		return (String) data.get (Properties.ORG_ID.getKey ());
	}
	
	
	public void setPersonId (String value)
	{
		data.set (Properties.PERSON_ID.getKey (), value);
	}
	
	
	public String getPersonId ()
	{
		return (String) data.get (Properties.PERSON_ID.getKey ());
	}
	
	
	public void setAttributes (Data value)
	{
		data.set (Properties.ATTRIBUTES.getKey (), value);
	}
	
	
	public Data getAttributes ()
	{
		return (Data) data.get (Properties.ATTRIBUTES.getKey ());
	}
	
}

