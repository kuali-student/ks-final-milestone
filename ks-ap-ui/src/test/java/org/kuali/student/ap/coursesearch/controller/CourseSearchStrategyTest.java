package org.kuali.student.ap.coursesearch.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.coursesearch.form.CourseSearchFormImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-test-context.xml" })
public class CourseSearchStrategyTest {

	public static final String principalId = "student1";
	public ContextInfo context;

    boolean disableCampusRelatedTests = true;

    CourseSearchStrategyImpl strategy = null;

	@Before
	public void setUp() {
		context = new ContextInfo();
		context.setPrincipalId(principalId);
        strategy = new CourseSearchStrategyImpl();
        strategy.setQueryTokenizer(new QueryTokenizerImpl());
	}

	@Test
	public void testAddDivisionSearchesNothing() {
        Map<String,List<String>> mapComponents = new HashMap<String,List<String>>();
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		strategy.addComponentRequests(mapComponents, requests);
		assertTrue(requests.isEmpty());
	}

	@Test
	public void testAddDivisionSearchesJustDivision() {
		ArrayList<String> divisions = new ArrayList<String>();
		divisions.add("DIVISION");
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
        Map<String,List<String>> mapComponents = new HashMap<String,List<String>>();
        mapComponents.put(CourseSearchStrategyImpl.DIVISIONS_COMPONENTS,divisions);
		strategy.addComponentRequests(mapComponents, requests);
		assertEquals(1, requests.size());
		SearchRequestInfo request = requests.get(0);
		assertEquals("ksap.lu.search.division", request.getSearchKey());
		assertEquals("DIVISION", request.getParams().get(0).getValues().get(0));
	}

	@Test
	public void testAddDivisionSearchesDivisionAndCode() {
		ArrayList<String> divisions = new ArrayList<String>();
		divisions.add("DIVISION");
		ArrayList<String> codes = new ArrayList<String>();
		codes.add("CODE");
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
        Map<String,List<String>> mapComponents = new HashMap<String,List<String>>();
        mapComponents.put(CourseSearchStrategyImpl.DIVISIONS_COMPONENTS,divisions);
        mapComponents.put(CourseSearchStrategyImpl.CODES_COMPONENTS,codes);
		strategy.addComponentRequests(mapComponents, requests);
		assertEquals(3, requests.size());
		SearchRequestInfo request = requests.get(0);
		assertEquals("ksap.lu.search.divisionAndCode", request.getSearchKey());
		assertEquals("DIVISION", request.getParams().get(0).getValues().get(0));
		assertEquals("CODE", request.getParams().get(1).getValues().get(0));
	}

	@Test
	public void testAddDivisionSearchesDivisionAndLevel() {
		ArrayList<String> divisions = new ArrayList<String>();
		divisions.add("DIVISION");
		ArrayList<String> levels = new ArrayList<String>();
		levels.add("100");
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
        Map<String,List<String>> mapComponents = new HashMap<String,List<String>>();
        mapComponents.put(CourseSearchStrategyImpl.DIVISIONS_COMPONENTS,divisions);
        mapComponents.put(CourseSearchStrategyImpl.LEVELS_COMPONENTS,levels);
        strategy.addComponentRequests(mapComponents, requests);
		assertEquals(3, requests.size());
		SearchRequestInfo request = requests.get(0);
		assertEquals("ksap.lu.search.divisionAndLevel",
				request.getSearchKey());
		assertEquals("DIVISION", request.getParams().get(0).getValues().get(0));
		assertEquals("100", request.getParams().get(1).getValues().get(0));
	}

    @Test
    public void testAddDivisionSearchesIncompleteCode() {
        ArrayList<String> divisions = new ArrayList<String>();
        divisions.add("DIVISION");
        ArrayList<String> incompleteCodes = new ArrayList<String>();
        incompleteCodes.add("DIVISION1");
        ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
        Map<String,List<String>> mapComponents = new HashMap<String,List<String>>();
        mapComponents.put("division",divisions);
        mapComponents.put("incompleteCodes",incompleteCodes);
        strategy.addComponentRequests(mapComponents, requests);
        assertEquals(1, requests.size());
        SearchRequestInfo request = requests.get(0);
        assertEquals("ksap.lu.search.courseCode",
                request.getSearchKey());
        assertEquals("DIVISION1", request.getParams().get(0).getValues().get(0));
    }

	@Test
	public void testAddFullTextSearches() {
		String query = "text \"text\"";
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		strategy.addFullTextRequests(query, requests, "termlist");
		assertEquals(8, requests.size());
		assertEquals("ksap.lu.search.title", requests.get(0)
				.getSearchKey());
		assertEquals("text", requests.get(0).getParams().get(0).getValues()
				.get(0));
		assertEquals("text", requests.get(1).getParams().get(0).getValues()
				.get(0));
	}

	@Test
	public void testExtractDivisions() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("A", "A   ");
		map.put("AB", "A B ");
		map.put("B", "B   ");
		map.put("C", "C   ");
		ArrayList<String> divisions = new ArrayList<String>();
		String query = "A B C";
		query = strategy.getQueryTokenizer().extractDivisions(map, query, divisions, true);
		assertEquals(2, divisions.size());
		assertEquals("A B ", divisions.get(0));
		assertEquals("C   ", divisions.get(1));
	}

	@Test
	public void testQueryToRequestsExactCourseCodeAndNumberMatch()
			throws Exception {
        if (disableCampusRelatedTests) return;
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("AP 101");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		List<SearchRequestInfo> requests = strategy.buildSearchRequests(form);
		assertEquals(1, requests.size());
		assertEquals("ksap.lu.search.divisionAndCode", requests.get(0)
				.getSearchKey());
		assertEquals(4, requests.get(0).getParams().size());
		List<SearchParamInfo> params = requests.get(0).getParams();
		SearchParamInfo param = null;
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("AP", param.getValues().get(0));
		param = params.get(1);
		assertEquals("code", param.getKey());
		assertEquals("101", param.getValues().get(0));
		param = params.get(2);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

	}

	@Test
	public void testQueryToRequestsCourseCodeMatch() throws Exception {
        if (disableCampusRelatedTests) return;
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("PHIL");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		SearchRequestInfo request = null;
		SearchParamInfo param = null;
		List<SearchRequestInfo> requests = strategy.buildSearchRequests(form);
		List<SearchParamInfo> params = null;
		assertEquals(3, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals(3, params.size());
		assertEquals("ksap.lu.search.division", request.getSearchKey());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("PHIL", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals(3, params.size());
		assertEquals("ksap.lu.search.title", request.getSearchKey());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("PHIL", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));
		param = params.get(2);
		assertEquals("lastScheduledTerm", param.getKey());
		assertEquals(KsapFrameworkServiceLocator.getTermHelper()
				.getLastScheduledTerm().getId(), param.getValues().get(0));

		request = requests.get(2);
		params = request.getParams();
		assertEquals(3, params.size());
		assertEquals("ksap.lu.search.description", request.getSearchKey());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("PHIL", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));
		param = params.get(2);
		assertEquals("lastScheduledTerm", param.getKey());
		assertEquals(KsapFrameworkServiceLocator.getTermHelper()
				.getLastScheduledTerm().getId(), param.getValues().get(0));
	}

	@Test
	public void testQueryToRequestsSingleStringMatch() throws Exception {
        if (disableCampusRelatedTests) return;
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("Astro");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		List<SearchRequestInfo> requests = strategy.buildSearchRequests(form);
		List<SearchParamInfo> params = null;
		SearchRequestInfo request = null;
		SearchParamInfo param = null;
		assertEquals(2, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("ksap.lu.search.title", request.getSearchKey());
		assertEquals(3, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTRO", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("ksap.lu.search.description", request.getSearchKey());
		assertEquals(3, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTRO", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

	}

	@Test
	public void testQueryToRequestsCodeAndLevelMatch() throws Exception {
        if (disableCampusRelatedTests) return;
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("BIOL 1xx");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		List<SearchRequestInfo> requests = strategy.buildSearchRequests(form);
		SearchParamInfo param = null;
		SearchRequestInfo request = null;
		List<SearchParamInfo> params = null;
		assertEquals(1, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("ksap.lu.search.divisionAndLevel",
				request.getSearchKey());
		assertEquals(4, params.size());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("BIOL", param.getValues().get(0));
		param = params.get(1);
		assertEquals("level", param.getKey());
		assertEquals("100", param.getValues().get(0));
		param = params.get(2);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));
		param = params.get(3);
		assertEquals("lastScheduledTerm", param.getKey());
		assertEquals(KsapFrameworkServiceLocator.getTermHelper()
				.getLastScheduledTerm().getId(), param.getValues().get(0));

	}

	@Test
	public void testQueryToRequestsSingleStringAndCourseCodeMatch()
			throws Exception {
        if (disableCampusRelatedTests) return;
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("Astronomy");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		List<SearchRequestInfo> requests = strategy.buildSearchRequests(form);
		List<SearchParamInfo> params = null;
		SearchParamInfo param = null;
		SearchRequestInfo request = null;
		assertEquals(2, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("ksap.lu.search.title", request.getSearchKey());
		assertEquals(3, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTRONOMY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("ksap.lu.search.description", request.getSearchKey());
		assertEquals(3, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTRONOMY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

	}

	@Test
	public void testQueryToRequestsTwoStringsMatch() throws Exception {
        if (disableCampusRelatedTests) return;
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("Engronomy biology");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		List<SearchRequestInfo> requests = strategy.buildSearchRequests(form);
		List<SearchParamInfo> params = null;
		SearchParamInfo param = null;
		SearchRequestInfo request = null;
		assertEquals(4, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("ksap.lu.search.title", request.getSearchKey());
		assertEquals(3, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ENGRONOMY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));
		param = params.get(2);
		assertEquals("lastScheduledTerm", param.getKey());
		assertEquals(KsapFrameworkServiceLocator.getTermHelper()
				.getLastScheduledTerm().getId(), param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("ksap.lu.search.title", request.getSearchKey());
		assertEquals(3, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("BIOLOGY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));
		param = params.get(2);
		assertEquals("lastScheduledTerm", param.getKey());
		assertEquals(KsapFrameworkServiceLocator.getTermHelper()
				.getLastScheduledTerm().getId(), param.getValues().get(0));

		request = requests.get(2);
		params = request.getParams();
		assertEquals("ksap.lu.search.description", request.getSearchKey());
		assertEquals(3, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ENGRONOMY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));
		param = params.get(2);
		assertEquals("lastScheduledTerm", param.getKey());
		assertEquals(KsapFrameworkServiceLocator.getTermHelper()
				.getLastScheduledTerm().getId(), param.getValues().get(0));

		request = requests.get(3);
		params = request.getParams();
		assertEquals("ksap.lu.search.description", request.getSearchKey());
		assertEquals(3, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("BIOLOGY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));
		param = params.get(2);
		assertEquals("lastScheduledTerm", param.getKey());
		assertEquals(KsapFrameworkServiceLocator.getTermHelper()
				.getLastScheduledTerm().getId(), param.getValues().get(0));
	}

	@Test
	public void testQueryToRequestsEmpty() throws Exception {
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("");
		List<String> campusParams = new ArrayList<String>();
		form.setCampusSelect(campusParams);
		form.setSearchTerm("");
		List<SearchRequestInfo> requests = strategy.buildSearchRequests(form);
		assertTrue(requests.isEmpty());
	}

	@Test
	public void testProcessRequests() throws Exception {
        if (disableCampusRelatedTests) return;
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("ASTR");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("306");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		SearchRequestInfo request = new SearchRequestInfo(
				"ksap.lu.search.division");
		request.addParam("division", "ASTR  ");
		requests.add(request);
		strategy.adjustSearchRequests(requests, form);
		SearchParamInfo param = null;
		List<SearchParamInfo> params = null;
		assertEquals(3, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals(1, params.size());
		assertEquals("ksap.lu.search.division", request.getSearchKey());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("ASTR  ", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals(1, params.size());
		assertEquals("ksap.lu.search.title", request.getSearchKey());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTR", param.getValues().get(0));

		request = requests.get(2);
		params = request.getParams();
		assertEquals(1, params.size());
		assertEquals("ksap.lu.search.description", request.getSearchKey());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTR", param.getValues().get(0));
	}

	@Test
	public void testProcessRequests2() throws Exception {
        if (disableCampusRelatedTests) return;
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("HIST");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		SearchRequestInfo request = new SearchRequestInfo(
				"ksap.lu.search.division");
		request.addParam("division", "HIST  ");
		requests.add(request);
		List<SearchParamInfo> params = null;
		SearchParamInfo param = null;
		strategy.adjustSearchRequests(requests, form);
		assertEquals(3, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("ksap.lu.search.division", request.getSearchKey());
		assertEquals(1, params.size());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("HIST  ", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("ksap.lu.search.title", request.getSearchKey());
		assertEquals(1, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("HIST", param.getValues().get(0));

		request = requests.get(2);
		// TODO: correct expectations
		// params = request.getParams();
		// assertEquals("ksap.lu.search.additionalDivision",
		// request.getSearchKey());
		// assertEquals(2, params.size());
		// param = params.get(0);
		// assertEquals("divisions", param.getKey());
		// String str = (String) param.getValues().get(0);
		// boolean t1 = str.contains("THIST ");
		// boolean t2 = str.contains("T HIST");
		// assertTrue(t1 && t2);
		// param = params.get(1);
		// assertEquals("campuses", param.getKey());
		// assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
		// param.getValues().get(0));
		//
		//
		// request = requests.get(3);
		params = request.getParams();
		assertEquals("ksap.lu.search.description", request.getSearchKey());
		assertEquals(1, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("HIST", param.getValues().get(0));
	}

	@Test
	public void testProcessRequests3() throws Exception {
        if (disableCampusRelatedTests) return;
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("PHILONOMY");
		form.setSearchTerm("any");
		SearchRequestInfo request = new SearchRequestInfo(
				"ksap.lu.search.fulltext");
		request.addParam("queryText", "PHILONOMY");
		requests.add(request);
		List<SearchParamInfo> params = null;
		SearchParamInfo param = null;
		strategy.adjustSearchRequests(requests, form);
		assertEquals(2, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("ksap.lu.search.title", request.getSearchKey());
		assertEquals(1, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("PHILONOMY", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("ksap.lu.search.description", request.getSearchKey());
		assertEquals(1, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("PHILONOMY", param.getValues().get(0));
	}

	@Test
	public void testProcessRequests4() throws Exception {
        if (disableCampusRelatedTests) return;
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("ASTRONOMY BIOLOGY");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		SearchRequestInfo request = new SearchRequestInfo(
				"ksap.lu.search.fulltext");
		request.addParam("queryText", "ASTRONOMY");
		request.addParam("campuses", "310");
		requests.add(request);
		request = new SearchRequestInfo("ksap.lu.search.fulltext");
		request.addParam("queryText", "BIOLOGY");
		request.addParam("campuses", "310");
		requests.add(request);

		List<SearchParamInfo> params = null;
		SearchParamInfo param = null;
		strategy.adjustSearchRequests(requests, form);
		assertEquals(4, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("ksap.lu.search.title", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTRONOMY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(2);
		params = request.getParams();
		assertEquals("ksap.lu.search.description", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTRONOMY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("ksap.lu.search.title", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("BIOLOGY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(3);
		params = request.getParams();
		assertEquals("ksap.lu.search.description", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("BIOLOGY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));
	}

	@Test
	public void testProcessRequests5() throws Exception {
        if (disableCampusRelatedTests) return;
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("ASTR BIOL");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("306");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		SearchRequestInfo request = new SearchRequestInfo(
				"ksap.lu.search.division");
		request.addParam("division", "ASTR  ");
		requests.add(request);
		request = new SearchRequestInfo("ksap.lu.search.division");
		request.addParam("division", "BIOL  ");
		requests.add(request);

		List<SearchParamInfo> params = null;
		SearchParamInfo param = null;
		strategy.adjustSearchRequests(requests, form);
		assertEquals(6, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("ksap.lu.search.division", request.getSearchKey());
		assertEquals(1, params.size());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("ASTR  ", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("ksap.lu.search.division", request.getSearchKey());
		assertEquals(1, params.size());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("BIOL  ", param.getValues().get(0));

		request = requests.get(2);
		params = request.getParams();
		assertEquals("ksap.lu.search.title", request.getSearchKey());
		assertEquals(1, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTR", param.getValues().get(0));

		request = requests.get(3);
		params = request.getParams();
		assertEquals("ksap.lu.search.description", request.getSearchKey());
		assertEquals(1, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTR", param.getValues().get(0));

		request = requests.get(4);
		params = request.getParams();
		assertEquals("ksap.lu.search.title", request.getSearchKey());
		assertEquals(1, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("BIOL", param.getValues().get(0));

		request = requests.get(5);
		params = request.getParams();
		assertEquals("ksap.lu.search.description", request.getSearchKey());
		assertEquals(1, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("BIOL", param.getValues().get(0));
	}

	@Test
	public void testProcessRequests6() throws Exception {
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("AS");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("306");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		SearchRequestInfo request = new SearchRequestInfo(
				"ksap.lu.search.division");
		request.addParam("division", "A S   ");
		requests.add(request);
		strategy.adjustSearchRequests(requests, form);
		SearchParamInfo param = null;
		List<SearchParamInfo> params = null;
		assertEquals(1, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("ksap.lu.search.division", request.getSearchKey());
		assertEquals(1, params.size());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("A S   ", param.getValues().get(0));

	}

	@Test
	public void testProcessRequests7() throws Exception {
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("");
		List<String> campusParams = new ArrayList<String>();
		form.setCampusSelect(campusParams);
		form.setSearchTerm("");
		strategy.adjustSearchRequests(requests, form);
		assertTrue(requests.isEmpty());

	}
}
