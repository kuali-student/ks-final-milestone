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


public class LuStatementInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		NAME ("name"),
		DESC ("desc"),
		OPERATOR ("operator"),
		LU_STATEMENT_IDS ("luStatementIds"),
		REQ_COMPONENT_IDS ("reqComponentIds"),
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
	
	private LuStatementInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static LuStatementInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new LuStatementInfoHelper (data);
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
	
	
	public void setDesc (RichTextInfoHelper value)
	{
		data.set (Properties.DESC.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public RichTextInfoHelper getDesc ()
	{
		return RichTextInfoHelper.wrap ((Data) data.get (Properties.DESC.getKey ()));
	}
	
	
	public void setOperator (String value)
	{
		data.set (Properties.OPERATOR.getKey (), value);
	}
	
	
	public String getOperator ()
	{
		return (String) data.get (Properties.OPERATOR.getKey ());
	}
	
	
	public void setLuStatementIds (Data value)
	{
		data.set (Properties.LU_STATEMENT_IDS.getKey (), value);
	}
	
	
	public Data getLuStatementIds ()
	{
		return (Data) data.get (Properties.LU_STATEMENT_IDS.getKey ());
	}
	
	
	public void setReqComponentIds (Data value)
	{
		data.set (Properties.REQ_COMPONENT_IDS.getKey (), value);
	}
	
	
	public Data getReqComponentIds ()
	{
		return (Data) data.get (Properties.REQ_COMPONENT_IDS.getKey ());
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

