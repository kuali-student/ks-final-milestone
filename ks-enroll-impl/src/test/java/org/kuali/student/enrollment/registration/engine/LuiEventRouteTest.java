package org.kuali.student.enrollment.registration.engine;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.ServiceStatus;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.DisableJmx;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.common.eventing.EventMessage;
import org.kuali.student.common.util.security.ContextUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

/**
 * Tests Lui event camel routes.
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:lui-event-route-test-context.xml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpoints("log:*")
@DisableJmx(false)
public class LuiEventRouteTest {

    @Resource
    protected CamelContext camelContext;

    @Resource
    protected CamelContext camelContextTwo;

    @EndpointInject(uri = "mock:a", context = "camelContext")
    protected MockEndpoint mockA;

    @EndpointInject(uri = "mock:b", context = "camelContext")
    protected MockEndpoint mockB;

    @EndpointInject(uri = "mock:c", context = "camelContextTwo")
    protected MockEndpoint mockC;

    @EndpointInject(uri = "mock:d", context = "camelContextTwo")
    protected MockEndpoint mockD;

    @Produce(uri = "direct:start", context = "camelContext")
    protected ProducerTemplate start;

    @Produce(uri = "direct:start2", context = "camelContextTwo")
    protected ProducerTemplate startTwo;

    /**
     * This test makes sure that the pattern matching works for our eventing route.
     *
     *
     * @throws Exception
     */
    @Test
    public void testPositive() throws Exception {
        assertEquals(ServiceStatus.Started, camelContext.getStatus());

        EventMessage wrongTypeMessage = getEventMessage("1", "UPDATE", "kuali.lui.type.course.NO.OFFERING");
        EventMessage updateMessage = getEventMessage("2", "UPDATE", "kuali.lui.type.course.offering");
        EventMessage createMessage = getEventMessage("3", "CREATE", "kuali.lui.type.course.offering");

        mockA.expectedBodiesReceived(updateMessage);
        mockB.expectedBodiesReceived(createMessage);

        mockA.expectedMessageCount(1);
        mockB.expectedMessageCount(1);

        start.sendBody(updateMessage);
        start.sendBody(wrongTypeMessage);
        start.sendBody(createMessage);

        MockEndpoint.assertIsSatisfied(camelContext);
    }

    /**
     * Tests filtering of messages.
     * @throws Exception
     */
    @Test
    public void testComplexLogic() throws Exception {
        assertEquals(ServiceStatus.Started, camelContextTwo.getStatus());

        EventMessage wrongTypeMessage = getEventMessage("1", "UPDATE", "kuali.lui.type.course.NO.OFFERING");
        EventMessage updateMessage = getEventMessage("2", "UPDATE", "kuali.lui.type.course.offering");
        EventMessage createMessage = getEventMessage("3", "CREATE", "kuali.lui.type.course.offering");
        EventMessage deleteMessage = getEventMessage("4", "DELETE", "kuali.lui.type.course.offering");

        mockC.expectedMessageCount(3); // 1 create and 1 update == 2

        startTwo.sendBody(updateMessage);
        startTwo.sendBody(wrongTypeMessage);
        startTwo.sendBody(deleteMessage);
        startTwo.sendBody(createMessage);

        MockEndpoint.assertIsSatisfied(camelContextTwo);
    }



    /**
     * This tests that our aggregation is working. ie. when a user updates seat counts
     * there are often multiple lui updates called. we only want the last one over a
     * span of 5 seconds (configurable)
     * @throws Exception
     */
    @Test
    public void testAggregation() throws Exception {
        assertEquals(ServiceStatus.Started, camelContext.getStatus());

        EventMessage wrongTypeMessage = getEventMessage("1", "UPDATE", "kuali.lui.type.course.NO.OFFERING");
        EventMessage updateMessage = getEventMessage("2", "UPDATE", "kuali.lui.type.course.offering");
        EventMessage createMessage = getEventMessage("3", "CREATE", "kuali.lui.type.course.offering");

        mockA.expectedBodiesReceived(updateMessage);
        mockB.expectedBodiesReceived(createMessage);

        mockA.expectedMessageCount(1);  // we're sending 10 messages, but only 1 should actually make it out
        mockB.expectedMessageCount(1);

        // Send 10 messages through the route.
        for(int i =0; i<10; i++) {
            start.sendBody(updateMessage);
        }
        start.sendBody(wrongTypeMessage);
        start.sendBody(createMessage);

        MockEndpoint.assertIsSatisfied(camelContext);
    }

    /**
     * helper method to create EventMessages
     * @param id
     * @param action
     * @param type
     * @return
     */
    private EventMessage getEventMessage(String id, String action, String type){
       EventMessage eventMessage = new EventMessage();

        eventMessage.setId(id);
        eventMessage.setAction(action);
        eventMessage.setType(type);
        eventMessage.setContextInfo(ContextUtils.createDefaultContextInfo());

        return eventMessage;
    }
}
