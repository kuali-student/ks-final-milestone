/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 */
package org.kuali.student.enrollment.class2.courseoffering.util;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ViewHelperUtil {
    public static void getInstructorNames(List<OfferingInstructorInfo> instructors){
        if(instructors != null && !instructors.isEmpty()){
            for(OfferingInstructorInfo instructor : instructors){
                Map<String, String> searchCriteria = new HashMap<String, String>();
                searchCriteria.put(KIMPropertyConstants.Person.ENTITY_ID, instructor.getPersonId());
                List<Person> lstPerson = getPersonService().findPeople(searchCriteria);
                if(lstPerson != null && !lstPerson.isEmpty()){
                    instructor.setPersonName(lstPerson.get(0).getName());
                }
            }
        }
    }

    public static PersonService getPersonService() {
        return KimApiServiceLocator.getPersonService();
    }
}
