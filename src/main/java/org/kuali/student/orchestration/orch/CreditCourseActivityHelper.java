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
package org.kuali.student.orchestration.orch;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class CreditCourseActivityHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ID ("Id"),
		ACTIVITY_TYPE ("ActivityType"),
		CONTACT_HOURS ("ContactHours"),
		VERSION_IND ("VersionInd");
		
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
	private ModifiableData data;
	
	public CreditCourseActivityHelper (ModifiableData data)
	{
		this.data = data;
	}
	
	public ModifiableData getData ()
	{
		return data;
	}
	
	
	public void setId (String value)
	{
		data.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return (String) data.get (Properties.ID.getKey ());
	}
	
	
	public void setActivityType (String value)
	{
		data.set (Properties.ACTIVITY_TYPE.getKey (), value);
	}
	
	
	public String getActivityType ()
	{
		return (String) data.get (Properties.ACTIVITY_TYPE.getKey ());
	}
	
	
	public void setContactHours (CreditCourseActivityContactHoursHelper value)
	{
		data.set (Properties.CONTACT_HOURS.getKey (), value.getData ());
	}
	
	
	public CreditCourseActivityContactHoursHelper getContactHours ()
	{
		return new CreditCourseActivityContactHoursHelper ((Data) data.get (Properties.CONTACT_HOURS.getKey ()));
	}
	
	
	public void setVersionInd (String value)
	{
		data.set (Properties.VERSION_IND.getKey (), value);
	}
	
	
	public String getVersionInd ()
	{
		return (String) data.get (Properties.VERSION_IND.getKey ());
	}
	
}

