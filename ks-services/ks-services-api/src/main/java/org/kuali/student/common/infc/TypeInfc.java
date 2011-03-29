/*
 * Copyright 2011 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.common.infc;


public interface TypeInfc extends HasKey, HasAttributes, HasEffectiveDates {

    /**
     * Get ???? Type: String ???
     */
    public String getName();

    /**
     * Get ???? Type: String ???
     */
    public String getDescr();        
        
    /**
     * This method returns the Object URI to which the Type belongs. E.g http://stduent.kuali.org/LuService/CluInfo will be the objectTypeURI for type 'kuali.lu.type.CreditCourse'
     * 
     * Type: String
     * 
     * @return
     */
    public String getRefObjectURI();
    
}
