package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.springframework.test.context.ContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:co-test-with-fewer-mocks-context.xml"})
public class TestCourseOfferingServiceImplWithFewerMocks extends TestCourseOfferingServiceImplWithMocks {
}
