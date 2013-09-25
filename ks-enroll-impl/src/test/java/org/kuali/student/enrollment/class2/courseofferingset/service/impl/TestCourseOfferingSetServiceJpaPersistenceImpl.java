/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseofferingset.service.impl;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

/**
 * Tests the jpa persistence impl
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:soc-jpa-persistence-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestCourseOfferingSetServiceJpaPersistenceImpl extends TestCourseOfferingSetServiceMockImpl{

    @Resource(name = "stateService")
    protected StateService stateService;

    @Before
    public void setUp(){

        super.setUp();
        try{
            loadStateData();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void loadStateData()throws Exception{
        try {
            String lifecycleKey = createLifecycle(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_KEY, "kuali.soc.lifecycle", "Set of Courses State Lifecycle", CourseOfferingSetServiceConstants.REF_OBJECT_URI_SOC);
            createState(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY, lifecycleKey, "Final Edits", "Final Edits",true);
            createState(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY, lifecycleKey, "Open", "Open",false);
            createState(CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY, lifecycleKey, "Published", "Published",false);
            createState(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, lifecycleKey, "Locked", "Locked",false);
            createState(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, lifecycleKey, "Final Edits", "Final Edits",false);
        } catch( AlreadyExistsException aee ) { }
    }

    private String createLifecycle(String key, String name, String descr, String uri) throws Exception{
        LifecycleInfo origLife = new LifecycleInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b>" + descr);
        rti.setPlain(descr);
        origLife.setDescr(rti);
        origLife.setKey(key);
        origLife.setName(name);
        origLife.setRefObjectUri(uri);
        LifecycleInfo infoLife = stateService.createLifecycle(origLife.getKey(), origLife, callContext);
        return infoLife.getKey();
    }

    private void createState(String key, String lifecycleKey, String name, String descr, boolean isInitialState)throws Exception{
        StateInfo orig = new StateInfo();
        orig.setKey(key);
        orig.setLifecycleKey(lifecycleKey);
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted again</b> " + descr);
        rti.setPlain(descr);
        orig.setDescr(rti);
        orig.setName(name);
        Date effDate = new Date();
        orig.setEffectiveDate(effDate);
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 8, 23);
        orig.setExpirationDate(cal.getTime());
        orig.setIsInitialState(isInitialState);
        stateService.createState(orig.getLifecycleKey(), orig.getKey(), orig, callContext);
    }

}
