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
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;


public class ReqComponentInfoData
	extends ModifiableData
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		DESC ("desc"),
		REQ_COMP_FIELD ("reqCompField"),
		EFFECTIVE_DATE ("effectiveDate"),
		EXPIRATION_DATE ("expirationDate"),
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
	
	public ReqComponentInfoData ()
	{
		// TODO: ask Wil if we want to really use the class name as the key
		super (ReqComponentInfo.class.getName ());
	}
	
	public void setDesc (RichTextInfoData value)
	{
		super.set (Properties.DESC.getKey (), value);
	}
	
	
	public RichTextInfoData getDesc ()
	{
		return super.get (Properties.DESC.getKey ());
	}
	
	
	public void setReqCompField (ReqCompFieldInfoData value)
	{
		super.set (Properties.REQ_COMP_FIELD.getKey (), value);
	}
	
	
	public ReqCompFieldInfoData getReqCompField ()
	{
		return super.get (Properties.REQ_COMP_FIELD.getKey ());
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

