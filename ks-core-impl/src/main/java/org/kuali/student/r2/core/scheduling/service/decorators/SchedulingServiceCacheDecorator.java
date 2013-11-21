package org.kuali.student.r2.core.scheduling.service.decorators;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.RoomServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestDisplayInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.transformer.ScheduleDisplayTransformer;

import javax.jws.WebParam;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cached decorator of scheduling service
 */
public class SchedulingServiceCacheDecorator extends SchedulingServiceDecorator {
    private static String cacheName = "scheduleCache";
    public static final String SCHEDULE_KEY = "schedule";
    public static final String SCHEDULE_REQUEST_KEY = "scheduleRequest";
    private CacheManager cacheManager;
    private static final String TIMESLOT_KEY = "timeslot";

    private AtpService atpService;
    private RoomService roomService;
    private TypeService typeService;

    //TODO KSENROLL-8513 add getScheduleRequestsByScheduleRequestSet it is used frequently

    @Override
    public ScheduleInfo createSchedule(@WebParam(name = "scheduleTypeKey") String scheduleTypeKey, @WebParam(name = "scheduleInfo") ScheduleInfo scheduleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ScheduleInfo result = getNextDecorator().createSchedule(scheduleTypeKey, scheduleInfo, contextInfo);
        MultiKey cacheKey = new MultiKey(SCHEDULE_KEY, result.getId());
        getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        return result;
    }

    @Override
    public ScheduleRequestInfo createScheduleRequest(@WebParam(name = "scheduleRequestTypeKey") String scheduleRequestTypeKey, @WebParam(name = "scheduleRequestInfo") ScheduleRequestInfo scheduleRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ScheduleRequestInfo result = getNextDecorator().createScheduleRequest(scheduleRequestTypeKey, scheduleRequestInfo, contextInfo);
        MultiKey cacheKey = new MultiKey(SCHEDULE_REQUEST_KEY, result.getId());
        getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        return result;
    }

    @Override
    public ScheduleInfo getSchedule(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey("schedule", scheduleId);

        Element cachedResult = getCacheManager().getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getSchedule(scheduleId, contextInfo);
            getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (ScheduleInfo)result;
    }

    @Override
    public List<ScheduleInfo> getSchedulesByIds(@WebParam(name = "scheduleIds") List<String> scheduleIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleInfo> scheduleInfos = new ArrayList<ScheduleInfo>();
        List<String> scheduleIdsToSearch = new ArrayList<String>();
        Cache cache = cacheManager.getCache(cacheName);
        //Check the cache for the ids
        for(String scheduleId : scheduleIds){
            MultiKey cacheKey = new MultiKey("schedule", scheduleId);
            Element cachedResult = cache.get(cacheKey);
            if (cachedResult != null) {
                //If the id was found in the cache then use that and remove from the list of ids one is searching for
                scheduleInfos.add((ScheduleInfo) cachedResult.getValue());
            } else {
                //Otherwise save this id as one that needs to be looked up
                scheduleIdsToSearch.add(scheduleId);
            }
        }
        //Call the underlying service to get the remainder
        if (!scheduleIdsToSearch.isEmpty()) {
            List<ScheduleInfo> uncachedScheduleInfos = getNextDecorator().getSchedulesByIds(scheduleIdsToSearch, contextInfo);
            for (ScheduleInfo scheduleRequestInfo : uncachedScheduleInfos) {
                MultiKey cacheKey = new MultiKey("scheduleRequest", scheduleRequestInfo.getId());
                cache.put(new Element(cacheKey, scheduleRequestInfo));
            }
            scheduleInfos.addAll(uncachedScheduleInfos);
        }

        return scheduleInfos;
    }

    @Override
    public ScheduleRequestInfo getScheduleRequest(@WebParam(name = "scheduleRequestId") String scheduleRequestId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey("scheduleRequest", scheduleRequestId);

        Element cachedResult = getCacheManager().getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getScheduleRequest(scheduleRequestId, contextInfo);
            getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (ScheduleRequestInfo)result;
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByIds(@WebParam(name = "scheduleRequestIds") List<String> scheduleRequestIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestInfo> scheduleRequestInfos = new ArrayList<ScheduleRequestInfo>();
        List<String> scheduleRequestIdsToSearch = new ArrayList<String>();
        Cache cache = cacheManager.getCache(cacheName);
        //Check the cache for the ids
        for(String scheduleRequestId : scheduleRequestIds){
            MultiKey cacheKey = new MultiKey("scheduleRequest", scheduleRequestId);
            Element cachedResult = cache.get(cacheKey);
            if (cachedResult != null) {
                //If the id was found in the cache then use that and remove from the list of ids one is searching for
                scheduleRequestInfos.add((ScheduleRequestInfo) cachedResult.getValue());
            } else {
                //Otherwise save this id as one that needs to be looked up
                scheduleRequestIdsToSearch.add(scheduleRequestId);
            }
        }
        //Call the underlying service to get the remainder
        if (!scheduleRequestIdsToSearch.isEmpty()) {
            List<ScheduleRequestInfo> uncachedScheduleRequestInfos = getNextDecorator().getScheduleRequestsByIds(scheduleRequestIdsToSearch, contextInfo);
            for (ScheduleRequestInfo scheduleRequestInfo : uncachedScheduleRequestInfos) {
                MultiKey cacheKey = new MultiKey("scheduleRequest", scheduleRequestInfo.getId());
                cache.put(new Element(cacheKey, scheduleRequestInfo));
            }
            scheduleRequestInfos.addAll(uncachedScheduleRequestInfos);
        }

        return scheduleRequestInfos;
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByRefObject(@WebParam(name = "refObjectType") String refObjectType, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        //Use two calls to take advantage of caching, first gets related ids,
        //second gets requests by list of ids and is cached.
        List<String> scheduleRequestIds = getNextDecorator().getScheduleRequestIdsByRefObject(refObjectType, refObjectId, contextInfo);

        try {
            return getScheduleRequestsByIds(scheduleRequestIds, contextInfo);
        } catch (DoesNotExistException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByRefObjects(
            @WebParam(name = "refObjectType") String refObjectType,
            @WebParam(name = "refObjectId") List<String> refObjectIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        //To take advantage of caching we will call the cached version if there is only 1 shedule request (which is true 9 times out of ten
        if(refObjectIds != null && refObjectIds.size()==1){
            return getScheduleRequestsByRefObject(refObjectType, refObjectIds.get(0), contextInfo);
        }
        return getNextDecorator().getScheduleRequestsByRefObjects(refObjectType, refObjectIds, contextInfo);
    }

    @Override
    public ScheduleInfo updateSchedule(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "scheduleInfo") ScheduleInfo scheduleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        MultiKey cacheKey = new MultiKey("schedule", scheduleId);
        ScheduleInfo result = getNextDecorator().updateSchedule(scheduleId, scheduleInfo, contextInfo);
        getCacheManager().getCache(cacheName).remove(cacheKey);
        getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        return result;
    }

    @Override
    public ScheduleRequestInfo updateScheduleRequest(@WebParam(name = "scheduleRequestId") String scheduleRequestId, @WebParam(name = "scheduleRequestInfo") ScheduleRequestInfo scheduleRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        MultiKey cacheKey = new MultiKey("scheduleRequest", scheduleRequestId);
        ScheduleRequestInfo result = getNextDecorator().updateScheduleRequest(scheduleRequestId, scheduleRequestInfo, contextInfo);
        getCacheManager().getCache(cacheName).remove(cacheKey);
        getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        return result;
    }

    @Override
    public StatusInfo deleteSchedule(@WebParam(name = "scheduleId") String scheduleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("schedule", scheduleId);
        StatusInfo result = getNextDecorator().deleteSchedule(scheduleId, contextInfo);
        getCacheManager().getCache(cacheName).remove(cacheKey);
        return result;
    }

    @Override
    public StatusInfo deleteScheduleRequest(@WebParam(name = "scheduleRequestId") String scheduleRequestId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("scheduleRequest", scheduleRequestId);
        StatusInfo result = getNextDecorator().deleteScheduleRequest(scheduleRequestId, contextInfo);
        getCacheManager().getCache(cacheName).remove(cacheKey);
        return result;
    }


    @Override
    public TimeSlotInfo createTimeSlot(@WebParam(name = "timeSlotTypeKey") String timeSlotTypeKey, @WebParam(name = "timeSlotInfo") TimeSlotInfo timeSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        TimeSlotInfo result = getNextDecorator().createTimeSlot(timeSlotTypeKey, timeSlotInfo, contextInfo);
        MultiKey cacheKey = new MultiKey(TIMESLOT_KEY, result.getId());
        getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        return result;
    }

    @Override
    public StatusInfo deleteTimeSlot(@WebParam(name = "timeSlotId") String timeSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(TIMESLOT_KEY, timeSlotId);
        StatusInfo result = getNextDecorator().deleteTimeSlot(timeSlotId, contextInfo);
        getCacheManager().getCache(cacheName).remove(cacheKey);
        return result;
    }

    @Override
    public TimeSlotInfo getTimeSlot(@WebParam(name = "timeSlotId") String timeSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey(TIMESLOT_KEY, timeSlotId);

        Element cachedResult = getCacheManager().getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getTimeSlot(timeSlotId, contextInfo);
            getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (TimeSlotInfo)result;    }

    @Override
    public List<TimeSlotInfo> getTimeSlotsByIds(@WebParam(name = "timeSlotIds") List<String> timeSlotIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<TimeSlotInfo> timeSlotInfos = new ArrayList<TimeSlotInfo>();
        List<String> timeSlotIdsToSearch = new ArrayList<String>();
        Cache cache = cacheManager.getCache(cacheName);
        //Check the cache for the ids
        for(String timeSlotId : timeSlotIds){
            MultiKey cacheKey = new MultiKey(TIMESLOT_KEY, timeSlotId);
            Element cachedResult = cache.get(cacheKey);
            if (cachedResult != null) {
                //If the id was found in the cache then use that and remove from the list of ids one is searching for
                timeSlotInfos.add((TimeSlotInfo) cachedResult.getValue());
            } else {
                //Otherwise save this id as one that needs to be looked up
                timeSlotIdsToSearch.add(timeSlotId);
            }
        }
        //Call the underlying service to get the remainder
        if (!timeSlotIdsToSearch.isEmpty()) {
            List<TimeSlotInfo> uncachedTimeSlotInfos = getNextDecorator().getTimeSlotsByIds(timeSlotIdsToSearch, contextInfo);
            for (TimeSlotInfo timeSlotInfo : uncachedTimeSlotInfos) {
                MultiKey cacheKey = new MultiKey(TIMESLOT_KEY, timeSlotInfo.getId());
                cache.put(new Element(cacheKey, timeSlotInfo));
            }
            timeSlotInfos.addAll(uncachedTimeSlotInfos);
        }

        return timeSlotInfos;
    }

    @Override
    public TimeSlotInfo updateTimeSlot(@WebParam(name = "timeSlotId") String timeSlotId, @WebParam(name = "timeSlotInfo") TimeSlotInfo timeSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        MultiKey cacheKey = new MultiKey(TIMESLOT_KEY, timeSlotId);
        TimeSlotInfo result = getNextDecorator().updateTimeSlot(timeSlotId, timeSlotInfo, contextInfo);
        getCacheManager().getCache(cacheName).remove(cacheKey);
        getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        return result;
    }

    @Override
    public ScheduleDisplayInfo getScheduleDisplay(String scheduleId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ScheduleInfo scheduleInfo = getSchedule(scheduleId, contextInfo);
        ScheduleDisplayInfo scheduleDisplayInfo =
                ScheduleDisplayTransformer.schedule2scheduleDisplay(scheduleInfo, getAtpService(), getRoomService(), this, contextInfo);
        return scheduleDisplayInfo;
    }

    @Override
    public List<ScheduleDisplayInfo> getScheduleDisplaysByIds(List<String> scheduleIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<ScheduleInfo> scheduleInfoList = getSchedulesByIds(scheduleIds, contextInfo);
        List<ScheduleDisplayInfo> displayInfoList = new ArrayList<ScheduleDisplayInfo>();
        if (scheduleInfoList != null) {
            for (ScheduleInfo info: scheduleInfoList) {
                ScheduleDisplayInfo scheduleDisplayInfo =
                        ScheduleDisplayTransformer.schedule2scheduleDisplay(info, getAtpService(), getRoomService(), this, contextInfo);
                displayInfoList.add(scheduleDisplayInfo);
            }
        }
        return displayInfoList;
    }

    @Override
    public ScheduleRequestDisplayInfo getScheduleRequestDisplay(String scheduleRequestId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        ScheduleRequestInfo scheduleInfo = getScheduleRequest(scheduleRequestId, contextInfo);
        ScheduleRequestDisplayInfo scheduleDisplayInfo =
                ScheduleDisplayTransformer.scheduleRequestInfo2SceduleRequestDisplayInfo(scheduleInfo, getTypeService(), getRoomService(), this, contextInfo);
        return scheduleDisplayInfo;
    }

    @Override
    public List<ScheduleRequestDisplayInfo> getScheduleRequestDisplaysByIds(List<String> scheduleRequestIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ScheduleRequestInfo> scheduleInfoList = getScheduleRequestsByIds(scheduleRequestIds, contextInfo);
        List<ScheduleRequestDisplayInfo> displayInfoList = new ArrayList<ScheduleRequestDisplayInfo>();
        if (scheduleInfoList != null) {
            for (ScheduleRequestInfo info: scheduleInfoList) {
                ScheduleRequestDisplayInfo scheduleDisplayInfo =
                        ScheduleDisplayTransformer.scheduleRequestInfo2SceduleRequestDisplayInfo(info, getTypeService(), getRoomService(), this, contextInfo);
                displayInfoList.add(scheduleDisplayInfo);
            }
        }
        return displayInfoList;
    }

    @Override
    public List<ScheduleRequestInfo> getScheduleRequestsByScheduleRequestSet(String scheduleRequestSetId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return super.getScheduleRequestsByScheduleRequestSet(scheduleRequestSetId, contextInfo);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public CacheManager getCacheManager() {
        if(cacheManager == null){
            cacheManager = CacheManager.getInstance();
        }
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }


    public AtpService getAtpService() {
        if(atpService == null){
            atpService = GlobalResourceLoader.getService(new QName(AtpServiceConstants.NAMESPACE,
                    AtpServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public RoomService getRoomService() {
        if (roomService == null){
            roomService = GlobalResourceLoader.getService(new QName(RoomServiceConstants.NAMESPACE,
                    RoomServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return roomService;
    }

    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return typeService;
    }

    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

}
