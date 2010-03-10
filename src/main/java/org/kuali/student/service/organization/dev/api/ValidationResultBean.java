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
	package org.kuali.student.service.organization.dev.api;
	
	
	import java.io.Serializable;
	
	
	public class ValidationResultBean
	 implements ValidationResultInfo	, Serializable
	{
		
		private static final long serialVersionUID = 1L;
		
		private String element;
		
		/**
		* Set Element
		*
		* Type: string
		*
		* Indication of which element the validation result pertains to. In the case of 
		* validation results which are stem from a cross type constraint, the element 
		* should be the containing object. Ex. if two citizenships (which are embedded in 
		* the person) fail a uniqueness constraint, the element indicated should be the 
		* person.
		*/
		@Override
		public void setElement(String element)
		{
			this.element = element;
		}
		
		/**
		* Get Element
		*
		* Type: string
		*
		* Indication of which element the validation result pertains to. In the case of 
		* validation results which are stem from a cross type constraint, the element 
		* should be the containing object. Ex. if two citizenships (which are embedded in 
		* the person) fail a uniqueness constraint, the element indicated should be the 
		* person.
		*/
		@Override
		public String getElement()
		{
			return this.element;
		}
						
		private String errorLevel;
		
		/**
		* Set Error Level
		*
		* Type: string
		*
		* Indication of the error level of the validation result. Allowed values are OK, 
		* WARN, and ERROR.
		*/
		@Override
		public void setErrorLevel(String errorLevel)
		{
			this.errorLevel = errorLevel;
		}
		
		/**
		* Get Error Level
		*
		* Type: string
		*
		* Indication of the error level of the validation result. Allowed values are OK, 
		* WARN, and ERROR.
		*/
		@Override
		public String getErrorLevel()
		{
			return this.errorLevel;
		}
						
		private String message;
		
		/**
		* Set Message
		*
		* Type: string
		*
		* Message generated for the validation. Describes failure conditions and success 
		* aspects. May have additional meta about the specifics of the constraint 
		* violated.
		*/
		@Override
		public void setMessage(String message)
		{
			this.message = message;
		}
		
		/**
		* Get Message
		*
		* Type: string
		*
		* Message generated for the validation. Describes failure conditions and success 
		* aspects. May have additional meta about the specifics of the constraint 
		* violated.
		*/
		@Override
		public String getMessage()
		{
			return this.message;
		}
						
		private String task;
		
		/**
		* Set Suggested Task
		*
		* Type: string
		*
		* Indication of a potential task stemming from the current validation result. In 
		* the case of a warning or error, this may be a corrective action
		*/
		@Override
		public void setTask(String task)
		{
			this.task = task;
		}
		
		/**
		* Get Suggested Task
		*
		* Type: string
		*
		* Indication of a potential task stemming from the current validation result. In 
		* the case of a warning or error, this may be a corrective action
		*/
		@Override
		public String getTask()
		{
			return this.task;
		}
						
	}

