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

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.InquiryForm;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import org.kuali.student.r2.lum.lrc.service.LRCService;

/**
 * This class provides a key value finder for student registration options
 *
 * @author Kuali Student Team
 */
public class StudentRegistrationOptionsKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        CourseOfferingEditWrapper form = null;
        if (model instanceof MaintenanceDocumentForm) {
            MaintenanceDocumentForm form1 = (MaintenanceDocumentForm)model;
            form = (CourseOfferingEditWrapper)form1.getDocument().getDocumentDataObject();
        } else if (model instanceof InquiryForm) {
            InquiryForm form1 = (InquiryForm)model;
            form = (CourseOfferingEditWrapper)form1.getDataObject();
        }

        if (form != null && form.getStudentRegOptions() != null) {
            ResultValuesGroupInfo rvg;
            try {
                ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
                for(String studentGradingOption : form.getStudentRegOptions()) {
                    rvg = CourseOfferingManagementUtil.getLrcService().getResultValuesGroup(studentGradingOption, contextInfo);
                    if (null != rvg) {
                        keyValues.add(new ConcreteKeyValue(studentGradingOption, rvg.getName()));
                    }
                    else {
                        keyValues.add(new ConcreteKeyValue(studentGradingOption, studentGradingOption));
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException("Error getting student registration options", e);
            }
        }

        return keyValues;
    }
}