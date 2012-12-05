package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingCodeGenerator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: swedev
 * Date: 12/5/12
 * Time: 9:23 AM
 *
 * This file is designed to test CourseOfferingCodeGeneratorImpl.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-with-class2-mock-context.xml"})
public class CourseOfferingCodeGeneratorImplTest {

    private static final Logger log = Logger.getLogger(CourseOfferingCodeGeneratorImplTest.class);

    @Resource
    CourseOfferingCodeGenerator codeGenerator;


    protected List<ActivityOfferingInfo> _getBaseAOList(){
        List<ActivityOfferingInfo> aoList = new ArrayList<ActivityOfferingInfo>();

        ActivityOfferingInfo ao1 = new ActivityOfferingInfo();
        ao1.setActivityCode("A");
        aoList.add(ao1);

        ActivityOfferingInfo ao2 = new ActivityOfferingInfo();
        ao2.setActivityCode("B");
        aoList.add(ao2);

        ActivityOfferingInfo ao3 = new ActivityOfferingInfo();
        ao3.setActivityCode("C");
        aoList.add(ao3);


        return aoList;
    }


    @Test
    public void testGenerateActivityOfferingCode(){

        String nextCode = codeGenerator.generateActivityOfferingCode(_getBaseAOList());

        assert ("D".equals(nextCode));

    }

}
