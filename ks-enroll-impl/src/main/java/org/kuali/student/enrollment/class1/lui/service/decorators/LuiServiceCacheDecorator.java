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
package org.kuali.student.enrollment.class1.lui.service.decorators;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;

import org.kuali.student.common.cache.KSCacheUtils;
import org.kuali.student.common.cache.KSCacheUtils.BulkCacheElementLoader;
import org.kuali.student.common.cache.KSCacheUtils.SingleCacheElementLoader;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

/*
 * Decorator for LuiService to add caching to lui service methods.
 */
public class LuiServiceCacheDecorator extends LuiServiceDecorator {

    /* Caching the LuiInfo objects by id */
    private static String luiCacheName = "luiCache";

    /* Caching the LuiLuiRelationInfo objects by id */
    private static String luiLuiCacheName = "luiLuiRelationCache";

    private CacheManager cacheManager;

    @Override
    public LuiInfo getLui(String luiId, final ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache luiCache = getCacheManager().getCache(luiCacheName);
        
        return KSCacheUtils.cacheAwareLoad(luiCache, luiId, new SingleCacheElementLoader<LuiInfo>() {

			@Override
			public LuiInfo load(String key) throws DoesNotExistException,
					OperationFailedException, InvalidParameterException,
					MissingParameterException, PermissionDeniedException {
				return getNextDecorator().getLui(key, contextInfo);
			}
        	
		});
        
    }

    @Override
    public List<LuiInfo> getLuisByIds(List<String> luiIds, final ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
    	
    	Cache luiCache = getCacheManager().getCache(luiCacheName);
    	 
        return KSCacheUtils.cacheAwareBulkLoad(luiCache, luiIds, new BulkCacheElementLoader<LuiInfo>() {

			@Override
			public List<LuiInfo> load(List<String> cacheMissKeys)
					throws DoesNotExistException, OperationFailedException,
					InvalidParameterException, MissingParameterException,
					PermissionDeniedException {
				return getNextDecorator().getLuisByIds(cacheMissKeys, contextInfo);
			}
		
        });
    }

    @Override
    public List<LuiInfo> getLuisByAtpAndType(String atpId, String luiTypeKey, ContextInfo contextInfo) throws
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> luiIds = getNextDecorator().getLuiIdsByAtpAndType(atpId, luiTypeKey, contextInfo);
        
        try {
			return getLuisByIds(luiIds, contextInfo);
		} catch (DoesNotExistException e) {
			throw new OperationFailedException("Failed to do actual load of luis by atp("+atpId+") and lui type ("+luiTypeKey+")", e);
		}
    }

    @Override
    public LuiInfo createLui(String luiId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        LuiInfo result = getNextDecorator().createLui(luiId, atpId, luiTypeKey, luiInfo, contextInfo);
        getCacheManager().getCache(luiCacheName).put(new Element(luiId, result));
        return result;
    }

    @Override
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        LuiInfo result = getNextDecorator().updateLui(luiId, luiInfo, contextInfo);
        getCacheManager().getCache(luiCacheName).put(new Element(luiId, result));
        return result;
    }

    @Override
    public StatusInfo deleteLui(String luiId, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteLui(luiId, contextInfo);
        getCacheManager().getCache(luiCacheName).remove(luiId);

        // Remove luilui relationship for lui Id.
        Cache luiluiCache = getCacheManager().getCache(luiLuiCacheName);
        Query query = luiluiCache.createQuery().includeKeys();
        query.addCriteria(luiluiCache.getSearchAttribute("luiId").eq(luiId));

        Results results = query.execute();
        for (Result queryResult : results.all()) {
            luiluiCache.remove(queryResult.getKey());
        }

        return result;
    }

    @Override
    public List<LuiInfo> getLuisByRelatedLuiAndRelationType(String relatedLuiId,
                                                            String luiLuiRelationTypeKey,
                                                            ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<String> luiIds = this.getNextDecorator().getLuiIdsByRelatedLuiAndRelationType(relatedLuiId, luiLuiRelationTypeKey, context);
        
        try {
        	return getLuisByIds(luiIds, context);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e);
        }

    }

    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, final ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        Cache luiCache = getCacheManager().getCache(luiLuiCacheName);
        
        return KSCacheUtils.cacheAwareLoad(luiCache, luiLuiRelationId, new SingleCacheElementLoader<LuiLuiRelationInfo>() {

			@Override
			public LuiLuiRelationInfo load(String key)
					throws DoesNotExistException, OperationFailedException,
					InvalidParameterException, MissingParameterException,
					PermissionDeniedException {
				return getNextDecorator().getLuiLuiRelation(key, contextInfo);
			}
		
        });
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIds(List<String> luiLuiRelationIds,
                                                            final ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        Cache cache = getCacheManager().getCache(luiLuiCacheName);
        
        return KSCacheUtils.cacheAwareBulkLoad(cache, luiLuiRelationIds, new BulkCacheElementLoader<LuiLuiRelationInfo>() {

			@Override
			public List<LuiLuiRelationInfo> load(List<String> cacheMissKeys)
					throws DoesNotExistException, OperationFailedException,
					InvalidParameterException, MissingParameterException,
					PermissionDeniedException {
				return getNextDecorator().getLuiLuiRelationsByIds(cacheMissKeys, context);
			}
		
        });
    }

    @Override
    public List<LuiInfo> getRelatedLuisByLuiAndRelationType(String luiId,
                                                            String luiLuiRelationTypeKey,
                                                            ContextInfo context)
            throws OperationFailedException, MissingParameterException, InvalidParameterException, PermissionDeniedException {

        List<String> luiIds = getNextDecorator().getLuiIdsByLuiAndRelationType(luiId, luiLuiRelationTypeKey, context);
        try {
           return getLuisByIds(luiIds, context);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e);
        }

    }

    @Override
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteLuiLuiRelation(luiLuiRelationId, contextInfo);
        getCacheManager().getCache(luiLuiCacheName).remove(luiLuiRelationId);
        return result;
    }

    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luiLuiRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        LuiLuiRelationInfo result = getNextDecorator().createLuiLuiRelation(luiId, relatedLuiId, luiLuiRelationTypeKey, luiLuiRelationInfo, contextInfo);
        getCacheManager().getCache(luiLuiCacheName).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {

        LuiLuiRelationInfo result = getNextDecorator().updateLuiLuiRelation(luiLuiRelationId, luiLuiRelationInfo, contextInfo);
        getCacheManager().getCache(luiLuiCacheName).put(new Element(result.getId(), result));
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
