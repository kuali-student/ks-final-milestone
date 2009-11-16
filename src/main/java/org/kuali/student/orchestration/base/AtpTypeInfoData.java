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


import java.util.Date;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.core.atp.dto.AtpTypeInfo;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class AtpTypeInfoData
	extends Data
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		NAME ("name"),
		DESC ("desc"),
		DURATION_TYPE ("durationType"),
		SEASONAL_TYPE ("seasonalType"),
		EFFECTIVE_DATE ("effectiveDate"),
		EXPIRATION_DATE ("expirationDate"),
		ATTRIBUTES ("attributes"),
		KEY ("key");
		
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
	
	public AtpTypeInfoData ()
	{
		// TODO: ask Wil if we want to really use the class name as the key
		super (AtpTypeInfo.class.getName ());
	}
	
	public void setName (String value)
	{
		super.set (Properties.NAME.getKey (), value);
	}
	
	
	public String getName ()
	{
		return super.get (Properties.NAME.getKey ());
	}
	
	
	public void setDesc (String value)
	{
		super.set (Properties.DESC.getKey (), value);
	}
	
	
	public String getDesc ()
	{
		return super.get (Properties.DESC.getKey ());
	}
	
	
	public void setDurationType (String value)
	{
		super.set (Properties.DURATION_TYPE.getKey (), value);
	}
	
	
	public String getDurationType ()
	{
		return super.get (Properties.DURATION_TYPE.getKey ());
	}
	
	
	public void setSeasonalType (String value)
	{
		super.set (Properties.SEASONAL_TYPE.getKey (), value);
	}
	
	
	public String getSeasonalType ()
	{
		return super.get (Properties.SEASONAL_TYPE.getKey ());
	}
	
	
	public void setEffectiveDate (Date value)
	{
		super.set (Properties.EFFECTIVE_DATE.getKey (), value);
	}
	
	
	public Date getEffectiveDate ()
	{
		return super.get (Properties.EFFECTIVE_DATE.getKey ());
	}
	
	
	public void setExpirationDate (Date value)
	{
		super.set (Properties.EXPIRATION_DATE.getKey (), value);
	}
	
	
	public Date getExpirationDate ()
	{
		return super.get (Properties.EXPIRATION_DATE.getKey ());
	}
	
	
	public void setAttributes (Data value)
	{
		super.set (Properties.ATTRIBUTES.getKey (), value);
	}
	
	
	public Data getAttributes ()
	{
		return super.get (Properties.ATTRIBUTES.getKey ());
	}
	
	
	public void setKey (String value)
	{
		super.set (Properties.KEY.getKey (), value);
	}
	
	
	public String getKey ()
	{
		return super.get (Properties.KEY.getKey ());
	}
	
}

