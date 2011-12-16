package org.kuali.student.core.atp.service.conformance.test;


import org.kuali.student.r2.common.dto.StatusInfo;
import java.text.SimpleDateFormat;
import java.util.List;
import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.classI.atp.mock.AtpServiceMockImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;

public class TestATPServiceConformance {

    private AtpService service = new AtpServiceMockImpl ();

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

    @Test
    public void testATPCrud() throws Exception {
        ContextInfo context = new ContextInfo ();
        context.setPrincipalId("principal1");

        AtpInfo atp = new AtpInfo ();
        atp.setKey(AtpServiceConstants.ATP_FALL_TYPE_KEY + "_2011");
        atp.setTypeKey(AtpServiceConstants.ATP_FALL_TYPE_KEY);
        atp.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        atp.setStartDate(new SimpleDateFormat ("yyyy-MM-dd").parse("2011-09-01"));
        atp.setEndDate(new SimpleDateFormat ("yyyy-MM-dd").parse("2011-12-31"));
        atp.setName("Fall 2010");
        atp.setDescr(new RichTextHelper ().fromPlain("plain for fall 2010"));
 

        // Test create
        AtpInfo createdAtp = service.createAtp(atp.getKey(), atp, context);

        // Test read ATP
        AtpInfo  readAtp = service.getAtp(atp.getKey(), context);
            
        MilestoneInfo milestone = new MilestoneInfo ();
        milestone.setName("registration opens");
        milestone.setTypeKey(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
        milestone.setStateKey (AtpServiceConstants.MILESTONE_OFFICIAL_STATE_KEY);
        milestone.setStartDate(new SimpleDateFormat ("yyyy-MM-dd").parse("2011-09-01"));
        milestone.setEndDate(new SimpleDateFormat ("yyyy-MM-dd").parse("2011-12-31"));
        milestone.setKey(atp.getKey() + milestone.getTypeKey());
        MilestoneInfo createdMilestone = service.createMilestone(milestone, context);
        
        MilestoneInfo readMilestone = service.getMilestone(createdMilestone.getKey(), context);
        
        StatusInfo info = service.addMilestoneToAtp(readMilestone.getKey(), readAtp.getKey(), context);
        assertTrue (info.getIsSuccess());  
        
        List<MilestoneInfo> list = service.getMilestonesForAtp(createdAtp.getKey(), context);
        assertEquals (1, list.size());
        
        list = service.getMilestonesByTypeForAtp(createdAtp.getKey(), AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY, context);
        assertEquals (1, list.size());        
        
    }

}
