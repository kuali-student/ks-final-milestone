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
	package org.kuali.student.service.dictionary.dev.api;
	
	
	import java.io.Serializable;
	
	
	public class FieldDescriptorBean
	 implements FieldDescriptorInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**
		* Set Field Name
		*
		* Type: string
		*
		* Friendly name for the field.
		*/
		@Override
		public void setName(String name)
		{
			this.name = name;
		}
		
		/**
		* Get Field Name
		*
		* Type: string
		*
		* Friendly name for the field.
		*/
		@Override
		public String getName()
		{
			return this.name;
		}
						
		private String desc;
		
		/**
		* Set Field Description
		*
		* Type: string
		*
		* Narrative description for the field.
		*/
		@Override
		public void setDesc(String desc)
		{
			this.desc = desc;
		}
		
		/**
		* Get Field Description
		*
		* Type: string
		*
		* Narrative description for the field.
		*/
		@Override
		public String getDesc()
		{
			return this.desc;
		}
						
		private String dataType;
		
		/**
		* Set Data Type
		*
		* Type: string
		*
		* The Data Type value indicates the type for a given field. This is currently 
		* assumed to be a primitive type (string, int, date, etc.).
		*/
		@Override
		public void setDataType(String dataType)
		{
			this.dataType = dataType;
		}
		
		/**
		* Get Data Type
		*
		* Type: string
		*
		* The Data Type value indicates the type for a given field. This is currently 
		* assumed to be a primitive type (string, int, date, etc.).
		*/
		@Override
		public String getDataType()
		{
			return this.dataType;
		}
						
		private Integer minLength;
		
		/**
		* Set Minimum Length
		*
		* Type: integer
		*
		* Primarily used for string data types, the Minimum Length value establishes a 
		* lower bound on the length of the string. A 0 length indicates an empty string is 
		* allowed. Must be less than or equal to the maxLength value, if specified.
		*/
		@Override
		public void setMinLength(Integer minLength)
		{
			this.minLength = minLength;
		}
		
		/**
		* Get Minimum Length
		*
		* Type: integer
		*
		* Primarily used for string data types, the Minimum Length value establishes a 
		* lower bound on the length of the string. A 0 length indicates an empty string is 
		* allowed. Must be less than or equal to the maxLength value, if specified.
		*/
		@Override
		public Integer getMinLength()
		{
			return this.minLength;
		}
						
		private String maxLength;
		
		/**
		* Set Maximum Length
		*
		* Type: string
		*
		* Primarily used for string data types, the Maximum Length value establishes an 
		* upper bound on the length of the string. The values of this field are restricted 
		* to integer values and the string "unbounded". Must be greater than or equal to 
		* the minLength value, if specified. "Unbounded" is automatically considered to 
		* meet this condition.
		*/
		@Override
		public void setMaxLength(String maxLength)
		{
			this.maxLength = maxLength;
		}
		
		/**
		* Get Maximum Length
		*
		* Type: string
		*
		* Primarily used for string data types, the Maximum Length value establishes an 
		* upper bound on the length of the string. The values of this field are restricted 
		* to integer values and the string "unbounded". Must be greater than or equal to 
		* the minLength value, if specified. "Unbounded" is automatically considered to 
		* meet this condition.
		*/
		@Override
		public String getMaxLength()
		{
			return this.maxLength;
		}
						
		private String validChars;
		
		/**
		* Set Valid Characters
		*
		* Type: string
		*
		* Primarily used for string data types, Valid Characters acts as a white list - 
		* only the characters which are specified should be used in the value. In general, 
		* this field should not be specified if invalidChars is also specified.
		*/
		@Override
		public void setValidChars(String validChars)
		{
			this.validChars = validChars;
		}
		
		/**
		* Get Valid Characters
		*
		* Type: string
		*
		* Primarily used for string data types, Valid Characters acts as a white list - 
		* only the characters which are specified should be used in the value. In general, 
		* this field should not be specified if invalidChars is also specified.
		*/
		@Override
		public String getValidChars()
		{
			return this.validChars;
		}
						
		private String invalidChars;
		
		/**
		* Set Invalid Characters
		*
		* Type: string
		*
		* Primarily used for string data types, Invalid Characters acts as a black list - 
		* the specified characters should not be used in the value. In general, this field 
		* should not be specified if invalidChars is also specified.
		*/
		@Override
		public void setInvalidChars(String invalidChars)
		{
			this.invalidChars = invalidChars;
		}
		
		/**
		* Get Invalid Characters
		*
		* Type: string
		*
		* Primarily used for string data types, Invalid Characters acts as a black list - 
		* the specified characters should not be used in the value. In general, this field 
		* should not be specified if invalidChars is also specified.
		*/
		@Override
		public String getInvalidChars()
		{
			return this.invalidChars;
		}
						
		private String minValue;
		
		/**
		* Set Minimum Value
		*
		* Type: string
		*
		* Primarily used for numeric and time-related data types, this value establishes a 
		* lower bound on the value of the field. Must be less than or equal to the 
		* maxValue value, if specified.
		*/
		@Override
		public void setMinValue(String minValue)
		{
			this.minValue = minValue;
		}
		
		/**
		* Get Minimum Value
		*
		* Type: string
		*
		* Primarily used for numeric and time-related data types, this value establishes a 
		* lower bound on the value of the field. Must be less than or equal to the 
		* maxValue value, if specified.
		*/
		@Override
		public String getMinValue()
		{
			return this.minValue;
		}
						
		private String maxValue;
		
		/**
		* Set Maximum Value
		*
		* Type: string
		*
		* Primarily used for numeric and time-related data types, this value establishes 
		* an upper bound on the value of the field. Must be greater than or equal to the 
		* minValue value, if specified.
		*/
		@Override
		public void setMaxValue(String maxValue)
		{
			this.maxValue = maxValue;
		}
		
		/**
		* Get Maximum Value
		*
		* Type: string
		*
		* Primarily used for numeric and time-related data types, this value establishes 
		* an upper bound on the value of the field. Must be greater than or equal to the 
		* minValue value, if specified.
		*/
		@Override
		public String getMaxValue()
		{
			return this.maxValue;
		}
						
		private EnumFieldViewInfo enumFieldView;
		
		/**
		* Set Enumeration
		*
		* Type: enumFieldViewInfo
		*
		* Description of an enumeration as it relates to a particular field.
		*/
		@Override
		public void setEnumFieldView(EnumFieldViewInfo enumFieldView)
		{
			this.enumFieldView = enumFieldView;
		}
		
		/**
		* Get Enumeration
		*
		* Type: enumFieldViewInfo
		*
		* Description of an enumeration as it relates to a particular field.
		*/
		@Override
		public EnumFieldViewInfo getEnumFieldView()
		{
			return this.enumFieldView;
		}
						
		private Integer minOccurs;
		
		/**
		* Set Minimum Occurrences
		*
		* Type: integer
		*
		* The Minimum Occurrences value establishes a lower bound on the number of times a 
		* field can appear in the given context. A minOccurs of 0 indicates that the field 
		* is not required and can be left null. Must be less than or equal to the 
		* maxOccurs value, if specified.
		*/
		@Override
		public void setMinOccurs(Integer minOccurs)
		{
			this.minOccurs = minOccurs;
		}
		
		/**
		* Get Minimum Occurrences
		*
		* Type: integer
		*
		* The Minimum Occurrences value establishes a lower bound on the number of times a 
		* field can appear in the given context. A minOccurs of 0 indicates that the field 
		* is not required and can be left null. Must be less than or equal to the 
		* maxOccurs value, if specified.
		*/
		@Override
		public Integer getMinOccurs()
		{
			return this.minOccurs;
		}
						
		private String maxOccurs;
		
		/**
		* Set Maximum Occurrences
		*
		* Type: string
		*
		* The Maximum Occurrences value establishes an upper bound on the number of times 
		* a field can appear in the given context. Allowed values are integers or the 
		* string "unbounded". Must be greater than or equal to the minOccurs value, if 
		* specified. "Unbounded" is evaluated to be greater than any integer value.
		*/
		@Override
		public void setMaxOccurs(String maxOccurs)
		{
			this.maxOccurs = maxOccurs;
		}
		
		/**
		* Get Maximum Occurrences
		*
		* Type: string
		*
		* The Maximum Occurrences value establishes an upper bound on the number of times 
		* a field can appear in the given context. Allowed values are integers or the 
		* string "unbounded". Must be greater than or equal to the minOccurs value, if 
		* specified. "Unbounded" is evaluated to be greater than any integer value.
		*/
		@Override
		public String getMaxOccurs()
		{
			return this.maxOccurs;
		}
						
		private Boolean readOnly;
		
		/**
		* Set Read Only Indicator
		*
		* Type: boolean
		*
		* Indicates if the field is read only. This field should not be used when 
		* describing parameters, such as in named searches, etc.
		*/
		@Override
		public void setReadOnly(Boolean readOnly)
		{
			this.readOnly = readOnly;
		}
		
		/**
		* Get Read Only Indicator
		*
		* Type: boolean
		*
		* Indicates if the field is read only. This field should not be used when 
		* describing parameters, such as in named searches, etc.
		*/
		@Override
		public Boolean isReadOnly()
		{
			return this.readOnly;
		}
						
	}

