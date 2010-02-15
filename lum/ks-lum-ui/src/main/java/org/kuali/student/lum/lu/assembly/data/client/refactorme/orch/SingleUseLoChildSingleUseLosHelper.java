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


public class SingleUseLoChildSingleUseLosHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ID ("id"),
		CHILD_LO ("childLo"),
		EFFECTIVE_DATE ("effectiveDate"),
		TYPE ("type"),
		STATE ("state"),
		SEQUENCE ("sequence");
		
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
	
	private SingleUseLoChildSingleUseLosHelper (Data data)
	{
		this.data = data;
	}
	
	public static SingleUseLoChildSingleUseLosHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new SingleUseLoChildSingleUseLosHelper (data);
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
	
	
	public void setChildLo (SingleUseLoHelper value)
	{
		data.set (Properties.CHILD_LO.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public SingleUseLoHelper getChildLo ()
	{
		return SingleUseLoHelper.wrap ((Data) data.get (Properties.CHILD_LO.getKey ()));
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
	
	
	public void setSequence (String value)
	{
		data.set (Properties.SEQUENCE.getKey (), value);
	}
	
	
	public String getSequence ()
	{
		return (String) data.get (Properties.SEQUENCE.getKey ());
	}
	
}

