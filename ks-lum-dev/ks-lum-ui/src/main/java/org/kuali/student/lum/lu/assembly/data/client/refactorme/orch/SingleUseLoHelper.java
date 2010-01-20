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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.orch;


import java.util.Date;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoHelper;


public class SingleUseLoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ID ("id"),
		DESCRIPTION ("description"),
		CATEGORY ("category"),
		LO_REPOSITORY ("loRepository"),
		CHILD_SINGLE_USE_LOS ("childSingleUseLos"),
		EFFECTIVE_DATE ("effectiveDate"),
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
	
	private SingleUseLoHelper (Data data)
	{
		this.data = data;
	}
	
	public static SingleUseLoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new SingleUseLoHelper (data);
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
	
	
	public void setDescription (RichTextInfoHelper value)
	{
		data.set (Properties.DESCRIPTION.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RichTextInfoHelper getDescription ()
	{
		return RichTextInfoHelper.wrap ((Data) data.get (Properties.DESCRIPTION.getKey ()));
	}
	
	
	public void setCategory (Data value)
	{
		data.set (Properties.CATEGORY.getKey (), value);
	}
	
	
	public Data getCategory ()
	{
		return (Data) data.get (Properties.CATEGORY.getKey ());
	}
	
	
	public void setLoRepository (String value)
	{
		data.set (Properties.LO_REPOSITORY.getKey (), value);
	}
	
	
	public String getLoRepository ()
	{
		return (String) data.get (Properties.LO_REPOSITORY.getKey ());
	}
	
	
	public void setChildSingleUseLos (Data value)
	{
		data.set (Properties.CHILD_SINGLE_USE_LOS.getKey (), value);
	}
	
	
	public Data getChildSingleUseLos ()
	{
		return (Data) data.get (Properties.CHILD_SINGLE_USE_LOS.getKey ());
	}
	
	
	public void setEffectiveDate (Date value)
	{
		data.set (Properties.EFFECTIVE_DATE.getKey (), value);
	}
	
	
	public Date getEffectiveDate ()
	{
		return (Date) data.get (Properties.EFFECTIVE_DATE.getKey ());
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

