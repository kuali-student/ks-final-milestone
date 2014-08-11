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
 * User: jonrcook
 * Date: 8/9/13
 * Time: 10:43 AM
 */
package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.jacorb.imr.Registration;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.class1.lui.service.decorators.LuiServiceDecorator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.enrollment.courseoffering.infc.ActivityOffering;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.BulkStatusInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/*
 * Decorator for CourseOfferingService to add caching to CourseOffering service methods.
 */
public class CourseOfferingServiceCacheDecorator extends CourseOfferingServiceDecorator {

    /* Caching the CourseOfferingInfo objects by id */
    private static String courseOfferingCacheName = "courseOfferingCache";
    private static String activityOfferingCacheName = "activityOfferingCache";
    private static String activityOfferingClusterCacheName = "activityOfferingClusterCache";
    private static String activityOfferingTypeCacheName = "activityOfferingTypeCache";
    private static String formatOfferingCacheName = "formatOfferingCache";
    private static String registrationGroupCacheName = "registrationGroupCache";
    private static String seatPoolCacheName = "seatPoolCache";

    private LuiService luiService;

    private CacheManager cacheManager;

    @Override
    public StatusInfo deleteCourseOfferingCascaded(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        deleteCourseOfferingFromCacheCascaded(courseOfferingId, context);
        StatusInfo result = getNextDecorator().deleteCourseOfferingCascaded(courseOfferingId, context);
        return result;
    }

    private void deleteCourseOfferingFromCacheCascaded(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        getCacheManager().getCache(courseOfferingCacheName).remove(courseOfferingId);

        List<FormatOfferingInfo> fos = getFormatOfferingsByCourseOffering(courseOfferingId, context);
        for (FormatOfferingInfo fo : fos) {
            deleteFormatOfferingFromCacheCascaded(fo.getId(), context);
        }
    }

    @Override
    public StatusInfo deleteFormatOfferingCascaded(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteFormatOfferingCascaded(formatOfferingId, context);
        deleteFormatOfferingFromCacheCascaded(formatOfferingId, context);
        return result;
    }

    private void deleteFormatOfferingFromCacheCascaded(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        getCacheManager().getCache(formatOfferingCacheName).remove(formatOfferingId);

        List<ActivityOfferingInfo> aos = getActivityOfferingsByFormatOffering(formatOfferingId, context);
        for (ActivityOfferingInfo ao : aos) {
            deleteActivityOfferingFromCacheCascaded(ao.getId(), context);
        }
    }

    @Override
    public StatusInfo deleteActivityOfferingCascaded(String activityOfferingId,
                                                     ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteActivityOfferingCascaded(activityOfferingId, context);
        deleteActivityOfferingFromCacheCascaded(activityOfferingId, context);
        return result;
    }

    private void deleteActivityOfferingFromCacheCascaded(String activityOfferingId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        getCacheManager().getCache(activityOfferingCacheName).remove(activityOfferingId);

        List<SeatPoolDefinitionInfo> seatPoolsToDelete = getSeatPoolDefinitionsForActivityOffering(activityOfferingId, context);
        for (SeatPoolDefinitionInfo seatPool : seatPoolsToDelete) {
            deleteSeatPoolDefinitionFromCache(seatPool.getId());
        }

    }

    @Override
    public StatusInfo deleteActivityOfferingClusterCascaded(
            String activityOfferingClusterId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        deleteActivityOfferingClusterFromCacheCascaded(activityOfferingClusterId, contextInfo);
        StatusInfo result = getNextDecorator().deleteActivityOfferingClusterCascaded(activityOfferingClusterId, contextInfo);
        return result;
    }

    private void deleteActivityOfferingClusterFromCacheCascaded(
            String activityOfferingClusterId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        deleteActivityOfferingClusterFromCache(activityOfferingClusterId);

        List<RegistrationGroupInfo> rgInfos =
                getRegistrationGroupsByActivityOfferingCluster(activityOfferingClusterId, contextInfo);
        for (RegistrationGroupInfo rgInfo : rgInfos) {
            deleteRegistrationGroupFromCache(rgInfo.getId());
        }
    }

    @Override
    public RegistrationGroupInfo createRegistrationGroup(String formatOfferingId, String activityOfferingClusterId, String registrationGroupType, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        RegistrationGroupInfo result = getNextDecorator().createRegistrationGroup(formatOfferingId, activityOfferingClusterId, registrationGroupType, registrationGroupInfo, context);
        getCacheManager().getCache(registrationGroupCacheName).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(String seatPoolDefinitionId, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        SeatPoolDefinitionInfo result = getNextDecorator().updateSeatPoolDefinition(seatPoolDefinitionId, seatPoolDefinitionInfo, context);
        getCacheManager().getCache(seatPoolCacheName).put(new Element(seatPoolDefinitionId, result));
        return result;
    }

    @Override
    public RegistrationGroupInfo updateRegistrationGroup(String registrationGroupId, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        RegistrationGroupInfo result = getNextDecorator().updateRegistrationGroup(registrationGroupId, registrationGroupInfo, context);
        getCacheManager().getCache(registrationGroupCacheName).put(new Element(registrationGroupId, result));
        return result;
    }

    @Override
    public FormatOfferingInfo updateFormatOffering(String formatOfferingId, FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        FormatOfferingInfo result = getNextDecorator().updateFormatOffering(formatOfferingId, formatOfferingInfo, context);
        getCacheManager().getCache(formatOfferingCacheName).put(new Element(formatOfferingId, result));
        return result;
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, List<String> optionKeys, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        CourseOfferingInfo result = getNextDecorator().updateCourseOfferingFromCanonical(courseOfferingId, optionKeys, context);
        getCacheManager().getCache(courseOfferingCacheName).put(new Element(courseOfferingId, result));
        return result;
    }

    @Override
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        CourseOfferingInfo result = getNextDecorator().updateCourseOffering(courseOfferingId, courseOfferingInfo, context);
        getCacheManager().getCache(courseOfferingCacheName).put(new Element(courseOfferingId, result));
        return result;
    }

    @Override
    public ActivityOfferingInfo updateActivityOffering(String activityOfferingId, ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        ActivityOfferingInfo result = getNextDecorator().updateActivityOffering(activityOfferingId, activityOfferingInfo, context);
        getCacheManager().getCache(activityOfferingCacheName).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public ActivityOfferingClusterInfo getActivityOfferingCluster(String activityOfferingClusterId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache cache = getCacheManager().getCache(activityOfferingClusterCacheName);
        Element cachedResult = cache.get(activityOfferingClusterId);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getActivityOfferingCluster(activityOfferingClusterId, contextInfo);
            cache.put(new Element(activityOfferingClusterId, result));
        } else {
            result = cachedResult.getValue();
        }
        return (ActivityOfferingClusterInfo) result;
    }

    @Override
    public ActivityOfferingClusterInfo createActivityOfferingCluster(String formatOfferingId,
                                                                     String activityOfferingClusterTypeKey, ActivityOfferingClusterInfo activityOfferingClusterInfo,
                                                                     ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        ActivityOfferingClusterInfo result = getNextDecorator().createActivityOfferingCluster(formatOfferingId, activityOfferingClusterTypeKey,
                activityOfferingClusterInfo, contextInfo);
        getCacheManager().getCache(activityOfferingClusterCacheName).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public ActivityOfferingClusterInfo updateActivityOfferingCluster(String formatOfferingId, String activityOfferingClusterId,
                                                                     ActivityOfferingClusterInfo activityOfferingClusterInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException {
        ActivityOfferingClusterInfo result = getNextDecorator().updateActivityOfferingCluster(formatOfferingId, activityOfferingClusterId,
                activityOfferingClusterInfo, contextInfo);
        getCacheManager().getCache(activityOfferingClusterCacheName).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public RegistrationGroupInfo getRegistrationGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache cache = getCacheManager().getCache(registrationGroupCacheName);
        Element cachedResult = cache.get(registrationGroupId);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getRegistrationGroup(registrationGroupId, context);
            cache.put(new Element(registrationGroupId, result));
        } else {
            result = cachedResult.getValue();
        }
        return (RegistrationGroupInfo) result;
    }

    @Override
    public FormatOfferingInfo getFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache cache = getCacheManager().getCache(formatOfferingCacheName);
        Element cachedResult = cache.get(formatOfferingId);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getFormatOffering(formatOfferingId, context);
            cache.put(new Element(formatOfferingId, result));
        } else {
            result = cachedResult.getValue();
        }
        return (FormatOfferingInfo) result;
    }

    @Override
    public CourseOfferingInfo getCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache cache = getCacheManager().getCache(courseOfferingCacheName);
        Element cachedResult = cache.get(courseOfferingId);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getCourseOffering(courseOfferingId, context);
            cache.put(new Element(courseOfferingId, result));
        } else {
            result = cachedResult.getValue();
        }
        return (CourseOfferingInfo) result;
    }

    @Override
    public List<ActivityOfferingClusterInfo> getActivityOfferingClustersByIds(List<String> activityOfferingClusterIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ActivityOfferingClusterInfo> result = new ArrayList<ActivityOfferingClusterInfo>();
        for (String id : activityOfferingClusterIds) {
            result.add(getActivityOfferingCluster(id, context));
        }
        return result;
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(List<String> registrationGroupIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<RegistrationGroupInfo> result = new ArrayList<RegistrationGroupInfo>();
        for (String id : registrationGroupIds) {
            result.add(getRegistrationGroup(id, context));
        }
        return result;

    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIds(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CourseOfferingInfo> result = new ArrayList<CourseOfferingInfo>();
        for (String id : courseOfferingIds) {
            result.add(getCourseOffering(id, context));
        }
        return result;
    }

    @Override
    public List<CourseOfferingDisplayInfo> getCourseOfferingDisplaysByIds(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<CourseOfferingDisplayInfo> result = new ArrayList<CourseOfferingDisplayInfo>();
        for (String id : courseOfferingIds) {
            result.add(getCourseOfferingDisplay(id, context));
        }
        return result;
    }

    @Override
    public ActivityOfferingDisplayInfo getActivityOfferingDisplay(String activityOfferingId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Cache cache = getCacheManager().getCache(activityOfferingCacheName);
        MultiKey cacheKey = new MultiKey("ActivityOfferingDisplay", activityOfferingId);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getActivityOfferingDisplay(activityOfferingId, contextInfo);
            cache.put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }
        return (ActivityOfferingDisplayInfo) result;
    }

    @Override
    public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysByIds(List<String> activityOfferingIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ActivityOfferingDisplayInfo> result = new ArrayList<ActivityOfferingDisplayInfo>();
        for (String id : activityOfferingIds) {
            result.add(getActivityOfferingDisplay(id, contextInfo));
        }
        return result;
    }

    @Override
    public ActivityOfferingInfo getActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Cache cache = getCacheManager().getCache(activityOfferingCacheName);
        Element cachedResult = cache.get(activityOfferingId);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getActivityOffering(activityOfferingId, context);
            cache.put(new Element(activityOfferingId, result));
        } else {
            result = cachedResult.getValue();
        }
        return (ActivityOfferingInfo) result;
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(List<String> activityOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<ActivityOfferingInfo> result = new ArrayList<ActivityOfferingInfo>();
        for (String id : activityOfferingIds) {
            result.add(getActivityOffering(id, context));
        }
        return result;
    }

    @Override
    public TypeInfo getActivityOfferingType(String activityOfferingTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache cache = getCacheManager().getCache(activityOfferingTypeCacheName);
        Element cachedResult = cache.get(activityOfferingTypeKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getActivityOfferingType(activityOfferingTypeKey, context);
            cache.put(new Element(activityOfferingTypeKey, result));
        } else {
            result = cachedResult.getValue();
        }
        return (TypeInfo) result;
    }

    @Override
    public StatusInfo deleteSeatPoolDefinition(String seatPoolDefinitionId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteSeatPoolDefinition(seatPoolDefinitionId, context);
        deleteSeatPoolDefinitionFromCache(seatPoolDefinitionId);
        return result;
    }

    private void deleteSeatPoolDefinitionFromCache(String seatPoolDefinitionId) {
        getCacheManager().getCache(seatPoolCacheName).remove(seatPoolDefinitionId);
    }

    @Override
    public StatusInfo deleteActivityOfferingCluster(String activityOfferingClusterId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        StatusInfo result = getNextDecorator().deleteActivityOfferingCluster(activityOfferingClusterId, context);
        deleteActivityOfferingClusterFromCache(activityOfferingClusterId);
        return result;
    }

    private void deleteActivityOfferingClusterFromCache(String activityOfferingClusterId) {
        getCacheManager().getCache(activityOfferingClusterCacheName).remove(activityOfferingClusterId);
    }

    @Override
    public StatusInfo deleteRegistrationGroup(String registrationGroupId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteRegistrationGroup(registrationGroupId, context);
        deleteRegistrationGroupFromCache(registrationGroupId);
        return result;
    }

    private void deleteRegistrationGroupFromCache(String registrationGroupId) {
        getCacheManager().getCache(registrationGroupCacheName).remove(registrationGroupId);
    }

    @Override
    public StatusInfo deleteFormatOffering(String formatOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        StatusInfo result = getNextDecorator().deleteFormatOffering(formatOfferingId, context);
        getCacheManager().getCache(formatOfferingCacheName).remove(formatOfferingId);
        return result;
    }

    @Override
    public StatusInfo deleteCourseOffering(String courseOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        StatusInfo result = getNextDecorator().deleteCourseOffering(courseOfferingId, context);
        getCacheManager().getCache(courseOfferingCacheName).remove(courseOfferingId);
        return result;
    }

    @Override
    public StatusInfo deleteActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DependentObjectsExistException {
        StatusInfo result = getNextDecorator().deleteActivityOffering(activityOfferingId, context);
        getCacheManager().getCache(activityOfferingCacheName).remove(activityOfferingId);
        return result;
    }

    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        SeatPoolDefinitionInfo result = getNextDecorator().createSeatPoolDefinition(seatPoolDefinitionInfo, context);
        getCacheManager().getCache(seatPoolCacheName).put(new Element(result.getId(), result));
        return result;
    }


    @Override
    public FormatOfferingInfo createFormatOffering(String courseOfferingId, String formatId, String formatOfferingType, FormatOfferingInfo formatOfferingInfo, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        FormatOfferingInfo result = getNextDecorator().createFormatOffering(courseOfferingId, formatId, formatOfferingType, formatOfferingInfo, context);
        getCacheManager().getCache(formatOfferingCacheName).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public CourseOfferingInfo createCourseOffering(String courseId, String termId, String courseOfferingTypeKey,
                                                   CourseOfferingInfo courseOfferingInfo, List<String> optionKeys, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        CourseOfferingInfo result = getNextDecorator().createCourseOffering(courseId, termId, courseOfferingTypeKey, courseOfferingInfo, optionKeys, context);
        getCacheManager().getCache(courseOfferingCacheName).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public ActivityOfferingInfo createActivityOffering(String formatOfferingId,
                                                       String activityId,
                                                       String activityOfferingTypeKey,
                                                       ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ActivityOfferingInfo result = getNextDecorator().createActivityOffering(formatOfferingId, activityId, activityOfferingTypeKey, activityOfferingInfo, context);
        getCacheManager().getCache(activityOfferingCacheName).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public ActivityOfferingInfo copyActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ActivityOfferingInfo result = getNextDecorator().copyActivityOffering(activityOfferingId, context);
        getCacheManager().getCache(activityOfferingCacheName).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public StatusInfo addSeatPoolDefinitionToActivityOffering(
            String seatPoolDefinitionId, String activityOfferingId,
            ContextInfo contextInfo) throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        StatusInfo result = getNextDecorator().addSeatPoolDefinitionToActivityOffering(seatPoolDefinitionId, activityOfferingId, contextInfo);
        getCacheManager().getCache(activityOfferingCacheName).remove(activityOfferingId);//remove from cache so it has to be reloaded
        return result;
    }

    @Override
    public StatusInfo removeSeatPoolDefinitionFromActivityOffering(
            String seatPoolDefinitionId, String activityOfferingId,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().removeSeatPoolDefinitionFromActivityOffering(seatPoolDefinitionId, activityOfferingId, contextInfo);
        getCacheManager().getCache(activityOfferingCacheName).remove(activityOfferingId);//remove from cache so it has to be reloaded
        return result;
    }


    @Override
    public StatusInfo changeCourseOfferingState(
            String courseOfferingId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        StatusInfo result = getNextDecorator().changeCourseOfferingState(courseOfferingId, nextStateKey, contextInfo);
        getCacheManager().getCache(courseOfferingCacheName).remove(courseOfferingId); //remove from cache so it has to be reloaded
        return result;
    }

    @Override
    public StatusInfo changeFormatOfferingState(
            String formatOfferingId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        StatusInfo result = getNextDecorator().changeFormatOfferingState(formatOfferingId, nextStateKey, contextInfo);
        getCacheManager().getCache(formatOfferingCacheName).remove(formatOfferingId);//remove from cache so it has to be reloaded
        return result;
    }

    @Override
    public StatusInfo changeActivityOfferingState(
            String activityOfferingId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        StatusInfo result = getNextDecorator().changeActivityOfferingState(activityOfferingId, nextStateKey, contextInfo);
        getCacheManager().getCache(activityOfferingCacheName).remove(activityOfferingId);//remove from cache so it has to be reloaded
        return result;
    }

    @Override
    public StatusInfo changeRegistrationGroupState(
            String registrationGroupId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        StatusInfo result = getNextDecorator().changeRegistrationGroupState(registrationGroupId, nextStateKey, contextInfo);
        getCacheManager().getCache(registrationGroupCacheName).remove(registrationGroupId);//remove from cache so it has to be reloaded
        return result;
    }

    @Override
    public StatusInfo changeActivityOfferingClusterState(
            String activityOfferingClusterId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        StatusInfo result = getNextDecorator().changeActivityOfferingClusterState(activityOfferingClusterId, nextStateKey, contextInfo);
        getCacheManager().getCache(activityOfferingClusterCacheName).remove(activityOfferingClusterId);//remove from cache so it has to be reloaded
        return result;
    }

    @Override
    public StatusInfo changeSeatPoolDefinitionState(
            String seatPoolDefinitionId,
            String nextStateKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        StatusInfo result = getNextDecorator().changeSeatPoolDefinitionState(seatPoolDefinitionId, nextStateKey, contextInfo);
        getCacheManager().getCache(seatPoolCacheName).remove(seatPoolDefinitionId);//remove from cache so it has to be reloaded
        return result;
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByFormatOffering(String formatOfferingId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // By retrieving the reg group ids for the format offering directly on the luiservice we can skip the registrationGroupTransformer
        // in the course offering service, and return the registration groups that are already in the cache instead.
        List<String> regGroupIds = luiService.getLuiIdsByLuiAndRelationType(formatOfferingId, LuiServiceConstants.LUI_LUI_RELATION_DELIVERED_VIA_FO_TO_RG_TYPE_KEY, context);

        List<RegistrationGroupInfo> result = new ArrayList<RegistrationGroupInfo>();
        for (String id : regGroupIds) {
            // Look for reg group in the cache before retrieving from db.
            result.add(getRegistrationGroup(id, context));
        }
        return result;
    }

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public CacheManager getCacheManager() {
        if (cacheManager == null) {
            cacheManager = CacheManager.getInstance();
        }
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

}
