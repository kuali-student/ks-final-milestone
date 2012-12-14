package org.kuali.student.myplan.course;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.kim.impl.identity.PersonImpl;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.common.search.dto.SearchResultRow;
import org.kuali.student.core.atp.dto.AtpTypeInfo;
import org.kuali.student.myplan.academicplan.service.AcademicPlanService;
import org.kuali.student.myplan.course.controller.CourseSearchController;
import org.kuali.student.myplan.course.controller.CourseSearchStrategy;
import org.kuali.student.myplan.course.dataobject.CourseSearchItem;
import org.kuali.student.myplan.course.form.CourseSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit Test Class for Course Search Controller
 * User: kmuthu
 * Date: 12/20/11
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:myplan-test-context.xml"})
public class CourseSearchControllerTest {

    @Autowired
    private CourseSearchController searchController;

    public CourseSearchController getSearchController() {
        return searchController;
    }

    public void setSearchController(CourseSearchController searchController) {
        this.searchController = searchController;
    }
    @Autowired
    private CourseSearchStrategy courseSearchStrategy = null;

    public CourseSearchStrategy getCourseSearchStrategy() {
        return courseSearchStrategy;
    }

    public void setCourseSearchStrategy(CourseSearchStrategy strategy) {
        this.courseSearchStrategy = strategy;
    }
    @Autowired
    private AcademicPlanService academicPlanService;

    public AcademicPlanService getAcademicPlanService() {
        return academicPlanService;
    }

    public void setAcademicPlanService(AcademicPlanService academicPlanService) {
        this.academicPlanService = academicPlanService;
    }
//    @Autowired
//    private PersonImpl person;
//
//    public PersonImpl getPersonImpl() {
//        return person;
//    }
//
//    public void setPersonImpl(PersonImpl personImpl) {
//        this.person = personImpl;
//    }

    @Test
    public void testHitComparator() {
        CourseSearchController.HitComparator comparator = new CourseSearchController.HitComparator();

        CourseSearchController.Hit hit1 = new CourseSearchController.Hit("a");
        hit1.count++;

        CourseSearchController.Hit hit2 = new CourseSearchController.Hit("b");
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
        CourseSearchController controller = getSearchController();
        SearchResultRow row = new SearchResultRow();
        row.addCell("key", "value");

        assertEquals("value", controller.getCellValue(row, "key"));

        try {
            controller.getCellValue(row, "fail");
            fail("should have throw exception");
        }
        catch( Exception e ) {}
    }

    @Test
    public void testGetCreditMap() {
        CourseSearchController controller = getSearchController();
        HashMap<String, CourseSearchController.Credit> map = controller.getCreditMap();
        assertFalse(map.isEmpty());
    }

    @Test
    public void testGetCreditByID() {
        CourseSearchController controller = getSearchController();

        CourseSearchController.Credit nothing = controller.getCreditByID("nothing");
        assertNull(nothing);

        CourseSearchController.Credit something = controller.getCreditByID("kuali.creditType.credit.degree.1-4");
        assertNotNull(something);
    }

    @Test
    public void testSearchForCoursesExactMatch() {
/*
        CourseSearchForm form = new CourseSearchForm();
        form.setSearchQuery("CHEM 453");
        form.setCampusSelect("0");
        form.setSearchTerm("any");
        form.setViewId("CourseSearch-FormView");
        academicPlanService = getAcademicPlanService();
//        person=getPersonImpl();
        searchController.setAcademicPlanService(academicPlanService);
//        searchController.setPerson(person);
        searchController.searchForCourses(form, null, null, null);
        List<CourseSearchItem> results = form.getCourseSearchResults();
        assertEquals(1, results.size());
        CourseSearchItem course = results.get(0);
        assertEquals("CHEM   453", course.getCode());
        assertEquals("CHEM", course.getSubject());
        assertEquals("453", course.getNumber());
        assertEquals("400", course.getLevel());
        assertEquals("3", course.getCredit());*/
    }

    @Test
    public void testSearchForCoursesSubjectArea() {

        /*     CourseSearchForm form = new CourseSearchForm();
    form.setSearchQuery("HDCE");
    form.setCampusSelect("0");
    form.setSearchTerm("any");
    form.setViewId("CourseSearch-FormView");
    academicPlanService = getAcademicPlanService();
//        person=getPersonImpl();
    searchController.setAcademicPlanService(academicPlanService);
//        searchController.setPerson(person);

    searchController.searchForCourses(form, null, null, null);

    List<CourseSearchItem> results = form.getCourseSearchResults();
    assertTrue( results.size() > 0 );*/
    }

    @Test
    public void testSearchForCoursesSubjectAreaLevel() {
/*
        CourseSearchForm form = new CourseSearchForm();
        form.setSearchQuery("ENGL 1xx");
        form.setCampusSelect("0");
        form.setSearchTerm("any");
        form.setViewId("CourseSearch-FormView");
        academicPlanService = getAcademicPlanService();
//        person=getPersonImpl();
        searchController.setAcademicPlanService(academicPlanService);
//        searchController.setPerson(person);

        searchController.searchForCourses(form, null, null, null);

        List<CourseSearchItem> results = form.getCourseSearchResults();
        assertTrue( results.size() > 0 );*/
    }

    @Test
    public void testIsCourseOffered() {

        CourseSearchForm form = new CourseSearchForm();
        CourseSearchItem course = new CourseSearchItem();
        CourseSearchController controller = getSearchController();

        try {
            form.setSearchTerm(CourseSearchForm.SEARCH_TERM_ANY_ITEM);

            assertTrue(controller.isCourseOffered(form, course));

            form.setSearchTerm("fake");
            course.setCode("CHEM");
            assertTrue(controller.isCourseOffered(form, course));

            course.setCode("FAKE");
            assertFalse(controller.isCourseOffered(form, course));
        } catch (Exception e) {
            fail("failed!");
        }
    }

    @Test
    public void testProcessSearchRequests() {

        CourseSearchForm form = new CourseSearchForm();
        CourseSearchController controller = getSearchController();
        form.setSearchQuery("AS 101");
        List<String> campusParams = new ArrayList<String>();
        campusParams.add("306");
        form.setCampusSelect(campusParams);
        form.setSearchTerm("any");
        CourseSearchStrategy strategy = getCourseSearchStrategy();
        List<SearchRequest> requests = null;
        ArrayList<CourseSearchController.Hit> hits = null;
        try {
            requests = strategy.queryToRequests(form, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            hits = controller.processSearchRequests(requests);
        } catch (MissingParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        assertEquals(1, hits.size());
        assertEquals("dd003c5a-d0e4-4cfe-a81c-cbb756383685", hits.get(0).courseID);

    }
    @Test
    public void testProcessSearchRequests2() {

        CourseSearchForm form = new CourseSearchForm();
        CourseSearchController controller = getSearchController();
        form.setSearchQuery("ASTR");
        List<String> campusParams = new ArrayList<String>();
        campusParams.add("306");
        form.setCampusSelect(campusParams);
        form.setSearchTerm("any");
        CourseSearchStrategy strategy = getCourseSearchStrategy();
        List<SearchRequest> requests = null;
        ArrayList<CourseSearchController.Hit> hits = null;
        try {
            requests = strategy.queryToRequests(form, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            hits = controller.processSearchRequests(requests);
        } catch (MissingParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        assertTrue(hits.size() > 0);
    }
    @Test
    public void testProcessSearchRequests3() {

        CourseSearchController controller = getSearchController();
        List<SearchRequest> requests = new ArrayList<SearchRequest>();
        ArrayList<CourseSearchController.Hit> hits = null;
        try {
            hits = controller.processSearchRequests(requests);
        } catch (MissingParameterException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        assertEquals(0, hits.size());
    }

    @Test
    public void testPopulateFacets() {

        CourseSearchController controller = getSearchController();
        CourseSearchForm form = new CourseSearchForm();
        form.setSearchQuery("CHEM 110");
        List<String> campusParams = new ArrayList<String>();
        campusParams.add("306");
        form.setCampusSelect(campusParams);
        form.setSearchTerm("any");
        form.setViewId("CourseSearch-FormView");
        List<CourseSearchItem> courses = new ArrayList<CourseSearchItem>();
        List<AtpTypeInfo> termInfos = new ArrayList<AtpTypeInfo>();
        AtpTypeInfo termInfo = new AtpTypeInfo();
        termInfo.setDurationType("kuali.uw.atp.duration.quarter");
        termInfo.setSeasonalType("kuali.uw.atp.season.autumn");
        termInfo.setId("kuali.uw.atp.type.autumn");
        termInfo.setName("autumn");
        termInfo.setDescr("autumn quarter");
        termInfo.setEffectiveDate(null);
        termInfo.setExpirationDate(null);
        termInfos.add(termInfo);

        CourseSearchItem courseSearchItem = new CourseSearchItem();
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
        controller.populateFacets(form, courses);
/*        assertTrue(form.getCurriculumFacetItems().size()>0);
        assertTrue(form.getCreditsFacetItems().size()>0);
        assertTrue(form.getCourseLevelFacetItems().size()>0);
        assertTrue(form.getGenEduReqFacetItems().size()>0);
        assertTrue(form.getTermsFacetItems().size()>0);*/
    }
    @Test
    public void testPopulateFacets2() {

        CourseSearchController controller = getSearchController();
        CourseSearchForm form = new CourseSearchForm();
        form.setSearchQuery("");
        List<String> campusParams = new ArrayList<String>();
        campusParams.add("306");
        form.setCampusSelect(campusParams);
        form.setSearchTerm("any");
        form.setViewId("CourseSearch-FormView");
        List<CourseSearchItem> courses = new ArrayList<CourseSearchItem>();
        controller.populateFacets(form, courses);
/*        assertTrue(form.getCurriculumFacetItems().size()==0);
        assertTrue(form.getCreditsFacetItems().size()==0);
        assertTrue(form.getCourseLevelFacetItems().size()==0);
        assertTrue(form.getGenEduReqFacetItems().size()==0);
        assertTrue(form.getTermsFacetItems().size()==0);*/
    }

    @Test
    public void testExtractDivisions() throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("A", "A   ");
        map.put("AB", "A B ");
        map.put("B", "B   ");
        map.put("C", "C   ");
        CourseSearchController controller = getSearchController();
        ArrayList<String> divisions = new ArrayList<String>();
        String query = "A B C";
        query = controller.extractDivisions(map, query, divisions, false);
        assertEquals("", query);
        assertEquals(2, divisions.size());
        assertEquals("A B ", divisions.get(0));
        assertEquals("C   ", divisions.get(1));
    }

    @Test
    public void testFetchCourseDivisions() throws Exception {
        CourseSearchController controller = getSearchController();
        HashMap<String, String> divisionsMap = controller.fetchCourseDivisions();
        assertFalse(divisionsMap.isEmpty());
    }




}
