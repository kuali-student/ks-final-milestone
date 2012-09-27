package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.courseoffering.service.BuildingInfoLookupable;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.core.room.service.RoomService;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 9/26/12
 * Time: 7:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class BuildingInfoLookupableImpl extends LookupableImpl implements BuildingInfoLookupable{

    private RoomService roomService;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        try {
            return getRoomService().searchForBuildings(null, ContextBuilder.loadContextInfo());
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
