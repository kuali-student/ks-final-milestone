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
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.lum.lu.dto.CluPublishingInfo;


public class CluPublishingInfoData
	extends Data
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
	
	public CluPublishingInfoData ()
	{
		// TODO: ask Wil if we want to really use the class name as the key
		super (CluPublishingInfo.class.getName ());
	}
	
	public void setStartCycle (String value)
	{
		super.set (Properties.START_CYCLE.getKey (), value);
	}
	
	
	public String getStartCycle ()
	{
		return super.get (Properties.START_CYCLE.getKey ());
	}
	
	
	public void setEndCycle (String value)
	{
		super.set (Properties.END_CYCLE.getKey (), value);
	}
	
	
	public String getEndCycle ()
	{
		return super.get (Properties.END_CYCLE.getKey ());
	}
	
	
	public void setPrimaryInstructor (CluInstructorInfoData value)
	{
		super.set (Properties.PRIMARY_INSTRUCTOR.getKey (), value);
	}
	
	
	public CluInstructorInfoData getPrimaryInstructor ()
	{
		return super.get (Properties.PRIMARY_INSTRUCTOR.getKey ());
	}
	
	
	public void setInstructors (Data value)
	{
		super.set (Properties.INSTRUCTORS.getKey (), value);
	}
	
	
	public Data getInstructors ()
	{
		return super.get (Properties.INSTRUCTORS.getKey ());
	}
	
	
	public void setAttributes (Data value)
	{
		super.set (Properties.ATTRIBUTES.getKey (), value);
	}
	
	
	public Data getAttributes ()
	{
		return super.get (Properties.ATTRIBUTES.getKey ());
	}
	
	
	public void setType (String value)
	{
		super.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return super.get (Properties.TYPE.getKey ());
	}
	
	
	public void setState (String value)
	{
		super.set (Properties.STATE.getKey (), value);
	}
	
	
	public String getState ()
	{
		return super.get (Properties.STATE.getKey ());
	}
	
	
	public void setId (String value)
	{
		super.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return super.get (Properties.ID.getKey ());
	}
	
}

