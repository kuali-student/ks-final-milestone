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
 * Created by vgadiyak on 5/23/12
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseOfferingIdFormatKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient CourseOfferingService courseOfferingService;
    private transient CourseService courseService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        MaintenanceDocumentForm form1 = (MaintenanceDocumentForm)model;
        FormatOfferingInfo form = (FormatOfferingInfo)form1.getDocument().getDocumentDataObject();

        ContextInfo context = TestHelper.getContext1();
        String courseOfferingId = form.getCourseOfferingId();

        if (courseOfferingId != null) {
            List<FormatInfo> formats;
            try {
                CourseOfferingInfo courseOfferingInfo = getCourseOfferingService().getCourseOffering(courseOfferingId, context);
                CourseInfo courseInfo = getCourseService().getCourse(courseOfferingInfo.getCourseId(), context);
                formats = courseInfo.getFormats();
            } catch (DoesNotExistException e) {
                throw new RuntimeException("No subject areas found! There should be some in the database", e);
            } catch (Exception e) {
                throw new RuntimeException("Error looking up subject areas", e);
            }

            for(FormatInfo format : formats) {
                keyValues.add(new ConcreteKeyValue(format.getId(), format.getTypeKey()));
            }
        }

        return keyValues;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if(courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/courseOffering", "CourseOfferingService"));
        }
        return this.courseOfferingService;
    }

    protected CourseService getCourseService() {
        if(courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, "CourseService"));
        }
        return this.courseService;
    }
}
