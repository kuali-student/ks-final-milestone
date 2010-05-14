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


import java.util.Date;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class MetaInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		VERSION_IND ("versionInd"),
		CREATE_TIME ("createTime"),
		CREATE_ID ("createId"),
		UPDATE_TIME ("updateTime"),
		UPDATE_ID ("updateId");
		
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
	
	private MetaInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static MetaInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new MetaInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
	}
	
	
	public void setVersionInd (String value)
	{
		data.set (Properties.VERSION_IND.getKey (), value);
	}
	
	
	public String getVersionInd ()
	{
		return (String) data.get (Properties.VERSION_IND.getKey ());
	}
	
	
	public void setCreateTime (Date value)
	{
		data.set (Properties.CREATE_TIME.getKey (), value);
	}
	
	
	public Date getCreateTime ()
	{
		return (Date) data.get (Properties.CREATE_TIME.getKey ());
	}
	
	
	public void setCreateId (String value)
	{
		data.set (Properties.CREATE_ID.getKey (), value);
	}
	
	
	public String getCreateId ()
	{
		return (String) data.get (Properties.CREATE_ID.getKey ());
	}
	
	
	public void setUpdateTime (Date value)
	{
		data.set (Properties.UPDATE_TIME.getKey (), value);
	}
	
	
	public Date getUpdateTime ()
	{
		return (Date) data.get (Properties.UPDATE_TIME.getKey ());
	}
	
	
	public void setUpdateId (String value)
	{
		data.set (Properties.UPDATE_ID.getKey (), value);
	}
	
	
	public String getUpdateId ()
	{
		return (String) data.get (Properties.UPDATE_ID.getKey ());
	}
	
}

