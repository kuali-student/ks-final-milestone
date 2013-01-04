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
 * Created by vgadiyak on 6/25/12
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.common.exceptions.*;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.kuali.student.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.*;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CreditOptionsKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private CourseService courseService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<String> gradingOptions;
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        MaintenanceForm form1 = (MaintenanceForm)model;
        CourseOfferingEditWrapper form = (CourseOfferingEditWrapper)form1.getDocument().getDocumentDataObject();

        String courseId = form.getCoInfo().getCourseId();
        ResultComponentInfo resultComponentInfo = null;

        if (courseId != null) {
            try {
                CourseInfo courseInfo = (CourseInfo) getCourseService().getCourse(courseId);
                List<ResultComponentInfo> creditOptions = courseInfo.getCreditOptions();
                resultComponentInfo = creditOptions.get(0);
            } catch (DoesNotExistException e) {
                throw new RuntimeException("No subject areas found! There should be some in the database", e);
            } catch (InvalidParameterException e) {
                throw new RuntimeException(e);
            } catch (MissingParameterException e) {
                throw new RuntimeException(e);
            } catch (OperationFailedException e) {
                throw new RuntimeException(e);
            } catch (PermissionDeniedException e) {
                throw new RuntimeException(e);
            }

            if (resultComponentInfo.getType().equalsIgnoreCase(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED)) {
                keyValues.add(new ConcreteKeyValue(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED, "Fixed"));
            } else if (resultComponentInfo.getType().equalsIgnoreCase(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE)) {
                keyValues.add(new ConcreteKeyValue(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED, "Fixed"));
                keyValues.add(new ConcreteKeyValue(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE, "Variable"));
                keyValues.add(new ConcreteKeyValue(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE, "Multiple"));
            } else if (resultComponentInfo.getType().equalsIgnoreCase(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE)) {
                keyValues.add(new ConcreteKeyValue(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED, "Fixed"));
                keyValues.add(new ConcreteKeyValue(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE, "Multiple"));
            }
        }

        return keyValues;
    }

    protected CourseService getCourseService() {
        if(courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, "CourseService"));
        }
        return this.courseService;
    }
}
