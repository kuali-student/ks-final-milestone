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


import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.helper.PropertyEnum;
import org.kuali.student.lum.common.client.lo.MetaInfoHelper;
import org.kuali.student.lum.common.client.lo.RichTextInfoHelper;


public class OrgPositionRestrictionInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		ORG_ID ("orgId"),
		ORG_PERSON_RELATION_TYPE_KEY ("orgPersonRelationTypeKey"),
		DESC ("desc"),
		TITLE ("title"),
		STD_DURATION ("stdDuration"),
		MIN_NUM_RELATIONS ("minNumRelations"),
		MAX_NUM_RELATIONS ("maxNumRelations"),
		ATTRIBUTES ("attributes"),
		META_INFO ("metaInfo"),
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
	
	private OrgPositionRestrictionInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static OrgPositionRestrictionInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new OrgPositionRestrictionInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setOrgId (String value)
	{
		data.set (Properties.ORG_ID.getKey (), value);
	}
	
	
	public String getOrgId ()
	{
		return (String) data.get (Properties.ORG_ID.getKey ());
	}
	
	
	public void setOrgPersonRelationTypeKey (String value)
	{
		data.set (Properties.ORG_PERSON_RELATION_TYPE_KEY.getKey (), value);
	}
	
	
	public String getOrgPersonRelationTypeKey ()
	{
		return (String) data.get (Properties.ORG_PERSON_RELATION_TYPE_KEY.getKey ());
	}
	
	
	public void setDesc (RichTextInfoHelper value)
	{
		data.set (Properties.DESC.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RichTextInfoHelper getDesc ()
	{
		return RichTextInfoHelper.wrap ((Data) data.get (Properties.DESC.getKey ()));
	}
	
	
	public void setTitle (String value)
	{
		data.set (Properties.TITLE.getKey (), value);
	}
	
	
	public String getTitle ()
	{
		return (String) data.get (Properties.TITLE.getKey ());
	}
	
	
	public void setStdDuration (TimeAmountInfoHelper value)
	{
		data.set (Properties.STD_DURATION.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public TimeAmountInfoHelper getStdDuration ()
	{
		return TimeAmountInfoHelper.wrap ((Data) data.get (Properties.STD_DURATION.getKey ()));
	}
	
	
	public void setMinNumRelations (Integer value)
	{
		data.set (Properties.MIN_NUM_RELATIONS.getKey (), value);
	}
	
	
	public Integer getMinNumRelations ()
	{
		return (Integer) data.get (Properties.MIN_NUM_RELATIONS.getKey ());
	}
	
	
	public void setMaxNumRelations (String value)
	{
		data.set (Properties.MAX_NUM_RELATIONS.getKey (), value);
	}
	
	
	public String getMaxNumRelations ()
	{
		return (String) data.get (Properties.MAX_NUM_RELATIONS.getKey ());
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
	
	
	public void setId (String value)
	{
		data.set (Properties.ID.getKey (), value);
	}
	
	
	public String getId ()
	{
		return (String) data.get (Properties.ID.getKey ());
	}
	
}

