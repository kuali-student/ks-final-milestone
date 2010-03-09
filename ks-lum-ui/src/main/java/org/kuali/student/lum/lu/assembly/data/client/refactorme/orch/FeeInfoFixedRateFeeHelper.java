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


public class FeeInfoFixedRateFeeHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		FEE_TYPE ("feeType"),
		RATE_TYPE ("rateType"),
		AMOUNT ("amount"),
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
	
	private FeeInfoFixedRateFeeHelper (Data data)
	{
		this.data = data;
	}
	
	public static FeeInfoFixedRateFeeHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new FeeInfoFixedRateFeeHelper (data);
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
	
	
	public void setRateType (String value)
	{
		data.set (Properties.RATE_TYPE.getKey (), value);
	}
	
	
	public String getRateType ()
	{
		return (String) data.get (Properties.RATE_TYPE.getKey ());
	}
	
	
	public void setAmount (String value)
	{
		data.set (Properties.AMOUNT.getKey (), value);
	}
	
	
	public String getAmount ()
	{
		return (String) data.get (Properties.AMOUNT.getKey ());
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

