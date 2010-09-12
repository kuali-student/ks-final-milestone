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


import java.util.Date;

import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;
import org.kuali.student.lum.common.client.lo.MetaInfoHelper;
import org.kuali.student.lum.common.client.lo.RichTextInfoHelper;


public class ResultOptionInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		DESC ("desc"),
		RESULT_USAGE_TYPE_KEY ("resultUsageTypeKey"),
		RESULT_COMPONENT_ID ("resultComponentId"),
		EFFECTIVE_DATE ("effectiveDate"),
		EXPIRATION_DATE ("expirationDate"),
		META_INFO ("metaInfo"),
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
	private Data data;
	
	private ResultOptionInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static ResultOptionInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new ResultOptionInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setDesc (RichTextInfoHelper value)
	{
		data.set (Properties.DESC.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RichTextInfoHelper getDesc ()
	{
		return RichTextInfoHelper.wrap ((Data) data.get (Properties.DESC.getKey ()));
	}
	
	
	public void setResultUsageTypeKey (String value)
	{
		data.set (Properties.RESULT_USAGE_TYPE_KEY.getKey (), value);
	}
	
	
	public String getResultUsageTypeKey ()
	{
		return (String) data.get (Properties.RESULT_USAGE_TYPE_KEY.getKey ());
	}
	
	
	public void setResultComponentId (String value)
	{
		data.set (Properties.RESULT_COMPONENT_ID.getKey (), value);
	}
	
	
	public String getResultComponentId ()
	{
		return (String) data.get (Properties.RESULT_COMPONENT_ID.getKey ());
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
	
	
	public void setMetaInfo (MetaInfoHelper value)
	{
		data.set (Properties.META_INFO.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public MetaInfoHelper getMetaInfo ()
	{
		return MetaInfoHelper.wrap ((Data) data.get (Properties.META_INFO.getKey ()));
	}
	
	
	public void setState (String value)
	{
		data.set (Properties.STATE.getKey (), value);
	}
	
	
	public String getState ()
	{
		return (String) data.get (Properties.STATE.getKey ());
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

