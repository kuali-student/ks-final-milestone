package org.kuali.student.enrollment.class2.registration.admin.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 * Utility Class for common auto generated reg group functions
 */
public class AdminRegClientCache {

    private final static String CACHE_NAME = "AdminRegistrationCodeCache";

    private final static String TERMID_CODEPARM_TO_COURSECODES = "termId.codeparm.to.coursecodes";
    private final static String TERMID_COURSECODE_TO_CO = "termId.coursecode.to.co";
    private final static String TERMCODE_TO_TERMINFO_KEY = "termcode.to.term";

    private static CacheManager cacheManager;
    private static Cache cache;

    public static CacheManager getCacheManager() {
        if (cacheManager == null) {
            // "ks-ehcache" is the parent bean in ks-ehcache.xml file. This should probably be a constant.
            cacheManager = CacheManager.getCacheManager("ks-ehcache");
        }
        return cacheManager;
    }

    public static Cache getCache() {
        if(cache == null) {
            cache = getCacheManager().getCache(CACHE_NAME);
        }
        return cache;
    }

    /**
     * The premise of this is rather simple. Return a distinct list of course code. At a minimum there needs to
     * be one character. It then does a char% search. so E% will return all ENGL or any E* codes.
     * <p/>
     * This implementation is a little special. It's both cached and recursive.
     * <p/>
     * Because this is a structured search and course codes don't update often we can cache this pretty heavily and make
     * some assumptions that allow us to make this very efficient.
     * <p/>
     * So a user wants to type and see the type ahead results very quickly. The server wants as few db calls as possible.
     * The "bad" way to do this is to search on Every character entered. If we cache the searches then we'll get much
     * better performance. But we can go one step further because ths is a structured search. The first letter E in
     * ENGL will return EVERY course that starts with an E. So when you search for EN... why would you call the DB if
     * you have already called a search for E. So this uses recursion to build the searches. So, in the average case
     * you will only have to call a db search Once for Every first letter of the course codes.
     *
     * @return List of distinct course codes or an empty list
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     */
    public static List<String> retrieveCourseCodes(String termId, String courseCode) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        List<String> results = new ArrayList<String>();
        MultiKey cacheKey = new MultiKey(TERMID_CODEPARM_TO_COURSECODES, termId, courseCode);
        Element cachedResult = getCache().get(cacheKey);

        // This is the base search.
        if (cachedResult == null) {
            if (courseCode.length() == 3) {
                List<CourseOfferingInfo> searchResult = searchCourseOfferingsByCodeAndTerm(termId, courseCode, true);
                for (CourseOfferingInfo courseOffering : searchResult) {
                    results.add(courseOffering.getCourseOfferingCode());
                }
                getCache().put(new Element(cacheKey, results));
                return results;
            }
            if(courseCode.length() > 3) {
                // This is where the recursion happens. If you entered CHEM and it didn't find anything it will
                // recurse and search for CHE.
                for (String searchedCode : retrieveCourseCodes(termId, courseCode.substring(0, courseCode.length() - 1))) {
                    // for every course code, see if it's part of the Match.
                    if (searchedCode.startsWith(courseCode)) {
                        results.add(searchedCode);
                    }
                }

                getCache().put(new Element(cacheKey, results));
            }
        } else {
            return (List<String>) cachedResult.getValue();
        }

        return results;
    }

    /**
     *
     * @param termCode
     * @param courseCode
     * @return CourseOffering
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public static CourseOfferingInfo getCourseOfferingByCodeAndTerm(String termCode, String courseCode)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey(TERMID_COURSECODE_TO_CO, termCode, courseCode);
        Element cachedResult = getCache().get(cacheKey);
        if (cachedResult == null) {
            List<CourseOfferingInfo> courseOfferings = searchCourseOfferingsByCodeAndTerm(termCode, courseCode, false);
            return KSCollectionUtils.getOptionalZeroElement(courseOfferings);
        }

        return (CourseOfferingInfo) cachedResult.getValue();
    }

    /**
     * Do a search Query for course offerings for the given term code and course code. It also updates the cache with
     * course offerings by term code and actual course codes.
     *
     * Note: the course code could be only a partial course code when used by a suggest field for example.
     *
     * @param termId the term id
     * @param courseCode the actial course code or the starting characters of a course code
     * @return a list of CourseOfferings containing matching course codes
     */
    public static List<CourseOfferingInfo> searchCourseOfferingsByCodeAndTerm(String termId, String courseCode, boolean addWildCard)
            throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {

        ContextInfo context = ContextUtils.createDefaultContextInfo();
        String searchCode = addWildCard ? courseCode + "*" : courseCode;

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.like(CourseOfferingConstants.COURSEOFFERING_COURSE_OFFERING_CODE, searchCode),
                PredicateFactory.equalIgnoreCase(CourseOfferingConstants.ATP_ID, termId)));
        QueryByCriteria criteria = qbcBuilder.build();

        List<CourseOfferingInfo> results = AdminRegResourceLoader.getCourseOfferingService().searchForCourseOfferings(criteria, context);
        for (CourseOfferingInfo result : results) {
            MultiKey cacheKey = new MultiKey(TERMID_COURSECODE_TO_CO, termId, result.getCourseOfferingCode());
            getCache().put(new Element(cacheKey, result));
        }

        Collections.sort(results, CourseOfferingInfoComparator.getInstance());
        return results;
    }

    /**
     *
     * @param termCode
     * @return
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public static TermInfo getTermByCode(String termCode)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey(TERMCODE_TO_TERMINFO_KEY, termCode);
        Element cachedResult = getCache().get(cacheKey);
        if (cachedResult == null) {
            List<TermInfo> results = searchTermByCode(termCode);
            return KSCollectionUtils.getOptionalZeroElement(results);
        }

        return (TermInfo) cachedResult.getValue();
    }

    /**
     *
     * @param termCode
     * @return
     * @throws OperationFailedException
     * @throws MissingParameterException
     * @throws InvalidParameterException
     * @throws PermissionDeniedException
     */
    public static List<TermInfo> searchTermByCode(String termCode)
            throws OperationFailedException, MissingParameterException, InvalidParameterException, PermissionDeniedException {

        ContextInfo context = ContextUtils.createDefaultContextInfo();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));
        QueryByCriteria criteria = qbcBuilder.build();

        List<TermInfo> terms = AdminRegResourceLoader.getAcademicCalendarService().searchForTerms(criteria, context);
        for (TermInfo result : terms) {
            MultiKey cacheKey = new MultiKey(TERMCODE_TO_TERMINFO_KEY, result.getCode());
            getCache().put(new Element(cacheKey, result));
        }
        return terms;
    }

    public static class CourseOfferingInfoComparator implements Comparator<CourseOfferingInfo>{
        private static CourseOfferingInfoComparator instance = new CourseOfferingInfoComparator();

        public int compare(final CourseOfferingInfo co1, final CourseOfferingInfo co2) {
            return co1.getCourseOfferingCode().compareTo(co2.getCourseOfferingCode());
        }
        public static CourseOfferingInfoComparator getInstance() {
            return instance;
        }
    }
}
