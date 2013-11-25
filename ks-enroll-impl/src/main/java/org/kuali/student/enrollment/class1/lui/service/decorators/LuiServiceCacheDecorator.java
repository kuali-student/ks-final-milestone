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
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.lui.dto.LuiCapacityInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.lui.dto.LuiSetInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;

import java.util.List;

/*
 * Decorator for LuiService to add caching to lui service methods.
 */
public class LuiServiceCacheDecorator extends LuiServiceDecorator {

    private static String cacheName = "luiCache";
    public static final String LUI_KEY = "lui";
    public static final String LUILUI_RELTN_KEY = "luiluireltn";
    private CacheManager cacheManager;

    @Override
    public LuiInfo getLui(String luiId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(LUI_KEY, luiId);

        Element cachedResult = getCacheManager().getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLui(luiId, contextInfo);
            getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (LuiInfo)result;
    }

    @Override
    public LuiInfo createLui(String luiId, String atpId, String luiTypeKey, LuiInfo luiInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        LuiInfo result = getNextDecorator().createLui(luiId, atpId, luiTypeKey, luiInfo, contextInfo);
        MultiKey cacheKey = new MultiKey(LUI_KEY, result.getId());
        getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        return result;
    }

    @Override
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        MultiKey cacheKey = new MultiKey(LUI_KEY, luiId);
        LuiInfo result = getNextDecorator().updateLui(luiId, luiInfo, contextInfo);
        getCacheManager().getCache(cacheName).remove(cacheKey);
        for (LuiLuiRelationInfo rel : getLuiLuiRelationsByLui(luiId, contextInfo)) {
            //remove the related Luis
            removeRelatedLuisByLui(rel.getLuiId());
        }
        getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));

        return result;
    }

    @Override
    public StatusInfo deleteLui(String luiId, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(LUI_KEY, luiId);
        StatusInfo result = getNextDecorator().deleteLui(luiId, contextInfo);
        getCacheManager().getCache(cacheName).remove(cacheKey);
        for (LuiLuiRelationInfo rel : getLuiLuiRelationsByLui(luiId, contextInfo)) {
            //remove the related Luis
            removeRelatedLuisByLui(rel.getLuiId());
        }

        return result;
    }

    @Override
    public List<LuiInfo> getRelatedLuisByLuiAndRelationType(String luiId, String luiLuiRelationTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(LUILUI_RELTN_KEY, luiId, luiLuiRelationTypeKey);

        Element cachedResult = getCacheManager().getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult != null && !((List<LuiInfo>)cachedResult.getValue()).isEmpty()) {
            result = cachedResult.getValue();
        } else {
            result = getNextDecorator().getRelatedLuisByLuiAndRelationType(luiId, luiLuiRelationTypeKey, contextInfo);
            getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        }
        return (List<LuiInfo>)result;
    }

    @Override
    public List<LuiInfo> getLuisByRelatedLuiAndRelationType(String relatedLuiId, String luiLuiRelationTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(LUILUI_RELTN_KEY, relatedLuiId, luiLuiRelationTypeKey);

        Element cachedResult = getCacheManager().getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult != null && !((List<LuiInfo>)cachedResult.getValue()).isEmpty()) {
            result = cachedResult.getValue();
        } else {
            result = getNextDecorator().getLuisByRelatedLuiAndRelationType(relatedLuiId, luiLuiRelationTypeKey, contextInfo);
            getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        }

        return (List<LuiInfo>)result;
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(LUILUI_RELTN_KEY, luiId);

        Element cachedResult = getCacheManager().getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult != null && !((List<LuiLuiRelationInfo>)cachedResult.getValue()).isEmpty()) {
            result = cachedResult.getValue();
        } else {
            result = getNextDecorator().getLuiLuiRelationsByLui(luiId, contextInfo);
            getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        }
        return (List<LuiLuiRelationInfo>)result;

    }

    @Override
    public LuiLuiRelationInfo createLuiLuiRelation(String luiId, String relatedLuiId, String luiLuiRelationTypeKey, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        //remove the related Luis
        removeRelatedLuisByLui(luiId);
        removeRelatedLuisByLui(relatedLuiId);
        return getNextDecorator().createLuiLuiRelation(luiId, relatedLuiId, luiLuiRelationTypeKey, luiLuiRelationInfo, contextInfo);
    }


    private void removeRelatedLuisByLui(String luiId) {
        for(Object key  : getCacheManager().getCache(cacheName).getKeys()) {
            MultiKey cacheKey = (MultiKey) key;
            if(cacheKey.size() > 1 && LUILUI_RELTN_KEY.equals(cacheKey.getKey(0)) && luiId.equals(cacheKey.getKey(1))) {
                getCacheManager().getCache(cacheName).remove(cacheKey);
            }
        }
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
