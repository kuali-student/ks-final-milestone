/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.lum.lu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

/**
 *
 * @author nwright
 */
public class TestCluServiceMockImpl {

    public TestCluServiceMockImpl() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    private CluService cluService = null;
    private ContextInfo contextInfo = null;

    protected void loadCluService () {
      if (cluService == null) {          
        cluService = new CluServiceMockImpl();
      }
    }
    
    @Before
    public void setUp() {
        contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("TESTUSER");
        contextInfo.setCurrentDate(new Date());
        
        // make sure it is loaded
        loadCluService ();
        
        CluDataLoader cluDataLoader = new CluDataLoader();
        cluDataLoader.setCluService(cluService);
        cluDataLoader.setContextInfo(contextInfo);
        cluDataLoader.load();
        
        CluSetDataLoader cluSetDataLoader = new CluSetDataLoader();
        cluSetDataLoader.setCluService(cluService);
        cluSetDataLoader.setContextInfo(contextInfo);
        cluSetDataLoader.load();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCluSet() throws Exception {
        System.out.println("testCluSet");
        CluSetInfo orig = new CluSetInfo();
        orig.setTypeKey(CluServiceConstants.CLUSET_TYPE_CREDIT_COURSE);
        orig.setStateKey("Active");
        orig.setName("MyCluSet");
        orig.setEffectiveDate(new Date());
        orig.setIsReferenceable(Boolean.TRUE);
        orig.setIsReusable(Boolean.FALSE);
        List<String> cluIds = new ArrayList<String>();
        CluInfo cluInfo1 = this.cluService.getClu("COURSE1", contextInfo);
        cluIds.add(cluInfo1.getVersion().getVersionIndId());
        CluInfo cluInfo2 = this.cluService.getClu("COURSE2", contextInfo);
        cluIds.add(cluInfo2.getVersion().getVersionIndId());
        orig.setCluIds(cluIds);
        CluSetInfo info = this.cluService.createCluSet(orig.getTypeKey(), orig, contextInfo);
        assertEquals(orig.getTypeKey(), info.getTypeKey());
        assertEquals(orig.getStateKey(), info.getStateKey());
    }
}