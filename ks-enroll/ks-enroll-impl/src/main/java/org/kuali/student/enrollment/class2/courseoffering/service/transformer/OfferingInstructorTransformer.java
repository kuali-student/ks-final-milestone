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
package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class OfferingInstructorTransformer {

    public static List<OfferingInstructorInfo> lprs2Instructors(List<LprInfo> lprs) {
        List<OfferingInstructorInfo> results = new ArrayList<OfferingInstructorInfo>(lprs.size());

        for(LprInfo lpr : lprs) {
            OfferingInstructorInfo instructor = new OfferingInstructorInfo();
            instructor.setPersonId(lpr.getPersonId());
            if(!StringUtils.isEmpty(lpr.getCommitmentPercent())) {
                instructor.setPercentageEffort(Float.parseFloat(lpr.getCommitmentPercent()));
            }
            instructor.setId(lpr.getId());
            instructor.setTypeKey(lpr.getTypeKey());
            instructor.setStateKey(lpr.getStateKey());

            // Should be only one person found by person id
            List<Person> personList = getInstructorByPersonId(instructor.getPersonId());
            if(personList != null && !personList.isEmpty()){
                instructor.setPersonName(personList.get(0).getName());
            }

            results.add(instructor);
        }

        return results;

    }

    public static List<Person> getInstructorByPersonId(String personId){
        Map<String, String> searchCriteria = new HashMap<String, String>();
        searchCriteria.put(KIMPropertyConstants.Person.ENTITY_ID, personId);
        List<Person> lstPerson = getPersonService().findPeople(searchCriteria);
        return lstPerson;
    }

    public static PersonService getPersonService() {
        return KimApiServiceLocator.getPersonService();
    }

    public static List<LprInfo> instructors2Lprs(LuiInfo luiInfo, List<OfferingInstructorInfo> instructors) {

        List<LprInfo> results = new ArrayList<LprInfo>(instructors.size());

        for (OfferingInstructorInfo instructorInfo : instructors) {
            LprInfo lprInfo = new LprInfo();
            lprInfo.setId(instructorInfo.getId());

            Float cp = instructorInfo.getPercentageEffort();

            if (cp != null)
                lprInfo.setCommitmentPercent("" + cp);
            else
                lprInfo.setCommitmentPercent(null);

            lprInfo.setLuiId(luiInfo.getId());
            lprInfo.setPersonId(instructorInfo.getPersonId());
            lprInfo.setEffectiveDate(new Date());
            lprInfo.setTypeKey(instructorInfo.getTypeKey());
            lprInfo.setStateKey(instructorInfo.getStateKey());

            results.add(lprInfo);
        }

        return results;
    }
}
