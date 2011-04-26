/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.r2.common.infc;


/**
 * Detailed Information about a Type
 *
 * Types are used in Kuali Student to further refine the entity in question.
 * For example: An organization type may be a "Department" or a "College" or
 * a "Student Group" or a "Thesis Committee".
 * 
 * Most of validation of the the fields on the entity should be based on a combination of type and state.
 *
 * @author Kuali Student Team (Kamal)
 *
 */
public interface Type extends HasKey, HasAttributes, HasEffectiveDates {

    /**
     * Name: Name
     *
     * Friendly name of the Type
     */
    public String getName();

    /**
     * Name: Description
     *
     * Narrative description of the type
     */
    public String getDescr();        
        
    /**
     * Name: Reference Object URI
     *
     * The Object URI to which the Type belongs.
     * E.g http://student.kuali.org/luService/wsdl/CluInfo will be the objectTypeURI for type 'kuali.lu.type.CreditCourse'
     * The refObjectURI has three parts:<ol>
     * <li>http://student.kuali.org/wsdl -- which is fixed
     * <li>luService -- which should match the namespace of the service in which the object is defined
     * <li>CluInfo -- which should match the java class's simple name
     * </ol>
     */
    public String getRefObjectURI();
    
}
