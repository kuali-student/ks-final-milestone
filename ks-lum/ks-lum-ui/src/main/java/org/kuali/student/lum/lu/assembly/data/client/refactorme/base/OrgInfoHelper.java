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


public class OrgInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		LONG_NAME ("longName"),
		SHORT_NAME ("shortName"),
		SORT_NAME ("sortName"),
		SHORT_DESC ("shortDesc"),
		LONG_DESC ("longDesc"),
		EFFECTIVE_DATE ("effectiveDate"),
		EXPIRATION_DATE ("expirationDate"),
		ORG_CODES ("orgCodes"),
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
	private Data data;
	
	private OrgInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static OrgInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new OrgInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setLongName (String value)
	{
		data.set (Properties.LONG_NAME.getKey (), value);
	}
	
	
	public String getLongName ()
	{
		return (String) data.get (Properties.LONG_NAME.getKey ());
	}
	
	
	public void setShortName (String value)
	{
		data.set (Properties.SHORT_NAME.getKey (), value);
	}
	
	
	public String getShortName ()
	{
		return (String) data.get (Properties.SHORT_NAME.getKey ());
	}
	
	
	public void setSortName (String value)
	{
		data.set (Properties.SORT_NAME.getKey (), value);
	}
	
	
	public String getSortName ()
	{
		return (String) data.get (Properties.SORT_NAME.getKey ());
	}
	
	
	public void setShortDesc (RichTextInfoHelper value)
	{
		data.set (Properties.SHORT_DESC.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RichTextInfoHelper getShortDesc ()
	{
		return RichTextInfoHelper.wrap ((Data) data.get (Properties.SHORT_DESC.getKey ()));
	}
	
	
	public void setLongDesc (RichTextInfoHelper value)
	{
		data.set (Properties.LONG_DESC.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RichTextInfoHelper getLongDesc ()
	{
		return RichTextInfoHelper.wrap ((Data) data.get (Properties.LONG_DESC.getKey ()));
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
	
	
	public void setOrgCodes (Data value)
	{
		data.set (Properties.ORG_CODES.getKey (), value);
	}
	
	
	public Data getOrgCodes ()
	{
		return (Data) data.get (Properties.ORG_CODES.getKey ());
	}
	
	
	public void setAttributes (Data value)
	{
		data.set (Properties.ATTRIBUTES.getKey (), value);
	}
	
	
	public Data getAttributes ()
	{
		return (Data) data.get (Properties.ATTRIBUTES.getKey ());
	}
	
	
	public void setMetaInfo (MetaInfoHelper value)
	{
		data.set (Properties.META_INFO.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public MetaInfoHelper getMetaInfo ()
	{
		return MetaInfoHelper.wrap ((Data) data.get (Properties.META_INFO.getKey ()));
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

