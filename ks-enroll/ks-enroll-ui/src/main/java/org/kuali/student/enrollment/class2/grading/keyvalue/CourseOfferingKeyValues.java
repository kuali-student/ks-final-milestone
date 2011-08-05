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
import org.kuali.rice.core.util.ConcreteKeyValue;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.rice.kns.util.ErrorMessage;
import org.kuali.rice.kns.util.GlobalVariables;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.classII.grading.service.GradingServiceMockImpl;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.test.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

public class CourseOfferingKeyValues extends KeyValuesBase {

    public List getKeyValues() {
        List keyValues = new ArrayList();

        ContextInfo context = TestHelper.getContext1();

        GradingService gradingService = (GradingService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/grading", "GradingService"));

        try {
            List<GradeRosterInfo> gradeRosterInfoList = gradingService.getGradeRostersByGraderAndTerm("Grader1", "201108", context);
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
            GlobalVariables.getMessageList().add(new ErrorMessage("test",e.getMessage()));
        }

        return keyValues;
    }

}