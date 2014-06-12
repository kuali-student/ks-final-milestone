/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.lum.lu.service.decorator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lu-cache-decorator-test-context.xml"})
public class TestCluServiceCacheDecorator {

    @Resource(name = "luiServiceCacheDecorator")
    private CluService cluService;

    private ContextInfo contextInfo;

    @Before
    public void setup(){
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("123");
    }

    @Test
    public void testCluCache() throws Exception {
        CluInfo clu = this.createClu();
        cluService.createClu("test.cache.type", clu, contextInfo);

        CluInfo result = cluService.getClu("clu1", contextInfo);
        assertNotNull(result);
        assertEquals("description", result.getDescr().getPlain());

        result.getDescr().setPlain("updated");
        cluService.updateClu(result.getId(), result, contextInfo);

        CluInfo updated = cluService.getClu("clu1", contextInfo);
        assertEquals("updated", updated.getDescr().getPlain());

        cluService.deleteClu("clu1", contextInfo);
        try{
            cluService.getClu("clu1", contextInfo);
            fail("clu1 should not exist.");
        } catch (DoesNotExistException dne){
        }
    }

    private CluInfo createClu(){
        CluInfo clu = new CluInfo();
        clu.setId("clu1");
        clu.setTypeKey("test.cache.type");
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("description");
        clu.setDescr(descr);
        return clu;
    }

    @Test
    public void testCluSetCache() throws Exception {
        CluSetInfo cluSet = this.createCluSet();
        cluService.createCluSet("test.cache.type", cluSet, contextInfo);

        CluSetInfo result = cluService.getCluSet("cluset1", contextInfo);
        assertNotNull(result);
        assertEquals("description", result.getDescr().getPlain());

        result.getDescr().setPlain("updated");
        cluService.updateCluSet(result.getId(), result, contextInfo);

        CluSetInfo updated = cluService.getCluSet("cluset1", contextInfo);
        assertEquals("updated", updated.getDescr().getPlain());

        cluService.deleteCluSet("cluset1", contextInfo);
        try{
            cluService.getCluSet("cluset1", contextInfo);
            fail("cluset1 should not exist.");
        } catch (DoesNotExistException dne){
        }
    }

    private CluSetInfo createCluSet(){
        CluSetInfo cluSet = new CluSetInfo();
        cluSet.setId("cluset1");
        cluSet.setTypeKey("test.cache.type");
        RichTextInfo descr = new RichTextInfo();
        descr.setPlain("description");
        cluSet.setDescr(descr);
        return cluSet;
    }
}
