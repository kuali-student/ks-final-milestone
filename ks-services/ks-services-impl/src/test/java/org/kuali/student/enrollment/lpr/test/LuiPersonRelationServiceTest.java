package org.kuali.student.enrollment.lpr.test;

import junit.framework.Assert;
import org.junit.Test;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Igor
 */
public class LuiPersonRelationServiceTest {

    private LuiPersonRelationService personRelationService;


    @Test
    public void testService() {
        ApplicationContext appContext =
                new ClassPathXmlApplicationContext(new String[]{"dao.xml", "services.xml", "test.xml"});
        personRelationService = (LuiPersonRelationService) appContext.getBean("luiPersonRelationService");
    }
}
