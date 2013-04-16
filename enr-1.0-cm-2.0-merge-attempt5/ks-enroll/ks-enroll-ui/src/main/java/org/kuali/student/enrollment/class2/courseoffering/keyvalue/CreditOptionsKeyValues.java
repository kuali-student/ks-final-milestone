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
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CreditOptionsKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient CourseService courseService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        MaintenanceDocumentForm form1 = (MaintenanceDocumentForm)model;
        CourseOfferingEditWrapper form = (CourseOfferingEditWrapper)form1.getDocument().getDocumentDataObject();

        String courseId = form.getCoInfo().getCourseId();
        List<ResultValuesGroupInfo> creditOptions;

        if (courseId != null) {
            try {
                CourseInfo courseInfo = getCourseService().getCourse(courseId, ContextUtils.getContextInfo());
                creditOptions = courseInfo.getCreditOptions();
            } catch (DoesNotExistException e) {
                throw new RuntimeException("No subject areas found! There should be some in the database", e);
            } catch (Exception e) {
                throw new RuntimeException("Error finding subject Areas", e);
            }

            for (ResultValuesGroupInfo rVGI : creditOptions) {
                String typeKey =  rVGI.getTypeKey();
                if(typeKey.equals(LrcServiceConstants.R1_RESULT_COMPONENT_TYPE_KEY_FIXED)||typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                    keyValues.add(new ConcreteKeyValue(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED, "Fixed"));
                } else if (typeKey.equals(LrcServiceConstants.R1_RESULT_COMPONENT_TYPE_KEY_RANGE) || typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {
                    keyValues.add(new ConcreteKeyValue(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED, "Fixed"));
                    keyValues.add(new ConcreteKeyValue(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE, "Variable"));
                    keyValues.add(new ConcreteKeyValue(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE, "Multiple"));
                } else if (typeKey.equals(LrcServiceConstants.R1_RESULT_COMPONENT_TYPE_KEY_MULTIPLE) || typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
                    keyValues.add(new ConcreteKeyValue(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED, "Fixed"));
                    keyValues.add(new ConcreteKeyValue(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE, "Multiple"));
                } else {
                    keyValues.add(new ConcreteKeyValue(typeKey, "Unknown Type"));
                }
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
