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
 * Created by Charles on 6/14/12
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.apache.log4j.Logger;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

import java.util.List;

/**
 * Cleans up any SOC Rollover Info, SOC Rollover Result Items in a term.  Deletes all course offerings (COs)
 * and anything dependent on it (FO/CO) including attributes.
 * 
 * Similar to reverse rollover, but does not generate
 *
 * @author cclin
 */
public class DeleteTargetTermRolloverRunner implements Runnable {
    CourseOfferingSetService socService;
    CourseOfferingService coService;
    String termId;

    final static Logger LOGGER = Logger.getLogger(DeleteTargetTermRolloverRunner.class);

    public CourseOfferingSetService getSocService() {
        return socService;
    }

    public void setSocService(CourseOfferingSetService socService) {
        this.socService = socService;
    }

    public CourseOfferingService getCoService() {
        return coService;
    }

    public void setCoService(CourseOfferingService coService) {
        this.coService = coService;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    private SocInfo _getMainSoc(List<String> socIds) {
        try {
            List<SocInfo> socInfoList = socService.getSocsByIds(socIds, new ContextInfo());
            for (SocInfo socInfo: socInfoList) {
                if (socInfo.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                    return socInfo;
                }
            }
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public void run() {
        try {
            List<String> socIds = socService.getSocIdsByTerm(termId, new ContextInfo());
            SocInfo socInfo = _getMainSoc(socIds);
            if (socInfo == null) {
                return;
            }
            List<String> coIds = socService.getCourseOfferingIdsBySoc(socInfo.getId(), new ContextInfo());
            // Cascade delete course offerings
            for (String coId: coIds) {
                coService.deleteCourseOfferingCascaded(coId, new ContextInfo());
            }
            // Delete SocInfo items
            // 1 Get rollover results
            List<String> resultIds = socService.getSocRolloverResultIdsByTargetSoc(socInfo.getId(), new ContextInfo());
            // 2 Iterate over the results
            for (String resultId: resultIds) {
                List<SocRolloverResultItemInfo> itemList = socService.getSocRolloverResultItemsByResultId(resultId, new ContextInfo());
                // 2.1 Delete the items
                for (SocRolloverResultItemInfo item: itemList) {
                    socService.deleteSocRolloverResultItem(item.getId(), new ContextInfo());
                }
                // 2.2 Delete the info
                socService.deleteSocRolloverResult(resultId, new ContextInfo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
