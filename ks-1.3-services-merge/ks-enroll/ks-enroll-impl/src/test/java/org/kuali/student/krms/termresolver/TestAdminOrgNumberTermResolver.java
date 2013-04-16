package org.kuali.student.krms.termresolver;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.impl.type.KrmsTypeResolver;
import org.kuali.student.common.util.krms.RulesExecutionConstants;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.krms.util.KSKRMSExecutionConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ks-krms-test-context.xml"})
@Ignore
public class TestAdminOrgNumberTermResolver {
	private KrmsTypeResolver typeResolver;

    public ContextInfo callContext = null;
    @Resource(name = "acadRecordService")
    private OrganizationService organizationService;
    private String studentID = "12020303";

    @Before
    public void setUp() {
        callContext = new ContextInfo();
    }


    @Test
    public void testOrgServiceNotNull() {
        assertNotNull(organizationService);
    }

    @Test
    public void testResolve() {
        AdminOrgNumberTermResolver termResolver = new AdminOrgNumberTermResolver();
        termResolver.setOrganizationService(organizationService);
        Map<String, Object> resolvedPrereqs = new HashMap<String, Object>();
        Map<String, String> parameters = new HashMap<String, String>();

        ContextInfo context = new ContextInfo();
        context.setLocale(new LocaleInfo());
        context.setPrincipalId("nina");

        resolvedPrereqs.put(RulesExecutionConstants.CONTEXT_INFO_TERM_NAME, context);

        parameters.put(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY, studentID);
        String personId = parameters.get(KSKRMSExecutionConstants.PERSON_ID_TERM_PROPERTY);

        List<String> orgRecords = termResolver.resolve(resolvedPrereqs, parameters);
        // TODO Do some assert statements on the expected returned list.
        assertNotNull(orgRecords);

    }



}

