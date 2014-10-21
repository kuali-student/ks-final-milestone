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
 * Created by David Yin on 8/7/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.student.enrollment.class2.courseoffering.service.SeatPoolUtilityService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class provides a default implementation of the SeatPoolUtility Service
 *
 * @author Kuali Student Team
 */
public class SeatPoolUtilityServiceImpl implements SeatPoolUtilityService {

    @Override
    public void updateSeatPoolDefinitionList(List<SeatPoolDefinitionInfo> updatedSeatPoolList, String activityOfferingId, ContextInfo context) {
        List<SeatPoolDefinitionInfo> updatedSeatPoolFinalList = new ArrayList<SeatPoolDefinitionInfo>();
        List <String> currentSeatPoolIds = getExistingSeatPoolIds(activityOfferingId, context);

        List<String> idsToDelete = _getSeatPoolIdsToDelete(updatedSeatPoolList, currentSeatPoolIds);

        try {
            if (updatedSeatPoolList != null)  {

                //delete SPs that have been removed by the user
                // This MUST happen BEFORE we add any items. There is validation that runs when
                // a Seatpool is added that breaks if we haven't removed deleted items from the
                // AO.
                if (idsToDelete != null && !idsToDelete.isEmpty()){
                    for(String seatPoolId: idsToDelete){
                        CourseOfferingManagementUtil.getCourseOfferingService().deleteSeatPoolDefinition(seatPoolId, context);
                    }
                }

                Collections.sort(updatedSeatPoolList, new Comparator<SeatPoolDefinitionInfo>() {
                    @Override
                    public int compare(SeatPoolDefinitionInfo sp1, SeatPoolDefinitionInfo sp2) {
                        return sp1.getProcessingPriority().compareTo(sp2.getProcessingPriority());
                    }
                });

                int seatPoolPriority = 1;
                for (SeatPoolDefinitionInfo seatPool : updatedSeatPoolList) {
                    seatPool.setProcessingPriority(seatPoolPriority);
                    if(seatPool.getId()!=null && !seatPool.getId().isEmpty() && currentSeatPoolIds.contains(seatPool.getId())) {
                        //update SP
                        SeatPoolDefinitionInfo seatPoolUpdated = CourseOfferingManagementUtil.getCourseOfferingService().updateSeatPoolDefinition(seatPool.getId(), seatPool, context);
                        updatedSeatPoolFinalList.add(seatPoolUpdated);
                        currentSeatPoolIds.remove(seatPool.getId());
                    } else {
                        //create new SP
                        seatPool.setTypeKey(LuiServiceConstants.SEATPOOL_LUI_CAPACITY_TYPE_KEY);
                        seatPool.setStateKey(LuiServiceConstants.LUI_CAPACITY_ACTIVE_STATE_KEY);
                        SeatPoolDefinitionInfo seatPoolCreated = CourseOfferingManagementUtil.getCourseOfferingService().createSeatPoolDefinition(seatPool,context);
                        CourseOfferingManagementUtil.getCourseOfferingService().addSeatPoolDefinitionToActivityOffering(seatPoolCreated.getId(),activityOfferingId, context);
                        updatedSeatPoolFinalList.add(seatPoolCreated);

                    }
                    seatPoolPriority++;
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<String>  _getSeatPoolIdsToDelete(List<SeatPoolDefinitionInfo> updatedSeatPoolList, List <String> currentSeatPoolIds){

        List<String> idsToDelete = new ArrayList<String>();
        List<String> updatedSeatPoolIds = new ArrayList<String>();

        for(SeatPoolDefinitionInfo sp : updatedSeatPoolList){
            updatedSeatPoolIds.add(sp.getId());
        }

        for(String  currentSeatPoolId : currentSeatPoolIds){
          if(!updatedSeatPoolIds.contains(currentSeatPoolId)){
            idsToDelete.add(currentSeatPoolId);
          }
        }
        return idsToDelete;
    }

    private List<String> getExistingSeatPoolIds(String activityOfferingId, ContextInfo context) {
        try {
            List<SeatPoolDefinitionInfo> seatPoolList = CourseOfferingManagementUtil.getCourseOfferingService().getSeatPoolDefinitionsForActivityOffering(activityOfferingId, context);
            List<String> seatPoolIds = new ArrayList<String>();

            if(seatPoolList != null && !seatPoolList.isEmpty()){
                for(SeatPoolDefinitionInfo seatPool : seatPoolList){
                    seatPoolIds.add(seatPool.getId());
                }
            }

            return seatPoolIds;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
