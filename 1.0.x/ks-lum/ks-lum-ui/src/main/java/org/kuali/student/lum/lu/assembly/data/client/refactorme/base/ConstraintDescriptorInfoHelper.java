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


public class ConstraintDescriptorInfoHelper
{
	private static final long serialVersionUID = 1;
	
	public enum Properties implements PropertyEnum
	{
		MIN_LENGTH ("minLength"),
		MAX_LENGTH ("maxLength"),
		VALID_CHARS ("validChars"),
		INVALID_CHARS ("invalidChars"),
		MIN_VALUE ("minValue"),
		MAX_VALUE ("maxValue"),
		MIN_OCCURS ("minOccurs"),
		MAX_OCCURS ("maxOccurs"),
		READ_ONLY ("readOnly"),
		SERVER_SIDE ("serverSide"),
		LOOKUP_KEY ("lookupKey"),
		SPECIAL_VALIDATOR_KEY ("specialValidatorKey"),
		MESSAGE_KEY ("messageKey"),
		DICTIONARY_KEY ("dictionaryKey"),
		KEY ("key");
		
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
	
	private ConstraintDescriptorInfoHelper (Data data)
	{
		this.data = data;
	}
	
	public static ConstraintDescriptorInfoHelper wrap (Data data)
	{
		if (data == null)
		{
			 return null;
		}
		return new ConstraintDescriptorInfoHelper (data);
	}
	
	public Data getData ()
	{
		return data;
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
	
	
	public void setServerSide (Boolean value)
	{
		data.set (Properties.SERVER_SIDE.getKey (), value);
	}
	
	
	public Boolean isServerSide ()
	{
		return (Boolean) data.get (Properties.SERVER_SIDE.getKey ());
	}
	
	
	public void setLookupKey (String value)
	{
		data.set (Properties.LOOKUP_KEY.getKey (), value);
	}
	
	
	public String getLookupKey ()
	{
		return (String) data.get (Properties.LOOKUP_KEY.getKey ());
	}
	
	
	public void setSpecialValidatorKey (String value)
	{
		data.set (Properties.SPECIAL_VALIDATOR_KEY.getKey (), value);
	}
	
	
	public String getSpecialValidatorKey ()
	{
		return (String) data.get (Properties.SPECIAL_VALIDATOR_KEY.getKey ());
	}
	
	
	public void setMessageKey (String value)
	{
		data.set (Properties.MESSAGE_KEY.getKey (), value);
	}
	
	
	public String getMessageKey ()
	{
		return (String) data.get (Properties.MESSAGE_KEY.getKey ());
	}
	
	
	public void setDictionaryKey (String value)
	{
		data.set (Properties.DICTIONARY_KEY.getKey (), value);
	}
	
	
	public String getDictionaryKey ()
	{
		return (String) data.get (Properties.DICTIONARY_KEY.getKey ());
	}
	
	
	public void setKey (String value)
	{
		data.set (Properties.KEY.getKey (), value);
	}
	
	
	public String getKey ()
	{
		return (String) data.get (Properties.KEY.getKey ());
	}
	
}

