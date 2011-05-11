/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.classI.lpr.service.conformance.tests;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.r2.common.dto.ComparisonInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CriteriaInfo;
import org.kuali.student.r2.common.infc.Comparison;
import org.kuali.student.r2.common.util.constants.LuiPersonRelationServiceConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author nwright
 */

public class TestLuiPersonRelationServiceSearchConformance {

    public TestLuiPersonRelationServiceSearchConformance() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    private LuiPersonRelationService service = null;

    public LuiPersonRelationService getService() {
        if (service == null) {
            ApplicationContext appContext =
                    new ClassPathXmlApplicationContext(new String[]{"ks-mock-lpr-service-configuration.xml"});
            service = (LuiPersonRelationService) appContext.getBean("mockLprPersistenceService");
        }
        return service;
    }

    public void setService(LuiPersonRelationService service) {
        this.service = service;
    }

    private ContextInfo getContext1() {
        return ContextInfo.getInstance("principalId.1", "en", "us");
    }

    private ContextInfo getContext2() {
        return ContextInfo.getInstance("principalId.2", "fr", "ca");
    }

    private Date parseDate(String str) {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = df.parse(str);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(str, ex);
        }
        return date;
    }

    private void loadData() throws Exception {
        String personId = "personId1";
        List<String> luiIdList = new ArrayList<String>();
        luiIdList.add("luiId1");
        luiIdList.add("luiId2");
        luiIdList.add("luiId3");
        luiIdList.add("luiId4");
        luiIdList.add("luiId5");
        luiIdList.add("luiId6");
        String relationState = LuiPersonRelationServiceConstants.APPLIED_STATE_KEY;
        String luiPersonRelationType = LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY;
        LuiPersonRelationInfo lpr = LuiPersonRelationInfo.newInstance();
        lpr.setEffectiveDate(parseDate("2010-01-01"));
        ContextInfo context = getContext1();
        getService().createBulkRelationshipsForPerson(personId,
                luiIdList,
                relationState,
                luiPersonRelationType,
                lpr, context);
    }

    private static final int ALL_COUNT = 6;

    /**
     * Test of createBulkRelationshipsForPerson method,
     */
    @Test
    public void testSearch() throws Exception {
        this.loadData();
        ContextInfo context = getContext1();
        CriteriaInfo criteria = null;
        ComparisonInfo comparison = null;
        List<Comparison> comparisons = null;
        List<String> lprIds = null;

        criteria = CriteriaInfo.newIntance();
        comparisons = new ArrayList<Comparison>();
        criteria.setComparisons(comparisons);
        lprIds = getService().searchForLuiPersonRelationIds(criteria, context);
        assertEquals(ALL_COUNT, lprIds.size());

        // max results
        criteria.setMaxResults(3);
        lprIds = getService().searchForLuiPersonRelationIds(criteria, context);
        assertEquals(3, lprIds.size());

        // max results
        criteria.setMaxResults(100);
        lprIds = getService().searchForLuiPersonRelationIds(criteria, context);
        assertEquals(ALL_COUNT, lprIds.size());

        // all should have this type
//        criteria = new CriteriaInfo.Builder();
//        comparisons = new ArrayList<Comparison>();
//        comparison = new ComparisonInfo.Builder();
//        comparison.setFieldKey("typeKey");
//        comparison.setOperator("==");
//        comparison.setValue(LuiPersonRelationServiceConstants.REGISTRANT_TYPE_KEY);
//        comparisons.add(comparison);
//        criteria.setComparisons(comparisons);
//        lprIds = getService().searchForLuiPersonRelationIds(criteria.build(), context);
//        assertEquals(ALL_COUNT, lprIds.size());

//        // one should have this type
//        criteria = new CriteriaInfo.Builder();
//        comparisons = new ArrayList<Comparison>();
//        criteria.setComparisons(comparisons);
//        comparison = new ComparisonInfo.Builder();
//        comparisons.add(comparison);
//        comparison.setFieldKey("luiId");
//        comparison.setOperator("=");
//        comparison.setValue("luiId2");
//        lprIds = getService().searchForLuiPersonRelationIds(criteria.build(), context);
//        assertEquals(1, lprIds.size());
    }
}
