package org.kuali.student.r2.core.class1.atp.service;

import java.util.GregorianCalendar;

import org.junit.Ignore;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Ignore
public class TestATPServiceConformance {

    private AtpService service = null;

    public AtpService getService() {
        if (service == null) {
            ApplicationContext appContext = new ClassPathXmlApplicationContext(new String[]{"classpath:testContext.xml"});
            service = (AtpService) appContext.getBean("atpServiceToTest");
        }
        return service;
    }

    public void setService(AtpService service) {
        this.service = service;
    }

    public void testATPCrud() throws Exception {
//        ContextInfo.Builder cbldr = new ContextInfo.Builder();
//        cbldr.principalId("principal1");

//        AtpInfo.Builder bldr = new AtpInfo.Builder();
//        bldr.setKey(AtpServiceConstants.ATP_FALL_TYPE_KEY + "_2010");
//        bldr.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
//        bldr.setStateKey("DRAFT");
//        bldr.setStartDate((new GregorianCalendar(2010, GregorianCalendar.AUGUST, 1).getTime()));
//        bldr.setEndDate((new GregorianCalendar(2010, GregorianCalendar.DECEMBER, 25).getTime()));
//        bldr.setName("Fall 2010");
//        RichTextInfo.Builder rbldr = new RichTextInfo.Builder();
//        rbldr.setFormatted("Formatted Fall 2010");
//        rbldr.setPlain("Plain Fall 2010");
//        bldr.setDescr(rbldr.build());
//        AtpInfo atp = bldr.build();
//
//        // Test create
//        AtpInfo createdAtp = service.createAtp(atp.getKey(), atp, cbldr.build());
//
//        // Test read ATP
//        AtpInfo  readAtp = service.getAtp(atp.getKey(), cbldr.build());
            
        
    }

    public void testMilestoneCrud() {

    }

    public void testAtpMilestoneRltnCrud() {

    }

    public void testAtpAtpRelationCrud() {

    }
}
