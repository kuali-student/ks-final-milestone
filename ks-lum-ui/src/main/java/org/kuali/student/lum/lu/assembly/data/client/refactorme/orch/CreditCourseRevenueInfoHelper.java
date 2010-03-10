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


public class CreditCourseRevenueInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		FEE_TYPE ("feeType"),
		ID ("id"),
		REVENUE_ORG ("revenueOrg"),
		TOTAL_PERCENTAGE ("totalPercentage");
		
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
	
	private CreditCourseRevenueInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static CreditCourseRevenueInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CreditCourseRevenueInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setFeeType (String value)
	{
		data.set (Properties.FEE_TYPE.getKey (), value);
	}
	
	
	public String getFeeType ()
	{
		return (String) data.get (Properties.FEE_TYPE.getKey ());
	}
	
	
	public void setId (String value)
	{
		data.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return (String) data.get (Properties.ID.getKey ());
	}
	
	
	public void setRevenueOrg (Data value)
	{
		data.set (Properties.REVENUE_ORG.getKey (), value);
	}
	
	
	public Data getRevenueOrg ()
	{
		return (Data) data.get (Properties.REVENUE_ORG.getKey ());
	}
	
	
	public void setTotalPercentage (String value)
	{
		data.set (Properties.TOTAL_PERCENTAGE.getKey (), value);
	}
	
	
	public String getTotalPercentage ()
	{
		return (String) data.get (Properties.TOTAL_PERCENTAGE.getKey ());
	}
	
}

