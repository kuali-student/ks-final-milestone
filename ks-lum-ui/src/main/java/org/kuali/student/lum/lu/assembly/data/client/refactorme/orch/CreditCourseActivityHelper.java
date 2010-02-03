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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;


import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;


public class CreditCourseActivityHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ID ("id"),
		ACTIVITY_TYPE ("activityType"),
		CONTACT_HOURS ("contactHours"),
		DURATION ("duration"),
		DEFAULT_ENROLLMENT_ESTIMATE ("defaultEnrollmentEstimate"),
		STATE ("state"),
		_RUNTIME_DATA ("_runtimeData");
		
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
	
	private CreditCourseActivityHelper (Data data)
	{
		this.data = data;
	}
	
	public static CreditCourseActivityHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CreditCourseActivityHelper (data);
	}
	
	public Data getData ()
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
		data.set (Properties.CONTACT_HOURS.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public CreditCourseActivityContactHoursHelper getContactHours ()
	{
		return CreditCourseActivityContactHoursHelper.wrap ((Data) data.get (Properties.CONTACT_HOURS.getKey ()));
	}
	
	
	public void setDuration (CreditCourseActivityDurationHelper value)
	{
		data.set (Properties.DURATION.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public CreditCourseActivityDurationHelper getDuration ()
	{
		return CreditCourseActivityDurationHelper.wrap ((Data) data.get (Properties.DURATION.getKey ()));
	}
	
	
	public void setDefaultEnrollmentEstimate (Integer value)
	{
		data.set (Properties.DEFAULT_ENROLLMENT_ESTIMATE.getKey (), value);
	}
	
	
	public Integer getDefaultEnrollmentEstimate ()
	{
		return (Integer) data.get (Properties.DEFAULT_ENROLLMENT_ESTIMATE.getKey ());
	}
	
	
	public void setState (String value)
	{
		data.set (Properties.STATE.getKey (), value);
	}
	
	
	public String getState ()
	{
		return (String) data.get (Properties.STATE.getKey ());
	}
	
	
	public void set_runtimeData (RuntimeDataHelper value)
	{
		data.set (Properties._RUNTIME_DATA.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RuntimeDataHelper get_runtimeData ()
	{
		return RuntimeDataHelper.wrap ((Data) data.get (Properties._RUNTIME_DATA.getKey ()));
	}
	
}

