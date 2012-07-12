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

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.type.dto.TypeInfo;
import org.kuali.student.r2.core.type.service.TypeService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ViewHelperUtil {

    public static List<Person> getInstructorByPersonId(String personId){
        Map<String, String> searchCriteria = new HashMap<String, String>();
        searchCriteria.put(KIMPropertyConstants.Person.ENTITY_ID, personId);
        List<Person> lstPerson = getPersonService().findPeople(searchCriteria);
        return lstPerson;
    }

    public static PersonService getPersonService() {
        return KimApiServiceLocator.getPersonService();
    }

    public static String buildDerivedFormatName(TypeService typeService, ContextInfo contextInfo, FormatInfo formatInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StringBuilder formatNameBuilder = new StringBuilder();

        // Create a derived name based on the activities, until https://jira.kuali.org/browse/KSENROLL-1518 is finished
        List<ActivityInfo> activities = formatInfo.getActivities();
        for (ActivityInfo activity : activities) {
            if(formatNameBuilder.length() != 0) {
                formatNameBuilder.append(" / ");
            }
            TypeInfo type = typeService.getType(activity.getActivityType(), contextInfo);
            formatNameBuilder.append(type.getName());
        }

        if(formatInfo.getActivities().size() == 1) {
            formatNameBuilder.append(" Only");
        }

        return formatNameBuilder.toString();
    }

    public static List<KeyValue> collectActivityTypeKeyValues(CourseInfo course, TypeService typeService, ContextInfo contextInfo) {
        List<KeyValue> results = new ArrayList<KeyValue>();

        Set<String> activityTypes = new HashSet<String>();
        for(FormatInfo format : course.getFormats()) {
            for (ActivityInfo activity : format.getActivities()) {
                // if we haven't added a value for this activity type yet
                if(activityTypes.add(activity.getActivityType())) {
                    try {
                        TypeInfo type = typeService.getType(activity.getActivityType(), contextInfo);
                        results.add(new ConcreteKeyValue(type.getKey(), type.getName()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        return results;
    }

}
