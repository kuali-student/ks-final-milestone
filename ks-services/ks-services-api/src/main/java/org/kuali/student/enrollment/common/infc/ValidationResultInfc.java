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



public interface ValidationResultInfc
{
	
	/**
	* Set ????
	*
	* Type: Integer
	*
	* ???
	*/
	public void setLevel(Integer level);
	
	/**
	* Get ????
	*
	* Type: Integer
	*
	* ???
	*/
	public Integer getLevel();
	
	
	
	/**
	* Set ????
	*
	* Type: String
	*
	* ???
	*/
	public void setMessage(String message);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* ???
	*/
	public String getMessage();
	
	
	
	/**
	* Set ????
	*
	* Type: String
	*
	* ???
	*/
	public void setElement(String element);
	
	/**
	* Get ????
	*
	* Type: String
	*
	* ???
	*/
	public String getElement();
	
	
	
	/**
	* Set ????
	*
	* Type: Integer
	*
	* Returns the ValidationResult's error level
	*/
	public void setErrorLevel(Integer errorLevel);
	
	/**
	* Get ????
	*
	* Type: Integer
	*
	* Returns the ValidationResult's error level
	*/
	public Integer getErrorLevel();
	
	
	
	/**
	* Set ????
	*
	* Type: boolean
	*
	* Convenience method. Returns true if getErrorLevel() == ErrorLevel.OK
	*/
	public void setOk(Boolean ok);
	
	/**
	* Get ????
	*
	* Type: boolean
	*
	* Convenience method. Returns true if getErrorLevel() == ErrorLevel.OK
	*/
	public Boolean isOk();
	
	
	
	/**
	* Set ????
	*
	* Type: boolean
	*
	* Convenience method. Returns true if getErrorLevel() == ErrorLevel.WARN
	*/
	public void setWarn(Boolean warn);
	
	/**
	* Get ????
	*
	* Type: boolean
	*
	* Convenience method. Returns true if getErrorLevel() == ErrorLevel.WARN
	*/
	public Boolean isWarn();
	
	
	
	/**
	* Set ????
	*
	* Type: boolean
	*
	* Convenience method. Returns true if getErrorLevel() == ErrorLevel.ERROR
	*/
	public void setError(Boolean error);
	
	/**
	* Get ????
	*
	* Type: boolean
	*
	* Convenience method. Returns true if getErrorLevel() == ErrorLevel.ERROR
	*/
	public Boolean isError();
	
	
	
}

