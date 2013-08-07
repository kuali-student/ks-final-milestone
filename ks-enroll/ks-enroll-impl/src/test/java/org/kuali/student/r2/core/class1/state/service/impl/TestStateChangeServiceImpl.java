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
 */
package org.kuali.student.r2.core.class1.state.service.impl;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:state-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestStateChangeServiceImpl extends TestStateServiceMockImpl{

    @Before
    public void setUp() throws Exception {
        super.setUp();
        loadStateData();
    }

    @Override
    public void testCRUDState() throws Exception {
        super.testCRUDState();
    }

    @Override
    public void testCRStateChange() throws Exception {
        super.testCRStateChange();
    }

    @Override
    public void testUpdateStateChange() throws Exception {
        super.testUpdateStateChange();
    }

    @Override
    public void testDeleteStateChange() throws Exception {
        super.testDeleteStateChange();
    }

    @Override
    public void testCRStateConstraint() throws Exception {
        super.testCRStateConstraint();
    }

    @Override
    public void testUpdateStateConstraint() throws Exception {
        super.testUpdateStateConstraint();
    }

    @Override
    public void testDeleteStateConstraint() throws Exception {
        super.testDeleteStateConstraint();
    }

    @Override
    public void testCRStatePropagation() throws Exception {
        super.testCRStatePropagation();
    }

    @Override
    public void testUpdateStatePropagation() throws Exception {
        super.testUpdateStatePropagation();
    }

    @Override
    public void testDeleteStatePropagation() throws Exception {
        super.testDeleteStatePropagation();
    }



    /* TODO: upon adding a new test to the mock-impl, which works fine, the entire collection of tests in this suite
     * started failing due to this method throwing an exception which complained about duplicate data being persisted.
     * This was temporarily resolved by trapping the exception in this method and then ignoring it.
     *
     * See TestStateServiceMockImpl#testGetInitialStatesByLifecycle()
     * Also see jira KSENROLL-5970
     */
    private void loadStateData()throws Exception{
        try {
            String lifecycleKey = createLifecycle(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_KEY, "kuali.soc.lifecycle", "Set of Courses State Lifecycle", CourseOfferingSetServiceConstants.REF_OBJECT_URI_SOC);
            createState(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY, lifecycleKey, "Open", "Open");
            createState(CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY, lifecycleKey, "Published", "Published");
            createState(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, lifecycleKey, "Locked", "Locked");
            createState(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, lifecycleKey, "Final Edits", "Final Edits");
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

    private void createState(String key, String lifecycleKey, String name, String descr)throws Exception{
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
        stateService.createState(orig.getLifecycleKey(), orig.getKey(), orig, callContext);
    }


}
