package org.kuali.student.myplan.course.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.course.CourseSearchForm;
import org.kuali.student.myplan.course.form.CourseSearchFormImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-test-context.xml" })
public class CourseSearchStrategyTest {

	public static final String principalId = "student1";
	public ContextInfo context;

	@Before
	public void setUp() {
		context = new ContextInfo();
		context.setPrincipalId(principalId);
	}

	@Test
	public void testFetchCourseDivisions() throws Exception {
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		Map<String, String> divisionsMap = strategy.fetchCourseDivisions();
		assertFalse(divisionsMap.isEmpty());
	}

	@Test
	public void testAddCampusParams() throws Exception {
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		campusParams.add("311");
		campusParams.add("312");
		campusParams.add("foobar");
		form.setCampusSelect(campusParams);

		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		requests.add(new SearchRequestInfo("test"));

		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		strategy.addCampusParams(requests, form);

		SearchRequestInfo request = requests.get(0);
		List<SearchParamInfo> params = request.getParams();
		assertEquals(1, params.size());

		SearchParamInfo param = null;
		param = params.get(0);
		assertEquals(3, param.getValues().size());
		assertEquals("310", param.getValues().get(0));
		assertEquals("311", param.getValues().get(1));
		assertEquals("312", param.getValues().get(2));
	}

	@Test
	public void testAddCampusParams2() throws Exception {
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setCampusSelect(null);

		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		requests.add(new SearchRequestInfo("test"));

		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		strategy.addCampusParams(requests, form);

		SearchRequestInfo request = requests.get(0);
		List<SearchParamInfo> params = request.getParams();
		assertEquals(1, params.size());

		SearchParamInfo param = null;
		param = params.get(0);
		assertEquals(1, param.getValues().size());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));
	}

	@Test
	public void testAddCampusParam() throws Exception {
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		campusParams.add("311");
		campusParams.add("312");
		form.setCampusSelect(campusParams);

		SearchRequestInfo requests = new SearchRequestInfo("test");

		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		strategy.addCampusParam(requests, form);

		List<SearchParamInfo> params = requests.getParams();
		assertEquals(1, params.size());

		SearchParamInfo param = null;
		param = params.get(0);
		assertEquals(3, param.getValues().size());
		assertEquals("310", param.getValues().get(0));
		assertEquals("311", param.getValues().get(1));
		assertEquals("312", param.getValues().get(2));
	}

	@Test
	public void testAddCampusParam2() throws Exception {
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setCampusSelect(null);

		SearchRequestInfo requests = new SearchRequestInfo("test");

		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		strategy.addCampusParam(requests, form);

		List<SearchParamInfo> params = requests.getParams();
		assertEquals(1, params.size());

		SearchParamInfo param = null;
		param = params.get(0);
		assertEquals(1, param.getValues().size());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));
	}

	@Test
	public void testAddDivisionSearchesNothing() {
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		ArrayList<String> divisions = new ArrayList<String>();
		ArrayList<String> levels = new ArrayList<String>();
		ArrayList<String> codes = new ArrayList<String>();
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		strategy.addDivisionSearches(divisions, levels, codes, requests);
		assertEquals(0, requests.size());
	}

	@Test
	public void testAddDivisionSearchesJustDivision() {
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		ArrayList<String> divisions = new ArrayList<String>();
		divisions.add("DIVISION");
		ArrayList<String> codes = new ArrayList<String>();
		ArrayList<String> levels = new ArrayList<String>();
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		strategy.addDivisionSearches(divisions, codes, levels, requests);
		assertEquals(1, requests.size());
		SearchRequestInfo request = requests.get(0);
		assertEquals("myplan.lu.search.division", request.getSearchKey());
		assertEquals("DIVISION", request.getParams().get(0).getValues().get(0));
	}

	@Test
	public void testAddDivisionSearchesDivisionAndCode() {
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		ArrayList<String> divisions = new ArrayList<String>();
		divisions.add("DIVISION");
		ArrayList<String> codes = new ArrayList<String>();
		codes.add("CODE");
		ArrayList<String> levels = new ArrayList<String>();
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		strategy.addDivisionSearches(divisions, codes, levels, requests);
		assertEquals(1, requests.size());
		SearchRequestInfo request = requests.get(0);
		assertEquals("myplan.lu.search.divisionAndCode", request.getSearchKey());
		assertEquals("DIVISION", request.getParams().get(0).getValues().get(0));
		assertEquals("CODE", request.getParams().get(1).getValues().get(0));
	}

	@Test
	public void testAddDivisionSearchesDivisionAndLevel() {
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		ArrayList<String> divisions = new ArrayList<String>();
		divisions.add("DIVISION");
		ArrayList<String> codes = new ArrayList<String>();
		ArrayList<String> levels = new ArrayList<String>();
		levels.add("100");
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		strategy.addDivisionSearches(divisions, codes, levels, requests);
		assertEquals(1, requests.size());
		SearchRequestInfo request = requests.get(0);
		assertEquals("myplan.lu.search.divisionAndLevel",
				request.getSearchKey());
		assertEquals("DIVISION", request.getParams().get(0).getValues().get(0));
		assertEquals("100", request.getParams().get(1).getValues().get(0));
	}

	@Test
	public void testAddFullTextSearches() {
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		String query = "text \"text\"";
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		strategy.addFullTextSearches(query, requests);
		assertEquals(2, requests.size());
		assertEquals("myplan.lu.search.fulltext", requests.get(0)
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
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		ArrayList<String> divisions = new ArrayList<String>();
		String query = "A B C";
		query = strategy.extractDivisions(map, query, divisions);
		assertEquals("", query);
		assertEquals(2, divisions.size());
		assertEquals("A B ", divisions.get(0));
		assertEquals("C   ", divisions.get(1));
	}

	@Test
	public void testQueryToRequestsExactCourseCodeAndNumberMatch()
			throws Exception {
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("AP 101");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		List<SearchRequestInfo> requests = strategy.queryToRequests(form);
		assertEquals(1, requests.size());
		assertEquals("myplan.lu.search.divisionAndCode", requests.get(0)
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
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("PHIL");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		SearchRequestInfo request = null;
		SearchParamInfo param = null;
		List<SearchRequestInfo> requests = strategy.queryToRequests(form);
		List<SearchParamInfo> params = null;
		assertEquals(3, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals(3, params.size());
		assertEquals("myplan.lu.search.division", request.getSearchKey());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("PHIL", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals(3, params.size());
		assertEquals("myplan.lu.search.title", request.getSearchKey());
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
		assertEquals("myplan.lu.search.description", request.getSearchKey());
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
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("Astro");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		List<SearchRequestInfo> requests = strategy.queryToRequests(form);
		List<SearchParamInfo> params = null;
		SearchRequestInfo request = null;
		SearchParamInfo param = null;
		assertEquals(2, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("myplan.lu.search.title", request.getSearchKey());
		assertEquals(3, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTRO", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("myplan.lu.search.description", request.getSearchKey());
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
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("BIOL 1xx");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		List<SearchRequestInfo> requests = strategy.queryToRequests(form);
		SearchParamInfo param = null;
		SearchRequestInfo request = null;
		List<SearchParamInfo> params = null;
		assertEquals(1, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("myplan.lu.search.divisionAndLevel",
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
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("Astronomy");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		List<SearchRequestInfo> requests = strategy.queryToRequests(form);
		List<SearchParamInfo> params = null;
		SearchParamInfo param = null;
		SearchRequestInfo request = null;
		assertEquals(2, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("myplan.lu.search.title", request.getSearchKey());
		assertEquals(3, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTRONOMY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("myplan.lu.search.description", request.getSearchKey());
		assertEquals(3, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTRONOMY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

	}

	/*
	 * This test either needs ref data see Kasp-10 or is for legacy code that is
	 * not present.
	 * 
	 * @Test public void
	 * testQueryToRequestsCourseCodeAndAdditionalCourseCodeMatch() throws
	 * Exception {
	 * 
	 * CourseSearchFormImpl form = new CourseSearchFormImpl();
	 * form.setSearchQuery("HIST"); List<String> campusParams=new
	 * ArrayList<String>(); campusParams.add("310");
	 * form.setCampusSelect(campusParams); form.setSearchTerm("any");
	 * CourseSearchStrategy strategy =
	 * KsapFrameworkServiceLocator.getCourseSearchStrategy();
	 * List<SearchRequestInfo> requests = strategy.queryToRequests(form,true,
	 * context); List<SearchParamInfo> params = null; SearchParamInfo param =
	 * null; SearchRequestInfo request = null; assertEquals(4, requests.size());
	 * 
	 * request = requests.get(0); params = request.getParams();
	 * assertEquals("myplan.lu.search.division", request.getSearchKey());
	 * assertEquals(2, params.size()); param = params.get(0);
	 * assertEquals("division", param.getKey()); assertEquals("HIST  ",
	 * param.getValues().get(0)); param = params.get(1);
	 * assertEquals("campuses", param.getKey());
	 * assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
	 * param.getValues().get(0));
	 * 
	 * request = requests.get(1); params = request.getParams();
	 * assertEquals("myplan.lu.search.title", request.getSearchKey());
	 * assertEquals(2, params.size()); param = params.get(0);
	 * assertEquals("queryText", param.getKey()); assertEquals("HIST",
	 * param.getValues().get(0)); param = params.get(1);
	 * assertEquals("campuses", param.getKey());
	 * assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
	 * param.getValues().get(0));
	 * 
	 * request = requests.get(2); params = request.getParams();
	 * assertEquals("myplan.lu.search.additionalDivision",
	 * request.getSearchKey()); assertEquals(2, params.size()); param =
	 * params.get(0); assertEquals("divisions", param.getKey()); String str =
	 * (String) param.getValues().get(0); boolean t1 = str.contains("THIST ");
	 * boolean t2 = str.contains("T HIST"); assertTrue(t1 && t2); param =
	 * params.get(1); assertEquals("campuses", param.getKey());
	 * assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
	 * param.getValues().get(0));
	 * 
	 * 
	 * request = requests.get(3); params = request.getParams();
	 * assertEquals("myplan.lu.search.description", request.getSearchKey());
	 * assertEquals(2, params.size()); param = params.get(0);
	 * assertEquals("queryText", param.getKey()); assertEquals("HIST",
	 * param.getValues().get(0)); param = params.get(1);
	 * assertEquals("campuses", param.getKey());
	 * assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
	 * param.getValues().get(0));
	 * 
	 * }
	 */

	@Test
	public void testQueryToRequestsTwoStringsMatch() throws Exception {
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("Engronomy biology");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		List<SearchRequestInfo> requests = strategy.queryToRequests(form);
		List<SearchParamInfo> params = null;
		SearchParamInfo param = null;
		SearchRequestInfo request = null;
		assertEquals(4, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("myplan.lu.search.title", request.getSearchKey());
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
		assertEquals("myplan.lu.search.title", request.getSearchKey());
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
		assertEquals("myplan.lu.search.description", request.getSearchKey());
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
		assertEquals("myplan.lu.search.description", request.getSearchKey());
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

	/*
	 * This test either needs ref data see Kasp-10 or is for legacy code that is
	 * not present.
	 * 
	 * @Test public void testQueryToRequestsTwoCourseCodesMatch() throws
	 * Exception { CourseSearchFormImpl form = new CourseSearchFormImpl();
	 * form.setSearchQuery("PHIL BIOL"); List<String> campusParams = new
	 * ArrayList<String>(); campusParams.add("310");
	 * form.setCampusSelect(campusParams); form.setSearchTerm("any");
	 * CourseSearchStrategy strategy =
	 * KsapFrameworkServiceLocator.getCourseSearchStrategy();
	 * List<SearchRequestInfo> requests = strategy.queryToRequests(form);
	 * List<SearchParamInfo> params = null; SearchParamInfo param = null;
	 * SearchRequestInfo request = null; assertEquals(6, requests.size());
	 * request = requests.get(0); params = request.getParams();
	 * assertEquals("myplan.lu.search.division", request.getSearchKey());
	 * assertEquals(3, params.size()); param = params.get(0);
	 * assertEquals("division", param.getKey()); assertEquals("BIOL",
	 * param.getValues().get(0)); param = params.get(1);
	 * assertEquals("campuses", param.getKey()); assertEquals("310",
	 * param.getValues().get(0)); param = params.get(2);
	 * assertEquals("lastScheduledTerm", param.getKey());
	 * assertEquals(KsapFrameworkServiceLocator
	 * .getAtpHelper().getLastScheduledAtpId(), param.getValues() .get(0));
	 * 
	 * request = requests.get(1); params = request.getParams();
	 * assertEquals("myplan.lu.search.division", request.getSearchKey());
	 * assertEquals(3, params.size()); param = params.get(0);
	 * assertEquals("division", param.getKey()); assertEquals("PHIL",
	 * param.getValues().get(0)); param = params.get(1);
	 * assertEquals("campuses", param.getKey()); assertEquals("310",
	 * param.getValues().get(0)); param = params.get(2);
	 * assertEquals("lastScheduledTerm", param.getKey());
	 * assertEquals(KsapFrameworkServiceLocator
	 * .getAtpHelper().getLastScheduledAtpId(), param.getValues() .get(0));
	 * 
	 * request = requests.get(2); params = request.getParams();
	 * assertEquals("myplan.lu.search.title", request.getSearchKey());
	 * assertEquals(3, params.size()); param = params.get(0);
	 * assertEquals("queryText", param.getKey()); assertEquals("BIOL",
	 * param.getValues().get(0)); param = params.get(1);
	 * assertEquals("campuses", param.getKey()); assertEquals("310",
	 * param.getValues().get(0)); param = params.get(2);
	 * assertEquals("lastScheduledTerm", param.getKey());
	 * assertEquals(KsapFrameworkServiceLocator
	 * .getAtpHelper().getLastScheduledAtpId(), param.getValues() .get(0));
	 * 
	 * request = requests.get(3); params = request.getParams();
	 * assertEquals("myplan.lu.search.description", request.getSearchKey());
	 * assertEquals(3, params.size()); param = params.get(0);
	 * assertEquals("queryText", param.getKey()); assertEquals("BIOL",
	 * param.getValues().get(0)); param = params.get(1);
	 * assertEquals("campuses", param.getKey()); assertEquals("310",
	 * param.getValues().get(0)); param = params.get(2);
	 * assertEquals("lastScheduledTerm", param.getKey());
	 * assertEquals(KsapFrameworkServiceLocator
	 * .getAtpHelper().getLastScheduledAtpId(), param.getValues() .get(0));
	 * 
	 * request = requests.get(4); params = request.getParams();
	 * assertEquals("myplan.lu.search.title", request.getSearchKey());
	 * assertEquals(3, params.size()); param = params.get(0);
	 * assertEquals("queryText", param.getKey()); assertEquals("PHIL",
	 * param.getValues().get(0)); param = params.get(1);
	 * assertEquals("campuses", param.getKey()); assertEquals("310",
	 * param.getValues().get(0)); param = params.get(2);
	 * assertEquals("lastScheduledTerm", param.getKey());
	 * assertEquals(KsapFrameworkServiceLocator
	 * .getAtpHelper().getLastScheduledAtpId(), param.getValues() .get(0));
	 * 
	 * request = requests.get(5); params = request.getParams();
	 * assertEquals("myplan.lu.search.description", request.getSearchKey());
	 * assertEquals(3, params.size()); param = params.get(0);
	 * assertEquals("queryText", param.getKey()); assertEquals("PHIL",
	 * param.getValues().get(0)); param = params.get(1);
	 * assertEquals("campuses", param.getKey()); assertEquals("310",
	 * param.getValues().get(0)); param = params.get(2);
	 * assertEquals("lastScheduledTerm", param.getKey());
	 * assertEquals(KsapFrameworkServiceLocator
	 * .getAtpHelper().getLastScheduledAtpId(), param.getValues() .get(0));
	 * 
	 * }
	 */

	@Test
	public void testQueryToRequestsEmpty() throws Exception {
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("");
		List<String> campusParams = new ArrayList<String>();
		form.setCampusSelect(campusParams);
		form.setSearchTerm("");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		List<SearchRequestInfo> requests = strategy.queryToRequests(form);
		assertEquals(0, requests.size());
	}

	/*
	 * @Test public void testGetEnumerationValueInfoList() throws Exception {
	 * String param = CourseSearchConstants.CAMPUS_LOCATION;
	 * CourseSearchStrategy strategy =
	 * KsapFrameworkServiceLocator.getCourseSearchStrategy();
	 * List<EnumeratedValueInfo> enumeratedValueInfoList =
	 * strategy.getEnumerationValueInfoList(param); assertEquals(4,
	 * enumeratedValueInfoList.size()); for (EnumeratedValueInfo
	 * enumeratedValueInfo : enumeratedValueInfoList) {
	 * assertEquals(CourseSearchConstants.CAMPUS_LOCATION,
	 * enumeratedValueInfo.getEnumerationKey()); }
	 * 
	 * assertEquals("0", enumeratedValueInfoList.get(0).getCode());
	 * assertEquals("Seattle", enumeratedValueInfoList.get(0).getValue());
	 * 
	 * assertEquals("1", enumeratedValueInfoList.get(1).getCode());
	 * assertEquals("Bothell", enumeratedValueInfoList.get(1).getValue());
	 * 
	 * assertEquals("2", enumeratedValueInfoList.get(2).getCode());
	 * assertEquals("Tacoma", enumeratedValueInfoList.get(2).getValue());
	 * 
	 * assertEquals("AL", enumeratedValueInfoList.get(3).getCode());
	 * assertEquals("All", enumeratedValueInfoList.get(3).getValue());
	 * 
	 * 
	 * }
	 */

	/*
	 * @Test public void testGetEnumerationValueInfoList2() throws Exception {
	 * String param = ""; CourseSearchStrategy strategy =
	 * KsapFrameworkServiceLocator.getCourseSearchStrategy();
	 * List<EnumeratedValueInfo> enumeratedValueInfoList =
	 * strategy.getEnumerationValueInfoList(param); assertEquals(0,
	 * enumeratedValueInfoList.size());
	 * 
	 * 
	 * }
	 */

	@Test
	public void testProcessRequests() throws Exception {
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("ASTR");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("306");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		SearchRequestInfo request = new SearchRequestInfo(
				"myplan.lu.search.division");
		request.addParam("division", "ASTR  ");
		request.addParam("campuses", CourseSearchStrategyImpl.NO_CAMPUS);
		requests.add(request);
		strategy.processRequests(requests, form);
		SearchParamInfo param = null;
		List<SearchParamInfo> params = null;
		assertEquals(3, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals(2, params.size());
		assertEquals("myplan.lu.search.division", request.getSearchKey());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("ASTR  ", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals(2, params.size());
		assertEquals("myplan.lu.search.title", request.getSearchKey());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTR", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));

		request = requests.get(2);
		params = request.getParams();
		assertEquals(2, params.size());
		assertEquals("myplan.lu.search.description", request.getSearchKey());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTR", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));

	}

	@Test
	public void testProcessRequests2() throws Exception {
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("HIST");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		SearchRequestInfo request = new SearchRequestInfo(
				"myplan.lu.search.division");
		request.addParam("division", "HIST  ");
		request.addParam("campuses", CourseSearchStrategyImpl.NO_CAMPUS);
		requests.add(request);
		List<SearchParamInfo> params = null;
		SearchParamInfo param = null;
		strategy.processRequests(requests, form);
		assertEquals(3, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("myplan.lu.search.division", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("HIST  ", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("myplan.lu.search.title", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("HIST", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(2);
		// TODO: correct expectations
		// params = request.getParams();
		// assertEquals("myplan.lu.search.additionalDivision",
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
		assertEquals("myplan.lu.search.description", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("HIST", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

	}

	@Test
	public void testProcessRequests3() throws Exception {
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("PHILONOMY");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		SearchRequestInfo request = new SearchRequestInfo(
				"myplan.lu.search.fulltext");
		request.addParam("queryText", "PHILONOMY");
		request.addParam("campuses", "310");
		requests.add(request);
		List<SearchParamInfo> params = null;
		SearchParamInfo param = null;
		strategy.processRequests(requests, form);
		assertEquals(2, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("myplan.lu.search.title", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("PHILONOMY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("myplan.lu.search.description", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("PHILONOMY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));
	}

	@Test
	public void testProcessRequests4() throws Exception {
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("ASTRONOMY BIOLOGY");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("310");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		SearchRequestInfo request = new SearchRequestInfo(
				"myplan.lu.search.fulltext");
		request.addParam("queryText", "ASTRONOMY");
		request.addParam("campuses", "310");
		requests.add(request);
		request = new SearchRequestInfo("myplan.lu.search.fulltext");
		request.addParam("queryText", "BIOLOGY");
		request.addParam("campuses", "310");
		requests.add(request);

		List<SearchParamInfo> params = null;
		SearchParamInfo param = null;
		strategy.processRequests(requests, form);
		assertEquals(4, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("myplan.lu.search.title", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTRONOMY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(2);
		params = request.getParams();
		assertEquals("myplan.lu.search.description", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTRONOMY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("myplan.lu.search.title", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("BIOLOGY", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals("310", param.getValues().get(0));

		request = requests.get(3);
		params = request.getParams();
		assertEquals("myplan.lu.search.description", request.getSearchKey());
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
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("ASTR BIOL");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("306");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		SearchRequestInfo request = new SearchRequestInfo(
				"myplan.lu.search.division");
		request.addParam("division", "ASTR  ");
		request.addParam("campuses", CourseSearchStrategyImpl.NO_CAMPUS);
		requests.add(request);
		request = new SearchRequestInfo("myplan.lu.search.division");
		request.addParam("division", "BIOL  ");
		request.addParam("campuses", CourseSearchStrategyImpl.NO_CAMPUS);
		requests.add(request);

		List<SearchParamInfo> params = null;
		SearchParamInfo param = null;
		strategy.processRequests(requests, form);
		assertEquals(6, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("myplan.lu.search.division", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("ASTR  ", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));

		request = requests.get(1);
		params = request.getParams();
		assertEquals("myplan.lu.search.division", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("BIOL  ", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));

		request = requests.get(2);
		params = request.getParams();
		assertEquals("myplan.lu.search.title", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTR", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));

		request = requests.get(3);
		params = request.getParams();
		assertEquals("myplan.lu.search.description", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("ASTR", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));

		request = requests.get(4);
		params = request.getParams();
		assertEquals("myplan.lu.search.title", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("BIOL", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));

		request = requests.get(5);
		params = request.getParams();
		assertEquals("myplan.lu.search.description", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("queryText", param.getKey());
		assertEquals("BIOL", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));
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
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		SearchRequestInfo request = new SearchRequestInfo(
				"myplan.lu.search.division");
		request.addParam("division", "A S   ");
		request.addParam("campuses", CourseSearchStrategyImpl.NO_CAMPUS);
		requests.add(request);
		strategy.processRequests(requests, form);
		SearchParamInfo param = null;
		List<SearchParamInfo> params = null;
		assertEquals(1, requests.size());

		request = requests.get(0);
		params = request.getParams();
		assertEquals("myplan.lu.search.division", request.getSearchKey());
		assertEquals(2, params.size());
		param = params.get(0);
		assertEquals("division", param.getKey());
		assertEquals("A S   ", param.getValues().get(0));
		param = params.get(1);
		assertEquals("campuses", param.getKey());
		assertEquals(CourseSearchStrategyImpl.NO_CAMPUS,
				param.getValues().get(0));
	}

	@Test
	public void testProcessRequests7() throws Exception {
		ArrayList<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("");
		List<String> campusParams = new ArrayList<String>();
		form.setCampusSelect(campusParams);
		form.setSearchTerm("");
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		;
		strategy.processRequests(requests, form);
		assertEquals(0, requests.size());

	}
}
