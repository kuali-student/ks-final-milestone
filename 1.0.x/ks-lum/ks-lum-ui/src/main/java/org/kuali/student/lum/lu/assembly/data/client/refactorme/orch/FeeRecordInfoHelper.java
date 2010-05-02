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


public class FeeRecordInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		FIXED_RATE_FEE ("fixedRateFee"),
		VARIABLE_RATE_FEE ("variableRateFee"),
		MULTIPLE_RATE_FEE ("multipleRateFee"),
		PER_CREDIT_FEE ("perCreditFee");
		
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
	
	private FeeRecordInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static FeeRecordInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new FeeRecordInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setFixedRateFee (Data value)
	{
		data.set (Properties.FIXED_RATE_FEE.getKey (), value);
	}
	
	public Data getFixedRateFee ()
	{
		return (Data) data.get (Properties.FIXED_RATE_FEE.getKey ());
	}
	
	
	public void setVariableRateFee (Data value)
	{
		data.set (Properties.VARIABLE_RATE_FEE.getKey (), value);
	}
	
	
	public Data getVariableRateFee ()
	{
		return (Data) data.get (Properties.VARIABLE_RATE_FEE.getKey ());
	}
	
	public void setMultipleRateFee (Data value)
	{
		data.set (Properties.MULTIPLE_RATE_FEE.getKey (), value);
	}
	
	
	public Data getMultipleRateFee ()
	{
		return (Data) data.get (Properties.MULTIPLE_RATE_FEE.getKey ());
	}
	
	
	public void setPerCreditFee (Data value)
	{
		data.set (Properties.PER_CREDIT_FEE.getKey (), value);
	}
	
	
	public Data getPerCreditFee ()
	{
		return (Data) data.get (Properties.PER_CREDIT_FEE.getKey ());
	}
}

