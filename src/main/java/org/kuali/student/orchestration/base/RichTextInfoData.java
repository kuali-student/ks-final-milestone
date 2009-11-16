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
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class RichTextInfoData
	extends Data
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		PLAIN ("plain"),
		FORMATTED ("formatted");
		
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
	
	public RichTextInfoData ()
	{
		// TODO: ask Wil if we want to really use the class name as the key
		super (RichTextInfo.class.getName ());
	}
	
	public void setPlain (String value)
	{
		super.set (Properties.PLAIN.getKey (), value);
	}
	
	
	public String getPlain ()
	{
		return super.get (Properties.PLAIN.getKey ());
	}
	
	
	public void setFormatted (String value)
	{
		super.set (Properties.FORMATTED.getKey (), value);
	}
	
	
	public String getFormatted ()
	{
		return super.get (Properties.FORMATTED.getKey ());
	}
	
}

