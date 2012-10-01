package org.kuali.student.enrollment.class2.grading.keyvalue;

/*
 * Copyright 2007 The Kuali Foundation
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

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.class2.grading.util.GradingConstants;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.GradingServiceConstants;
import org.kuali.student.mock.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.rice.krad.util.GlobalVariables;

public class CourseOfferingForGradingKeyValues extends KeyValuesBase {

    @Override
    public List getKeyValues() {
        List keyValues = new ArrayList();

        ContextInfo context = TestHelper.getContext1();

        GradingService gradingService = (GradingService) GlobalResourceLoader.getService(new QName(GradingServiceConstants.NAMESPACE, GradingServiceConstants.SERVICE_NAME_LOCAL_PART));

        String currentUser = GlobalVariables.getUserSession().getPrincipalId();

        try {
            List<GradeRosterInfo> gradeRosterInfoList = gradingService.getGradeRostersByGraderAndTerm(currentUser, GradingConstants.CURRENT_TERM, context);
            keyValues.add(new ConcreteKeyValue("", ""));
            if (gradeRosterInfoList != null){
                List courseOfferingList = new ArrayList();
                for (GradeRosterInfo rosterInfo : gradeRosterInfoList){
                    keyValues.add(new ConcreteKeyValue(rosterInfo.getCourseOfferingId(), rosterInfo.getName()));
                }
            }
        } catch (DoesNotExistException e) {
            keyValues.add(new ConcreteKeyValue("", ""));
        } catch (InvalidParameterException e) {
            throw new RuntimeException(e);
        } catch (MissingParameterException e) {
            throw new RuntimeException(e);
        } catch (OperationFailedException e) {
            throw new RuntimeException(e);
        } catch (PermissionDeniedException e) {
            GlobalVariables.getMessageMap().putError ("test",e.getMessage());
        }

        return keyValues;
    }

}
