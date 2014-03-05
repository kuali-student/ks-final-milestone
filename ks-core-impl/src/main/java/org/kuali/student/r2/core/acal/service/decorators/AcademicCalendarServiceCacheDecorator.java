package org.kuali.student.r2.core.acal.service.decorators;

import net.sf.ehcache.CacheManager;

/**
 * Cache Decorator for the Academic Calendar service. This class will grow as caching needs arise.
 *
 * Note: it uses ehcache which is configured in ks-ehcache.xml
 */
public class AcademicCalendarServiceCacheDecorator extends AcademicCalendarServiceDecorator{

    private CacheManager cacheManager;


    protected static final String ACAL_SERVICE_CACHE = "AcademicCalendarServiceCache";
    protected static final String INSTR_DAYS_FOR_TERM_KEY = "getInstructionalDaysForTerm";

    /* The cache is causing problems for the users. They update some part of the acal and are expecting to see
     the instructional days get updated, but they don't because of the cache. Since almost any change to a calendard or
     ATP should invalidate the cache, it's not reasonable to cache this method unless absolutly necessary.
     Recently we have improved the performance of getInstructionalDays considerably so this cache might not be as
      necessary. Commenting out for now. KSENROLL-7800

    @Override
    public Integer getInstructionalDaysForTerm(String termId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(INSTR_DAYS_FOR_TERM_KEY, termId);

        Element cachedResult = getCacheManager().getCache(ACAL_SERVICE_CACHE).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getInstructionalDaysForTerm(termId,contextInfo);
            getCacheManager().getCache(ACAL_SERVICE_CACHE).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (Integer)result;
    }
    */

    public CacheManager getCacheManager() {
        if (cacheManager == null){
            cacheManager = CacheManager.getInstance();
        }
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
