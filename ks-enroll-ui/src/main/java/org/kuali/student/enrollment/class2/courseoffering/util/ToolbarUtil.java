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
 * Created by Li Pan on 1/26/13
 */
package org.kuali.student.enrollment.class2.courseoffering.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.List;

/**
 * This class contains mehtods to handle CO and AO toolbars matrix
 *
 * @author Kuali Student Team
 */
public class ToolbarUtil {
    public static void processCoToolbarForDeptAdmin(List<CourseOfferingListSectionWrapper> coListWrapperList, CourseOfferingManagementForm form){
        String socState = form.getSocStateKey();
        if(coListWrapperList != null && !coListWrapperList.isEmpty()){
        for(CourseOfferingListSectionWrapper coListWrapper : coListWrapperList){
            String coState = coListWrapper.getCourseOfferingStateKey();
            if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY) ||
                StringUtils.equals(socState, CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY)){
                form.setEnableAddButton(true);
                if(StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY)){
                    if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY)){
                        coListWrapper.setEnableApproveButton(true);
                    }
                    coListWrapper.setEnableSuspendButton(true);
                    coListWrapper.setEnableCancelButton(true);
                    coListWrapper.setEnableDeleteButton(true);
                }

                if(StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY)){
                    coListWrapper.setEnableSuspendButton(true);
                    coListWrapper.setEnableCancelButton(true);
                    coListWrapper.setEnableDeleteButton(true);
                }

                if(StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)){
                    coListWrapper.setEnableSuspendButton(true);
                    coListWrapper.setEnableCancelButton(true);
                }

            }

            if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.CLOSED_SOC_STATE_KEY)) {
                    //all buttons disabled

            }

            if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY)) {
                if(StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY) ||
                        StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY)){
                    coListWrapper.setEnableDeleteButton(true);
                }
            }
        }
        }
    }

    public static void processCoToolbarForCentralAdmin(List<CourseOfferingListSectionWrapper> coListWrapperList, CourseOfferingManagementForm form){
        String socState = form.getSocStateKey();
        if(coListWrapperList != null && !coListWrapperList.isEmpty()){
        for(CourseOfferingListSectionWrapper coListWrapper : coListWrapperList){
            String coState = coListWrapper.getCourseOfferingStateKey();
            if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.CLOSED_SOC_STATE_KEY)){
                form.setEnableAddButton(true);
                if(StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY)){
                    if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY)){
                        coListWrapper.setEnableApproveButton(true);
                    }
                    coListWrapper.setEnableSuspendButton(true);
                    coListWrapper.setEnableCancelButton(true);
                    coListWrapper.setEnableDeleteButton(true);
                }

                if(StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY)){
                    coListWrapper.setEnableSuspendButton(true);
                    coListWrapper.setEnableCancelButton(true);
                    coListWrapper.setEnableDeleteButton(true);
                }

                if(StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)){
                    coListWrapper.setEnableSuspendButton(true);
                    coListWrapper.setEnableCancelButton(true);
                    coListWrapper.setEnableDeleteButton(true);
                }
                //TODO:   locked but mse not in progress
            }

            if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY)){
               //TODO: locked & mse in progress
                    //all buttons disabled
            }
        }
        }
    }

    public static void processAoToolbarForDeptAdmin(List<ActivityOfferingWrapper> activityWrapperList, CourseOfferingManagementForm form){
        String socState = form.getSocStateKey();
        if(activityWrapperList != null && !activityWrapperList.isEmpty()){
        for(ActivityOfferingWrapper activityWrapper : activityWrapperList){
            String aoState = activityWrapper.getAoInfo().getStateKey();
            if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY) ||
                StringUtils.equals(socState, CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY)){
                form.setEnableAddButton(true);
                if(StringUtils.equals(aoState, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY)){
                    if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY)){
                        activityWrapper.setEnableApproveButton(true);
                    }
                    activityWrapper.setEnableSuspendButton(true);
                    activityWrapper.setEnableCancelButton(true);
                    activityWrapper.setEnableDeleteButton(true);
                }

                if(StringUtils.equals(aoState, LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY)){
                    activityWrapper.setEnableSuspendButton(true);
                    activityWrapper.setEnableCancelButton(true);
                    activityWrapper.setEnableDeleteButton(true);
                    activityWrapper.setEnableDraftButton(true);
                }

                if(StringUtils.equals(aoState, LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY)){
                    activityWrapper.setEnableSuspendButton(true);
                    activityWrapper.setEnableCancelButton(true);
                }
            }

            if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.CLOSED_SOC_STATE_KEY)) {
                    //all buttons disabled
            }

            if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY)) {
                if(StringUtils.equals(aoState, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY)){
                    activityWrapper.setEnableDeleteButton(true);
                }
                if(StringUtils.equals(aoState, LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY)){
                    activityWrapper.setEnableDraftButton(true);
                    activityWrapper.setEnableDeleteButton(true);
                }
            }
        }
        }
    }

    public static void processAoToolbarForCentralAdmin(List<ActivityOfferingWrapper> activityWrapperList, CourseOfferingManagementForm form){
        String socState = form.getSocStateKey();
        if(activityWrapperList != null && !activityWrapperList.isEmpty()){
        for(ActivityOfferingWrapper activityWrapper : activityWrapperList){
            String aoState = activityWrapper.getAoInfo().getStateKey();
            if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY) ||
                    StringUtils.equals(socState, CourseOfferingSetServiceConstants.CLOSED_SOC_STATE_KEY)){
                form.setEnableAddButton(true);
                if(StringUtils.equals(aoState, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY)){
                    if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY)){
                        activityWrapper.setEnableApproveButton(true);
                    }
                    activityWrapper.setEnableSuspendButton(true);
                    activityWrapper.setEnableCancelButton(true);
                    activityWrapper.setEnableDeleteButton(true);
                }

                if(StringUtils.equals(aoState, LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY)){
                    activityWrapper.setEnableSuspendButton(true);
                    activityWrapper.setEnableCancelButton(true);
                    activityWrapper.setEnableDeleteButton(true);
                    activityWrapper.setEnableDraftButton(true);
                }

                if(StringUtils.equals(aoState, LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY)){
                    activityWrapper.setEnableSuspendButton(true);
                    activityWrapper.setEnableCancelButton(true);
                    activityWrapper.setEnableDeleteButton(true);
                }
                //TODO:   locked but mse not in progress
            }

            if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY)){
                //TODO: locked & mse in progress
                     //all buttons disabled
            }
        }
    }
    }

}
