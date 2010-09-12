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


public class TagInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		NAMESPACE ("namespace"),
		PREDICATE ("predicate"),
		VALUE ("value"),
		REFERENCE_TYPE_KEY ("referenceTypeKey"),
		REFERENCE_ID ("referenceId"),
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
	private Data data;
	
	private TagInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static TagInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new TagInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setNamespace (String value)
	{
		data.set (Properties.NAMESPACE.getKey (), value);
	}
	
	
	public String getNamespace ()
	{
		return (String) data.get (Properties.NAMESPACE.getKey ());
	}
	
	
	public void setPredicate (String value)
	{
		data.set (Properties.PREDICATE.getKey (), value);
	}
	
	
	public String getPredicate ()
	{
		return (String) data.get (Properties.PREDICATE.getKey ());
	}
	
	
	public void setValue (String value)
	{
		data.set (Properties.VALUE.getKey (), value);
	}
	
	
	public String getValue ()
	{
		return (String) data.get (Properties.VALUE.getKey ());
	}
	
	
	public void setReferenceTypeKey (String value)
	{
		data.set (Properties.REFERENCE_TYPE_KEY.getKey (), value);
	}
	
	
	public String getReferenceTypeKey ()
	{
		return (String) data.get (Properties.REFERENCE_TYPE_KEY.getKey ());
	}
	
	
	public void setReferenceId (String value)
	{
		data.set (Properties.REFERENCE_ID.getKey (), value);
	}
	
	
	public String getReferenceId ()
	{
		return (String) data.get (Properties.REFERENCE_ID.getKey ());
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

