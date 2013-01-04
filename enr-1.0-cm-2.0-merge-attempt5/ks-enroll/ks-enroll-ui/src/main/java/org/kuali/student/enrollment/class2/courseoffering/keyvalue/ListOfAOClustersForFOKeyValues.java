/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.form.RegistrationGroupManagementForm;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class retrieves Formats based on the course and returns a key-value pair list of
 * the Activity Type name and the Activity Id
 *
 * @author andrewlubbers
 *
 */
public class ListOfAOClustersForFOKeyValues extends UifKeyValuesFinderBase implements Serializable {
    private transient CourseOfferingService courseOfferingService;
    private static final long serialVersionUID = 1L;
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        RegistrationGroupManagementForm rgForm = (RegistrationGroupManagementForm) model;

        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("", "Select activity offering cluster..."));
        String formatOfferingId = rgForm.getFormatOfferingIdForViewRG();

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            List<ActivityOfferingClusterInfo> clusterInfos = getCourseOfferingService().getActivityOfferingClustersByFormatOffering(formatOfferingId, contextInfo);
            for (ActivityOfferingClusterInfo clusterInfo : clusterInfos) {
                keyValues.add(new ConcreteKeyValue(clusterInfo.getId(), clusterInfo.getPrivateName()));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting clusters for format offering", e);
        }
        return keyValues;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE, "CourseOfferingService"));
        }
        return courseOfferingService;
    }

}
