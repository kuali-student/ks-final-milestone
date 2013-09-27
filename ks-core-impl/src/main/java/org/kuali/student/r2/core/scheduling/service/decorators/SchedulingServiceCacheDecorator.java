package org.kuali.student.r2.core.scheduling.service.decorators;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
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
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleRequestInfo;

import javax.jws.WebParam;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 9/26/13
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class SchedulingServiceCacheDecorator extends SchedulingServiceDecorator {
    private static String cacheName = "scheduleCache";
    private CacheManager cacheManager;

    //TODO KSENROLL-8513 add getScheduleRequestsByScheduleRequestSet it is used frequently

    @Override
    public ScheduleInfo createSchedule(@WebParam(name = "scheduleTypeKey") String scheduleTypeKey, @WebParam(name = "scheduleInfo") ScheduleInfo scheduleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ScheduleInfo result = getNextDecorator().createSchedule(scheduleTypeKey, scheduleInfo, contextInfo);
        MultiKey cacheKey = new MultiKey("schedule", result.getId());
        getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        return result;
    }

    @Override
    public ScheduleRequestInfo createScheduleRequest(@WebParam(name = "scheduleRequestTypeKey") String scheduleRequestTypeKey, @WebParam(name = "scheduleRequestInfo") ScheduleRequestInfo scheduleRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ScheduleRequestInfo result = getNextDecorator().createScheduleRequest(scheduleRequestTypeKey, scheduleRequestInfo, contextInfo);
        MultiKey cacheKey = new MultiKey("scheduleRequest", result.getId());
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

    public CacheManager getCacheManager() {
        if(cacheManager == null){
            cacheManager = CacheManager.getInstance();
        }
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
