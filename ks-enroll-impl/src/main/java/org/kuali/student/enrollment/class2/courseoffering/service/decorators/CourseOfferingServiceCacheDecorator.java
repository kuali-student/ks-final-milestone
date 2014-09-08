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

import java.util.ArrayList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.cache.KSCacheUtils;
import org.kuali.student.common.cache.KSCacheUtils.BulkCacheElementLoader;
import org.kuali.student.common.cache.KSCacheUtils.SingleCacheElementLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
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
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeInfoCopier;

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

    private CacheManager cacheManager;
    
    private ActivityOfferingInfoCopier aoCopier = new ActivityOfferingInfoCopier();
    private ActivityOfferingDisplayInfoCopier aodCopier = new ActivityOfferingDisplayInfoCopier();
    private CourseOfferingInfoCopier coCopier = new CourseOfferingInfoCopier();
    private FormatOfferingInfoCopier foCopier = new FormatOfferingInfoCopier();
    private ActivityOfferingClusterInfoCopier aocCopier = new ActivityOfferingClusterInfoCopier();
    private RegistrationGroupInfoCopier rgCopier = new RegistrationGroupInfoCopier();
    private SeatPoolDefinitionInfoCopier seatPoolCopier = new SeatPoolDefinitionInfoCopier ();
    
    

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
        
    	deleteActivityOfferingFromCache (activityOfferingId, context);

        List<SeatPoolDefinitionInfo> seatPoolsToDelete = getSeatPoolDefinitionsForActivityOffering(activityOfferingId, context);
        for (SeatPoolDefinitionInfo seatPool : seatPoolsToDelete) {
            deleteSeatPoolDefinitionFromCache(seatPool.getId());
        }

    }

    private void deleteActivityOfferingFromCache(String activityOfferingId,
			ContextInfo context) {
    	
    	getCacheManager().getCache(activityOfferingCacheName).remove(activityOfferingId);
        
        MultiKey aoDisplayKey = new MultiKey("ActivityOfferingDisplay", activityOfferingId);
        
        getCacheManager().getCache(activityOfferingCacheName).remove(aoDisplayKey);
        
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
        
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(registrationGroupCacheName), result.getId(), result, rgCopier);
        
        return rgCopier.deepCopy(result);
    }

    @Override
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(String seatPoolDefinitionId, SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        SeatPoolDefinitionInfo result = getNextDecorator().updateSeatPoolDefinition(seatPoolDefinitionId, seatPoolDefinitionInfo, context);
        
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(seatPoolCacheName), seatPoolDefinitionId, result, seatPoolCopier);
        
        return seatPoolCopier.deepCopy(result);
    }

    @Override
    public RegistrationGroupInfo updateRegistrationGroup(String registrationGroupId, RegistrationGroupInfo registrationGroupInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        RegistrationGroupInfo result = getNextDecorator().updateRegistrationGroup(registrationGroupId, registrationGroupInfo, context);
        
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(registrationGroupCacheName), registrationGroupId, result, rgCopier);
        
        return rgCopier.deepCopy(result);
    }

    @Override
    public FormatOfferingInfo updateFormatOffering(String formatOfferingId, FormatOfferingInfo formatOfferingInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
    	
        FormatOfferingInfo result = getNextDecorator().updateFormatOffering(formatOfferingId, formatOfferingInfo, context);
        
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(formatOfferingCacheName), formatOfferingId, result, foCopier);
        
        return foCopier.deepCopy(result);
        
    }

    @Override
    public CourseOfferingInfo updateCourseOfferingFromCanonical(String courseOfferingId, List<String> optionKeys, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        CourseOfferingInfo result = getNextDecorator().updateCourseOfferingFromCanonical(courseOfferingId, optionKeys, context);
        
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(courseOfferingCacheName), courseOfferingId, result, coCopier);
        
        return coCopier.deepCopy(result);
    }

    @Override
    public CourseOfferingInfo updateCourseOffering(String courseOfferingId, CourseOfferingInfo courseOfferingInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        CourseOfferingInfo result = getNextDecorator().updateCourseOffering(courseOfferingId, courseOfferingInfo, context);
        
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(courseOfferingCacheName), courseOfferingId, result, coCopier);
        
        return coCopier.deepCopy(result);
        
    }

    @Override
    public ActivityOfferingInfo updateActivityOffering(String activityOfferingId, ActivityOfferingInfo activityOfferingInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        ActivityOfferingInfo result = getNextDecorator().updateActivityOffering(activityOfferingId, activityOfferingInfo, context);
        
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(activityOfferingCacheName), result.getId(), result, aoCopier);
        
        return aoCopier.deepCopy(result);
    }

    @Override
    public ActivityOfferingClusterInfo getActivityOfferingCluster(String activityOfferingClusterId, final ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache cache = getCacheManager().getCache(activityOfferingClusterCacheName);
        
        return KSCacheUtils.cacheAwareLoad(cache, activityOfferingClusterId, aocCopier, new SingleCacheElementLoader<ActivityOfferingClusterInfo>() {

			@Override
			public ActivityOfferingClusterInfo load(String key)
					throws DoesNotExistException, OperationFailedException,
					InvalidParameterException, MissingParameterException,
					PermissionDeniedException {
				return getNextDecorator().getActivityOfferingCluster(key, contextInfo);
			}
		
        });
        
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
        
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(activityOfferingClusterCacheName), result.getId(), result, aocCopier);
        
        return aocCopier.deepCopy(result);
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
        
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(activityOfferingClusterCacheName), result.getId(), result, aocCopier);
        
        return aocCopier.deepCopy(result);
        
    }

    @Override
    public RegistrationGroupInfo getRegistrationGroup(String registrationGroupId, final ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache cache = getCacheManager().getCache(registrationGroupCacheName);
        
        return KSCacheUtils.cacheAwareLoad(cache, registrationGroupId, rgCopier, new SingleCacheElementLoader<RegistrationGroupInfo>() {

			@Override
			public RegistrationGroupInfo load(String key)
					throws DoesNotExistException, OperationFailedException,
					InvalidParameterException, MissingParameterException,
					PermissionDeniedException {
				return getNextDecorator().getRegistrationGroup(key, context);
			}
        	
		});
        
    }

    @Override
    public FormatOfferingInfo getFormatOffering(String formatOfferingId, final ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache cache = getCacheManager().getCache(formatOfferingCacheName);
        return KSCacheUtils.cacheAwareLoad(cache, formatOfferingId, foCopier, new SingleCacheElementLoader<FormatOfferingInfo>() {

			@Override
			public FormatOfferingInfo load(String key)
					throws DoesNotExistException, OperationFailedException,
					InvalidParameterException, MissingParameterException,
					PermissionDeniedException {
				return getNextDecorator().getFormatOffering(key, context);
			}
        	
		});
    }

    @Override
    public CourseOfferingInfo getCourseOffering(String courseOfferingId, final ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache cache = getCacheManager().getCache(courseOfferingCacheName);
        
        return KSCacheUtils.cacheAwareLoad(cache, courseOfferingId, coCopier, new SingleCacheElementLoader<CourseOfferingInfo>() {

			@Override
			public CourseOfferingInfo load(String id)
					throws DoesNotExistException, OperationFailedException,
					InvalidParameterException, MissingParameterException,
					PermissionDeniedException {
				return getNextDecorator().getCourseOffering(id, context);
			}
        	
		});
        
    }

    @Override
    public List<ActivityOfferingClusterInfo> getActivityOfferingClustersByIds(List<String> activityOfferingClusterIds, final ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        Cache cache = getCacheManager().getCache(activityOfferingClusterCacheName);
        
        return KSCacheUtils.cacheAwareBulkLoad(cache, activityOfferingClusterIds, aocCopier, new BulkCacheElementLoader<ActivityOfferingClusterInfo>() {

			@Override
			public List<ActivityOfferingClusterInfo> load(List<String> cacheMissIds)
					throws DoesNotExistException, OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException {
				
					return getNextDecorator().getActivityOfferingClustersByIds(cacheMissIds, context);
			}
        	
		});
    }

    @Override
    public List<RegistrationGroupInfo> getRegistrationGroupsByIds(List<String> registrationGroupIds, final ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Cache cache = getCacheManager().getCache(registrationGroupCacheName);
        
        return KSCacheUtils.cacheAwareBulkLoad(cache, registrationGroupIds, rgCopier, new BulkCacheElementLoader<RegistrationGroupInfo>() {

			@Override
			public List<RegistrationGroupInfo> load(List<String> cacheMissIds)
					throws DoesNotExistException, OperationFailedException,
					InvalidParameterException, MissingParameterException,
					PermissionDeniedException {
				return getNextDecorator().getRegistrationGroupsByIds(cacheMissIds, context);
			}
        	
		});
        
    }

    @Override
    public List<CourseOfferingInfo> getCourseOfferingsByIds(List<String> courseOfferingIds, final ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
        Cache cache = getCacheManager().getCache(courseOfferingCacheName);
        
        return KSCacheUtils.cacheAwareBulkLoad(cache, courseOfferingIds, coCopier, new BulkCacheElementLoader<CourseOfferingInfo>() {

			@Override
			public List<CourseOfferingInfo> load(List<String> cacheMissIds)
					throws DoesNotExistException, OperationFailedException,
					InvalidParameterException, MissingParameterException,
					PermissionDeniedException {
				return getNextDecorator().getCourseOfferingsByIds(cacheMissIds, context);
			}
        	
		});
    }

    @Override
    public List<CourseOfferingDisplayInfo> getCourseOfferingDisplaysByIds(List<String> courseOfferingIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

    	  return getNextDecorator().getCourseOfferingDisplaysByIds(courseOfferingIds, context);
    	  
    	  // TODO KSENROLL-14538 for now delegating to the next decorator is more efficient.
    	  
//        List<CourseOfferingDisplayInfo> result = new ArrayList<CourseOfferingDisplayInfo>();
//        for (String id : courseOfferingIds) {
//            result.add(getCourseOfferingDisplay(id, context));
//        }
//        return result;
    }

    @Override
    public ActivityOfferingDisplayInfo getActivityOfferingDisplay(String activityOfferingId, final ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Cache cache = getCacheManager().getCache(activityOfferingCacheName);
        
        MultiKey cacheKey = new MultiKey("ActivityOfferingDisplay", activityOfferingId);

        return KSCacheUtils.cacheAwareLoad(cache, activityOfferingId, cacheKey, aodCopier, new SingleCacheElementLoader<ActivityOfferingDisplayInfo>() {

			@Override
			public ActivityOfferingDisplayInfo load(String id)
					throws DoesNotExistException, OperationFailedException,
					InvalidParameterException, MissingParameterException,
					PermissionDeniedException {
				return getNextDecorator().getActivityOfferingDisplay(id, contextInfo);
			}
        	
		});
        
    }

    @Override
    public List<ActivityOfferingDisplayInfo> getActivityOfferingDisplaysByIds(List<String> activityOfferingIds, final ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        
    	Cache cache = getCacheManager().getCache(activityOfferingCacheName);
    	
    	return KSCacheUtils.cacheAwareBulkLoad(cache, activityOfferingIds, aodCopier, new BulkCacheElementLoader<ActivityOfferingDisplayInfo>() {

			@Override
			public List<ActivityOfferingDisplayInfo> load(
					List<String> cacheMissKeys) throws DoesNotExistException,
					OperationFailedException, InvalidParameterException,
					MissingParameterException, PermissionDeniedException {
				return getNextDecorator().getActivityOfferingDisplaysByIds(cacheMissKeys, contextInfo);
			}
    		
		});
    }

    @Override
    public ActivityOfferingInfo getActivityOffering(String activityOfferingId, final ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Cache cache = getCacheManager().getCache(activityOfferingCacheName);
        
        return KSCacheUtils.cacheAwareLoad(cache, activityOfferingId, aoCopier, new SingleCacheElementLoader<ActivityOfferingInfo>() {

			@Override
			public ActivityOfferingInfo load(String id)
					throws DoesNotExistException, OperationFailedException,
					InvalidParameterException, MissingParameterException,
					PermissionDeniedException {
				return getNextDecorator().getActivityOffering(id, context);
			}
		
        });
       
    }

    @Override
    public List<ActivityOfferingInfo> getActivityOfferingsByIds(List<String> activityOfferingIds, final ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Cache cache = getCacheManager().getCache(activityOfferingCacheName);
        
        return KSCacheUtils.cacheAwareBulkLoad(cache, activityOfferingIds, aoCopier, new BulkCacheElementLoader<ActivityOfferingInfo>() {

			@Override
			public List<ActivityOfferingInfo> load(List<String> cacheMissIds)
					throws DoesNotExistException, OperationFailedException, InvalidParameterException, MissingParameterException, PermissionDeniedException {
				
					return getNextDecorator().getActivityOfferingsByIds(cacheMissIds, context);
			}
        	
		});
        
       
    }

    @Override
    public TypeInfo getActivityOfferingType(String activityOfferingTypeKey, final ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache cache = getCacheManager().getCache(activityOfferingTypeCacheName);
        
        return KSCacheUtils.cacheAwareLoad(cache, activityOfferingTypeKey, new TypeInfoCopier(), new SingleCacheElementLoader<TypeInfo>() {

			@Override
			public TypeInfo load(String key) throws DoesNotExistException,
					OperationFailedException, InvalidParameterException,
					MissingParameterException, PermissionDeniedException {
				return getNextDecorator().getActivityOfferingType(key, context);
			}
		
        	
        });
       
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
        
        deleteActivityOfferingFromCache(activityOfferingId, context);
        
        return result;
    }

    @Override
    public SeatPoolDefinitionInfo createSeatPoolDefinition(SeatPoolDefinitionInfo seatPoolDefinitionInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        SeatPoolDefinitionInfo result = getNextDecorator().createSeatPoolDefinition(seatPoolDefinitionInfo, context);
        
        KSCacheUtils.updateCacheElement( getCacheManager().getCache(seatPoolCacheName), result.getId(), result, seatPoolCopier);
        
        return seatPoolCopier.deepCopy(result);
    }


    @Override
    public FormatOfferingInfo createFormatOffering(String courseOfferingId, String formatId, String formatOfferingType, FormatOfferingInfo formatOfferingInfo, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        FormatOfferingInfo result = getNextDecorator().createFormatOffering(courseOfferingId, formatId, formatOfferingType, formatOfferingInfo, context);
        
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(formatOfferingCacheName), result.getId(), result, foCopier);
        
        return foCopier.deepCopy(result);
    }

    @Override
    public CourseOfferingInfo createCourseOffering(String courseId, String termId, String courseOfferingTypeKey,
                                                   CourseOfferingInfo courseOfferingInfo, List<String> optionKeys, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        CourseOfferingInfo result = getNextDecorator().createCourseOffering(courseId, termId, courseOfferingTypeKey, courseOfferingInfo, optionKeys, context);
        
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(courseOfferingCacheName), result.getId(), result, coCopier);
        
        return coCopier.deepCopy(result);
    }

    @Override
    public ActivityOfferingInfo createActivityOffering(String formatOfferingId,
                                                       String activityId,
                                                       String activityOfferingTypeKey,
                                                       ActivityOfferingInfo activityOfferingInfo, ContextInfo context)
            throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
    	
        ActivityOfferingInfo result = getNextDecorator().createActivityOffering(formatOfferingId, activityId, activityOfferingTypeKey, activityOfferingInfo, context);
        
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(activityOfferingCacheName), result.getId(), result, aoCopier);
        
        return aoCopier.deepCopy(result);
    }

    @Override
    public ActivityOfferingInfo copyActivityOffering(String activityOfferingId, ContextInfo context) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        ActivityOfferingInfo result = getNextDecorator().copyActivityOffering(activityOfferingId, context);
 
        KSCacheUtils.updateCacheElement(getCacheManager().getCache(activityOfferingCacheName), result.getId(), result, aoCopier);
        
        return aoCopier.deepCopy(result);
    }

    @Override
    public StatusInfo addSeatPoolDefinitionToActivityOffering(
            String seatPoolDefinitionId, String activityOfferingId,
            ContextInfo contextInfo) throws AlreadyExistsException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        StatusInfo result = getNextDecorator().addSeatPoolDefinitionToActivityOffering(seatPoolDefinitionId, activityOfferingId, contextInfo);
        deleteActivityOfferingFromCache(activityOfferingId, contextInfo);//remove from cache so it has to be reloaded
        return result;
    }

    @Override
    public StatusInfo removeSeatPoolDefinitionFromActivityOffering(
            String seatPoolDefinitionId, String activityOfferingId,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().removeSeatPoolDefinitionFromActivityOffering(seatPoolDefinitionId, activityOfferingId, contextInfo);
        deleteActivityOfferingFromCache(activityOfferingId, contextInfo);//remove from cache so it has to be reloaded
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
        deleteActivityOfferingFromCache(activityOfferingId, contextInfo);//remove from cache so it has to be reloaded
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

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingServiceConstants.PREDICATE_PATH_FOR_FORMATOFFERINGID, formatOfferingId));

        List<String> regGroupIds = getNextDecorator().searchForRegistrationGroupIds(qbcBuilder.build(), context);

        List<RegistrationGroupInfo> result = getRegistrationGroupsByIds(regGroupIds, context);

        return result;
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
