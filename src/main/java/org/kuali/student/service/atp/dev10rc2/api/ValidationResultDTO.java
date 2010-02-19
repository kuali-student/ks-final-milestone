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
	package org.kuali.student.service.atp.dev10rc2.api;
	
	
	
	
	public class ValidationResultDTO
	 implements ValidationResultInfo
	{
		
		private String element;
		
		@Override
		public void setElement(String element)
		{
			this.element = element;
		}
		
		@Override
		public String getElement()
		{
			return this.element;
		}
						
		private String errorLevel;
		
		@Override
		public void setErrorLevel(String errorLevel)
		{
			this.errorLevel = errorLevel;
		}
		
		@Override
		public String getErrorLevel()
		{
			return this.errorLevel;
		}
						
		private String message;
		
		@Override
		public void setMessage(String message)
		{
			this.message = message;
		}
		
		@Override
		public String getMessage()
		{
			return this.message;
		}
						
		private String task;
		
		@Override
		public void setTask(String task)
		{
			this.task = task;
		}
		
		@Override
		public String getTask()
		{
			return this.task;
		}
						
	}

