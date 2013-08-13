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
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;

import java.util.List;

/*
 * Decorator for LuiService to add caching to select lui service methods.
 */
public class LuiServiceCacheDecorator extends LuiServiceDecorator {
    private static String cacheName = "luiCache";
    private CacheManager cacheManager;

    @Override
    public LuiLuiRelationInfo getLuiLuiRelation(String luiLuiRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuiLuiRelation", luiLuiRelationId);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiLuiRelation(luiLuiRelationId, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (LuiLuiRelationInfo) result;
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByIds(List<String> luiLuiRelationIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuiLuiRelationsByIds", luiLuiRelationIds);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiLuiRelationsByIds(luiLuiRelationIds, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<LuiLuiRelationInfo>) result;
    }

    @Override
    public List<String> getLuiLuiRelationIdsByType(String luiLuiRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuiLuiRelationIdsByType", luiLuiRelationTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiLuiRelationIdsByType(luiLuiRelationTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLui(String luiId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuiLuiRelationsByLui", luiId);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiLuiRelationsByLui(luiId, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<LuiLuiRelationInfo>) result;
    }

    @Override
    public List<LuiLuiRelationInfo> getLuiLuiRelationsByLuiAndRelatedLui(String luiId, String relatedLuiId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuiLuiRelationsByLuiAndRelatedLui", luiId, relatedLuiId);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiLuiRelationsByLuiAndRelatedLui(luiId, relatedLuiId, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<LuiLuiRelationInfo>) result;
    }

    @Override
    public List<String> getLuiIdsByRelatedLuiAndRelationType(String relatedLuiId, String luiLuiRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuiIdsByRelatedLuiAndRelationType", relatedLuiId, luiLuiRelationTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiIdsByRelatedLuiAndRelationType(relatedLuiId, luiLuiRelationTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    @Override
    public List<LuiInfo> getLuisByRelatedLuiAndRelationType(String relatedLuiId, String luiLuiRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuisByRelatedLuiAndRelationType", relatedLuiId, luiLuiRelationTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuisByRelatedLuiAndRelationType(relatedLuiId, luiLuiRelationTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<LuiInfo>) result;
    }

    @Override
    public List<String> getLuiIdsByLuiAndRelationType(String luiId, String luiLuiRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuiIdsByLuiAndRelationType", luiId, luiLuiRelationTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiIdsByLuiAndRelationType(luiId, luiLuiRelationTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    @Override
    public List<LuiInfo> getRelatedLuisByLuiAndRelationType(String luiId, String luiLuiRelationTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getRelatedLuisByLuiAndRelationType", luiId, luiLuiRelationTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getRelatedLuisByLuiAndRelationType(luiId, luiLuiRelationTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<LuiInfo>) result;
    }

    @Override
    public List<LuiInfo> getLuiLuiRelationsByLuiAndRelatedLuiType(String luiId, String relatedLuiTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuiLuiRelationsByLuiAndRelatedLuiType", luiId, relatedLuiTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiLuiRelationsByLuiAndRelatedLuiType(luiId, relatedLuiTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<LuiInfo>) result;
    }

    @Override
    public List<String> searchForLuiLuiRelationIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("searchForLuiLuiRelationIds", criteria);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().searchForLuiLuiRelationIds(criteria, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    @Override
    public List<LuiLuiRelationInfo> searchForLuiLuiRelations(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("searchForLuiLuiRelations", criteria);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().searchForLuiLuiRelations(criteria, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<LuiLuiRelationInfo>) result;
    }

    @Override
    public LuiLuiRelationInfo updateLuiLuiRelation(String luiLuiRelationId, LuiLuiRelationInfo luiLuiRelationInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        LuiLuiRelationInfo result = getNextDecorator().updateLuiLuiRelation(luiLuiRelationId, luiLuiRelationInfo, contextInfo);
        cacheManager.getCache(cacheName).removeAll();
        return result;
    }

    @Override
    public StatusInfo deleteLuiLuiRelation(String luiLuiRelationId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteLuiLuiRelation(luiLuiRelationId, contextInfo);
        cacheManager.getCache(cacheName).removeAll();
        return result;
    }

    @Override
    public List<LuiInfo> getLuisByIds(List<String> luiIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuisByIds", luiIds);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuisByIds(luiIds, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<LuiInfo>) result;
    }

    @Override
    public List<String> getLuiIdsByType(String luiTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuiIdsByType", luiTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiIdsByType(luiTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    @Override
    public List<String> getLuiIdsByClu(String cluId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuiIdsByClu", cluId);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiIdsByClu(cluId, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    @Override
    public List<String> getLuiIdsByAtpAndType(String atpId, String luiTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuiIdsByAtpAndType", atpId, luiTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiIdsByAtpAndType(atpId, luiTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    @Override
    public List<String> getLuiIdsByAtpAndClu(String cluId, String atpId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuiIdsByAtpAndClu", cluId, atpId);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuiIdsByAtpAndClu(cluId, atpId, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    @Override
    public List<LuiInfo> getLuisByAtpAndClu(String cluId, String atpId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLuisByAtpAndClu", cluId, atpId);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLuisByAtpAndClu(cluId, atpId, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<LuiInfo>) result;
    }

    @Override
    public List<String> searchForLuiIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("searchForLuiIds", criteria);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().searchForLuiIds(criteria, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    @Override
    public List<LuiInfo> searchForLuis(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("searchForLuis", criteria);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().searchForLuis(criteria, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<LuiInfo>) result;
    }

    @Override
    public LuiInfo getLui(String luiId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLui", luiId);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLui(luiId, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (LuiInfo)result;
    }

    @Override
    public LuiInfo updateLui(String luiId, LuiInfo luiInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        LuiInfo result = getNextDecorator().updateLui(luiId, luiInfo, contextInfo);
        cacheManager.getCache(cacheName).removeAll();
        return result;
    }

    @Override
    public StatusInfo deleteLui(String luiId, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteLui(luiId, contextInfo);
        cacheManager.getCache(cacheName).removeAll();
        return result;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
