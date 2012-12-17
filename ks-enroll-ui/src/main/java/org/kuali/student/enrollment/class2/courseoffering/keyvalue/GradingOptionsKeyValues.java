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
 * Created by vgadiyak on 6/2/12
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.InquiryForm;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.mock.utilities.TestHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class GradingOptionsKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient CourseService courseService;
    private transient LRCService lrcService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        ContextInfo context = TestHelper.getContext1();

        CourseOfferingEditWrapper form = null;
        if (model instanceof MaintenanceDocumentForm) {
            MaintenanceDocumentForm form1 = (MaintenanceDocumentForm)model;
            form = (CourseOfferingEditWrapper)form1.getDocument().getDocumentDataObject();
        } else if (model instanceof InquiryForm) {
            InquiryForm form1 = (InquiryForm)model;
            form = (CourseOfferingEditWrapper)form1.getDataObject();
        }

        if (form != null && form.getCrsGradingOptions() != null) {
           for(String crsGradingOption : form.getCrsGradingOptions()) {
               try {
                   ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(crsGradingOption, context);  // gradingOption = LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER || LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL
                   keyValues.add(new ConcreteKeyValue(rvg.getKey(), rvg.getName()));
               } catch (Exception e) {
                   throw new RuntimeException("Error looking up grading option",e);
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

    protected LRCService getLrcService() {
        if(lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lrc", "LrcService"));
        }
        return this.lrcService;
    }
}