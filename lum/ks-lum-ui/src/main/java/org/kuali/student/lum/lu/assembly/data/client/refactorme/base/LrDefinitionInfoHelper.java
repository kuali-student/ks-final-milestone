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


public class LrDefinitionInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		NAME ("name"),
		DESC ("desc"),
		LR_TYPE ("lrType"),
		LR_SCALE ("lrScale"),
		VALUE ("value"),
		OPERATOR ("operator"),
		ATTRIBUTES ("attributes"),
		META_INFO ("metaInfo"),
		LR_DEFINITION_ID ("lrDefinitionId");
		
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
	
	private LrDefinitionInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static LrDefinitionInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new LrDefinitionInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setName (String value)
	{
		data.set (Properties.NAME.getKey (), value);
	}
	
	
	public String getName ()
	{
		return (String) data.get (Properties.NAME.getKey ());
	}
	
	
	public void setDesc (String value)
	{
		data.set (Properties.DESC.getKey (), value);
	}
	
	
	public String getDesc ()
	{
		return (String) data.get (Properties.DESC.getKey ());
	}
	
	
	public void setLrType (String value)
	{
		data.set (Properties.LR_TYPE.getKey (), value);
	}
	
	
	public String getLrType ()
	{
		return (String) data.get (Properties.LR_TYPE.getKey ());
	}
	
	
	public void setLrScale (String value)
	{
		data.set (Properties.LR_SCALE.getKey (), value);
	}
	
	
	public String getLrScale ()
	{
		return (String) data.get (Properties.LR_SCALE.getKey ());
	}
	
	
	public void setValue (String value)
	{
		data.set (Properties.VALUE.getKey (), value);
	}
	
	
	public String getValue ()
	{
		return (String) data.get (Properties.VALUE.getKey ());
	}
	
	
	public void setOperator (String value)
	{
		data.set (Properties.OPERATOR.getKey (), value);
	}
	
	
	public String getOperator ()
	{
		return (String) data.get (Properties.OPERATOR.getKey ());
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
	
	
	public void setLrDefinitionId (String value)
	{
		data.set (Properties.LR_DEFINITION_ID.getKey (), value);
	}
	
	
	public String getLrDefinitionId ()
	{
		return (String) data.get (Properties.LR_DEFINITION_ID.getKey ());
	}
	
}

