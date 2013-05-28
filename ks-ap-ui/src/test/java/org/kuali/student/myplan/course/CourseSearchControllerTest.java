package org.kuali.student.myplan.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.course.CourseSearchForm;
import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.ap.framework.course.CourseSearchStrategy;
import org.kuali.student.ap.framework.course.Credit;
import org.kuali.student.myplan.course.controller.CourseSearchController;
import org.kuali.student.myplan.course.controller.CourseSearchStrategyImpl;
import org.kuali.student.myplan.course.dataobject.CourseSearchItemImpl;
import org.kuali.student.myplan.course.form.CourseSearchFormImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit Test Class for Course Search Controller User: kmuthu Date: 12/20/11
 * Time: 11:54 AM To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-test-context.xml" })
public class CourseSearchControllerTest {

	public static final String principalId = "student1";
	public ContextInfo context;

	@Before
	public void setUp() {
		context = new ContextInfo();
		context.setPrincipalId(principalId);
	}

	@Autowired
	private CourseSearchController searchController;

	public CourseSearchController getSearchController() {
		return searchController;
	}

	public void setSearchController(CourseSearchController searchController) {
		this.searchController = searchController;
	}

	// @Autowired
	// private PersonImpl person;
	//
	// public PersonImpl getPersonImpl() {
	// return person;
	// }
	//
	// public void setPersonImpl(PersonImpl personImpl) {
	// this.person = personImpl;
	// }

	@Test
	public void testHitComparator() {
		CourseSearchStrategyImpl.HitComparator comparator = new CourseSearchStrategyImpl.HitComparator();

		CourseSearchStrategyImpl.Hit hit1 = new CourseSearchStrategyImpl.Hit(
				"a");
		hit1.count++;

		CourseSearchStrategyImpl.Hit hit2 = new CourseSearchStrategyImpl.Hit(
				"b");
		hit2.count++;
		hit2.count++;
		hit2.count++;

		assertTrue(comparator.compare(hit1, hit2) > 0);
		assertTrue(comparator.compare(hit2, hit1) < 0);
		assertTrue(comparator.compare(hit1, hit1) == 0);
		assertTrue(comparator.compare(hit1, null) > 0);
		assertTrue(comparator.compare(null, hit2) < 0);
	}

	@Test
	public void testGetCellValue() {
		CourseSearchStrategy strategy = KsapFrameworkServiceLocator
				.getCourseSearchStrategy();
		SearchResultRowInfo row = new SearchResultRowInfo();
		row.addCell("key", "value");
		assertEquals("value", strategy.getCellValue(row, "key"));
		try {
			strategy.getCellValue(row, "fail");
			fail("should have throw exception");
		} catch (Exception e) {
		}
	}

	@Test
	public void testGetCreditMap() {
		CourseSearchStrategy strategy = KsapFrameworkServiceLocator
				.getCourseSearchStrategy();
		Map<String, Credit> map = strategy.getCreditMap();
		assertFalse(map.isEmpty());
	}

	@Test
	public void testGetCreditByID() {
		CourseSearchStrategy strategy = KsapFrameworkServiceLocator
				.getCourseSearchStrategy();
		Credit nothing = strategy.getCreditByID("nothing");
		assertNull(nothing);

		Credit something = strategy
				.getCreditByID("kuali.result.scale.credit.degree");
		assertNotNull(something);
	}

	@Test
	public void testSearchForCoursesExactMatch() {
		/*
		 * CourseSearchForm form = new CourseSearchForm();
		 * form.setSearchQuery("CHEM 453"); form.setCampusSelect("0");
		 * form.setSearchTerm("any"); form.setViewId("CourseSearch-FormView");
		 * academicPlanService = getAcademicPlanService(); //
		 * person=getPersonImpl();
		 * searchController.setAcademicPlanService(academicPlanService); //
		 * searchController.setPerson(person);
		 * searchController.searchForCourses(form, null, null, null);
		 * List<CourseSearchItem> results = form.getCourseSearchResults();
		 * assertEquals(1, results.size()); CourseSearchItem course =
		 * results.get(0); assertEquals("CHEM   453", course.getCode());
		 * assertEquals("CHEM", course.getSubject()); assertEquals("453",
		 * course.getNumber()); assertEquals("400", course.getLevel());
		 * assertEquals("3", course.getCredit());
		 */
	}

	@Test
	public void testSearchForCoursesSubjectArea() {

		/*
		 * CourseSearchForm form = new CourseSearchForm();
		 * form.setSearchQuery("HDCE"); form.setCampusSelect("0");
		 * form.setSearchTerm("any"); form.setViewId("CourseSearch-FormView");
		 * academicPlanService = getAcademicPlanService(); //
		 * person=getPersonImpl();
		 * searchController.setAcademicPlanService(academicPlanService); //
		 * searchController.setPerson(person);
		 * 
		 * searchController.searchForCourses(form, null, null, null);
		 * 
		 * List<CourseSearchItem> results = form.getCourseSearchResults();
		 * assertTrue( results.size() > 0 );
		 */
	}

	@Test
	public void testSearchForCoursesSubjectAreaLevel() {
		/*
		 * CourseSearchForm form = new CourseSearchForm();
		 * form.setSearchQuery("ENGL 1xx"); form.setCampusSelect("0");
		 * form.setSearchTerm("any"); form.setViewId("CourseSearch-FormView");
		 * academicPlanService = getAcademicPlanService(); //
		 * person=getPersonImpl();
		 * searchController.setAcademicPlanService(academicPlanService); //
		 * searchController.setPerson(person);
		 * 
		 * searchController.searchForCourses(form, null, null, null);
		 * 
		 * List<CourseSearchItem> results = form.getCourseSearchResults();
		 * assertTrue( results.size() > 0 );
		 */
	}

	@Test
	public void testIsCourseOffered() throws Throwable {
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		CourseSearchItemImpl course = new CourseSearchItemImpl();
		CourseSearchStrategy strategy = KsapFrameworkServiceLocator
				.getCourseSearchStrategy();

		form.setSearchTerm(CourseSearchForm.SEARCH_TERM_ANY_ITEM);

		assertTrue(strategy.isCourseOffered(form, course));

		form.setSearchTerm("20122");
		course.setCode("7b1fb0cb-a070-487e-a6d5-c74e037b912c");
		course.setSubject("BIOL");
		assertTrue(strategy.isCourseOffered(form, course));

		course.setCode("FAKE");
		assertFalse(strategy.isCourseOffered(form, course));
	}

	// TODO: need reference data to support this, see KSAP-5
	/*
	 * @Test public void testProcessSearchRequests() throws Throwable {
	 * 
	 * CourseSearchForm form = new CourseSearchForm(); CourseSearchController
	 * controller = getSearchController(); form.setSearchQuery("AS 101");
	 * List<String> campusParams = new ArrayList<String>();
	 * campusParams.add("310"); form.setCampusSelect(campusParams);
	 * form.setSearchTerm("any"); CourseSearchStrategy strategy =
	 * getCourseSearchStrategy(); List<SearchRequestInfo> requests = null;
	 * ArrayList<CourseSearchController.Hit> hits = null; requests =
	 * strategy.queryToRequests(form, true, context); hits =
	 * controller.processSearchRequests(requests, context);
	 * 
	 * // assertEquals(1, hits.size()); //
	 * assertEquals("dd003c5a-d0e4-4cfe-a81c-cbb756383685",
	 * hits.get(0).courseID); }
	 */
	/*
	 * TODO: need reference data to support this, see KSAP-5
	 * 
	 * @Test public void testProcessSearchRequests2() throws Throwable {
	 * 
	 * CourseSearchForm form = new CourseSearchForm(); CourseSearchController
	 * controller = getSearchController(); form.setSearchQuery("ASTR");
	 * List<String> campusParams = new ArrayList<String>();
	 * campusParams.add("306"); form.setCampusSelect(campusParams);
	 * form.setSearchTerm("any"); CourseSearchStrategy strategy =
	 * getCourseSearchStrategy(); List<SearchRequestInfo> requests = null;
	 * ArrayList<CourseSearchController.Hit> hits = null; requests =
	 * strategy.queryToRequests(form, true, context); hits =
	 * controller.processSearchRequests(requests, context);
	 * 
	 * // assertTrue(hits.size() > 0); }
	 */
	@Test
	public void testProcessSearchRequests3() throws Throwable {
		CourseSearchStrategyImpl strategy = new CourseSearchStrategyImpl();
		List<SearchRequestInfo> requests = new ArrayList<SearchRequestInfo>();
		List<CourseSearchStrategyImpl.Hit> hits = strategy
				.processSearchRequests(requests);
		assertEquals(0, hits.size());
	}

	@Test
	public void testPopulateFacets() {

		CourseSearchStrategy strategy = KsapFrameworkServiceLocator
				.getCourseSearchStrategy();

		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("CHEM 110");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("306");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		form.setViewId("CourseSearch-FormView");
		List<CourseSearchItem> courses = new ArrayList<CourseSearchItem>();
		List<String> termInfos = new ArrayList<String>();
		// AtpInfo termInfo = new AtpInfo();
		// termInfo.setDurationType("kuali.uw.atp.duration.quarter");
		// termInfo.setSeasonalType("kuali.uw.atp.season.autumn");
		// termInfo.setId("kuali.uw.atp.type.autumn");
		// termInfo.setName("autumn");
		// termInfo.setDescr("autumn quarter");
		// termInfo.setEffectiveDate(null);
		// termInfo.setExpirationDate(null);
		termInfos.add("autumn");

		CourseSearchItemImpl courseSearchItem = new CourseSearchItemImpl();
		courseSearchItem.setCourseId("74995ac1-8d2a-45f2-a408-056cb929f8a7");
		courseSearchItem.setCode("CHEM   110");
		courseSearchItem.setNumber("110");
		courseSearchItem.setSubject("CHEM");
		courseSearchItem.setLevel("100");
		courseSearchItem.setCourseName("INTRODUCTION TO GENERAL CHEMISTRY");
		courseSearchItem.setCredit("3");
		courseSearchItem.setCreditMin(3);
		courseSearchItem.setCreditMax(3);
		courseSearchItem.setCreditType(CourseSearchItem.CreditType.fixed);
		courseSearchItem.setGenEduReq("NW");
		courseSearchItem.setStatus(CourseSearchItem.PlanState.SAVED);
		courseSearchItem.setTermInfoList(termInfos);
		courses.add(courseSearchItem);
		strategy.populateFacets(form, courses);
		/*
		 * assertTrue(form.getCurriculumFacetItems().size()>0);
		 * assertTrue(form.getCreditsFacetItems().size()>0);
		 * assertTrue(form.getCourseLevelFacetItems().size()>0);
		 * assertTrue(form.getGenEduReqFacetItems().size()>0);
		 * assertTrue(form.getTermsFacetItems().size()>0);
		 */
	}

	@Test
	public void testPopulateFacets2() {

		CourseSearchStrategy strategy = KsapFrameworkServiceLocator
				.getCourseSearchStrategy();
		CourseSearchFormImpl form = new CourseSearchFormImpl();
		form.setSearchQuery("");
		List<String> campusParams = new ArrayList<String>();
		campusParams.add("306");
		form.setCampusSelect(campusParams);
		form.setSearchTerm("any");
		form.setViewId("CourseSearch-FormView");
		List<CourseSearchItem> courses = new ArrayList<CourseSearchItem>();
		strategy.populateFacets(form, courses);
		/*
		 * assertTrue(form.getCurriculumFacetItems().size()==0);
		 * assertTrue(form.getCreditsFacetItems().size()==0);
		 * assertTrue(form.getCourseLevelFacetItems().size()==0);
		 * assertTrue(form.getGenEduReqFacetItems().size()==0);
		 * assertTrue(form.getTermsFacetItems().size()==0);
		 */
	}

	@Test
	public void testExtractDivisions() throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("A", "A   ");
		map.put("AB", "A B ");
		map.put("B", "B   ");
		map.put("C", "C   ");
		CourseSearchStrategy strategy = KsapFrameworkServiceLocator
				.getCourseSearchStrategy();
		ArrayList<String> divisions = new ArrayList<String>();
		String query = "A B C";
		query = strategy.extractDivisions(map, query, divisions, false);
		assertEquals("", query);
		assertEquals(2, divisions.size());
		assertEquals("A B ", divisions.get(0));
		assertEquals("C   ", divisions.get(1));
	}

	@Test
	public void testFetchCourseDivisions() throws Exception {
		CourseSearchStrategy strategy = KsapFrameworkServiceLocator
				.getCourseSearchStrategy();
		Map<String, String> divisionsMap = strategy.fetchCourseDivisions();
		assertFalse(divisionsMap.isEmpty());
	}

}
