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
package org.kuali.student.lum.lu.assembly.data.client.refactorme.base;


import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class CluIdentifierInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		CODE ("code"),
		SHORT_NAME ("shortName"),
		LONG_NAME ("longName"),
		LEVEL ("level"),
		DIVISION ("division"),
		SUFFIX_CODE ("suffixCode"),
		VARIATION ("variation"),
		ORG_ID ("orgId"),
		TYPE ("type"),
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
	
	private CluIdentifierInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static CluIdentifierInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new CluIdentifierInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setCode (String value)
	{
		data.set (Properties.CODE.getKey (), value);
	}
	
	
	public String getCode ()
	{
		return (String) data.get (Properties.CODE.getKey ());
	}
	
	
	public void setShortName (String value)
	{
		data.set (Properties.SHORT_NAME.getKey (), value);
	}
	
	
	public String getShortName ()
	{
		return (String) data.get (Properties.SHORT_NAME.getKey ());
	}
	
	
	public void setLongName (String value)
	{
		data.set (Properties.LONG_NAME.getKey (), value);
	}
	
	
	public String getLongName ()
	{
		return (String) data.get (Properties.LONG_NAME.getKey ());
	}
	
	
	public void setLevel (String value)
	{
		data.set (Properties.LEVEL.getKey (), value);
	}
	
	
	public String getLevel ()
	{
		return (String) data.get (Properties.LEVEL.getKey ());
	}
	
	
	public void setDivision (String value)
	{
		data.set (Properties.DIVISION.getKey (), value);
	}
	
	
	public String getDivision ()
	{
		return (String) data.get (Properties.DIVISION.getKey ());
	}
	
	
	public void setSuffixCode (String value)
	{
		data.set (Properties.SUFFIX_CODE.getKey (), value);
	}
	
	
	public String getSuffixCode ()
	{
		return (String) data.get (Properties.SUFFIX_CODE.getKey ());
	}
	
	
	public void setVariation (String value)
	{
		data.set (Properties.VARIATION.getKey (), value);
	}
	
	
	public String getVariation ()
	{
		return (String) data.get (Properties.VARIATION.getKey ());
	}
	
	
	public void setOrgId (String value)
	{
		data.set (Properties.ORG_ID.getKey (), value);
	}
	
	
	public String getOrgId ()
	{
		return (String) data.get (Properties.ORG_ID.getKey ());
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
	
	
	public void setId (String value)
	{
		data.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return (String) data.get (Properties.ID.getKey ());
	}
	
}

