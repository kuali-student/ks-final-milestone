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
 */
package org.kuali.student.r2.core.acal.service.statehelper;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.state.service.StateHelper;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * This class is the state helper for milestone state changes
 *
 * @author Kuali Student Team
 */
public class MilestoneStateHelperImpl implements StateHelper{
    
    private AtpService atpService;
    private AcademicCalendarService academicCalendarService;
    private TypeService typeService;
    
    @Override
    public StatusInfo updateState(String entityId, String nextStateKey, ContextInfo context) {
        StatusInfo statusInfo = new StatusInfo();

        try{
            MilestoneInfo milestoneInfo = getAtpService().getMilestone(entityId,context);
            List<TypeTypeRelationInfo> typeTypeRelationInfos = getTypeService().getTypeTypeRelationsByRelatedTypeAndType(milestoneInfo.getTypeKey(),TypeServiceConstants.TYPE_TYPE_RELATION_GROUP_TYPE_KEY,context);

            if (typeTypeRelationInfos.isEmpty()){
                 throw new RuntimeException("Type type relation does not exists for " + milestoneInfo.getTypeKey());
            }

            //For calendar, we never have a type related with multiple types.. but just a check to make sure we're not having multiple entries
            if (typeTypeRelationInfos.size() > 1){
                throw new RuntimeException("Multiple Type Type relations exists for " + milestoneInfo.getTypeKey() + ", which is causing issue with state propagation.");
            }

            String groupType = typeTypeRelationInfos.get(0).getOwnerTypeKey();
            if (StringUtils.equals(groupType,AtpServiceConstants.MILESTONE_EVENT_GROUPING_TYPE_KEY)){
                return getAcademicCalendarService().changeAcalEventState(entityId,nextStateKey,context);
            } else if (StringUtils.equals(groupType,AtpServiceConstants.MILESTONE_HOLIDAY_GROUPING_TYPE_KEY)){
                return getAcademicCalendarService().changeHolidayState(entityId,nextStateKey,context);
            } else {//It must be a keydate
                return getAcademicCalendarService().changeKeyDateState(entityId,nextStateKey,context);
            }
        } catch (Exception e) {
            statusInfo.setSuccess(false);
            statusInfo.setMessage("Error updating Holiday state - " + e.getMessage());
            return statusInfo;
        }

    }

    @Override
    public String getStateKey(String entityId, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, OperationFailedException, PermissionDeniedException {
        return getAtpService().getMilestone(entityId, context).getStateKey();
    }
    
    protected AtpService getAtpService(){
        if (atpService == null){
            atpService = (AtpService) GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE, AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return atpService;
    }

    protected AcademicCalendarService getAcademicCalendarService(){
        if (academicCalendarService == null){
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return academicCalendarService;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
             typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }
}
