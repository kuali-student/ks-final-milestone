package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.enrollment.class2.courseoffering.service.BuildingInfoLookupable;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.room.service.RoomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 9/26/12
 * Time: 7:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class RoomInfoLookupableImpl extends LookupableImpl implements BuildingInfoLookupable{

    private RoomService roomService;

    @Override
    public boolean validateSearchParameters(LookupForm form, Map<String, String> searchCriteria){
        if (searchCriteria == null || searchCriteria.isEmpty() || StringUtils.isBlank(searchCriteria.get("buildingId"))){
            GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"Please use the Building lookup first to select a building");
            return false;
        }
        return true;
    }

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {
        boolean validate = validateSearchParameters(lookupForm,fieldValues);
        if (validate){
            try {
                List<String> roomIds = getRoomService().getRoomIdsByBuilding(fieldValues.get("buildingId"), ContextBuilder.loadContextInfo());
                return getRoomService().getRoomsByIds(roomIds,ContextBuilder.loadContextInfo());
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
