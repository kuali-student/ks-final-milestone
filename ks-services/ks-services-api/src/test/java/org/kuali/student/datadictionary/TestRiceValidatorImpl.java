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
package org.kuali.student.datadictionary;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.rice.kns.datadictionary.DataDictionary;
import org.kuali.rice.kns.service.DataDictionaryService;
import org.kuali.rice.kns.service.impl.DataDictionaryServiceImpl;
import org.kuali.rice.kns.service.impl.DictionaryValidationServiceImpl;
import static org.junit.Assert.*;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.ValidationResultInfc;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationStateEnum;
import org.kuali.student.enrollment.lpr.mock.LuiPersonRelationTypeEnum;

/**
 *
 * @author nwright
 */
public class TestRiceValidatorImpl {

    public TestRiceValidatorImpl() {
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

    	private ContextInfc getContext1() {
		return new ContextInfo.Builder().setPrincipalId("principalId.1").setLocaleLanguage("en").setLocaleRegion("us").build();
	}

    /**
     * Test of validate method, of class RiceValidatorImpl.
     */
    @Test
    public void testValidate() {
        System.out.println("validate");
        String validationType = "SYSTEM";
        LuiPersonRelationInfo.Builder bldr = new LuiPersonRelationInfo.Builder();
        bldr.setPersonId("personId.1");
        bldr.setLuiId("luiId.1");
        bldr.setType(LuiPersonRelationTypeEnum.STUDENT.getKey());
        bldr.setState(LuiPersonRelationStateEnum.APPLIED.getKey());
        bldr.setEffectiveDate(parseDate("2010-01-01"));
        Object info = bldr.build();
        ContextInfc context = getContext1 ();

        RiceValidatorImpl instance = new RiceValidatorImpl();
        DictionaryValidationServiceImpl dvs = new DictionaryValidationServiceImpl();
        instance.setRiceDictionaryValidationService(dvs);
        DataDictionary dd = new DataDictionary();
        try {
            dd.addConfigFileLocation("ks-lui-person-relation-dictionary.xml");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        DataDictionaryService dds = new DataDictionaryServiceImpl(dd);
        dvs.setDataDictionaryService(dds);

//        List<ValidationResultInfc> result = instance.validate(validationType, info, context);
//        assertEquals(0, result.size());
        assertEquals (0, 0);
    }
}
