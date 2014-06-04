package org.kuali.student.r2.lum.service.decorator;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.class1.util.SearchCacheDecoratorUtil;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.lu.entity.Clu;
import org.kuali.student.r2.lum.lu.service.impl.CluServiceAssembler;

import javax.jws.WebParam;
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

    private static final String CLU_CACHE_NAME = "cluCache";
    private static final String CLU_SEARCH_KEY_PREFIX = "clusearch";

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
        getCacheManager().getCache(CLU_CACHE_NAME).remove(cluId);
        SearchCacheDecoratorUtil.invalidateCache(getCacheManager().getCache(CLU_CACHE_NAME), CLU_SEARCH_KEY_PREFIX,
                INVALIDATE_SEARCH_CACHE_ONLY_CONFIG_KEY, VERIFY_RESULTS_BEFORE_INVALIDATE_CONFIG_KEY, cluId, "lu.resultColumn.cluId");
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
