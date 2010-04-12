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


public class CluFeeInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		CLU_FEE_RECORDS ("cluFeeRecords"),
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
	
	private CluFeeInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static CluFeeInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CluFeeInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setCluFeeRecords (Data value)
	{
		data.set (Properties.CLU_FEE_RECORDS.getKey (), value);
	}
	
	
	public Data getCluFeeRecords ()
	{
		return (Data) data.get (Properties.CLU_FEE_RECORDS.getKey ());
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

