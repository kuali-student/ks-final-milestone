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
import org.kuali.student.core.enumerationmanagement.dto.FieldDescriptorInfo;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;


public class FieldDescriptorInfoData
	extends Data
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
	
	public FieldDescriptorInfoData ()
	{
		// TODO: ask Wil if we want to really use the class name as the key
		super (FieldDescriptorInfo.class.getName ());
	}
	
	public void setName (String value)
	{
		super.set (Properties.NAME.getKey (), value);
	}
	
	
	public String getName ()
	{
		return super.get (Properties.NAME.getKey ());
	}
	
	
	public void setDesc (String value)
	{
		super.set (Properties.DESC.getKey (), value);
	}
	
	
	public String getDesc ()
	{
		return super.get (Properties.DESC.getKey ());
	}
	
	
	public void setDataType (String value)
	{
		super.set (Properties.DATA_TYPE.getKey (), value);
	}
	
	
	public String getDataType ()
	{
		return super.get (Properties.DATA_TYPE.getKey ());
	}
	
	
	public void setMinLength (Integer value)
	{
		super.set (Properties.MIN_LENGTH.getKey (), value);
	}
	
	
	public Integer getMinLength ()
	{
		return super.get (Properties.MIN_LENGTH.getKey ());
	}
	
	
	public void setMaxLength (String value)
	{
		super.set (Properties.MAX_LENGTH.getKey (), value);
	}
	
	
	public String getMaxLength ()
	{
		return super.get (Properties.MAX_LENGTH.getKey ());
	}
	
	
	public void setValidChars (String value)
	{
		super.set (Properties.VALID_CHARS.getKey (), value);
	}
	
	
	public String getValidChars ()
	{
		return super.get (Properties.VALID_CHARS.getKey ());
	}
	
	
	public void setInvalidChars (String value)
	{
		super.set (Properties.INVALID_CHARS.getKey (), value);
	}
	
	
	public String getInvalidChars ()
	{
		return super.get (Properties.INVALID_CHARS.getKey ());
	}
	
	
	public void setMinValue (String value)
	{
		super.set (Properties.MIN_VALUE.getKey (), value);
	}
	
	
	public String getMinValue ()
	{
		return super.get (Properties.MIN_VALUE.getKey ());
	}
	
	
	public void setMaxValue (String value)
	{
		super.set (Properties.MAX_VALUE.getKey (), value);
	}
	
	
	public String getMaxValue ()
	{
		return super.get (Properties.MAX_VALUE.getKey ());
	}
	
	
	public void setEnumFieldView (EnumFieldViewInfoData value)
	{
		super.set (Properties.ENUM_FIELD_VIEW.getKey (), value);
	}
	
	
	public EnumFieldViewInfoData getEnumFieldView ()
	{
		return super.get (Properties.ENUM_FIELD_VIEW.getKey ());
	}
	
	
	public void setMinOccurs (Integer value)
	{
		super.set (Properties.MIN_OCCURS.getKey (), value);
	}
	
	
	public Integer getMinOccurs ()
	{
		return super.get (Properties.MIN_OCCURS.getKey ());
	}
	
	
	public void setMaxOccurs (String value)
	{
		super.set (Properties.MAX_OCCURS.getKey (), value);
	}
	
	
	public String getMaxOccurs ()
	{
		return super.get (Properties.MAX_OCCURS.getKey ());
	}
	
	
	public void setReadOnly (Boolean value)
	{
		super.set (Properties.READ_ONLY.getKey (), value);
	}
	
	
	public Boolean getReadOnly ()
	{
		return super.get (Properties.READ_ONLY.getKey ());
	}
	
}

