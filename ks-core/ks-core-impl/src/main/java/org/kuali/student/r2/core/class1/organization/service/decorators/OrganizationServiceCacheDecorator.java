package org.kuali.student.r2.core.class1.organization.service.decorators;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
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
import org.kuali.student.r2.core.class1.util.SearchCacheDecoratorUtil;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationServiceDecorator;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

import javax.jws.WebParam;

/**
 * Caches organizations
 */
public class OrganizationServiceCacheDecorator extends OrganizationServiceDecorator {

    private static final String ORG_CACHE_NAME = "orgCache";
    private static final String ORG_SEARCH_KEY_PREFIX = "orgsearch";

    private static final String INVALIDATE_SEARCH_CACHE_ONLY_CONFIG_KEY = "org.cache.search.invalidateSearchCacheOnly";
    private static final String VERIFY_RESULTS_BEFORE_INVALIDATE_CONFIG_KEY = "org.cache.search.verifyResultsBeforeInvalidate";

    private CacheManager cacheManager;

    @Override
    public OrgInfo getOrg(@WebParam(name = "orgId") String orgId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Element cachedResult = getCacheManager().getCache(ORG_CACHE_NAME).get(orgId);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getOrg(orgId, contextInfo);
            getCacheManager().getCache(ORG_CACHE_NAME).put(new Element(orgId, result));
        } else {
            result = cachedResult.getValue();
        }

        return (OrgInfo)result;
    }

    @Override
    public OrgInfo createOrg(@WebParam(name = "orgTypeKey") String orgTypeKey, @WebParam(name = "orgInfo") OrgInfo orgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        OrgInfo result = getNextDecorator().createOrg(orgTypeKey, orgInfo, contextInfo);
        SearchCacheDecoratorUtil.invalidateCache(getCacheManager().getCache(ORG_CACHE_NAME), ORG_SEARCH_KEY_PREFIX,
                INVALIDATE_SEARCH_CACHE_ONLY_CONFIG_KEY, VERIFY_RESULTS_BEFORE_INVALIDATE_CONFIG_KEY, null, "org.resultColumn.orgId");
        getCacheManager().getCache(ORG_CACHE_NAME).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public OrgInfo updateOrg(@WebParam(name = "orgId") String orgId, @WebParam(name = "orgInfo") OrgInfo orgInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        OrgInfo result = getNextDecorator().updateOrg(orgId, orgInfo, contextInfo);
        getCacheManager().getCache(ORG_CACHE_NAME).remove(orgId);
        SearchCacheDecoratorUtil.invalidateCache(getCacheManager().getCache(ORG_CACHE_NAME), ORG_SEARCH_KEY_PREFIX,
                INVALIDATE_SEARCH_CACHE_ONLY_CONFIG_KEY, VERIFY_RESULTS_BEFORE_INVALIDATE_CONFIG_KEY, orgId, "org.resultColumn.orgId");
        getCacheManager().getCache(ORG_CACHE_NAME).put(new Element(orgId, result));
        return result;
    }

    @Override
    public StatusInfo deleteOrg(@WebParam(name = "orgId") String orgId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteOrg(orgId, contextInfo);
        getCacheManager().getCache(ORG_CACHE_NAME).remove(orgId);
        SearchCacheDecoratorUtil.invalidateCache(getCacheManager().getCache(ORG_CACHE_NAME), ORG_SEARCH_KEY_PREFIX,
                INVALIDATE_SEARCH_CACHE_ONLY_CONFIG_KEY, VERIFY_RESULTS_BEFORE_INVALIDATE_CONFIG_KEY, orgId, "org.resultColumn.orgId");
        return result;
    }

    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        return SearchCacheDecoratorUtil.search(getCacheManager().getCache(ORG_CACHE_NAME), ORG_SEARCH_KEY_PREFIX, getNextDecorator(), searchRequestInfo, contextInfo);
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
