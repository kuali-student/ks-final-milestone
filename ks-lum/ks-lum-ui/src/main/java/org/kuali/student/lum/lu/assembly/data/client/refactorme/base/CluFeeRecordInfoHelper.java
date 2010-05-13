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


import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;


public class CluFeeRecordInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		FEE_TYPE ("feeType"),
		FEE_AMOUNT ("feeAmount"),
		AFFILIATED_ORG_INFO_LIST ("affiliatedOrgInfoList"),
		ATTRIBUTES ("attributes"),
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
	
	private CluFeeRecordInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static CluFeeRecordInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CluFeeRecordInfoHelper (data);
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
	
	
	public void setFeeAmount (CurrencyAmountInfoHelper value)
	{
		data.set (Properties.FEE_AMOUNT.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public CurrencyAmountInfoHelper getFeeAmount ()
	{
		return CurrencyAmountInfoHelper.wrap ((Data) data.get (Properties.FEE_AMOUNT.getKey ()));
	}
	
	
	public void setAffiliatedOrgInfoList (Data value)
	{
		data.set (Properties.AFFILIATED_ORG_INFO_LIST.getKey (), value);
	}
	
	
	public Data getAffiliatedOrgInfoList ()
	{
		return (Data) data.get (Properties.AFFILIATED_ORG_INFO_LIST.getKey ());
	}
	
	
	public void setAttributes (Data value)
	{
		data.set (Properties.ATTRIBUTES.getKey (), value);
	}
	
	
	public Data getAttributes ()
	{
		return (Data) data.get (Properties.ATTRIBUTES.getKey ());
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

