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
import org.kuali.student.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class LoLoRelationInfoData
	extends ModifiableData
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		LO_ID ("loId"),
		RELATED_LO_ID ("relatedLoId"),
		EFFECTIVE_DATE ("effectiveDate"),
		EXPIRATION_DATE ("expirationDate"),
		ATTRIBUTES ("attributes"),
		META_INFO ("metaInfo"),
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
	
	public LoLoRelationInfoData ()
	{
		// TODO: ask Wil if we want to really use the class name as the key
		super (LoLoRelationInfo.class.getName ());
	}
	
	public void setLoId (String value)
	{
		super.set (Properties.LO_ID.getKey (), value);
	}
	
	
	public String getLoId ()
	{
		return super.get (Properties.LO_ID.getKey ());
	}
	
	
	public void setRelatedLoId (String value)
	{
		super.set (Properties.RELATED_LO_ID.getKey (), value);
	}
	
	
	public String getRelatedLoId ()
	{
		return super.get (Properties.RELATED_LO_ID.getKey ());
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
	
	
	public void setId (String value)
	{
		super.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return super.get (Properties.ID.getKey ());
	}
	
}

