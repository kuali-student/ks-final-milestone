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

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;

import java.util.ArrayList;
import java.util.List;

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
    public LuiInfo getLui(String luiId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Cache luiCache = getCacheManager().getCache(luiCacheName);
        Element cachedResult = luiCache.get(luiId);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLui(luiId, contextInfo);
            luiCache.put(new Element(luiId, result));
        } else {
            result = cachedResult.getValue();
        }

        return (LuiInfo) result;
    }

    @Override
    public List<LuiInfo> getLuisByIds(List<String> luiIds, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<LuiInfo> luiInfos = new ArrayList<LuiInfo>();
        for (String id : luiIds) {
            luiInfos.add(getLui(id, contextInfo));
        }
        return luiInfos;
    }

    @Override
    public List<LuiInfo> getLuisByAtpAndType(String atpId, String luiTypeKey, ContextInfo contextInfo) throws
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> luiIds = getNextDecorator().getLuiIdsByAtpAndType(atpId, luiTypeKey, contextInfo);
        try {
            // Look for LuiInfos on cache decorator.
            return getLuisByIds(luiIds, contextInfo);
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e);
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
        List<LuiInfo> infoList = new ArrayList<LuiInfo>();
        try {
            for (String luiId : luiIds) {
                infoList.add(this.getLui(luiId, context));
            }
        } catch (DoesNotExistException e) {
            throw new OperationFailedException(e);
        }

        return infoList;
    }

    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        Cache luiCache = getCacheManager().getCache(luiLuiCacheName);
        Element cachedResult = luiCache.get(luiLuiRelationId);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiLuiRelation(luiLuiRelationId, contextInfo);
            luiCache.put(new Element(luiLuiRelationId, result));
        } else {
            result = cachedResult.getValue();
        }

        return (LuiLuiRelationInfo) result;
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIds(List<String> luiLuiRelationIds,
                                                            ContextInfo context)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        List<LuiLuiRelationInfo> result = new ArrayList<LuiLuiRelationInfo>();
        try {
            for (String relationdId : luiLuiRelationIds) {
                result.add(this.getLuiLuiRelation(relationdId, context));
            }
        } catch (DoesNotExistException e) {
            throw new OperationFailedException();
        }
        return result;
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
