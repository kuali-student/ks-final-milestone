/**
 * Copyright 2013 The Kuali Foundation Licensed under the
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
 * Created by David Yin on 3/25/13
 */
package org.kuali.student.enrollment.class2.autogen.keyvalue;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.autogen.controller.ARGUtil;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ARGClustersForCreateAOKeyValues extends UifKeyValuesFinderBase implements Serializable {
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        ARGCourseOfferingManagementForm coForm = (ARGCourseOfferingManagementForm) model;
        String formatOfferingId = coForm.getFormatOfferingIdForNewAO();
        if (formatOfferingId==null || formatOfferingId.equals("")) {
            if (coForm.getFormatOfferingIds().isEmpty()) {
                return keyValues;
            }
            formatOfferingId = coForm.getFormatOfferingIds().get(0);
        }
//        keyValues.add(new ConcreteKeyValue("", "Select Cluster"));

        if(!StringUtils.isEmpty(formatOfferingId)) {
            try {
                List<ActivityOfferingClusterInfo> clusters =
                        getCourseOfferingService().getActivityOfferingClustersByFormatOffering(formatOfferingId, ContextUtils.getContextInfo());
                //if there are clusters for the given FO, display them in the dropdown, otherwise create a default cluster to display
                if (clusters!=null && clusters.size()>0) {
                    for (ActivityOfferingClusterInfo cluster : clusters) {
                        keyValues.add(new ConcreteKeyValue(cluster.getId(), cluster.getPrivateName()));
                        coForm.setHasAOCluster(true);
                    }
                } else {
                    keyValues.add(new ConcreteKeyValue("", ARGUtil.getArgServiceAdapter().getDefaultClusterNamePerCO(coForm.getCurrentCourseOfferingWrapper().getCourseOfferingId(),ContextUtils.getContextInfo())));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return keyValues;
    }

    protected CourseService getCourseService() {
        return CourseOfferingResourceLoader.loadCourseService();
    }

    protected TypeService getTypeService(){
        return CourseOfferingResourceLoader.loadTypeService();
    }

    protected CourseOfferingService getCourseOfferingService(){
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }


}
