/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
package org.kuali.student.enrollment.registration.client.service;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.registration.client.service.impl.CourseRegistrationCartClientServiceImpl;
import org.kuali.student.enrollment.registration.client.service.impl.util.CourseRegistrationAndScheduleOfClassesUtil;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * This class provides unit tests for {@link CourseRegistrationCartClientServiceImpl} REST service. The
 * REST service is deployed to a standalone CXF-based server which hosts the REST service
 * end point (using JAX-RS) with embedded jetty. On the client side, BDD-style testing and validation
 * is utilized by using REST-assured. Mocking of injected services is provided by mockito. Mocking of
 * static methods is provided by PowerMock.
 *
 * @author Kuali Student Team
 */
@RunWith(PowerMockRunner.class)
// PowerMock annotation used for mocking static methods in CourseRegistrationAndScheduleOfClassesUtil
@PrepareForTest(CourseRegistrationAndScheduleOfClassesUtil.class)
// avoid org.apache.http.conn.ssl.SSLInitializationException
@PowerMockIgnore("org.apache.http.conn.ssl.*")
public class CourseRegistrationClientServiceImplTest {
    private static final int PORT = 9999;
    private static final String ENDPOINT_ADDRESS = "http://localhost:" + PORT + "/";
    private static final String KUALI_ATP_2012_FALL = "kuali.atp.2012Fall";
    private static Server server;
    private static RegistrationRequestInfo expectedRegistrationRequestInfo;

    /**
     * Creates a JAX-RS server end point using CXF (with embedded Jetty)
     */
    @BeforeClass
    public static void initialize() throws Exception {
        CourseRegistrationCartClientServiceImpl serviceImpl = new CourseRegistrationCartClientServiceImpl();

        // all AtpService methods are stubbed
        serviceImpl.setAtpService(mock(AtpService.class));

        expectedRegistrationRequestInfo = new RegistrationRequestInfo();
        expectedRegistrationRequestInfo.setTermId(KUALI_ATP_2012_FALL);
        expectedRegistrationRequestInfo.setRegistrationRequestItems(new ArrayList<RegistrationRequestItemInfo>());
        expectedRegistrationRequestInfo.setId("76d916e7-2373-46cc-9c78-b15a69223e49");

        CourseRegistrationService mockCourseRegistrationService = mock(CourseRegistrationService.class);
        // any call to createRegistrationRequest() should return the expected value
        when(mockCourseRegistrationService.createRegistrationRequest(
                anyString(), any(RegistrationRequestInfo.class), any(ContextInfo.class))).
                thenReturn(expectedRegistrationRequestInfo);
        serviceImpl.setCourseRegistrationService(mockCourseRegistrationService);

        // bean to help easily create JAX-RS server end points
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();

        // sets one or more root resource classes
        sf.setResourceClasses(CourseRegistrationCartClientServiceImpl.class);

        // sets the provider managing the life-cycle of the given resource class
        sf.setResourceProvider(CourseRegistrationCartClientServiceImpl.class,
                new SingletonResourceProvider(serviceImpl));

        // custom JAX-RS providers
        List<Object> providers = new ArrayList<>();
        providers.add(new JacksonJsonProvider());
        sf.setProviders(providers);
        sf.setAddress(ENDPOINT_ADDRESS);
        server = sf.create();
    }

    @AfterClass
    public static void destroy() throws Exception {
        server.stop();
        server.destroy();
    }

    @Test
    public void searchForCartIsSuccessful() throws Exception {
        SearchService mockSearchService = mock(SearchService.class);
        // any call to search() should return a new SearchResultInfo
        when(mockSearchService.search(any(SearchRequestInfo.class), any(ContextInfo.class))).thenReturn(new SearchResultInfo());

        // enable static mocking for all methods in this class
        mockStatic(CourseRegistrationAndScheduleOfClassesUtil.class);
        // any call to getSearchService() should return mockSearchService
        when(CourseRegistrationAndScheduleOfClassesUtil.getSearchService()).thenReturn(mockSearchService);

        // start building the request
        given().
                port(PORT).
                queryParam("termId", KUALI_ATP_2012_FALL).
        // as a GET request
        when().
                get("/cart").
        // validate the response
        then().
                statusCode(200).and().
                contentType(JSON).and().
                body("termId", is(expectedRegistrationRequestInfo.getTermId()));
    }
}
