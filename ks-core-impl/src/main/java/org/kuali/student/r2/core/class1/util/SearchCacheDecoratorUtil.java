package org.kuali.student.r2.core.class1.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.core.search.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides some utility methods that a CacheDecorator can use to easily implement a search cache
 */
public class SearchCacheDecoratorUtil {

    private static final Logger LOG = LoggerFactory.getLogger(SearchCacheDecoratorUtil.class);

    /**
     * Invalidate the items in the cache, either all, or just the search items, depending on the value of invalidateSearchCacheOnly.
     * @param cache Cache object to check
     * @param cacheSearchKeyPrefix Prefix used to create the search key
     * @param invalidateSearchCacheOnlyConfigKey Key used to look up a configuration value to determine if the entire cache
     *                                           should be invalidated, or just the search part
     * @param verifyResultsBeforeInvalidateConfigKey Key used to look up a configuration value to determine if the results
     *                                               should be checked for the existence of id before invalidating the cache
     * @param id id to search for in the cached results
     * @param cellKey Key used to look up a value in the cached search results
     */
    public static void invalidateCache(Cache cache, String cacheSearchKeyPrefix, String invalidateSearchCacheOnlyConfigKey,
                                       String verifyResultsBeforeInvalidateConfigKey, String id, String cellKey) {

        boolean invalidateSearchCacheOnly = ConfigContext.getCurrentContextConfig().getBooleanProperty(invalidateSearchCacheOnlyConfigKey);
        boolean verifyResultsBeforeInvalidate = ConfigContext.getCurrentContextConfig().getBooleanProperty(verifyResultsBeforeInvalidateConfigKey);
        if (!invalidateSearchCacheOnly) {
            LOG.debug("Clearing entire cache ({})", cache.getName());
            cache.removeAll();
        }
        else {
            List<MultiKey> keys = new ArrayList<MultiKey>();
            for(Object key  : cache.getKeys()) {
                if(key instanceof MultiKey) {
                    MultiKey cacheKey = (MultiKey) key;
                    if(cacheKey.size() > 1 && cacheSearchKeyPrefix.equals(cacheKey.getKey(0))) {
                        if (!verifyResultsBeforeInvalidate || id == null) {
                            keys.add(cacheKey);
                            LOG.debug("Clearing key {} from cache ({})", key, cache.getName());
                        }
                        else {
                            Element cachedResult = cache.get(cacheKey);
                            if (cachedResult != null) {
                                SearchResultInfo sri = (SearchResultInfo)cachedResult.getValue();
                                for (SearchResultRow row : sri.getRows()) {
                                    String resultId = getCellValue(row, cellKey);
                                    if (id.equals(resultId)) {
                                        // Remove result from the cache
                                        keys.add(cacheKey);
                                        LOG.debug("Clearing key {} from cache ({})", key, cache.getName());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            cache.removeAll(keys);
        }
    }

    /**
     * Looks in the Cache, using the cacheSearchKeyPrefix and searchRequestInfo as a MultiKey.  If no results are found,
     * uses the searcher to perform a new search
     * @param cache Cache object to check
     * @param cacheSearchKeyPrefix Prefix used to create the search key
     * @param searcher Service implementation that will actually be performing the search
     * @param searchRequestInfo
     * @param contextInfo
     * @return The cached version of SearchResultInfo if found in the cache, otherwise, uses the searcher to perform a search and returns a SearchResultInfo from there.
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public static SearchResultInfo search(Cache cache, String cacheSearchKeyPrefix, SearchService searcher,
                                          SearchRequestInfo searchRequestInfo, ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(cacheSearchKeyPrefix, searchRequestInfo);

        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), searchRequestInfo);
            result = searcher.search(searchRequestInfo, contextInfo);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), searchRequestInfo);
            result = cachedResult.getValue();
        }

        return (SearchResultInfo) result;
    }


    /**
     * Convenience method to help get values out of a SearchResultRow
     * @param row Row data to look through
     * @param key Key used to get the results from a particular cell
     * @return The cell value with the given key inside the row
     */
    private static String getCellValue(SearchResultRow row, String key) {
        for (SearchResultCell cell : row.getCells()) {
            if (key.equals(cell.getKey())) {
                return cell.getValue();
            }
        }
        throw new RuntimeException("cell result '" + key + "' not found");
    }

}
