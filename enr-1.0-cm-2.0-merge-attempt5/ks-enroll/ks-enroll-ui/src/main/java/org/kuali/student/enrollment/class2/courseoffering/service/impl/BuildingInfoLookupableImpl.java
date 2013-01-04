package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.courseoffering.service.BuildingInfoLookupable;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.core.room.service.RoomService;

import java.util.List;
import java.util.Map;

/**
 * This lookup implementation is just for the KD. This will be replaced by the autosuggest after M4 rice upgrade.
 */
public class BuildingInfoLookupableImpl extends LookupableImpl implements BuildingInfoLookupable{

    private RoomService roomService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        try {
            String buildingCode = fieldValues.get("buildingCode");
            if (StringUtils.isNotBlank(buildingCode)){
                 return getRoomService().getBuildingsByBuildingCode(buildingCode, ContextBuilder.loadContextInfo());
            } else{
                return getRoomService().searchForBuildings(null, ContextBuilder.loadContextInfo());
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
