package org.kuali.student.ap.coursesearch.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.coursesearch.FacetState;
import org.kuali.student.ap.coursesearch.SearchInfo;
import org.kuali.student.ap.coursesearch.dataobject.CourseSearchItemImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-test-context.xml" })
public class CourseFacetStrategyTest {

    public static final String principalId = "student1";
    public ContextInfo context;

    CourseFacetStrategyImpl strategy = null;

    @Before
    public void setUp() {
        context = new ContextInfo();
        context.setPrincipalId(principalId);
        strategy = new CourseFacetStrategyImpl();
    }

    @Test
    public void testIsThisThingOn() {
         assertNotNull("Strategy is null", strategy);
    }

    @Test
    public void testGetFacetColumnsReversed(){
        List<String> facets = strategy.getFacetColumnsReversed();
        assertEquals(5,facets.size());
        assertEquals("facet_quarter",facets.get(0));
        assertEquals("facet_genedureq",facets.get(1));
        assertEquals("facet_credits",facets.get(2));
        assertEquals("facet_level",facets.get(3));
        assertEquals("facet_curriculum",facets.get(4));
    }

    @Test
    public void testGetFacetColumns(){
        Map<String, Integer> facets = strategy.getFacetColumns();
        assertEquals((long)facets.get("facet_curriculum"),(long)4);
        assertEquals((long)facets.get("facet_credits"),(long)2);
        assertEquals((long)facets.get("facet_genedureq"),(long)1);
        assertEquals((long)facets.get("facet_level"),(long)3);
        assertEquals((long)facets.get("facet_quarter"),(long)0);
    }

    @Test
    public void testCreateInitialFacetStateMap(){
        List<SearchInfo> results = new ArrayList<SearchInfo>();
        Map<String,Map<String,FacetState>> map = strategy.createInitialFacetStateMap(createFacetColumns(),results);
        testMap(map, new String[]{}, new String[]{}, new String[]{}, new String[]{}, new String[]{});

        results.add(createSearchResultCourse1());
        map = strategy.createInitialFacetStateMap(createFacetColumns(),results);
        testMap(map, new String[]{"DSHU->1"}, new String[]{"3.0->1","4.0->1","1.0->1","2.0->1"}, new String[]{"300->1","200->1","100->1"}, new String[]{"FA 14->1", "SP 13->1", "SP 14->1"}, new String[]{"ENGL->1"});

        results.add(createSearchResultCourse2());
        map = strategy.createInitialFacetStateMap(createFacetColumns(),results);
        testMap(map, new String[]{"DSHU->2"}, new String[]{"3.0->2", "4.0->2", "1.0->1", "5.0->1", "2.0->1"}, new String[]{"300->2", "200->1", "100->1"}, new String[]{"FA 14->2", "SP 13->2", "SP 14->1"}, new String[]{"ENGL->1"});

        results.add(createSearchResultCourse3());
        map = strategy.createInitialFacetStateMap(createFacetColumns(),results);
        testMap(map, new String[]{"DSHU->2"}, new String[]{"3.0->3", "4.0->3", "1.0->2", "5.0->1", "2.0->1"}, new String[]{"300->3", "200->1", "100->1"}, new String[]{"FA 14->2", "SP 13->3", "SP 14->2"}, new String[]{"ENGL->2"});
    }

    @Test
    public void testProcessFacetStateMap(){
        List<SearchInfo> results = new ArrayList<SearchInfo>();
        Map<String,Map<String,FacetState>> map = strategy.createInitialFacetStateMap(createFacetColumns(),results);
        results.add(createSearchResultCourse1());
        results.add(createSearchResultCourse2());
        results.add(createSearchResultCourse3());
        map = strategy.createInitialFacetStateMap(createFacetColumns(),results);
        map = strategy.processFacetStateMap(map, createFacetColumns());

        Map<String,FacetState> credits = map.get("facet_credits");
        Iterator<String> iter= credits.keySet().iterator();
        if(!iter.next().equals("1.0")) fail("Fail credits out of order");
        if(!iter.next().equals("2.0")) fail("Fail credits out of order");
        if(!iter.next().equals("3.0")) fail("Fail credits out of order");
        if(!iter.next().equals("4.0")) fail("Fail credits out of order");
        if(!iter.next().equals("5.0")) fail("Fail credits out of order");

        Map<String,FacetState> levels = map.get("facet_level");
        iter= levels.keySet().iterator();
        if(!iter.next().equals("100")) fail("Fail levels out of order");
        if(!iter.next().equals("200")) fail("Fail levels out of order");
        if(!iter.next().equals("300")) fail("Fail levels out of order");

        Map<String,FacetState> quarters = map.get("facet_quarter");
        iter= quarters.keySet().iterator();
        if(!iter.next().equals("SP 13")) fail("Fail quarters out of order");
        if(!iter.next().equals("FA 14")) fail("Fail quarters out of order");
        if(!iter.next().equals("SP 14")) fail("Fail quarters out of order");

        assertNotNull("Strategy is null", strategy);
    }

    @Test
    public void testFacetClick(){
        List<SearchInfo> results = new ArrayList<SearchInfo>();
        Map<String,Map<String,FacetState>> map = strategy.createInitialFacetStateMap(createFacetColumns(),results);
        results.add(createSearchResultCourse1());
        results.add(createSearchResultCourse2());
        results.add(createSearchResultCourse3());
        map = strategy.createInitialFacetStateMap(createFacetColumns(),results);
        map = strategy.processFacetStateMap(map, createFacetColumns());

        boolean oneClick = false;
        oneClick = strategy.facetClick("SP 14","facet_quarter", map.get("facet_quarter"), oneClick);
        if(!oneClick) fail("Click value is false should be true");
        testMap(map, new String[]{"DSHU->2->true"}, new String[]{"3.0->3->true", "4.0->3->true", "1.0->2->true", "5.0->1->true", "2.0->1->true"}, new String[]{"300->3->true", "200->1->true", "100->1->true"}, new String[]{"FA 14->2->false", "SP 13->3->false", "SP 14->2->true"}, new String[]{"ENGL->2->true"});

        oneClick = false;
        oneClick = strategy.facetClick("300","facet_level", map.get("facet_level"), oneClick);
        if(!oneClick) fail("Click value is false should be true");
        testMap(map, new String[]{"DSHU->2->true"}, new String[]{"3.0->3->true", "4.0->3->true", "1.0->2->true", "5.0->1->true", "2.0->1->true"}, new String[]{"300->3->true", "200->1->false", "100->1->false"}, new String[]{"FA 14->2->false", "SP 13->3->false", "SP 14->2->true"}, new String[]{"ENGL->2->true"});

        oneClick = false;
        oneClick = strategy.facetClick("All","", map.get("facet_level"), oneClick);
        if(oneClick) fail("Click value is true should be false");
        testMap(map, new String[]{"DSHU->2->true"}, new String[]{"3.0->3->true", "4.0->3->true", "1.0->2->true", "5.0->1->true", "2.0->1->true"}, new String[]{"300->3->true", "200->1->true", "100->1->true"}, new String[]{"FA 14->2->false", "SP 13->3->false", "SP 14->2->true"}, new String[]{"ENGL->2->true"});

        oneClick = false;
        oneClick = strategy.facetClick("All","", map.get("facet_quarter"), oneClick);
        if(oneClick) fail("Click value is true should be false");
        testMap(map, new String[]{"DSHU->2->true"}, new String[]{"3.0->3->true", "4.0->3->true", "1.0->2->true", "5.0->1->true", "2.0->1->true"}, new String[]{"300->3->true", "200->1->true", "100->1->true"}, new String[]{"FA 14->2->true", "SP 13->3->true", "SP 14->2->true"}, new String[]{"ENGL->2->true"});

    }

    @Test
    public void testFacetClickAll(){
        List<SearchInfo> results = new ArrayList<SearchInfo>();
        Map<String,Map<String,FacetState>> map = strategy.createInitialFacetStateMap(createFacetColumns(),results);
        results.add(createSearchResultCourse1());
        results.add(createSearchResultCourse2());
        results.add(createSearchResultCourse3());
        map = strategy.createInitialFacetStateMap(createFacetColumns(),results);
        map = strategy.processFacetStateMap(map, createFacetColumns());

        boolean oneClick = false;

        oneClick = strategy.facetClickAll(oneClick,map);
        if(!oneClick) fail("Click value is false should be true");
        testMap(map, new String[]{"DSHU->2->true"}, new String[]{"3.0->3->true", "4.0->3->true", "1.0->2->true", "5.0->1->true", "2.0->1->true"}, new String[]{"300->3->true", "200->1->true", "100->1->true"}, new String[]{"FA 14->2->true", "SP 13->3->true", "SP 14->2->true"}, new String[]{"ENGL->2->true"});

        oneClick = strategy.facetClick("SP 14","facet_quarter", map.get("facet_quarter"), oneClick);
        oneClick = false;
        oneClick = strategy.facetClick("300","facet_level", map.get("facet_level"), oneClick);

        oneClick = strategy.facetClickAll(oneClick,map);
        if(oneClick) fail("Click value is true should be false");
        testMap(map, new String[]{"DSHU->2->true"}, new String[]{"3.0->3->true", "4.0->3->true", "1.0->2->true", "5.0->1->true", "2.0->1->true"}, new String[]{"300->3->true", "200->1->true", "100->1->true"}, new String[]{"FA 14->2->true", "SP 13->3->true", "SP 14->2->true"}, new String[]{"ENGL->2->true"});

    }

    @Test
    public void testUpdateFacetCounts(){
        List<SearchInfo> results = new ArrayList<SearchInfo>();
        results.add(createSearchResultCourse1());
        Map<String,Map<String,FacetState>> map = strategy.createInitialFacetStateMap(createFacetColumns(),results);
        map = strategy.processFacetStateMap(map, createFacetColumns());
        testMap(map, new String[]{"DSHU->1"}, new String[]{"3.0->1","4.0->1","1.0->1","2.0->1"}, new String[]{"300->1","200->1","100->1"}, new String[]{"FA 14->1", "SP 13->1", "SP 14->1"}, new String[]{"ENGL->1"});

        results.add(createSearchResultCourse2());
        strategy.updateFacetCounts(results,map,createFacetColumns());
        testMap(map, new String[]{"DSHU->2"}, new String[]{"3.0->2","4.0->2","1.0->1","2.0->1"}, new String[]{"300->2","200->1", "100->1"}, new String[]{"FA 14->2", "SP 13->2", "SP 14->1"}, new String[]{"ENGL->1"});

        results.add(createSearchResultCourse3());
        strategy.updateFacetCounts(results,map,createFacetColumns());
        testMap(map, new String[]{"DSHU->2"}, new String[]{"3.0->3","4.0->3","1.0->2","2.0->1"}, new String[]{"300->3","200->1", "100->1"}, new String[]{"FA 14->2", "SP 13->3", "SP 14->2"}, new String[]{"ENGL->2"});
    }


    private void testMap(Map<String,Map<String,FacetState>> map, String genEd[], String credits[], String level[], String quarter[],String curr[]){
        testMapEntry("facet_quarter",quarter,map);
        testMapEntry("facet_level",level,map);
        testMapEntry("facet_credits",credits,map);
        testMapEntry("facet_genedureq",genEd,map);
        testMapEntry("facet_curriculum",curr,map);

    }
    private void testMapEntry(String entryName, String expected[], Map<String,Map<String,FacetState>> map){
        if(expected.length==0){
            try{
                if(!map.get(entryName).isEmpty()){
                    fail(entryName+" should be empty but has items in it");
                }
            }catch (NullPointerException e){
                fail(entryName+" not found in map");
            }
        }
        if(map.get(entryName).size()!=expected.length){
            fail(entryName+" size is off: "+map.get(entryName).size()+" should be "+ expected.length);
        }
        for(String value : expected){
            String values[] = value.split("->");
            String text = values[0];
            if(!map.get(entryName).containsKey(text)){
                fail(entryName+" is missing value: "+ value);
            }
            if(values.length==2){
                int count = Integer.parseInt(values[1]);
                if(map.get(entryName).get(text).getCount()!=count){
                    fail(entryName+" at value: "+ text +" count is wrong: "+ map.get(entryName).get(text).getCount() + " should be "+count);
                }
            }
            if(values.length==3){
                boolean checked = Boolean.parseBoolean(values[2]);
                if(map.get(entryName).get(text).isChecked()!= checked){
                    fail(entryName+" at value: "+ text +" checked is wrong: "+ map.get(entryName).get(text).isChecked() + " should be "+checked);
                }
            }else{
                if(!map.get(entryName).get(text).isChecked()){
                    fail(entryName+" at value: "+ text +" is not checked");
                }
            }
        }
    }
    private Map<String,List<String>> createFacetColumns(){
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        List<String> credits = new ArrayList<String>();
        credits.add("1.0");
        credits.add("4.0");
        credits.add("2.0");
        credits.add("3.0");
        map.put("facet_credits",credits);

        List<String> quarter = new ArrayList<String>();
        quarter.add("FA 14");
        quarter.add("SP 14");
        quarter.add("SP 13");
        map.put("facet_quarter",quarter);

        List<String> curriculumn = new ArrayList<String>();
        curriculumn.add("ENGL");
        map.put("facet_curriculum",curriculumn);

        List<String> genedureq = new ArrayList<String>();
        genedureq.add("DSHU");
        map.put("facet_genedureq",genedureq);


        List<String> level = new ArrayList<String>();
        level.add("300");
        level.add("100");
        level.add("200");
        map.put("facet_level",level);

        return map;
    }
    private SearchInfoImpl createSearchResultCourse1(){
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setCode("ENGL201");
        course.setCourseName("Inventing Western Literature: Ancient and Medieval Traditions");
        Set<String> credits = new HashSet<>();
        credits.add("1.0");
        credits.add("4.0");
        credits.add("2.0");
        credits.add("3.0");
        course.setCreditsFacetKeys(credits);

        Set<String> quarter = new HashSet<String>();
        quarter.add("FA 14");
        quarter.add("SP 14");
        quarter.add("SP 13");
        course.setTermsFacetKeys(quarter);

        Set<String> curriculumn = new HashSet<String>();
        curriculumn.add("ENGL");
        course.setCurriculumFacetKeys(curriculumn);

        Set<String> genedureq = new HashSet<String>();
        genedureq.add("DSHU");
        course.setGenEduReqFacetKeys(genedureq);


        Set<String> level = new HashSet<String>();
        level.add("300");
        level.add("100");
        level.add("200");
        course.setCourseLevelFacetKeys(level);

        return new SearchInfoImpl(course);
    }
    private SearchInfoImpl createSearchResultCourse2(){
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setCode("ENGL300");
        course.setCourseName("Writing about Literature");
        Set<String> credits = new HashSet<>();
        credits.add("4.0");
        credits.add("5.0");
        credits.add("3.0");
        course.setCreditsFacetKeys(credits);

        Set<String> quarter = new HashSet<String>();
        quarter.add("FA 14");
        quarter.add("SP 13");
        course.setTermsFacetKeys(quarter);

        Set<String> curriculumn = new HashSet<String>();
        course.setCurriculumFacetKeys(curriculumn);

        Set<String> genedureq = new HashSet<String>();
        genedureq.add("DSHU");
        course.setGenEduReqFacetKeys(genedureq);


        Set<String> level = new HashSet<String>();
        level.add("300");
        course.setCourseLevelFacetKeys(level);

        return new SearchInfoImpl(course);
    }
    private SearchInfoImpl createSearchResultCourse3(){
        CourseSearchItemImpl course = new CourseSearchItemImpl();
        course.setCode("ENGL301");
        course.setCourseName("Critical Methods in the Study of Literature");
        Set<String> credits = new HashSet<>();
        credits.add("1.0");
        credits.add("4.0");
        credits.add("3.0");
        course.setCreditsFacetKeys(credits);

        Set<String> quarter = new HashSet<String>();
        quarter.add("SP 14");
        quarter.add("SP 13");
        course.setTermsFacetKeys(quarter);

        Set<String> curriculumn = new HashSet<String>();
        curriculumn.add("ENGL");
        course.setCurriculumFacetKeys(curriculumn);

        Set<String> genedureq = new HashSet<String>();
        course.setGenEduReqFacetKeys(genedureq);


        Set<String> level = new HashSet<String>();
        level.add("300");
        course.setCourseLevelFacetKeys(level);

        return new SearchInfoImpl(course);
    }
}
