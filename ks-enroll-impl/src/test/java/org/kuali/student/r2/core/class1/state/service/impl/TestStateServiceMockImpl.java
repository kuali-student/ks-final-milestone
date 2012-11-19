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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:state-mock-service-test-context.xml"})
public class TestStateServiceMockImpl {
    @Resource(name = "stateService")
    protected StateService stateService;

    public static String principalId = "123";
   public ContextInfo callContext = null;

   @Before
   public void setUp() {
       callContext = new ContextInfo();
       callContext.setPrincipalId(principalId);
   }

   @Test
   public void testCRUDState() throws Exception {
        // create
        LifecycleInfo origLife = new LifecycleInfo();
        RichTextInfo rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted</b> lifecycle for testing purposes");
        rti.setPlain("Plain lifecycle for testing purposes");
        origLife.setDescr(rti);
        origLife.setKey(AtpServiceConstants.ATP_LIFECYCLE_KEY);
        origLife.setName("Testing");
        origLife.setRefObjectUri(AtpServiceConstants.REF_OBJECT_URI_ATP);
        AttributeInfo attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        origLife.getAttributes().add(attr);
        LifecycleInfo infoLife = stateService.createLifecycle(origLife.getKey(), origLife, callContext);
        assertNotNull(infoLife);
        assertEquals(origLife.getKey(), infoLife.getKey());
        assertEquals(origLife.getName(), infoLife.getName());
        assertEquals(origLife.getDescr().getPlain(), infoLife.getDescr().getPlain());
        assertEquals(origLife.getDescr().getFormatted(), infoLife.getDescr().getFormatted());
        assertEquals(origLife.getRefObjectUri(), infoLife.getRefObjectUri());
        assertEquals(origLife.getAttributes().size(), infoLife.getAttributes().size());
        assertEquals(origLife.getAttributes().get(0).getKey(), infoLife.getAttributes().get(0).getKey());
        assertEquals(origLife.getAttributes().get(0).getValue(), infoLife.getAttributes().get(0).getValue());
        assertNotNull(infoLife.getMeta());
        assertNotNull(infoLife.getMeta().getCreateId());
        assertNotNull(infoLife.getMeta().getCreateTime());
        assertNotNull(infoLife.getMeta().getUpdateId());
        assertNotNull(infoLife.getMeta().getUpdateTime());
        assertNotNull(infoLife.getMeta().getVersionInd());

        // get
        origLife = infoLife;
        infoLife = stateService.getLifecycle(origLife.getKey(), callContext);
        assertNotNull(infoLife);
        assertEquals(origLife.getKey(), infoLife.getKey());
        assertEquals(origLife.getName(), infoLife.getName());
        assertEquals(origLife.getDescr().getPlain(), infoLife.getDescr().getPlain());
        assertEquals(origLife.getDescr().getFormatted(), infoLife.getDescr().getFormatted());
        assertEquals(origLife.getRefObjectUri(), infoLife.getRefObjectUri());
        assertEquals(origLife.getAttributes().size(), infoLife.getAttributes().size());
        assertEquals(origLife.getAttributes().get(0).getKey(), infoLife.getAttributes().get(0).getKey());
        assertEquals(origLife.getAttributes().get(0).getValue(), infoLife.getAttributes().get(0).getValue());
        assertNotNull(infoLife.getMeta());
        assertNotNull(infoLife.getMeta().getCreateId());
        assertNotNull(infoLife.getMeta().getCreateTime());
        assertNotNull(infoLife.getMeta().getUpdateId());
        assertNotNull(infoLife.getMeta().getUpdateTime());
        assertNotNull(infoLife.getMeta().getVersionInd());

        // update
        origLife = infoLife;
        origLife.setName("Different Lifecycle");
        infoLife = stateService.updateLifecycle(origLife.getKey(), origLife, callContext);
        assertNotNull(infoLife);
        assertEquals(origLife.getKey(), infoLife.getKey());
        assertEquals(origLife.getName(), infoLife.getName());
        assertEquals(origLife.getDescr().getPlain(), infoLife.getDescr().getPlain());
        assertEquals(origLife.getDescr().getFormatted(), infoLife.getDescr().getFormatted());
        assertEquals(origLife.getRefObjectUri(), infoLife.getRefObjectUri());
        assertEquals(origLife.getAttributes().size(), infoLife.getAttributes().size());
        assertEquals(origLife.getAttributes().get(0).getKey(), infoLife.getAttributes().get(0).getKey());
        assertEquals(origLife.getAttributes().get(0).getValue(), infoLife.getAttributes().get(0).getValue());
        assertNotNull(infoLife.getMeta());
        assertNotNull(infoLife.getMeta().getCreateId());
        assertNotNull(infoLife.getMeta().getCreateTime());
        assertNotNull(infoLife.getMeta().getUpdateId());
        assertNotNull(infoLife.getMeta().getUpdateTime());
        assertNotSame(origLife.getMeta().getVersionInd(), infoLife.getMeta().getVersionInd());

        LifecycleInfo atpLife = infoLife;

        // create state
        StateInfo orig = new StateInfo();
        orig.setKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        orig.setLifecycleKey(atpLife.getKey());
        rti = new RichTextInfo();
        rti.setFormatted("<b>Formatted again</b> state for testing purposes");
        rti.setPlain("Plain state again for testing purposes");
        orig.setDescr(rti);
        orig.setName("Testing state");
        Date effDate = new Date();
        orig.setEffectiveDate(effDate);
        Calendar cal = Calendar.getInstance();
        cal.set(2022, 8, 23);
        orig.setExpirationDate(cal.getTime());
        attr = new AttributeInfo();
        attr.setKey("attribute.key");
        attr.setValue("attribute value");
        orig.getAttributes().add(attr);
        StateInfo info = stateService.createState(orig.getLifecycleKey(), orig.getKey(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getKey(), info.getKey());
        assertEquals(orig.getLifecycleKey(), info.getLifecycleKey());
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        assertEquals(orig.getAttributes().size(), info.getAttributes().size());
        assertEquals(orig.getAttributes().get(0).getKey(), info.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), info.getAttributes().get(0).getValue());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());
        assertNotNull(info.getMeta().getVersionInd());

        // get
        orig = info;
        info = stateService.getState(orig.getKey(), callContext);
        assertNotNull(info);
        assertEquals(orig.getKey(), info.getKey());
        assertEquals(orig.getLifecycleKey(), info.getLifecycleKey());
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getDescr().getPlain(), info.getDescr().getPlain());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        assertEquals(orig.getAttributes().size(), info.getAttributes().size());
        assertEquals(orig.getAttributes().get(0).getKey(), info.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), info.getAttributes().get(0).getValue());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());

        // update
        orig = info;
        orig.setName("DifferentName");
        info = stateService.updateState(orig.getKey(), orig, callContext);
        assertNotNull(info);
        assertEquals(orig.getKey(), info.getKey());
        assertEquals(orig.getLifecycleKey(), info.getLifecycleKey());
        assertEquals(orig.getName(), info.getName());
        assertEquals(orig.getEffectiveDate(), info.getEffectiveDate());
        assertEquals(orig.getExpirationDate(), info.getExpirationDate());
        assertEquals(orig.getAttributes().size(), info.getAttributes().size());
        assertEquals(orig.getAttributes().get(0).getKey(), info.getAttributes().get(0).getKey());
        assertEquals(orig.getAttributes().get(0).getValue(), info.getAttributes().get(0).getValue());
        assertNotNull(info.getMeta());
        assertNotNull(info.getMeta().getCreateId());
        assertNotNull(info.getMeta().getCreateTime());
        assertNotNull(info.getMeta().getUpdateId());
        assertNotNull(info.getMeta().getUpdateTime());
        assertNotNull(info.getMeta().getVersionInd());

        StateInfo atpDraftState = info;

        // delete state
        StatusInfo result = stateService.deleteState(atpDraftState.getKey(), callContext);
        assertEquals(true, result.getIsSuccess());
        try {
            info = stateService.getState(atpDraftState.getKey(), callContext);
            fail("should have thrown dne exception");
        } catch (DoesNotExistException e) {
            // expected
        }
        // delete life
        result = stateService.deleteLifecycle(atpLife.getKey(), callContext);
        assertEquals(true, result.getIsSuccess());
        try {
            infoLife = stateService.getLifecycle(atpLife.getKey(), callContext);
            fail("should have thrown dne exception");
        } catch (DoesNotExistException e) {
            // expected
        }
    }
}
