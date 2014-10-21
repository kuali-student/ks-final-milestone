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
package org.kuali.student.ap.coursesearch.controller;

import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.ap.coursesearch.CourseSearchForm;
import org.kuali.student.ap.coursesearch.CourseSearchItem;
import org.kuali.student.ap.coursesearch.CourseSearchStrategy;
import org.kuali.student.ap.coursesearch.Credit;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.coursesearch.Hit;
import org.kuali.student.ap.coursesearch.QueryTokenizer;
import org.kuali.student.ap.coursesearch.dataobject.CourseSearchItemImpl;
import org.kuali.student.ap.coursesearch.form.CourseSearchFormImpl;
import org.kuali.student.ap.coursesearch.util.CourseLevelFacet;
import org.kuali.student.ap.coursesearch.util.CreditsFacet;
import org.kuali.student.ap.coursesearch.util.CurriculumFacet;
import org.kuali.student.ap.coursesearch.util.GenEduReqFacet;
import org.kuali.student.ap.coursesearch.util.TermsFacet;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseSearchStrategyImpl implements CourseSearchStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(CourseSearchStrategyImpl.class);

    private static final String KSAP_MAX_SEARCH_RESULTS_CONFIG_KEY = "ksap.search.results.max";

    private static WeakReference<Map<String, Credit>> creditMapRef;
    private static WeakReference<Map<String, String>> genEdMapRef;
    private static WeakReference<Map<String, String>> curriculumMapRef = null;

    private static Map<String, String> subjectAreaMap = null;

    private final String NONE = "none";
    private final String SAVED = "saved";
    private final String PLANNED = "planned";
    private final String SAVED_AND_PLANNED = "saved_and_planned";

    public static final String DIVISIONS_COMPONENTS = "divisions";
    public static final String KEYWORDDIVISIONS_COMPONENTS = "keywordDivisions";
    public static final String CODES_COMPONENTS = "codes";
    public static final String LEVELS_COMPONENTS = "levels";
    public static final String INCOMPLETEDCODES_COMPONENTS = "incompleteCodes";
    public static final String COMPLETEDCODES_COMPONENTS = "completedCodes";
    public static final String COMPLETEDLEVELS_COMPONENTS = "completedLevels";

    private QueryTokenizer queryTokenizer;

    @Override
    public QueryTokenizer getQueryTokenizer() {
        return queryTokenizer;
    }

    public void setQueryTokenizer(QueryTokenizer queryTokenizer) {
        this.queryTokenizer = queryTokenizer;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#createInitialSearchForm()
     */
    @Override
    public CourseSearchForm createInitialSearchForm() {
        CourseSearchFormImpl rv = new CourseSearchFormImpl();
        return rv;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#courseSearch(org.kuali.student.ap.coursesearch.CourseSearchForm, String)
     */
    @Override
    public List<CourseSearchItem> courseSearch(CourseSearchForm form, String studentId) {
        // Determine maximum number of search results allowed to return
        String maxCountProp = ConfigContext.getCurrentContextConfig()
                .getProperty(KSAP_MAX_SEARCH_RESULTS_CONFIG_KEY);
        int maxCount = maxCountProp != null && !"".equals(maxCountProp.trim()) ? Integer
                .valueOf(maxCountProp) : MAX_HITS;
        form.setLimitExceeded(false);

        // Build and run search, retrieving a list of course Ids
        List<SearchRequestInfo> requests = buildSearchRequests(form);

        // Process Current list of Requests into direct search queries
        requests = adjustSearchRequests(requests, form);

        // Search for course Ids on the list of requests
        List<Hit> hits = preformSearch(requests);
        List<String> courseIDs = new ArrayList<String>();
        for (Hit hit : hits) {
            courseIDs.add(hit.courseID);
        }

        // Filter results and trim if exceeds result limit
        List<? extends CourseSearchItem> courses = new ArrayList<CourseSearchItemImpl>();
        if (!courseIDs.isEmpty()) {
            courseIDs = termfilterCourseIds(courseIDs, form.getSearchTerm());
            if (courseIDs.size() > maxCount) {
                courseIDs = courseIDs.subList(0,maxCount);
                form.setLimitExceeded(true);
            }
        }

        // Populate course data for found course ids
        if (!courseIDs.isEmpty()) {
            courses = loadCourseItems(courseIDs, studentId, form);
        }

        // Populate Curriculum Map based on orgs for found courses
        populateCurriculumMap(courses);

        // Populate Facets for the found courses
        populateFacets(form, courses);

        return (List<CourseSearchItem>)courses;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#buildSearchRequests(org.kuali.student.ap.coursesearch.CourseSearchForm)
     */
    @Override
    public List<SearchRequestInfo> buildSearchRequests(CourseSearchForm form) {
        LOG.debug("Start Of Method buildSearchRequests in CourseSearchStrategy: {}",
                System.currentTimeMillis());

        // To keep search from being case specific all text is uppercased
        String query = form.getSearchQuery().toUpperCase();

        // Unchanging query for full text search
        String pureQuery = query;

        //Search Components
        List<String> divisions = new ArrayList<String>();
        List<String> codes;
        List<String> levels;
        List<String> incompleteCodes;
        List<String> completedCodes;
        List<String> completedLevels;
        List<String> keywordDivisions;

        //Search queries
        List<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();

        // Extract components from query
        levels = queryTokenizer.extractCourseLevels(query);
        codes = queryTokenizer.extractCourseCodes(query);
        queryTokenizer.extractDivisions(fetchCourseDivisions(), query, divisions, Boolean.parseBoolean(
                ConfigContext.getCurrentContextConfig().getProperty(CourseSearchConstants.COURSE_SEARCH_DIVISION_SPACEALLOWED)));

        // Extract divisions using their keywords.
        if (subjectAreaMap == null || subjectAreaMap.size() == 0) {
            subjectAreaMap = KsapFrameworkServiceLocator.getOrgHelper().getSubjectAreas();
        }
        keywordDivisions = queryTokenizer.extractDivisionsFromSubjectKeywords(query, subjectAreaMap);

        // remove found levels and codes to find incomplete code components
        for (String level : levels) query = query.replace(level, "");
        for (String code : codes) query = query.replace(code, "");
        incompleteCodes = queryTokenizer.extractIncompleteCourseCodes(query, divisions);


        completedCodes = queryTokenizer.extractCompleteCourseCodes(pureQuery, divisions, codes);
        completedLevels = queryTokenizer.extractCompleteCourseLevels(pureQuery, divisions, levels);

        // Remove found completed levels to not make full text for them
        for (String completedLevel : completedLevels) {
            String division = completedLevel.substring(0, completedLevel.indexOf(','));
            String level = completedLevel.substring(completedLevel.indexOf(',') + 1);
            pureQuery = pureQuery.replace(division + level, "");
        }

        // Remove found completed codes to not make full text for them
        for (String completedCode : completedCodes) {
            String division = completedCode.substring(0, completedCode.indexOf(','));
            String code = completedCode.substring(completedCode.indexOf(',') + 1);
            pureQuery = pureQuery.replace(division + code, "");
        }

        LOG.debug("Start of method addComponentRequests of CourseSearchStrategy: {}",
                System.currentTimeMillis());
        Map<String,List<String>> componentMap = new HashMap<String,List<String>>();
        componentMap.put(DIVISIONS_COMPONENTS, divisions);
        componentMap.put(KEYWORDDIVISIONS_COMPONENTS, keywordDivisions);
        componentMap.put(CODES_COMPONENTS, codes);
        componentMap.put(LEVELS_COMPONENTS, levels);
        componentMap.put(INCOMPLETEDCODES_COMPONENTS, incompleteCodes);
        componentMap.put(COMPLETEDCODES_COMPONENTS, completedCodes);
        componentMap.put(COMPLETEDLEVELS_COMPONENTS, completedLevels);
        addComponentRequests(componentMap, requests);
        LOG.debug("End of method addComponentRequests of CourseSearchStrategy: {}",
                System.currentTimeMillis());

        LOG.debug("Start of method addFullTextSearches of CourseSearchStrategy: {}",
                System.currentTimeMillis());
        addFullTextRequests(pureQuery, requests, form.getSearchTerm());
        LOG.debug("End of method addFullTextSearches of CourseSearchStrategy: {}",
                System.currentTimeMillis());

        LOG.debug("End Of Method buildSearchRequests in CourseSearchStrategy: {}",
                System.currentTimeMillis());

        return requests;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#adjustSearchRequests(java.util.List, org.kuali.student.ap.coursesearch.CourseSearchForm)
     */
    @Override
    public List<SearchRequestInfo> adjustSearchRequests(List<SearchRequestInfo> requests, CourseSearchForm form) {
        LOG.debug("Start of method adjustSearchRequests in CourseSearchStrategy: {}",
                System.currentTimeMillis());

        // Remove Duplicates
        List<SearchRequestInfo> prunedRequests = new ArrayList<SearchRequestInfo>();
        for (SearchRequestInfo request : requests) {
            if (!prunedRequests.contains(request)) prunedRequests.add(request);
        }
        requests = prunedRequests;

        LOG.debug("End of adjustSearchRequests method in CourseSearchStrategy: {}",
                System.currentTimeMillis());

        return requests;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#preformSearch(java.util.List)
     */
    @Override
    public List<Hit> preformSearch(List<SearchRequestInfo> requests) {
        LOG.debug("Start of preformSearch of CourseSearchController: {}",
                System.currentTimeMillis());
        List<Hit> hits = new java.util.LinkedList<Hit>();
        Set<String> seen = new java.util.HashSet<String>();
        String id;
        for (SearchRequestInfo request : requests)
            try {
                SearchResult results = KsapFrameworkServiceLocator.getSearchService()
                        .search(request, KsapFrameworkServiceLocator.getContext().getContextInfo());
                for (SearchResultRow row : results.getRows()){
                    if (seen.add(id = KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID)))
                        hits.add(new Hit(id));
                }
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException(
                        "Invalid course ID or CLU lookup error", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException(
                        "Invalid course ID or CLU lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalStateException("CLU lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalArgumentException("CLU lookup error", e);
            }
        LOG.debug("End of preformSearch of CourseSearchController: {}",
                System.currentTimeMillis());
        return hits;
    }

    /**
     * Currently supports filtering by:
     * Term
     *
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#filterSearchResults(java.util.List, org.kuali.student.ap.coursesearch.CourseSearchForm)
     */
    @Override
    public List<String> filterSearchResults(List<String> courseIds, CourseSearchForm form){
        // Filter results by term
        List<String> filteredIds = termfilterCourseIds(courseIds, form.getSearchTerm());

        return filteredIds;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#loadCourseItems(java.util.List, String, org.kuali.student.ap.coursesearch.CourseSearchForm)
     */
    @Override
    public List<? extends CourseSearchItem> loadCourseItems(List<String> courseIDs, String studentId, CourseSearchForm form) {
        LOG.debug("Start of method getCurrentVersionOfCourse of CourseSearchController: {}",
                System.currentTimeMillis());
        List<CourseSearchItem> listOfCourses = new ArrayList<CourseSearchItem>();

        // Get course information for each course from the course id
        SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSE_INFO_BY_ID_KEY);
        request.addParam(CourseSearchConstants.SearchParameters.VERSION_IND_ID_LIST, courseIDs);
        SearchResult result;
        try {
            result = KsapFrameworkServiceLocator.getSearchService().search(
                    request,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException(
                    "Invalid course ID or CLU lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException(
                    "Invalid course ID or CLU lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("CLU lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("CLU lookup error", e);
        }

        // Create and fill in course object
        if ((result != null) && (!result.getRows().isEmpty())) {
            for (String courseId : courseIDs) {
                for (SearchResultRow row : result.getRows()) {
                    String id = KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.CLU_ID);
                    String versionId = KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID);
                    // Course information is filled in based on the original result order
                    if (versionId.equals(courseId)) {
                        CourseSearchItemImpl course = new CourseSearchItemImpl();
                        course.setCourseId(id);
                        course.setSearchExceeded(form.isLimitExceeded());
                        course.setSubject(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.COURSE_SUBJECT));
                        course.setNumber(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.COURSE_NUMBER));
                        course.setLevel(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.COURSE_LEVEL));
                        course.setCourseName(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.COURSE_NAME));
                        course.setCode(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.COURSE_CODE));
                        course.setVersionIndependentId(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID));
                        CreditsFormatter.Range range = KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().getRange(id);
                        if (range != null) {
                            course.setCreditMin(range.getMin().floatValue());
                            course.setCreditMax(range.getMax().floatValue());
                            course.setMultipleCredits(range.getMultiple());
                            course.setCreditType(CourseSearchItem.CreditType.valueOf(range.getType()));
                            course.setCredit(KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().formatCredits(range));
                            if (range.getMultiple() != null && range.getMultiple().size() > 0) {
                                //Override the display with a "truncated" version
                                course.setCredit(KsapFrameworkServiceLocator.getCourseHelper().getCreditsFormatter().formatCreditsTruncated(range.getMultiple(), course.getCredit()));
                            }
                        }
                        listOfCourses.add(course);
                        break;
                    }
                }
            }
        }

        // Load additional data for each course
        loadScheduledTerms(listOfCourses);
        loadTermsOffered(listOfCourses, courseIDs);
        loadGenEduReqs(listOfCourses);

        LOG.debug("End of method getCurrentVersionOfCourse of CourseSearchController: {}",
                System.currentTimeMillis());
        return listOfCourses;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#addComponentRequests(java.util.Map, java.util.List)
     */
    @Override
    public void addComponentRequests(Map<String, List<String>> componentMap, List<SearchRequestInfo> requests) {

        // Separate components from the map
        List<String> divisions = componentMap.get(DIVISIONS_COMPONENTS);
        List<String> keywordDivisions = componentMap.get(KEYWORDDIVISIONS_COMPONENTS);
        List<String> codes = componentMap.get(CODES_COMPONENTS);
        List<String> levels = componentMap.get(LEVELS_COMPONENTS);
        List<String> incompleteCodes = componentMap.get(INCOMPLETEDCODES_COMPONENTS);
        List<String> completeCodes = componentMap.get(COMPLETEDCODES_COMPONENTS);
        List<String> completeLevels = componentMap.get(COMPLETEDLEVELS_COMPONENTS);

        // Insure no null lists
        if(divisions == null) divisions = Collections.EMPTY_LIST;
        if(keywordDivisions == null) keywordDivisions = Collections.EMPTY_LIST;
        if(codes == null) codes = Collections.EMPTY_LIST;
        if(levels == null) levels = Collections.EMPTY_LIST;
        if(incompleteCodes == null) incompleteCodes = Collections.EMPTY_LIST;
        if(completeCodes == null) completeCodes = Collections.EMPTY_LIST;
        if(completeLevels == null) completeLevels = Collections.EMPTY_LIST;

        // Sort Components
        Collections.sort(divisions);
        Collections.sort(codes);
        Collections.sort(levels);
        Collections.sort(incompleteCodes);

        // Add keyword divisions after sort to maintain order of keyword divisions
        divisions.addAll(keywordDivisions);

        // Combine search requests in execution order
        requests.addAll(addCompletedCodeRequests(completeCodes, divisions, codes));
        requests.addAll(addDivisionAndCodeRequests(divisions, codes));
        requests.addAll(addCompletedLevelRequests(completeLevels, divisions, levels));
        requests.addAll(addDivisionAndLevelRequests(divisions, levels));
        requests.addAll(addIncompleteCodeRequests(incompleteCodes, divisions));
        requests.addAll(addDivisionRequests(divisions));
        requests.addAll(addCodeRequests(codes));
        requests.addAll(addLevelRequests(levels));

    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#addFullTextRequests(String, java.util.List, String)
     */
    public void addFullTextRequests(String query, List<SearchRequestInfo> requests, String searchTerm) {
        //find all tokens in the query string
        List<QueryTokenizer.Token> tokens = queryTokenizer.tokenize(query);
        requests.addAll(addTitleRequests(tokens, searchTerm));
        requests.addAll(addDescriptionRequests(tokens, searchTerm));
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#getCurriculumMap(java.util.Set)
     */
    @Override
    public Map<String, String> getCurriculumMap(Set<String> orgIds) {
        Map<String, String> rv = curriculumMapRef == null ? null : curriculumMapRef
                .get();
        if (rv == null) {
            Map<String, String> curriculumMap = new java.util.LinkedHashMap<String, String>();
            ContextInfo contextInfo = KsapFrameworkServiceLocator.getContext()
                    .getContextInfo();
            List<OrgInfo> orgs = new ArrayList<OrgInfo>();

            try {
                if (orgIds != null && orgIds.size() > 0) {
                    orgs = KsapFrameworkServiceLocator.getOrganizationService().getOrgsByIds(new ArrayList<String>(orgIds),
                            contextInfo);
                }
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("Org lookup error", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("Org lookup error", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("Org lookup error", e);
            } catch (OperationFailedException e) {
                throw new IllegalStateException("Org lookup error", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalStateException("Org lookup error", e);
            }

            for (OrgInfo org : orgs) {
                curriculumMap.put(org.getShortName(), org.getLongName());
            }

            curriculumMapRef = new WeakReference<Map<String, String>>(
                    rv = Collections.unmodifiableMap(Collections
                            .synchronizedMap(curriculumMap)));
        }
        return rv;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#getGenEdMap()
     */
    @Override
    public Map<String, String> getGenEdMap() {
        Map<String, String> rv = genEdMapRef == null ? null : genEdMapRef
                .get();
        if (rv == null) {
            Map<String, String> genEdMap = new java.util.LinkedHashMap<String, String>();
            genEdMapRef = new WeakReference<Map<String, String>>(
                    rv = Collections.synchronizedMap(genEdMap));
        }
        return rv;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#getCreditMap()
     */
    @Override
    public Map<String, Credit> getCreditMap() {
        // Unused
        return null;
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#populateFacets(org.kuali.student.ap.coursesearch.CourseSearchForm, java.util.List)
     */
    @Override
    public void populateFacets(CourseSearchForm form, List<? extends CourseSearchItem> courses) {
        LOG.debug("Start of method populateFacets of CourseSearchController: {}",
                System.currentTimeMillis());
        // Initialize facets.
        CurriculumFacet curriculumFacet = new CurriculumFacet();
        CreditsFacet creditsFacet = new CreditsFacet();
        CourseLevelFacet courseLevelFacet = new CourseLevelFacet();
        GenEduReqFacet genEduReqFacet = new GenEduReqFacet();
        TermsFacet termsFacet = new TermsFacet();

        // Update facet info and code the item.
        for (CourseSearchItem course : courses) {
            curriculumFacet.process(course);
            courseLevelFacet.process(course);
            genEduReqFacet.process(course);
            creditsFacet.process(course);
            termsFacet.process(course);
        }

        LOG.debug("End of method populateFacets of CourseSearchController: {}",
                System.currentTimeMillis());
    }

    /**
     * Creates a list of level only search requests from provided level components
     * Assumed levels are in format #XX
     *
     * @param levels - List of level components
     * @return A list of completed search requests for level searches
     */
    private List<SearchRequestInfo> addLevelRequests(List<String> levels) {
        List<SearchRequestInfo> searches = new ArrayList<SearchRequestInfo>();

        // Create level only search
        for (String level : levels) {
            // Converts "1XX" to "100"
            level = level.substring(0, 1) + "00";
            SearchRequestInfo request = new SearchRequestInfo(
                    CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_LEVEL_KEY);
            request.addParam(CourseSearchConstants.SearchParameters.LEVEL, level);
            searches.add(request);
        }

        return searches;
    }

    /**
     * Creates a list of code only search requests from the provided code components
     *
     * @param codes - List of code components
     * @return A list of completed search requests for code searches
     */
    private List<SearchRequestInfo> addCodeRequests(List<String> codes) {
        List<SearchRequestInfo> searches = new ArrayList<SearchRequestInfo>();

        // Create course code only search
        for (String code : codes) {
            SearchRequestInfo request = new SearchRequestInfo(
                    CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_CODE_KEY);
            request.addParam(CourseSearchConstants.SearchParameters.CODE, code);
            searches.add(request);
        }

        return searches;
    }

    /**
     * Creates a list of division only search requests from the provided division components
     *
     * @param divisions - List of division components
     * @return A list of completed search requests for division searches
     */
    private List<SearchRequestInfo> addDivisionRequests(List<String> divisions) {
        List<SearchRequestInfo> searches = new ArrayList<SearchRequestInfo>();

        for (String division : divisions) {
            SearchRequestInfo request = new SearchRequestInfo(
                    CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_DIVISION_KEY);
            request.addParam(CourseSearchConstants.SearchParameters.DIVISION, division);
            searches.add(request);
        }

        return searches;
    }

    /**
     * Creates a list of division and code search requests from the provided components
     *
     * @param divisions - List of division components
     * @param codes - List of code components
     * @return A list of completed search requests for division and codes searches
     */
    private List<SearchRequestInfo> addDivisionAndCodeRequests(List<String> divisions, List<String> codes) {
        List<SearchRequestInfo> searches = new ArrayList<SearchRequestInfo>();

        List<String> seenDivisions = new ArrayList<String>();
        for (String division : divisions) {
            // Skip if already seen
            if (seenDivisions.contains(division)) continue;
            seenDivisions.add(division);
            List<String> seenCodes = new ArrayList<String>();
            for (String code : codes) {
                // Skip if already seen
                if (seenCodes.contains(code)) continue;
                seenCodes.add(code);
                SearchRequestInfo request = new SearchRequestInfo(
                        CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_CODE_KEY);
                request.addParam(CourseSearchConstants.SearchParameters.DIVISION, division);
                request.addParam(CourseSearchConstants.SearchParameters.CODE, code);
                searches.add(request);
            }
        }

        return searches;
    }

    /**
     * Creates a list of division and level search requests from the provided components
     *
     * @param divisions - List of division components
     * @param levels - List of level components
     * @return A list of completed search requests for division and level searches
     */
    private List<SearchRequestInfo> addDivisionAndLevelRequests(List<String> divisions, List<String> levels) {
        List<SearchRequestInfo> searches = new ArrayList<SearchRequestInfo>();

        List<String> seenDivisions = new ArrayList<String>();
        for (String division : divisions) {
            // Skip if already seen
            if (seenDivisions.contains(division)) continue;
            seenDivisions.add(division);
            List<String> seenLevels = new ArrayList<String>();
            for (String level : levels) {
                // Skip if already seen
                if (seenLevels.contains(level)) continue;
                seenLevels.add(level);

                // Converts "1XX" to "100"
                level = level.substring(0, 1) + "00";

                SearchRequestInfo request = new SearchRequestInfo(
                        CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_LEVEL_KEY);
                request.addParam(CourseSearchConstants.SearchParameters.DIVISION, division);
                request.addParam(CourseSearchConstants.SearchParameters.LEVEL, level);
                searches.add(request);
            }
        }

        return searches;
    }

    /**
     * Creates a list of division and level search requests from the provided components
     * These requests are made from components that have strong context between them
     * Used components are removed from their respective lists so they are not used again when creating normal division
     * and levels requests
     *
     * @param completeLevels - List of completed level components
     * @param divisions - List of division components
     * @param levels - List of level components
     * @return A list of completed level search requests for division and level searches
     */
    private List<SearchRequestInfo> addCompletedLevelRequests(List<String> completeLevels, List<String> divisions, List<String> levels) {
        List<SearchRequestInfo> searches = new ArrayList<SearchRequestInfo>();

        // Complete Level searches
        for (String completedLevel : completeLevels) {
            // Break into pieces
            String division = completedLevel.substring(0, completedLevel.indexOf(","));
            String level = completedLevel.substring(completedLevel.indexOf(",") + 1);

            // Remove an entry from the lists of pieces since were using one
            levels.remove(level);
            divisions.remove(division);

            // Create Search
            SearchRequestInfo request = new SearchRequestInfo(
                    CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_LEVEL_KEY);
            // Converts "1XX" to "100"
            level = level.substring(0, 1) + "00";
            request.addParam(CourseSearchConstants.SearchParameters.DIVISION, division);
            request.addParam(CourseSearchConstants.SearchParameters.LEVEL, level);
            searches.add(request);

        }

        return searches;
    }

    /**
     * Creates a list of division and codes search requests from the provided components
     * These requests are made from components that have strong context between them
     * Used components are removed from their respective lists so they are not used again when creating normal division
     * and code requests
     *
     * @param completeCodes - List of completed codes components
     * @param divisions - List of division components
     * @param codes - List of codes components
     * @return A list of completed code search requests for division and codes searches
     */
    private List<SearchRequestInfo> addCompletedCodeRequests(List<String> completeCodes, List<String> divisions, List<String> codes) {
        List<SearchRequestInfo> searches = new ArrayList<SearchRequestInfo>();

        // Complete Code searches
        for (String completedCode : completeCodes) {
            // Break into pieces
            String division = completedCode.substring(0, completedCode.indexOf(','));
            String code = completedCode.substring(completedCode.indexOf(',') + 1);

            // Remove an entry from the lists of pieces since were using one
            codes.remove(code);
            divisions.remove(division);

            //Create search
            SearchRequestInfo request = new SearchRequestInfo(
                    CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_DIVISION_AND_CODE_KEY);
            request.addParam(CourseSearchConstants.SearchParameters.DIVISION, division);
            request.addParam(CourseSearchConstants.SearchParameters.CODE, code);
            searches.add(request);
        }

        return searches;
    }

    /**
     * Creates a list of incomplete code search requests from the provided components
     * Incomplete codes are division codes + numbers but not a full code
     * (Example: Engl2 or Engl20)
     *
     * @param incompleteCodes - List of incomplete code components
     * @param divisions - List of division components
     * @return A list of incomplete code search requests
     */
    private List<SearchRequestInfo> addIncompleteCodeRequests(List<String> incompleteCodes, List<String> divisions) {
        List<SearchRequestInfo> searches = new ArrayList<SearchRequestInfo>();

        // Create course code only search
        List<String> seenIncompleteCodes = new ArrayList<String>();
        for (String incompleteCode : incompleteCodes) {
            // Skip if already seen
            if (seenIncompleteCodes.contains(incompleteCode)) continue;
            seenIncompleteCodes.add(incompleteCode);

            // Remove an entry from the lists of pieces since were using one
            for (int i = 0; i < divisions.size(); i++) {
                String division = divisions.get(i);
                if (incompleteCode.matches(division + "[0-9]+")) {
                    divisions.remove(i);
                    break;
                }
            }

            SearchRequestInfo request = new SearchRequestInfo(
                    CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_FULL_CODE_KEY);
            request.addParam(CourseSearchConstants.SearchParameters.CODE, incompleteCode);
            searches.add(request);
        }

        return searches;
    }

    /**
     * Creates a list of title search requests from the provided tokens
     * Title searches are created for both Course and Course Offering titles.
     *
     * @param tokens - List of text tokens
     * @param searchTerm - Term filter for the search (Used for CO title search)
     * @return A list of title search requests
     */
    private List<SearchRequestInfo> addTitleRequests(List<QueryTokenizer.Token> tokens, String searchTerm) {
        List<SearchRequestInfo> searches = new ArrayList<SearchRequestInfo>();

        for (QueryTokenizer.Token token : tokens) {
            // Convert token to its correct text
            String queryText = queryTokenizer.cleanToken(token);

            // Skip if query is less than 3 characters
            if (queryText != null && queryText.length() < 3) continue;

            // Add course title search
            SearchRequestInfo requestTitle = new SearchRequestInfo(
                    CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_CLU_TITLE_KEY);
            requestTitle.addParam(CourseSearchConstants.SearchParameters.QUERYTEXT, queryText.trim());
            searches.add(requestTitle);

            // Add course offering title search
            SearchRequestInfo requestOffering = new SearchRequestInfo(
                    CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_LUI_TITLE_KEY);
            requestOffering.addParam(CourseSearchConstants.SearchParameters.QUERYTEXT, queryText.trim());
            requestOffering.addParam(CourseSearchConstants.SearchParameters.ATP_ID_LIST, getTermsToFilterOn(searchTerm));
            searches.add(requestOffering);
        }

        return searches;
    }

    /**
     * Creates a list of description search requests from the provided tokens
     * Description searches are created for both Course and Course Offering titles.
     *
     * @param tokens - List of text tokens
     * @param searchTerm - Term filter for the search (Used for CO title search)
     * @return A list of description search requests
     */
    private List<SearchRequestInfo> addDescriptionRequests(List<QueryTokenizer.Token> tokens, String searchTerm) {
        List<SearchRequestInfo> searches = new ArrayList<SearchRequestInfo>();

        for (QueryTokenizer.Token token : tokens) {
            // Convert token to its correct text
            String queryText = queryTokenizer.cleanToken(token);

            // Skip if query is less than 3 characters
            if (queryText != null && queryText.length() < 3) continue;

            // Add course description search
            SearchRequestInfo requestDescription = new SearchRequestInfo(
                    CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_CLU_DESCRIPTION_KEY);
            requestDescription.addParam(CourseSearchConstants.SearchParameters.QUERYTEXT, queryText.trim());
            searches.add(requestDescription);

            // Add course offering description search
            SearchRequestInfo requestOfferingDescr = new SearchRequestInfo(
                    CourseSearchConstants.KSAP_COURSE_SEARCH_LU_BY_LUI_DESCRIPTION_KEY);
            requestOfferingDescr.addParam(CourseSearchConstants.SearchParameters.QUERYTEXT, queryText.trim());
            requestOfferingDescr.addParam(CourseSearchConstants.SearchParameters.ATP_ID_LIST, getTermsToFilterOn(searchTerm));
            searches.add(requestOfferingDescr);
        }

        return searches;
    }

    /**
     * Filters the result course ids from the search based on the term filter
     *
     * @param courseIds  - Full list of course ids found by the search
     * @param termFilter - Term to filter by
     * @return A list of course ids with offerings matching the selected filter
     */
    private List<String> termfilterCourseIds(List<String> courseIds, String termFilter) {
        LOG.debug("Start of method termfilterCourseIds of CourseSearchController: {}",
                System.currentTimeMillis());

        // If any term option is select return list as is, no filtering needed.
        if (termFilter.equals(CourseSearchForm.SEARCH_TERM_ANY_ITEM)) {
            return courseIds;
        }

        // Build list of valid terms based on the filter
        List<Term> terms = new ArrayList<Term>();
        if (termFilter.equals(CourseSearchForm.SEARCH_TERM_SCHEDULED)) {
            // Any Scheduled term selected
            List<Term> currentScheduled = KsapFrameworkServiceLocator.getTermHelper().getCurrentTermsWithPublishedSOC();
            List<Term> futureScheduled = KsapFrameworkServiceLocator.getTermHelper().getFutureTermsWithPublishedSOC();
            if (currentScheduled != null) terms.addAll(currentScheduled);
            if (futureScheduled != null) terms.addAll(futureScheduled);
        } else {
            // Single Term selected
            terms.add(KsapFrameworkServiceLocator.getTermHelper().getTerm(termFilter));
        }
        List<String> filteredIds = new ArrayList<String>();
        try {
            List<String> offeredCourseIds = new ArrayList<String>();
            for(Term term : terms){
                SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEVERSIONIDS_BY_TERM_SCHEDULED_KEY);
                request.addParam(CourseSearchConstants.SearchParameters.ATP_ID, term.getId());
                List<SearchResultRowInfo> rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                        KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
                for(SearchResultRowInfo row : rows){
                    offeredCourseIds.add(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID));
                }
            }

            // Fill filtered id list
            for (String courseId : courseIds) {
                if(offeredCourseIds.contains(courseId)){
                    filteredIds.add(courseId);
                }
            }
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("ATP lookup failed", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("ATP lookup failed", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("ATP lookup failed", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("ATP lookup failed", e);
        }
        LOG.debug("End of method termfilterCourseIds of CourseSearchController: {}",
                System.currentTimeMillis());
        return filteredIds;

    }

    /**
     * Load scheduling information for courses based on their course offerings
     *
     * @param courses - List of courses to load information for.
     */
    private void loadScheduledTerms(List<? extends CourseSearchItem> courses) {
        LOG.debug("Start of method loadScheduledTerms of CourseSearchController: {}",
                System.currentTimeMillis());

        // Any Scheduled term selected
        List<Term> terms = new ArrayList<Term>();
        List<Term> currentScheduled = KsapFrameworkServiceLocator.getTermHelper().getCurrentTermsWithPublishedSOC();
        List<Term> futureScheduled = KsapFrameworkServiceLocator.getTermHelper().getFutureTermsWithPublishedSOC();
        if (currentScheduled != null) terms.addAll(currentScheduled);
        if (futureScheduled != null) terms.addAll(futureScheduled);
        Map<String,List<String>> offeredCourseIdMap= new HashMap<String,List<String>>();
        try {
            for(Term term : terms){
                SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEVERSIONIDS_BY_TERM_SCHEDULED_KEY);
                request.addParam(CourseSearchConstants.SearchParameters.ATP_ID, term.getId());
                List<SearchResultRowInfo> rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                        KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
                for(SearchResultRowInfo row : rows){
                    String id = KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.COURSE_VERSION_INDEPENDENT_ID);
                    if(offeredCourseIdMap.containsKey(id)){
                        List<String> offeredTermIds = offeredCourseIdMap.get(id);
                        offeredTermIds.add(term.getId());
                        offeredCourseIdMap.put(id,offeredTermIds);
                    }else{
                        List<String> offeredTermIds = new ArrayList<String>();
                        offeredTermIds.add(term.getId());
                        offeredCourseIdMap.put(id,offeredTermIds);
                    }
                }

            }
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("ATP lookup failed", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("ATP lookup failed", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("ATP lookup failed", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("ATP lookup failed", e);
        }

        for(CourseSearchItem course : courses){
            ((CourseSearchItemImpl)course).setScheduledTerms(offeredCourseIdMap.get(course.getVersionIndependentId()));
        }

        LOG.debug("End of method loadScheduledTerms of CourseSearchController: {}",
                System.currentTimeMillis());
    }

    /**
     * Loads projected term information for the courses.
     * This information is found in the KSLU_CLU_ATP_TYPE_KEY table
     *
     * @param courses   - The list of course information for the courses
     * @param courseIDs - The list of course ids for the courses.
     */
    private void loadTermsOffered(List<? extends CourseSearchItem> courses, final List<String> courseIDs) {
        LOG.debug("Start of method loadTermsOffered of CourseSearchController: {}",
                System.currentTimeMillis());

        String termTypes[] = ConfigContext.getCurrentContextConfig().getProperty(
                CourseSearchConstants.TERMS_OFFERED_SORTED_KEY).split(",");
        Map<String,List<String>> offeredMap= new HashMap<String,List<String>>();
        try {

            for(String type : termTypes){
                SearchRequestInfo request = new SearchRequestInfo(CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEIDS_BY_TERM_OFFERED_KEY);
                request.addParam(CourseSearchConstants.SearchParameters.ATP_TYPE_KEY, type);
                List<SearchResultRowInfo> rows = KsapFrameworkServiceLocator.getSearchService().search(request,
                        KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
                for(SearchResultRowInfo row : rows){
                    String id = KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.CLU_ID);
                    if(offeredMap.containsKey(id)){
                        List<String> offeredTermIds = offeredMap.get(id);
                        offeredTermIds.add(type);
                        offeredMap.put(id,offeredTermIds);
                    }else{
                        List<String> offeredTermIds = new ArrayList<String>();
                        offeredTermIds.add(type);
                        offeredMap.put(id,offeredTermIds);
                    }
                }
            }


        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("ATP lookup failed", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("ATP lookup failed", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("ATP lookup failed", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("ATP lookup failed", e);
        }

        for (CourseSearchItem course : courses) {
            if (offeredMap.containsKey(course.getCourseId())) {
                List<String> termsOffered = offeredMap.get(course.getCourseId());
                Collections.sort(termsOffered);
                ((CourseSearchItemImpl)course).setTermInfoList(termsOffered);
            }
        }


        LOG.debug("End of method loadTermsOffered of CourseSearchController: {}",
                System.currentTimeMillis());
    }

    /**
     * Loads the gen ed information for the courses.
     * Gen Ed information is store as course sets that can be returned using the independent version id
     *
     * @param courses - The list of course information for the courses
     */
    private void loadGenEduReqs(List<? extends CourseSearchItem> courses) {
        LOG.debug("Start of method loadGenEduReqs of CourseSearchController: {}",
                System.currentTimeMillis());

        Map<String,List<String>> genEdCourses = new HashMap<String,List<String>>();
        SearchRequestInfo requestGenEdValues = new SearchRequestInfo(
                CourseSearchConstants.KSAP_COURSE_SEARCH_GENERAL_EDUCATION_VALUES_KEY);


        // Search for the requirements
        SearchResult result;
        try {
            List<SearchResultRowInfo> genEdRows = KsapFrameworkServiceLocator.getSearchService().search(requestGenEdValues,
                    KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
            for(SearchResultRowInfo row : genEdRows){
                String id =  KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.CLU_SET_ID);
                String name =  KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.CLU_SET_NAME);
                String value =  KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.CLU_SET_ATTR_VALUE);
                getGenEdMap().put(value, name);

                // Create map relating course to list of gen ed values
                SearchRequestInfo requestGenEdCourses = new SearchRequestInfo(
                        CourseSearchConstants.KSAP_COURSE_SEARCH_COURSEIDS_BY_GENERAL_EDUCATION_KEY);
                requestGenEdCourses.addParam(CourseSearchConstants.SearchParameters.GENED_KEY, id);
                List<SearchResultRowInfo> genEdCourseRows = KsapFrameworkServiceLocator.getSearchService().search(requestGenEdCourses,
                        KsapFrameworkServiceLocator.getContext().getContextInfo()).getRows();
                for(SearchResultRowInfo courseRow : genEdCourseRows){
                    String cluId = KsapHelperUtil.getCellValue(courseRow, CourseSearchConstants.SearchResultColumns.CLU_ID);
                    if(genEdCourses.containsKey(cluId)){
                        List<String> genIds = genEdCourses.get(cluId);
                        genIds.add(value);
                        genEdCourses.put(cluId,genIds);
                    }else{
                        List<String> genIds = new ArrayList<String>();
                        genIds.add(value);
                        genEdCourses.put(cluId,genIds);
                    }
                }
            }
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException(
                    "Invalid course ID or CLU lookup error", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException(
                    "Invalid course ID or CLU lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("CLU lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("CLU lookup error", e);
        }

        // Fill in the course information
        for (CourseSearchItem course : courses) {
            if(genEdCourses.containsKey(course.getCourseId())){
                ((CourseSearchItemImpl)course).setGenEduReqs(genEdCourses.get(course.getCourseId()));
            }
        }

        LOG.debug("End of method loadGenEduReqs of CourseSearchController: {}",
                System.currentTimeMillis());
    }

    /**
     * Creates a map relating the course to its status in the plan.
     *
     * @param studentID - Id of the user running the search
     * @return A map of the plan state for a course.
     */
    private Map<String, String> getCourseStatusMap(String studentID) {
        LOG.debug("Start of method getCourseStatusMap of CourseSearchController: {}",
                System.currentTimeMillis());
        ContextInfo context = KsapFrameworkServiceLocator.getContext().getContextInfo();

        String planTypeKey = AcademicPlanServiceConstants.LEARNING_PLAN_TYPE_PLAN;

        Map<String, String> savedCourseSet = new HashMap<String, String>();

        /*
		 * For each plan item in each plan set the state based on the type.
		 */
        // Find list of learning plans
        List<LearningPlanInfo> learningPlanList;
        try {
            learningPlanList = KsapFrameworkServiceLocator.getAcademicPlanService().getLearningPlansForStudentByType(
                    studentID, planTypeKey,context);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("LP lookup error", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("LP lookup error", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("LP lookup error", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("LP lookup permission error", e);
        }

        // Process list of learning plan's entries
        for (LearningPlan learningPlan : learningPlanList) {
            String learningPlanID = learningPlan.getId();
            List<PlanItem> planItemList = KsapFrameworkServiceLocator.getPlanHelper().getPlanItems(learningPlanID);

            // Process plan items in learning plan
            for (PlanItem planItem : planItemList) {
                //Notes from Bonnie: right now it's a mix -- planned and backed planItems are still using
                //courseId for RefObjectId but bookmark items have been switched to use Clu's versionIndependentId.
                String versionIndependentId = planItem.getRefObjectId();

                //4 possible states: none, planned, saved, or both

                String state = NONE; //initial default state

                if (planItem.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.WISHLIST)) {
                    state = SAVED;
                } else if (planItem.getCategory().equals(
                        AcademicPlanServiceConstants.ItemCategory.PLANNED)
                        || planItem.getCategory().equals(
                        AcademicPlanServiceConstants.ItemCategory.BACKUP)
                        || planItem.getCategory().equals(
                        AcademicPlanServiceConstants.ItemCategory.CART)) {
                    state = PLANNED;
                } else {
                    throw new RuntimeException("Unknown plan item type.");
                }

                if (!savedCourseSet.containsKey(versionIndependentId)) {
                    // First time through the loop, add state from above directly
                    savedCourseSet.put(versionIndependentId, state);
                } else if (savedCourseSet.get(versionIndependentId).equals(NONE)) {
                    // Was through once already, didn't get saved or planned
                    savedCourseSet.put(versionIndependentId, state);
                } else if ((savedCourseSet.get(versionIndependentId).equals(SAVED) && state.equals(PLANNED))
                        || (savedCourseSet.get(versionIndependentId).equals(PLANNED) && state.equals(SAVED))) {
                    //previously had saved OR planned... now it must have both
                    savedCourseSet.put(versionIndependentId, SAVED_AND_PLANNED);
                }
            }
        }
        LOG.debug("End of method getCourseStatusMap of CourseSearchController: {}",
                System.currentTimeMillis());
        return savedCourseSet;
    }

    /**
     * Retrieves the credit for s specific id
     *
     * @param id - Id of the credit value in the map
     * @return - Credit object for the specific id in the map or a unknown default
     */
    private Credit getCreditByID(String id) {
        Map<String, Credit> creditMap = getCreditMap();
        Credit credit = creditMap.get(id);
        return credit == null ? creditMap.get("u") : credit;
    }

    /**
     * Creates a map of divisions values with spaces removed to the value with spaces.
     * Store both trimmed and original, because source data is sometimes space padded.
     *
     * @return Map Division w/out spaces to Divsion w/ spaces
     */
    private Map<String, String> fetchCourseDivisions() {
        Map<String, String> map = new java.util.LinkedHashMap<String, String>();
        for (String div : getDivisionCodes())
            map.put(div.trim().replaceAll("\\s+", ""), div);
        return map;
    }

    /**
     * Retrieves a list of all unique divisions in which there are courses for
     *
     * @return A list of divisions
     */
    private List<String> getDivisionCodes() {

        ContextInfo context = KsapFrameworkServiceLocator.getContext()
                .getContextInfo();
        SearchRequestInfo searchRequest = new SearchRequestInfo(
                CourseSearchConstants.KSAP_COURSE_SEARCH_ALL_DIVISION_CODES_KEY);
        SearchResultInfo result;
        try {
            result = KsapFrameworkServiceLocator.getSearchService().search(searchRequest, context);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Error in CLU division search",
                    e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Error in CLU division search",
                    e);
        } catch (OperationFailedException e) {
            throw new IllegalArgumentException("Error in CLU division search",
                    e);
        } catch (PermissionDeniedException e) {
            throw new IllegalArgumentException("Error in CLU division search",
                    e);
        }
        List<String> rv = new java.util.ArrayList<String>();
        for (SearchResultRow row : result.getRows()) {
            rv.add(KsapHelperUtil.getCellValue(row, CourseSearchConstants.SearchResultColumns.DIVISION_CODE));
        }
        return rv;
    }

    /**
     * Populate the curriculumMap from the org ids associated with the provided list of courses
     *
     * @param courses - List of courses returned by search
     */
    private void populateCurriculumMap(List<? extends CourseSearchItem> courses){
        Set<String> orgIds = new HashSet<String>();
        for (CourseSearchItem course : courses) {
            String orgId = "ORGID-" + course.getSubject();
            orgIds.add(orgId);
        }
        getCurriculumMap(orgIds);
    }

    /**
     * @see org.kuali.student.ap.coursesearch.CourseSearchStrategy#loadPlanStatus(String, String, java.util.List)
     */
    @Override
    public void loadPlanStatus(String sessionId, String studentId, List<? extends CourseSearchItem> courses){
        Map<String, String> courseStatusMap = getCourseStatusMap(studentId);
        for (CourseSearchItem course : courses) {
            String versionIndependentId = course.getVersionIndependentId();

            //Note: we should move away to use courseId for planned/backup planItem, bookmark has been switched to use
            //versionIndependentId, the following code before "else if" should be removed after we complete the migration
            //to use versionIndependentId all the time
            String courseId = course.getCourseId();
            if (courseStatusMap.containsKey(courseId)) {
                // Set plan item's new status
               String status = courseStatusMap.get(courseId);
                if (status.equals(NONE)) {
                    ((CourseSearchItemImpl)course).setSaved(false);
                    ((CourseSearchItemImpl)course).setPlanned(false);
                } else if (status.equals(SAVED)) {
                    ((CourseSearchItemImpl)course).setSaved(true);
                    ((CourseSearchItemImpl)course).setPlanned(false);
                } else if (status.equals(PLANNED)) {
                    ((CourseSearchItemImpl)course).setPlanned(true);
                    ((CourseSearchItemImpl)course).setSaved(false);
                } else if (status.equals(SAVED_AND_PLANNED)) {
                    ((CourseSearchItemImpl)course).setPlanned(true);
                    ((CourseSearchItemImpl)course).setSaved(true);
                } else {
                    LOG.debug("Unknown status in map. Unable to set status of course with ID: {}", courseId);
                }
            }else if (courseStatusMap.containsKey(versionIndependentId))   {
                //KSAP-1853
                String status = courseStatusMap.get(versionIndependentId);
                if (status.equals(NONE)) {
                    ((CourseSearchItemImpl)course).setSaved(false);
                    ((CourseSearchItemImpl)course).setPlanned(false);
                } else if (status.equals(SAVED)) {
                    ((CourseSearchItemImpl)course).setSaved(true);
                    ((CourseSearchItemImpl)course).setPlanned(false);
                } else if (status.equals(PLANNED)) {
                    ((CourseSearchItemImpl)course).setPlanned(true);
                    ((CourseSearchItemImpl)course).setSaved(false);
                } else if (status.equals(SAVED_AND_PLANNED)) {
                    ((CourseSearchItemImpl)course).setPlanned(true);
                    ((CourseSearchItemImpl)course).setSaved(true);
                } else {
                    LOG.debug("Unknown status in map. Unable to set status of course with versionIndependentId: {}", versionIndependentId);
                }
            } else{
                // Reset to default status
                ((CourseSearchItemImpl)course).setSaved(false);
                ((CourseSearchItemImpl)course).setPlanned(false);
            }
            ((CourseSearchItemImpl)course).setSessionid(sessionId);
        }
    }

    /**
     * Determines a list of terms to filter results on based on a filter value
     * This method handles the filtering term filter options:
     * Any Term - No filtered state
     * Scheduled Term - Term with published SOC state
     * Single Term - Specific term (only one specific term is allowed)
     *
     * @param termFilter - A value that determines what terms to filter on.
     * @return A list of term ids.
     */
    private List<String> getTermsToFilterOn(String termFilter) {
        List<String> termsToFilterOn = new ArrayList<String>();

        if (termFilter.equals(CourseSearchForm.SEARCH_TERM_ANY_ITEM) || termFilter.equals(CourseSearchForm.SEARCH_TERM_SCHEDULED)) {
            // Any Term or Any Scheduled term selected
            List<Term> terms = new ArrayList<Term>();
            List<Term> currentScheduled = KsapFrameworkServiceLocator.getTermHelper().getCurrentTermsWithPublishedSOC();
            List<Term> futureScheduled = KsapFrameworkServiceLocator.getTermHelper().getFutureTermsWithPublishedSOC();
            if (currentScheduled != null) terms.addAll(currentScheduled);
            if (futureScheduled != null) terms.addAll(futureScheduled);
            for (int i = 0; i < terms.size(); i++) {
                termsToFilterOn.add(terms.get(i).getId());
            }
        } else {
            // Single Term selected
            termsToFilterOn.add(termFilter);
        }
        return termsToFilterOn;
    }
}
