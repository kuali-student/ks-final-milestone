package org.kuali.student.myplan.course.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.myplan.course.form.CourseSearchForm;
import org.kuali.student.myplan.course.util.CourseSearchConstants;
import org.kuali.student.myplan.plan.util.AtpHelper;
import org.kuali.student.myplan.plan.util.OrgHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.infc.SearchResult;
import org.kuali.student.r2.core.search.infc.SearchResultCell;
import org.kuali.student.r2.core.search.infc.SearchResultRow;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

public class CourseSearchStrategy {
    private final Logger logger = Logger.getLogger(CourseSearchStrategy.class);

    private transient CluService cluService;
    
    /*Remove the HashMap after enumeration service is in the ehcache and remove the hashmap occurance in this*/
    private HashMap<String, List<OrgInfo>> orgTypeCache;
    private HashMap<String, Map<String, String>> hashMap;

    public HashMap<String, List<OrgInfo>> getOrgTypeCache() {
        if (this.orgTypeCache == null) {
            this.orgTypeCache = new HashMap<String, List<OrgInfo>>();
        }
        return this.orgTypeCache;
    }

    public void setOrgTypeCache(HashMap<String, List<OrgInfo>> orgTypeCache) {
        this.orgTypeCache = orgTypeCache;
    }

    public HashMap<String, Map<String, String>> getHashMap() {
        if (this.hashMap == null) {
            this.hashMap = new HashMap<String, Map<String, String>>();
        }
        return this.hashMap;
    }

    public void setHashMap(HashMap<String, Map<String, String>> hashMap) {
        this.hashMap = hashMap;
    }


    public HashMap<String, String> fetchCourseDivisions(ContextInfo context) {
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            SearchRequestInfo request = new SearchRequestInfo("myplan.distinct.clu.divisions");

            SearchResult result = getCluService().search(request, context);

            for (SearchResultRow row : result.getRows()) {
                for (SearchResultCell cell : row.getCells()) {
                    String division = cell.getValue();
                    // Store both trimmed and original, because source data
                    // is sometimes space padded.
                    String key = division.trim().replaceAll("\\s+", "");
                    map.put(key, division);
                }
            }
        } catch (Exception e) {
            // TODO: Handle this exception better
            e.printStackTrace();
        }
        return map;
    }


    // TODO: Fetch these from the enumeration service, ala CourseDetailsInquiryViewHelperServiceImpl.initializeCampusLocations
    public final static String NO_CAMPUS = "-1";


    public void addCampusParams(ArrayList<SearchRequestInfo> requests, CourseSearchForm form, ContextInfo context) {
        List<String> str = form.getCampusSelect();
        String[] results = null;
        if (str != null) {
            results = str.toArray(new String[str.size()]);
        }

        List<OrgInfo> campusLocations = new ArrayList<OrgInfo>();
        if (!this.getOrgTypeCache().containsKey(CourseSearchConstants.CAMPUS_LOCATION)) {
            campusLocations = OrgHelper.getOrgInfo(CourseSearchConstants.CAMPUS_LOCATION, CourseSearchConstants.ORG_QUERY_SEARCH_BY_TYPE_REQUEST, CourseSearchConstants.ORG_TYPE_PARAM, context);
            this.getOrgTypeCache().put(CourseSearchConstants.CAMPUS_LOCATION, campusLocations);
        } else {
            campusLocations = getOrgTypeCache().get(CourseSearchConstants.CAMPUS_LOCATION);
        }

        String[] campus = new String[campusLocations.size()];
        for (int k = 0; k < campus.length; k++) {
            campus[k] = NO_CAMPUS;
        }
        if (results != null) {
            for (int i = 0; i < results.length; i++) {
                for (OrgInfo entry : campusLocations) {
                    if (results[i].equalsIgnoreCase(entry.getId())) {
                        campus[i] = results[i];
                        break;
                    }
                }
            }
        }
        //  Add the individual term items.
        for (SearchRequestInfo request : requests) {
            for (int j = 0; j < campus.length; j++) {
                int count = j + 1;
                String campusKey = "campus" + count;
                request.addParam(campusKey, campus[j]);
            }
        }

    }


    public void addCampusParam(SearchRequestInfo request, CourseSearchForm form, ContextInfo context) {
        List<String> str = form.getCampusSelect();
        String[] results = null;
        if (str != null) {
            results = str.toArray(new String[str.size()]);
        }
        List<OrgInfo> campusLocations = new ArrayList<OrgInfo>();
        if (!this.getOrgTypeCache().containsKey(CourseSearchConstants.CAMPUS_LOCATION)) {
            campusLocations = OrgHelper.getOrgInfo(CourseSearchConstants.CAMPUS_LOCATION, CourseSearchConstants.ORG_QUERY_SEARCH_BY_TYPE_REQUEST, CourseSearchConstants.ORG_TYPE_PARAM, context);
            this.getOrgTypeCache().put(CourseSearchConstants.CAMPUS_LOCATION, campusLocations);
        } else {
            campusLocations = getOrgTypeCache().get(CourseSearchConstants.CAMPUS_LOCATION);
        }

        String[] campus = new String[campusLocations.size()];
        for (int k = 0; k < campus.length; k++) {
            campus[k] = NO_CAMPUS;
        }
        if (results != null) {
            for (int i = 0; i < results.length; i++) {
                for (OrgInfo entry : campusLocations) {
                    if (results[i].equalsIgnoreCase(entry.getId())) {
                        campus[i] = results[i];
                        break;
                    }
                }
            }
        }
        for (int j = 0; j < campus.length; j++) {
            int count = j + 1;
            String campusKey = "campus" + count;
            request.addParam(campusKey, campus[j]);
        }

    }

    /**
     * @param divisionMap for reference
     * @param query       initial query
     * @param divisions   matches found
     * @return query string, minus matches found
     */
    public String extractDivisions(HashMap<String, String> divisionMap, String query, List<String> divisions) {
        boolean match = true;
        while (match) {
            match = false;
            // Retokenize after each division found is removed
            // Remove extra spaces to normalize input
            /*Replacing all the special characters in query with empty space except for Ampersand(&)character
              cause it might be in course codes*/
            query = query.trim().replaceAll("[\\s\\\\/:?\\\"<>|`~!@#$%^*()_+-={}\\]\\[;',.]", " ");
            List<QueryTokenizer.Token> tokens = QueryTokenizer.tokenize(query);
            List<String> list = QueryTokenizer.toStringList(tokens);
            List<String> pairs = TokenPairs.toPairs(list);
            TokenPairs.sortedLongestFirst(pairs);

            Iterator<String> i = pairs.iterator();
            while (match == false && i.hasNext()) {
                String pair = i.next();

                String key = pair.replace(" ", "");
                if (divisionMap.containsKey(key)) {
                    String division = divisionMap.get(key);
                    divisions.add(division);
                    query = query.replace(pair, "");
                    match = true;
                }
            }
        }
        return query;
    }

    public void addDivisionSearches(List<String> divisions, List<String> codes, List<String> levels, List<SearchRequestInfo> requests) {
        for (String division : divisions) {
            boolean needDivisionQuery = true;

            for (String code : codes) {
                needDivisionQuery = false;
                SearchRequestInfo request = new SearchRequestInfo("myplan.lu.search.divisionAndCode");
                request.addParam("division", division);
                request.addParam("code", code);
                requests.add(request);
            }

            for (String level : levels) {
                needDivisionQuery = false;

                // Converts "1XX" to "100"
                level = level.substring(0, 1) + "00";

                SearchRequestInfo request = new SearchRequestInfo("myplan.lu.search.divisionAndLevel");
                request.addParam("division", division);
                request.addParam("level", level);
                requests.add(request);
            }

            if (needDivisionQuery) {
                SearchRequestInfo request = new SearchRequestInfo("myplan.lu.search.division");
                request.addParam("division", division);
                requests.add(request);
            }
        }
    }

    public void addFullTextSearches(String query, List<SearchRequestInfo> requests) {
        List<QueryTokenizer.Token> tokens = QueryTokenizer.tokenize(query);

        for (QueryTokenizer.Token token : tokens) {
            String queryText = null;
            switch (token.rule) {
                case WORD:
                    queryText = token.value;
                    break;
                case QUOTED:
                    queryText = token.value;
                    queryText = queryText.substring(1, queryText.length() - 1);
                    break;
                default:
                    break;
            }
            SearchRequestInfo request = new SearchRequestInfo("myplan.lu.search.fulltext");
            request.addParam("queryText", queryText);
            requests.add(request);
        }
    }

    public List<SearchRequestInfo> queryToRequests(CourseSearchForm form, boolean isAcademicCalenderServiceUp, ContextInfo context)
            throws Exception {
        logger.info("Start Of Method queryToRequests in CourseSearchStrategy:" + System.currentTimeMillis());
        String query = form.getSearchQuery().toUpperCase();

        List<String> levels = QueryTokenizer.extractCourseLevels(query);
        for (String level : levels) {
            query = query.replace(level, "");
        }
        List<String> codes = QueryTokenizer.extractCourseCodes(query);
        for (String code : codes) {
            query = query.replace(code, "");
        }

        HashMap<String, String> divisionMap = fetchCourseDivisions(context);

        ArrayList<String> divisions = new ArrayList<String>();
        query = extractDivisions(divisionMap, query, divisions);


        ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
        logger.info("Start of method addDivisionSearches of CourseSearchStrategy:" + System.currentTimeMillis());
        // Order is important, more exact search results appear at top of list
        addDivisionSearches(divisions, codes, levels, requests);
        logger.info("End of method addDivisionSearches of CourseSearchStrategy:" + System.currentTimeMillis());
        logger.info("Start of method addFullTextSearches of CourseSearchStrategy:" + System.currentTimeMillis());
        addFullTextSearches(query, requests);
        logger.info("Start of method addFullTextSearches of CourseSearchStrategy:" + System.currentTimeMillis());
        logger.info("Start of method addCampusParams of CourseSearchStrategy:" + System.currentTimeMillis());
        addCampusParams(requests, form, context);
        logger.info("Start of method addCampusParams of CourseSearchStrategy:" + System.currentTimeMillis());
        logger.info("Count of No of Query Tokens:" + requests.size());
        processRequests(requests, form, context);
        logger.info("No of Requests after processRequest method:" + requests.size());
        logger.info("End Of Method queryToRequests in CourseSearchStrategy:" + System.currentTimeMillis());
        addVersionDateParam(requests, isAcademicCalenderServiceUp);
        return requests;
    }

    /**
     * @param requests
     * @param form
     */
    //To process the Request with search key as division or full Text
    public void processRequests(ArrayList<SearchRequestInfo> requests, CourseSearchForm form, ContextInfo context) {
        logger.info("Start of method processRequests in CourseSearchStrategy:" + System.currentTimeMillis());
        Map<String, String> subjects = null;
        int size = requests.size();
        for (int i = 0; i < size; i++) {
            if (requests.get(i).getSearchKey() != null) {
                if (requests.get(i).getSearchKey().equalsIgnoreCase("myplan.lu.search.division")) {
                    String queryText = (String) requests.get(i).getParams().get(0).getValues().get(0);
                    String key = (String) requests.get(i).getParams().get(0).getValues().get(0);
                    if (form.getSearchQuery().length() <= 2) {
                        break;
                    } else {
                        SearchRequestInfo request0 = new SearchRequestInfo("myplan.lu.search.title");
                        request0.addParam("queryText", queryText.trim());
                        addCampusParam(request0, form, context);
                        requests.add(request0);
                        if (!this.getHashMap().containsKey(CourseSearchConstants.SUBJECT_AREA)) {
                            subjects = OrgHelper.getSubjectAreas(context);
                            getHashMap().put(CourseSearchConstants.SUBJECT_AREA, subjects);

                        } else {
                            subjects = getHashMap().get(CourseSearchConstants.SUBJECT_AREA);
                        }
                        StringBuffer additionalDivisions = new StringBuffer();
                        if (subjects != null && subjects.size() > 0) {
                            //  Add the individual term items.
                            for (Map.Entry<String, String> entry : subjects.entrySet()) {
                                if (entry.getKey().trim().contains(key.trim())) {
                                    if (!entry.getKey().equalsIgnoreCase(queryText)) {
                                        additionalDivisions.append(entry.getKey() + ",");
                                    }
                                }

                            }
                        }
                        if (additionalDivisions.length() > 0) {
                            String div = additionalDivisions.substring(0, additionalDivisions.length() - 1);
                            SearchRequestInfo request1 = new SearchRequestInfo("myplan.lu.search.additionalDivision");
                            request1.addParam("divisions", div.trim());
                            addCampusParam(request1, form, context);
                            requests.add(request1);
                        }
                        SearchRequestInfo request2 = new SearchRequestInfo("myplan.lu.search.description");
                        request2.addParam("queryText", queryText.trim());
                        addCampusParam(request2, form, context);
                        requests.add(request2);

                    }

                }
                if (requests.get(i).getSearchKey().equalsIgnoreCase("myplan.lu.search.fulltext")) {
                    String key = (String) requests.get(i).getParams().get(0).getValues().get(0);
                    String division = null;
                    if (key.length() <= 2) {
                        requests.get(i).getParams().get(0).setValues(Arrays.asList("null"));
                        break;
                    } else {
                        if (key.length() > 2) {

                            if (!this.getHashMap().containsKey(CourseSearchConstants.SUBJECT_AREA)) {
                                subjects = OrgHelper.getSubjectAreas(context);
                                getHashMap().put(CourseSearchConstants.SUBJECT_AREA, subjects);

                            } else {
                                subjects = getHashMap().get(CourseSearchConstants.SUBJECT_AREA);
                            }

                            if (subjects != null && subjects.size() > 0) {
                                //  Add the individual term items.
                                for (Map.Entry<String, String> entry : subjects.entrySet()) {
                                    if (entry.getValue().trim().equalsIgnoreCase(key.trim())) {
                                        division = entry.getKey();

                                    }

                                }
                            }
                            if (division != null) {
                                requests.get(i).setSearchKey("myplan.lu.search.division");
                                requests.get(i).getParams().get(0).setKey("division");
                                requests.get(i).getParams().get(0).setValues(Arrays.asList(division));

                                SearchRequestInfo request1 = new SearchRequestInfo("myplan.lu.search.title");
                                request1.addParam("queryText", key.trim());
                                addCampusParam(request1, form, context);
                                requests.add(request1);
                                SearchRequestInfo request2 = new SearchRequestInfo("myplan.lu.search.description");
                                request2.addParam("queryText", key.trim());
                                addCampusParam(request2, form, context);
                                requests.add(request2);
                            } else {
                                requests.get(i).setSearchKey("myplan.lu.search.title");
                                SearchRequestInfo request2 = new SearchRequestInfo("myplan.lu.search.description");
                                request2.addParam("queryText", key.trim());
                                addCampusParam(request2, form, context);
                                requests.add(request2);
                            }
                        }

                    }
                }
            }
        }


        logger.info("End of processRequests method in CourseSearchStrategy:" + System.currentTimeMillis());
    }

    private void addVersionDateParam(List<SearchRequestInfo> searchRequests, boolean isAcademicCalenderServiceUp) {
        String currentTerm = null;
        String lastScheduledTerm = null;

        if (isAcademicCalenderServiceUp) {
            currentTerm = AtpHelper.getCurrentAtpId();
            lastScheduledTerm = AtpHelper.getLastScheduledAtpId();
        } else {
            currentTerm = AtpHelper.populateAtpIdFromCalender().get(0).getId();
            lastScheduledTerm = currentTerm;
        }
        for (SearchRequestInfo searchRequest : searchRequests) {
            // TODO: Fix when version issue for course is addressed
//            searchRequest.addParam("currentTerm", currentTerm);
            searchRequest.addParam("lastScheduledTerm", lastScheduledTerm);
        }
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = (CluService) GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, "CluService"));
        }
        return this.cluService;
    }

    public void setCluService(CluService luService) {
        this.cluService = cluService;
    }

}
