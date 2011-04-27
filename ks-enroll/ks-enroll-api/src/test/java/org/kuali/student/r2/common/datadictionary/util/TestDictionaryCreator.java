/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.datadictionary.util;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.common.dto.AmountInfo;
import org.kuali.student.r2.core.classI.atp.dto.AtpInfo;
import org.kuali.student.r2.core.classI.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.classI.atp.dto.MilestoneInfo;
import org.kuali.student.enrollment.classI.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.classI.lui.dto.LuiInfo;
import org.kuali.student.enrollment.classI.lui.dto.LuiLuiRelationInfo;
import org.kuali.student.enrollment.classII.academiccalendar.dto.AcademicCalendarInfo;
import org.kuali.student.enrollment.classII.academiccalendar.dto.CampusCalendarInfo;
import org.kuali.student.enrollment.classII.academiccalendar.dto.HolidayInfo;
import org.kuali.student.enrollment.classII.academiccalendar.dto.KeyDateInfo;
import org.kuali.student.enrollment.classII.academiccalendar.dto.RegistrationDateGroupInfo;
import org.kuali.student.enrollment.classII.academiccalendar.dto.TermInfo;
import org.kuali.student.r2.common.datadictionary.util.DictionaryCreator;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ComparisonInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.CriteriaInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.SearchParamInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

/**
 *
 * @author nwright
 */
public class TestDictionaryCreator {

    public TestDictionaryCreator() {
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

    /**
     * Test of execute method, of class DictionaryCreator.
     */
    @Test
    public void testExecute() {
        System.out.println("execute");
        try{
        new DictionaryCreator().execute(LuiPersonRelationInfo.class);
        new DictionaryCreator().execute(LuiInfo.class);
        new DictionaryCreator().execute(LuiLuiRelationInfo.class);
        new DictionaryCreator().execute(AtpInfo.class);
        new DictionaryCreator().execute(MilestoneInfo.class);
        new DictionaryCreator().execute(AtpMilestoneRelationInfo.class);
        new DictionaryCreator().execute(AcademicCalendarInfo.class);
        new DictionaryCreator().execute(CampusCalendarInfo.class);
        new DictionaryCreator().execute(RegistrationDateGroupInfo.class);
        new DictionaryCreator().execute(HolidayInfo.class);
        new DictionaryCreator().execute(KeyDateInfo.class);
        new DictionaryCreator().execute(TermInfo.class);
        // r2 common
        new DictionaryCreator().execute(AttributeInfo.class);
        new DictionaryCreator().execute(ComparisonInfo.class);
        new DictionaryCreator().execute(ContextInfo.class);
        new DictionaryCreator().execute(CriteriaInfo.class);
        new DictionaryCreator().execute(MetaInfo.class);
        new DictionaryCreator().execute(RichTextInfo.class);
        new DictionaryCreator().execute(SearchParamInfo.class);
        new DictionaryCreator().execute(StateInfo.class);
        new DictionaryCreator().execute(StatusInfo.class);
        new DictionaryCreator().execute(TimeAmountInfo.class);
        new DictionaryCreator().execute(TypeInfo.class);
        new DictionaryCreator().execute(TypeTypeRelationInfo.class);
        new DictionaryCreator().execute(ValidationResultInfo.class);
        // r1 common
        new DictionaryCreator().execute(AmountInfo.class);               
             
        }catch(Exception ex){
        	fail(ex.getMessage());
        }
              



    }
}
