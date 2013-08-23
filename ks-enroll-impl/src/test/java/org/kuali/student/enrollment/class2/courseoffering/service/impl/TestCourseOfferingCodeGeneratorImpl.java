/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Daniel on 6/7/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingCodeGenerator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-context.xml"})
public class TestCourseOfferingCodeGeneratorImpl {

    @Resource
    CourseOfferingCodeGenerator offeringCodeGenerator;

    @Test
    public void testGenerator(){

        String code;
        String courseOfferingCode = "ENGL101";
        code = offeringCodeGenerator.generateActivityOfferingCode(courseOfferingCode,new ArrayList<ActivityOfferingInfo>());
        assertEquals("A",code);

        List<ActivityOfferingInfo> aos = new ArrayList<ActivityOfferingInfo>();
        for(char c='A';c<='Z';c++){
            ActivityOfferingInfo a = new ActivityOfferingInfo();
            a.setActivityCode(String.valueOf(c));

            aos.add(a);
        }
        code = offeringCodeGenerator.generateActivityOfferingCode(courseOfferingCode,aos);
        assertEquals("AA",code);

        aos.remove(3);

        code = offeringCodeGenerator.generateActivityOfferingCode(courseOfferingCode, aos);
        assertEquals("D",code);

    }

    @Test
    public void testGetNextCode(){

        CourseOfferingCodeGeneratorDBImpl dbImpl = (CourseOfferingCodeGeneratorDBImpl)offeringCodeGenerator;

        assertEquals("A",dbImpl.getNextCode(""));
        assertEquals("E",dbImpl.getNextCode("D"));
        assertEquals("AA",dbImpl.getNextCode("Z"));
        assertEquals("AF",dbImpl.getNextCode("AE"));
        assertEquals("BA",dbImpl.getNextCode("AZ"));
        assertEquals("BF",dbImpl.getNextCode("BE"));
        assertEquals("ZB",dbImpl.getNextCode("ZA"));
        assertEquals("AAA",dbImpl.getNextCode("ZZ"));
        assertEquals("AAF",dbImpl.getNextCode("AAE"));
        assertEquals("ABA",dbImpl.getNextCode("AAZ"));
        assertEquals("ABG",dbImpl.getNextCode("ABF"));
        assertEquals("AAAA",dbImpl.getNextCode("ZZZ"));
        assertEquals("AAEB",dbImpl.getNextCode("AAEA"));
        assertEquals("AAZB",dbImpl.getNextCode("AAZA"));
        assertEquals("ABAA",dbImpl.getNextCode("AAZZ"));
        assertEquals("AZAC",dbImpl.getNextCode("AZAB"));
        assertEquals("ZZZD",dbImpl.getNextCode("ZZZC"));
        assertEquals("AAAAA",dbImpl.getNextCode("ZZZZ"));
    }

    @Test
    public void testGenerateCourseOfferingInternalCode(){

        String code;
        code = offeringCodeGenerator.generateCourseOfferingInternalCode(new ArrayList<CourseOfferingInfo>());
        assertEquals("A",code);

        List<CourseOfferingInfo> cos = new ArrayList<CourseOfferingInfo>();
        for(char c='A';c<='Z';c++){
            CourseOfferingInfo a = new CourseOfferingInfo();
            a.setCourseNumberSuffix(String.valueOf(c));

            cos.add(a);
        }
        code = offeringCodeGenerator.generateCourseOfferingInternalCode(cos);
        assertEquals("AA",code);

        cos.remove(3);

        code = offeringCodeGenerator.generateCourseOfferingInternalCode(cos);
        assertEquals("D",code);
    }

    protected List<ActivityOfferingInfo> _getBaseAOList(){
        List<ActivityOfferingInfo> aoList = new ArrayList<ActivityOfferingInfo>();

        String courseOfferingCode = "ENGL102";

        ActivityOfferingInfo ao1 = new ActivityOfferingInfo();
        ao1.setActivityCode("A");
        ao1.setCourseOfferingCode(courseOfferingCode);
        aoList.add(ao1);

        ActivityOfferingInfo ao2 = new ActivityOfferingInfo();
        ao2.setActivityCode("B");
        ao2.setCourseOfferingCode(courseOfferingCode);
        aoList.add(ao2);

        ActivityOfferingInfo ao3 = new ActivityOfferingInfo();
        ao3.setActivityCode("C");
        ao3.setCourseOfferingCode(courseOfferingCode);
        aoList.add(ao3);


        return aoList;
    }


    @Test
    public void testGenerateActivityOfferingCode(){

        String courseOfferingCode = "ENGL103";
        List<ActivityOfferingInfo> aoList = _getBaseAOList();

        String nextCode = offeringCodeGenerator.generateActivityOfferingCode(courseOfferingCode,aoList);

        // the list passed in above is A,B,C so the next should be D
        assertTrue("D".equals(nextCode));

        // Lets add a gap
        ActivityOfferingInfo ao1 = new ActivityOfferingInfo();
        ao1.setActivityCode("E");
        ao1.setCourseOfferingCode("ENGL101");
        aoList.add(ao1);

        nextCode = offeringCodeGenerator.generateActivityOfferingCode(courseOfferingCode,aoList);

        // the list passed in above is A,B,C,D,F so we should fill in the gap with E
        assertTrue ("D".equals(nextCode));

    }


    /**
     *     This class is set to ignore right now, but should be set to @Test once our concurrency
     *     issue is solved.
     */
    @Test
    public void testGenerateActivityOfferingCodeMultiThread(){
        try{
            test("ENGL104", 6);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void test(final String courseOfferingCode, final int threadCount) throws InterruptedException, ExecutionException {


        Callable<String> task = new Callable<String>() {

            @Override

            public String call() {

                return offeringCodeGenerator.generateActivityOfferingCode(courseOfferingCode,new ArrayList<ActivityOfferingInfo>());

            }

        };

        List<Callable<String>> tasks = Collections.nCopies(threadCount, task);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        List<Future<String>> futures = executorService.invokeAll(tasks);

        List<String> resultList = new ArrayList<String>(futures.size());

        // Check for exceptions

        for (Future<String> future : futures) {

            // Throws an exception if an exception was thrown by the task.

            resultList.add(future.get());

        }

        // Validate the IDs

        assert (futures.size() ==  threadCount);

        List<String> expectedList = new ArrayList<String>(threadCount);

        String nextCode = "";
        for (long i = 1; i <= threadCount; i++) {
            nextCode = getNextCode(nextCode);
            expectedList.add(nextCode);

        }

        Collections.sort(resultList);

        System.out.println("Expected/Got: \n" + expectedList + "\n" + resultList);

        for(int i=0;i< resultList.size(); i++){
            assertTrue("\nWas expecting \n[" + expectedList.get(i) + "] but got \n[" + resultList.get(i) + "]\n" , expectedList.get(i).equals(resultList.get(i)));
        }

    }

    public String getNextCode(String source){
        if (StringUtils.isEmpty(source)){
            return "A";
        } else if (StringUtils.endsWithIgnoreCase(source,"Z")){
            return getNextCode(StringUtils.substringBeforeLast(source,"Z")) + "A";
        } else {
            char lastLetter = source.charAt(source.length()-1);
            return StringUtils.substringBeforeLast(source,""+lastLetter) + ++lastLetter;
        }
    }

}
