package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.lookup.Lookupable;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.common.util.ContextBuilder;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.service.RoomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This lookup implementation is just for the KD. This will be replaced by the autosuggest after M4 rice upgrade.
 */
public class RoomInfoLookupableImpl extends LookupableImpl implements Lookupable {

    private RoomService roomService;

    @Override
    public boolean validateSearchParameters(LookupForm form, Map<String, String> searchCriteria){
        if (searchCriteria == null || searchCriteria.isEmpty() || StringUtils.isBlank(searchCriteria.get("buildingCode"))){
            GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Please enter the building code first to select a room");
            return false;
        }
        return true;
    }

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {
        boolean validate = validateSearchParameters(lookupForm,searchCriteria);
        int firstBuilding = 0;
        if (validate){
            try {

                List<BuildingInfo> buildings = getRoomService().getBuildingsByBuildingCode(searchCriteria.get("buildingCode"), ContextBuilder.loadContextInfo());

                if (buildings.isEmpty()){
                    GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Invalid building code");
                    return new ArrayList<RoomInfo>();
                }

                if (StringUtils.isBlank(searchCriteria.get("roomCode"))){
                    List<String> roomIds = getRoomService().getRoomIdsByBuilding(buildings.get(firstBuilding).getId(), ContextBuilder.loadContextInfo());

                    if(roomIds.isEmpty()) {
                        return new ArrayList<RoomInfo>();
                    }

                    return getRoomService().getRoomsByIds(roomIds,ContextBuilder.loadContextInfo());
                } else {
                    return getRoomService().getRoomsByBuildingAndRoomCode(buildings.get(firstBuilding).getId(),searchCriteria.get("roomCode"),ContextBuilder.loadContextInfo());
                }

            } catch (DoesNotExistException e) {
                return new ArrayList<RoomInfo>();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return new ArrayList<RoomInfo>();

    }

    public RoomService getRoomService(){
        if (roomService == null){
            roomService = CourseOfferingResourceLoader.loadRoomService();
        }
        return roomService;
    }
}
