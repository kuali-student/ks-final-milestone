package org.kuali.student.enrollment.ui.registration.mq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 12/17/13
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestMessageRunner {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("org/kuali/student/enrollment/ui/registration/mq/mq-test-context.xml");//loading beans
        JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");// getting a reference to the sender bean
        for (int i = 0; i < 10; i++) {
            jmsTemplate.convertAndSend("Test.Queue.Name", "Message # " + i + " on " + new Date());// send
        }

    }
}
