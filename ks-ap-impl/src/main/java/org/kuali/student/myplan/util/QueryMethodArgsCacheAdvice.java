package org.kuali.student.myplan.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.util.spring.MethodArgsToObjectEhcacheAdvice;

/**
 * Extends the methodArgsCacheAdvice to be able to cache searchRequests based on same QueryByCriteria objects
 *
 * TODO: UW - please review, this appears to be dead code in CM-2.0 /mwfyffe
 * TODO: This class should be removed when https://jira.kuali.org/browse/KULRICE-6988 is fixed
 *
 *
 * @Author kmuthu
 * Date: 3/22/12
 */
public class QueryMethodArgsCacheAdvice extends MethodArgsToObjectEhcacheAdvice {

	
    // @Override
    protected String generateCacheKey(ProceedingJoinPoint pjp) {
		final StringBuffer cacheKey = new StringBuffer(pjp.getSignature().getName());
		cacheKey.append("(");
		for (int i = 0; i < pjp.getArgs().length; i++) {

			if(null == pjp.getArgs()[i]) {
				cacheKey.append("<null>");
			} else {

                // Parse through the QueryByCriteria to establish same key for same criteria values
                if( pjp.getArgs()[i] instanceof QueryByCriteria) {

                    QueryByCriteria qbc = (QueryByCriteria) pjp.getArgs()[i];
                    cacheKey.append(qbc.getPredicate().toString());

                }   else {

				    cacheKey.append(pjp.getArgs()[i].toString());

                }
			}

			if (i + 1 != pjp.getArgs().length) {
				cacheKey.append(",");
			}

		}

        cacheKey.append(")");
		return cacheKey.toString();
	}
}
