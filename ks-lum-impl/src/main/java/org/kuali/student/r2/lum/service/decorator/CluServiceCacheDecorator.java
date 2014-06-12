package org.kuali.student.r2.lum.service.decorator;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.Status;
import org.kuali.student.r2.core.class1.util.SearchCacheDecoratorUtil;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.lu.entity.CluSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 11/1/13
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class CluServiceCacheDecorator extends CluServiceDecorator {

    /* Caching the CluInfo objects by id */
    private static final String CLU_CACHE_NAME = "cluCache";
    private static final String CLU_SEARCH_KEY_PREFIX = "clusearch";

    /* Caching the ClusetInfo objects by id */
    private static final String CLUSET_CACHE_NAME = "clusetCache";
    private static final String CLUSET_CLUIDS_KEY_PREFIX = "clusetCluIds";
    private static final String CLUSET_ALLCLUIDS_KEY_PREFIX = "clusetAllCluIds";

    /* Caching the CluCluRelationshipInfo objects by id */
    private static final String CLUCLURELATION_CACHE_NAME = "cluCluRelationCache";

    private static final String INVALIDATE_SEARCH_CACHE_ONLY_CONFIG_KEY = "clu.cache.search.invalidateSearchCacheOnly";
    private static final String VERIFY_RESULTS_BEFORE_INVALIDATE_CONFIG_KEY = "clu.cache.search.verifyResultsBeforeInvalidate";

    private CacheManager cacheManager;

    @Override
    public CluInfo createClu(String luTypeKey, CluInfo cluInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        CluInfo result = getNextDecorator().createClu(luTypeKey, cluInfo, contextInfo);
        SearchCacheDecoratorUtil.invalidateCache(getCacheManager().getCache(CLU_CACHE_NAME), CLU_SEARCH_KEY_PREFIX,
                INVALIDATE_SEARCH_CACHE_ONLY_CONFIG_KEY, VERIFY_RESULTS_BEFORE_INVALIDATE_CONFIG_KEY, null, "lu.resultColumn.cluId");
        getCacheManager().getCache(CLU_CACHE_NAME).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public StatusInfo deleteClu(String cluId, ContextInfo contextInfo)
            throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        StatusInfo result = getNextDecorator().deleteClu(cluId, contextInfo);

        // Remove from clu cache.
        getCacheManager().getCache(CLU_CACHE_NAME).remove(cluId);
        SearchCacheDecoratorUtil.invalidateCache(getCacheManager().getCache(CLU_CACHE_NAME), CLU_SEARCH_KEY_PREFIX,
                INVALIDATE_SEARCH_CACHE_ONLY_CONFIG_KEY, VERIFY_RESULTS_BEFORE_INVALIDATE_CONFIG_KEY, cluId, "lu.resultColumn.cluId");

        // Remove from cluclu relationship cache for clu Id.
        Cache luiluiCache = getCacheManager().getCache(CLUCLURELATION_CACHE_NAME);
        Query query = luiluiCache.createQuery().includeKeys();
        query.addCriteria(luiluiCache.getSearchAttribute("cluId").eq(cluId));

        Results results = query.execute();
        for (Result queryResult : results.all()) {
            luiluiCache.remove(queryResult.getKey());
        }
        return result;
    }

    @Override
    public CluInfo getClu(String cluId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        Cache cluCache = getCacheManager().getCache(CLU_CACHE_NAME);
        Element cachedResult = cluCache.get(cluId);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getClu(cluId, contextInfo);
            cluCache.put(new Element(cluId, result));
        } else {
            result = cachedResult.getValue();
        }

        return (CluInfo) result;
    }

    @Override
    public List<CluInfo> getClusByIds(List<String> cluIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<CluInfo> result = new ArrayList<CluInfo>();
        try {
            for (String cluId : cluIds) {
                result.add(this.getClu(cluId, context));
            }
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException(e);
        }
        return result;
    }

    @Override
    public List<CluInfo> getClusByLuType(String luTypeKey, String luState, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<CluInfo> result = new ArrayList<CluInfo>();
        try {
            List<String> cluIds = this.getNextDecorator().getCluIdsByLuType(luTypeKey, luState, context);
            for (String cluId : cluIds) {
                result.add(this.getClu(cluId, context));
            }
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException(e);
        }
        return result;
    }

    @Override
    public CluInfo updateClu(String cluId, CluInfo cluInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        CluInfo result = getNextDecorator().updateClu(cluId, cluInfo, contextInfo);
        getCacheManager().getCache(CLU_CACHE_NAME).put(new Element(cluId, result));
        SearchCacheDecoratorUtil.invalidateCache(getCacheManager().getCache(CLU_CACHE_NAME), CLU_SEARCH_KEY_PREFIX,
                INVALIDATE_SEARCH_CACHE_ONLY_CONFIG_KEY, VERIFY_RESULTS_BEFORE_INVALIDATE_CONFIG_KEY, cluId, "lu.resultColumn.cluId");
        return result;
    }

    @Override
    public List<CluInfo> getRelatedClusByCluAndRelationType(String cluId, String cluCluRelationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> cluIds = getNextDecorator().getRelatedCluIdsByCluAndRelationType(cluId, cluCluRelationTypeKey, contextInfo);
        List<CluInfo> result = new ArrayList<CluInfo>();
        for (String relatedCluId : cluIds) {
            result.add(this.getClu(relatedCluId, contextInfo));
        }

        return result;
    }

    @Override
    public List<CluInfo> getClusByRelatedCluAndRelationType(String relatedCluId, String luLuRelationTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<CluInfo> result = new ArrayList<CluInfo>();
        try {
            List<String> cluIds = getNextDecorator().getCluIdsByRelatedCluAndRelationType(relatedCluId, luLuRelationTypeKey, context);
            for (String cluId : cluIds) {
                result.add(this.getClu(cluId, context));
            }
        } catch (PermissionDeniedException e) {
            throw new OperationFailedException(e);
        }
        return result;
    }

    @Override
    public CluCluRelationInfo createCluCluRelation(String cluId, String relatedCluId, String cluCluRelationTypeKey,
                                                   CluCluRelationInfo cluCluRelationInfo, ContextInfo contextInfo)
            throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        CluCluRelationInfo result = this.getNextDecorator().createCluCluRelation(cluId, relatedCluId, cluCluRelationTypeKey, cluCluRelationInfo, contextInfo);
        getCacheManager().getCache(CLUCLURELATION_CACHE_NAME).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public CluCluRelationInfo getCluCluRelation(String cluCluRelationId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        Cache cluCache = getCacheManager().getCache(CLUCLURELATION_CACHE_NAME);
        Element cachedResult = cluCache.get(cluCluRelationId);
        Object result;
        if (cachedResult == null) {
            result = this.getNextDecorator().getCluCluRelation(cluCluRelationId, contextInfo);
            cluCache.put(new Element(cluCluRelationId, result));
        } else {
            result = cachedResult.getValue();
        }

        return (CluCluRelationInfo) result;
    }

    @Override
    public CluCluRelationInfo updateCluCluRelation(String cluCluRelationId, CluCluRelationInfo cluCluRelationInfo,
                                                   ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        CluCluRelationInfo result = this.getNextDecorator().updateCluCluRelation(cluCluRelationId, cluCluRelationInfo, contextInfo);
        getCacheManager().getCache(CLUCLURELATION_CACHE_NAME).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public StatusInfo deleteCluCluRelation(String cluCluRelationId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        StatusInfo result = this.getNextDecorator().deleteCluCluRelation(cluCluRelationId, context);
        getCacheManager().getCache(CLUCLURELATION_CACHE_NAME).remove(cluCluRelationId);
        return result;
    }

    @Override
    public CluSetInfo createCluSet(String cluSetTypeKey, CluSetInfo cluSetInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException {
        CluSetInfo result = this.getNextDecorator().createCluSet(cluSetTypeKey, cluSetInfo, contextInfo);
        getCacheManager().getCache(CLU_CACHE_NAME).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public StatusInfo deleteCluSet(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        StatusInfo result = this.getNextDecorator().deleteCluSet(cluSetId, contextInfo);

        Cache cluSetCache = getCacheManager().getCache(CLUSET_CACHE_NAME);
        cluSetCache.remove(cluSetId);

        MultiKey cluIdsKey = new MultiKey(CLUSET_CLUIDS_KEY_PREFIX,cluSetId);
        cluSetCache.remove(cluIdsKey);

        MultiKey allCluIdsKey = new MultiKey(CLUSET_ALLCLUIDS_KEY_PREFIX,cluSetId);
        cluSetCache.remove(allCluIdsKey);

        return result;
    }

    @Override
    public CluSetInfo getCluSet(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        Cache cluSetCache = getCacheManager().getCache(CLUSET_CACHE_NAME);
        Element cachedResult = cluSetCache.get(cluSetId);
        Object result;
        if (cachedResult == null) {
            result = this.getNextDecorator().getCluSet(cluSetId, contextInfo);
            cluSetCache.put(new Element(cluSetId, result));
        } else {
            result = cachedResult.getValue();
        }

        return (CluSetInfo) result;
    }

    @Override
    public List<String> getCluIdsFromCluSet(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(CLUSET_CLUIDS_KEY_PREFIX,cluSetId);
        Cache cluSetCache = getCacheManager().getCache(CLUSET_CACHE_NAME);
        Element cachedResult = cluSetCache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = this.getNextDecorator().getCluIdsFromCluSet(cluSetId, contextInfo);
            cluSetCache.put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    @Override
    public List<String> getAllCluIdsInCluSet(String cluSetId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(CLUSET_ALLCLUIDS_KEY_PREFIX,cluSetId);
        Cache cluSetCache = getCacheManager().getCache(CLUSET_CACHE_NAME);
        Element cachedResult = cluSetCache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = this.getNextDecorator().getAllCluIdsInCluSet(cluSetId, contextInfo);
            cluSetCache.put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>) result;
    }

    @Override
    public CluSetInfo updateCluSet(String cluSetId, CluSetInfo cluSetInfo, ContextInfo contextInfo)
            throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, UnsupportedActionException,
            VersionMismatchException {
        CluSetInfo result = this.getNextDecorator().updateCluSet(cluSetId, cluSetInfo, contextInfo);

        Cache cluSetCache = getCacheManager().getCache(CLUSET_CACHE_NAME);
        cluSetCache.put(new Element(result.getId(), result));

        MultiKey cluIdsKey = new MultiKey(CLUSET_CLUIDS_KEY_PREFIX,cluSetId);
        cluSetCache.remove(cluIdsKey);

        MultiKey allCluIdsKey = new MultiKey(CLUSET_ALLCLUIDS_KEY_PREFIX,cluSetId);
        cluSetCache.remove(allCluIdsKey);

        return result;
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        return SearchCacheDecoratorUtil.search(getCacheManager().getCache(CLU_CACHE_NAME), CLU_SEARCH_KEY_PREFIX, getNextDecorator(), searchRequestInfo, contextInfo);
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
