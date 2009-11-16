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


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.lum.lu.dto.LuCodeInfo;


public class LuCodeInfoData
	extends Data
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		DESC ("desc"),
		VALUE ("value"),
		ATTRIBUTES ("attributes"),
		META_INFO ("metaInfo"),
		TYPE ("type");
		
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
	
	public LuCodeInfoData ()
	{
		// TODO: ask Wil if we want to really use the class name as the key
		super (LuCodeInfo.class.getName ());
	}
	
	public void setDesc (String value)
	{
		super.set (Properties.DESC.getKey (), value);
	}
	
	
	public String getDesc ()
	{
		return super.get (Properties.DESC.getKey ());
	}
	
	
	public void setValue (String value)
	{
		super.set (Properties.VALUE.getKey (), value);
	}
	
	
	public String getValue ()
	{
		return super.get (Properties.VALUE.getKey ());
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
	
}

