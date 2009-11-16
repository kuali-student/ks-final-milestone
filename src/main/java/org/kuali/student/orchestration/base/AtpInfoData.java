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
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class AtpInfoData
	extends Data
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		NAME ("name"),
		DESC ("desc"),
		START_DATE ("startDate"),
		END_DATE ("endDate"),
		ATTRIBUTES ("attributes"),
		META_INFO ("metaInfo"),
		TYPE ("type"),
		STATE ("state"),
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
	
	public AtpInfoData ()
	{
		// TODO: ask Wil if we want to really use the class name as the key
		super (AtpInfo.class.getName ());
	}
	
	public void setName (String value)
	{
		super.set (Properties.NAME.getKey (), value);
	}
	
	
	public String getName ()
	{
		return super.get (Properties.NAME.getKey ());
	}
	
	
	public void setDesc (RichTextInfoData value)
	{
		super.set (Properties.DESC.getKey (), value);
	}
	
	
	public RichTextInfoData getDesc ()
	{
		return super.get (Properties.DESC.getKey ());
	}
	
	
	public void setStartDate (Date value)
	{
		super.set (Properties.START_DATE.getKey (), value);
	}
	
	
	public Date getStartDate ()
	{
		return super.get (Properties.START_DATE.getKey ());
	}
	
	
	public void setEndDate (Date value)
	{
		super.set (Properties.END_DATE.getKey (), value);
	}
	
	
	public Date getEndDate ()
	{
		return super.get (Properties.END_DATE.getKey ());
	}
	
	
	public void setAttributes (Data value)
	{
		super.set (Properties.ATTRIBUTES.getKey (), value);
	}
	
	
	public Data getAttributes ()
	{
		return super.get (Properties.ATTRIBUTES.getKey ());
	}
	
	
	public void setMetaInfo (MetaInfoData value)
	{
		super.set (Properties.META_INFO.getKey (), value);
	}
	
	
	public MetaInfoData getMetaInfo ()
	{
		return super.get (Properties.META_INFO.getKey ());
	}
	
	
	public void setType (String value)
	{
		super.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return super.get (Properties.TYPE.getKey ());
	}
	
	
	public void setState (String value)
	{
		super.set (Properties.STATE.getKey (), value);
	}
	
	
	public String getState ()
	{
		return super.get (Properties.STATE.getKey ());
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

