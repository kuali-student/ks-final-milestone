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
package org.kuali.student.r2.core.acal.service.impl;

import org.apache.commons.httpclient.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.TermCodeGenerator;
import org.kuali.student.r2.core.acal.service.impl.TermCodeGeneratorImpl;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test class for TermCodeGeneratorImpl
 *
 * @author Kuali Student Team
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:acal-test-context.xml"})
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class TestTermCodeGeneratorImpl {

    @Resource(name = "typeService" )
    private TypeService typeService;

    private static final String YEAR_ONLY_FORMAT_STRING = "yyyy";

    public static String principalId = "123";
    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        principalId = "123";
        callContext = new ContextInfo();
        callContext.setPrincipalId(principalId);
    }

    @Test
    public void testTermCodeGenerator() throws Exception {
        TypeInfo termType = typeService.getType(AtpServiceConstants.ATP_WINTER_TYPE_KEY, callContext);
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        AttributeInfo attrInfo = new AttributeInfo();
        attrInfo.setId("term_type_code_for_" + AtpServiceConstants.ATP_WINTER_TYPE_KEY);
        attrInfo.setKey(TypeServiceConstants.ATP_TERM_TYPE_CODE_ATTR);
        attrInfo.setValue("00");
        attributes.add(attrInfo);
        termType.setAttributes(attributes);
        typeService.updateType(termType.getKey(), termType, callContext);

        TermInfo term = new TermInfo();
        term.setStartDate(new Date());
        term.setTypeKey(AtpServiceConstants.ATP_WINTER_TYPE_KEY);

        TypeInfo type = typeService.getType(term.getTypeKey(), callContext);
        String typeCode = type.getAttributeValue(TypeServiceConstants.ATP_TERM_TYPE_CODE_ATTR);

        StringBuilder termCode = new StringBuilder(DateUtil.formatDate(term.getStartDate(), YEAR_ONLY_FORMAT_STRING));
        termCode.append(typeCode);

        String startYear = DateFormatters.DEFULT_YEAR_FORMATTER.format(new Date());
        assertEquals(startYear + "00", termCode.toString());
    }
}
