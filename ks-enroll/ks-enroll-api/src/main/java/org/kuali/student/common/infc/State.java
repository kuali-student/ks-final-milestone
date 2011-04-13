/*
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.infc;

/**
 * Describes the state of an object
 *
 * States are used in Kuali Student to decribe where the entity is within a process
 *
 * For example: A new course may be created in a "draft" state, then move to a "submitted"
 * state for review until it is finally "approved".
 *
 * Most of validation of the the fields on the entity should be based on state in combination with the entity's type.
 *
 * @author nwright
 */
public interface State extends HasKey, HasAttributes, HasEffectiveDates {
    
   
    /**
     * Name: Name
     *
     * Friendly name of the State
     */
    public String getName();

    /**
     * Name: Description
     * 
     * Narrative description of the State
     */
    public String getDescr();


}
