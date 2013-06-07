package org.kuali.student.r2.core.acal.service.decorators;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 6/7/13
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class AcademicCalendarServiceCacheDecorator extends AcademicCalendarServiceDecorator{

    private CacheManager cacheManager;


    protected static final String ACAL_SERVICE_CACHE = "AcademicCalendarServiceCache";
    protected static final String INSTR_DAYS_FOR_TERM_KEY = "getInstructionalDaysForTerm";

    @Override
    public Integer getInstructionalDaysForTerm(String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(INSTR_DAYS_FOR_TERM_KEY, termId);

        Element cachedResult = cacheManager.getCache(ACAL_SERVICE_CACHE).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getInstructionalDaysForTerm(termId,contextInfo);
            cacheManager.getCache(ACAL_SERVICE_CACHE).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (Integer)result;
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
