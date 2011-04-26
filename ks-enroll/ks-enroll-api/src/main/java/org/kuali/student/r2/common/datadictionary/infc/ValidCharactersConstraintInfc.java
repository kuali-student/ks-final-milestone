/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.datadictionary.infc;

/**
 * Constraint that applied a regular expression to check if the characters are valid
 *
 * @author nwright
 */
public interface ValidCharactersConstraintInfc extends BaseConstraintInfc {

	 /**
      * Name: Value
      *
     * The Java based regex for valid characters
      * There are two forms:
      * regex:xxxxx
      * and just
      * xxxx
      *
      * If the 2nd form then it is interpreted as a simple list of valid characters.
      *
      * Note: it is actualy converted to a regular expression by wrapping it in [].
     */
    public String getValue();



	/**
     * Name: Java Script Value
     *
	 * Javascript version of the regex defined in value.  This does not have to be set if this constraint's
	 * key maps to one of the default valid character methods contained in jQuery - (insert that list here).
	 * This must be set if there is NO default method that matches the label key and applyClientSide is true.
	 *
	 * This is completely ignored if applyClientSide is set to false.
	 */
	public String getJsValue();

   
}
