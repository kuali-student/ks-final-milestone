/*
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.ap.framework.context.decorators;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.r2.core.acal.infc.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TermHelperCacheDecorator extends TermHelperDecorator {

    private static final Logger LOG = LoggerFactory.getLogger(TermHelperCacheDecorator.class);

    private static final String TERM_HELPER_CACHE = "ksapTermHelperCache";
    private static final String TERM_HELPER_TERMS_PREFIX = "ksapterm";
    private static final String TERM_HELPER_STATUS_PREFIX = "ksapstatus";

    // Cache manager used in storing search results for sqls
    private CacheManager cacheManager;

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public Term getTerm(String atpId) {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_TERMS_PREFIX, atpId);
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), atpId);
            result = getNextDecorator().getTerm(atpId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), atpId);
            result = cachedResult.getValue();
        }

        return (Term) result;
    }

    @Override
    public YearTerm getYearTerm(String atpId) {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_TERMS_PREFIX + "yearterm", atpId);
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), atpId);
            result = getNextDecorator().getYearTerm(atpId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), atpId);
            result = cachedResult.getValue();
        }

        return (YearTerm) result;
    }

    @Override
    public List<Term> getCurrentTerms() {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_TERMS_PREFIX, "current");
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), "current");
            result = getNextDecorator().getCurrentTerms();
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), "current");
            result = cachedResult.getValue();
        }

        return (List<Term>) result;
    }

    @Override
    public Term getLastScheduledTerm() {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_TERMS_PREFIX, "lastscheduled");
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), "lastscheduled");
            result = getNextDecorator().getLastScheduledTerm();
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), "lastscheduled");
            result = cachedResult.getValue();
        }

        return (Term) result;
    }

    @Override
    public Term getOldestHistoricalTerm() {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_TERMS_PREFIX, "oldesthistorical");
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), "oldesthistorical");
            result = getNextDecorator().getOldestHistoricalTerm();
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), "oldesthistorical");
            result = cachedResult.getValue();
        }

        return (Term) result;
    }

    @Override
    public List<Term> getTermsInAcademicYear(YearTerm yearTerm) {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_TERMS_PREFIX, yearTerm);
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), yearTerm);
            result = getNextDecorator().getTermsInAcademicYear();
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), yearTerm);
            result = cachedResult.getValue();
        }

        return (List<Term>) result;
    }

    @Override
    public List<Term> getPlanningTerms() {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_TERMS_PREFIX, "planning");
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), "planning");
            result = getNextDecorator().getPlanningTerms();
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), "planning");
            result = cachedResult.getValue();
        }

        return (List<Term>) result;
    }

    @Override
    public boolean isOfficial(String termId) {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_STATUS_PREFIX + "isofficial", termId);
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), termId);
            result = getNextDecorator().isOfficial(termId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), termId);
            result = cachedResult.getValue();
        }

        return (boolean) result;
    }

    /**
     * @see org.kuali.student.ap.framework.context.TermHelper#isPlanning(String)
     */
    @Override
    public boolean isPlanning(String termId) {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_STATUS_PREFIX + "isplanning", termId);
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), termId);
            result = getNextDecorator().isPlanning(termId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), termId);
            result = cachedResult.getValue();
        }

        return (boolean) result;
    }

    /**
     * @see org.kuali.student.ap.framework.context.TermHelper#isCompleted(String)
     */
    @Override
    public boolean isCompleted(String termId) {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_STATUS_PREFIX + "iscompleted", termId);
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), termId);
            result = getNextDecorator().isCompleted(termId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), termId);
            result = cachedResult.getValue();
        }

        return (boolean) result;
    }

    /**
     * @see org.kuali.student.ap.framework.context.TermHelper#isInProgress(String)
     */
    @Override
    public boolean isInProgress(String termId) {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_STATUS_PREFIX + "isinprogress", termId);
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), termId);
            result = getNextDecorator().isInProgress(termId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), termId);
            result = cachedResult.getValue();
        }

        return (boolean) result;
    }

    /**
     * @see org.kuali.student.ap.framework.context.TermHelper#isFutureTerm(String)
     */
    @Override
    public boolean isFutureTerm(String termId) {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_STATUS_PREFIX + "isfutureterm", termId);
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), termId);
            result = getNextDecorator().isFutureTerm(termId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), termId);
            result = cachedResult.getValue();
        }

        return (boolean) result;
    }

    /**
     * @see org.kuali.student.ap.framework.context.TermHelper#isCurrentTerm(String)
     */
    @Override
    public boolean isCurrentTerm(String termId) {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_STATUS_PREFIX + "iscurrentterm", termId);
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), termId);
            result = getNextDecorator().isCurrentTerm(termId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), termId);
            result = cachedResult.getValue();
        }

        return (boolean) result;
    }

    /**
     * Registration is considered open so long as the schedule adjustment period has not ended.
     *
     * @see org.kuali.student.ap.framework.context.TermHelper#isRegistrationOpen(String)
     */
    @Override
    public boolean isRegistrationOpen(String termId) {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_STATUS_PREFIX + "isregistrationopen", termId);
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), termId);
            result = getNextDecorator().isRegistrationOpen(termId);
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), termId);
            result = cachedResult.getValue();
        }

        return (boolean) result;
    }

    @Override
    public List<Term> getOfficialTerms() {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_TERMS_PREFIX, "official");
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), "official");
            result = getNextDecorator().getOfficialTerms();
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), "official");
            result = cachedResult.getValue();
        }

        return (List<Term>) result;
    }

    @Override
    public Term getCurrentTerm() {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_TERMS_PREFIX, "currentterm");
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), "currentterm");
            result = getNextDecorator().getCurrentTerm();
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), "currentterm");
            result = cachedResult.getValue();
        }

        return (Term) result;
    }

    @Override
    public List<Term> getCurrentTermsBasedOnKeyDate() {
        MultiKey cacheKey = new MultiKey(TERM_HELPER_TERMS_PREFIX, "currenttermsbasedonkeydate");
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), "currenttermsbasedonkeydate");
            result = getNextDecorator().getCurrentTermsBasedOnKeyDate();
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), "currenttermsbasedonkeydate");
            result = cachedResult.getValue();
        }

        return (List<Term>) result;
    }

    @Override
    public List<Term> getCurrentTermsWithPublishedSOC (){
        MultiKey cacheKey = new MultiKey(TERM_HELPER_TERMS_PREFIX, "currenttermswithpublishedsoc");
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), "currenttermswithpublishedsoc");
            result = getNextDecorator().getCurrentTermsWithPublishedSOC();
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), "currenttermswithpublishedsoc");
            result = cachedResult.getValue();
        }

        return (List<Term>) result;
    }

    @Override
    public List<Term> getFutureTermsWithPublishedSOC (){
        MultiKey cacheKey = new MultiKey(TERM_HELPER_TERMS_PREFIX, "futuretermswithpublishedsoc");
        Cache cache = getCacheManager().getCache(TERM_HELPER_CACHE);
        Element cachedResult = cache.get(cacheKey);
        Object result;
        if (cachedResult == null) {
            LOG.debug("Cache ({}) miss for search of {}", cache.getName(), "futuretermswithpublishedsoc");
            result = getNextDecorator().getFutureTermsWithPublishedSOC();
            cache.put(new Element(cacheKey, result));
        } else {
            LOG.debug("Cache ({}) hit for search of {}", cache.getName(), "futuretermswithpublishedsoc");
            result = cachedResult.getValue();
        }
        return (List<Term>) result;
    }

}
