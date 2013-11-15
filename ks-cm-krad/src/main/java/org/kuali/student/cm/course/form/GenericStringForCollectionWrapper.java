/**
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.cm.course.form;


/**
 * Class to generically use a KRAD collection component with a plain string value.
 *
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class GenericStringForCollectionWrapper {

	private static final long serialVersionUID = -1L;
	
	private String value;
    
    public GenericStringForCollectionWrapper(final String value) {
        setValue(value);
    }
	

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public final String getValue() {
        return this.value;
    }

    /**
     * Sets the value of value
     *
     * @param argValue Value to assign to this.value
     */
    public void setValue(final String argValue) {
        this.value = argValue;
    }
}
