package org.kuali.student.ap.coursesearch;

import org.kuali.student.r2.core.search.dto.SearchRequestInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Strategy interface for specifying course search behavior.
 */
public interface CourseSearchStrategy {

    public static final int MAX_HITS = 1000;

	/**
	 * Create a new instance of the course search form.
	 * 
	 * @return A new instance of the course search form.
	 */
	public CourseSearchForm createInitialSearchForm();

    /**
     * Preforms the search detailed in the provided search form
     *
     * @param form - Form containing information on the search
     * @param studentId - Id of the user the search is being ran under
     * @return A list of courses found using the criteria defined in the form.
     */
    public List<CourseSearchItem> courseSearch(CourseSearchForm form, String studentId);

    /**
     * Creates formatted ordered list of sql search requests objects from data passed in through the search form.
     *
     * @param form - Form containing search parameters and query
     * @return A formatted and order list of requests.
     */
    public List<SearchRequestInfo> buildSearchRequests(CourseSearchForm form);

    /**
     * Process the Request adding any additional values, additional checks, or additional valiadations
     *
     * @param requests - The list of requests.
     * @param form     - The search form.
     * @return A formatted list of requests.
     */
    public List<SearchRequestInfo> adjustSearchRequests(List<SearchRequestInfo> requests, CourseSearchForm form);

    /**
     * Preforms sql queries on a list of requests and returns the results in the form of Hits.
     *
     * @param requests - List of sql requests to search on.
     * @return A list of results along with how many times each was found.
     */
    public List<Hit> preformSearch(List<SearchRequestInfo> requests);

    /**
     * Determines a list of terms to filter results on based on a filter value
     *
     * @param termFilter - A value that determines what terms to filter on.
     * @return A list of term ids.
     */
    public List<String> getTermsToFilterOn(String termFilter);

    /**
     * Add searches based on the components found in the query string
     *
     * @param componentMap    - A map of the different components to make requests on
     * @param requests        - The list of search requests to be ran
     */
    public void addComponentRequests(Map<String, List<String>> componentMap, List<SearchRequestInfo> requests);

    /**
     * Add searches based on text tokens found in the query string
     * Tokens are used in search of the course description and title text.
     *
     * @param query      - The query string
     * @param requests   - The list of search requests to be ran
     * @param searchTerm - The term filter for the search (used for CO searches)
     */
    public void addFullTextRequests(String query, List<SearchRequestInfo> requests, String searchTerm);

    /**
     * Determines if the search results exceeded the max number of results allowed to be returned.
     *
     * @return True if limit is exceeded, false otherwise
     */
    public boolean isLimitExceeded();

    /**
     * Populates and retrieves a stored map of short and long names for the organizations related to the courses found
     * in the search
     *
     * @param orgIds - List of organization ids found in the course search
     * @return  Currently stored map of organization short name to long name
     */
    Map<String, String> getCurriculumMap(Set<String> orgIds);

    /**
     * Retrieve a stored map of general eduction codes and names found in the course search.
     *
     * @return Currently stored map of general education code to name
     */
    Map<String, String> getGenEdMap();

    /**
     * Populate and retrieve a stored map of possible credit values
     *
     * @return Currently stored map of credit id to credit object
     */
    Map<String, Credit> getCreditMap();

    /**
     * Populate the facet information for a set courses
     *
     * @param form - Form containing information on the search
     * @param courses - The list of classes to set the facets on
     */
    public void populateFacets(CourseSearchForm form, List<CourseSearchItem> courses);

    /**
     * Retrieves a static map of facets to their related sort algorithm.
     *
     * @return  Static map of facet id to comparator
     */
    Map<String, Comparator<String>> getFacetSort();

    /**
     * Retrieves the configured QueryTokenizer
     * @return QueryTokenizer
     */
    QueryTokenizer getQueryTokenizer();

}
