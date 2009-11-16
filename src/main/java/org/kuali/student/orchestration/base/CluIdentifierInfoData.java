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
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;


public class CluIdentifierInfoData
	extends Data
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
	
	public CluIdentifierInfoData ()
	{
		// TODO: ask Wil if we want to really use the class name as the key
		super (CluIdentifierInfo.class.getName ());
	}
	
	public void setCode (String value)
	{
		super.set (Properties.CODE.getKey (), value);
	}
	
	
	public String getCode ()
	{
		return super.get (Properties.CODE.getKey ());
	}
	
	
	public void setShortName (String value)
	{
		super.set (Properties.SHORT_NAME.getKey (), value);
	}
	
	
	public String getShortName ()
	{
		return super.get (Properties.SHORT_NAME.getKey ());
	}
	
	
	public void setLongName (String value)
	{
		super.set (Properties.LONG_NAME.getKey (), value);
	}
	
	
	public String getLongName ()
	{
		return super.get (Properties.LONG_NAME.getKey ());
	}
	
	
	public void setLevel (String value)
	{
		super.set (Properties.LEVEL.getKey (), value);
	}
	
	
	public String getLevel ()
	{
		return super.get (Properties.LEVEL.getKey ());
	}
	
	
	public void setDivision (String value)
	{
		super.set (Properties.DIVISION.getKey (), value);
	}
	
	
	public String getDivision ()
	{
		return super.get (Properties.DIVISION.getKey ());
	}
	
	
	public void setSuffixCode (String value)
	{
		super.set (Properties.SUFFIX_CODE.getKey (), value);
	}
	
	
	public String getSuffixCode ()
	{
		return super.get (Properties.SUFFIX_CODE.getKey ());
	}
	
	
	public void setVariation (String value)
	{
		super.set (Properties.VARIATION.getKey (), value);
	}
	
	
	public String getVariation ()
	{
		return super.get (Properties.VARIATION.getKey ());
	}
	
	
	public void setOrgId (String value)
	{
		super.set (Properties.ORG_ID.getKey (), value);
	}
	
	
	public String getOrgId ()
	{
		return super.get (Properties.ORG_ID.getKey ());
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
	
	
	public void setId (String value)
	{
		super.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return super.get (Properties.ID.getKey ());
	}
	
}

