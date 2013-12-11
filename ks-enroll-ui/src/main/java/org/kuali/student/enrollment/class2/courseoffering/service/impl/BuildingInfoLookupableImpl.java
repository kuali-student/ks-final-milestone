package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.rice.krad.lookup.Lookupable;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.student.common.util.ContextBuilder;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.r2.core.room.service.RoomService;

import java.util.List;
import java.util.Map;

/**
 * This lookup implementation is just for the KD. This will be replaced by the autosuggest after M4 rice upgrade.
 */
public class BuildingInfoLookupableImpl extends LookupableImpl implements Lookupable {

    private RoomService roomService;

    @Override
    public List<?> performSearch(LookupForm lookupForm, Map<String, String> searchCriteria, boolean bounded) {
        try {
            String buildingCode = searchCriteria.get("buildingCode");
            if (StringUtils.isNotBlank(buildingCode)){
                 return getRoomService().getBuildingsByBuildingCode(buildingCode, ContextBuilder.loadContextInfo());
            } else{
                return getRoomService().searchForBuildings(QueryByCriteria.Builder.create().build(), ContextBuilder.loadContextInfo());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    public RoomService getRoomService(){
        if (roomService == null){
            roomService = CourseOfferingResourceLoader.loadRoomService();
        }
        return roomService;
    }
}
