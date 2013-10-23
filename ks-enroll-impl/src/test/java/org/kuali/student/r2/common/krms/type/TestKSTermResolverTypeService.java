/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.common.krms.type;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.impl.type.KrmsTypeResolver;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:ks-krms-test-context.xml"})
@Ignore
public class TestKSTermResolverTypeService {
    private KrmsTypeResolver typeResolver;

    public ContextInfo callContext = null;

    @Before
    public void setUp() {
        callContext = new ContextInfo();
    }

    @Test
    public void testloadTermResolver() {

        KSTermResolverTypeService service = new KSTermResolverTypeService();
        TermSpecificationDefinition.Builder termSpec = TermSpecificationDefinition.Builder.create("1", "spec", "KS-SYS", "type");
        TermResolverDefinition.Builder termResolver = TermResolverDefinition.Builder.create("1", "KS-SYS", "name", "type", termSpec, null, null, null);
        TermResolver<?> term = service.loadTermResolver(termResolver.build());
    }


}
