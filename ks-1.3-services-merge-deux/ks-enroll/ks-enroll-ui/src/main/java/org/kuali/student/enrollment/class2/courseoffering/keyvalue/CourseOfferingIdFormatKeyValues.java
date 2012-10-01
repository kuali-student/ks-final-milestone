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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.course.service.CourseServiceConstants;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;

public class CourseOfferingIdFormatKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private CourseOfferingService courseOfferingService;
    private CourseService courseService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<FormatInfo> formats = new ArrayList<FormatInfo>();
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        MaintenanceForm form1 = (MaintenanceForm)model;
        FormatOfferingInfo form = (FormatOfferingInfo)form1.getDocument().getDocumentDataObject();

        ContextInfo context = TestHelper.getContext1();
        String courseOfferingId = form.getCourseOfferingId();

        if (courseOfferingId != null) {
            try {
                CourseOfferingInfo courseOfferingInfo = (CourseOfferingInfo) getCourseOfferingService().getCourseOffering(courseOfferingId, context);
                CourseInfo courseInfo = (CourseInfo) getCourseService().getCourse(courseOfferingInfo.getCourseId());
                formats = courseInfo.getFormats();
            } catch (org.kuali.student.common.exceptions.DoesNotExistException e) {
                throw new RuntimeException("No subject areas found! There should be some in the database", e);
            } catch (org.kuali.student.common.exceptions.InvalidParameterException e) {
                throw new RuntimeException(e);
            } catch (org.kuali.student.common.exceptions.MissingParameterException e) {
                throw new RuntimeException(e);
            } catch (org.kuali.student.common.exceptions.OperationFailedException e) {
                throw new RuntimeException(e);
            } catch (org.kuali.student.common.exceptions.PermissionDeniedException e) {
                throw new RuntimeException(e);
            } catch (PermissionDeniedException e) {
                e.printStackTrace();
            } catch (MissingParameterException e) {
                e.printStackTrace();
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            } catch (OperationFailedException e) {
                e.printStackTrace();
            } catch (DoesNotExistException e) {
                throw new RuntimeException("No subject areas found! There should be some in the database", e);
            }

            for(FormatInfo format : formats) {
                keyValues.add(new ConcreteKeyValue(format.getId(), format.getType()));
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
