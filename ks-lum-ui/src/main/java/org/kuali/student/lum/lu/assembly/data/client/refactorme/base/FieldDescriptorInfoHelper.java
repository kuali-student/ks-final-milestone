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


public class FieldDescriptorInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		NAME ("name"),
		DESC ("desc"),
		DATA_TYPE ("dataType"),
		MIN_LENGTH ("minLength"),
		MAX_LENGTH ("maxLength"),
		VALID_CHARS ("validChars"),
		INVALID_CHARS ("invalidChars"),
		MIN_VALUE ("minValue"),
		MAX_VALUE ("maxValue"),
		ENUM_FIELD_VIEW ("enumFieldView"),
		MIN_OCCURS ("minOccurs"),
		MAX_OCCURS ("maxOccurs"),
		READ_ONLY ("readOnly");
		
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
	
	private FieldDescriptorInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static FieldDescriptorInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new FieldDescriptorInfoHelper (data);
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
	
	
	public void setDataType (String value)
	{
		data.set (Properties.DATA_TYPE.getKey (), value);
	}
	
	
	public String getDataType ()
	{
		return (String) data.get (Properties.DATA_TYPE.getKey ());
	}
	
	
	public void setMinLength (Integer value)
	{
		data.set (Properties.MIN_LENGTH.getKey (), value);
	}
	
	
	public Integer getMinLength ()
	{
		return (Integer) data.get (Properties.MIN_LENGTH.getKey ());
	}
	
	
	public void setMaxLength (String value)
	{
		data.set (Properties.MAX_LENGTH.getKey (), value);
	}
	
	
	public String getMaxLength ()
	{
		return (String) data.get (Properties.MAX_LENGTH.getKey ());
	}
	
	
	public void setValidChars (String value)
	{
		data.set (Properties.VALID_CHARS.getKey (), value);
	}
	
	
	public String getValidChars ()
	{
		return (String) data.get (Properties.VALID_CHARS.getKey ());
	}
	
	
	public void setInvalidChars (String value)
	{
		data.set (Properties.INVALID_CHARS.getKey (), value);
	}
	
	
	public String getInvalidChars ()
	{
		return (String) data.get (Properties.INVALID_CHARS.getKey ());
	}
	
	
	public void setMinValue (String value)
	{
		data.set (Properties.MIN_VALUE.getKey (), value);
	}
	
	
	public String getMinValue ()
	{
		return (String) data.get (Properties.MIN_VALUE.getKey ());
	}
	
	
	public void setMaxValue (String value)
	{
		data.set (Properties.MAX_VALUE.getKey (), value);
	}
	
	
	public String getMaxValue ()
	{
		return (String) data.get (Properties.MAX_VALUE.getKey ());
	}
	
	
	public void setEnumFieldView (EnumFieldViewInfoHelper value)
	{
		data.set (Properties.ENUM_FIELD_VIEW.getKey (), (value == null) ? null : value.getData ());
	}
	
	
	public EnumFieldViewInfoHelper getEnumFieldView ()
	{
		return EnumFieldViewInfoHelper.wrap ((Data) data.get (Properties.ENUM_FIELD_VIEW.getKey ()));
	}
	
	
	public void setMinOccurs (Integer value)
	{
		data.set (Properties.MIN_OCCURS.getKey (), value);
	}
	
	
	public Integer getMinOccurs ()
	{
		return (Integer) data.get (Properties.MIN_OCCURS.getKey ());
	}
	
	
	public void setMaxOccurs (String value)
	{
		data.set (Properties.MAX_OCCURS.getKey (), value);
	}
	
	
	public String getMaxOccurs ()
	{
		return (String) data.get (Properties.MAX_OCCURS.getKey ());
	}
	
	
	public void setReadOnly (Boolean value)
	{
		data.set (Properties.READ_ONLY.getKey (), value);
	}
	
	
	public Boolean isReadOnly ()
	{
		return (Boolean) data.get (Properties.READ_ONLY.getKey ());
	}
	
}

