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
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.common.util.security.ContextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a key value finder for Activity Offering Clusters for the Create Activity Offering ui
 *
 * @author Kuali Student Team
 */
public class ClustersForCreateAOKeyValues extends UifKeyValuesFinderBase implements Serializable {
    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        FormatsForCreateAOKeyValues formatsForCreateAOKeyValues = new FormatsForCreateAOKeyValues();
        CourseOfferingManagementForm coForm = (CourseOfferingManagementForm) model;
        String formatOfferingId = coForm.getFormatOfferingIdForNewAO();
        coForm.setHasAOCluster(false);
        //If there is no selected format and the CO has no formats, return an  empty list
        if (StringUtils.isEmpty(formatOfferingId)) {
            //  Just return if the CO has no formats (e.g. it was just created).
            if (coForm.getFoId2aoTypeMap().isEmpty()) {
                return keyValues;
            }
            formatOfferingId = formatsForCreateAOKeyValues.getFirstKey(model);
        }

        if (!StringUtils.isEmpty(formatOfferingId)) {
            try {
                //Grab the clusters corresponding to the selected format id (or all if no selection was made)
                List<ActivityOfferingClusterWrapper> clusters = new ArrayList<ActivityOfferingClusterWrapper>();
                for (ActivityOfferingClusterWrapper cluster : coForm.getClusterResultList()) {
                    if (StringUtils.isEmpty(formatOfferingId) || formatOfferingId.equals(cluster.getFormatOfferingId())) {
                        clusters.add(cluster);
                    }
                }

                //if there are clusters for the given FO, display them in the dropdown, otherwise create a default cluster to display
                if (!clusters.isEmpty()) {
                    for (ActivityOfferingClusterWrapper cluster : clusters) {
                        keyValues.add(new ConcreteKeyValue(cluster.getAoCluster().getId()==null?"":cluster.getAoCluster().getId(), cluster.getAoCluster().getPrivateName()));
                        coForm.setHasAOCluster(true);
                    }
                } else {
                    keyValues.add(new ConcreteKeyValue("", CourseOfferingManagementUtil.getCourseOfferingServiceFacade().getDefaultClusterNamePerCO(coForm.getCurrentCourseOfferingWrapper().getCourseOfferingId(), ContextUtils.getContextInfo())));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return keyValues;
    }
}
