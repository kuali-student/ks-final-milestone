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

package org.kuali.student.r1.core.personsearch.service.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.common.test.spring.AbstractServiceTest;
import org.kuali.student.common.test.spring.Client;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

/**
 * Simple text case to test that the PersonSearchService is available as a web service.
 * To test it fully requires that the KIM Identity service in rice be available
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class TestPersonSearchServiceImpl extends AbstractServiceTest {
    @Client(value = "org.kuali.student.r1.core.personsearch.service.impl.PersonSearchServiceImpl")
    public SearchService client;

    private ContextInfo context = new ContextInfo();

    @Test
    public void testClient() {
        assertNotNull(client);
    }

    @Test
    public void testCall() throws MissingParameterException, PermissionDeniedException, OperationFailedException {
        SearchResultInfo result = client.search(new SearchRequestInfo(), context);
        assertNotNull(result);
    }


}
