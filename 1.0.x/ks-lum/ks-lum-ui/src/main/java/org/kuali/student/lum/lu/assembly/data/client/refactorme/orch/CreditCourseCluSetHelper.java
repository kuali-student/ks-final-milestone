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


import java.util.Date;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoHelper;


public class CreditCourseCluSetHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ID ("id"),
		NAME ("name"),
		DESCRIPTION ("description"),
		FIXED_CLU_SET ("fixedCluSet"),
		DYNAMIC_CLU_SET ("dynamicCluSet"),
		EFFECTIVE_DATE ("effectiveDate"),
		EXPIRATION_DATE ("expirationDate"),
		TYPE ("type"),
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
	
	private CreditCourseCluSetHelper (Data data)
	{
		this.data = data;
	}
	
	public static CreditCourseCluSetHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CreditCourseCluSetHelper (data);
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
	
	
	public void setName (String value)
	{
		data.set (Properties.NAME.getKey (), value);
	}
	
	
	public String getName ()
	{
		return (String) data.get (Properties.NAME.getKey ());
	}
	
	
	public void setDescription (RichTextInfoHelper value)
	{
		data.set (Properties.DESCRIPTION.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RichTextInfoHelper getDescription ()
	{
		return RichTextInfoHelper.wrap ((Data) data.get (Properties.DESCRIPTION.getKey ()));
	}
	
	
	public void setFixedCluSet (Data value)
	{
		data.set (Properties.FIXED_CLU_SET.getKey (), value);
	}
	
	
	public Data getFixedCluSet ()
	{
		return (Data) data.get (Properties.FIXED_CLU_SET.getKey ());
	}
	
	
	public void setDynamicCluSet (Data value)
	{
		data.set (Properties.DYNAMIC_CLU_SET.getKey (), value);
	}
	
	
	public Data getDynamicCluSet ()
	{
		return (Data) data.get (Properties.DYNAMIC_CLU_SET.getKey ());
	}
	
	
	public void setEffectiveDate (Date value)
	{
		data.set (Properties.EFFECTIVE_DATE.getKey (), value);
	}
	
	
	public Date getEffectiveDate ()
	{
		return (Date) data.get (Properties.EFFECTIVE_DATE.getKey ());
	}
	
	
	public void setExpirationDate (Date value)
	{
		data.set (Properties.EXPIRATION_DATE.getKey (), value);
	}
	
	
	public Date getExpirationDate ()
	{
		return (Date) data.get (Properties.EXPIRATION_DATE.getKey ());
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
	
	
	public void set_runtimeData (RuntimeDataHelper value)
	{
		data.set (Properties._RUNTIME_DATA.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RuntimeDataHelper get_runtimeData ()
	{
		return RuntimeDataHelper.wrap ((Data) data.get (Properties._RUNTIME_DATA.getKey ()));
	}
	
}

