	/*
	 * Copyright 2011 The Kuali Foundation
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
	package org.kuali.student.enrollment.common.infc;
	
	
	import java.io.Serializable;
	
	
	public class ValidationResultBean
	 implements ValidationResultInfc	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private Integer level;
		
		/**
		* Set ????
		*
		* Type: Integer
		*
		* ???
		*/
		@Override
		public void setLevel(Integer level)
		{
			this.level = level;
		}
		
		/**
		* Get ????
		*
		* Type: Integer
		*
		* ???
		*/
		@Override
		public Integer getLevel()
		{
			return this.level;
		}
						
		private String message;
		
		/**
		* Set ????
		*
		* Type: String
		*
		* ???
		*/
		@Override
		public void setMessage(String message)
		{
			this.message = message;
		}
		
		/**
		* Get ????
		*
		* Type: String
		*
		* ???
		*/
		@Override
		public String getMessage()
		{
			return this.message;
		}
						
		private String element;
		
		/**
		* Set ????
		*
		* Type: String
		*
		* ???
		*/
		@Override
		public void setElement(String element)
		{
			this.element = element;
		}
		
		/**
		* Get ????
		*
		* Type: String
		*
		* ???
		*/
		@Override
		public String getElement()
		{
			return this.element;
		}
						
		private Integer errorLevel;
		
		/**
		* Set ????
		*
		* Type: Integer
		*
		* Returns the ValidationResult's error level
		*/
		@Override
		public void setErrorLevel(Integer errorLevel)
		{
			this.errorLevel = errorLevel;
		}
		
		/**
		* Get ????
		*
		* Type: Integer
		*
		* Returns the ValidationResult's error level
		*/
		@Override
		public Integer getErrorLevel()
		{
			return this.errorLevel;
		}
						
		private Boolean ok;
		
		/**
		* Set ????
		*
		* Type: boolean
		*
		* Convenience method. Returns true if getErrorLevel() == ErrorLevel.OK
		*/
		@Override
		public void setOk(Boolean ok)
		{
			this.ok = ok;
		}
		
		/**
		* Get ????
		*
		* Type: boolean
		*
		* Convenience method. Returns true if getErrorLevel() == ErrorLevel.OK
		*/
		@Override
		public Boolean isOk()
		{
			return this.ok;
		}
						
		private Boolean warn;
		
		/**
		* Set ????
		*
		* Type: boolean
		*
		* Convenience method. Returns true if getErrorLevel() == ErrorLevel.WARN
		*/
		@Override
		public void setWarn(Boolean warn)
		{
			this.warn = warn;
		}
		
		/**
		* Get ????
		*
		* Type: boolean
		*
		* Convenience method. Returns true if getErrorLevel() == ErrorLevel.WARN
		*/
		@Override
		public Boolean isWarn()
		{
			return this.warn;
		}
						
		private Boolean error;
		
		/**
		* Set ????
		*
		* Type: boolean
		*
		* Convenience method. Returns true if getErrorLevel() == ErrorLevel.ERROR
		*/
		@Override
		public void setError(Boolean error)
		{
			this.error = error;
		}
		
		/**
		* Get ????
		*
		* Type: boolean
		*
		* Convenience method. Returns true if getErrorLevel() == ErrorLevel.ERROR
		*/
		@Override
		public Boolean isError()
		{
			return this.error;
		}
						
	}

