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


public class CluPublishingInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		START_CYCLE ("startCycle"),
		END_CYCLE ("endCycle"),
		PRIMARY_INSTRUCTOR ("primaryInstructor"),
		INSTRUCTORS ("instructors"),
		ATTRIBUTES ("attributes"),
		TYPE ("type"),
		STATE ("state"),
		ID ("id");
		
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
	
	private CluPublishingInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static CluPublishingInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CluPublishingInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setStartCycle (String value)
	{
		data.set (Properties.START_CYCLE.getKey (), value);
	}
	
	
	public String getStartCycle ()
	{
		return (String) data.get (Properties.START_CYCLE.getKey ());
	}
	
	
	public void setEndCycle (String value)
	{
		data.set (Properties.END_CYCLE.getKey (), value);
	}
	
	
	public String getEndCycle ()
	{
		return (String) data.get (Properties.END_CYCLE.getKey ());
	}
	
	
	public void setPrimaryInstructor (CluInstructorInfoHelper value)
	{
		data.set (Properties.PRIMARY_INSTRUCTOR.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public CluInstructorInfoHelper getPrimaryInstructor ()
	{
		return CluInstructorInfoHelper.wrap ((Data) data.get (Properties.PRIMARY_INSTRUCTOR.getKey ()));
	}
	
	
	public void setInstructors (Data value)
	{
		data.set (Properties.INSTRUCTORS.getKey (), value);
	}
	
	
	public Data getInstructors ()
	{
		return (Data) data.get (Properties.INSTRUCTORS.getKey ());
	}
	
	
	public void setAttributes (Data value)
	{
		data.set (Properties.ATTRIBUTES.getKey (), value);
	}
	
	
	public Data getAttributes ()
	{
		return (Data) data.get (Properties.ATTRIBUTES.getKey ());
	}
	
	
	public void setType (String value)
	{
		data.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return (String) data.get (Properties.TYPE.getKey ());
	}
	
	
	public void setState (String value)
	{
		data.set (Properties.STATE.getKey (), value);
	}
	
	
	public String getState ()
	{
		return (String) data.get (Properties.STATE.getKey ());
	}
	
	
	public void setId (String value)
	{
		data.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return (String) data.get (Properties.ID.getKey ());
	}
	
}

