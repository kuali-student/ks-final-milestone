package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.lookup.Lookupable;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.common.util.ContextBuilder;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This lookup implementation is just for the KD. This will be replaced by the autosuggest after M4 rice upgrade.
 */
public class RoomInfoLookupableImpl extends LookupableImpl implements Lookupable {

    @Override
    public boolean validateSearchParameters(LookupForm form, Map<String, String> searchCriteria){
        if (searchCriteria == null || searchCriteria.isEmpty() || StringUtils.isBlank(searchCriteria.get("buildingCode"))){
            GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Please enter the building code first to select a room");
            return false;
        }
        return true;
    }

    @Override
    public Map<String, String> performClear(LookupForm form, Map<String, String> searchCriteria) {
        String buildingCode = searchCriteria.get("buildingCode");
        Map<String, String> clearedSearchCriteria = super.performClear(form, searchCriteria);
        clearedSearchCriteria.put("buildingCode", buildingCode);
        return clearedSearchCriteria;
    }

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        boolean validate = validateSearchParameters(lookupForm,fieldValues);
        int firstBuilding = 0;
        if (validate){
            try {

                List<BuildingInfo> buildings = CourseOfferingManagementUtil.getRoomService().getBuildingsByBuildingCode(fieldValues.get("buildingCode"), ContextBuilder.loadContextInfo());

                if (buildings.isEmpty()){
                    GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Invalid building code");
                    return new ArrayList<RoomInfo>();
                }

                if (StringUtils.isBlank(searchCriteria.get("roomCode"))){
                    List<String> roomIds = CourseOfferingManagementUtil.getRoomService().getRoomIdsByBuilding(buildings.get(firstBuilding).getId(), ContextBuilder.loadContextInfo());

                    if(roomIds.isEmpty()) {
                        return new ArrayList<RoomInfo>();
                    }

                    return CourseOfferingManagementUtil.getRoomService().getRoomsByIds(roomIds,ContextBuilder.loadContextInfo());
                } else {
                    return CourseOfferingManagementUtil.getRoomService().getRoomsByBuildingAndRoomCode(buildings.get(firstBuilding).getId(),fieldValues.get("roomCode"),ContextBuilder.loadContextInfo());
                }

            } catch (DoesNotExistException e) {
                return new ArrayList<RoomInfo>();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return new ArrayList<RoomInfo>();

    }
}
