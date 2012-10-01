/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.r2.common.infc;


/**
 * Detailed Information about a State Process
 *
 * State Process are used in Kuali Student to define various process keys that might be associated with objectTypes
 * For example: A course might have a lifecycle process or workflow process with different states in each process
 * 
 *
 * @author Kuali Student Team (Kamal)
 *
 */
public interface StateProcess extends HasKey, HasAttributes, HasEffectiveDates {

    /**
     * Friendly name of the State Process
     * @name Name
     * @readOnly
     * @required
     */
    public String getName();
    

    /**
     * Narrative description of the State Process
     * @name Description
     * @readOnly
     */
    public RichText getDescr();        
            
}
