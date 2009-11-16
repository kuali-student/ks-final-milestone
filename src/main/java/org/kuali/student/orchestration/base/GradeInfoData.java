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
import org.kuali.student.lum.lrc.dto.GradeInfo;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class GradeInfoData
	extends ModifiableData
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		NAME ("name"),
		VALUE ("value"),
		SCALE_KEY ("scaleKey"),
		RANK ("rank"),
		EFFECTIVE_DATE ("effectiveDate"),
		EXPIRATION_DATE ("expirationDate"),
		ATTRIBUTES ("attributes"),
		TYPE ("type"),
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
	
	public GradeInfoData ()
	{
		// TODO: ask Wil if we want to really use the class name as the key
		super (GradeInfo.class.getName ());
	}
	
	public void setName (String value)
	{
		super.set (Properties.NAME.getKey (), value);
	}
	
	
	public String getName ()
	{
		return super.get (Properties.NAME.getKey ());
	}
	
	
	public void setValue (String value)
	{
		super.set (Properties.VALUE.getKey (), value);
	}
	
	
	public String getValue ()
	{
		return super.get (Properties.VALUE.getKey ());
	}
	
	
	public void setScaleKey (String value)
	{
		super.set (Properties.SCALE_KEY.getKey (), value);
	}
	
	
	public String getScaleKey ()
	{
		return super.get (Properties.SCALE_KEY.getKey ());
	}
	
	
	public void setRank (String value)
	{
		super.set (Properties.RANK.getKey (), value);
	}
	
	
	public String getRank ()
	{
		return super.get (Properties.RANK.getKey ());
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
	
	
	public void setType (String value)
	{
		super.set (Properties.TYPE.getKey (), value);
	}
	
	
	public String getType ()
	{
		return super.get (Properties.TYPE.getKey ());
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

