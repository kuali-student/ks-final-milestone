package org.kuali.student.core.ges.service.impl;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ges-test-with-map-context.xml"})
public class TestGesServiceImplConformanceMap extends TestGesServiceImplConformanceExtendedCrud {
}
